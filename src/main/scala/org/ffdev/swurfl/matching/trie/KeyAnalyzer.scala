/*
 * Copyright 2011. ffdev.org
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