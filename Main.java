import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Random;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {
        if (args.length > 0) {
            try {
                testingFile(args[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            State orig = new State(5);
            System.out.println(orig);
            State state = new State(orig);
            Algorithm algo = new IterativeDeepeningAStar(orig);

            state.shuffle();

            List<Direction> moves = new ArrayList<Direction>();
            System.out.println(state);
            System.out.println(moves);
            System.out.println(algo.solve(state));
        }
    }

    private static void testingFile(String file) throws FileNotFoundException {
        ReadFile rf = new ReadFile(file);
        ReadFile.Init contents = rf.getDataFile();
        State original = new State(contents.size, contents.target);
        Algorithm algo = new IterativeDeepeningAStar(original);
        State state = new State(contents.size, contents.init);

        System.out.println(algo.solve(state));
    }
}
