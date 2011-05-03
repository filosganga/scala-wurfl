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

import sbt._

class SwurflProject(info: ProjectInfo) extends DefaultProject(info) {

  val scalajCollection = "org.scalaj" %% "scalaj-collection" % "1.0.1"


  val specs = "org.scala-tools.testing" %% "specs" % "1.6.7" % "test"
  val mockito = "org.mockito" % "mockito-all" % "1.8.5" % "test"

  //override def compileAction = super.compileAction dependsOn formatLicenseHeaders

}

