/**
 * Copyright (c) 2015 the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.jmnarloch.spring.cloud.zuul.trie;

import gnu.trove.map.TCharObjectMap;
import gnu.trove.map.hash.TCharObjectHashMap;

/**
 * A Trove {@link TCharObjectMap} backed Trie node.
 *
 * @author Jakub Narloch
 */
class CharHashMapTrieNode<T> extends AbstractTrieNode<T, CharHashMapTrieNode<T>> {

    /**
     * The map of children nodes.
     */
    private final TCharObjectMap<CharHashMapTrieNode<T>> next;

    /**
     * Creates new instance of {@link CharHashMapTrieNode}.
     */
    public CharHashMapTrieNode() {
        next = new TCharObjectHashMap<CharHashMapTrieNode<T>>();
    }

    /**
     * Creates new instance of {@link CharHashMapTrieNode} with specific initial capacity.
     *
     * @param initialCapacity the initial capacity
     */
    public CharHashMapTrieNode(int initialCapacity) {
        next = new TCharObjectHashMap<CharHashMapTrieNode<T>>(initialCapacity);
    }

    /**
     * Creates new instance of {@link CharHashMapTrieNode} with specific initial capacity and load factor.
     *
     * @param initialCapacity the initial capacity
     * @param loadFactor      the load factor
     */
    public CharHashMapTrieNode(int initialCapacity, float loadFactor) {
        next = new TCharObjectHashMap<CharHashMapTrieNode<T>>(initialCapacity, loadFactor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNext(char c, CharHashMapTrieNode<T> next) {
        this.next.put(c, next);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CharHashMapTrieNode<T> getNext(char c) {
        return next.get(c);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeNext(char c) {
        next.remove(c);
    }
}
