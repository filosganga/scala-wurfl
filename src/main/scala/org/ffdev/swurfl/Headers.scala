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

package org.ffdev.swurfl

/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 20/12/10
 * Time: 11.19
 * To change this template use File | Settings | File Templates.
 */

class Headers(private val delegate : Map[String, Seq[String]]) {

  def this(xs: scala.Tuple2[String, Seq[String]]*) {
    this(Map(xs.map(a=>(a._1.toLowerCase->a._2)):_*))
  }

  def apply(key: String) = get(key)

  def get(key: String): Seq[String] = delegate(key.toLowerCase)

  def contains(key: String): Boolean = delegate.contains(key.toLowerCase)

  def size: Int = delegate.size

  def userAgent: Option[String] = delegate.get("user-agent").flatMap(_.headOption)

  def accept: Option[String] = delegate.get("accept").flatMap(_.headOption)

  def withHeader(name: String, value: String): Headers = {

    new Headers(delegate.updated(name.toLowerCase, delegate.getOrElse(name.toLowerCase, Seq[String]()) :+ value))
  }

  def withUserAgent(ua: String): Headers = withHeader("user-agent", ua)

  override def equals(o: Any): Boolean = {

    if(o.isInstanceOf[Headers]) {
      delegate == o.asInstanceOf[Headers].delegate
    }
    else {
      false
    }

  }

  override def hashCode: Int = {
    getClass.hashCode + delegate.hashCode
  }

}

object Headers {

  def apply(xs: scala.Tuple2[String, Seq[String]]*) = new Headers(xs:_*)

  def fromUserAgent(ua: String) = new Headers("User-Agent"->List(ua))

//  // TODO Move this to another package
//  def apply(request : HttpServletRequest) = {
//
//    var headers : Map[String, Seq[String]] = Map();
//
//    request.getHeaderNames.asScala.asInstanceOf[Iterable[String]].foreach((name : String) => {
//
//      val values : Seq[String] = request.getHeaders(name).asInstanceOf[java.util.List[String]].asScala.toList
//      headers += name.toLowerCase -> values
//
//    })
//
//    new Headers(headers)
//  }
}