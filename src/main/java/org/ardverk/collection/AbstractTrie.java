/*
 * Copyright 2005-2009 Roger Kapsi, Sam Berlin
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package org.ardverk.collection;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Map;

/**
 * This class provides some basic {@link Trie} functionality and 
 * utility methods for actual {@link Trie} implementations.
 */
abstract class AbstractTrie<K, V> extends AbstractMap<K, V> 
        implements Trie<K, V>, Serializable {
    
    private static final long serialVersionUID = 5826987063535505652L;
    
    /**
     * The {@link KeyAnalyzer} that's being used to build the 
     * PATRICIA {@link Trie}
     */
    protected final KeyAnalyzer<? super K> keyAnalyzer;
    
    /** 
     * Constructs a new {@link Trie} using the given {@link KeyAnalyzer} 
     */
    public AbstractTrie(KeyAnalyzer<? super K> keyAnalyzer) {
        if (keyAnalyzer == null) {
            throw new NullPointerException("keyAnalyzer");
        }
        
        this.keyAnalyzer = keyAnalyzer;
    }
    
    /**
     * Returns the {@link KeyAnalyzer} that constructed the {@link Trie}.
     */
    public KeyAnalyzer<? super K> getKeyAnalyzer() {
        return keyAnalyzer;
    }
    
    /**
     * {@inheritDoc}
     */
    public K selectKey(K key) {
        Map.Entry<K, V> entry = select(key);
        if (entry == null) {
            return null;
        }
        return entry.getKey();
    }
    
    /**
     * {@inheritDoc}
     */
    public V selectValue(K key) {
        Map.Entry<K, V> entry = select(key);
        if (entry == null) {
            return null;
        }
        return entry.getValue();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("Trie[").append(size()).append("]={\n");
        for (Map.Entry<K, V> entry : entrySet()) {
            buffer.append("  ").append(entry).append("\n");
        }
        buffer.append("}\n");
        return buffer.toString();
    }
    
    /**
     * A utility method to cast keys. It actually doesn't
     * cast anything. It's just fooling the compiler!
     */
    @SuppressWarnings("unchecked")
    final K castKey(Object key) {
        return (K)key;
    }
    
    /**
     * Returns the length of the given key in bits
     * 
     * @see KeyAnalyzer#lengthInBits(Object)
     */
    final int lengthInBits(K key) {
        if (key == null) {
            return 0;
        }
        
        return keyAnalyzer.lengthInBits(key);
    }
    
    /**
     * Returns the number of bits per element in the key
     * 
     * @see KeyAnalyzer#bitsPerElement()
     */
    final int bitsPerElement() {
        return keyAnalyzer.bitsPerElement();
    }
    
    /**
     * Returns whether or not the given bit on the 
     * key is set or false if the key is null.
     * 
     * @see KeyAnalyzer#isBitSet(Object, int, int)
     */
    final boolean isBitSet(K key, int bitIndex, int lengthInBits) {
        if (key == null) { // root's might be null!
            return false;
        }
        return keyAnalyzer.isBitSet(key, bitIndex, lengthInBits);
    }
    
    /**
     * Utility method for calling {@link KeyAnalyzer#bitIndex(Object, int, int, Object, int, int)}
     */
    final int bitIndex(K key, K foundKey) {
        return keyAnalyzer.bitIndex(key, 0, lengthInBits(key), 
                foundKey, 0, lengthInBits(foundKey));
    }
    
    /**
     * An utility method for calling {@link KeyAnalyzer#compare(Object, Object)}
     */
    final boolean compareKeys(K key, K other) {
        if (key == null) {
            return (other == null);
        } else if (other == null) {
            return (key == null);
        }
        
        return keyAnalyzer.compare(key, other) == 0;
    }
    
    /**
     * A basic implementation of {@link Entry}
     */
    abstract static class BasicEntry<K, V> implements Map.Entry<K, V>, Serializable {
        
        private static final long serialVersionUID = -944364551314110330L;

        protected K key;
        
        protected V value;
        
        private final int hashCode;
        
        public BasicEntry(K key) {
            this.key = key;
            
            this.hashCode = (key != null ? key.hashCode() : 0);
        }
        
        public BasicEntry(K key, V value) {
            this.key = key;
            this.value = value;
            
            this.hashCode = (key != null ? key.hashCode() : 0)
                    ^ (value != null ? value.hashCode() : 0);
        }
        
        /**
         * Replaces the current key and value with the provided
         * key &amp; value
         */
        public V setKeyValue(K key, V value) {
            this.key = key;
            return setValue(value);
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public K getKey() {
            return key;
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public V getValue() {
            return value;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public V setValue(V value) {
            V previous = this.value;
            this.value = value;
            return previous;
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            return hashCode;
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            } else if (!(o instanceof Map.Entry)) {
                return false;
            }
            
            Map.Entry<?, ?> other = (Map.Entry<?, ?>)o;
            if (Tries.compare(key, other.getKey()) 
                    && Tries.compare(value, other.getValue())) {
                return true;
            }
            return false;
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return key + "=" + value;
        }
    }
}
