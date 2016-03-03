import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Random;
import java.io.FileNotFoundException;

public class Main {
	private static Direction randomDirection(Set<Direction> set) {
		Random rand = new Random(System.currentTimeMillis());
		List<Direction> setArray = new ArrayList<Direction>(set);
		return setArray.get(rand.nextInt(set.size()));
	}

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

			List<Direction> moves = new ArrayList<Direction>();
			for (int i = 0; i < 20; i++) {
				Direction nextMove = randomDirection(state.availableMoves());
				moves.add(nextMove);
				state = state.move(nextMove);
			}
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
