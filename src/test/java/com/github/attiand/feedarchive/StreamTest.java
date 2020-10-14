package com.github.attiand.feedarchive;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class StreamTest {

	@Test
	public void shouldStreamArchives() throws Exception {
		Feed feed = FeedReader.fromUri("src/test/resources/archive1.xml");

		assertThat(feed.stream()).hasSize(101);
		assertThat(feed.stream().findFirst()).hasValueSatisfying(e -> {
			assertThat(e.getUri()).hasValueSatisfying(uri -> {
				assertThat(uri).isEqualTo("c66740f8-f776-48a2-b4d6-d0197975133e");
			});
		});

		assertThat(feed.stream().skip(100).findFirst()).hasValueSatisfying(e -> {
			assertThat(e.getUri()).hasValueSatisfying(uri -> {
				assertThat(uri).isEqualTo("e4b3606b-d989-4044-a7ec-79527e075afe");
			});
		});
	}

	@Test
	public void shouldStreamArchivesBackward() throws Exception {
		Feed feed = FeedReader.fromUri("src/test/resources/archive2.xml");

		assertThat(feed.reverseStream()).hasSize(101);

		assertThat(feed.reverseStream().findFirst()).hasValueSatisfying(e -> {
			assertThat(e.getUri()).hasValueSatisfying(uri -> {
				assertThat(uri).isEqualTo("e4b3606b-d989-4044-a7ec-79527e075afe");
			});
		});

		assertThat(feed.reverseStream().skip(100).findFirst()).hasValueSatisfying(e -> {
			assertThat(e.getUri()).hasValueSatisfying(uri -> {
				assertThat(uri).isEqualTo("c66740f8-f776-48a2-b4d6-d0197975133e");
			});
		});
	}
}
