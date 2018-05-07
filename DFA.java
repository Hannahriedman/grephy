/* CMPT 440
 * Final Project
 * Filename: DFA.java
 *
 */

import java.util.LinkedHashSet;
import java.util.Set;

public class DFA extends NFA {

    public DFA () {
      super();
    }

    public void nfatoDfa(NFA nfa) {
       this.start = nfa.start;
       this.stop = nfa.stop;
    }

    public String DFAtoDOT() {
        StringBuilder dotfile = new StringBuilder();
        Set<Integer> visited = new LinkedHashSet<>();
        dotfile.append("digraph dfa {\n");
        dotfile.append("  rankdir = LR;\n");
        dotfile.append("  size=\"8,5\"\n");
        dotfile.append("  node [shape = circle, height = 0.45];\n");
        toDOT_(start, dotfile, visited);
        dotfile.append("}\n");
        return dotfile.toString();
    }

    protected void toDOT_(DFA.State p, StringBuilder dotfile, Set<Integer> visited) {
        String label;
        if (visited.contains(p.stateNumber)){
            return;
        }
        visited.add(p.stateNumber);
        for (DFA.Edge e: p.edges) {
            if (e.label == DFA.FAIL){
                break;
            } else {
                label = String.valueOf(e.label);
            }
            dotfile.append("  " + p.stateNumber + " -> " + e.target.stateNumber + " [label=\"" + label + "\"];\n");
            toDOT_(e.target, dotfile, visited);
        }
    }
}
