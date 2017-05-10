package stubs;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;


/**
 * Mapper's input records are:
 *
 *  <key-as-string><,><value-as-integer>
 *
 * This class, AggregateByKeyMapper, generates all (K,V) pairs.
 * Note that K's can have duplicates. For example it might generate:
 * (K,2) and (K,3).
 *
 *
 * @author Mahmoud Parsian
 *
 */
public class AggregateByKeyMapper extends Mapper<LongWritable, Text, Text, DoubleWritable> {

	// reuse objects
	private Text K2 = new Text();
	private DoubleWritable V2 = new DoubleWritable();

	@Override
	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {

		String valueAsString = value.toString().trim();
		String[] tokens = valueAsString.split(",");
		if (tokens.length != 3) {
			return;
		}

		// movieID, userID, rating and we only need movieID at 0  and rating at 2
		String movieID = tokens[0];
		double rating = Double.parseDouble(tokens[2]);
		//      String url = tokens[0];
		//      int frequency =  Integer.parseInt(tokens[1]);
		K2.set(movieID);
		V2.set(rating);
		context.write(K2, V2);
	}
}