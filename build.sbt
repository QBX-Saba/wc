name := "dse-spark-cassandra-test"

version := "1.0"
scalaVersion := "2.11.8"

lazy val root = (project in file(".")).
  settings(
    mainClass in Compile := Some("WordCount.scala")
  )

resolvers ++= Seq(
  "All Spark Repository -> bintray-spark-packages" at "https://dl.bintray.com/spark-packages/maven/"
)

libraryDependencies ++= Seq(

  "org.apache.spark" % "spark-core_2.11" % "2.0.1" ,
  "org.apache.spark" % "spark-sql_2.11" % "2.0.1",
  "com.datastax.spark" % "spark-cassandra-connector_2.10" % "2.0.3"
)

