
import java.util.*;

public class BattleSubs {
	private Player p1;
	private Player p2;
	private Random rand = new Random();
	private Scanner in = new Scanner(System.in);

	/**
	 * Default Constructor, creates two players with default names
	 */
	public BattleSubs() {
		p1 = new Lazy("Allie");
		p2 = new Lazy("Brandon");
	}

	/**
	 * Constructor, creates two players with given names
	 */
	public BattleSubs(Player a, Player b) {
		p1 = a;
		p2 = b;
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

	/**
	 * Main method to play the game
	 */
	public void play() {
		System.out.println("Flipping Coin to see who goes first...");
		System.out.println();
		int turnCounter = 1;
		int flip = rand.nextInt(2);
		if (flip == 0) {
			System.out.println(p1.getName() + " goes first.");
			p1.setMyTurn(true);
		} else {
			System.out.println(p2.getName() + " goes first.");
			p2.setMyTurn(true);
		}
		while (p1.isAlive() && p2.isAlive()) {
			System.out.println("---------------------");
			System.out.println("Turn: " + turnCounter);
			System.out.println("---------------------");
			if (p1.isMyTurn()) {
				p1.playerTurn(p2);
				p1.setMyTurn(false);
				p2.setMyTurn(true);
				turnCounter++;
			} else {
				p2.playerTurn(p1);
				p1.setMyTurn(true);
				p2.setMyTurn(false);
				turnCounter++;
			}
		}
		if (!p1.isAlive() && p2.isAlive()) {
			System.out.println("---------------------");
			System.out.println(p2.getName() + " won!!!!!");
			System.out.println("---------------------");
		} else if (p1.isAlive() && !p2.isAlive()) {
			System.out.println("---------------------");
			System.out.println(p1.getName() + " won!!!!!");
			System.out.println("---------------------");
		}
	}
}