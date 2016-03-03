import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;

import javafx.scene.Node;


public class Grid {

	private List<Tile> tiles;
	private final int nbOfTiles;
	private final int size; //en pixel
	private State state;
	
	public Grid(int nbOfTiles, int size, State state) {
		tiles = new ArrayList<Tile>();
		this.size = size;

		// ajout excpetion si pas un carre
		this.nbOfTiles = nbOfTiles;
		this.state = state;
		createTiles(state);
	}

	private void createTiles(State state) {
		int tileSize = (int) Math.round(size/nbOfTiles);
		Tile currentTile; 
		Point currentPoint;
		int currentValue;
		for (int i = 0; i < nbOfTiles; i++) {
			for (int j = 0; j < nbOfTiles; j++) {
				currentValue = state.getValue(i,j);
				if(currentValue == 0)
					continue;
				System.out.println(j + " " + i%3);
				currentPoint = new Point(j*tileSize, i * tileSize);
				currentTile = new Tile(Integer.toString(currentValue), tileSize, currentPoint);
				this.tiles.add(currentTile);
			}
		}
	}

	public Collection<Node> getTiles(){
		Collection<Node> rectangles = new ArrayList<Node>();
		for(Tile currentTile : tiles) {
			rectangles.add(currentTile.getSquare());
		}

		return rectangles;
	}
}
