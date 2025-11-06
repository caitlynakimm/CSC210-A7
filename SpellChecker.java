import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

/**
 * Class that is a spell checker program that can operate in command-line argument mode or file input mode
 * Finds misspelled words and suggests similar valid words
 */
public class SpellChecker{

    /**
     * Processes words provided as command-line arguments and checks spelling
     * @param args array of words to check
     * @param dictionary the dictionary used for word validation
     */
    public static void argsMode(String[] args, WordValidation dictionary){
        for (String word : args) { //processing each argument in args as a word to check
            if (!dictionary.containsWord(word)) {
                ArrayList<String> nearMissWords = dictionary.nearMisses(word);
                System.out.println("Not found: " + word);
                System.out.println("  Suggestions: " + nearMissWords);
            } else {
                System.out.println("'" + word + "' is spelled correctly.");
            }         
        }
    }

    /**
     * Processes text input from a file and checks spelling of all words
     * Words are cleaned by removing non-alphabetic characters and converting letters to lowercase
     * @param dictionary the dictionary used for word validation
     */
    public static void fileMode(WordValidation dictionary){
        Scanner fileScanner = new Scanner(System.in);
        HashSet<String> misspelledWords = new HashSet<>();

        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();

            String[] words = line.split("\\s+");

            for (String word : words) {
                String cleanedWord = word.replaceAll("^[^a-zA-Z']+|[^a-zA-Z']+$", "").toLowerCase();
                
                if (!dictionary.containsWord(cleanedWord) && !misspelledWords.contains(cleanedWord)) {
                    System.out.println("'" + cleanedWord + "' wasn't found.");
                    ArrayList<String> nearMissWords = dictionary.nearMisses(cleanedWord);
                    System.out.println("    Suggestions: " + nearMissWords);
                    System.out.println();

                    misspelledWords.add(cleanedWord); //save new misspelled word
                }
            }
            
        }
        fileScanner.close();
    }

    /**
     * Main method used as entry point for the spell checker program
     * Works in command-line argument mode (if arguments are provided) or else in file input mode
     * @param args command line arguments (words to check if provided)
     */
    public static void main(String[] args) {
        WordValidation dictionary = new WordValidation("words.txt"); 

        if (args.length > 0) { //if command-line arguments are present
            argsMode(args, dictionary); //command-line argument mode
        } else {
            fileMode(dictionary); //file input mode
        }

    }
}