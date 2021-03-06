package stubs;

import java.io.IOException;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;

/**
 *  Mapper's input are read from SequenceFile and records are: (K, V)
 *    where 
 *          K is a Text
 *          V is an Integer
 * 
 * @author Mahmoud Parsian
 *
 */
public class TopNMapper extends Mapper<LongWritable, Text, IntWritable, Pair1>{
	//	private int N = 10; // default
	//	private SortedMap<Double, String> top = new TreeMap<Double, String>();
	//
	//	@Override
	//	public void map(Text key, Text value, Context context) throws IOException, InterruptedException {
	//		String movie_id = key.toString();
	//		double rating = Double.parseDouble(value.toString());
	//		String compositeValue = movie_id + "," + rating;
	//		top.put(rating, compositeValue);
	//		// keep only top N
	//		if (top.size() > N) {
	//			top.remove(top.firstKey());
	//		}
	//	}
	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

		String valueAsString = value.toString();
		String[] words = valueAsString.split("\t");
		double similarity = Double.parseDouble( words[1]); 
		String moviePair = words[0];

		moviePair = moviePair.replace("(","");
		moviePair = moviePair.replace(")","");
		String[] pair = moviePair.split(",");

		int first = Integer.parseInt(pair[0]);
		int second = Integer.parseInt(pair[1]);

		context.write(new IntWritable(first), new Pair1(second , similarity));
	}
		
}