// TnIType.java, used for
// JDubiel.java: computer program for pc-set analysis
// by Akira Takaoka, akira@music.columbia.edu
// Copyright (C) 1994-2010 Akira Takaoka
// All rights reserved.

package cp.model.setclass;

public class TnIType extends TnType {

	// Constructors
	public TnIType() {
		this.set = new int[SCALE_STEPS];
		this.m7set = new int[SCALE_STEPS];
	}

	public TnIType(int[] s) {
		set = new int[s.length];
		for (int i = 0; i < s.length; i++)
			set[i] = s[i];
	}

	public TnIType(char[] t) {
		charSet = new char[t.length];
		for (int i = 0; i < t.length; i++)
			charSet[i] = t[i];
	}

	public TnIType(int size) {
		set = new int[size];
		m7set = new int[size];
		tntype = new int[size];
		tnitype = new int[size];
		tntnitype = new int[size];
	}

	// Find the TnI type of a pc-set.
	public void getTnIType() {
		getTnType();
		tnitype = getBestNormal(sortPcSet(invertPcSet(tntype)));
	}

	// Return the TnI type of a pc-set.
	public int[] returnTnIType(int[] st) {
		return getBestNormal(checkDuplicates(sortPcSet(invertPcSet(st))));
	}
}
