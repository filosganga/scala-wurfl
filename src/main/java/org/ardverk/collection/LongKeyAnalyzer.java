/*
 * Copyright 2005-2009 Roger Kapsi
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
 * A {@link KeyAnalyzer} for {@link Long}s
 */
public class LongKeyAnalyzer extends AbstractKeyAnalyzer<Long> {
    
    private static final long serialVersionUID = -4119639247588227409L;

    /**
     * A singleton instance of {@link LongKeyAnalyzer}
     */
    public static final LongKeyAnalyzer INSTANCE = new LongKeyAnalyzer();
    
    /**
     * The length of an {@link Long} in bits
     */
    public static final int LENGTH = Long.SIZE;
    
    /**
     * A bit mask where the first bit is 1 and the others are zero
     */
    private static final long MSB = 0x8000000000000000L;
    
    /**
     * Returns a bit mask where the given bit is set
     */
    private static long mask(int bit) {
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
    public int lengthInBits(Long key) {
        return LENGTH;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isBitSet(Long key, int bitIndex, int lengthInBits) {
        return (key & mask(bitIndex)) != 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int bitIndex(Long key, int offsetInBits, int lengthInBits, 
            Long other, int otherOffsetInBits, int otherLengthInBits) {
        
        if (offsetInBits != 0 || otherOffsetInBits != 0) {
            throw new IllegalArgumentException("offsetInBits=" + offsetInBits 
                    + ", otherOffsetInBits=" + otherOffsetInBits);
        }
        
        long keyValue = key.longValue();
        if (keyValue == 0L) {
            return NULL_BIT_KEY;
        }

        long otherValue = (other != null ? other.longValue() : 0L);
        
        if (keyValue != otherValue) {
            long xorValue = keyValue ^ otherValue;
            for (int i = 0; i < LENGTH; i++) {
                if ((xorValue & mask(i)) != 0L) {
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
    public boolean isPrefix(Long prefix, int offsetInBits, 
            int lengthInBits, Long key) {
        
        long value1 = (prefix.longValue() << offsetInBits);
        long value2 = key.longValue();
        
        long mask = 0L;
        for (int i = 0; i < lengthInBits; i++) {
            mask |= (0x1L << i);
        }
        
        return (value1 & mask) == (value2 & mask);
    }
}