name := "dse-spark-cassandra-test"

version := "1.0"
scalaVersion := "2.11.8"

lazy val root = (project in file(".")).
  settings(
    mainClass in Compile := Some("WriteRead")
  )

resolvers ++= Seq(
  "All Spark Repository -> bintray-spark-packages" at "https://dl.bintray.com/spark-packages/maven/"
)

libraryDependencies ++= Seq(

  "org.apache.spark" % "spark-core_2.11" % "2.0.1" ,
  "org.apache.spark" % "spark-sql_2.11" % "2.0.1",
  "datastax" % "spark-cassandra-connector" % "2.0.3-s_2.11"
)

