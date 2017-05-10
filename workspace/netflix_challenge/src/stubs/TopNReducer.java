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

import com.google.common.collect.Ordering;
import com.google.common.collect.TreeMultimap;

/**
 *  Reducer's input are local top N from all mappers.
 *  We have a single reducer, which creates the final top N.
 * 
 * @author Mahmoud Parsian
 *
 */
public class TopNReducer  extends Reducer<IntWritable, Pair1, IntWritable, Pair1> implements Configurable {

	private int N = 10; // default
	//	private SortedMap<Double, String> top = new TreeMap<Double, String>();
	//	private HashMap<String, String> movieTitle = new HashMap<String, String>();


	@Override
	public void reduce(IntWritable key, Iterable<Pair1> values, Context context) throws IOException, InterruptedException {
		TreeMultimap< Double,Integer> top =  TreeMultimap.create(Ordering.natural(),Ordering.natural().reverse());
		int i = 0;
		int j = 0;

		for (Pair1 value : values) {
			top.put(value.getRight(),value.getLeft());
			if (top.size() > N) {
				top.remove(top.asMap().firstKey(), top.get(top.asMap().firstKey()).first());
			}
		}

		List<Double> keys = new ArrayList<Double>(top.keySet());
		while(j < keys.size()) {
			Double similarity = keys.get(j);
			for(Integer id : top.get(keys.get(j))) {
				context.write(key,new Pair1(id , similarity));
				i += 1;
			}
			j += 1;
		}
		top.clear();
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

	}
}
