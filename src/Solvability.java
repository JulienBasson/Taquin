import java.awt.Point;

public class Solvability {
    private final State initial;
    public Solvability(State initial) {
        this.initial = initial;
    }

    public boolean isSolvable(State s) {
        int size = s.size();
        int inversions = s.inversionCount();
        Point gap = s.gapPosition();

        // oddity is counted from the bottom here
        boolean oddRowGap = ((size - gap.y) & 1) == 1;

        return (((size & 1) == 1) && ((inversions & 1) == 0))
            || (((size & 1) == 0) && (oddRowGap == ((inversions & 1) == 0)));
    }
}
