import java.util.*;

public abstract class Player {
    private final int boardSize = 10;
	private int[][][] board = new int[boardSize][boardSize][boardSize]; //0 is empty, 1 has a ship, 2 is hit
	private ArrayList<Ship> playerShips = new ArrayList<Ship>();
	private final int[] shipSizes = { 8, 7, 5, 5, 3 };
	private Scanner in = new Scanner(System.in);
	private int powerUps = 0;
	private String name;
	private boolean stillAlive = true;
	private boolean myTurn = false;
	
	/**
	 * Default constructor, sets player name
	 * @param n - name
	 */
	public Player(String n) {
		name = n;
	}
	
	/**
	 * Method to place initialize ship lengths and coordinates
	 */
	public abstract void initShips();
	
	/**
	 * Gets board size
	 * 
	 * @return - board size
	 */
	public int getBoardSize(){
	    return boardSize;
	}
	/**
	 * Gets array with ship sizes
	 * 
	 * @return - ship sizes
	 */
	public int[] getShipSizes() {
		quickSort(shipSizes, 0, shipSizes.length-1);
		return shipSizes;
	}
	/**
	 * Accessor method for board
	 * @return player's board
	 */
	public int[][][] getBoard(){
		return board;
	}
	/**
	 * Accessor method for player ship list
	 * @return arraylist of player ships
	 */
	public ArrayList<Ship> getPlayerShips(){
		return playerShips;
	}
	/**
	 * Accessor method for player name
	 * @return player name
	 */
	public String getName(){
		return name;
	}
	/**
	 * Accessor method for player's turn
	 * @return true if it is player's turn
	 */
	public boolean isMyTurn(){
		return myTurn;
	}
	/**
	 * Setter method for player's turn
	 */
	public void setMyTurn(boolean v){
		myTurn = v;
	}
	/**
	 * checks to see if all ships are alive
	 * @return - true if all ships are alive and false if at least one ship is dead
	 */
	public boolean isAlive(){
		stillAlive = false;
		for(int f = 0; f<playerShips.size(); f++){
			if(playerShips.get(f).getIsAlive()){
				stillAlive = true;
			}
		}
		return stillAlive;
	}
	/**
	 * Method to set a board space to "hit"
	 * @param n - name
	 */
	public void setHit(int arr[]){
		if(getBoard()[arr[0]][arr[1]][arr[2]] == 0){
			board[arr[0]][arr[1]][arr[2]] = 3;
		}
		if(getBoard()[arr[0]][arr[1]][arr[2]] == 1 || getBoard()[arr[0]][arr[1]][arr[2]] == 2){
			board[arr[0]][arr[1]][arr[2]] = 2;
		}	
	}
	
