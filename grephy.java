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

	public static void main(String[] args) throws IOException {
    BufferedReader input =  // standard input
      new BufferedReader(new InputStreamReader(System.in));

    // Read and echo lines until EOF.
    System.out.println("Regex:");
    String regex1 = input.readLine();
    //while (regex!=null) {
      System.out.println(regex1);
      regex1 = input.readLine();
    //}

	}

}
