val scalaVer = "2.11.5"
val sbtVer   = "0.13.7"

scalaVersion := scalaVer
sbtVersion   := sbtVer

lazy val defaultPom = (
  <url>https://github.com/anatoliykmetyuk/janki-gen</url>
  <licenses>
    <license>
      <name>BSD-style</name>
      <url>http://www.opensource.org/licenses/bsd-license.php</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>git@github.com/anatoliykmetyuk/janki-gen.git</url>
    <connection>scm:git:git@github.com/anatoliykmetyuk/janki-gen.git</connection>
  </scm>
  <developers>
    <developer>
      <id>anatoliykmetyuk</id>
      <name>Anatoliy Kmetyuk</name>
    </developer>
  </developers>
)

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

lazy val commonSettings = Seq(
  scalaVersion := scalaVer,
  sbtVersion   := sbtVer,

  libraryDependencies ++= Seq(
    "commons-io"         %  "commons-io"       % "2.4",
    "org.apache.commons" %  "commons-compress" % "1.9",
    "org.scalatest"      %% "scalatest"        % "2.2.4" % "test"
  ),

  organization := "io.github.anki-japan",
  version      := "1.0",

  publishMavenStyle := true,

  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value)
      Some("snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("releases"  at nexus + "service/local/staging/deploy/maven2")
  },

  publishArtifact in Test := false,

  pomExtra := defaultPom
)

lazy val jentities = (project in file("japanese-entity-api"))
    .settings(commonSettings: _*)
    .settings(
      name := "jentities",

      fork in console := true,
      javaOptions in console += "-Xmx2G",

      initialCommands := """
        |import jentities._
        |import java.io._
        |import org.apache.commons.io._
        |import scala.collection.JavaConversions._
        |import org.atilika.kuromoji._
        |import jentities.util.Implicits.localDatabase._
        |
        |val base          = new File("/Users/anatolii/Downloads")
        |val sentencesFile = new File(base, "sentences.csv")
        |val linksFile     = new File(base, "links.csv")
        |
        |val vfSents = new File(base, "tSent.csv")
        |val vfLinks = new File(base, "vLinks.csv")
      """.stripMargin,

      // TEMP
      resolvers += "Atilika Open Source repository" at "http://www.atilika.org/nexus/content/repositories/atilika",
      libraryDependencies += "org.atilika.kuromoji" % "kuromoji" % "0.7.7",
      // TEMP

      libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.0.3"
    )

lazy val jorders = (project in file("j-orders"))
    .dependsOn(jentities)
    .settings(commonSettings: _*)
    .settings(
      name := "jorders",

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
    name := "jtext",

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