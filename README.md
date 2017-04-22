# feed-archive-stream

Stream API for Atom feed archives (RFC 5005). Follows `next-archive` and `prev-archive` links when iterating Atom entries.

## Maven

```xml
<dependency>
    <groupId>com.github.attiand</groupId>
    <artifactId>feed-archive-stream</artifactId>
    <version>${feed-archive-stream.version}</version>
</dependency>
```

## Examples

### Read forward

```java
Feed feed = FeedReader.fromUri("http://localhost:8080/feed/1");

feed.stream().feed.stream().map(e -> e.getUri()).forEach(o -> o.ifPresent(System.out::println));
```

### Read backward

```java
Feed feed = FeedReader.fromUri("http://localhost:8080/feed/recent");

feed.reverseStream().feed.stream().map(e -> e.getUri()).forEach(o -> o.ifPresent(System.out::println));
```
### Find specific node

```java
Feed feed = FeedReader.fromUri("http://localhost:8080/feed/1");
Optional<Entry> entry = feed.stream().
    filter(e -> e.getUri().isPresent() && e.getUri().get().equals("3197a96f-cf9d-4791-ba3b-cafe2d02e9f2")).
    findFirst();

entry.ifPresent(System.out::println);
```
