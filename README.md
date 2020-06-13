# Technical Test

Checkout/download this project. The goal is to complete the following exercise that contains both coding challenges and
a theoretical question in 50 minutes.

Please either create a new branch that you will push back to Github (if you have an account) or archive your project at the end of the hour
(send it to nicolas.tallineau@ubisoft.com or eduardo.lomonaco@ubisoft.com). You can answer the question directly in this README file.

--------

### Coding exercise:

A few libraries are already setup in the pom.xml, you can add or remove any dependency as you wish during this
exercise.

#### Part 1

The goal of this first exercise is to review quickly the basics of Java. In the file Part1.java, a list of Person objects
have been initialized.

#####1. Complete the implementation of the method printStatus that should print the output:

> D is young [8]<br>
> A is young [15]<br>
> G is young [21]<br>
> B is not that young [24]<br>
> F is not that young [34]<br>
> C is not that young [62]<br>
> E is not that young [90]

```python
Solution considerations: 
1. Sort out the List "persons" by person's age. 
2. Use "for loop" to iterate each person in persons and within for loop, then use "if else statement" to sort out the person by age group. 

persons.sort(Comparator.comparingInt(Person::getAge));

String ageGroup;
for (Person person: persons){
    if (person.getAge()<24) {
        ageGroup = " is young ";
    }
    else{
        ageGroup = " is not that young ";
    }
    System.out.println(person.getName()+ageGroup+"["+person.getAge()+"]");
```

#####2. Complete the implementation of the method getCityStats that should return a Map of the city names with their occurrences:

> Map(<br>
>    "Quebec" -> 1<br>
>    "Granby" -> 1<br>
>    "Montreal" -> 4<br>
>    "Sherbrooke" -> 1<br>
> )

```python
Solution consideration: 
1. Create a HashMap to store the result. 
2. Use "for loop" to iterate each person in List "persons". 
3. Use "if else statement" to check each person's city, if the city in the Map, then add 1 to the variable count. 
If the name isn't in the Map key, then set the value as 1. 
4. Return this HashMap as the final result. 

Map<String, Integer> cityMap = new HashMap<String,Integer>();
for (Person person: persons) {
    Integer count;
    if (cityMap.containsKey(person.getAddress().getCity())){
        count = cityMap.get(person.getAddress().getCity())+1;
        cityMap.put(person.getAddress().getCity(), count);

    }
    else{
        cityMap.put(person.getAddress().getCity(), 1);
    }

}
return cityMap;  
```
#### Part 2

The goal of this exercise is to ingest and manipulate a small dataset contained in the Avro files located at
src/main/resources/input. The Avro schema is provided in the src/main/resources/avro_utils folder.

Please initiate in the file Part2.java a Spark 2 Session in local mode (no interaction with an Hadoop cluster will happen during this
exercise).

#####1. **Compaction** : Using the Spark API, please generate one Avro file that will contain all the records of the four
 #####input files. You can output this file to the path of your choice on your local machine. Result file does not have to be committed to the
 #####repository at the end of the exercise.
          
Compaction : Using the Spark API, please generate one Avro file
that will contain all the records of the four input files.
You can output this file to the path of your choice on your local machine.
Result file does not have to be committed to the repository at the end of the exercise.
     
```python    

My solution is refered below links: 
https://sparkbyexamples.com/spark/using-avro-data-files-from-spark-sql-2-3-x/
https://spark.apache.org/docs/latest/sql-data-sources-avro.html
https://stackoverflow.com/questions/53164427/how-can-i-generate-a-single-avro-file-for-large-flat-file-with-30mb-data

    // create a spark session instance 
    SparkSession spark = SparkSession.builder().master("local").getOrCreate();
    
    // Creates a DataFrame from a specified Path
    Dataset<Row> ds = spark.read().format("com.databricks.spark.avro")
            .load("src/main/resources/input");
    
    ds.show(false);
    System.out.println("Total number of rows: " + ds.count());
    
    // Merge 4 avro data files into one and save it on a local path. 
    ds.coalesce(1).write().format("com.databricks.spark.avro").mode(SaveMode.Overwrite).save("src/main/resources/output");
```


#####2. **Transformation/Datamart** : Using the result of the first step, please output the data again (again at the path
 #####of your choice), but this time partitioned by age, *excluding* people that are younger than 21. Output should look like :
#####    - \path\age=21\\***.avro
#####    - \path\age=24\\***.avro
#####   - ...

Transformation/Datamart : Using the result of the first step,
please output the data again (again at the path of your choice),
but this time partitioned by age, excluding people that are younger than 21. Output should look like :


```python  
Solution: Filter the dataset, exclude age < 21 and write partitions into pp folder. 

ds.filter("age>=21").write().partitionBy("age")
        .format("com.databricks.spark.avro")
        .mode(SaveMode.Overwrite)
        .save("src/main/resources/partition");
```

### Question:

    Even if our use case is too simple to show the real advantage of compacting small files in fewer bigger ones,
    can you explain why it is critical to run this kind of compaction in HDFS?
    
    I think there are two main advantages:
    1. HDFS is a distributed file system, so it will speed up data processing and also the replication mechanism will increase data availability and consistancy. 
    2. It will greatly support data further consumers, like business analysis, machine learning, OLAP services, etc.  

