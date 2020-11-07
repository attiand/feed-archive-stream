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
Feed feed = FeedReader.fromUri("src/test/resources/simple.xml");

feed.stream().map(Entry::getUri).flatMap(Optional::stream).forEach(System.out::println);
```

### Read backward

```java
Feed feed = FeedReader.fromUri("src/test/resources/simple.xml");

feed.reverseStream().map(Entry::getUri).flatMap(Optional::stream).forEach(System.out::println);
```
