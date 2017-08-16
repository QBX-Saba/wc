import com.datastax.spark.connector._
import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}

object WordCount2 {

  def main(args:Array[String]) : Unit = {

    val spark = SparkSession.builder
      .appName("Datastax Scala example")
      .enableHiveSupport()
      .getOrCreate()

    val sc = spark.sparkContext
    val sentences = sc.cassandraTable[(String)]("sentencesks", "sentences").select("sentence")

    val textRDD = sc.parallelize(List(sentences.fold("")((f: String, s: String) => f + s)))

    val splits = textRDD.flatMap(line => line.split(" ")).map(word =>(word,1))
    val counts = splits.reduceByKey((x,y)=>x+y)

    counts.saveToCassandra("sentencesks", "wordcount", SomeColumns("word", "count"))
    spark.stop()
    sys.exit(0)

  }
}