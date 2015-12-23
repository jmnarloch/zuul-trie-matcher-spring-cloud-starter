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

import org.springframework.util.Assert;

/**
 * The base class for all {@link Trie} instances.
 *
 * @author Jakub Narloch
 */
abstract class AbstractTrie<T, N extends AbstractTrie.TrieNode<T, N>> implements Trie<T> {

    /**
     * A node factory.
     */
    private final TrieNodeFactory<T, N> nodeFactory;

    /**
     * The root node of the tree.
     */
    private N root;

    /**
     * Creates new instance of {@link AbstractTrie} with specific node factory.
     *
     * @param nodeFactory the node factory
     */
    public AbstractTrie(TrieNodeFactory<T, N> nodeFactory) {
        this.nodeFactory = nodeFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void put(String key, T value) {
        Assert.hasLength(key, "Key must be not null or not empty string.");

        root = put(getRoot(), key, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(String key) {
        Assert.hasLength(key, "Key must be not null or not empty string.");

        return get(key) != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T get(String key) {
        Assert.hasLength(key, "Key must be not null or not empty string.");

        return get(getRoot(), key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T prefix(String key) {
        Assert.hasLength(key, "Key must be not null or not empty string.");

        return prefix(getRoot(), key);
    }

    private N put(N node, String key, T value) {

        if (node == null) {
            node = createTrieNode();
        }
        final N root = node;

        N next;
        int index = 0;

        while (index < key.length()) {
            final char c = getChar(key, index);
            next = node.getNext(c);
            if (next == null) {
                next = createTrieNode();
                node.setNext(c, next);
            }
            node = next;
            index++;
        }
        node.setValue(value);
        return root;
    }

    private T get(N node, String key) {

        int index = 0;
        while (node != null) {
            if (index == key.length()) {
                return node.getValue();
            }
            node = node.getNext(getChar(key, index));
            index++;
        }
        return null;
    }

    private T prefix(N node, String key) {

        T value = null;
        int index = 0;
        while (node != null) {
            if (node.hasValue()) {
                value = node.getValue();
            }
            if (index == key.length()) {
                break;
            }
            node = node.getNext(getChar(key, index));
            index++;
        }
        return value;
    }

    private char getChar(String key, int index) {
        return key.charAt(index);
    }

    private N getRoot() {
        return root;
    }

    private N createTrieNode() {
        return nodeFactory.createNode();
    }

    interface TrieNodeFactory<T, N extends TrieNode<T, N>> {

        N createNode();
    }

    interface TrieNode<T, N extends TrieNode<T, N>> {

        void setNext(char c, N next);

        N getNext(char c);

        void setValue(T value);

        T getValue();

        boolean hasValue();
    }
}
