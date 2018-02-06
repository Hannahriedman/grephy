/**
 * A deterministic finite-state automaton that 
 * recognizes strings that are binary
 * representations of integers that are divisible
 * by 3.  Leading zeros are permitted, and the
 * empty string is taken as a representation for 0
 * (along with "0", "00", and so on).
 */
public class Mod3 {
  /* 
   * Constants q0 through q3 represent states, and
   * a private int holds the current state code.
   */
  private static final int q0 = 0;
  private static final int q1 = 1;
  private static final int q2 = 2;
  private static final int q3 = 3;

  private int state;


  /**
   * Perform the delta function and transition on the given char based on the
   * current state.
   * @param c the char to evaluate
   * @param s the current state
   * @return the integer of the new state after the symbol is evaluated
   */
  static private int delta(int s, char c) {
    switch (s) {
      case q0: switch (c) {
            case '0': return q0;
            case '1': return q1; 
            default: return q3;
          }
      case q1: switch (c) {
            case '0': return q2;
            case '1': return q0;
            default: return q3;
          }
      case q2: switch (c) {
            case '0': return q1;
            case '1': return q2; 
            default: return q3;
          }
      default: return q3;
    }
  }
  
  
   /**
   * Reset the current state to the start state.
   */
  public void reset() {
    state = q0;
  }


  /**
   * Make one transition on each char in the given string.
   * @param in the String to use
   */
  public void process(String in) {
    for (int i = 0; i < in.length(); i++) {
      char c = in.charAt(i);
      state = delta(state, c);
    }
  } 


  /**
   * Test whether the DFA accepted the string.
   * @return true iff the final state was accepting
   */
  public boolean accepted() {
    return state==q0;
  }
}
