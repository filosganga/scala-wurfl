/*
 * Copyright 2012.
 *   Filippo De Luca <me@filippodeluca.com>
 *   Fantayeneh Asres Gizaw <fantayeneh@gmail.com>
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

package org.filippodeluca.wurfl.matching.trie

import collection.immutable.Map
import scala.Iterator
import collection.generic.{CanBuildFrom, ImmutableMapFactory}

trait Trie[+B] {

  def nearest(key: String): Trie[B]

  def get(key: String): Option[B]

  def apply(key: String) = get(key).get

  def + [B1 >: B](kv: (String, B1)): Trie[B1]

  def updated [B1 >: B](key: String, value: B1) = this + (key, value)

  def size: Int
}

object EmptyTrie extends Trie[Nothing] with Serializable {

  def size: Int = 0

  def get(key: String): Option[Nothing] = None

  def iterator: Iterator[(String, Nothing)] = Iterator.empty

  def + [B1](kv: (String, B1)): Trie[B1] = kv match {
    case (k,v) => new NonEmptyTrie(k, Some(v), Map.empty[Char, Trie[B1]])
  }

  def nearest(key: String) = this
}

case class NonEmptyTrie[+B](key: String, value: Option[B], children: Map[Char, Trie[B]] = Map.empty) extends Trie[B] {

  def get(k: String) = getTrie(k).flatMap(_.value)

  private def getTrie(k: String) = if (k.equals(key)) {
    Some(this)
  } else if(k.contains(key)) {
    // TODO call get recursively to the right child
    None
  } else {
    None
  }

  def +[B1 >: B](kv: (String, B1)) = kv match {
    case (k,v) if(k == key) => {
      new NonEmptyTrie(k, Some(v), children)
    }
    case (k,v) if(k.contains(key)) => {
      val newKey = k.substring(key.length)
      children(newKey.charAt(0)) + (newKey->v)
    }
    case (k, _) if(key.contains(k)) => {
      // TODO if > key call the children
      this
    }
  }

  def nearest(key: String) = this

  def iterator = new Iterator[B] {
    def hasNext = false
    def next() = value.get
  }

  def size = 0
}
