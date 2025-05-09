package argon.kwic;

import java.util.TreeMap;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@SuppressWarnings("unused")
public class App {
	
	static List<String> input;
	static TreeMap<String, String> word_context_map;
	
    public static void main(String[] args) throws IOException {  
    	
    	// where we will store the keywords and their contexts
    	word_context_map = new TreeMap<String, String>();
    	
    	// read file into input
    	readInputFile();
    	
    	// for each line in input
    	for (String line : input) {
    		
    		// splits line into words 	
    		String[] words = line.split("\\W+");	
    		
    		// for every word
    		for (String word : words) {
    			
    			// build its context and add to map
    			buildContext(word, line);
    		}
    	}
    	
    	// print the map in alphabetical order
    	print_pairs();
    }
    
    // used for reading the input file into a List<String>
    private static void readInputFile() throws IOException {
    	input = Files.readAllLines(Paths.get("input.txt"));
    }
    
    // main program logic. add word and surrounding substrings to map
    private static void buildContext(String word, String line) {
		
		// get the word's index
		int word_start = line.indexOf(word);
		
		// get the substrings before and after it
		String before = line.substring(0, word_start);
		String after = line.substring(word_start + word.length());
		
		// format them by adding white space or truncating to length
		before = trimOrAddLeft(before, 20);
		after = trimOrAddRight(after, 20 - word.length()); // dangerous code...

		// make the key word bold
		word = "\u001B[1m" + word + "\u001B[0m";
		
		// put it all together
		String output = before + word + after;
		
		// add it to the map
		word_context_map.put(word, output);
    }
    
    // prints the lines in alphabetical order (based on key word)
    private static void print_pairs() {
    	for (String key : word_context_map.keySet()) {
    		System.out.println(word_context_map.get(key) + "\n");
    	}
    }
    
    // magic method for either truncating the string to desired length
    // or adding white space to left
    private static String trimOrAddLeft(String before, int length) {
		if (before.length() > length) {
			// dangerous code...
			before = "..." + before.substring(before.length() - length + 3, before.length());
		} else before = String.format("%" + length + "s", before);
		
		return before;
    }
    
    // magic method for either truncating the string to desired length
    // or adding white space to right
    private static String trimOrAddRight(String after, int length) {
		if (after.length() > length) {
			// more dangerous code
			after = after.substring(0, length - 2) + "...";
		} else after = String.format("%-" + length + "s", after);
		
		return after;
    }
}