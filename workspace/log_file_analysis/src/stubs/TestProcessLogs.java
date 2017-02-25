package stubs;

import java.util.ArrayList;
import java.util.List;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

/**
 * Example input line:
 * 96.7.4.14 - - [24/Apr/2011:04:20:11 -0400] "GET /cat.jpg HTTP/1.1" 200 12433
 *
 */

public class TestProcessLogs {
	MapDriver<LongWritable, Text, Text, IntWritable> mapDriver;
	ReduceDriver<Text, IntWritable, Text, IntWritable> reduceDriver;
	MapReduceDriver<LongWritable, Text, Text, IntWritable, Text, IntWritable> mapReduceDriver;

	//setups
	@Before
	public void setUp() {

		SumReducer reducer = new SumReducer();
		reduceDriver = new ReduceDriver<Text, IntWritable, Text, IntWritable>();
		reduceDriver.setReducer(reducer);

		LogFileMapper mapper = new LogFileMapper();
		mapDriver = new MapDriver<LongWritable, Text, Text, IntWritable>();
		mapDriver.setMapper(mapper);

		mapReduceDriver = new MapReduceDriver<LongWritable, Text, Text, IntWritable, Text, IntWritable>();
		mapReduceDriver.setMapper(mapper);
		mapReduceDriver.setReducer(reducer);
	}
	@Test
	public void testReducer() { //reducer test
		List<IntWritable> values = new ArrayList<IntWritable>();
		values.add(new IntWritable(1));
		values.add(new IntWritable(1));
		reduceDriver.withInput(new Text("96.7.4.14"), values);
		reduceDriver.withOutput(new Text("96.7.4.14"), new IntWritable(2));
		reduceDriver.runTest();

	}

	@Test
	public void testMapper() { //mapper test
		mapDriver.withInput(new LongWritable(1), new Text("96.7.4.14 - - [24/Apr/2011:04:20:11 -0400] \"GET /cat.jpg HTTP/1.1\" 200 12433 \n"));
		mapDriver.withOutput(new Text("96.7.4.14"), new IntWritable(1));
		mapDriver.runTest();
	}


	@Test
	public void testMapReduce() { //both test
		mapReduceDriver.withInput(new LongWritable(1), new Text("96.7.4.14"));
		mapReduceDriver.addOutput(new Text("96.7.4.14"), new IntWritable(1));
		mapReduceDriver.runTest();
		//System.out.println("MR - " + mapReduceDriver.run());

	}
}
