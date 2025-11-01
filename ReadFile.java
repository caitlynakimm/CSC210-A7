import java.io.*;
import java.util.Scanner;

/** Class containing a file reading demo */
public class ReadFile {
  /** The main method reads from a file and prints the contents. */
  public static void main(String[] args) {
    String filename = (args.length > 0) ? args[0] : "YUMA_2023.txt";
    Scanner file = null;
  try {
    file = new Scanner(new File(filename));
  } catch (FileNotFoundException e) {
    System.err.println("Cannot locate file.");
    System.exit(-1);
  }
  while (file.hasNextLine()) {
    String line = file.nextLine();
    String[] fields = line.split("\\s+");
    String date = fields[1];
    float temperature = Float.valueOf(fields[8]);
    System.out.println("On "+date+" the temperature was "+temperature+" degrees Celsius.");
  }
  file.close();
  }
}