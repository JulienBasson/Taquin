public class Main {
    public static void main(String[] args) {
        State state = new State(3);
        Heuristic he = new Manhattan(state);

        System.out.println(state.availableMoves());
        System.out.println(he.costLeft(state));
        System.out.println(state);

        state.move(Direction.DOWN);

        System.out.println(state.availableMoves());
        System.out.println(he.costLeft(state));
        System.out.println(state);

        state.move(Direction.RIGHT);
        state.move(Direction.RIGHT);

        System.out.println(state.availableMoves());
        System.out.println(he.costLeft(state));
        System.out.println(state);
    }
}
