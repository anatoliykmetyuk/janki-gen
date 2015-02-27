scalaVersion := "2.11.5"
sbtVersion   := "0.13.7"

lazy val jentities = (project in file("japanese-entity-api"))
  .settings(
    initialCommands := """
      |import jentities._
    """.stripMargin
  )