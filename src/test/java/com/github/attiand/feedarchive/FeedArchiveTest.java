package com.github.attiand.feedarchive;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class FeedArchiveTest {

	@Test
	public void shouldExtractEntry() throws Exception {
		Feed feed = FeedReader.fromUri("src/test/resources/simple.xml");

		assertThat(feed.stream()).hasSize(2);
		assertThat(feed.stream().findFirst()).hasValueSatisfying(e -> {
			assertThat(e.getUri()).hasValueSatisfying(uri -> {
				assertThat(uri).isEqualTo("4ade0415-abdc-4024-b838-9c8961e80cca");
			});
			assertThat(e.getUpdatedDate()).hasValueSatisfying(d -> {
				assertThat(d).isEqualTo("2017-02-24T09:52:43Z");
			});
		});
	}
}
