/*
 * Copyright 2011. ffdev.org
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

package org.ffdev.swurfl.repository

/**
 * Created by IntelliJ IDEA.
 * User: filippodeluca
 * Date: 26/12/10
 * Time: 19.17
 * To change this template use File | Settings | File Templates.
 */

import org.ffdev.swurfl.Device

trait Repository extends Traversable[Device] {

  def roots: Traversable[Device]

  def get(id: String): Option[Device]

  def apply(id: String): Device = get(id).get

  def patch(patches: Traversable[DeviceEntry]*): Repository

}

