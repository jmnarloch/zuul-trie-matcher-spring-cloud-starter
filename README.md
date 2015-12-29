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
  <version>1.0.1</version>
</dependency>
```

Enable the Zuul proxy with `@EnableZuulProxyMatcher` - use this annotation as a replacement for standard `@EnableZuulProxy`,
the only requirement to use this component is to register a concrete bean of type `RouteMatcher`

```java
@EnableZuulProxyMatcher
@SpringBootApplication
public static class Application {

    @Bean
    public RouteMatcher routeMatcher() {
        return new TrieRouteMatcher(() -> Tries.newCharHashMapTrie());
    }
}
```

Except for that everything is generally the same as when used with standard Zuul proxy.

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

## Performance characteristics

The standard implementation of [ProxyRouteLocator](https://github.com/spring-cloud/spring-cloud-netflix/blob/master/spring-cloud-netflix-core/src/main/java/org/springframework/cloud/netflix/zuul/filters/ProxyRouteLocator.java) iterates over every `ZuulProperties.ZuulRoute` in order to find the
first one matching the request URI. If we denote N - as number of routes and M as the maximum path length then we can
say that finding the path takes O(NM) time in worst case.

The proposed alternative will replace this path finding by performing prefix search on the Trie tree, with
running time of O(M) in worst case, the performance gains are made in exchange of extra memory usage.

Also the side effect of using the Trie is that it allows to define overlapping paths for instance:

* /uaa/**
* /uaa/account/**

As already stated the standard implementation would chose either of those paths depending on the order they have been
defined in properties file, the Trie tree in contrary would find the best matching route i.e.
for path /uaa/authorize, /uaa/** would be used and for /uaa/account/j.doe, /uaa/account/** is going to be matched.

## License

Apache 2.0