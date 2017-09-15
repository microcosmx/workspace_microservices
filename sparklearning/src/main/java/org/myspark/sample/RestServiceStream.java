
package org.myspark.sample;

import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Pattern;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.StorageLevels;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import scala.Tuple2;

// scalastyle:on
public class RestServiceStream {
	
	private static final Pattern SPACE = Pattern.compile(" ");
	
	public static void main(String[] args) throws IOException, InterruptedException {
    
    String metric = "restservice";
    String host = "localhost";
    int port = 44444;
    
    SparkConf sparkConf = new SparkConf().setAppName(metric).setMaster("local[*]");
    JavaStreamingContext ssc = new JavaStreamingContext(sparkConf, Durations.seconds(12));

    JavaReceiverInputDStream<String> lines = ssc.socketTextStream(
    		host, port, StorageLevels.MEMORY_AND_DISK_SER);
    
    lines.print();
    
    JavaDStream<String> words = lines.flatMap(x -> Arrays.asList(SPACE.split(x)).iterator());
    JavaPairDStream<String, Integer> wordCounts = words
    		.mapToPair(s -> new Tuple2<>(s, 1))
    		.reduceByKey((i1,i2) -> i1 + i2);

    wordCounts.print();
    
    

    ssc.start();
    ssc.awaitTermination();
  }
}
