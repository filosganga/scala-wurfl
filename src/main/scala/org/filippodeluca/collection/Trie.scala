package org.filippodeluca.collection

import collection.mutable.Map
import collection.MapLike

/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 13/01/11
 * Time: 11.49
 * To change this template use File | Settings | File Templates.
 */
trait Trie[A, B] extends Map[A, B] with MapLike[A, B, Map[A, B]] {

  def bestMatch(guess: A): (A, B)

  def bestMatches(guess: A): Iterable[(A, B)]

  def bestMatches(guess: A, n: Int): Iterable[(A, B)]

}