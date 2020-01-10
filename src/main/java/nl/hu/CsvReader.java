package nl.hu;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecords;

public class CsvReader {
	
	
	public CsvReader() {
		
	}
	
	public Map<String, String> getAllAankopen() {
		String csvFile = "F:\\database.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";
        Map<String, String> aankopen = new HashMap<String, String>();

        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] aankoop = line.split(cvsSplitBy);
                String a = aankoop[0].subSequence(1, aankoop[0].length() -1).toString();
                String b = aankoop[1].subSequence(1, aankoop[1].length() -1).toString();
                aankopen.put(a, b);
                //System.out.println(aankoop[0]+ ":"+aankoop[1]);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return aankopen;
	}
}
