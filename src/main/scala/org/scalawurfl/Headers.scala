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

package org.scalawurfl

class Headers(private val delegate : Map[String, Seq[String]]) {

  def this(xs: scala.Tuple2[String, Seq[String]]*) {
    this(Map(xs.map(a=>(a._1.toLowerCase->a._2)):_*))
  }

  def apply(key: String) = get(key)

  def get(key: String): Seq[String] = delegate.get(key.toLowerCase).getOrElse(Seq.empty)

  def contains(key: String): Boolean = delegate.contains(key.toLowerCase)

  def size: Int = delegate.size

  def userAgent: Option[String] = get("user-agent").headOption

  def accept: Option[String] = get("accept").headOption

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

  def apply(xs: (String, Seq[String])*) = new Headers(xs:_*)

  def userAgent(ua: String) = new Headers("User-Agent"->Seq(ua))

}