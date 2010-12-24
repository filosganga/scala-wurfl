package org.filippodeluca.swurfl.matching

/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 23/12/10
 * Time: 04.36
 * To change this template use File | Settings | File Templates.
 */
abstract class RadixTree[A] {

  var root = new RadixTreeNode("", None)

  def add(key: String, value: Option[A]) {

  }

  def get(key: String): Option[A] = {

    None
  }

  def delete(key: String): Boolean

  def contains(key: String): Boolean

  def findPrefix(prefix: String, limit: Int): Set[A]

  def size: Int
}
