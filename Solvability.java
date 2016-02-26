import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.StringBuilder;

/**
 * Class which test the solvability of a configuration
 */
public class Solvability {
		
	public boolean isSolvent(int[][] taquin){
		// A map which contain the value in order with the value in the configuration
		Map<Integer, Integer> permutation = new HashMap<Integer, Integer>();
		// The value which te case should have
		int currentCase = 1;
		
		for(int i = 0; i < taquin.length; i++){
			for(int j = 0; j < taquin[i].length; j++){
				permutation.put(taquin[i][j], currentCase);
				//System.out.println("Should be " + taquin[i][j] + " there is " + currentCase);
				if(currentCase == taquin.length * taquin[1].length - 1)
					currentCase = 0;
				else
					currentCase++;
			}
		}
		
		System.out.println(affichePermutation(permutation));
		
		List<List<Integer>> cycles = new ArrayList<List<Integer>>();
		int currentValue, startValue;
		List<Integer> currentCycle;
		
		while(!permutation.isEmpty()){
			currentCycle = new ArrayList<Integer>(); 
			// get an object
			int value = (int) permutation.keySet().toArray()[0];
			System.out.println("Start value " + value);
			startValue = value;
			
			currentCycle.add(startValue);
			currentValue = permutation.get(startValue);
			permutation.remove(startValue);
			
			while(startValue != currentValue){
				// ajoute la valeur actuelle au cycle
				currentCycle.add(currentValue);
				System.out.println("Current value " + currentValue);
				// modifie la valeur de la valeur actuelle
				currentValue = permutation.get(currentValue);
				// supprime la valeur de la liste des permutations
				permutation.remove(currentValue);
			}
			
			cycles.add(currentCycle);
		}
		
		int[] nbOfElements = new int[cycles.size()];
		int index = 0;
		for(List<Integer> cycle : cycles) {
			nbOfElements[index] = cycle.size() % 2;
			index++;
		}
		
		int sum = 0;
		for(int i : nbOfElements){
			sum += i; 
		}
		
		return sum % 2 == 0;
	}
	
	private String affichePermutation(Map<Integer, Integer> permutation){
		StringBuilder builder = new StringBuilder("");
		for(Object key : permutation.keySet().toArray()){
			builder.append(key + " => " + permutation.get(key) + "\n"); 
		}
		
		return builder.toString();
	}
}
