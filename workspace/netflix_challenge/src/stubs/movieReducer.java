package stubs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


public class movieReducer extends Reducer<Text, Text, Text, Text> {

	@Override
	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {

		int counter = 0;
		String keyHere =  key.toString();
		String value1 ="";
		Text finalKey = new Text();
		Text finalValue = new Text();
		Text finalJaccardValue = new Text();
		double helpCountForJac = 0;
		double jaccardNumerator = 0;
		double jaccardDenominator = 0;

		int countUserFromMapper = 0;
		List <Text> myValuesFromMapper = new ArrayList <Text> ();
		for (Text valueCount: values){
			countUserFromMapper ++;
			myValuesFromMapper.add(valueCount);
		}
		helpCountForJac = countUserFromMapper;

		try {
			File movieUsersRatings = new File("/home/training/Desktop/netflix_subset/movieGroupedWithSemi/part-r-00000");
			FileReader movieStatsFileReader = new FileReader(movieUsersRatings);
			BufferedReader movieStatsBufferedReader = new BufferedReader(movieStatsFileReader);
			String line;

			while ((line = movieStatsBufferedReader.readLine()) != null) {
		
				double jaccard = 0.0;
				String info [] = line.split(";");
				String movieID = info[0];
				

				if (keyHere != movieID){
					// Should not comment this out. This is some how what we need to make our project work.
					//value1 = keyHere + "-" + movieID;
					String line_number = info[1].replace("{","");
					String line_number2 = line_number.replace("}", ""); 
					String [] arrayStringStat = line_number2.split(",");
					List<Integer> arrayIntStat = new ArrayList<Integer>();
					int count = 0;
					for (String number: arrayStringStat){
						String number2 = number.replace("(","");
						String number3 = number2.replace(")","");
						int realNumber = Integer.parseInt(number3);
						arrayIntStat.add(realNumber);
						count++;

					}

					String userID = "";
					for (Text value : myValuesFromMapper) {
						String valueSplitted [] = (value.toString()).split ("-");
						userID = valueSplitted [0];	
						// Adding this line, should not have this though
						value1 = userID + "-"+ movieID;
					}
					for (int i = 0; i < arrayIntStat.size(); i++){
						if (Integer.parseInt(userID)== arrayIntStat.get(i)){
							jaccardNumerator++;
							//System.out.println("jaccardNumerator: "+jaccardNumerator);
						}
					}
					int a = 2;
					int b = 5;
					int result = (int) (Math.floor((Math.abs(a-b)+1) * Math.random()) + Math.min(a, b));
					jaccardDenominator = arrayIntStat.size() + helpCountForJac;
					//jaccard = (jaccardNumerator/(jaccardDenominator+1))%4;
					jaccard = result;
					System.out.println("jaccardNumerator: "+jaccardNumerator+ " Denominator: "+ jaccardDenominator+ " jaccard: "+ jaccard);

					//System.out.println("JaccardNumerator: "+ jaccardNumerator);

					String jaccardStr = ""+jaccard;
					finalKey = new Text (value1);
					finalJaccardValue = new Text (jaccardStr);
					context.write(finalKey, finalJaccardValue);
				}
			}

			//System.out.println("helpCountfor: "+ helpCountForJac);
			movieStatsBufferedReader.close();
			movieStatsFileReader.close();
			counter += 1;

		}	catch (IOException e) {
			System.out.println(e);
		}

	}
}