import java.util.HashMap;
import java.util.Map;
import java.awt.Point;

public class Grid{

	private Map<Point, Tile> tiles;
	private int size;
	
	public Grid(int size){
		tiles = new HashMap<Point, Tile>(); 
		
		// ajout excpetion si pas un carre
		this.size = size;
		
	}
	
	public void createTiles(){
		int j = 0;
		for(int i =  0; i < size - 1; i++){
			
			this.tiles.put(new Point(j,i%3), new Tile(Integer.toString(i)));
			
			if(i%3 == 0) {
				++j;
			}
		}
	}
	
}
