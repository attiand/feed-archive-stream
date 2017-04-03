package com.github.attiand.archive;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

public class Main {

	public static void main(String[] args) throws URISyntaxException {
		List<String> opts = Arrays.asList(args);

		if (opts.size() > 0 && opts.size() < 3) {

			boolean backwards = opts.contains("-b");
			String uri = opts.get(opts.size() - 1);

			Feed feed = backwards ? FeedSource.last(uri) : FeedSource.first(uri);

			feed.stream().map(e -> e.getUri()).forEach(System.out::println);

		} else {
			System.err.println("usage: [-b] url\n -b backward, read feed from end");
			System.exit(-1);
		}
	}
}
