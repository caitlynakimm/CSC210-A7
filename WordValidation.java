import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileNotFoundException;

import a5.SpellingOperations;

public class WordValidation implements SpellingOperations {
    private HashSet<String> dictionary;
    /**
     *  @param query the word to check
     *  @return true if the query word is in the dictionary.
     */
    public boolean containsWord(String query) {
        return dictionary.contains(query.toLowerCase());
    }

    /**
     *  @param query the word to check
     *  @return a list of all valid words that are one edit away from the query
     */
    public ArrayList<String> nearMisses(String query) {
        HashSet<String> similarWords = new HashSet<>();
        String queryLowerCase = query.toLowerCase();

        if (!containsWord(query)){
            //for deletions
            for (int i = 0; i < queryLowerCase.length(); i++) {
                String possibleWord = queryLowerCase.substring(0, i) + queryLowerCase.substring(i + 1);
                if (containsWord(possibleWord)) {
                    similarWords.add(possibleWord); //HashSet automatically rejects duplicate words
                }
            }

            //for insertions
            for (int i = 0; i <= queryLowerCase.length(); i++) {
                for (char letter = 'a'; letter <= 'z'; letter++) {
                    String possibleWord = queryLowerCase.substring(0, i) + letter + queryLowerCase.substring(i);
                    if (containsWord(possibleWord)) {
                        similarWords.add(possibleWord);
                    }
                }
            }

            //for substitutions
            for (int i = 0; i < queryLowerCase.length(); i++) {
                for (char letter = 'a'; letter <= 'z'; letter++) {
                    if (letter != queryLowerCase.charAt(i)) { //checking that the particular character of the word is being replaced by a different character
                        String possibleWord = queryLowerCase.substring(0, i) + letter + queryLowerCase.substring(i + 1);
                        if (containsWord(possibleWord)) {
                            similarWords.add(possibleWord);
                        }
                    }
                }
            }

            //for transpositions
            for (int i = 0; i < queryLowerCase.length() - 1; i++) {
                char chars[] = queryLowerCase.toCharArray();
                
                //swap characters at positions i and i + 1
                char temp = chars[i];
                chars[i] = chars[i + 1];
                chars[i + 1] = temp;
                
                String possibleWord = new String(chars);
                if (containsWord(possibleWord)) {
                    similarWords.add(possibleWord);
                }
            }

            //for splits
            for (int i = 1; i < queryLowerCase.length(); i++) { //can start at index 1 since substring(0,0) + " " would result in a space before the word which wouldn't result in a valid word 
                String possibleWord = queryLowerCase.substring(0, i) + " " + queryLowerCase.substring(i);
                
                if (containsWord(possibleWord)) {
                    similarWords.add(possibleWord);
                }
            }

        }
        return new ArrayList<>(similarWords);
    }

    /**
     * Constructor reads words from file into HashSet
     * @param filename
     */
    public WordValidation(String filename) {
        dictionary = new HashSet<String>();

        try {
            Scanner fileScanner = new Scanner(new File(filename));
            while (fileScanner.hasNextLine()) {
                //only keep alphanumeric words and convert them to lowercase
                String word = fileScanner.next().replaceAll("^[a-zA-Z0-9]+$", "").toLowerCase();
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
        //tests for each near miss case goes here
        //also check if need commented out import
        String testing[] = {"catttle", "catle", "caxtle", "cattel", "cattell"};
        ArrayList<String> closeWords = nearMisses(testing);
    }
}
