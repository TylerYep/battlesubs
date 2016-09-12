import java.util.*;

public class Lazy extends Player{
    private Random rand = new Random();
    
    /**
	 * Default constructor, sets player name
	 * @param n - name
	 */
    public Lazy(String n){
		super(n);
	}
    
    /**
	 * Initializes ships for both players randomly.
	 * 
	 * @param player
	 *            to initialize ships for
	 */
	public void initShips() {
		System.out.println(super.getName() + "'s Board");
		for (int d = 0; d < super.getShipSizes().length; d++) {
			int x = rand.nextInt(super.getBoardSize());
			int y = rand.nextInt(super.getBoardSize());
			int z = rand.nextInt(super.getBoardSize());

			int[] coord1 = { x, y, z };
			int[] coord2 = { x, y, z };

			int direction = 0, sign = 0;
			do {
				direction = rand.nextInt(3);
				sign = rand.nextInt(2);
				if (sign == 0) {
					sign = -1;
				}
			} while (coord1[direction] + sign * (super.getShipSizes()[d]-1) < 0
					|| coord1[direction] + sign * super.getShipSizes()[d] >= super.getBoardSize());
			coord2[direction] = coord1[direction] + sign * (super.getShipSizes()[d]-1);
			super.addShip(super.getShipSizes()[d], coord1, coord2);

			System.out.println("Ship of length " + super.getShipSizes()[d]);
			System.out.println("Front: " + printCoord(coord1));
			System.out.println("Back: " + printCoord(coord2));
		}
		System.out.println("------------------------");
	}
	
	/**
	 * Prints out a set of x,y,z coordinates, separated by commas
	 * 
	 * @param r
	 *            - coordinate array
	 * @return string with coordinates
	 */
	private String printCoord(int[] r) {
		String total = "";
		for (int w = 0; w < r.length - 1; w++) {
			total += r[w] + ", ";
		}
		total += r[r.length - 1];
		return total;
	}
}