import java.util.*;
//Run Config javac BattleSubsDriver.java && javac BattleSubs.java && javac Player.java && javac Ship.java && java BattleSubsDriver

public class BattleSubsDriver {
	public static void main(String[] args) {
		Player p1;
		Player p2;
		
		// Type names and start game
		Scanner in = new Scanner(System.in);
		System.out.println("---------------------");
		System.out.println("Welcome to Battle Subs!");
		System.out.println("---------------------");
		System.out.print("Player 1 name: ");
		String a = in.next();
		
		System.out.print("Player 2 name: ");
		String b = in.next();

		// Player 1 Initialize Ships
		System.out.println(a + "'s Turn");
		System.out.print("Type 1 to input manual ship positions, or any other number to randomize ship positions: ");
		String choice = in.next();
		if (choice.equals("1")) {
			p1 = new Smart(a);
			p1.initShips();
		} else {
			p1 = new Lazy(a);
			p1.initShips();
		}
		
		// Player 2 Initialize Ships
		System.out.println(b + "'s Turn");
		System.out.print("Type 1 to input manual ship positions, or any number to randomize ship positions: ");
		choice = in.next();
		if (choice.equals("1")) {
			p2 = new Smart(b);
			p2.initShips();
		} else {
			p2 = new Lazy(b);
			p2.initShips();
		}
		
		
		BattleSubs newGame = new BattleSubs(p1, p2);
		// Play game
		newGame.play();
	}
}
