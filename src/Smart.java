/**
 * @author Jonathan, Tyler, Cathleen, Davis, Himanshu
 */
import java.util.*;
public class Smart extends Player{
	Scanner in = new Scanner(System.in);
	/**
	 * Default constructor, sets player name
	 * @param n - name
	 */
	public Smart(String n){
		super(n);
	}
	
	/**
	 * Initializes ships for both players manually.
	 * 
	 * @param player
	 *            to initialize ships for
	 */
	public void initShips() {
		int[] coord1 = new int[3];
		int[] coord2 = new int[3];
		for (int d = 0; d < super.getShipSizes().length; d++) {
			do {
				do {
					inCoords(coord1, coord2, d);
					if (!secondCoord(coord1, coord2, super.getShipSizes()[d])) {
						System.out.println("Invalid coordinates");
						System.out.println("Starting over.................");
					}
				} while (!secondCoord(coord1, coord2, super.getShipSizes()[d]));
			} while (!super.addShip(super.getShipSizes()[d], coord1, coord2));
			System.out.println("Ship is placed!");
		}
		System.out.println("All ships have been placed");
	}

	/**
	 * Helper method to check if second coord is in the board and at correct
	 * distance
	 * 
	 * @param coord1
	 *            - start coordinate
	 * @param coord2
	 *            - end coordinate
	 * @param x
	 *            - position in shipSizes
	 */
	private void inCoords(int[] coord1, int[] coord2, int x) {
		do {
			System.out.println("Give start coordinates of ship of size " + super.getShipSizes()[x] + ": ");
			System.out.print("X Coord: ");
			coord1[0] = in.nextInt();
			System.out.print("Y Coord: ");
			coord1[1] = in.nextInt();
			System.out.print("Z Coord: ");
			coord1[2] = in.nextInt();
			if (!coordInBoard(coord1)) {
				System.out.println("Invalid start coordinates");
			}
		} while (!coordInBoard(coord1));

		System.out.println("Give end coordinates of ship of size " + super.getShipSizes()[x] + ": ");
		System.out.print("X Coord: ");
		coord2[0] = in.nextInt();
		System.out.print("Y Coord: ");
		coord2[1] = in.nextInt();
		System.out.print("Z Coord: ");
		coord2[2] = in.nextInt();
	}

	/**
	 * Helper method to check if second coord is in the board and at correct
	 * distance
	 * 
	 * @param coord1
	 * @param coord2
	 * @param size
	 * @return true or false
	 */
	private boolean secondCoord(int[] coord1, int[] coord2, int size) {
		boolean valid = false;
		if (coordInBoard(coord2) && distance(coord1, coord2) == size - 1) {
			valid = true;
		}
		return valid;
	}

	/**
	 * Helper method to check if a coordinate is within the board
	 * 
	 * @param arr
	 * @return
	 */
	private boolean coordInBoard(int[] arr) {
		boolean in = true;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] >= super.getBoardSize()) {
				in = false;
			}
		}
		return in;
	}

	/**
	 * Helper method to get distance between to coordinates
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	private static double distance(int[] start, int[] end) {
		int x = end[0] - start[0];
		int y = end[1] - start[1];
		int z = end[2] - start[2];
		return (Math.sqrt(x * x + y * y + z * z));
	}
}
