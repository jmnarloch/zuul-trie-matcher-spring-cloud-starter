/**
 * Copyright (c) 2016 the original author or authors
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

/**
 * The base class that provides the common implementation for every trie node.
 *
 * @author Jakub Narloch
 */
public abstract class AbstractTrieNode<T, N extends AbstractTrieNode<T, N>> implements AbstractTrie.TrieNode<T, N> {

    /**
     * The node value.
     */
    private T value;

    /**
     * The total node size.
     */
    private int size;

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSize() {
        return size;
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
}
