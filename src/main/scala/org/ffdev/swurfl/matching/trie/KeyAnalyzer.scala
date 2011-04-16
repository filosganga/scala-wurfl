package org.ffdev.swurfl.matching.trie

import java.util.Comparator


trait KeyAnalyzer[K] extends Comparator[K] {

  def isSet(key: K, bitIndex: Int): Boolean

  def bitIndex(key: K, other: K): Int

}

object KeyAnalyzer {

    /* Returned by {@link #bitIndex(Object, int, int, Object, int, int)}
     * if key's bits are all 0
     */
    val NULL_KEY = -1

    /**
     * Returns true if bitIndex is a {@link KeyAnalyzer#NULL_BIT_KEY}
     */
    def isNullIndex(bitIndex: Int): Boolean = bitIndex == NULL_KEY

}