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

import collection.{Seq, mutable}
import util.control.Breaks

class PatriciaTrie[A, B](implicit keySupport: KeySupport[A]) extends Trie[A, B] {

  case class Node(msd: Int, key: Option[A], var value: Option[B], var left: Node = null, var right: Node = null) {

    def put(pmsd: Int, pkey: A, pvalue: B, parent: Node): Node = {
      if (this.msd >= pmsd || this.msd <= parent.msd) {
        val node = new Node(pmsd, Some(pkey), Some(pvalue))
        node.left = if (keySupport.isSet(pkey, pmsd)) this else node
        node.right = if (keySupport.isSet(pkey, pmsd)) node else this
        node
      }
      else {
        if (!keySupport.isSet(pkey, msd)) {
          left = left.put(pmsd, pkey, pvalue, this)
        }
        else {
          right = right.put(pmsd, pkey, pvalue, this)
        }
        this
      }
    }

    def foreach[U](fmsd: Int)(f: ((A, B)) => U): Unit = {
      if (this.msd > fmsd) {
        (key -> value) match {
          case (Some(a), Some(b)) => f(a -> b)
          case _ =>
        }

        left.foreach(this.msd)(f)
        right.foreach(this.msd)(f)
      }
    }

    def foldLeft[Z](z: Z, fmsd: Int)(op: (Z, (A,B)) => Z): Z = {
      var result = z
      this.foreach(fmsd)(x => result = op(result, x))
      result
    }
  }

  var root: Node = new Node(-1, None, None)
  root.left = root
  root.right = root

  def iterator: Iterator[(A, B)] = toSeq.iterator

  def get(key: A): Option[B] = searchNode(key) match {
    case Node(_, Some(k), v, _, _) if k == key => v
    case _ => None
  }

  def -=(key: A): this.type = get(key) match {
    case Some(v) => {
      retain((k, v) => k != key)
    }
    case _ => this
  }


  def +=(kv: (A, B)): this.type = {

    val (key, value) = kv

    searchNode(key) match {
      case n: Node if n.key.exists(_==key) => {
        n.value = Some(value)
      }
      case n: Node => {

        val msd = Iterator.from(0).indexWhere{i=>
          keySupport.isSet(key, i) != keySupport.isSet(n.key, i)
        }

        root.left = root.left.put(msd, key, value, root)
      }
    }

    this
  }


  private def searchNode(key: A): Node = {

    var prev = root
    var curr = root.left

    while(prev.msd < curr.msd) {
      prev = curr
      curr = if(!keySupport.isSet(key, curr.msd)) curr.left else curr.right
    }

    curr
  }


  def search(key: A): Option[B] = {

    searchNode(key).value
  }


  def searchCandidates(key: A): Seq[(A,B)] = {
    val nearest = searchNode(key)
    nearest.foldLeft(mutable.ArrayBuffer.empty[(A,B)], -1){(s,item)=>
      s += item
    }.toSeq
  }

  override def retain(p: (A, B) => Boolean): this.type = {

    val toRetain = toSeq.filter(x => p(x._1, x._2))
    clear()
    this ++= toRetain

  }

  override def clear() {
    root = new Node(-1, None, None)
    root.left = root
    root.right = root
  }

  override def toSeq: Seq[(A, B)] = {
    (mutable.ArrayBuffer.empty[(A, B)] /: this){(b,i)=>
      b += i
    }.toSeq
  }

  override def foreach[U](f: ((A, B)) => U) {
    root.left.foreach(-1)(f)
  }

}