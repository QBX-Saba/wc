import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming._
import org.apache.spark.storage._
import com.datastax.spark.connector._
import com.datastax.spark.connector.cql._
import com.datastax.spark.connector.streaming._
import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.sql.cassandra._
import org.apache.spark.streaming.mqtt.MQTTUtils
/**
  * Created by smmal on 8/18/2017.
  */
object StreamingTest {

  def main(args:Array[String]) : Unit = {

    val spark = SparkSession.builder
      .appName("Streaming Sample")
      .enableHiveSupport()
      .getOrCreate()

    val sc = spark.sparkContext

//    val conf = new SparkConf(true)
//      .setMaster("spark://35.156.14.39:7077")
//      .setAppName(getClass.getSimpleName)
//      .set("spark.executor.memory", "lg")
//      .set("spark.cores.max", "1")
//      .set("spark.connection.cassandra.host", "35.156.14.39")

//    val sc = new SparkContextFunctions(conf)

    /** Creates the Spark Streaming Context */
    val ssc = new StreamingContext(sc, Seconds(2))

    /** Creates an input stream that pulls messages from a Kafka broker */
    val stream = MQTTUtils.createStream(ssc, "", "", StorageLevel.MEMORY_AND_DISK_2 )
    //[String, String, StringDecoder, StringDecoder](scc, kafka.KafkaParams, Map(topic -> 1), StorageLevel.MEMORY_ONLY)

    stream.map(_.split(",")).countByValue().saveToCassandra("sentencesks", "wordcount")

    ssc.start()
  }
}
