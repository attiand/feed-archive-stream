# feed-archive-stream

Stream API for Atom feed archives. Follows `next-archive` and `prev-archive` links when iterating Atom entries.

## Examples

### Read forward.

```java
Feed feed = FeedSource.first("http://localhost:8080/sim/api/feed/1");
feed.stream().map(e -> e.getUri()).forEach(System.out::println);
```

### Read backward form the end.

```java
Feed feed = FeedSource.last("http://localhost:8080/sim/api/feed/recent");
feed.stream().map(e -> e.getUri()).forEach(System.out::println);
```
### Find specific node.

```java
Feed feed = FeedSource.last("http://localhost:8080/sim/api/feed/recent");
Optional<Entry> entry = feed.stream().
                        filter(e -> e.getUri().equals("3197a96f-cf9d-4791-ba3b-cafe2d02e9f2")).
                        findFirst();
```

## JavaScript

```javascript
var feedSource = Java.type('com.github.attiand.archive.FeedSource');
var feed = feedSource.first('http://localhost:8080/sim/api/feed/recent');
var entry = feed.stream().filter(function(e) e.getUri() == '3197a96f-cf9d-4791-ba3b-cafe2d02e9f2').findFirst();
```
