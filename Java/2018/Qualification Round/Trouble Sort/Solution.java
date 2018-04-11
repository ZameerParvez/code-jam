import java.util.*;
import java.io.*;

public class Solution {

  public static void main(String[] args) {
    Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
    int t = in.nextInt();  // t is number of test cases
    for (int i = 1; i <= t; ++i) {
      int n = in.nextInt(); // n is number of elements in array
      int[] list = new int[n];
      for (int j = 0 ; j < n; j++) {
          list[j] = in.nextInt();
      } 
      System.out.println("Case #" + i + ": " + solve(list));
    }
    in.close();
  }

  private static String solve(int[] l) {
    List<Integer>  troubleSortedList = mimicedTroubleSort(l);
    
    int indexOfFirstError = -1;
    for (int i = 0; i < troubleSortedList.size() - 1; i++) {
        if (troubleSortedList.get(i) > troubleSortedList.get(i + 1)) {
            indexOfFirstError = i;
            break;
        }
    }

    if (indexOfFirstError > -1) {
        return "" + indexOfFirstError;
    } else {
        return "OK";
    }
  }

  private static List<Integer> mimicedTroubleSort(int[] l) {
    List<Integer> l1 = new ArrayList<>();
    List<Integer> l2 = new ArrayList<>();
    for (int i = 0; i < l.length; i++) {
        if (i % 2 == 0) {
            l2.add(l[i]);
        } else {
            l1.add(l[i]);
        }
    }

    Collections.sort(l1);
    Collections.sort(l2);

    List<Integer> troubleSortedList = new ArrayList();
    boolean l1Bigger = l1.size() > l2.size();
    int n = (l1Bigger) ? l2.size() : l1.size();
    for (int i = 0; i < n; i++) {
        troubleSortedList.add(l2.get(i));
        troubleSortedList.add(l1.get(i));
    }
    if (l1Bigger) {
        troubleSortedList.add(l1.get(n));
    } else if (l2.size() > l1.size()) {
        troubleSortedList.add(l2.get(n));
    }
    
    return troubleSortedList;
  }

  private static void troubleSort(int[] l) {
      boolean done = false;
      int count = 0;
      while (!done) {
          done = true;
          count++;
          for (int i = 0; i < l.length - 2; i++) {
              if (l[i] > l[i + 2]) {
                  done = false;
                  int temp = l[i];
                  l[i] = l[i + 2];
                  l[i + 2] = temp;
              }
          }
      }
  }

}