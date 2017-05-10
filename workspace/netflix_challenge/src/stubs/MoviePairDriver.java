package stubs;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class MoviePairDriver extends Configured implements Tool{
	public static void main(String[] args) throws Exception {
		int exitCode=ToolRunner.run(new Configuration(), new MoviePairDriver (), args);
		System.exit(exitCode);
	}
	/*
	 * Validate that two arguments were passed from the command line.
	 */ 

	/*
	 * Instantiate a Job object for your job's configuration. 
	 */
	public int run (String[] args) throws Exception {

		// Job job = new Job();
		Job job = new Job(getConf());


		job.setJarByClass(MoviePairDriver.class);
		job.setJobName("Job1 Movie and Rating Pairing");

		job.setMapOutputKeyClass(IntWritable.class);
		job.setMapOutputValueClass(Pair.class);
		job.setOutputKeyClass(Pair.class);
		job.setOutputValueClass(Pair.class);

		/*
		 * DONE implement
		 */
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		job.setMapperClass(MoviePairMapper.class);
		job.setReducerClass(MoviePairReducer.class);


		/*
		 * Start the MapReduce job and wait for it to finish.
		 * If it finishes successfully, return 0. If not, return 1.
		 */
		boolean success = job.waitForCompletion(true);
		return success ? 0 : 1;
	}
}
