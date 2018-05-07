/* CMPT 440
 * Final Project
 * Filename: NFA.java
 *
 * Sources:
 * http://matt.might.net/articles/implementation-of-nfas-and-regular-expressions-in-java/
 * http://www.jflap.org/tutorial/regular/index.html
 * https://www.programcreek.com/java-api-examples/index.php?source_dir=GATECH-master/CS3240/MiniRE/src/project/nfa/NFA.java#
 * https://github.com/akcieslak/NFA-Regex
 */


import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class NFA {
    public static final int FAIL = 0;
    public static final char EPSILON = 0;
    protected static int globalStateCounter = 0;

    public static class State {
        public final int stateNumber; // state number
        public final List<Edge> edges = new ArrayList<>();

        public State() {
            stateNumber = globalStateCounter++;
        }

        public void edge(char label, State target) {
            Edge e = new Edge(label, target);
            edges.add(e);

        }
        public void epsilon(State target) {
            edge(EPSILON,target);
        }
        public String toString() {
            return "s"+stateNumber;
        }
    }

    public static class Edge {
        final State target;
        char label;

        public Edge(char label, State target) {
            this.label = label;
            this.target = target;
        }
    }

    protected State start;
    protected State stop;

    public NFA() {
        start = new State();
        stop = new State();
    }

    public NFA(State start, State stop) {
        this.stop = stop;
        this.start = start;
    }

    public String NFAtoDOT() {
        StringBuilder dotfile = new StringBuilder();
        Set<Integer> visited = new LinkedHashSet<>();
        dotfile.append("digraph nfa {\n");
        dotfile.append("  rankdir = LR;\n");
        dotfile.append("  size=\"8,5\"\n");
        dotfile.append("  node [shape = circle, height = 0.45];\n");
        toDOT_(start, dotfile, visited);
        dotfile.append("}\n");
        return dotfile.toString();
    }

    protected void toDOT_(State p, StringBuilder dotfile, Set<Integer> visited) {
        String label;
        if (visited.contains(p.stateNumber)){
            return;
        }
        visited.add(p.stateNumber);
        for (Edge e: p.edges) {
            if (e.label == NFA.FAIL){
                label = "&epsilon;";
            } else {
                label = String.valueOf(e.label);
            }
            dotfile.append("  " + p.stateNumber + " -> " + e.target.stateNumber + " [label=\"" + label + "\"];\n");
            toDOT_(e.target, dotfile, visited);
        }
    }

    public static NFA atom(char label) {
        State a = new State();
        State b = new State();
        a.edge(label, b);
        return new NFA(a,b);
    }
}

