package pathfinding;

import java.util.ArrayList;

import adventureGame2D.GamePanel;
import entity.Entity;
import tile.InteractiveTile;

public class Pathfinder {

	GamePanel gp;
	Node [] [] node;
	private ArrayList <Node> openList = new ArrayList <> ();
	private ArrayList <Node> pathList = new ArrayList <> ();
	Node startNode, goalNode, currentNode;
	boolean goalReached = false;
	int step = 0;
	
	
	public ArrayList <Node> getNodeOpenList () { return openList; }

	public Pathfinder (GamePanel gp) {
		this.gp = gp;
		instantiateNodes();
	}
	
	//Set all the nodes to the tiles on the game.
	public final void instantiateNodes () {
		
		node = new Node [gp.getMaxWorldCol()][gp.getMaxWorldRow()];
		
		int col = 0;
		int row = 0;
		
		while (col < gp.getMaxWorldCol() && row < gp.getMaxWorldCol()) {
			node [col] [row] = new Node (col, row);
			
			++col;
			if (col == gp.getMaxWorldCol()) {
				col = 0;
				++row;
			}
		}
	}
	
	public final void resetNodes() {
		
		int col = 0;
		int row = 0;
		
		while (col < gp.getMaxWorldCol() && row < gp.getMaxWorldRow()) {
			
			
			//Reset any open, checked and solid nodes
			node [col] [row].open = false;
			node [col] [row].checked = false;
			node [col] [row].solid = false;
			
			++col;
			if (col == gp.getMaxWorldCol()) {
				col = 0;
				++row;
			}
		}
		//Reset all open and path nodes
		openList.clear();
		pathList.clear();
		goalReached = false;
		step = 0;
	}
	
	public final void setNodes(int startCol, int startRow, int goalCol, int goalRow) {
		
		this.resetNodes();
		
		//set start and goal node
		startNode = node [startCol][startRow];
		currentNode = startNode;
		goalNode = node[goalCol] [goalRow];
		openList.add(currentNode);
		
		int col = 0;
		int row = 0;
		
		while (col < gp.getMaxWorldCol() && row < gp.getMaxWorldRow()) {
			int tileNum = gp.getTileManager().mapTileNum[gp.currentMap][col][row];
			
			//Check Solid objects that can't be passed through
			if (gp.getTileManager().getTilesList().get(tileNum).collision) {
				node[col][row].solid = true;
			}
			
			//Check interactive tiles
			
			for (int i = 0; i < gp.getInteractiveTiles().get(gp.currentMap).size(); ++i) {
				
				try {
				InteractiveTile iTile = (InteractiveTile)gp.getInteractiveTiles().get(gp.currentMap).get(i);
				if (iTile.getDestructible()) {
					int iCol = iTile.getWorldX()/gp.getTileSize();
					int iRow = iTile.getWorldY()/gp.getTileSize();
					node[iCol][iRow].solid = true;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			//get the g, h, f cost of the node
			this.getCost(node[col][row]);
			
			++col;
			if (col == gp.getMaxWorldCol()) {
				col = 0;
				++row;
			}
		}
	}
	
	private final void getCost(Node node) {
		//Get G Cost
		int xGDistance = Math.abs(node.col - startNode.col);
		int yGDistance = Math.abs(node.row - startNode.row);
		node.gCost = xGDistance + yGDistance;
		
		//Get H Cost
		int xHDistance = Math.abs(node.col - goalNode.col);
		int yHDistance = Math.abs(node.row - goalNode.row);
		node.hCost = xHDistance + yHDistance;
		
		node.fCost = node.gCost + node.hCost;
	}
	
	public final boolean searchPath() {
		while (!goalReached && step < 500) {
			int col = currentNode.col;
			int row = currentNode.row;
			
			currentNode.checked = true;
			openList.remove(currentNode);
			
			//UP
			if (row - 1 >= 0) addOpenNode(node[col][row-1]);
			
			//DOWN
			if (row + 1 < gp.getMaxWorldRow()) addOpenNode(node [col] [row+1]);
			
			//LEFT
			if (col - 1 >= 0) addOpenNode(node[col-1][row]);
			
			//RIGHT
			if (col + 1 < gp.getMaxWorldCol()) addOpenNode(node[col+1][row]);
			
			int bestNodeIndex = 0;
			int bestNodeCost = 9999;
			
			for (int i = 0; i < openList.size(); ++i) {
				if (openList.get(i).fCost < bestNodeCost) {
					bestNodeIndex = i;
					bestNodeCost = openList.get(i).fCost;
				} else if (openList.get(i).fCost == bestNodeCost) {
					if (openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
						bestNodeIndex = i;
					}
				}
			}
			
			//if there is no node in the open List, end the loop
		
			if (openList.isEmpty()) {
				break;
			}
			
			currentNode = openList.get(bestNodeIndex);
			
			if (currentNode == goalNode) {
				goalReached = true;

				backtrackPath();
			}
			
			++step;

		}
	
		return goalReached;
	}
	
	private final void addOpenNode (Node node) {
		if (!node.open && !node.checked && !node.solid) {
			node.open = true;
			node.parentNode = currentNode;
			openList.add(node);
		}
	}
	
	
	//adds all the nodes starting from the end to the start
	//first node of the pathList arraylist will be the first location for NPCs to move to
	private final void backtrackPath () {
		Node current = goalNode;
		
		while (current != startNode) {
			pathList.add(0, current);
			current = current.parentNode;
		}
	}

	public final ArrayList<Node> getPathList () { return pathList; }
}
