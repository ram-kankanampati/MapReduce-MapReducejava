# MapReduce-MapReducejava
Map Reduce programs with Traditional MapReduce framework, Spark with Java and Scala
/**
 * @author Ram Kankanampati
 * 
 * <p><b>Steps to Run:</b>
 * <p>1) Build the jar by run as maven 'clean compile install'
 * <p>2) Copy the JAR file to Unix server, in your case sandbox
 * <p>3) copy the input sampledata.txt file in to HDFS file system under /rams/input folder
 * 		<br>[root@sandbox ~]# hadoop fs -mkdir /rams/input
 *		<br>[root@sandbox ~]# hadoop fs -put sampledata.txt  /rams/input/
 *		<br>[root@sandbox ~]# hadoop fs -ls /rams/input
 * <p>4) Run jar file with below command
 *     <br>[root@sandbox ~]# hadoop jar hadoop-mapred-proj-0.0.1-SNAPSHOT.jar /rams/input /rams/output
 * <p>5) You can verify the out under /rams/ouput1 <br>
 * 	  		[root@sandbox ~]# hadoop fs -ls /rams/output1 
 * 	  <br>  [root@sandbox ~]# hadoop fs -cat /rams/output1/part-r-00000
 * <p>6) To check Job status in web UI
 * 		<br>http://localhost:8088/cluster
 * <p>7)Ambari Dashboard:
 * 		<br>http://localhost:8080/#/main/hosts/sandbox.hortonworks.com/summary
 * <p> Misc:
 * 		<br>http://tuttlem.github.io/2014/01/30/create-a-mapreduce-job-using-java-and-maven.html
 * 
 */
