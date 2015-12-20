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
 * A character array backed Trie node.
 *
 * @author Jakub Narloch
 */
class CharArrayTrieNode<T> implements AbstractTrie.TrieNode<T, CharArrayTrieNode<T>> {

    /**
     * The number of distinct children - resembles the 2 byte char distinct values.
     */
    private static final int R = 0xffff;

    /**
     * The array of child nodes.
     */
    private CharArrayTrieNode<T>[] next;

    /**
     * The node value.
     */
    private T value;

    /**
     * Creates new instance of {@link CharArrayTrieNode} class.
     */
    @SuppressWarnings("unchecked")
    public CharArrayTrieNode() {

        this.next = (CharArrayTrieNode<T>[]) new CharArrayTrieNode[R];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNext(char c, CharArrayTrieNode<T> next) {

        this.next[getIndex(c)] = next;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CharArrayTrieNode<T> getNext(char c) {

        return this.next[getIndex(c)];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(T value) {

        this.value = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T getValue() {

        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasValue() {

        return getValue() != null;
    }

    /**
     * Retrieves the code point of the given character.
     *
     * @param c the character
     * @return the character code point
     */
    private int getIndex(char c) {

        return c;
    }
}
