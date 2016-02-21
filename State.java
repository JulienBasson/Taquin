import java.util.ArrayList;
import java.util.List;
import java.awt.Point;

public class State {
    private ArrayList<ArrayList<Integer>> board;
    private final int size;
    private Point gap;

    private void generateBoard(List<Integer> elements){
        assert elements.size() == size*size;
        int n = 0;
        for (int i = 0; i < size; i++) {
            board.add(new ArrayList<Integer>());
            for (int j = 0; j < size; j++) {
                board.get(i).set(j, elements.get(n++));
            }
        }
    }

    public State(int size, List<Integer> elements){
        this.size = size;
        generateBoard(elements);
    }

    public State(int size){
        List<Integer> elements = new ArrayList<Integer>();
        for (int i = 1; i < size*size-1; i++) {
            elements.add(i);
        }
        elements.add(0);

        this.size = size;
        generateBoard(elements);
    }

    public boolean equals(State other){
        if(size != other.size) return false;
        for (int i = 0; i < size; i++) {
            if(!board.get(i).equals(other.board.get(i)))
                return false;
        }
        return true;
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

    public static State goal(int size){
        return new State(size);
    }
}
