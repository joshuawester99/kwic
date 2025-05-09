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
    			
    			int word_start = line.indexOf(word);
    			
    			String before = line.substring(0, word_start);
    			String after = line.substring(word_start + word.length());
    			
    			if (before.length() > 10) before = before.substring(before.length()-9, before.length());
    			if (after.length() > 10) after = after.substring(0, 11);
    			
    			word = "\u001B[1m" + word + "\u001B[0m";
    			
    			String output = "";
    			
    			if (before.isEmpty()) output = before + word + after + "...";
    			else if (after.isEmpty()) output = "..." + before + word + after;
    			else output = "..." + before + word + after + "...";
    			
    			word_shift_pairs.put(word, output);
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
}