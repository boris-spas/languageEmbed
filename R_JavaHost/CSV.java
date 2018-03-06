import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class CSV {

	public static ArrayList<String[]> importCSV(String filePath) throws Exception{
	    BufferedReader br = new BufferedReader(new FileReader(filePath));

	    ArrayList<String[]> parsedCSV = new ArrayList<String[]>();

	    String line = null;
	    while ((line = br.readLine()) != null) {

	    	String[] values = line.split(",");
        String[] parsedLine = new String[values.length];
	    	for (int i = 0; i < values.length; i++) {
	    		parsedLine[i] = values[i];
	    	}

	    	parsedCSV.add(parsedLine);
	    }
	    br.close();
	    return parsedCSV;
	  }
}
