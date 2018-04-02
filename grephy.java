/* CMPT 440
 * Final Project
 * Filename: grephy.java
 * @author Hannah Riedman
 *
 * grep utility that searches files for regular expression
 * pattern matches and produces dot graph file output for
 * the automata used in the matching computation.
 */

import java.io.*;

//import java.util.regex;

public class Grephy {

  public static class ReadFile {
    private String file;

    public ReadFile (String file_path) {
      file = file_path;
    }

    public String[] OpenFile() throws IOException {
      FileReader fr = new FileReader(file);
      BufferedReader input = new BufferedReader(fr);
      int numberofLines = readLines();
      String[] data = new String[numberofLines];

      for (int i = 0; i < numberofLines; i++) {
        data[i] = input.readLine();
        System.out.println(data[i]);
      }
      input.close();
      return data;
    }

    int readLines() throws IOException {
      FileReader fileToRead = new FileReader(file);
      BufferedReader input = new BufferedReader(fileToRead);

      String line;
      int numberOfLines = 0;

      while ((line = input.readLine())!= null) {
        numberOfLines++;
      }
      input.close();
      return numberOfLines;
    }

  }
 /**
  * TODO clean up code (especially ReadFile)
  * TODO add in parameters to check for all parameters
  * TODO add in something to account for directory?
  * TODO ^ if so this must account for System (Windows or Mac)
  * TODO create logger/debug for system.out prints
  * TODO start creating files for NFA, DFA, etc 
  *
 */
	public static void main(String[] args) throws IOException {
    BufferedReader in =  // standard input
      new BufferedReader(new InputStreamReader(System.in));


    // Read and echo lines until EOF.
    System.out.println("Welcome to Grephy!");
    String s = in.readLine();
    while (s!=null) {
      // exit condition
      if (s.toLowerCase().contains("exit") || s.toLowerCase().contains("quit") || s.toLowerCase().contains("end")) {
          break;
      } else {
        String file_name = s;
        try {
          ReadFile file = new ReadFile(file_name);
          String[] lines = file.OpenFile();
        } catch (IOException e) {
          System.out.println(e.getMessage());
          System.out.println("hello");
        }
      }
      System.out.println("Welcome to Grephy!");
      s = in.readLine();
    }
    System.out.println("hello");
    //String file_name = "/Users/hannahriedman/Documents/workspace/Grephy1/src/test1.txt";


    // Read and echo lines until EOF.
  //  System.out.println("Regex:");
    //String regex1 = input.readLine();
    //while (regex!=null) {
    //  System.out.println(regex1);
    //  regex1 = input.readLine();
    //}

	}

}
