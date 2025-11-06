import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

/**
 * 
 */
public class SpellChecker{

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

    public static void main(String[] args) {
        WordValidation dictionary = new WordValidation("words.txt"); 

        if (args.length > 0) { //if command-line arguments are present
            argsMode(args, dictionary); //command-line argument mode
        } else {
            fileMode(dictionary); //file input mode
        }

    }
}