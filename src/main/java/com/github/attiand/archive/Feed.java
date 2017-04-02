package com.github.attiand.archive;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.github.attiand.archive.internal.AbstractFeedSource;
import com.github.attiand.archive.internal.Direction;
import com.github.attiand.archive.internal.FeedSourceFactory;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndLink;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

public class Feed {

	private final FeedSourceFactory factory;
	private final Direction direction;
	private final URI uri;

	Feed(FeedSourceFactory factory, Direction direction, URI uri) throws URISyntaxException {
		this.factory = factory;
		this.direction = direction;
		this.uri = uri;
	}

	public Stream<Entry> stream() {
		return StreamSupport.stream(
				Spliterators.spliteratorUnknownSize(new EntryIterator(uri), Spliterator.ORDERED | Spliterator.NONNULL),
				false);
	}

	public Iterator<Entry> iterator() throws Exception {
		return new EntryIterator(uri);
	}

	Optional<SyndFeed> getNextFeed(SyndFeed feed) {
		Optional<SyndLink> prev = feed.getLinks().stream().filter(link -> link.getRel().equals(direction.getRel()))
				.findFirst();
		try {
			return prev.isPresent() ? getFeed(new URI(prev.get().getHref())) : Optional.empty();
		} catch (URISyntaxException e) {
			throw new FeedSourceException("Illegal uri: " + prev.get().getHref(), e);
		}
	}

	Optional<SyndFeed> getFeed(URI uri) {
		try (AbstractFeedSource httpClient = factory.create(uri.getScheme());
				InputStream stream = httpClient.getContent(uri)) {

			SyndFeedInput input = new SyndFeedInput();
			return Optional.of(input.build(new XmlReader(stream)));
		} catch (IOException | FeedException e) {
			throw new FeedSourceException(uri, e);
		}
	}

	class EntryIterator implements Iterator<Entry> {
		private Optional<SyndFeed> feed;
		private ListIterator<SyndEntry> entriesIterator;

		EntryIterator(URI uri) {
			feed = getFeed(uri);
			entriesIterator = getFeedIterator(feed);
		}

		public boolean hasNext() {
			if (direction == Direction.FORWARD ? entriesIterator.hasNext() : entriesIterator.hasPrevious()) {
				return true;
			} else {
				if (feed.isPresent()) {
					feed = getNextFeed(feed.get());
					entriesIterator = getFeedIterator(feed);
					return direction == Direction.FORWARD ? entriesIterator.hasNext() : entriesIterator.hasPrevious();
				} else {
					return false;
				}
			}
		}

		public Entry next() {
			return new Entry(direction == Direction.FORWARD ? entriesIterator.next() : entriesIterator.previous());
		}

		private ListIterator<SyndEntry> getFeedIterator(Optional<SyndFeed> feed) {
			if (feed.isPresent()) {
				List<SyndEntry> entries = feed.get().getEntries();
				return direction == Direction.FORWARD ? entries.listIterator() : entries.listIterator(entries.size());
			} else {
				return Collections.<SyndEntry>emptyList().listIterator();
			}
		}
	}
}
