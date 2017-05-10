package stubs;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WordCoMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

  @Override
  public void map(LongWritable key, Text value, Context context)
      throws IOException, InterruptedException {
    
    /*
     * DONE TODO implement
     */	
	  String previousWord = "";
	  String input = value.toString().toLowerCase(); //convert line to string, convert to lowercase
	  for (String word : input.split("\\W+")) {
		  if (word.length() > 0) {
			  if (previousWord != ""){ //wait until second loop to write, set previous word
				  context.write(new Text(previousWord + " " + word), new IntWritable(1));
				  //System.out.println("previous word:  " + previousWord + "    Current word:  "+ word);
			  }
			  previousWord = word;
		  }
	  }
  }
}
