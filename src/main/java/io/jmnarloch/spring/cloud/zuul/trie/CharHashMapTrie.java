/**
 * Copyright (c) 2015 the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.jmnarloch.spring.cloud.zuul.trie;

/**
 * A Trie tree backed by Trove's {@link gnu.trove.map.hash.TCharObjectHashMap}.
 *
 * @author Jakub Narloch
 */
public class CharHashMapTrie<T> extends AbstractTrie<T, CharHashMapTrieNode<T>> {

    /**
     * Creates new instance of {@link CharHashMapTrie} class.
     */
    public CharHashMapTrie() {
        super(new TrieNodeFactory<T, CharHashMapTrieNode<T>>() {
            @Override
            public CharHashMapTrieNode<T> createNode() {
                return new CharHashMapTrieNode<T>();
            }
        });
    }

    /**
     * Creates new instance of {@link CharHashMapTrie} class with initial capacity.
     *
     * @param initialCapacity the initial capacity
     */
    public CharHashMapTrie(final int initialCapacity) {
        super(new TrieNodeFactory<T, CharHashMapTrieNode<T>>() {
            @Override
            public CharHashMapTrieNode<T> createNode() {
                return new CharHashMapTrieNode<T>(initialCapacity);
            }
        });
    }

    /**
     * Creates new instance of {@link CharHashMapTrie} class with initial capacity and load factor.
     *
     * @param initialCapacity the initial capacity
     * @param loadFactor      the load factor
     */
    public CharHashMapTrie(final int initialCapacity, final float loadFactor) {
        super(new TrieNodeFactory<T, CharHashMapTrieNode<T>>() {
            @Override
            public CharHashMapTrieNode<T> createNode() {
                return new CharHashMapTrieNode<T>(initialCapacity, loadFactor);
            }
        });
    }
}
