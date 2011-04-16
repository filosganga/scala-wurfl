import sbt._

class SwurflProject(info: ProjectInfo) extends DefaultProject(info) {

  val scalajCollection = "org.scalaj" %% "scalaj-collection" % "1.0.1"


  val specs = "org.scala-tools.testing" %% "specs" % "1.6.7" % "test"
  val mockito = "org.mockito" % "mockito-all" % "1.8.5" % "test"

}

