import sbt._
import Keys._
import java.net.URL

object ScalaWurfl extends Build {
   
   val project = (Project("scala-wurfl", file(".")) settings(
     organization := "org.filippodeluca.wurfl",
     name := "scala-wurfl",
     version := "1.1-SNAPSHOT",
     scalaVersion := "2.10.0",
     crossScalaVersions := Seq("2.8.2", "2.9.2", "2.10.0"),
     licenses := Seq("Apache License, Version 2.0"->new URL("http://www.apache.org/licenses/LICENSE-2.0.html")),
     libraryDependencies ++= dependencies,
     autoCompilerPlugins := true
   ) settings(publishSettings:_*))
   
   def publishSettings: Seq[Setting[_]] = Seq(
     // If we want on maven central, we need to be in maven style.
     publishMavenStyle := true,
     publishArtifact in Test := false,
     // The Nexus repo we're publishing to.
     publishTo <<= version { (v: String) =>
       val nexus = "https://oss.sonatype.org/"
       if (v.trim.endsWith("SNAPSHOT")) Some("snapshots" at nexus + "content/repositories/snapshots") 
       else                             Some("releases"  at nexus + "service/local/staging/deploy/maven2")
     },
     // Maven central cannot allow other repos.  We're ok here because the artifacts we
     // we use externally are *optional* dependencies.
     pomIncludeRepository := { x => false },
     // Maven central wants some extra metadata to keep things 'clean'.
     pomExtra := (
 	    <licenses>
 		    <license>
 			    <name>Apache License, Version 2.0</name>
 			    <url>http://www.apache.org/licenses/LICENSE-2.0.html</url>
 			    <distribution>repo</distribution>
 		    </license>
 	    </licenses>
       <scm>
         <url>git@github.com:filosganga/scala-wurfl.git</url>
         <connection>scm:git:git@github.com:filosganga/scala-wurfl.git</connection>
       </scm>
       <developers>
         <developer>
           <id>filosganga</id>
           <name>Filippo De Luca</name>
           <url>http://filippodeluca.com</url>
         </developer>
         <developer>
           <id>fantayeneh</id>
           <name>Fantayeneh Asres Gizaw</name>
           <url>http://fantayeneh.github.com</url>
         </developer>
       </developers>)
   )
   
   def dependencies = Seq(
     "com.typesafe" % "config" % "1.0.0",
     "com.jsuereth" %% "scala-arm" % "1.3",

     "org.specs2" %% "specs2" % "1.13" % "test",
     "org.mockito" % "mockito-all" % "1.9.0" % "test"
   )
   
   
}