import java.util.List;
import java.util.ArrayList;

public class IterativeDeepeningAStar implements Algorithm {
    final State target;
    final Heuristic h;
    public IterativeDeepeningAStar(State target) {
        this.target = target;
        this.h = new Manhattan(target);
    }

    public List<Direction> solve(State from) {
        double bound = h.costLeft(from);
        while (true) {
            Path t = search(from, 0, bound, new ArrayList<Direction>());
            if (t.found)
                return t.path;
            if (t.cost == Double.POSITIVE_INFINITY)
                return null;
            bound = t.cost;
        }
    }

    private Path search(
        State node, double costAlready, double bound, List<Direction> previousMoves) {
        double cheapestPath = costAlready + h.costLeft(node);
        if (cheapestPath > bound)
            return new Path(previousMoves, cheapestPath, false);
        if (node.equals(target))
            return new Path(previousMoves, costAlready, true);

        Path min = new Path(previousMoves, Double.POSITIVE_INFINITY, false);
        for (Direction dir : node.availableMoves()) {
            List<Direction> moves = new ArrayList<Direction>(previousMoves);
            moves.add(dir);

            State nextNode = node.move(dir);

            Path t = search(nextNode,
                costAlready + moves.size(), // step cost is how far we are from root
                bound, moves);

            if (t.found)
                return t;
            if (t.cost < min.cost)
                min = t;
        }
        return min;
    }

    private class Path {
        public Path(List<Direction> path, double cost, boolean found) {
            this.path = path;
            this.cost = cost;
            this.found = found;
        }
        public final List<Direction> path;
        public final double cost;
        public final boolean found;
    }
}
