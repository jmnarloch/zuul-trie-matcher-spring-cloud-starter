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
 * A convenient class for instantiating the Trie tries.
 *
 * @author Jakub Narloch
 */
public final class Tries {

    /**
     * Creates new instances of {@link Tries}.
     *
     * Private constructor prevents from instantation outside this class.
     */
    private Tries() {
        // empty constructor
    }

    /**
     * Creates new instance of {@link CharArrayTrie}.
     *
     * @param <T> the element type
     * @return the instance of {@link CharArrayTrie}
     */
    public static <T> CharArrayTrie<T> newCharArrayTrie() {
        return new CharArrayTrie<T>();
    }

    /**
     * Creates new instance of {@link CharHashMapTrie}.
     *
     * @param <T> the element type
     * @return the instance of {@link CharHashMapTrie}
     */
    public static <T> CharHashMapTrie<T> newCharMapTrie() {
        return new CharHashMapTrie<T>();
    }

    /**
     * Creates new instance of {@link HashMapTrie}.
     *
     * @param <T> the element type
     * @return the instance of {@link HashMapTrie}
     */
    public static <T> HashMapTrie<T> newHashMapTrie() {
        return new HashMapTrie<T>();
    }
}
