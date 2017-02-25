package stubs;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class LetterMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

	@Override
	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {

		/*
		 * Done implement
		 */
		String line = value.toString();
		Text init = new Text(); //reading text file
		IntWritable length = new IntWritable();

		for (String word : line.split("\\W+")) {
			if (word.length() > 0) {
				init.set(String.valueOf(word.charAt(0))); //get first char and map to word length
				length.set(word.length());
				context.write(init, length);
			}
		}
	}
}
