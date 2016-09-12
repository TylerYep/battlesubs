/**
 * 
 * @author Ship Master Davis the Gu-reat
 *
 *
 */
import java.util.*;
public class Ship {
	// length of the ship
	private int length;
	// the front end of where the ship is placed
	private int[] startPoint = new int[3];
	// the back end of where the ship is placed
	private int[] endPoint = new int[3];
	// whether all parts of the ship has been hit
	private boolean isAlive = true;
	// a boolean array that shows what part of the ship had been hit
	private boolean[] hit;
	// shipParts contains all coordinates of ship
	private ArrayList<int[]> shipParts = new ArrayList<int[]>();

	/**
	 * parameter constructor that sets the length of the ship.
	 * 
	 * @param length
	 *            - length of the ship
	 */
	public Ship(int l, int[] start, int[] end) {
		length = l;
		
		// no part of the ship has been hit yet. All elements are false.
		hit = new boolean[length];
		for(int i=0;i<hit.length;i++){
			hit[i]=false;
		}
		
		
		if ((int) distance(start, end) != length - 1) {
			System.out.println("Wrong Length, should be " + distance(start, end));
		}
		else {
			for (int i = 0; i < startPoint.length; i++) {
				startPoint[i] = start[i];
				endPoint[i] = end[i];
			}
		}
		
		// Initialize shipParts
		initShipParts(startPoint, endPoint);
	}
	
	private void initShipParts(int[] start, int[]end){
		int index = 0;
		for(int i = 0; i < start.length; i++){
			if(start[i] != end[i]){
				index = i;
			}
		}		
		if(start[index] <= end[index]){
			for(int x = start[0]; x<=end[0]; x++){
				for (int y = start[1]; y<=end[1]; y++){
					for (int z = start[2]; z<=end[2]; z++){
						int[] temp = {x,y,z};
						shipParts.add(temp);
					}	
				}	
			}
		} else {
			for(int x = start[0]; x>=end[0]; x--){
				for (int y = start[1]; y>=end[1]; y--){
					for (int z = start[2]; z>=end[2]; z--){
						int[] temp = {x,y,z};
						shipParts.add(temp);
					}	
				}	
			}
		}		
	}

	/**
	 * the ship gets hit at the assigned location. if all parts of the ship is
	 * hit, set isAlive to false
	 * 
	 * @param partOfShip
	 *            - where the ship gets hit. Goes from 0 to length-1
	 */
	public void shipHit(int partOfShip) {
		hit[partOfShip] = true;
		boolean allPartsHit = true;
		for (int i = 0; i < hit.length; i++) {
			if (hit[i] == false) {
				allPartsHit = false;
			}
		}
		if (allPartsHit == true) {
			isAlive = false;
		}
	}

	/**
	 * static method to calculate distance between two points
	 * 
	 * @param start
	 *            - first point
	 * @param end
	 *            - second point
	 * @return - returns the distance between the two points.
	 */
	private static double distance(int[] start, int[] end) {
		int x = end[0] - start[0];
		int y = end[1] - start[1];
		int z = end[2] - start[2];
		return (Math.sqrt(x*x + y*y + z*z));
	}

	/**
	 * returns whether the ship is alive or not.
	 * 
	 * @return - isAlive
	 */
	public boolean getIsAlive() {
		return isAlive;
	}
	
	public ArrayList<int[]> getCoords(){
		return shipParts;
	}
}
