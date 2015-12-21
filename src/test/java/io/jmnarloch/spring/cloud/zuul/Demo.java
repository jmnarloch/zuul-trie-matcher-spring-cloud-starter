/**
 * Copyright (c) 2015 the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.jmnarloch.spring.cloud.zuul;

import io.jmnarloch.spring.cloud.zuul.api.EnableZuulProxyMatcher;
import io.jmnarloch.spring.cloud.zuul.matcher.RouteMatcher;
import io.jmnarloch.spring.cloud.zuul.matcher.TrieRouteMatcher;
import io.jmnarloch.spring.cloud.zuul.trie.Trie;
import io.jmnarloch.spring.cloud.zuul.trie.Tries;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.cloud.netflix.zuul.filters.ProxyRouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Demonstrates the usage of this component.
 *
 * @author Jakub Narloch
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebIntegrationTest("server.port=0")
@SpringApplicationConfiguration(classes = Demo.Application.class)
public class Demo {

    @Autowired
    public ProxyRouteLocator proxyRouteLocator;

    @Test
    public void shouldMatchRoute() {

        // given
        final String path = "/uaa/authorize";

        // when
        final ProxyRouteLocator.ProxyRouteSpec route = proxyRouteLocator.getMatchingRoute(path);

        // then
        assertNotNull(route);
        assertEquals("/uaa/**", route.getPath());
    }

    @EnableZuulProxyMatcher
    @SpringBootApplication
    public static class Application {

        @Bean
        public RouteMatcher routeMatcher() {
            return new TrieRouteMatcher(new TrieRouteMatcher.TrieSupplier() {
                @Override
                public <T> Trie<T> createTrie() {
                    return Tries.newCharHashMapTrie();
                }
            });
        }
    }
}
