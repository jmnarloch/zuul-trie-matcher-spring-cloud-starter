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

/**
 * A character array backed Trie tree.
 *
 * @author Jakub Narloch
 */
public class CharArrayTrie<T> extends AbstractTrie<T, CharArrayTrieNode<T>> {

    /**
     * Creates new instance of {@link CharArrayTrie}.
     */
    public CharArrayTrie() {
        super(new TrieNodeFactory<T, CharArrayTrieNode<T>>() {
            @Override
            public CharArrayTrieNode<T> createNode() {
                return new CharArrayTrieNode<T>();
            }
        });
    }
}
