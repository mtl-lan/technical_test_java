package test;

import org.apache.spark.sql.*;

public class Part2 {
    public static void main(String[] args) {
        /*
            Compaction : Using the Spark API, please generate one Avro file
            that will contain all the records of the four input files.
            You can output this file to the path of your choice on your local machine.
            Result file does not have to be committed to the repository at the end of the exercise.


            References: https://sparkbyexamples.com/spark/using-avro-data-files-from-spark-sql-2-3-x/
            https://spark.apache.org/docs/latest/sql-data-sources-avro.html
            https://stackoverflow.com/questions/53164427/how-can-i-generate-a-single-avro-file-for-large-flat-file-with-30mb-data
        */

        SparkSession spark = SparkSession.builder().master("local").getOrCreate();

        // Creates a DataFrame from a specified Path
        Dataset<Row> ds = spark.read().format("com.databricks.spark.avro")
                .load("src/main/resources/input");

        ds.show(false);
        System.out.println("Total number of rows: " + ds.count());

        // Merge 4 avro data files into one
        ds.coalesce(1).write().format("com.databricks.spark.avro").mode(SaveMode.Overwrite).save("src/main/resources/output");

        /*
        Transformation/Datamart : Using the result of the first step,
        please output the data again (again at the path of your choice),
        but this time partitioned by age, excluding people that are younger than 21. Output should look like :
        */

        ds.filter("age>=21").write().partitionBy("age")
                .format("com.databricks.spark.avro")
                .mode(SaveMode.Overwrite)
                .save("src/main/resources/partition");
    }
}
