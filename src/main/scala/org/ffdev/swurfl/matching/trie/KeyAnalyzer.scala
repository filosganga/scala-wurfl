/*
 * Copyright (c) 2011.
 *   Fantayeneh Asres Gizaw <fantayeneh@gmail.com>
 *   Filippo De Luca <me@filippodeluca.com>
 *
 * This file is part of swurfl.
 *
 * swurfl is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * swurfl is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with swurfl. If not, see <http://www.gnu.org/licenses/>.
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