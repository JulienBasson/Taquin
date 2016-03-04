import java.awt.Point;
import java.util.Map;
import java.util.HashMap;

public class LinearConflict implements Heuristic {
    private Map<Integer, Point> targetCoords;
    private int size;
    public LinearConflict(State target) {
        this.targetCoords = new HashMap<Integer, Point>();
        this.size = target.size();
        for (int i = 0; i < target.size(); i++) {
            for (int j = 0; j < target.size(); j++) {
                Point p = new Point(j, i);
                targetCoords.put(target.getValue(p), p);
            }
        }
    }

    public double costLeft(State state) {
        if (state.size() != size)
            return Double.POSITIVE_INFINITY;

        int total = 0;
        for (int j = 0; j < state.size(); j++) {
            for (int i = 0; i < state.size(); i++) {
                Point currentPosition = new Point(i, j);
                int currentValue = state.getValue(i, j);
                for (int k = i+1; k < state.size(); k++) {
                    if(state.getValue(k, j) < currentValue)
                        total += 2;
                }
            }
        }
        return total;
    }
}
