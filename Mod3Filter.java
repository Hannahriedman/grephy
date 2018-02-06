 import java.io.*;

/**
 * A Java application to demonstrate the Mod3 class by
 * using it to filter the standard input stream.  Those
 * lines that are accepted by Mod3 are echoed to the
 * standard output.
 */
public class Mod3Filter {
  public static void main(String[] args) 
        throws IOException {

    Mod3 m = new Mod3(); // the DFA
    BufferedReader in =  // standard input
      new BufferedReader(new InputStreamReader(System.in));


    // Read and echo lines until EOF.
    System.out.println("Enter string to evaulate: ");
    String s = in.readLine();
    while (s!=null) {
      // exit condition
      if (s.toLowerCase().contains("exit") || s.toLowerCase().contains("quit") || s.toLowerCase().contains("end")) {
          break;
      }
      // help menu
      else if (s.toLowerCase().contains("help")) {
          System.out.println("\nMod3\nA Java application to demonstrate the Mod3 class" + 
            "by using it to filter the standard input stream.  Those" +
            "lines that are accepted by Mod3 are echoed to the" +
            "standard output.\n\n" +
            "Mod3: A deterministic finite-state automaton that" +
            "recognizes strings that are binary" +
            "representations of integers that are divisible" +
            "by 3.  Leading zeros are permitted, and the" +
            "empty string is taken as a representation for 0" +
            "(along with \"0\", \"00\", and so on).\n"
            );
      }
        
        m.reset();
        m.process(s);
        if (m.accepted()) {
              System.out.println("Accepted: " + s);
        }
        else {
              System.out.println("Rejected: " + s);
        }
        System.out.println("Enter string to evaulate: ");
        s = in.readLine();
    }
  }
}
