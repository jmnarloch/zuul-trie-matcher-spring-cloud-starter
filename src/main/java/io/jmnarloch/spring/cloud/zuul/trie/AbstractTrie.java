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

import java.util.Deque;
import java.util.LinkedList;

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
        this.root = createTrieNode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return getRoot().getSize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T put(String key, T value) {
        Assert.hasLength(key, "Key must be not null or not empty string.");

        return put(getRoot(), key, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean containsKey(String key) {
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

    @Override
    public T remove(String key) {
        Assert.hasLength(key, "Key must be not null or not empty string.");

        return remove(getRoot(), key);
    }

    private T put(N root, String key, T value) {

        N node = root;
        final Deque<N> stack = new LinkedList<N>();
        stack.push(node);
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
            stack.push(node);
            index++;
        }
        final boolean replaced = node.hasValue();
        final T old = node.getValue();
        node.setValue(value);
        if(replaced) {
            return old;
        }

        while (!stack.isEmpty()) {
            node = stack.pop();
            node.setSize(node.getSize() + 1);
        }
        return null;
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

    protected T remove(N root, String key) {

        int index = 0;
        N node = root;
        N next;
        final Deque<N> stack = new LinkedList<N>();

        while (index < key.length()) {
            stack.push(node);
            next = node.getNext(getChar(key, index));
            if(next == null) {
                return null;
            }
            node = next;
            index++;
        }
        if(!node.hasValue()) {
            return null;
        }
        final T value = node.getValue();
        node.setSize(node.getSize() - 1);
        node.removeValue();
        index = key.length() - 1;
        while(!stack.isEmpty()) {
            final char c = getChar(key, index);
            node = stack.pop();

            if(node.getNext(c).isEmpty()) {
                node.removeNext(c);
            }
            node.setSize(node.getSize() - 1);
            index--;
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

        boolean isEmpty();

        void setSize(int size);

        int getSize();

        void setNext(char c, N next);

        N getNext(char c);

        void removeNext(char c);

        void setValue(T value);

        T getValue();

        boolean hasValue();

        void removeValue();
    }
}
