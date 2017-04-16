package com.github.attiand.archive;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.github.attiand.archive.Feed;
import com.github.attiand.archive.FeedSource;

public class FeedArchiveTest {

	@Test
	public void shouldIterateOverArchives() throws Exception {
		Feed feed = FeedSource.fromUri("src/test/resources/archive1.xml");
		long count = feed.stream().count();

		assertThat(count, is(101L));
		assertThat(feed.stream().map(e -> e.getUri()).findFirst().get().get(), is("c66740f8-f776-48a2-b4d6-d0197975133e"));
		assertThat(feed.stream().skip(count - 1).map(e -> e.getUri()).findFirst().get().get(),
				is("e4b3606b-d989-4044-a7ec-79527e075afe"));
	}

	@Test
	public void shouldIterateOverArchivesBackward() throws Exception {
		Feed feed = FeedSource.fromUri("src/test/resources/archive2.xml");
		long count = feed.reverseStream().count();

		assertThat(count, is(101L));
		assertThat(feed.reverseStream().map(e -> e.getUri()).findFirst().get().get(), is("e4b3606b-d989-4044-a7ec-79527e075afe"));
		assertThat(feed.reverseStream().skip(count - 1).map(e -> e.getUri()).findFirst().get().get(),
				is("c66740f8-f776-48a2-b4d6-d0197975133e"));
	}
}
