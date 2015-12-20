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
import org.springframework.cloud.netflix.zuul.filters.ProxyRouteLocator;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A Trie based {@link RouteMatcher}.
 *
 * @author Jakub Narloch
 */
public class TrieRouteMatcher implements RouteMatcher {

    /**
     * The suffix used for wildcard route matching.
     */
    private static final String WILDCARD = "/**";

    /**
     * The instance of {@link TrieSupplier} used for instantiating new Tries.
     */
    private final TrieSupplier trieSupplier;

    /**
     * Holds the reference to the Trie instance.
     */
    private final AtomicReference<Trie<ProxyRouteLocator.ProxyRouteSpec>> trie =
            new AtomicReference<Trie<ProxyRouteLocator.ProxyRouteSpec>>();

    /**
     * Creates new instance of {@link TrieRouteMatcher} with specific supplier.
     * @param trieSupplier the Trie instance supplier
     */
    public TrieRouteMatcher(TrieSupplier trieSupplier) {
        Assert.notNull(trieSupplier, "Parameter 'trieSupplier' can not be null");
        this.trieSupplier = trieSupplier;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setRoutes(Map<String, ProxyRouteLocator.ProxyRouteSpec> routes) {

        final Trie<ProxyRouteLocator.ProxyRouteSpec> trie = createTrie();
        for (Map.Entry<String, ProxyRouteLocator.ProxyRouteSpec> route : routes.entrySet()) {
            final String path = path(route.getKey());
            trie.put(path, route.getValue());
        }
        this.trie.set(trie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProxyRouteLocator.ProxyRouteSpec getMatchingRoute(String path) {

        return trie.get().prefix(path);
    }

    /**
     * Normalizes the path by removing any wildcard symbol front the end.
     *
     * @param path the path
     * @return the normalized path
     */
    private String path(String path) {
        if(path.endsWith(WILDCARD)) {
            path = path.substring(0, path.length() - WILDCARD.length());
        }
        return path;
    }

    /**
     * Creates new instance of {@link Trie} by delegating to the provided {@link TrieSupplier} instance.
     *
     * @return the trie instance
     */
    private Trie<ProxyRouteLocator.ProxyRouteSpec> createTrie() {
        return trieSupplier.createTrie();
    }

    /**
     * The Trie instance supplier, used whenever to instantiate and populate a Trie whenever a new list of routes is
     * being provided.
     *
     * @author Jakub Narloch
     */
    public interface TrieSupplier {

        <T> Trie<T> createTrie();
    }
}
