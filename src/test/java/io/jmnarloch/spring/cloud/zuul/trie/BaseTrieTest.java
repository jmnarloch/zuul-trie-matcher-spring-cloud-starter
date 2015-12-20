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
package io.jmnarloch.spring.cloud.zuul.trie;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * The base class for all Trie tests.
 *
 * @author Jakub Narloch
 */
public abstract class BaseTrieTest {

    private Trie<String> instance;

    @Before
    public void setUp() throws Exception {

        instance = createTrie();
        final Set<String> routes = getRoutes();
        for (String route : routes) {
            instance.put(route, route);
        }
    }

    @Test
    public void shouldFindAllMatchingRoutes() throws Exception {

        for (String route : getRoutes()) {
            // when
            final String result = instance.get(route);

            // then
            assertEquals(route, result);
        }
    }

    @Test
    public void shouldFindAllPrefixRoutes() throws Exception {

        for (String route : getRoutes()) {
            // when
            final String result = instance.prefix(route);

            // then
            assertEquals(route, result);
        }
    }

    @Test
    public void shouldFindAllExistingRoutes() throws Exception {

        for (String route : getRoutes()) {
            // when
            final boolean exists = instance.contains(route);

            // then
            assertTrue(exists);
        }
    }

    protected Set<String> getRoutes() {

        return new HashSet<String>(Arrays.asList(
                "/uaa/**",
                "/user/**",
                "/account/**",
                "/api/**",
                "/notifications/**",
                "/ws/**"
        ));
    }

    protected abstract Trie<String> createTrie();
}