	/**
	 * This player's board receives a hit
	 * 
	 */
	public void receiveAttack(int[] arr){
		if(getBoard()[arr[0]][arr[1]][arr[2]] == 0){
			System.out.print("Miss! at ");
			for(int i = 0; i < arr.length; i++){
				System.out.print(arr[i] + " ");
			}
			System.out.println();
		} 
		if(getBoard()[arr[0]][arr[1]][arr[2]] == 2 || getBoard()[arr[0]][arr[1]][arr[2]] == 3 ){
			for(int i = 0; i < arr.length; i++){
				System.out.print(arr[i] + " ");
			}
			System.out.println("was hit already");
		}
		if(getBoard()[arr[0]][arr[1]][arr[2]] == 1){
			System.out.print("Hit! at ");
			for(int i = 0; i < arr.length; i++){
				System.out.print(arr[i] + " ");
			}
			System.out.println();
			boolean shipIsAlive=true;//created so that "ship sunk" is only printed once
			for(Ship test : playerShips){ //runs through player's ships
				shipIsAlive=true;
				for(int j=0;j<test.getCoords().size();j++){//runs through the shipParts--the coordinates that the ship occupies
					if(arr[0]==test.getCoords().get(j)[0]&&arr[1]==test.getCoords().get(j)[1]&&arr[2]==test.getCoords().get(j)[2]){//if the xyz of hit coords are the same as ship Parts element xyz
						test.shipHit(j);
						if(test.getIsAlive()==false){
							shipIsAlive=false;//becomes false if the test ship is dead
						}
					}
				}
			}
			if(shipIsAlive==false){
				System.out.println("Ship sunk!");// if the ship is dead :D
			}
		}
		setHit(arr);
	}
	/**
	 * Adds a ship if placeOnBoard accepts it
	 * @param length of the ship
	 * @param start coordinates of ship
	 * @param end coordinates of ship
	 * @return true if ship is added to arraylist
	 */
	public boolean addShip(int length, int[] start, int[] end) {
		boolean success = false;
		if(placeOnBoard(start, end)){
			playerShips.add(new Ship(length, start, end));
			success = true;
		}
		return success;
	}
	/**
	 * Checks if placing a ship in coordinates would have overlap
	 * @param start coordinates of ship
	 * @param end coordinates of ship
	 * @return true if ship has no overlaps
	 */
	private boolean placeOnBoard(int[] start, int[] end){
		int index1 = 0;
		for(int i = 0; i < start.length; i++){
			if(start[i] != end[i]){
				index1 = i;
			}
		}
		if(start[index1] <= end[index1]){
			for(int k = start[index1]; k <= end[index1]; k++){
				if(index1 == 0){
					if(board[k][start[1]][start[2]] == 1){
						System.out.println("This placement overlaps with another ship");
						return false;
					}
				}
				if(index1 == 1){
					if(board[start[0]][k][start[2]] == 1){
						System.out.println("This placement overlaps with another ship");
						return false;
					}
				} 
				if(index1 == 2) {
					if(board[start[0]][start[1]][k] == 1){
						System.out.println("This placement overlaps with another ship");
						return false;
					}
				}
			}	
		} else { //start[index1] > end[index1]
			for(int k = start[index1]; k >= end[index1]; k--){
				if(index1 == 0){
					if(board[k][start[1]][start[2]] == 1){
						System.out.println("This placement overlaps with another ship");
						return false;
					}
				}
				if(index1 == 1){
					if(board[start[0]][k][start[2]] == 1){
						System.out.println("This placement overlaps with another ship");
						return false;
					}
				} 
				if(index1 == 2) {
					if(board[start[0]][start[1]][k] == 1){
						System.out.println("This placement overlaps with another ship");
						return false;
					}
				}
			}
		}
		if(start[index1] <= end[index1]){
			for(int k = start[index1]; k <= end[index1]; k++){
				if(index1 == 0){
					board[k][start[1]][start[2]] = 1;
				}
				if(index1 == 1){
					board[start[0]][k][start[2]] = 1;
				} 
				if(index1 == 2) {
					board[start[0]][start[1]][k] = 1;
				}
			}	
		} else { //start[index1] > end[index1]
			for(int k = start[index1]; k >= end[index1]; k--){
				if(index1 == 0){
					board[k][start[1]][start[2]] = 1;
				}
				if(index1 == 1){
					board[start[0]][k][start[2]] = 1;
				} 
				if(index1 == 2) {
					board[start[0]][start[1]][k] = 1;
				}
			}
		}
		return true;
	}
	/**
	 * Checks if the move is legal
	 * @param arr - coordinate entered
	 * @return true if move is legal
	 */
	private boolean isLegalMove(int[] arr) {
		boolean in = true;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] >= boardSize || arr[i] < 0) {
				in = false;
			}
		}
		return in;
	}
	/**
	 * Helper method to check if coordinate has been hit
	 * @param arr array of coordinates
	 * @param otherPlayer player to attack
	 * @return true if coordinate has been hit
	 */
	private boolean hasBeenHit(int[] arr, Player otherPlayer){
		if(otherPlayer.getBoard()[arr[0]][arr[1]][arr[2]] == 2||otherPlayer.getBoard()[arr[0]][arr[1]][arr[2]] ==3){
			return true;
		} else {
			return false;
		}
	}
	/**
	 * Simulates dice roll and adds powerups if roll is a 5 or a 6
	 */
	public void rollDice() {
		Random rand = new Random();
		int num = rand.nextInt(6) + 1;
		if (num == 6) {
			System.out.println("You gained 2 PowerUps!");
			System.out.println();
			powerUps += 2;
		} else if (num == 5) {
			System.out.println("You gained 1 PowerUp!");
			System.out.println();
			powerUps += 1;
		}
	}
	
	/**
	 * Helper method to print a coordinate
	 * @param r array of coordinate
	 * @return string of the coordinate
	 */
	private String printCoord(int[] r){
		String total = "";
		for (int w = 0; w<r.length-1; w++){
			total += r[w] + ", ";
		}
		total += r[r.length-1];
		return total;
	}
	/**
	 * Single coordinate attack
	 */
	public void attackCoord(Player otherPlayer) {
		int[] attack = new int[3];
		do {
			System.out.println("Regular attack - Enter coordinates to attack");
			System.out.print("X Coord: ");
			attack[0] = in.nextInt();
			System.out.print("Y Coord: ");
			attack[1] = in.nextInt();
			System.out.print("Z Coord: ");
			attack[2] = in.nextInt();
			if (!isLegalMove(attack)) {
				System.out.println("Invalid coordinates");
			} else if(hasBeenHit(attack, otherPlayer)){
				System.out.println("You have attacked this coordinate already");
			}
		} while (!isLegalMove(attack) || hasBeenHit(attack, otherPlayer));
		System.out.println("Attacking: " + printCoord(attack));
		otherPlayer.receiveAttack(attack);
	}
	/**
	 * Special attack from left axis
	 * @param attack - array of coordinates to attack (contains Z and Y coordinates)
	 * @param p - opponent
	 */
	private boolean attackLeft(int [] attack, Player otherPlayer) {
		if (isLegalMove(attack)){
			System.out.println("Special Attack Left: " + printCoord(attack));
			int[] specialAttack = new int[3];
			specialAttack[2] = attack[0]; // take in row (Z)
			specialAttack[1] = attack[1]; // take in col (Y)
			for(int x = 0; x<boardSize; x++){ // attack coords (z/y stay constant, x changes)
				specialAttack[0] = x;
				otherPlayer.receiveAttack(specialAttack);
			}
			return true;
		}
		else{
			System.out.println("Not valid coordinate. Try Again.");
			return false;
		}
	}
	/**
	 * Special attack from top axis
	 * @param attack - array of coordinates to attack (contains X and Z coordinates)
	 * @param p - opponent
	 */
	private boolean attackTop(int [] attack, Player otherPlayer) {
		if (isLegalMove(attack)){
			System.out.println("Special Attack Top: " + printCoord(attack));
			int[] specialAttack = new int[3];
			specialAttack[0] = attack[0]; // take in col (X)
			specialAttack[2] = attack[1]; // take in layer (Z)
			for(int y = 0; y<boardSize; y++){ // attack coords (x/z stay constant, y changes)
				specialAttack[1] = y;
				otherPlayer.receiveAttack(specialAttack);
			}
			return true;
		}
		else{
			System.out.println("Not valid coordinate. Try Again.");
			return false;
		}
	}
	/**
	 * Special attack from right axis
	 * @param attack - array of coordinates to attack (contains X and Y coordinates)
	 * @param p - opponent
	 */
	private boolean attackFront(int [] attack, Player otherPlayer) {
		if (isLegalMove(attack)){
			System.out.println("Special Attack Front: " + printCoord(attack));
			int[] specialAttack = new int[3];
			specialAttack[0] = attack[0]; // take in row (X)
			specialAttack[1] = attack[1]; // take in layer (Y)
			for(int z = 0; z<boardSize; z++){ // attack coords (x/y stay constant, z changes)
				specialAttack[2] = z;
				otherPlayer.receiveAttack(specialAttack);
			}
			return true;
		}
		else{
			System.out.println("Not valid coordinate. Try Again.");
			return false;
		}
	}
	/**
	 * accepts input from player to choose which axis to attack from and coordinates to attack
	 * then calls the appropriate attack helper method
	 * @param otherPlayer - opponent
	 */
	public void specialAttack(Player otherPlayer){
		boolean isValidAxis=false;
		if(powerUps>0){
			while(isValidAxis==false){
				System.out.println("Special attack - Choose side of cube");
				System.out.println("Top, Left, Front face?");
				String a = in.next().toLowerCase();
				int[] attack = new int[2];
				switch(a){
					case "top":
						do {
							System.out.print("Coordinate " + "X" + ": ");
							attack[0] = in.nextInt();
							System.out.print("Coordinate " + "Z" + ": ");
							attack[1] = in.nextInt();
							isValidAxis=true;
						} while (!this.attackTop(attack,otherPlayer));
						break;
					case "left":
						do {
							System.out.print("Coordinate " + "Z" + ": ");
							attack[0] = in.nextInt();
							System.out.print("Coordinate " + "Y" + ": ");
							attack[1] = in.nextInt();
							isValidAxis=true;
						} while (!this.attackLeft(attack,otherPlayer));
						break;
					case "front":
						do {
							System.out.print("Coordinate " + "X" + ": ");
							attack[0] = in.nextInt();
							System.out.print("Coordinate " + "Y" + ": ");
							attack[1] = in.nextInt();
							isValidAxis = true;
						} while(!this.attackFront(attack,otherPlayer));
						break;
					default:
						System.out.println("Not a valid axis. Try Again.");
						isValidAxis = false;
						break;
				}
			}
		}
	}
	/**
	 * Main player turn method uses all other helper methods 
	 * 
	 */
	public void playerTurn(Player otherPlayer){
		System.out.println("Rolling lucky dice...");
		System.out.println();
		this.rollDice();
		boolean isValidAnswer = false;
		while(isValidAnswer == false){
			System.out.println(this.getName() + "'s Turn");
			System.out.println(this.toString());
			System.out.println("Enter 1 to view your board.");
			System.out.println("Enter 2 to view opponent's board.");
			System.out.println("Enter 3 to begin your attack.");
			int g = in.nextInt();
			switch(g){
				case 1:
					System.out.println("Which layer do you want? (number) ");
					int a = in.nextInt();
					this.displayBoard(a);
					break;
				case 2:
					System.out.println("Which layer do you want? (number)");
					int b = in.nextInt();
					otherPlayer.displayBoard(b);
					break;
				case 3:
					System.out.println("Starting attack...");
					if (powerUps > 0){
						System.out.println("Use power up?");
						String answer = in.next();
						if(answer.toLowerCase().equals("yes")||answer.toLowerCase().equals("y")|| answer.toLowerCase().equals("ye")){
							this.specialAttack(otherPlayer);
							powerUps--;
							isValidAnswer = true;
						}
						else{
							this.attackCoord(otherPlayer);
							isValidAnswer = true;
						}
					}
					else{
						this.attackCoord(otherPlayer);
						isValidAnswer = true;
						}
					break;
				default:
					System.out.println("Not a valid answer. Try Again.");
					break;
				}
		}
	}
	/**
	 * Main display board method (0 is empty, 1, is ship, 2 is hit, M is miss)
	 */
	public void displayBoard(int layer){
		if(layer<boardSize){
			boolean acceptedAns = false;
			while(!acceptedAns){
				System.out.println("Enter top, left, or right layer.");
				String d = in.next();
				if(d.toLowerCase().equals("top") || d.toLowerCase().equals("t")){
					this.displayBoardTop(layer);
					acceptedAns = true;
				}
				else if(d.toLowerCase().equals("left") || d.toLowerCase().equals("l")){
					this.displayBoardLeft(layer);
					acceptedAns = true;
				}
				else if(d.toLowerCase().equals("right") || d.toLowerCase().equals("r")){
					this.displayBoardRight(layer);
					acceptedAns = true;
				}
				else{
					System.out.println("Error, try again.");
				}
			}
		}
		else{
			System.out.println("Not valid axis. Try Again.");
		}
	}
	/**
	 * Displays player's board from the top down, given a layer 
	 * @param layer - layer to examine
	 */
	public void displayBoardTop(int layer){
		System.out.println();
		for(int z = boardSize - 1; z >= 0; z--){
			for(int y = 0; y < boardSize; y++){
				if(isMyTurn() == false && board[layer][y][z] == 1){
					System.out.print(0 + " ");
				} else if(isMyTurn() && board[layer][y][z] == 1) {
					System.out.print(board[layer][y][z] + " ");
				} else if(board[layer][y][z] == 3){
					System.out.print("M ");
				} else if(board[layer][y][z] == 0 || board[layer][y][z] == 2){
					System.out.print(board[layer][y][z] + " ");
				}
			}
			System.out.println();
		}
		System.out.println();
	}
	/**
	 * Displays player's board from the left side, given a layer
	 * @param layer - layer to examine
	 */
	public void displayBoardLeft(int layer){
		System.out.println();
		for(int y = boardSize - 1; y >= 0; y--){
			for(int z = boardSize - 1; z >= 0; z--){
				if(isMyTurn() == false && board[layer][y][z] == 1){
					System.out.print(0 + " ");
				} else if(isMyTurn() && board[layer][y][z] == 1) {
					System.out.print(board[layer][y][z] + " ");
				} else if(board[layer][y][z] == 3){
					System.out.print("M ");
				} else if(board[layer][y][z] == 0 || board[layer][y][z] == 2){
					System.out.print(board[layer][y][z] + " ");
				}			
			}
			System.out.println();
		}
		System.out.println();
	}
	/**
	 * Displays player's board from the right side, given a layer
	 * @param layer - layer to examine
	 */
	public void displayBoardRight(int layer){
		System.out.println();
		for(int y = boardSize - 1; y >= 0; y--){
			for(int z = 0; z < boardSize; z++){
				if(isMyTurn() == false && board[layer][y][z] == 1){
					System.out.print(0 + " ");
				} else if(isMyTurn() && board[layer][y][z] == 1) {
					System.out.print(board[layer][y][z] + " ");
				} else if(board[layer][y][z] == 3){
					System.out.print("M ");
				} else if(board[layer][y][z] == 0 || board[layer][y][z] == 2){
					System.out.print(board[layer][y][z] + " ");
				}
			}
			System.out.println();
		}
		System.out.println();
	}
	/**
	 * Prints out player name, powerups, and ships remaining
	 */
	public String toString(){
		int aliveShips = 0;
		for(int f = 0; f<playerShips.size(); f++){
			if(playerShips.get(f).getIsAlive()){
				aliveShips++;
			}
		}
		return "Name: " + name + "\nPowerUps: " + powerUps + "\nShips Remaining: " + aliveShips;
	}
	
	/**
	 * Implementation of Quicksort
	 * 
	 * @param a
	 *            reference to an array of integers to be sorted
	 * @param first
	 *            starting index of range of values to be sorted
	 * @param last
	 *            ending index of range of values to be sorted
	 */
	public void quickSort(int [] a, int first, int last) {
		int index = 0;
		int i = first;
		int j = last;
		int pivot = a[(first + last) / 2];
		while (i <= j) {
			while (a[i] < pivot) {
				i++;
			}
			while (a[j] > pivot) {
				j--;
			}
			if (i <= j) {
				int temp = a[i];
				a[i] = a[j];
				a[j] = temp;
				i++;
				j--;
			}
		}
		index = i;
		if (first < index - 1){
			quickSort(a, first, index - 1);
		}
		if (index < last){
			quickSort(a, index, last);
		}
	}
}
	