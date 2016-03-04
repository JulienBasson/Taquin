import java.awt.Point;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grid {
    private Map<Point, Tile> tiles;
    private final int nbOfTiles;
    private final int size; // pixel size
    private State state;
    private State targetState;
    private int nbOfMoves;

    public Grid(int nbOfTiles, int size, State state) {
        tiles = new HashMap<Point, Tile>();
        this.size = size;
        this.nbOfMoves = 0;
        // ajout excpetion si pas un carre
        this.nbOfTiles = nbOfTiles;
        this.targetState = new State(nbOfTiles);
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
        ++nbOfMoves;
        Point pointToMove = getTileToMove(dir);
        Tile tile = tiles.remove(pointToMove);
        tile.move(dir);
        tiles.put(state.gapPosition(), tile);
        state = state.move(dir);
    }

    public void move(Point toMove) {
        int tileSize = size/nbOfTiles;
        Point gap = state.gapPosition();
        if (toMove.x/tileSize == gap.x+1 && toMove.y/tileSize == gap.y) {
            move(Direction.LEFT);
        } else if (toMove.x/tileSize == gap.x-1 && toMove.y/tileSize == gap.y) {
            move(Direction.RIGHT);
        } else if (toMove.x/tileSize == gap.x && toMove.y/tileSize == gap.y-1) {
            move(Direction.DOWN);
        } else if (toMove.x/tileSize == gap.x && toMove.y/tileSize == gap.y+1) {
            move(Direction.UP);
        }
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

    public int getMovesCount(){
        return nbOfMoves;
    }
    
    public boolean isFinish(){
        return state.equals(new State(nbOfTiles));
    }
    
    public int fewestMoves() {
       Algorithm algo = new IterativeDeepeningAStar(targetState);
       return algo.solve(state).size();
    }

    public void solve() {
        BenchableAlgorithm algo = new IterativeDeepeningAStar(targetState);
        List<Direction> solution = algo.solve(state);
        for(Direction dir : solution){
            move(dir);
        }
    }
}
