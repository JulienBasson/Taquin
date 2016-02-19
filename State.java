import java.util.ArrayList;
import java.util.List;
import java.awt.Point;

public class State {
    private ArrayList<ArrayList<Integer>> matrix;
    private final int size;
    private Point gap;

    public State(int size, List<Integer> elements){
        this.size = size;
        assert elements.size() == size*size;
        int n = 0;
        for (int i = 0; i < size; i++) {
            matrix.add(new ArrayList<Integer>());
            for (int j = 0; j < size; j++) {
                matrix.get(i).set(j, elements.get(n++));
            }
        }
    }

    public boolean equals(State other){
        if(size != other.size) return false;
        for (int i = 0; i < size; i++) {
            if(!matrix.get(i).equals(other.matrix.get(i)))
                return false;
        }
        return true;
    }

    public void move(Direction dir){
        int block;
        switch (dir) {
            case UP: // move bottom block up
                block = matrix.get(gap.y+1).get(gap.x);
                matrix.get(gap.y).set(gap.x, block);
                matrix.get(gap.y+1).set(gap.x, 0);
                ++gap.y;
                break;
            case DOWN: // move top block down
                block = matrix.get(gap.y-1).get(gap.x);
                matrix.get(gap.y).set(gap.x, block);
                matrix.get(gap.y-1).set(gap.x, 0);
                --gap.y;
                break;
            case RIGHT: // move left block right
                block = matrix.get(gap.y).get(gap.x-1);
                matrix.get(gap.y).set(gap.x, block);
                matrix.get(gap.y).set(gap.x-1, 0);
                --gap.x;
                break;
            case LEFT: // move right block left
                block = matrix.get(gap.y).get(gap.x+1);
                matrix.get(gap.y).set(gap.x, block);
                matrix.get(gap.y).set(gap.x+1, 0);
                ++gap.x;
                break;
        }
    }

    public void move(List<Direction> dirs){
        for (Direction dir : dirs) {
            move(dir);
        }
    }
}
