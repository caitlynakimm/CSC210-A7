import java.util.ArrayList;

public class SpellChecker{

    public static void argWords(String[] args){
        WordValidation dictionary = new WordValidation("words.txt"); 

        if (args.length > 0) { //if command-line arguments are present
            for (String word : args) { //processing each argument in args as a word to check
                if (!dictionary.containsWord(word)) {
                    ArrayList<String> nearMissWords = dictionary.nearMisses(word);
                    System.out.println("Not found: " + word);
                    System.out.println("  Suggestions: " + nearMissWords);
                } else {
                    System.out.println("'" + word + "' is spelled correctly.");
                }            
            }
        }  else {

        }
    }

    public static void fileWords(String filename){
        
    }

    public static void main(String[] args) {
        
    }
}