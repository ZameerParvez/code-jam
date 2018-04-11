import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Solution {

	private static List<List<Integer>> getTimes(Scanner s, int numOfTrips) {
		List<List<Integer>> times = new ArrayList<List<Integer>>();
		List<Integer> departuresAtCurrent = new ArrayList<>();
		List<Integer> arrivalsAtOther = new ArrayList<>();
		
		for (int j = 0; j < numOfTrips; j++) {
			String[] temp = s.next().split(":");
			departuresAtCurrent.add(60 * Integer.valueOf(temp[0]) + Integer.valueOf(temp[1]));
			temp = s.next().split(":");
			arrivalsAtOther.add(60 * Integer.valueOf(temp[0]) + Integer.valueOf(temp[1]));
		}
	
		Collections.sort(departuresAtCurrent);
		Collections.sort(arrivalsAtOther);
		
		times.add(departuresAtCurrent);
		times.add(arrivalsAtOther);
		
		return times;
	}
	
	public static List<Integer> getTrainsNeeded(List<Integer> departuresFromA, List<Integer> arrivalsAtA, List<Integer> departuresFromB, List<Integer> arrivalsAtB, int turnTime) {
		int numStartA = 0;
		int numStartB = 0;
		
		// For all departures from station B check if a train is available, if there is not then increment the counter for trains starting at B
        while (departuresFromB.size() > 0) {
            if (arrivalsAtB.size() == 0 || arrivalsAtB.get(0) + turnTime > departuresFromB.get(0)) {
            	numStartB++;
            } else {
                arrivalsAtB.remove(0);
            }
            departuresFromB.remove(0);
        }
		
        // For all departures from station A check if a train is available, if there is not then increment the counter for trains starting at A
        while (departuresFromA.size() > 0) {
            if (arrivalsAtA.size() == 0 || arrivalsAtA.get(0) + turnTime > departuresFromA.get(0)) {
            	numStartA++; 
            } else {
                arrivalsAtA.remove(0);
            }
            departuresFromA.remove(0);
        }
        
        List<Integer> numTrains = new ArrayList<>();
        numTrains.add(numStartA);
        numTrains.add(numStartB);
        return numTrains;
	}
		
	public static void main(String... args) {
		Scanner s = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		
		int numOfCases = s.nextInt();
		for(int i = 0; i < numOfCases; i++) {
			int turnTime = s.nextInt();
			int numTimesA = s.nextInt();
			int numTimesB = s.nextInt();
			
			List<List<Integer>> stationA = getTimes(s, numTimesA);
			List<List<Integer>> stationB = getTimes(s, numTimesB);
			
			List<Integer> dFromA = stationA.get(0);
			List<Integer> aAtB = stationA.get(1);
			List<Integer> dFromB = stationB.get(0);
			List<Integer> aAtA = stationB.get(1);
			
			List<Integer> numTrains = getTrainsNeeded(dFromA, aAtA, dFromB, aAtB, turnTime);
			System.out.printf("Case #%d: %d %d\n", i + 1, numTrains.get(0), numTrains.get(1));

		}
		s.close();
	}

// The following code was my first attempt to find a solution to the problem
// it has been commented out because it does not give the most optimal number of trains needed

/*
	private static void getTimes(Scanner s, List<List<Integer>> times, int numOfTrips) {
		for (int j = 0; j < numOfTrips; j++) {
			String[] temp = s.next().split(":");
			List<Integer> t = new ArrayList<>();
			t.add(60 * Integer.valueOf(temp[0]) + Integer.valueOf(temp[1]));
			temp = s.next().split(":");
			t.add(60 * Integer.valueOf(temp[0]) + Integer.valueOf(temp[1]));
			times.add(t);
		}
		Collections.sort(times, departureComparator);
		System.out.println(times.toString());
	}
	
	public static Comparator<List<Integer>> departureComparator = new Comparator<List<Integer>>() {
		public int compare(List<Integer> l1, List<Integer> l2) {
			Integer a1 = l1.get(0);
			Integer a2 = l1.get(1);
			Integer b1 = l2.get(0);
			Integer b2 = l2.get(1);
			if ((a1 - b1)%1 <= (a2 - b2)%1) {
				return a2.compareTo(b2);
			} else {
				return a1.compareTo(b1);
			}
		}
	};
	
	public static List<Integer> getTrainsNeeded(List<List<Integer>> AtoB, List<List<Integer>> BtoA, int turnTime) {
		int numStartA = 0;
		int numStartB = 0;
		
		List<Integer> trainsAtA = new ArrayList<Integer>();
		List<Integer> trainsAtB = new ArrayList<Integer>();
		List<Integer> currentTrains = trainsAtA;
		List<Integer> otherTrains = trainsAtB;

		List<List<Integer>> currentStation = AtoB;
		boolean notFinished = true;
		while (notFinished) {
			boolean notDone = true;
			if (AtoB.isEmpty()) {
				currentStation = BtoA;
				currentTrains = trainsAtB;
				otherTrains = trainsAtA;
				numStartB++;
			} else if (BtoA.isEmpty()) {
				currentStation = AtoB;
				currentTrains = trainsAtA;
				otherTrains = trainsAtB;
				numStartA++;
			} else if (AtoB.isEmpty() && BtoA.isEmpty()) {
				notDone = false;
				notFinished = false;	
			} else {
				List<Integer> a = AtoB.get(0);
				List<Integer> b = BtoA.get(0);
				if (departureComparator.compare(a, b) < 0) {
					currentStation = AtoB;
					currentTrains = trainsAtA;
					otherTrains = trainsAtB;
					numStartA++;
				} else if (departureComparator.compare(a, b) >= 0) {
					currentStation = BtoA;
					currentTrains = trainsAtB;
					otherTrains = trainsAtA;
					numStartB++;
				}
			}
			
			otherTrains.add(currentStation.get(0).get(1) + turnTime);
			currentStation.remove(0);
			
			// check first time, if a train is unavailable add it, then remove
			// the time and then move the train to the other station, then check
			// if can arrive on time to depart at the other station. repete.
			while (notDone) {
				for (int i = 0; i <= currentTrains.size(); i++) {
					if (!currentStation.isEmpty() && !currentTrains.isEmpty() && i < currentTrains.size()
							&& currentTrains.get(i) <= currentStation.get(0).get(0)) {
						currentTrains.set(i, currentStation.get(0).get(1) + turnTime);
						otherTrains.add(currentTrains.get(i));
						currentTrains.remove(i);
						currentStation.remove(0);
						break;
					} else if (i == currentTrains.size()) {
						notDone = false;					
					}
				}
				
				if (currentStation == AtoB) {
					currentStation = BtoA;
					currentTrains = trainsAtB;
					otherTrains = trainsAtA;
				} else {
					currentStation = AtoB;
					currentTrains = trainsAtA;
					otherTrains = trainsAtB;
				}
			}
			if (AtoB.isEmpty() && BtoA.isEmpty()) {
				notFinished = false;
			}
		}
		List<Integer> count = new ArrayList<>();
		count.add(numStartA);
		count.add(numStartB);
		return count;
	}
		
	public static void main(String... args) {
		Scanner s = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		
		int numOfCases = s.nextInt();
		for(int i = 0; i < numOfCases; i++) {
			int turnAroundTime = s.nextInt();
			int numTripsAB = s.nextInt();
			int numTripsBA = s.nextInt();
			
			List<List<Integer>> tripsAB = new ArrayList<>();
			getTimes(s, tripsAB, numTripsAB);
			
			List<List<Integer>> tripsBA = new ArrayList<>();
			getTimes(s, tripsBA, numTripsBA);
	
			List<Integer> m = getTrainsNeeded(tripsAB, tripsBA, turnAroundTime);
			System.out.printf("Case #%d: %d %d\n", i + 1, m.get(0), m.get(1));

		}
		s.close();
	}
*/
}
