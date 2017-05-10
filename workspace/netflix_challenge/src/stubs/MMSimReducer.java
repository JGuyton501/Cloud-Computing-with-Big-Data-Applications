package stubs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


public class MMSimReducer extends Reducer<Pair, Pair, Pair, DoubleWritable> {
	@Override
	public void reduce(Pair key, Iterable<Pair> values, Context context)
			throws IOException, InterruptedException {
		
		double rx = 0;
		double ry = 0;
		double similarity = 0;
		double temp = 0.0;
				
		ArrayList<Integer> r1=new ArrayList<Integer>();
		ArrayList<Integer> r2=new ArrayList<Integer>();
		
		for (Pair pair_rates : values) {
			int v1 = pair_rates.getLeft();
			int v2 = pair_rates.getRight();
			r1.add(v1);
			r2.add(v2);
			
			similarity = similarity + v1 * v2;
			rx += v1 * v1;
			ry += v2 * v2;
			System.out.println("v1: "+ v1+ " v2: "+ v2);

		}

		rx = Math.sqrt(rx);
		ry = Math.sqrt(ry);
		
		
		temp = similarity / (rx*ry);
		System.out.println("temp: "+ temp+ " rx: "+ rx+ " ry: "+ ry);
		context.write(key, new DoubleWritable(temp));
	}


}