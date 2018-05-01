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

public class grephy {

    public static class WriteFile {
        private final String outfile;
        static StringBuilder dotfile = new StringBuilder();

        public WriteFile(String file_path) {
            outfile = file_path;
        }

        public NFA ProcessNFA (String[] regex, int i) {
            //NFA nfa1 = new NFA();
            RegexParser parser = new RegexParser(regex[i]);
            NFA nfa1 = parser.regex();
            System.out.println(nfa1.toDOT());
            String text = nfa1.toDOT();
            dotfile.append(text);
            return nfa1;
        }

        public void ProcessDFA (NFA nfa) {
            //DFA dfa1 = new DFA();
            //dfa1.streamIn(nfa);

            //dotfile.append(text);
        }

        public void OutputFile() {
            try {
                File file = new File(outfile);
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(dotfile.toString());
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
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
                System.out.println(data[i]);
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

    /**
     * TODO create logger/debug for system.out prints
     */
    public static void main(String[] args) throws IOException {
        BufferedReader in =  // standard input
                new BufferedReader(new InputStreamReader(System.in));

        //Logger logger = Logger.getLogger("grephy");
        //logger.setLevel(Level.DEBUG);
        // Read and echo lines until EOF.
        System.out.println("Welcome to Grephy! Enter in a regex file (with path)");
        System.out.println("Followed by a path and name for your NFA and DFA files");
        System.out.println("To see a proper file format, type 'example'");
        String s = in.readLine();
        while (s != null) {
            // exit condition
            if (s.toLowerCase().contains("exit") || s.toLowerCase().contains("quit")
                    || s.toLowerCase().contains("end")) {
                break;
            } else if (s.toLowerCase().contains("example")){
                System.out.println("/Users/hannahriedman/Documents/workspace/Grephy1/src/test1.txt " +
                        "/Users/hannahriedman/Documents/workspace/Grephy1/src/dfa.dot " +
                        "/Users/hannahriedman/Documents/workspace/Grephy1/src/dfa1.dot\n");
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
                    System.out.println(lines.length);
                    for (int i=0; i < lines.length;i++) {
                        NFA nfa = outputNFA.ProcessNFA(lines,i);
                        outputNFA.ProcessDFA(nfa);
                    }
                    outputNFA.OutputFile();
                    outputDFA.OutputFile();

                    System.out.println("NFA file at: " + NFA_file);
                    System.out.println("DFA file at: " + DFA_file);
                    System.out.println("Thanks for using Grephy!");
                    System.out.println("---------------------------");
                    System.out.println("We are currently working to have a visual representation available for dot files,");
                    System.out.println("Until then, visit http://www.webgraphviz.com/ to view your files. :)");
                    break;
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    System.out.println("Error Reading File");
                }
            }
            System.out.println("Enter in file name (with path)");
            s = in.readLine();
        }

    }
}


