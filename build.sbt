name := "dse-spark-cassandra-test"

version := "1.0"
scalaVersion := "2.11.8"

import AssemblyKeys._ // put this at the top of the file

assemblySettings

lazy val root = (project in file(".")).
  settings(
    mainClass in Compile := Some("WordCount2")
  )



resolvers ++= Seq(
  "All Spark Repository -> bintray-spark-packages" at "https://dl.bintray.com/spark-packages/maven/"
)

libraryDependencies ++= Seq(

  "org.apache.spark" % "spark-core_2.11" % "2.2.0"  % "provided",
  "org.apache.spark" % "spark-sql_2.11" % "2.2.0"  % "provided",
  "datastax" % "spark-cassandra-connector" % "2.0.3-s_2.11",
  "org.apache.spark" % "spark-streaming_2.11" % "2.0.1"  % "provided",
  "org.apache.spark" % "spark-streaming-mqtt-assembly_2.10" % "1.6.3"  % "provided"

)



