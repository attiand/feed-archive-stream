package com.github.attiand.feedarchive;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Iterator;

import org.junit.Test;

import com.github.attiand.feedarchive.Entry;
import com.github.attiand.feedarchive.Feed;
import com.github.attiand.feedarchive.FeedReader;

public class IteratorTest {

	@Test
	public void shouldIterateForward() throws Exception {
		Feed feed = FeedReader.fromUri("src/test/resources/simple.xml");
		Iterator<Entry> it = feed.iterator();
		assertThat(it.hasNext(), is(true));
		assertThat(it.next().getUri().get(), is("4ade0415-abdc-4024-b838-9c8961e80cca"));
		assertThat(it.hasNext(), is(true));
		assertThat(it.next().getUri().get(), is("3197a96f-cf9d-4791-ba3b-cafe2d02e9f2"));
		assertThat(it.hasNext(), is(false));
	}

	@Test
	public void shouldIterateBackward() throws Exception {
		Feed feed = FeedReader.fromUri("src/test/resources/simple.xml");
		Iterator<Entry> it = feed.reverseIterator();
		assertThat(it.hasNext(), is(true));
		assertThat(it.next().getUri().get(), is("3197a96f-cf9d-4791-ba3b-cafe2d02e9f2"));
		assertThat(it.hasNext(), is(true));
		assertThat(it.next().getUri().get(), is("4ade0415-abdc-4024-b838-9c8961e80cca"));
		assertThat(it.hasNext(), is(false));
	}
}
