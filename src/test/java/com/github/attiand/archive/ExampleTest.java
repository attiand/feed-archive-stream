package com.github.attiand.archive;

import org.junit.Ignore;
import org.junit.Test;

public class ExampleTest {

	@Ignore
	@Test
	public void shouldReadBackward() throws Exception {
		Feed feed = FeedSource.last("src/test/resources/simple.xml");
		feed.stream().map(e -> e.getUri()).forEach(System.out::println);
	}
	
	@Ignore
	@Test
	public void shouldReadForward() throws Exception {
		Feed feed = FeedSource.first("src/test/resources/simple.xml");
		feed.stream().map(e -> e.getUri()).forEach(System.out::println);
	}
}
