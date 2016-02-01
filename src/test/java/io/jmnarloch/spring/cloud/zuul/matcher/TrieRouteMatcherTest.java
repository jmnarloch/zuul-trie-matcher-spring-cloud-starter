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
package io.jmnarloch.spring.cloud.zuul.matcher;

import io.jmnarloch.spring.cloud.zuul.trie.Trie;
import io.jmnarloch.spring.cloud.zuul.trie.Tries;
import org.junit.Before;
import org.junit.Test;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Tests the {@link TrieRouteMatcher} class.
 *
 * @author Jakub Narloch
 */
public class TrieRouteMatcherTest {

    private TrieRouteMatcher instance;

    @Before
    public void setUp() throws Exception {

        instance = new TrieRouteMatcher(new TrieRouteMatcher.TrieSupplier() {
            @Override
            public <T> Trie<T> createTrie() {
                return Tries.newCharHashMapTrie();
            }
        });
        instance.setRoutes(getRoutes());
    }

    @Test
    public void shouldNotMatchWildcardRoute() {
        // given
        final String path = "/accounts/";

        // when
        final ZuulProperties.ZuulRoute result = instance.getMatchingRoute(path);

        // then
        assertNull(result);
    }

    @Test
    public void shouldMatchWildcardRoute() {
        // given
        final String path = "/account/details";

        // when
        final ZuulProperties.ZuulRoute result = instance.getMatchingRoute(path);

        // then
        assertNotNull(result);
    }

    @Test
    public void shouldNotMatchExactRouteWithSuffix() {
        // given
        final String path = "/uaa/authorize";

        // when
        final ZuulProperties.ZuulRoute result = instance.getMatchingRoute(path);

        // then
        assertNull(result);
    }

    @Test
    public void shouldMatchExactRoute() {
        // given
        final String path = "/uaa/";

        // when
        final ZuulProperties.ZuulRoute result = instance.getMatchingRoute(path);

        // then
        assertNotNull(result);
    }

    @Test
    public void shouldNotMatchExactRoute() {
        // given
        final String path = "/uaa";

        // when
        final ZuulProperties.ZuulRoute result = instance.getMatchingRoute(path);

        // then
        assertNull(result);
    }

    protected Map<String, ZuulProperties.ZuulRoute> getRoutes() {

        final Map<String, ZuulProperties.ZuulRoute> routes =
                new HashMap<String, ZuulProperties.ZuulRoute>();
        routes.put("/uaa/", new ZuulProperties.ZuulRoute("uaa", "/uaa", "uaa", "/uaa", false, null));
        routes.put("/account/**", new ZuulProperties.ZuulRoute("account", "/account", "account", "/account", false, null));
        return routes;
    }
}