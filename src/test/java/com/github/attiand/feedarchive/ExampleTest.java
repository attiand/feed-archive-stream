package com.github.attiand.feedarchive;

import java.util.Optional;

import org.junit.Ignore;
import org.junit.Test;

import com.github.attiand.feedarchive.Entry;
import com.github.attiand.feedarchive.Feed;
import com.github.attiand.feedarchive.FeedReader;

public class ExampleTest {

	@Ignore
	@Test
	public void shouldReadForward() throws Exception {
		Feed feed = FeedReader.fromUri("src/test/resources/simple.xml");
		feed.stream().map(e -> e.getUri()).filter(Optional::isPresent).map(Optional::get).forEach(System.out::println);
	}

	@Ignore
	@Test
	public void shouldReadBackward() throws Exception {
		Feed feed = FeedReader.fromUri("src/test/resources/simple.xml");
		feed.reverseStream().map(e -> e.getUri()).filter(Optional::isPresent).map(Optional::get).forEach(System.out::println);
	}

	@Ignore
	@Test
	public void shouldfindNode() throws Exception {
		Feed feed = FeedReader.fromUri("src/test/resources/simple.xml");
		Optional<Entry> entry = feed.stream()
				.filter(e -> e.getUri().isPresent() && e.getUri().get().equals("3197a96f-cf9d-4791-ba3b-cafe2d02e9f2"))
				.findFirst();
		entry.ifPresent(System.out::println);
	}
}
