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

trait Repository extends Traversable[Device] {

  def roots: Traversable[Device]

  def get(id: String): Option[Device]

  def apply(id: String): Device = get(id).get

}

trait Patchable[A <: Repository] {
  self: A=>

  def patch(patches: Traversable[DeviceEntry]*): A
}

trait Reloadable[A <: Repository] {
  self: A=>

  def reload(main: Traversable[DeviceEntry], patches: Traversable[DeviceEntry]*): A
}

