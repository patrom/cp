// TnType.java, used for
// JDubiel.java: computer program for pc-set analysis
// by Akira Takaoka, akira@music.columbia.edu
// Copyright (C) 1994-2010 Akira Takaoka
// All rights reserved.

package cp.model.setclass;

public class TnType extends PcSet {

	// Constructors
	public TnType() {
		this.set = new int[SCALE_STEPS];
		this.m7set = new int[SCALE_STEPS];
	}

	public TnType(int[] s) {
		set = new int[s.length];
		for (int i = 0; i < s.length; i++)
			set[i] = s[i];
	}

	public TnType(char[] t) {
		charSet = new char[t.length];
		for (int i = 0; i < t.length; i++)
			charSet[i] = t[i];
	}

	public TnType(int size) {
		set = new int[size];
		m7set = new int[size];
		tntype = new int[size];
		tnitype = new int[size];
		tntnitype = new int[size];
	}

	// Find normal order of a pc-set.
	public int[] getNormalOrder(int[] s) {
		return checkDuplicates(sortPcSet(s));
	}

	// Find the Tn type (best normal order) of a pc-set.
	public void getTnType() {
		tntype = getBestNormal(checkDuplicates(sortPcSet(set)));
	}

	// Return the Tn type (best normal order) of a pc-set.
	public int[] returnTnType(int[] st) {
		return getBestNormal(checkDuplicates(sortPcSet(st)));
	}
}
