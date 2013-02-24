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

package org.scalawurfl.matching.trie

import collection.immutable.Map
import scala.Iterator
import collection.generic.{CanBuildFrom, ImmutableMapFactory}

trait Trie[+B] {

  def nearest(key: String): Trie[B]

  def get(key: String): Option[B]

  def apply(key: String) = get(key).get

  def iterator: Iterator[(String, B)]

  def + [B1 >: B](kv: (String, B1)): Trie[B1]

  def - [B1 >: B](kv: (String, B1)): Trie[B1]

  def updated [B1 >: B](key: String, value: B1) = this + (key, value)

  def size: Int

  def toMap: Map[String, B]
}

object Trie {

  def empty[B]: Trie[B] = EmptyTrie

  def apply[B](kvs: (String, B)*): Trie[B] = {
    kvs.foldLeft[Trie[B]](empty){(s,x)=>
      s + (x._1->x._2)
    }
  }
}

object EmptyTrie extends Trie[Nothing] with Serializable {

  def size: Int = 0

  def get(key: String): Option[Nothing] = None

  def iterator: Iterator[(String, Nothing)] = Iterator.empty

  def + [B](kv: (String, B)): Trie[B] = kv match {
    case (k,v) => new NonEmptyTrie(k, Some(v), Map.empty[Char, Trie[B]])
  }

  def - [B](kv: (String, B)) = this

  def nearest(key: String) = this

  def toMap: Map[String, Nothing] = Map.empty[String, Nothing]
}

case class NonEmptyTrie[+B](key: String, value: Option[B], children: Map[Char, Trie[B]] = Map.empty) extends Trie[B] {

  def get(k: String) = getTrie(k).flatMap(_.value)

  private def getTrie(k: String): Option[NonEmptyTrie[B]] = if (k == key) {
    Some(this)
  } else if(k.contains(key)) {
    children.get(k(key.length)) match {
      case Some(c: NonEmptyTrie[B]) => c.getTrie(k.substring(key.length))
      case _ => None
    }
  } else {
    None
  }

  def +[B1 >: B](kv: (String, B1)) = kv match {
    case (k,v) if(k == key) => {
      new NonEmptyTrie(k, Some(v), children)
    }
    case (k,v) if(k.contains(key)) => {
      val newKey = k.substring(key.length)
      new NonEmptyTrie(key, value, children + (newKey(0)-> addToChildren(newKey, v)))
    }
    case (k, v) if(key.contains(k)) => {
      val newKey = key.substring(k.length)
      new NonEmptyTrie[B1](k, Some(v), Map(newKey(0)->new NonEmptyTrie[B1](newKey, value, children)))
    }
    case (k, v) => {
      val newKey = longestCommonPart(k, key)
      val k1 = key.substring(newKey.length)
      val k2 = k.substring(newKey.length)
      new NonEmptyTrie[B1](newKey, None, Map(
        k1(0)->new NonEmptyTrie(k1, value, children),
        k2(0)->new NonEmptyTrie(k2, Some(v), Map.empty[Char, Trie[B1]])
      ))
    }
  }

  private def addToChildren[B1 >: B](k: String, v: B1): Trie[B1] = {
    children.get(k(0)) match {
      case Some(n) => n + (k->v)
      case _ => new NonEmptyTrie[B1](k, Some(v), Map.empty[Char, Trie[B]])

    }
  }

  // TODO need to find a faster implementation, maybe starting from back
  private def longestCommonPart(a: String, b: String) = {
    a.zip(b).takeWhile(Function.tupled(_ == _)).map(_._1).mkString
  }

  def - [B1 >: B](kv: (String, B1)): Trie[B1] = ???

  def nearest(k: String): Trie[B] = nearest(k, "")

  // TODO Refactor it
  protected def nearest(k: String, prefix: String): Trie[B] = if (k == key) {
    new NonEmptyTrie(prefix + key, value, children)
  } else if(k.contains(key)) {
    children.get(k(key.length)) match {
      case Some(c: NonEmptyTrie[B]) => {
        c.nearest(k.substring(key.length), prefix + key)
      }
      case _ => new NonEmptyTrie(prefix + key, value, children)
    }
  } else {
    new NonEmptyTrie(prefix + key, value, children)
  }


  def iterator = new Iterator[(String, B)]() {

    var current: Option[NonEmptyTrie[B]] = Some(NonEmptyTrie.this)

    def hasNext = current.isDefined

    def next() = current.map {
      case NonEmptyTrie(k,Some(v),_)=>k->v
    }.getOrElse(
      throw new NoSuchElementException
    )
  }

  def size = children.values.foldLeft(value.map(x=>1).getOrElse(0)){
    (s, x) => s + x.size
  }

  def toMap: Map[String, B] = {
    toMap("", Map.empty[String, B])
  }

  protected def toMap[B1 >: B](prefix: String, accumulator: Map[String, B1]): Map[String, B1] = {
    val map = value match {
      case Some(v) => accumulator + (prefix+key->v)
      case _ => accumulator
    }

    children.values.foldLeft(map){
      case (s, x: NonEmptyTrie[B1]) => x.toMap(prefix+key, s)
    }
  }


  override def equals(obj: Any): Boolean = obj match {
    case that: NonEmptyTrie[B] => that.toMap == toMap
    case _ => false
  }

  override def toString: String = {
    "NonEmpty[" + key + "->" + value + ", " + children.values.mkString(",") + "]"
  }
}
