import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.awt.Point;

public class State {
    private ArrayList<ArrayList<Integer>> board;
    private final int size;
    private Point gap;

    private void buildBoard(List<Integer> elements){
        assert elements.size() == size*size;
        int n = 0;
        for (int i = 0; i < size; i++) {
            board.add(new ArrayList<Integer>());
            for (int j = 0; j < size; j++) {
                int toAdd = elements.get(n++);
                if(toAdd == 0) this.gap = new Point(j, i);
                board.get(i).add(toAdd);
            }
        }
    }

    public State(int size, List<Integer> elements){
        this.size = size;
        buildBoard(elements);
    }

    public State(int size){
        List<Integer> elements = new ArrayList<Integer>();
        for (int i = 1; i < size*size; i++) {
            elements.add(i);
        }
        elements.add(0);

        this.size = size;
        this.board = new ArrayList<ArrayList<Integer>>();
        buildBoard(elements);
    }

    public boolean equals(State other){
        if(size != other.size) return false;
        for (int i = 0; i < size; i++) {
            if(!board.get(i).equals(other.board.get(i)))
                return false;
        }
        return true;
    }

    public Set<Direction> availableMoves(){
        Set<Direction> result = new TreeSet<Direction>();
        if(gap.y > 0)
            result.add(Direction.DOWN);
        if(gap.y < size-1)
            result.add(Direction.UP);
        if(gap.x > 0)
            result.add(Direction.RIGHT);
        if(gap.x < size-1)
            result.add(Direction.LEFT);
        return result;
    }

    public void move(Direction dir){
        int block;
        switch (dir) {
            case UP: // move bottom block up
                block = board.get(gap.y+1).get(gap.x);
                board.get(gap.y).set(gap.x, block);
                board.get(gap.y+1).set(gap.x, 0);
                ++gap.y;
                break;
            case DOWN: // move top block down
                block = board.get(gap.y-1).get(gap.x);
                board.get(gap.y).set(gap.x, block);
                board.get(gap.y-1).set(gap.x, 0);
                --gap.y;
                break;
            case RIGHT: // move left block right
                block = board.get(gap.y).get(gap.x-1);
                board.get(gap.y).set(gap.x, block);
                board.get(gap.y).set(gap.x-1, 0);
                --gap.x;
                break;
            case LEFT: // move right block left
                block = board.get(gap.y).get(gap.x+1);
                board.get(gap.y).set(gap.x, block);
                board.get(gap.y).set(gap.x+1, 0);
                ++gap.x;
                break;
        }
    }

    public void move(List<Direction> dirs){
        for (Direction dir : dirs) {
            move(dir);
        }
    }

    public int size() {
        return size;
    }

    public String toString() {
        String result = "";
        for (ArrayList<Integer> row : board) {
            for (Integer i : row) {
                result += i + " ";
            }
            result = result.substring(0, result.length()-1) + '\n';
        }
        return result;
    }
}
