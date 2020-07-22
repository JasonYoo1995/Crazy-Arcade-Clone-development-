package map;

public class Map {
	public int width = 15;
	public int height = 13;
	
	// required item number list
	public int[] items = {20,20,20}; // bubble, fluid, roller

	public int totalBlockNum = 90;
	public int totalItemBlockNum;
	public int totalEmptyBlockNum;	
	
	public int map[][] = { // map[13][15]
			{ 0, 2, 1, 2, 1, 4, 0, 0, 3, 4,-2, 1,-2, 0,-2},
			{ 0,-1, 3,-1, 3,-4, 3, 0, 0,-4, 1, 2, 0, 0, 0},
			{ 0, 0, 2, 1, 2, 4, 0, 3, 3, 4,-2, 3,-2, 3,-2},
			{ 3,-1, 3,-1, 3,-4, 3, 0, 0,-4, 2, 1, 2, 1, 2},
			{ 1, 2, 1, 2, 1, 4, 0, 0, 3, 4,-2, 3,-2, 3,-2},
			{ 2,-1, 2,-1, 2,-4, 3, 3, 0, 0, 1, 2, 1, 2, 1},
			{-4, 4,-4, 4,-4, 4, 0, 0, 3, 4,-4, 4,-4, 4,-4},
			{ 1, 2, 1, 2, 1, 0, 3, 0, 0,-4, 1,-1, 1,-1, 1},
			{-3, 3,-3, 3,-3, 4, 0, 3, 3, 4, 2, 1, 2, 1, 2},
			{ 2, 1, 2, 1, 2,-4, 3, 0, 0,-4, 3,-1, 3,-1, 3},
			{-3, 0,-3, 3,-3, 4, 0, 0, 3, 4, 1, 2, 1, 2, 0},
			{ 0, 0, 1, 2, 1,-4, 0, 3, 0,-4, 3,-1, 3,-1, 0},
			{-3, 0,-3, 1,-3, 4, 0, 0, 3, 4, 2, 1, 2, 0, 0}
	};
	/*
	 * -4: tree			- barrier block
	 * -3: blue house	- barrier block
	 * -2: yellow house	- barrier block
	 * -1: red house	- barrier block
	 * 0 : empty		- no blocked
	 * 1 : red box		- item block
	 * 2 : orange box	- item block
	 * 3 : x box		- item block
	 * 4 : forest 		- movable block
	 */
	
	public Block[][] block = new Block[width][height];
	Map() {
		for (int i = 0; i < 3; i++) {
			totalItemBlockNum += items[i];
		}
		totalEmptyBlockNum = totalBlockNum - totalItemBlockNum;
	}
	
	public void generateItemBlock(Map map) {
		
	}
}
