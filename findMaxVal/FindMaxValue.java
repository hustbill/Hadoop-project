
/*************************************************************
 * FileName: 	 FindMaxValue.java
 * Author:   	 Billy Zhang (billyzhang2010@gmail.com)
 * Date:          	May 27, 2014
 * Version:			0.2
 * Description: 
 * 	array1 = [2,3...655], length = 300
 * 		array2 = [4,6,6,466], length = 300
 * 			...
 * 				array9 ...
 *
 * 				Find the max elem globally from all array.
 *  Compile:  
 *     [hua@h-node-01 findMaxVal]$ javac -classpath  ${HADOOP_PREFIX}/hadoop-core-${HADOOP_VERSION}.jar:${HADOOP_PREFIX}/lib/commons-cli-1.2.jar -d ./ ./*.java
 *      hadoop jar FindMaxValue.jar  ./fd_Classes/FindMaxValue
 * ****************************************************************/


import java.io.*;
import java.util.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.commons.cli.Options;

public class FindMaxValue {

	public static class FindMaxValueMapper extends Mapper<LongWritable, Text, IntWritable, IntWritable>{
		private final static IntWritable one = new IntWritable(1);

       @Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			String str = value.toString().trim();
			int intVal = Integer.parseInt(str);
			context.write(one, new IntWritable(intVal));
		}
	}
	
	public static class FindMaxValueReducer extends Reducer<IntWritable, IntWritable, Text, IntWritable>{

		public void reduce(IntWritable key, Iterable<IntWritable> values, Context context) 
		    throws IOException, InterruptedException{
			Iterator it = values.iterator();
			int maxInt = 0, tmp;
			if(it.hasNext()){
				maxInt = ((IntWritable) it.next()).get();
			}else{
				context.write(new Text("Max elem : "), null);
				return;
			}
			while(it.hasNext()){
				tmp = ((IntWritable) it.next()).get();
				if(tmp > maxInt){
					maxInt = tmp;
				}
			}
			context.write(new Text("Max elem is :"), new IntWritable(maxInt));
		}
	}
	
	public static void main(String[] args) throws IOException, 
	                         InterruptedException, ClassNotFoundException {
		Configuration conf = new Configuration();
		//String[] paths = { "hdfs://h-node-01:9000/tmp/data.txt", "hdfs://h-node-01:9000/tmp/outputs" };
		//String[] otherArgs = new GenericOptionsParser(conf, paths).getRemainingArgs();
		
		Job job = new Job(conf, "FindValue");
		job.setJarByClass(FindMaxValue.class);
		job.setMapperClass(FindMaxValueMapper.class);
		job.setReducerClass(FindMaxValueReducer.class);
		
		job.setMapOutputKeyClass(IntWritable.class);
		job.setMapOutputValueClass(IntWritable.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
