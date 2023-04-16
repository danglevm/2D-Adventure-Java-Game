package pathfinding;


public class Node {
	
	Node parentNode;
	int col;
	int row;
	int gCost;
	int hCost;
	int fCost;
	boolean start;
	boolean target;
	boolean solid;
	boolean open;
	boolean checked;
	
	public Node (int col, int row) {
		this.col = col;
		this.row = row;
	}
	
}
