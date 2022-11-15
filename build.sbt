name := """sample-application-for-ecr-deployment"""
organization := "com.example"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.3"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"

enablePlugins(DockerPlugin)
Docker / maintainer := "yusuke307.ac@gmail.com"
dockerBaseImage := "amazoncorretto:8"
Docker / dockerExposedPorts := Seq(9000, 9000)
Docker / daemonUser := "daemon"

import com.amazonaws.regions.{Region, Regions}
enablePlugins(EcrPlugin)
Ecr / region := Region.getRegion(Regions.AP_NORTHEAST_1)
Ecr / repositoryName := "project/sample-ecr"
Ecr / repositoryTags := Seq(version.value, "latest")
Ecr / localDockerImage := (packageName in Docker).value + ":" + (version in Docker).value

import ReleaseTransformations._
releaseVersionBump := sbtrelease.Version.Bump.Bugfix
releaseProcess := Seq[ReleaseStep](
  ReleaseStep(state => Project.extract(state).runTask(login in Ecr, state)._1),
  inquireVersions,
  runClean,
  setReleaseVersion,
  ReleaseStep(state => Project.extract(state).runTask(publishLocal in Docker, state)._1),
  ReleaseStep(state => Project.extract(state).runTask(push in Ecr, state)._1),
  commitReleaseVersion,
  tagRelease,
  setNextVersion,
  commitNextVersion,
  pushChanges
)
