import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SavingTheUniverse {

	public static int countSwitches(List<String> engines, List<String> queries) {
		int numOfSwitches = 0;
		
		String current = getNextEngine(engines, queries);
		for (int i = 0; i < queries.size(); i++) {
			if (queries.get(i).equals(current)) {
				numOfSwitches++;
				current = getNextEngine(engines, queries.subList(i, queries.size()));
			}
		}
		
		return numOfSwitches;
	}

	public static String getNextEngine(List<String> engines, List<String> queries) {
		List<String> enginesOrder = new ArrayList<>();
		for (int i = 0; i < queries.size(); i++) {
			if (!enginesOrder.contains(queries.get(i))) {
				enginesOrder.add(queries.get(i));
			}
		}
		
		for (String i : engines) {
			if (!enginesOrder.contains(i)) {
				enginesOrder.add(i);
			}
		}
		
		return enginesOrder.get(enginesOrder.size() - 1);
	}

	public static void main(String... args) {
		Scanner s = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		
		int numOfTests = Integer.parseInt(s.nextLine());
		for (int currentTest = 0; currentTest < numOfTests; currentTest++) {
			int numOfSearchEngines = Integer.parseInt(s.nextLine());
			List<String> searchEngines = new ArrayList<>();
			for (int i = 0; i < numOfSearchEngines; i++) {
				searchEngines.add(s.nextLine());
			}
			
			int numOfQueries = Integer.parseInt(s.nextLine());
			List<String> queries = new ArrayList<>();
			for (int i = 0; i < numOfQueries; i++) {
				queries.add(s.nextLine());
			}
			
			int numOfSwitches = countSwitches(searchEngines, queries);
			System.out.printf("case #%d: %d\n", currentTest + 1, numOfSwitches);
		}
		s.close();
	}
}
