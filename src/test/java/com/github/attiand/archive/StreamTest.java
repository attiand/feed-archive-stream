package com.github.attiand.archive;

import static com.github.attiand.archive.XPathMapper.xpath;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.w3c.dom.Element;

public class StreamTest {

	@Test
	public void shouldFindEntry() throws Exception {
		Feed feed = FeedSource.fromUri("src/test/resources/simple.xml");
		Optional<Entry> entry = feed.stream().filter(e -> e.getUri().equals("3197a96f-cf9d-4791-ba3b-cafe2d02e9f2"))
				.findFirst();

		assertThat(entry.isPresent(), is(true));
		assertThat(entry.get().getUpdatedDate().toInstant().toString(), is("2017-02-24T09:52:33Z"));
	}

	@Test
	public void example() throws Exception {
		Feed feed = FeedSource.fromUri("src/test/resources/simple.xml");
		Optional<Element> entry = feed.stream().flatMap(e -> e.dom()).flatMap(xpath("//*:rel")).findFirst();

		if (entry.isPresent()) {
			System.out.println(entry.get().getTextContent());
		}

	}
}
