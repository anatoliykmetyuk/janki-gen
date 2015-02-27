scalaVersion := "2.11.5"
sbtVersion   := "0.13.7"

libraryDependencies ++= Seq(
  "commons-io"         %  "commons-io"       % "2.4",
  "org.apache.commons" %  "commons-compress" % "1.9",
  "org.scalatest"      %% "scalatest"        % "2.2.4" % "test"
)