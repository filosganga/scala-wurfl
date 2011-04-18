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
 * Document... 
 *
 * @author: Filippo De Luca
 * @version: 1.0 16/04/11/04/2011
 */

case class DeviceEntry(val id: String, val userAgent: Option[String] = None, val fallBackId: Option[String] = None, val capabilities: Map[String, String] = Map.empty){
  def isRoot = fallBackId.isEmpty
}