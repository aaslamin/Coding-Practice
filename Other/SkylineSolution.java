import java.util.*;
import java.io.*;

/*
 * PROBLEM DESCRIPTION: uva.onlinejudge.org - 105 - The Skyline Problem
 * GITHUB.COM/AASLAMIN
 */

public class SkylineSolution { 
	
	public static void main (String[] args) {
		try {
			printSkyline(getBuildings());
		} catch (IllegalInputException e) {
			System.err.println(e.getMessage());
		}
	}
	
	/*
	 * Note: the height of the skyline can only change at either the left or right edge of a building. 
	 * Thus, by maintaining a list of active buildings, one can query for the maximum height in a straightforward manner. In my implementation, I used a 
	 * a priority queue to maintain the list of active buildings as it enables me to get the tallest active building in constant time. 
	 * 
	 * when a LEFT edge is encountered: add it to the active list
	 * when a RIGHT edge is encountered: remove it from the active list 
	 * 
	 * any time the above two events occur, AFTER updating the active list, query for the maximum height amongst the set of active buildings
	 * 
	 */
	
	public static void printSkyline (ArrayList<Building> buildings) {
		
		ArrayList<CriticalPoint> criticalPoints = new ArrayList<CriticalPoint>();

		for (Building b : buildings) {
			criticalPoints.add(new CriticalPoint(b, b.left));
			criticalPoints.add(new CriticalPoint(b, b.right));
		}

		PriorityQueue<Building> activeBuildings = new PriorityQueue<Building>(criticalPoints.size(), new HeightComparator());			
		// sort all Points in ascending order of their x coordinate 
		Collections.sort(criticalPoints, new PointComparator());

		int activeMax = 0; 
		for (int i = 0; i < criticalPoints.size(); i++) {
			CriticalPoint p = criticalPoints.get(i);
			if (activeBuildings.contains(p.b)) {
				activeBuildings.remove(p.b);
			} else {
				activeBuildings.add(p.b);
			}

			int height = (activeBuildings.peek() != null) ? activeBuildings.peek().height:0;
			if (height != activeMax) { // if there is a height change, print to console
				activeMax = height;
				System.out.print(p.x + " " + activeMax);
				if (i != criticalPoints.size()-1) System.out.print(" ");
			}
		}
	}
	
	public static ArrayList<Building> getBuildings () throws IllegalInputException {
		ArrayList<Building> buildings = new ArrayList<Building>();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line;
		
		try {
			while ((line = br.readLine()) != null && line.length() != 0) {
				String[] building = line.split(" ");
				
				if (building.length != 3) throw new IllegalInputException("Invalid input format provided");
				// each building is parsed using the following format: (L, H, R) 
				buildings.add(new Building(
						Integer.parseInt(building[0]), 
						Integer.parseInt(building[2]), 
						Integer.parseInt(building[1])));
			}
			
		} catch (IOException e) { }
		
		return buildings;
	}
	
}

class CriticalPoint {
	
	Building b;
	int x;
	
	CriticalPoint (Building b, int x) {
		this.b = b;
		this.x = x;
	}
}


class Building {
	
	int left, right, height;

	Building (int left, int right, int height) {
		this.left = left;
		this.right = right;
		this.height = height;
	}
	
	@Override
	public String toString () {
		return "(" + left + " " + height + " " + right + ")";
	}
}

class PointComparator implements Comparator<CriticalPoint> {

	@Override
	public int compare(CriticalPoint p1, CriticalPoint p2) {
		return p1.x - p2.x;
	}
}

class HeightComparator implements Comparator<Building> {
	
	@Override
	public int compare (Building b1, Building b2) {
		return b2.height - b1.height;
	}
}

class IllegalInputException extends Exception {
	public IllegalInputException(String message) {
		super (message);
	}
}
