# feed-archive-stream

Stream API for Atom feed archives (RFC 5005). Follows `next-archive` and `prev-archive` links when iterating Atom entries.

## Examples

### Read forward

```java
Feed feed = FeedSource.fromUri("http://localhost:8080/feed/1");
feed.stream().map(e -> e.getUri()).forEach(System.out::println);
```

### Read backward

```java
Feed feed = FeedSource.fromUri("http://localhost:8080/feed/recent");
feed.reverseStream().map(e -> e.getUri()).forEach(System.out::println);
```
### Find specific node

```java
Feed feed = FeedSource.fromUri("http://localhost:8080/feed/1");
Optional<Entry> entry = feed.stream().
                        filter(e -> e.getUri().equals("3197a96f-cf9d-4791-ba3b-cafe2d02e9f2")).
                        findFirst();
```

## JavaScript

```javascript
var feedSource = Java.type('com.github.attiand.archive.FeedSource');
var feed = feedSource.fromUri('http://localhost:8080/feed/1');
var entry = feed.stream().
                 filter(function(e) e.getUri() == '3197a96f-cf9d-4791-ba3b-cafe2d02e9f2').
                 findFirst();
```
