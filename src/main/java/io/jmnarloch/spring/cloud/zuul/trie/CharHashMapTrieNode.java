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
    private final TCharObjectMap<CharHashMapTrieNode<T>> next = new TCharObjectHashMap<CharHashMapTrieNode<T>>();

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
}
