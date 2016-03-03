

import java.util.Map;


public class Grid<Tile>{

	private Map<Tile> tiles;
	public Grid(int size){
		tiles = new HashMap<Point, Tile>(); 
	}
}
