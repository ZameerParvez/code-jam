import java.util.*;
import java.io.*;

public class Solution {
  private static int[] sIndicies;
  private static int numOfSs;
  private static StringBuilder p;
  private static int d;

  public static void main(String[] args) {
    Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
    int t = in.nextInt();  // t is number of test cases
    for (int i = 1; i <= t; ++i) {
      d = in.nextInt(); // d is total damage shield can handle
      p = new StringBuilder(in.next()); // p is pattern e.g "SCCSSC"
      System.out.println("Case #" + i + ": " + solve());
    }
    in.close();
  }

  public static String solve() {
    countSInP();
    if (numOfSs > d) {
      return "IMPOSSIBLE";
    } else {
      initArrays();
      int count = 0;
      return "" + countSwaps(p, numOfSs - 1, count);
    }
  }

  private static void countSInP() {
    int n = 0;
    for (int i = 0; i < p.length(); i++) {
      if (p.charAt(i) == 'S') {
        n++;
      }
    }

    numOfSs = n;
  }

  // finds indicies of Ss in p, subtracting indexes gives number of Cs between 
  private static void initArrays() {
    sIndicies = new int[numOfSs];
    int countIndex = 0;
    for (int i = 0; i < p.length(); i++) {
      if (p.charAt(i) == 'S') {
        sIndicies[countIndex] = i;
        countIndex++;
      }
    }
  }

  private static void swapChars(StringBuilder p, int i1, int i2) {
    char c = p.charAt(i1);
    p.setCharAt(i1, p.charAt(i2));
    p.setCharAt(i2, c);
  }

  private static int calculateTotalDamage(StringBuilder p) {
    int total = 0;
    for (int i = 0; i < numOfSs; i++) {
      total += Math.pow(2, sIndicies[i] - i);
    }
    
    return total;
  }

  // numS is i in ith S starting at 0 e.g. if there are 4 Ss then numS should start as 3
  public static int countSwaps(StringBuilder p, int numS, int count) {

    if (calculateTotalDamage(p) <= d) {
      return count;
    }
    
    int sIndex1 = sIndicies[numS];

    if (numS == sIndicies[numS]) {  // if this is true it means that there are no Cs before the S
      return count;        
    } else if (numOfSs != 1 && numS > 0 && sIndex1 == sIndicies[numS - 1] + 1) {  // Checks if Ss are next to each other unless it is the first S
      return countSwaps(p, numS - 1, count);
    } else {
      swapChars(p, sIndex1, sIndex1 - 1);
      sIndicies[numS]--;
      count++;
      if (numS == numOfSs - 1) {   // Checks if it is the last S
        return countSwaps(p, numS, count);
      } else {
        return countSwaps(p, numS + 1, count);
      }
    }
  }
}