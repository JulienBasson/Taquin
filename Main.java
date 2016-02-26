import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        State state = new State(3);
        Heuristic he = new Manhattan(state);
        Solvability solv = new Solvability(state);

        System.out.println(state.availableMoves());
        System.out.println(solv.isSolvable(state));
        System.out.println(state.inversionCount());
        System.out.println(he.costLeft(state));
        System.out.println(state);

        state.move(Direction.DOWN);

        System.out.println(state.availableMoves());
        System.out.println(solv.isSolvable(state));
        System.out.println(state.inversionCount());
        System.out.println(he.costLeft(state));
        System.out.println(state);

        state.move(Direction.RIGHT);
        state.move(Direction.RIGHT);

        System.out.println(state.availableMoves());
        System.out.println(solv.isSolvable(state));
        System.out.println(state.inversionCount());
        System.out.println(he.costLeft(state));
        System.out.println(state);

        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(8);// swapped
        list.add(7);//
        list.add(0);
        State impossible = new State(3, list);

        System.out.println(impossible.availableMoves());
        System.out.println(solv.isSolvable(impossible));
        System.out.println(impossible.inversionCount());
        System.out.println(he.costLeft(impossible));
        System.out.println(impossible);
    }
}
