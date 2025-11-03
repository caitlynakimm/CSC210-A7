import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileNotFoundException;

//import a5.SpellingOperations;

public class WordValidation implements SpellingOperations{
    private HashSet<String> dictionary;
    /**
     *  @param query the word to check
     *  @return true if the query word is in the dictionary.
     */
    public boolean containsWord(String query) {
        return (dictionary.contains(query)) ? true : false;
    }

    /**
     *  @param query the word to check
     *  @return a list of all valid words that are one edit away from the query
     */
    public ArrayList<String> nearMisses(String query) {
        ArrayList<String> similarWords = new ArrayList<String>();

        if (!containsWord(query)){
            //if (query.remove(any letter) && if that similar word is in dictionary)
                //add similar word to similarWords list
            //if (query.insert(letter first, after any letter, or last) && check if that similar word is in dictionary)
                //add similar word to similarWords list
            //substitution
            }
        } else {

        }
        return similarWords;
    }

    /**
     * Constructor reads words from file into HashSet
     * @param filename
     */
    public WordValidation(String filename) {
        dictionary = new HashSet<String>();

        try {
            //delimiter set to match one or more non-alphanumeric characters
            //splits input by any puncutation marks or special characters
            Scanner fileScanner = new Scanner(new File(filename)).useDelimiter(Pattern.compile("^[a-zA-Z0-9]+$"));
            while (fileScanner.hasNextLine()) {
                String word = fileScanner.nextLine().trim().toLowerCase();
                if (!word.isEmpty()) {
                    dictionary.add(word);
                }
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Cannot locate file.");
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        //tests for each near miss case goes here i think
        //also check if need commented out import
    }
}
