package stubs;

import static org.junit.Assert.assertEquals;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;
import org.junit.Test;

public class SentimentPartitionTest {

	SentimentPartitioner mpart;

	@Test
	public void testSentimentPartition() {

		SentimentPartitioner spart = (new SentimentPartitioner());
		spart.setConf(new Configuration());

		/*
		 * Test the words "love", "deadly", and "zodiac". 
		 * The expected outcomes should be 0, 1, and 2. 
		 */

		/*
		 * TODO implement
		 */          
		int love = spart.getPartition(new Text("love"), new IntWritable(1), 3);
		int deadly = spart.getPartition(new Text("deadly"), new IntWritable(1), 3);
		int zodiac = spart.getPartition(new Text("zodiac"), new IntWritable(1), 3);

		assertEquals(love, 0);
		assertEquals(deadly, 1);
		assertEquals(zodiac, 2);		
	}

}
