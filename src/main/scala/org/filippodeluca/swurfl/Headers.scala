package org.filippodeluca.swurfl

import javax.servlet.http.HttpServletRequest

import scalaj.collection.Imports._

/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 20/12/10
 * Time: 11.19
 * To change this template use File | Settings | File Templates.
 */

class Headers(private val delegate : Map[String, Seq[String]]) {

  def apply(key : String) = get(key)

  def get(key : String) : Seq[String] = delegate(key.toLowerCase)

  def size : Int = delegate.size

  def withHeader(key : String, value : String) : Headers = {

    var values = delegate.getOrElse(key, List());
    value :+ values

    new Headers(delegate.updated(key.toLowerCase, values))
  }

}

object Headers {

  def apply(headers : Map[String, Seq[String]]) = new Headers(headers)

  def apply(request : HttpServletRequest) = {

    var headers : Map[String, Seq[String]] = Map();

    request.getHeaderNames.asScala.asInstanceOf[Iterable[String]].foreach((name : String) => {

      val values : Seq[String] = request.getHeaders(name).asInstanceOf[java.util.List[String]].asScala.toList
      headers += name.toLowerCase -> values

    })

    new Headers(headers)
  }




}