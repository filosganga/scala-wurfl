package org.filippodeluca.swurfl.matching

/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 23/12/10
 * Time: 13.54
 * To change this template use File | Settings | File Templates.
 */

class RadixTreeNode[A](var key: String = "", var value: Option[A] = None) {

  var children: Map[Char, RadixTree[A]] = Map()

  def get(key: String): Option[A] = {

    if(key.isEmpty)
      value
    else
      children.get(key(0)).flatMap(_.get(key.substring(this.key.length)))
  }

  def add(key: String, value: A) {

    if(key.isEmpty) {
      this.value = Some(value)
    }
    else {
      children.get(key(0)) match {
        case None => children + (key(0)->new RadixTreeNode(key))
        case child => {
          val node = child.get

            //


        }
      }

      //children.get(key(0)).foreach((child) => child.add(key.substring(child.key.lenght), value));
    }

  }

  def candidates: Map[String, A] = {

    var candidates: Map[String, A] = Map()
    if (value.isDefined)
      candidates += key -> value.get

    candidates

//    children.foldLeft(candidates) {
//      (cs, node) => cs ++ node.candidates
//    }
  }


}