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
    	
    	for (String line : input) {
    		
    		String[] words = line.split("\\W+"); 		
    		
    		for (String word : words) {
        		line = addCircularShiftedString(line);
    		}
    	}
    	
    	print_pairs();
    }
    
    private static void readInputFile() throws IOException {
    	input = Files.readAllLines(Paths.get("input.txt"));
    }
    
    private static void print_pairs() {
    	for (String key : word_shift_pairs.keySet()) {
    		System.out.println(word_shift_pairs.get(key) + "\n");
    	}
    }
    
    private static String addCircularShiftedString(String line) {
    	String[] split = line.split("(?!^)\\b", 3); 
    	
    	String first_word = split[0].trim();
    	String first_char = split[1].trim();
    	String remainder = split[2].trim();
    	String remainder_key = remainder.split("(?!^)\\b", 2)[0].trim();
    	
    	String shifted_string = remainder + " " + first_word + first_char;
    	word_shift_pairs.put(remainder_key, shifted_string);
    	
    	return shifted_string;
    }
}