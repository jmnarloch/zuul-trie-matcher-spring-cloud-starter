# Spring Cloud Zuul Trie route matcher

> A Spring Cloud Trie route matcher

[![Build Status](https://travis-ci.org/jmnarloch/zuul-trie-matcher-spring-cloud-starter.svg?branch=master)](https://travis-ci.org/jmnarloch/zuul-trie-matcher-spring-cloud-starter)
[![Coverage Status](https://coveralls.io/repos/jmnarloch/zuul-trie-matcher-spring-cloud-starter/badge.svg?branch=master&service=github)](https://coveralls.io/github/jmnarloch/zuul-trie-matcher-spring-cloud-starter?branch=master)

## Features

Extends the Spring Cloud's `ProxyRouteLocator` with configurable matching strategy. The provided implementation allows
to register a [Trie tree](https://en.wikipedia.org/wiki/Trie) for matching the routes.

## Setup

Add the Spring Cloud starter to your project:

```xml
<dependency>
  <groupId>io.jmnarloch</groupId>
  <artifactId>zuul-trie-matcher-spring-cloud-starter</artifactId>
  <version>1.0.0</version>
</dependency>
```

Enable the Zuul proxy with `@EnableZuulProxyMatcher` - use this annotation as a replacement for standard `@EnableZuulProxy`,
the only requirement to use this component is to register a concrete bean of type `RouteMatcher`

```java
@EnableZuulProxyStore
@SpringBootApplication
public static class Application {

    @Bean
    public RouteMatcher routeMatcher() {
        return new TrieRouteMatcher(() -> Tries.newCharMapTrie());
    }
}
```

## Implementation details

The Trie is a R way tree that is designed for efficient string searches, perfectly fitting for use cases like Zuul
route path matching. For most effective use the Trie is being build on application context refresh and used for
queries afterwards.

At this moment this component defines three different implementation of the Trie, all of which differs slightly
in performance, but far most with the memory consumption.

The available Trie implementations are:

* CharArrayTrie
* HashMapTrie
* CharHashMapTrie - that uses Trove TCharObjectHashMap

## License

Apache 2.0