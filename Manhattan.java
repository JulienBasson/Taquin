import java.awt.Point;
import java.util.Map;
import java.util.HashMap;

public class Manhattan implements Heuristic {
    private Map<Integer, Point> targetCoords;
    private int size;
    public Manhattan(State target){
        this.targetCoords = new HashMap<Integer, Point>();
        this.size = target.size();
        for (int i = 0; i < target.size(); i++) {
            for (int j = 0; j < target.size(); j++) {
                Point p = new Point(j, i);
                targetCoords.put(target.getValue(p), p);
            }
        }
    }

    public double distance(State state){
        if(state.size() != size)
            return Double.POSITIVE_INFINITY;

        int total = 0;
        for (int j = 0; j < state.size(); j++) {
            for (int i = 0; i < state.size(); i++) {
                int currentValue = state.getValue(i, j);
                Point target = targetCoords.get(currentValue);
                int inc = Math.abs(i - target.x) + Math.abs(j - target.y);
                total += inc;
            }
        }
        return total;
    }
}
