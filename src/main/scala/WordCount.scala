
import com.datastax.spark.connector._
import org.apache.spark.{SparkConf, SparkContext}

object WordCount {

  def main(args:Array[String]) : Unit = {

    val conf = new SparkConf().setAppName("Spark WordCount").setMaster("spark://35.156.14.39:7077")

    val sc = new SparkContext(conf)
    val sentences = sc.cassandraTable[(String)]("sentencesks", "sentences").select("sentence")

    val textRDD = sc.parallelize(List(sentences.fold("")((f: String, s: String) => f + s)))

    val splits = textRDD.flatMap(line => line.split(" ")).map(word =>(word,1))
    val counts = splits.reduceByKey((x,y)=>x+y)

    counts.saveToCassandra("sentencesks", "wordcount", SomeColumns("word", "count"))

  }
}