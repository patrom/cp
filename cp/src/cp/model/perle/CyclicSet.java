package cp.model.perle;

import java.util.Arrays;

import cp.util.Util;


public class CyclicSet {

	private IntervalCycle pIntervalCycle;
	private IntervalCycle iIntervalCycle;
	private int[] pCycle;
	private int[] iCycle;
	private int[] cyclicSet;
	
	public CyclicSet(IntervalCycle pcycle, int indexPCycle) {
		if(pcycle.getIntervalCycle().length < indexPCycle){
			throw new IllegalArgumentException("cycle out of bound");
		}
		this.pIntervalCycle = pcycle;
		this.iIntervalCycle = pcycle.getInversePCycle();
		this.pCycle = pcycle.getIntervalCycle();
		this.iCycle = iIntervalCycle.getIntervalCycle();
		if (indexPCycle != 0) {
			this.pCycle = Util.rotateArray(pcycle.getIntervalCycle(), indexPCycle);
		}
		
		cyclicSet = new int[pcycle.getIntervalCycle().length * 2];
		for (int i = 0, j = 0; i < cyclicSet.length; i=i+2, j++) {
			cyclicSet[i] = pCycle[j];
			cyclicSet[i + 1] = iCycle[j];
		}
	}

	public int[] getCyclicSet(){
		return cyclicSet;
	}
	
	public int getLeftTonicSum(){
		return cyclicSet[0] + cyclicSet[1];
	}
	
	public int getRightTonicSum(){
		return cyclicSet[1] + cyclicSet[2];
	}
	
	public int[] getTonicSums(){
		int[] tonicSums = new int[2];
		tonicSums[0] = cyclicSet[0] + cyclicSet[1];
		tonicSums[1] = cyclicSet[1] + cyclicSet[2];
		return tonicSums;
	}
	
	public String getName(){
		return getPTonicSumName(getLeftTonicSum() % 12) + getITonicSumName(getRightTonicSum() % 12);
	}

	private String getPTonicSumName(int tonicSum) {
		String name;
		if (tonicSum % 2 == 0) {
			name = "p" + tonicSum;
		}else{
			name = "i" + tonicSum;
		}
		return name;
	}
	
	private String getITonicSumName(int tonicSum) {
		String name;
		if (tonicSum % 2 == 0) {
			name = "i" + tonicSum;
		}else{
			name = "p" + tonicSum;
		}
		return name;
	}
	
	public int getCyclicInterval(){
		return pIntervalCycle.getInterval();
	}
	
	public void transpose(int step){//update pcycle, icycle???
		for (int i = 0; i < cyclicSet.length; i++) {
			cyclicSet[i] = (cyclicSet[i] + step) % 12;
		}
	}
	
	/**
	 * Semi-transposition
	 * @param step: step 0 will semi-transpose
	 */
	public void semiTranspose(int step){//update pcycle, icycle???
		for (int i = 0; i < cyclicSet.length; i = i + 2) {
			cyclicSet[i] = (cyclicSet[i] + step) % 12;
			cyclicSet[i + 1] = (cyclicSet[i + 1] + step + 1) % 12;
		}
	}
	
	public void inverse(int step){//update pcycle, icycle???
		for (int i = 0; i < cyclicSet.length; i++) {
			cyclicSet[i] = (12 - cyclicSet[i] + step) % 12;
		}
	}
	
	@Override
	public String toString() {
		return Arrays.toString(cyclicSet);
	}

}
