// TnIType.java, used for
// JDubiel.java: computer program for pc-set analysis
// by Akira Takaoka, akira@music.columbia.edu
// Copyright (C) 1994-2010 Akira Takaoka
// All rights reserved.
package cp.model.setclass;

public class PcSetUnorderedProperties {

	private final TnTnIType setClass;
	private Set prime;
	
	public PcSetUnorderedProperties(int[] set) {
		super();
		this.setClass = new TnTnIType(set);
		setClass.getTnTnIType();
		prime = new Set();
	}

	public String getForteName() {
		return setClass.getPrimeByTnTnI().name;
	}

	public String[] getSetClassProperties() {
		prime = setClass.getPrimeByTnTnI();
		String tntypeString = setClass.tntypeToString();
		String tnitypeString = setClass.tnitypeToString();
		String tntnitypeString = setClass.tntnitypeToString();
		 
		// I-vector
		String ivectorString = prime.ivectorToString();
		String m7m7iString = "N/A";
		// M7/M7I
		if (prime.set.length <= 6){
			m7m7iString = prime.m7setToString();
		}
		// Symmetry
		String symmetryString = prime.symmetry[0] + ", " + prime.symmetry[1];
		String combinatorialString = "N/A";
		// Combinatoriality
		if (prime.set.length == 6){
			combinatorialString = prime.combinatorialToString();
		}
        return new String[]{prime.name,
                       tntypeString,
                       tnitypeString,
                       tntnitypeString,
                       ivectorString,
                       m7m7iString,
                       symmetryString,
                       combinatorialString};
	}
}
