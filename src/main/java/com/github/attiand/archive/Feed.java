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
	private final URI baseUri;

	Feed(FeedSourceFactory factory, Direction direction, URI uri) throws URISyntaxException {
		this.factory = factory;
		this.baseUri = uri;
	}

	public Stream<Entry> stream() {
		return StreamSupport.stream(Spliterators.spliteratorUnknownSize(new EntryIterator(baseUri, Direction.FORWARD),
				Spliterator.ORDERED | Spliterator.NONNULL), false);
	}

	public Stream<Entry> reverseStream() {
		return StreamSupport.stream(Spliterators.spliteratorUnknownSize(new EntryIterator(baseUri, Direction.BACKWARD),
				Spliterator.ORDERED | Spliterator.NONNULL), false);
	}

	public Iterator<Entry> iterator() throws Exception {
		return new EntryIterator(baseUri, Direction.FORWARD);
	}

	public Iterator<Entry> reverseIterator() throws Exception {
		return new EntryIterator(baseUri, Direction.BACKWARD);
	}

	Optional<SyndFeed> getNextFeed(SyndFeed feed, String rel) {
		Optional<SyndLink> prev = feed.getLinks().stream().filter(link -> link.getRel().equals(rel)).findFirst();
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
		private final Direction direction;
		private Optional<SyndFeed> baseFeed;
		private ListIterator<SyndEntry> entriesIterator;

		EntryIterator(URI uri, Direction direction) {
			this.direction = direction;
			this.baseFeed = getFeed(uri);
			this.entriesIterator = getFeedIterator(baseFeed);
		}

		public boolean hasNext() {
			if (direction == Direction.FORWARD ? entriesIterator.hasNext() : entriesIterator.hasPrevious()) {
				return true;
			} else {
				if (baseFeed.isPresent()) {
					baseFeed = getNextFeed(baseFeed.get(), direction.getRel());
					entriesIterator = getFeedIterator(baseFeed);
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
