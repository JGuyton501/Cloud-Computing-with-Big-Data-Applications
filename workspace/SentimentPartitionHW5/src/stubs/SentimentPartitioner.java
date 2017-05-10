package stubs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.conf.Configurable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.Partitioner;

public class SentimentPartitioner extends Partitioner<Text, IntWritable> implements
Configurable {

	private Configuration configuration;
	Set<String> positive = new HashSet<String>();
	Set<String> negative = new HashSet<String>();

	/**
	 * Set up the positive and negative hash set in the setConf method.
	 */
	@Override
	public void setConf(Configuration configuration) {
		/*
		 * Add the positive and negative words to the respective sets using the files 
		 * positive-words.txt and negative-words.txt.
		 */
		/*
		 * DONE TODO implement 
		 */

		//		File positiveWords = new File("/home/training/workspace/SentimentPartitionHW5/PosNegFiles/positive-words.txt");
		//		File negativeWords = new File("/home/training/workspace/SentimentPartitionHW5/PosNegFiles/negative-words.txt");
		File positiveWords = new File("negative-words.txt");
		File negativeWords = new File("negative-words.txt");

		try {
			//pos
			FileReader positiveFileReader = new FileReader(positiveWords);
			BufferedReader positiveBufferedReader = new BufferedReader(positiveFileReader);
			String line;

			while ((line = positiveBufferedReader.readLine()) != null) {
				if (line.charAt(0) != ';') { //ignore all lines in the txt file starting with ; 
					positive.add(line);
				}
			}
			positiveBufferedReader.close();
			positiveFileReader.close();

		}	catch (IOException e) {
			System.out.println(e);
		}
		try {
			//neg
			FileReader negativeFileReader = new FileReader(negativeWords);
			BufferedReader negativeBufferedReader = new BufferedReader(negativeFileReader);
			String line;

			while ((line = negativeBufferedReader.readLine()) != null) {
				// process the line.
				if (line.charAt(0) != ';'){
					negative.add(line);
				}
			}
			negativeBufferedReader.close();
			negativeFileReader.close();
		}	catch (IOException e) {
			System.out.println(e);
		}
	}

	/**
	 * Implement the getConf method for the Configurable interface.
	 */
	@Override
	public Configuration getConf() {
		return configuration;
	}

	/**
	 * You must implement the getPartition method for a partitioner class.
	 * This method receives the words as keys (i.e., the output key from the mapper.)
	 * It should return an integer representation of the sentiment category
	 * (positive, negative, neutral).
	 * 
	 * For this partitioner to work, the job configuration must have been
	 * set so that there are exactly 3 reducers.
	 */
	public int getPartition(Text key, IntWritable value, int numReduceTasks) {
		/*
		 * DONE TODO implement
		 * Change the return 0 statement below to return the number of the sentiment 
		 * category; use 0 for positive words, 1 for negative words, and 2 for neutral words. 
		 * Use the sets of positive and negative words to find out the sentiment.
		 *
		 * Hint: use positive.contains(key.toString()) and negative.contains(key.toString())
		 * If a word appears in both lists assume it is positive. That is, once you found 
		 * that a word is in the positive list you do not need to check if it is in the 
		 * negative list. 
		 */
		if (positive.contains(key.toString())){ //0 for positive words
			return 0;
		} else if (negative.contains(key.toString())){ // for negative words
			return 1;
		} else{ //all else
			return 2; 
		}
	}
}
