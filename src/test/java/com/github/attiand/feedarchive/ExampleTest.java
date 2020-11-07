package com.github.attiand.feedarchive;

import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class ExampleTest {

	@Disabled
	@Test
	public void shouldReadForward() throws Exception {
		Feed feed = FeedReader.fromUri("src/test/resources/simple.xml");
		feed.stream().map(Entry::getUri).flatMap(Optional::stream).forEach(System.out::println);
	}

	@Disabled
	@Test
	public void shouldReadBackward() throws Exception {
		Feed feed = FeedReader.fromUri("src/test/resources/simple.xml");
		feed.reverseStream().map(Entry::getUri).flatMap(Optional::stream).forEach(System.out::println);
	}
}