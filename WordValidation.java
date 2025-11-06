import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Class implements spelling operations to validate words and suggest corrections
 */
public class WordValidation implements SpellingOperations {
    /** Dictionary contains valid words for spelling validation*/
    private HashSet<String> dictionary;

    /**
     * @param query the word to check
     * @return true if the query word is in the dictionary.
     */
    public boolean containsWord(String query) {
        return dictionary.contains(query.toLowerCase());
    }

    /**
     * Finds all valid words that are one edit away from query
     * Includes near miss types: deletions, insertions, subtitutions, transpositions, and splits
     * @param query the word to check
     * @return a list of all valid words that are one edit away from the query
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
                String firstWord = queryLowerCase.substring(0, i);
                String secondWord = queryLowerCase.substring(i);

                if (containsWord(firstWord) && containsWord(secondWord)) { //check each word is in dictionary
                    similarWords.add(firstWord + " " + secondWord); //add new string with both words and space inbetween them to similarWords
                }
            }

        }
        return new ArrayList<>(similarWords);
    }

    /**
     * Constructor reads words from file into HashSet
     * Words are converted to lowercase and non-alphabetic characters (except apostrophes) are removed
     * @param filename path to the dictionary file containing words
     * @throws FileNotFoundException if specified file isn't found
     */
    public WordValidation(String filename) {
        dictionary = new HashSet<String>();

        try {
            Scanner fileScanner = new Scanner(new File(filename));
            while (fileScanner.hasNextLine()) {
                //converts alphabetic characters in the word to lowercase
                String word = fileScanner.nextLine().trim().toLowerCase();
                //remove any characters that aren't lowercase letters in the word
                word = word.replaceAll("[^a-z']", "");
                
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

    /**
     * Main method for testing WordValidation class functionality
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        //add each word from "words.txt" file into dictionary
        WordValidation dictionary = new WordValidation("words.txt");
        
        //testing exception in constructor
        //WordValidation noDict = new WordValidation("hi.txt");

        //checking if dictionary contains valid word
        //System.out.println(dictionary.containsWord("cattle"));
        
        System.out.println();
        System.out.println("///////////////TEST FOR DELETION//////////////");
        System.out.println();

        //test cases for near miss type deletion plus misspelled words with apostrophes
        String[] testWordsOne = {"catttle", "potatoo", "coookie", "Jaimie", "IIreland"};
        
        for (String word : testWordsOne) {
            System.out.println("Does dictionary contain " + word + "?: " + dictionary.containsWord(word));
            ArrayList<String> nearMissWordsOne = dictionary.nearMisses(word);
            System.out.println("Near misses for " + word + " are: " + nearMissWordsOne);
            System.out.println();
        }

        System.out.println("///////////////TEST FOR INSERTION//////////////");
        System.out.println();

        //test cases for near miss type insertion
        String[] testWordsTwo = {"catle", "potao", "ookie", "Jame", "Irelan"};
        
        for (String word : testWordsTwo) {
            System.out.println("Does dictionary contain " + word + "?: " + dictionary.containsWord(word));
            ArrayList<String> nearMissWordsTwo = dictionary.nearMisses(word);
            System.out.println("Near misses for " + word + " are: " + nearMissWordsTwo);
            System.out.println();
        }
        
        System.out.println("///////////////TEST FOR SUBSTITUTION//////////////");
        System.out.println();

        //test cases for near miss type substitution
        String[] testWordsThree = {"caxtle", "cattle'x", "cattle''", "cabdy", "dof"};
        
        for (String word : testWordsThree) {
            System.out.println("Does dictionary contain " + word + "?: " + dictionary.containsWord(word));
            ArrayList<String> nearMissWordsThree = dictionary.nearMisses(word);
            System.out.println("Near misses for " + word + " are: " + nearMissWordsThree);
            System.out.println();
        }

        System.out.println("///////////////TEST FOR TRANSPOSITION//////////////");
        System.out.println();

        //test cases for near miss type transposition
        String[] testWordsFour = {"cattel", "cattles'", "bugrer", "aMry", "plnat"};
        
        for (String word : testWordsFour) {
            System.out.println("Does dictionary contain " + word + "?: " + dictionary.containsWord(word));
            ArrayList<String> nearMissWordsFour = dictionary.nearMisses(word);
            System.out.println("Near misses for " + word + " are: " + nearMissWordsFour);
            System.out.println();
        }

        System.out.println("///////////////TEST FOR SPLIT//////////////");
        System.out.println();

        //test cases for near miss type split
        String[] testWordsFive = {"cattell", "iam", "peanutand", "darkchocolate", "init", "badminton"};
        
        for (String word : testWordsFive) {
            System.out.println("Does dictionary contain " + word + "?: " + dictionary.containsWord(word));
            ArrayList<String> nearMissWordsFive = dictionary.nearMisses(word);
            System.out.println("Near misses for " + word + " are: " + nearMissWordsFive);
            System.out.println();
        }

        //trying to debug program to figure out how to not to let "le", "catt", and "ell" be considered valid words
        // System.out.println("Does dictionary contain 'cat'?: " + dictionary.containsWord("cat"));
        // System.out.println("Does dictionary contain 'le'?: " + dictionary.containsWord("le"));
        // System.out.println("Does dictionary contain 'catt'?: " + dictionary.containsWord("catt"));
        // System.out.println("Does dictionary contain 'ell'?: " + dictionary.containsWord("ell"));

    }
}
