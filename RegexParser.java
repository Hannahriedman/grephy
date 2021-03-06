/* CMPT 440
 * Final Project
 * Filename: RegexParser.java
 *
 * Sources:
 * http://matt.might.net/articles/implementation-of-nfas-and-regular-expressions-in-java/
 * http://www.jflap.org/tutorial/regular/index.html
 * https://www.programcreek.com/java-api-examples/index.php?source_dir=GATECH-master/CS3240/MiniRE/src/project/nfa/NFA.java#
 * https://github.com/akcieslak/NFA-Regex
 */

public class RegexParser {
    protected final char[] pattern;	// the regex pattern to parse
    protected int p = 0;	 		// pattern[p] is next char to match
    protected final int n;

    public RegexParser(String pattern) {
        this.pattern = pattern.toCharArray();
        n = this.pattern.length;
    }

    public NFA regex() {
        NFA r;
        r = sequence();

        if (r == null) {
            r = NFA.atom(NFA.EPSILON);
        }

        if (look() == '|'){
            NFA tmp1 = new NFA();
            tmp1.start.epsilon(r.start);
            r.stop.epsilon(tmp1.stop);
            r = tmp1;
        }

        while ( p<n && look()=='|' ) {
            match('|');
            if (look() == ')'){
                NFA tmp = NFA.atom(NFA.EPSILON);
                r.start.epsilon(tmp.start);
                tmp.stop.epsilon(r.stop);
                return r;
            }
            NFA x = sequence();
            r.start.epsilon(x.start); // connect the next sequence
            x.stop.epsilon(r.stop);	// sequence connects to stop node
        }

        return r;
    }

    public NFA sequence() {
        NFA r;
        if (isElem() || look() == '('){
            r = closure();
            NFA k = sequence();
            if (k != null) {
                r.stop.epsilon(k.start);
                return new NFA(r.start, k.stop);
            } else {
                return r;
            }
        }

        else {
            return null;
        }
    }

    public NFA closure() {
        NFA r;
        r = element();
        while (look() == '*'){
            match('*');
            NFA tmp = new NFA();
            tmp.start.epsilon(r.start);
            r.stop.epsilon(tmp.start);
            r.stop.epsilon(tmp.stop);
            tmp.start.epsilon(tmp.stop);
            r = tmp;
        }
        return r;
    }

    public NFA element() {
        NFA r;
        char k;
        if (isLetter()){
            k = pattern[p];
            match(k);
            r = NFA.atom(k);
            return r;
        } if (look() == '(') {
            match('(');
            r = regex();
            match(')');
            return r;
        } else  {
            return null;
        }
    }

    public boolean isLetter(){
        return look() <= 'z' && look() >= 'a';
    }

    public boolean isElem() {
        return look() >= 97 && look() <= 122;
    }

    public void match(char c) {
        if (look() == c) {
            p++;
        }
    }

    public char look(){
        if (p >= pattern.length) return (char) -1;
        return pattern[p];
    }

}

