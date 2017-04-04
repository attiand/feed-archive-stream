describe("drive js api", function() {
	it("iterate forward", function() {
		var feedSource = Java.type("com.github.attiand.archive.FeedSource");
		var feed = feedSource.fromUri("src/test/resources/simple.xml");
		var it = feed.iterator();

		expect(it.hasNext()).toBe(true);
		expect(it.next().getUri()).toBe("4ade0415-abdc-4024-b838-9c8961e80cca");
		expect(it.hasNext()).toBe(true);
		expect(it.next().getUri()).toBe("3197a96f-cf9d-4791-ba3b-cafe2d02e9f2");		
		expect(it.hasNext()).toBe(false);
	});
	
	it("stream find", function() {
		var feedSource = Java.type("com.github.attiand.archive.FeedSource");
		var feed = feedSource.fromUri("src/test/resources/simple.xml");
		var entry = feed.stream().filter(function(e) e.getUri() == '3197a96f-cf9d-4791-ba3b-cafe2d02e9f2').findFirst();

		expect(entry.isPresent()).toBe(true);
		expect(entry.get().getUri()).toBe('3197a96f-cf9d-4791-ba3b-cafe2d02e9f2');
	});
});
