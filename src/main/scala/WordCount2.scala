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
    sentences.collect().foreach(s=>{println(s)})

    val textRDD = sc.parallelize(List(sentences.fold("")((f: String, s: String) => f + s)))
    textRDD.collect().foreach(s=>{println(s)})

    val splits = textRDD.flatMap(line => line.split(" ")).map(word =>(word,1))
    splits.collect().foreach(s=>{println(s)})

    val counts = splits.reduceByKey((x,y)=>x+y)
    counts.collect().foreach(s=>{println(s)})

    counts.saveToCassandra("sentencesks", "wordcount", SomeColumns("word", "count"))
    spark.stop()
    sys.exit(0)

  }
}