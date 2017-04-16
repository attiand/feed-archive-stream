package com.github.attiand.archive;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Test;

import com.github.attiand.archive.Entry;
import com.github.attiand.archive.Feed;
import com.github.attiand.archive.FeedSource;

public class StreamTest {

	@Test
	public void shouldFindEntry() throws Exception {
		Feed feed = FeedSource.fromUri("src/test/resources/simple.xml");
		Optional<Entry> entry = feed.stream().filter(e -> e.getUri().get().equals("3197a96f-cf9d-4791-ba3b-cafe2d02e9f2"))
				.findFirst();

		assertThat(entry.isPresent(), is(true));
		assertThat(entry.get().getUpdatedDate().get().toInstant().toString(), is("2017-02-24T09:52:33Z"));
	}
}
