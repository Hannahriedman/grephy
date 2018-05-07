/* CMPT 440
 * Final Project
 * Filename: grephy.java
 * @author Hannah Riedman
 *
 * grep utility that searches files for regular expression
 * pattern matches and produces dot graph file output for
 * the automata used in the matching computation.
 *
 * Sources:
 * http://www.homeandlearn.co.uk/java/read_a_textfile_in_java.html
 * http://hanoo.org/index.php?article=how-to-generate-logs-in-java
 *
 */

import java.io.*;
import java.util.logging.Logger;
import java.util.logging.Level;


public class grephy {

    private final static Logger LOGGER = Logger.getLogger(grephy.class.getName());// "loggerinfo");

    public static class WriteFile {
        private final String outfile;
        static StringBuilder NFAfile = new StringBuilder();
        static StringBuilder DFAfile = new StringBuilder();
        private final int NFA = 0;
        private final int DFA = 1;

        public WriteFile(String file_path) {
            outfile = file_path;
        }

        public NFA ProcessNFA (String[] regex, int i) {
            RegexParser parser = new RegexParser(regex[i]);
            NFA nfa1 = parser.regex();
            String text = nfa1.NFAtoDOT();
            NFAfile.append(text);
            return nfa1;
        }

        public void ProcessDFA (NFA nfa) {
            DFA dfa1 = new DFA();
            dfa1.nfatoDfa(nfa);
            String text = dfa1.DFAtoDOT();
            DFAfile.append(text);
        }

        public void OutputFile(int nfaordfa) {
            try {
                File file = new File(outfile);
                FileWriter fileWriter = new FileWriter(file);
                if (nfaordfa == NFA) {
                    LOGGER.log(Level.INFO,"nfafile:"+NFAfile.toString());
                    fileWriter.write(NFAfile.toString());
                } else {
                    LOGGER.log(Level.INFO,"dfafile:"+DFAfile.toString());
                    fileWriter.write(DFAfile.toString());
                }
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                LOGGER.log(Level.WARNING,e.getMessage());
            }

        }
    }


    public static class ReadFile {
        private String file;

        public ReadFile(String file_path) {
            file = file_path;
        }

        public String[] OpenFile() throws IOException {
            FileReader fr = new FileReader(file);
            BufferedReader input = new BufferedReader(fr);
            int numberofLines = readLines();
            String[] data = new String[numberofLines];

            for (int i = 0; i < numberofLines; i++) {
                data[i] = input.readLine();
                LOGGER.log(Level.INFO,"regex:"+data[i]);
            }
            input.close();
            return data;
        }

        int readLines() throws IOException {
            FileReader fileToRead = new FileReader(file);
            BufferedReader input = new BufferedReader(fileToRead);

            int numberOfLines = 0;

            while (input.readLine() != null) {
                numberOfLines++;
            }
            input.close();
            return numberOfLines;
        }

    }

    public static void main(String[] args) throws IOException {
        LOGGER.log(Level.INFO,"Beginning of Log");
        BufferedReader in =  // standard input
                new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Welcome to Grephy! Enter in a regex file (with path)");
        System.out.println("Followed by a path and name for your NFA and DFA files");
        System.out.println("To see a proper file format, type 'example'");
        String s = in.readLine();
        LOGGER.log(Level.INFO,"line from user:"+s);
        while (s != null) {
            // exit condition
            if (s.toLowerCase().contains("exit") || s.toLowerCase().contains("quit")
                    || s.toLowerCase().contains("end")) {
                break;
            } else if (s.toLowerCase().contains("example")){
                System.out.println("/Users/hannahriedman/Documents/workspace/Grephy1/src/test1.txt " +
                        "/Users/hannahriedman/Documents/workspace/Grephy1/src/nfa.dot " +
                        "/Users/hannahriedman/Documents/workspace/Grephy1/src/dfa.dot\n");
            } else {
                String[] line = s.split(" ");
                String regex1 = line[0];
                String NFA_file = line[1];
                String DFA_file = line[2];
                try {
                    ReadFile file = new ReadFile(regex1);
                    String[] lines = file.OpenFile();
                    WriteFile outputNFA = new WriteFile(NFA_file);
                    WriteFile outputDFA = new WriteFile(DFA_file);
                    LOGGER.log(Level.INFO,"Amount of regex:"+lines.length);
                    for (int i=0; i < lines.length;i++) {
                        NFA nfa = outputNFA.ProcessNFA(lines,i);
                        outputDFA.ProcessDFA(nfa);
                    }
                    outputNFA.OutputFile(0);
                    outputDFA.OutputFile(1);

                    System.out.println("NFA file at: " + NFA_file);
                    System.out.println("DFA file at: " + DFA_file);
                    System.out.println("Thanks for using Grephy!");
                    System.out.println("---------------------------");
                    System.out.println("We are currently working to have a visual representation available for dot files,");
                    System.out.println("Until then, visit http://www.webgraphviz.com/ to view your files. :)");
                    break;
                } catch (IOException e) {
                    LOGGER.log(Level.WARNING,e.getMessage());
                    LOGGER.log(Level.WARNING,"Error Reading File");
                }
            }
            System.out.println("Enter in file name (with path)");
            s = in.readLine();
        }

    }
}


