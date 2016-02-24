public class Main {
    public static void main(String[] args) {
        State state = new State(3);
        System.out.println(state);
        System.out.println(state.availableMoves());
        state.move(Direction.DOWN);
        System.out.println(state);
        System.out.println(state.availableMoves());
        state.move(Direction.RIGHT);
        state.move(Direction.RIGHT);
        System.out.println(state);
        System.out.println(state.availableMoves());
    }
}
