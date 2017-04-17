package com.github.attiand.feedarchive;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Main {

	public static void main(String[] args) throws URISyntaxException {
		List<String> opts = Arrays.asList(args);

		if (opts.size() > 0 && opts.size() < 3) {
			String uri = opts.get(opts.size() - 1);

			Feed feed = FeedReader.fromUri(uri);

			Stream<Entry> strem = opts.contains("-b") ? feed.reverseStream() : feed.stream();

			strem.map(e -> e.getUri()).forEach(System.out::println);

		} else {
			System.err.println("usage: [-b] url\n -b backward, read feed from end");
			System.exit(-1);
		}
	}
}
