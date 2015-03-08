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
        |import jentities.util.LanguageDatabase._
        |val kanjidic2file = "/Users/anatolii/Projects/janki-gen/japanese-entity-api/src/main/resources/language-data/kanjidic2.xml"
      """.stripMargin,

      libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.0.3"
    )

lazy val jorders = (project in file("j-orders"))
    .dependsOn(jentities)
    .settings(commonSettings: _*)
    .settings(
      initialCommands := """
        |import jorders._
        |import jentities._
        |import Sample._
        |import Constants._
        |import Fields._
        |import jorders.output._
      """.stripMargin
    )

lazy val textDecompose = (project in file("j-text"))
  .dependsOn(jentities, jorders)
  .settings(commonSettings: _*)
  .settings(
    resolvers += "Atilika Open Source repository" at "http://www.atilika.org/nexus/content/repositories/atilika",
    libraryDependencies += "org.atilika.kuromoji" % "kuromoji" % "0.7.7",

    initialCommands := """
      |import jtext._
      |import jentities._
      |import jorders._
      |import Sample._
    """.stripMargin
  )

// lazy val webapp = (project in file("janki-webapp"))
//   .dependsOn(jentities, jorders, textDecompose)
//   .settings(commonSettings: _*)