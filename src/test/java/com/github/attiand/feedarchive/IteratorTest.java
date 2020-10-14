package com.github.attiand.feedarchive;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Iterator;
import java.util.Optional;

import org.junit.jupiter.api.Test;

public class IteratorTest {

	@Test
	public void shouldIterateForward() throws Exception {
		Feed feed = FeedReader.fromUri("src/test/resources/simple.xml");
		Iterator<Entry> it = feed.iterator();
		assertThat(it).isNotNull().hasNext();
		assertThat(it).toIterable().extracting(Entry::getUri).extracting(Optional::get)
				.containsExactly("4ade0415-abdc-4024-b838-9c8961e80cca", "3197a96f-cf9d-4791-ba3b-cafe2d02e9f2");
	}

	@Test
	public void shouldIterateBackward() throws Exception {
		Feed feed = FeedReader.fromUri("src/test/resources/simple.xml");
		Iterator<Entry> it = feed.reverseIterator();

		assertThat(it).isNotNull().hasNext();
		assertThat(it).isNotNull().hasNext();
		assertThat(it).toIterable().extracting(Entry::getUri).extracting(Optional::get)
				.containsExactly("3197a96f-cf9d-4791-ba3b-cafe2d02e9f2", "4ade0415-abdc-4024-b838-9c8961e80cca");
	}
}
