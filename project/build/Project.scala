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

import sbt._
import java.net.URL
import java.util.jar.Attributes.Name._

class SwurflProject(info: ProjectInfo) extends DefaultProject(info) {


//  lazy val projectUrl                 = propertyOptional[URL](new URL("http://github.com/ff-dev/swurfl"), true)
//  lazy val projectInceptionyear       = propertyOptional[Int](2011, true)
//  lazy val projectOrganizationName    = propertyOptional[String](organization, true)
//  lazy val projectOrganizationUrl     = propertyOptional[URL](new URL("http://ffdev.org"), true)
//
//  lazy val projectLicenseName         = propertyOptional[String]("GPLv3", true)
//  lazy val projectLicenseUrl          = propertyOptional[URL](new URL("http://www.gnu.org/licenses/gpl-3.0.txt"), true)
//  lazy val projectLicenseDistribution = propertyOptional[String]("repo", true)


  override def packageSrcJar= defaultJarPath("-sources.jar")
  val sourceArtifact = Artifact.sources(artifactID)

  override def packageDocsJar = defaultJarPath("-javadoc.jar")
  val docsArtifact = Artifact.javadoc(artifactID)

  override def packageToPublishActions = super.packageToPublishActions ++ Seq(packageSrc, packageDocs)


  override def managedStyle = ManagedStyle.Maven
  // Set up publish repository (the tuple avoids SBT's ReflectiveRepositories detection)
  private lazy val snapshotPublishRepo = ("Sonatype OSS Maven Repository Snapshots" -> "https://oss.sonatype.org/content/repositories/snapshots/")
  private lazy val releasePublishRepo  = ("Sonatype OSS Maven Repository Releases"  -> "https://oss.sonatype.org/content/repositories/maven2/")

  val publishTo =
    if (version.toString.endsWith("-SNAPSHOT")) snapshotPublishRepo._1 at snapshotPublishRepo._2
    else releasePublishRepo._1 at releasePublishRepo._2

  Credentials(Path.userHome / ".ivy2" / ".credentials", log)



//  override def pomExtra =
//  <licenses>
//    <license>
//      <name>GPLv3</name>
//      <url>http://www.gnu.org/licenses/gpl-3.0.txt</url>
//      <distribution>repo</distribution>
//    </license>
//  </licenses>



  val specs = "org.scala-tools.testing" %% "specs" % "1.6.7" % "test"
  val mockito = "org.mockito" % "mockito-all" % "1.8.5" % "test"

}

