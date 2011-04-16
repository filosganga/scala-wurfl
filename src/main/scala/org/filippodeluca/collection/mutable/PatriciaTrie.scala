package org.filippodeluca.swurfl.matching.trie


import collection.{Seq, Iterator, mutable}
import collection.generic.MutableMapFactory

/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 16/01/11
 * Time: 12.37
 * To change this template use File | Settings | File Templates.
 */

class PatriciaTrie[B] extends Trie[String, B] with mutable.MapLike[String, B, PatriciaTrie[B]] {

  private val root = new PatriciaNode[B]("", None)

  private var _size = 0

  override def empty: PatriciaTrie[B] = PatriciaTrie.empty[B]

  override def size: Int = _size

  def get(key: String): Option[B] = getR(key, root)

  private def getR(key: String, node: PatriciaNode[B]): Option[B] = {

    if(key == node.key) {
      node.value
    }
    else if(key.startsWith(node.key)) {
      val keyOffset = key.substring(node.key.length)
      node.children.get(keyOffset(0)).flatMap(getR(keyOffset, _))
    }
    else {
      None
    }
  }

  def bestMatch(guess: String): (String, B) = {
    val node = bestMatchR(guess, root)
    node.key->node.value
  }

  private def bestMatchR(guess: String, node: PatriciaNode[B]): PatriciaNode[B] = {

    if(guess == node.key || node.key.startsWith(key)) {
      node
    }
    else if(key.startsWith(node.key)) {
      val keyOffset = key.substring(node.key.length)
      node.children.get(keyOffset(0)).flatMap(getR(keyOffset, _))
    }
  }

  def bestMatches(guess: String): Iterable[(String, B)] = toSeq(bestMatchR(guess, root))

  def bestMatches(guess: String, n: Int): Iterable[(String, B)] = toSeq(bestMatchR(guess, root))


  def +=(kv: (String, B)): this.type = {

    val key = kv._1
    val value = kv._2

    if(key == null || key.isEmpty) {
      root.value = Some(value)
    }
    else {
      root.children += key(0)->putR(key, value, root.children.getOrElse(key(0), PatriciaNode(key, Some(value))))
    }
    _size = _size + 1

    this
  }

  private def putR(key: String, value: B, node: PatriciaNode[B]): PatriciaNode[B] = {

    if(key == node.key) {
      node.value = Some(value)
      node
    }
    else if(key.startsWith(node.key)) {
      val subKey = key.substring(node.key.length)
      node.children += (subKey(0)->putR(subKey, value, node.children.getOrElse(subKey(0), PatriciaNode(subKey, Some(value)))))
      node
    }
    else {

      val insertingKey = PatriciaTrie.MCP(key, node.key)
      val insertingNode = PatriciaNode[B](insertingKey, None)

      val thisKey = node.key.substring(insertingKey.length)
      insertingNode.children += (thisKey(0)->node.withKey(thisKey))

      putR(key, value, insertingNode)
    }
  }


  override def toSeq: Seq[(String, B)] = toSeq(root, "")

  def iterator: Iterator[(String, B)] = toSeq(root, "").iterator

  private def toSeq(node: PatriciaNode[B], prefix: String): Seq[(String, B)] = {

    var results = node.value.foldLeft(Seq[(String, B)]()){
      (r,b)=>r :+ ((prefix + node.key)->b)
    }

    results = node.children.values.foldLeft(results){(r,n)=>
      r ++ toSeq(n, prefix + node.key)
    }

    results
  }

  def -=(key: String): this.type = throw new UnsupportedOperationException()

  override def clear() {
    _size = 0
    root.children.clear()
  }

  private class PatriciaNode[B](val key: String, var value: Option[B]) {

    val children: mutable.Map[Char, PatriciaNode[B]] = mutable.Map.empty

    def withKey(newKey: String): PatriciaNode[B] = {
      val node = PatriciaNode(newKey, value)
      node.children ++= children
      node
    }

  }

  private object PatriciaNode {

    def apply[B](key:String, value: Option[B]) = new PatriciaNode[B](key, value)

  }

}

object PatriciaTrie /* extends MutableMapFactory[PatriciaTrie] */ {

  //implicit def canBuildFrom[String, B]: collection.generic.CanBuildFrom[Coll, (String, B), PatriciaTrie[String, B]] = new MapCanBuildFrom[String, B]

  def empty[B] = new PatriciaTrie[B]

  private def MCP(one: String, others: String*): String = {

    others.foldLeft(one){(r,current)=>

      if(current.startsWith(r)) {
        r
      }
      else if(r.startsWith(current)) {
        current
      }
      else {
        var i=0
        while(r(i)==current(i))i = i + 1
        r.take(i)
      }
    }
  }

}
