package stubs;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class AverageReducer extends Reducer<Text, IntWritable, Text, DoubleWritable> {

	@Override
	public void reduce(Text key, Iterable<IntWritable> values, Context context)
			throws IOException, InterruptedException {

		/*
		 * DONE implement
		 */
		//init vars	
		double count = 0;
		double lengthSum = 0;
		double lengthAvg = 0;

		for (IntWritable val : values) { //loop each val in text file
			lengthSum += val.get();
			count += 1;
		}
		lengthAvg = lengthSum / count;

		context.write(key, new DoubleWritable(lengthAvg)); //used double instead of IntWritable
	}
}