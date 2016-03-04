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
			State orig = new State(3);
			System.out.println(orig);
			State state = new State(orig);
			BenchableAlgorithm algo = new IterativeDeepeningAStar(orig);
            state = state.shuffle();
			System.out.println(state);
			System.out.println(algo.solve(state));
			System.out.println(algo.getNbNode());
		}
	}

	private static void testingFile(String file) throws FileNotFoundException {
		ReadFile rf = new ReadFile(file);
		ReadFile.Init contents = rf.getDataFile();
		State original = new State(contents.size, contents.target);
		BenchableAlgorithm algo = new IterativeDeepeningAStar(original);
		State state = new State(contents.size, contents.init);
		
		System.out.println("State original: \n" + original);
		System.out.println("State state: \n" + state);
		
		
		long startTime = System.currentTimeMillis();
		List<Direction> listDirectionSolve = algo.solve(state);
		long time = System.currentTimeMillis() - startTime;
		
		System.out.println(listDirectionSolve);
		System.out.println("Nombre coup: \t" + listDirectionSolve.size());
		System.out.println("Noeuds visites: \t" + algo.getNbNode());
		System.out.println("Taille maximum de la taille de structure: \t" + algo.getMaximumSize());
		System.out.println("Temps CPU: \t" + time);
    }
}
