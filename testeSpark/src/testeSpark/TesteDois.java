package testeSpark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class TesteDois {

    public static void main(String[] args) {
        // configure spark
        SparkConf sparkConf = new SparkConf().setAppName("Read Text to RDD")
                                        .setMaster("local[2]").set("spark.executor.memory","2g");
        // start a spark context
        JavaSparkContext sc = new JavaSparkContext(sparkConf);
        
        // provide path to input text file
        String path = "../../../../root/Downloads/COTAHIST_A2017.TXT";
        
        // read text file to RDD
        //JavaRDD<String> lines = sc.textFile(path).map(x -> String.format(x, args));
        JavaRDD<String> lines2 = sc.textFile(path).filter(x -> x.subSequence(0, 1).equals("00"));
 
        
        // collect RDD for printing
        for(String line:lines2.collect()){
            System.out.println(line);
            break;
        }
        sc.close();
    }

}