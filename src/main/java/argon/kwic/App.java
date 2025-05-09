package argon.kwic;

import java.util.TreeMap;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@SuppressWarnings("unused")
public class App {
	
	static List<String> input;
	static TreeMap<String, String> word_shift_pairs;
	
    public static void main(String[] args) throws IOException {  
    	
    	word_shift_pairs = new TreeMap<String, String>();
    	
    	readInputFile();
    	
    	// for each line
    	for (String line : input) {
    		
    		// splits line into words 	
    		String[] words = line.split("\\W+");	
    		
    		// for every word
    		for (String word : words) {
    			
    			// add it to the map along with the corresponding shifted string
        		line = addCircularShiftedString(line);
    		}
    	}
    	
    	print_pairs();
    }
    
    // used for reading the input file into a List<String>
    private static void readInputFile() throws IOException {
    	input = Files.readAllLines(Paths.get("input.txt"));
    }
    
    // debugging method for checking state of the Map
    private static void print_pairs() {
    	for (String key : word_shift_pairs.keySet()) {
    		System.out.println(word_shift_pairs.get(key) + "\n");
    	}
    }
    
    // removes first word from string and concatenates to the end, 
    // returning the modified string
    // violated Single-Responsibility principle but 
    // each time the string is modified, it and its new first word are put in the map
    private static String addCircularShiftedString(String line) {
    	// split into first word, any deliminator/break, and the rest of the string
    	String[] split = line.split("(?!^)\\b", 3); 
    	
    	// trim each of excess
    	String first_word = split[0].trim();
    	String first_char = split[1].trim();
    	String remainder = split[2].trim();
    	
    	// get the first word of the new string
    	String remainder_key = remainder.split("(?!^)\\b", 2)[0].trim();
    	
    	// perform the shift and put in the map
    	String shifted_string = remainder + " " + first_word + first_char;
    	word_shift_pairs.put(remainder_key, shifted_string);
    	
    	// return the shifted string
    	return shifted_string;
    }
}