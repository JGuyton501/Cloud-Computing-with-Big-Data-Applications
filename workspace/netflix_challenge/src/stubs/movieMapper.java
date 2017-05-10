package stubs;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class movieMapper extends Mapper<LongWritable, Text, Text, Text> {

  @Override
  public void map(LongWritable key, Text value, Context context)
      throws IOException, InterruptedException {

    String line = value.toString().trim();
    
    String user;
    Text movieId;
    Text rating;
    Text user_rating;
    
      if (line.length() > 0) {
    	  
    	  String info [] = line.split(",");
    	  
    	  movieId = new Text (info[0]);
    	  user = (info[1]);
    	  rating = new Text (info[2]);
    	  user_rating =  new Text (user + "-" + rating);
    	  
    	  context.write(movieId, user_rating);
      }
  }
}