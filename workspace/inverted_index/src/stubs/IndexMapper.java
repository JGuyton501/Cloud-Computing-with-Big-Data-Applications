package stubs;
import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class IndexMapper extends Mapper<Text, Text, Text, Text> {


	@Override
	public void map(Text key, Text value, Context context) throws IOException,
	InterruptedException {

		/*
		 * DONE TODO implement
		 */
		Text string = new Text();
		Text fs = new Text();

		String filenameStr = ((FileSplit) context.getInputSplit()).getPath().getName();
		fs = new Text(filenameStr);
		String line = value.toString();

		line = line.toLowerCase(); //all to lowercase

		StringTokenizer t = new StringTokenizer(line);
		while (t.hasMoreTokens()) {
			string.set(t.nextToken());			
			context.write(string, new Text(fs + "@" + key) );
		}
	}
}