import sbt._
import sbt.Keys._

object BuildSettings {

    val ParentProject = "spark-sch-test-parent"
    val generator = "spark-test-generator"

    val Version = "0.1"
    val ScalaVersion = "2.11.8"

    lazy val rootbuildSettings = Defaults.coreDefaultSettings ++ Seq (
        name          := ParentProject,
        version       := Version,
        scalaVersion  := ScalaVersion,
        organization  := "com.ibm.spark.sch.test",
        description   := "Spark Scheduler Test",
        scalacOptions := Seq("-deprecation", "-unchecked", "-encoding", "utf8", "-Xlint") 
    )

    lazy val generatorSettings = Defaults.coreDefaultSettings ++ Seq (
        name          := generator,
        version       := Version,
        scalaVersion  := ScalaVersion,
        organization  := "com.ibm.spark.sch.test",
        description   := "Spark Scheduler Test Generator",
        scalacOptions := Seq("-deprecation", "-unchecked", "-encoding", "utf8", "-Xlint")
    )
}

object Resolvers {
    val typesafe = "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
    val sonatype = "Sonatype Release" at "https://oss.sonatype.org/content/repositories/releases"
    val mvnrepository = "MVN Repo" at "http://mvnrepository.com/artifact"
    val allResolvers = Seq(typesafe, sonatype, mvnrepository)
}

object Dependency {
    object Version {
        val Spark        = "2.0.1"
        val Slf4jVersion = "1.7.12"
        val Log4jVersion = "1.2.17"
        val akkaV = "2.4.12"
    }
    
    val sparkCore = "org.apache.spark" %% "spark-core" % Version.Spark % "provided"
    val sparkMl = "org.apache.spark" %% "spark-mllib" % Version.Spark % "provided"

    val akkaActor = "com.typesafe.akka" %% "akka-actor" % Version.akkaV 

    val configLib = "com.typesafe" % "config" % "1.2.1"
    val slf4j = "org.slf4j" % "slf4j-api" % Version.Slf4jVersion % "provided"
    val log4jbind = "org.slf4j" % "slf4j-log4j12" % "1.7.12"
    val kafkaClient = "net.cakesolutions" %% "scala-kafka-client" % "0.10.0.0" 
}

object Dependencies {
    import Dependency._
    
    val generatorDependencies = Seq(sparkCore, sparkMl, configLib, slf4j,
                                    log4jbind, akkaActor, kafkaClient)
}

object SparkTestBuild extends Build {
    import Resolvers._
    import Dependencies._
    import BuildSettings._

    lazy val parent = Project(
        id = "spark-sch-test-parent",
        base = file("."),
        aggregate = Seq(generator),
        settings = rootbuildSettings ++ Seq(
            aggregate in update := false
        )
    )

    lazy val generator = Project(
        id = "spark-test-generator",
        base = file("./generator"),
        settings = generatorSettings ++ Seq(
            maxErrors := 5,
            ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true) },
            triggeredMessage := Watched.clearWhenTriggered,
            resolvers := allResolvers,
            libraryDependencies ++= Dependencies.generatorDependencies,
            unmanagedResourceDirectories in Compile += file(".") / "conf",
            fork := true,
            connectInput in run := true
        ) 
    )
}
