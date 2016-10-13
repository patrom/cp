package cp.out.orchestration.notetemplate;

import org.apache.commons.math3.random.RandomDataGenerator;

public class ThreeNoteTemplate {

	private final RandomDataGenerator randomDataGenerator = new RandomDataGenerator();

	public int[] note012(){
		return new int[] {0,1,2};
	}
	
	public int[] note210(){
		return new int[] {2,1,0};
	}
	
	public int[] random3Note(){
		return randomDataGenerator.nextPermutation(3, 3);
	}
}
