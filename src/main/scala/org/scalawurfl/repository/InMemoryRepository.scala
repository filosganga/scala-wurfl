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

package org.scalawurfl.repository

import org.scalawurfl.Device


class InMemoryRepository(entries: Traversable[DeviceEntry], patches: Traversable[DeviceEntry]*) extends Repository {

  val entriesById = patchEntries(entries.map(e => (e.id->e)).toMap, patches)

  def roots: Traversable[Device] = entries.filter(_.isRoot).map(device(_))

  def get(key: String): Option[Device] = {
    entriesById.get(key).map(device(_))
  }

  def foreach[U](f: (Device) => U) {
    entries.foreach{entry =>
      f(device(entry))
    }
  }

  private def device(entry: DeviceEntry): Device = {

    new Device {

      def id = entry.id

      def userAgent = entry.userAgent

      def capability(name: String): Option[String] = entry.capabilities.get(name).orElse {
        ancestor.flatMap(a => a.capability(name))
      }

      def capabilities: Map[String, String] = {
        (entry.capabilities /: ancestor){(owned, inherit)=>
          inherit.capabilities ++ owned
        }
      }

      private def ancestor: Option[Device] = {
        entry.fallBackId.flatMap(get(_))
      }

      override def equals(obj: Any): Boolean = obj match {
        case that: Device => id == that.id
        case _ => false
      }

      override def toString: String = "Device[id: " + id + ", userAgent: " + userAgent + "]"
    }
  }

  def patch(patches: Traversable[DeviceEntry]*): InMemoryRepository = {
    new InMemoryRepository(entriesById.values, patches:_*)
  }

  private def patchEntries(sources: Map[String, DeviceEntry], patches: Seq[Traversable[DeviceEntry]]): Map[String, DeviceEntry] = {
    (sources /: patches){(s,p)=>
      patchEntries(s, p)
    }
  }

  private def patchEntries(sources: Map[String, DeviceEntry], patchers: Traversable[DeviceEntry]): Map[String, DeviceEntry] = {
    (sources /: patchers){(sources,patcher)=>
      sources ++ patchEntries(sources, patcher)
    }
  }

  private def patchEntries(sources: Map[String, DeviceEntry], patcher: DeviceEntry): Map[String, DeviceEntry] = {
    val patched = sources.get(patcher.id) match {
      case None => patcher
      case Some(source) => patchEntry(source, patcher)
    }

    sources + (patched.id->patched)
  }

  private def patchEntry(patching: DeviceEntry, patcher: DeviceEntry): DeviceEntry = {

    patching.ensuring(_.id == patcher.id)
    DeviceEntry(patcher.id, patcher.userAgent, patcher.fallBackId, patching.capabilities ++ patcher.capabilities)
  }

}