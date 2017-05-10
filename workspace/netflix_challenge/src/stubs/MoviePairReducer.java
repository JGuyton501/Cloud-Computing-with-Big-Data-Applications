package stubs;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.Reducer;

public class MoviePairReducer extends Reducer<IntWritable, Pair, Pair, Pair>{

	@Override
	public void reduce(IntWritable key, Iterable<Pair> values, Context context)
			throws IOException, InterruptedException {

		//		double sum = 0;
		//		int count = 0;
		//		for (DoubleWritable value : values) {
		//			count += 1;
		//			sum += value.get();
		//		}
		//		String temp = count + " " + sum;
		//context.write(key, new DoubleWritable(temp));

		ArrayList<Pair> temp = new ArrayList<Pair>();

		for (Pair value : values) {
			temp.add(new Pair(value.getLeft(), value.getRight()));
		}

		for (Pair first : temp) {
			for (Pair second : temp) {
				if (first.getLeft() < second.getLeft()) {				    	
					context.write(new Pair(first.getLeft(), second.getLeft()), new Pair(first.getRight(), second.getRight()));
				}
			}
		}			
	}
}