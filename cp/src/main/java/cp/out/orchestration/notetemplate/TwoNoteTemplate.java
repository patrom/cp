package cp.out.orchestration.notetemplate;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
public class TwoNoteTemplate {
	
	private final RandomDataGenerator randomDataGenerator = new RandomDataGenerator();

	public int[] note01(){
		return new int[] {0,1};
	}
	
	public int[] note011Repetition(){
		return new int[] {0,1,1};
	}
	
	public int[] note10(){
		return new int[] {1,0};
	}
	
	public int[] note110Repetition(){
		return new int[] {1,1,0};
	}
	
	public int[] random2Note(){
		return randomDataGenerator.nextPermutation(2, 2);
	}
	
	public static void main(String[] args) {
		RandomDataGenerator randomDataGenerator = new RandomDataGenerator();
		int[] permutation = randomDataGenerator.nextPermutation(2, 2);
		Arrays.stream(permutation).forEach(n -> System.out.println(n));
		Collection<Integer> pcs = new ArrayList<>();
		pcs.add(2);
		pcs.add(1);
		pcs.add(3);
		Collection<List<Integer>> permutatons = CollectionUtils.permutations(pcs);
		for (List<Integer> list : permutatons) {
			list.forEach(n -> System.out.print(n + ","));
			System.out.println();
		}	
	}
}
