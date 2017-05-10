package stubs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import org.apache.hadoop.conf.Configurable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 *  Reducer's input are local top N from all mappers.
 *  We have a single reducer, which creates the final top N.
 * 
 * @author Mahmoud Parsian
 *
 */
public class TopNReducer  extends
Reducer<NullWritable, Text, DoubleWritable, Text> implements Configurable {

	private int N = 10; // default
	private SortedMap<Double, String> top = new TreeMap<Double, String>();
	private HashMap<String, String> movieTitle = new HashMap<String, String>();

	private void readFile(String file_name) {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file_name));
			String line = br.readLine();

			while (line != null) {
				String[] tokens = line.split(",");
				String movieID = tokens[0];
				String title = tokens[2];
				movieTitle.put(movieID, title);
				line = br.readLine();
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void reduce(NullWritable key, Iterable<Text> values, Context context) 
			throws IOException, InterruptedException {
		for (Text value : values) {
			String valueAsString = value.toString().trim();
			String[] tokens = valueAsString.split(",");
			String movieID = tokens[0];
			double rating =  Double.parseDouble(tokens[1]);
			top.put(rating, movieID);
			// keep only top N
			if (top.size() > N) {
				top.remove(top.firstKey());
			}
		}

		// emit final top N
		List<Double> keys = new ArrayList<Double>(top.keySet());
		for(int i=keys.size()-1; i>=0; i--){
			context.write(new DoubleWritable(keys.get(i)), new Text(movieTitle.get(top.get(keys.get(i)))));
		}
	}

	@Override
	protected void setup(Context context) 
			throws IOException, InterruptedException {
		this.N = context.getConfiguration().getInt("N", 10); // default is top 10
	}

	@Override
	public Configuration getConf() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setConf(Configuration arg0) {
		// TODO Auto-generated method stub
		readFile("/home/training/Desktop/netflix_subset/movie_titles.txt");
	}
}
