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

package org.ffdev.swurfl.repository

import org.ffdev.swurfl.Device

trait Repository extends Traversable[Device] {

  def roots: Traversable[Device]

  def get(id: String): Option[Device]

  def apply(id: String): Device = get(id).get

  def patch(patches: Traversable[DeviceEntry]*): Repository

}

