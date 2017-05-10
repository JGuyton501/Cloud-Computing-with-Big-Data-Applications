package stubs;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Mapper;


public class MoviePairMapper extends Mapper<LongWritable, Text, IntWritable, Pair>{

	// reuse objects
	private Text Movie = new Text();
	private Text User = new Text();
	private DoubleWritable Rate = new DoubleWritable();
	private Text User_Rate = new Text();

	@Override
	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {

		String valueAsString = value.toString();
		String[] tokens = valueAsString.split(",");

		if (tokens.length != 3) {
			return;
		}

		/*
		// movieID, userID, rating 
		String movieID = tokens[0];
		String userID = tokens[1];
		double rating = Double.parseDouble(tokens[2]);

		Movie.set(movieID);
		User.set(userID);
		Rate.set(rating);

		String temp = userID + " " + rating;
		User_Rate.set(temp);

		//context.write(Movie, User_Rate);
		 */
		int rating = (int) Double.parseDouble(tokens[2]);
		//user, movies rating
		context.write(new IntWritable(Integer.parseInt(tokens[1])), new Pair(Integer.parseInt(tokens[0]), rating));
	}
}
