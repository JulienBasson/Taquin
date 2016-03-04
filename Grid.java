import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.Node;

public class Grid {
    private Map<Point, Tile> tiles;
    private final int nbOfTiles;
    private final int size; // pixel size
    private State state;

    public Grid(int nbOfTiles, int size, State state) {
        tiles = new HashMap<Point, Tile>();
        this.size = size;

        // ajout excpetion si pas un carre
        this.nbOfTiles = nbOfTiles;
        this.state = state;
        createTiles(state);
    }

    private void createTiles(State state) {
        int tileSize = (int) Math.round(size / nbOfTiles);
        Tile currentTile;
        Point currentPoint;
        int currentValue;
        for (int i = 0; i < nbOfTiles; i++) {
            for (int j = 0; j < nbOfTiles; j++) {
                currentValue = state.getValue(i, j);
                if (currentValue == 0)
                    continue;
                currentPoint = new Point(i * tileSize, j * tileSize);
                currentTile = new Tile(Integer.toString(currentValue), tileSize, currentPoint);
                this.tiles.put(new Point(i, j), currentTile);
            }
        }
    }

    public Collection<Tile> getTiles() {
        return tiles.values();
    }

    public void move(Direction dir) {
        if (!state.availableMoves().contains(dir))
            return;
        Point pointToMove = getTileToMove(dir);
        Tile tile = tiles.remove(pointToMove);
        tile.move(dir);
        tiles.put(state.gapPosition(), tile);
        state = state.move(dir);
    }

    private Point getTileToMove(Direction dir) {
        Point gap = state.gapPosition();
        Point pointToMove = gap;
        switch (dir) {
            case UP:
                pointToMove.y++;
                break;
            case DOWN:
                pointToMove.y--;
                break;
            case LEFT:
                pointToMove.x++;
                break;
            case RIGHT:
                pointToMove.x--;
                break;
        }
        return pointToMove;
    }
}
