package com.rams.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

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
public class JobRunnerMain extends Configured implements Tool{
	
	public static void main(String[] args) throws Exception{
		int exitCode = ToolRunner.run(new JobRunnerMain(), args);
		System.exit(exitCode);
	}
 
	public int run(String[] args) throws Exception {
		if (args.length != 3) {
			System.err.printf("Usage: %s [generic options] <wordcount|wordsort> <input> <output>\n",
					getClass().getSimpleName());
			ToolRunner.printGenericCommandUsage(System.err);
			return -1;
		}
	
		Configuration conf = new Configuration();

		Job job = new Job(conf, args[0]);
		
		job.setJarByClass(JobRunnerMain.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		
		if(args[0].equalsIgnoreCase("wordcount")){
			job.setMapperClass(WordCountMapper.class);
			job.setReducerClass(WordCountReducer.class);
		}else if(args[0].equalsIgnoreCase("wordsort")){
			job.setMapperClass(WordSortMapper.class);
			//job.setReducerClass(WordCountReducer.class); //Reducer not required for sorting
			
			//By default MapReduce framework sort keys in Ascending order. To override that behavior we are passing custom DescendingKeyComparator
			job.setSortComparatorClass(DescendingKeyComparator.class);  
		}
		//This control the number of reducers output files in HDFS
		job.setNumReduceTasks(1);
		
		//Delete output path if it exists in file system
		Path outputPath = new Path(args[2]);
	    FileSystem hdfs = FileSystem.get(conf);
	    if (hdfs.exists(outputPath)){
		      hdfs.delete(outputPath, true);
	    }
	    
		FileInputFormat.addInputPath(job, new Path(args[1]));
		FileOutputFormat.setOutputPath(job, new Path(args[2]));
	
		int returnValue = job.waitForCompletion(true) ? 0:1;
		System.out.println("job.isSuccessful " + job.isSuccessful());
		return returnValue;
	}
}
