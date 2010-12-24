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


/**
 * A {@link KeyAnalyzer} for {@link Integer}s
 */
public class IntegerKeyAnalyzer extends AbstractKeyAnalyzer<Integer> {
    
    private static final long serialVersionUID = 4928508653722068982L;
    
    /**
     * A singleton instance of {@link IntegerKeyAnalyzer}
     */
    public static final IntegerKeyAnalyzer INSTANCE = new IntegerKeyAnalyzer();
    
    /**
     * The length of an {@link Integer} in bits
     */
    public static final int LENGTH = Integer.SIZE;
    
    /**
     * A bit mask where the first bit is 1 and the others are zero
     */
    private static final int MSB = 0x80000000;
    
    /**
     * Returns a bit mask where the given bit is set
     */
    private static int mask(int bit) {
        return MSB >>> bit;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int bitsPerElement() {
        return 1;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int lengthInBits(Integer key) {
        return LENGTH;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isBitSet(Integer key, int bitIndex, int lengthInBits) {
        return (key & mask(bitIndex)) != 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int bitIndex(Integer key, int offsetInBits, int lengthInBits, 
            Integer other, int otherOffsetInBits, int otherLengthInBits) {
        
        if (offsetInBits != 0 || otherOffsetInBits != 0) {
            throw new IllegalArgumentException("offsetInBits=" + offsetInBits 
                    + ", otherOffsetInBits=" + otherOffsetInBits);
        }
        
        int keyValue = key.intValue();
        if (keyValue == 0) {
            return NULL_BIT_KEY;
        }

        int otherValue = (other != null ? other.intValue() : 0);
        
        if (keyValue != otherValue) {
            int xorValue = keyValue ^ otherValue;
            for (int i = 0; i < LENGTH; i++) {
                if ((xorValue & mask(i)) != 0) {
                    return i;
                }
            }
        }
        
        return KeyAnalyzer.EQUAL_BIT_KEY;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPrefix(Integer prefix, int offsetInBits, 
            int lengthInBits, Integer key) {
        
        int value1 = (prefix.intValue() << offsetInBits);
        int value2 = key.intValue();
        
        int mask = 0;
        for (int i = 0; i < lengthInBits; i++) {
            mask |= (0x1 << i);
        }
        
        return (value1 & mask) == (value2 & mask);
    }
}