import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.Iterator;
import java.awt.Point;

public class State implements Iterable<Integer> {
    private ArrayList<ArrayList<Integer>> board;
    private final int size;
    private Point gap;

    private void buildBoard(List<Integer> elements) {
        assert elements.size() == size * size;
        int n = 0;
        for (int i = 0; i < size; i++) {
            board.add(new ArrayList<Integer>());
            for (int j = 0; j < size; j++) {
                int toAdd = elements.get(n++);
                if (toAdd == 0)
                    this.gap = new Point(j, i);
                board.get(i).add(toAdd);
            }
        }
    }

    public State(State toCopy) {
        this.size = toCopy.size;
        this.gap = new Point(toCopy.gap);
        this.board = new ArrayList<ArrayList<Integer>>();
        for (ArrayList<Integer> list : toCopy.board) {
            this.board.add(new ArrayList<Integer>(list));
        }
    }

    public State(int size, List<Integer> elements) {
        this.size = size;
        this.board = new ArrayList<ArrayList<Integer>>();
        buildBoard(elements);
    }

    public State(int size) {
        List<Integer> elements = new ArrayList<Integer>();
        for (int i = 1; i < size * size; i++) {
            elements.add(i);
        }
        elements.add(0);

        this.size = size;
        this.board = new ArrayList<ArrayList<Integer>>();
        buildBoard(elements);
    }

    public boolean equals(State other) {
        if (size != other.size)
            return false;
        for (int i = 0; i < size; i++) {
            if (!board.get(i).equals(other.board.get(i)))
                return false;
        }
        return true;
    }

    public Set<Direction> availableMoves() {
        Set<Direction> result = new TreeSet<Direction>();
        if (gap.y > 0)
            result.add(Direction.DOWN);
        if (gap.y < size - 1)
            result.add(Direction.UP);
        if (gap.x > 0)
            result.add(Direction.RIGHT);
        if (gap.x < size - 1)
            result.add(Direction.LEFT);
        return result;
    }

    public State move(Direction dir) {
        State result = new State(this);
        int block;
        switch (dir) {
            case UP: // move bottom block up
                block = result.board.get(gap.y + 1).get(gap.x);
                result.board.get(gap.y).set(gap.x, block);
                result.board.get(gap.y + 1).set(gap.x, 0);
                ++result.gap.y;
                break;
            case DOWN: // move top block down
                block = result.board.get(gap.y - 1).get(gap.x);
                result.board.get(gap.y).set(gap.x, block);
                result.board.get(gap.y - 1).set(gap.x, 0);
                --result.gap.y;
                break;
            case RIGHT: // move left block right
                block = result.board.get(gap.y).get(gap.x - 1);
                result.board.get(gap.y).set(gap.x, block);
                result.board.get(gap.y).set(gap.x - 1, 0);
                --result.gap.x;
                break;
            case LEFT: // move right block left
                block = result.board.get(gap.y).get(gap.x + 1);
                result.board.get(gap.y).set(gap.x, block);
                result.board.get(gap.y).set(gap.x + 1, 0);
                ++result.gap.x;
                break;
        }
        return result;
    }

    public int size() {
        return size;
    }

    public int getValue(int x, int y) {
        return board.get(y).get(x);
    }

    public int getValue(Point coord) {
        return getValue(coord.x, coord.y);
    }

    public Point gapPosition() {
        return new Point(gap);
    }

    public int inversionCount() {
        int total = 0;
        for (StateIterator i = new StateIterator(0, 0); i.hasNext();) {
            int currentVal = i.next();
            Point currentPos = i.position();

            if (currentVal != 0) {
                for (StateIterator j = new StateIterator(currentPos); j.hasNext();) {
                    int ahead = j.next();
                    if (ahead < currentVal && ahead != 0) {
                        ++total;
                    }
                }
            }
        }
        return total;
    }

    public String toString() {
        String result = "";
        for (ArrayList<Integer> row : board) {
            for (Integer i : row) {
                result += i + " ";
            }
            result = result.substring(0, result.length() - 1) + '\n';
        }
        return result;
    }

    public Iterator<Integer> iterator() {
        return new StateIterator(0, 0);
    }

    private class StateIterator implements Iterator<Integer> {
        Point next;
        public StateIterator(Point startingState) {
            next = new Point(startingState);
        }
        public StateIterator(int x, int y) {
            next = new Point(x, y);
        }
        public Point position() {
            return next;
        }
        public boolean hasNext() {
            return next.y < size;
        }
        public Integer next() {
            int val = getValue(next);
            if (next.x < size - 1)
                ++next.x;
            else {
                ++next.y;
                next.x = 0;
            }
            return val;
        }
    }
}
