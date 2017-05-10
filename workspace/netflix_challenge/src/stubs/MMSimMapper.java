package stubs;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MMSimMapper extends Mapper<LongWritable, Text, Pair, Pair> {

	@Override
	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {

		//    String line = value.toString().trim();
		//    
		//    String user;
		//    Text movieId;
		//    Text rating;
		//    Text user_rating;
		//    
		//      if (line.length() > 0) {
		//    	  
		//    	  String info [] = line.split(",");
		//    	  
		//    	  movieId = new Text (info[0]);
		//    	  user = (info[1]);
		//    	  rating = new Text (info[2]);
		//    	  user_rating =  new Text (user + "-" + rating);
		//    	  
		//    	  context.write(movieId, user_rating);
		//      }

		String valueAsString = value.toString();
		valueAsString = valueAsString.replace(")", "");
		valueAsString = valueAsString.replace("(", "");

		String[] tokens = valueAsString.split("\t");

		String[] moviePair=tokens[0].split(",");
		String[] ratingPair=tokens[1].split(",");
		int firstMovie = Integer.parseInt(moviePair[0].trim());
		int secondMovie = Integer.parseInt(moviePair[1].trim());	
		int firstRating = Integer.parseInt(ratingPair[0].trim());
		int secondRating = Integer.parseInt(ratingPair[1].trim());

		context.write(new Pair(firstMovie,secondMovie), new Pair(firstRating,secondRating));
	}
}