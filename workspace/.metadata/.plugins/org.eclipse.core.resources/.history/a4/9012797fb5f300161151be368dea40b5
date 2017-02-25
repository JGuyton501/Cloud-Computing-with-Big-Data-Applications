package stubs;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.conf.Configuration;


public class LetterMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

	private boolean caseSensitive = true;
	
	@Override
	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {

		/*
		 * Done implement
		 */
		String line = value.toString();
		Text init = new Text(); //reading text file
		IntWritable length = new IntWritable();

		if (caseSensitive == false) {
			for (String word : line.split("\\W+")) {
				if (word.length() > 0) {
					init.set(String.valueOf(word.charAt(0)).toLowerCase()); //lowercase if not case sensitive
					length.set(word.length());
					context.write(init, length);
				}
			}
		} else{
			for (String word : line.split("\\W+")) {
				if (word.length() > 0) {
					init.set(String.valueOf(word.charAt(0))); //case sensitive default
					length.set(word.length());
					context.write(init, length);
				}
			}
		}
	}
	public void setup(Context context){
		  Configuration conf = context.getConfiguration();
		  caseSensitive = conf.getBoolean("caseSensitive", false); //default case sensitive
	  }
}
