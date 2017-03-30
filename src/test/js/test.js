describe("Feed API suite", function() {
	it("should return message", function() {
		var Test = Java.type("feed.api.Test");
		var test = new Test();
		expect(test.foo()).toBe('hello');
	});
});