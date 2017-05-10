package stubs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;

public class SequenceFileWriter {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {
		String uri = args[0];
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(uri), conf);
		Path path = new Path(uri);

		Text key = new Text();
		Text value = new Text();
		SequenceFile.Writer writer = null;
		try {
			File file = new File("/home/training/Desktop/testfile.txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;

			while ((line = bufferedReader.readLine()) != null) {
			
				try {
					writer = SequenceFile.createWriter(fs, conf, path, key.getClass(), value.getClass());
					String str = line.split (" -", 2)[0];
					key.set(str);
					value.set(line);
					System.out.printf("[%s]\t%s\t%s\n", writer.getLength(), key, value);
					writer.append(key, value);

				} finally {
					IOUtils.closeStream(writer);
				}
			}
			fileReader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}