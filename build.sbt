val scalaVer = "2.11.5"
val sbtVer   = "0.13.7"

scalaVersion := scalaVer
sbtVersion   := sbtVer

lazy val commonSettings = Seq(
  scalaVersion := scalaVer,
  sbtVersion   := sbtVer,

  libraryDependencies ++= Seq(
    "commons-io"         %  "commons-io"       % "2.4",
    "org.apache.commons" %  "commons-compress" % "1.9",
    "org.scalatest"      %% "scalatest"        % "2.2.4" % "test"
  )
)

lazy val jentities = (project in file("japanese-entity-api"))
  .settings(commonSettings: _*)
  .settings(
    initialCommands := """
      |import jentities._
    """.stripMargin
  )

lazy val jorders = (project in file("j-orders"))
  .dependsOn(jentities)
  .settings(commonSettings: _*)