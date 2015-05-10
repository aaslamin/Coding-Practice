import java.util.*;


/*
 * PROBLEM: http://community.topcoder.com/stat?c=problem_statement&pm=2998&rd=5857
 * GITHUB.COM/AASLAMIN
 */

public final class grafixMask {
	
	private static final int ROWS = 400;
	private static final int COLUMNS = 600;
	private static boolean GRID[][] = new boolean[ROWS][COLUMNS];
	
	
	
	
	public static void main(String[] args) {
		
		/* The following are the test cases provided in the problem description */
		
		String[] testCase0 = new String[] {"0 292 399 307"};
		String[] testCase1 = new String[] {"48 192 351 207", "48 392 351 407", "120 52 135 547", "260 52 275 547"};
		String[] testCase2 = new String[] {"0 192 399 207", "0 392 399 407", "120 0 135 599", "260 0 275 599"};
		String[] testCase3 = new String[] {"50 300 199 300", "201 300 350 300", "200 50 200 299", "200 301 200 550"};
		String[] testCase4 = new String[] { "0 20 399 20", "0 44 399 44",
				"0 68 399 68", "0 92 399 92", "0 116 399 116", "0 140 399 140",
				"0 164 399 164", "0 188 399 188", "0 212 399 212",
				"0 236 399 236", "0 260 399 260", "0 284 399 284",
				"0 308 399 308", "0 332 399 332", "0 356 399 356",
				"0 380 399 380", "0 404 399 404", "0 428 399 428",
				"0 452 399 452", "0 476 399 476", "0 500 399 500",
				"0 524 399 524", "0 548 399 548", "0 572 399 572",
				"0 596 399 596", "5 0 5 599", "21 0 21 599", "37 0 37 599",
				"53 0 53 599", "69 0 69 599", "85 0 85 599", "101 0 101 599",
				"117 0 117 599", "133 0 133 599", "149 0 149 599",
				"165 0 165 599", "181 0 181 599", "197 0 197 599",
				"213 0 213 599", "229 0 229 599", "245 0 245 599",
				"261 0 261 599", "277 0 277 599", "293 0 293 599",
				"309 0 309 599", "325 0 325 599", "341 0 341 599",
				"357 0 357 599", "373 0 373 599", "389 0 389 599" };
		
		
		printResult(testCase0);
		printResult(testCase1);
		printResult(testCase2);
		printResult(testCase3);
		printResult(testCase4);

		
	}
	
	public static int[] sortedAreas(String[] rectangles) {
		
		initializeGrid(rectangles);
		ArrayList<Integer> areas = new ArrayList<Integer>();
		
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLUMNS; col++) {
				if (!GRID[row][col]) {
					areas.add(fillGrid(row, col));
				}
			}
				
		}
		
		Collections.sort(areas);
		int[] result = new int[areas.size()];
		
		for (int i = 0; i < areas.size(); i++) {
			result[i] = areas.get(i);
		}
				
		return result;
	}
	
	public static void printResult (String[] input) {
		
		int[] result = sortedAreas(input);
		
		System.out.print("{ ");
		for (int i = 0; i < result.length; i++) {
			System.out.print(result[i]);
			if (!(i == result.length-1)) {
				System.out.print(", ");
			}
		}
		
		GRID = new boolean[ROWS][COLUMNS];
		
		System.out.print(" }");
		System.out.println("\n\n");

	}
	
	public static int fillGrid(int row, int column) {

		class Node {
			int row, column;

			Node(int row, int column) {
				this.row = row;
				this.column = column;
			}
		}

		Node start = new Node(row, column);
		int area = 0;
		Stack<Node> toVisit = new Stack<Node>();
		toVisit.push(start);

		while (!toVisit.isEmpty()) {
			Node current = toVisit.pop();

			if (current.row < 0 || current.row >= ROWS) continue;
			if (current.column < 0 || current.column >= COLUMNS) continue;
			if (GRID[current.row][current.column] == true) continue;
			
			GRID[current.row][current.column] = true;  

			toVisit.push(new Node(current.row - 1, current.column));
			toVisit.push(new Node(current.row + 1, current.column));
			toVisit.push(new Node(current.row, current.column - 1));
			toVisit.push(new Node(current.row, current.column + 1));
			
			area++;
		}
		return area;
	}
	
	public static void initializeGrid(String[] rectangles) {
		
		class Rectangle {
			
			int leftRow, leftColumn, rightRow, rightColumn; 
			
			Rectangle (int leftRow, int leftColumn, int rightRow, int rightColumn) {
				this.leftRow = leftRow;
				this.leftColumn = leftColumn;
				this.rightRow = rightRow;
				this.rightColumn = rightColumn;
			}
			
			void setGrid () {
				for (int row = this.leftRow; row <= this.rightRow; row++) {
					for (int col = this.leftColumn; col <= this.rightColumn; col++) {
						GRID[row][col] = true; 
					}
				}
			}
		}
		
		for (String rectangle : rectangles) {
			String[] dimensions = rectangle.split(" ");
			new Rectangle (
					Integer.parseInt(dimensions[0]),
					Integer.parseInt(dimensions[1]),
					Integer.parseInt(dimensions[2]),
					Integer.parseInt(dimensions[3])).setGrid();;
			
		}
		
	}
}


