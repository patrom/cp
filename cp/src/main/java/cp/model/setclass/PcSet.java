// TnIType.java, used for
// JDubiel.java: computer program for pc-set analysis
// by Akira Takaoka, akira@music.columbia.edu
// Copyright (C) 1994-2010 Akira Takaoka
// All rights reserved.
package cp.model.setclass;

import java.util.Arrays;

public class PcSet extends Set {

	// Constructors
	public PcSet() {
	}

	public PcSet(int[] s) {
		set = new int[s.length];
		for (int i = 0; i < s.length; i++)
			set[i] = s[i];
	}

	public PcSet(char[] t) {
		charSet = new char[t.length];
		for (int i = 0; i < t.length; i++)
			charSet[i] = t[i];
	}

	public PcSet(int size) {
		set = new int[size];
		m7set = new int[size];
		tntype = new int[size];
		tnitype = new int[size];
		tntnitype = new int[size];
	}

	// Find the inversion of pc-set x.
	public int[] invertPcSet(int[] x) {
		int[] pcs = new int[x.length];

		for (int i = 0; i < x.length; i++)
			pcs[i] = x[x.length - 1] - x[i];

		return pcs;
	}

	// Find the set of a pc-set multiplied by 7.
	public void getMultipliedBy7() {
		m7set = getBestNormal(checkDuplicates(sortPcSet(multiplyBy(set, 7))));
	}

	// Find the set of a pc-set multiplied by 7.
	public int[] returnMultipliedBy7(int[] st) {
		return getBestNormal(checkDuplicates(sortPcSet(multiplyBy(st, 7))));
	}

	// Find the set of a pc-set multiplied by x.
	public int[] getMultiplied(int x) {
		return getBestNormal(checkDuplicates(sortPcSet(multiplyBy(set, x))));
	}

	// Multiply by y each member of a pc-set.
	public int[] multiplyBy(int[] t, int y) {
		int[] m = new int[t.length];
		for (int i = 0; i < t.length; i++)
			m[i] = (y * t[i]) % SCALE_STEPS;

		return m;
	}

	// Sort pcs in ascending order.
	public int[] sortPcSet(int[] s) {
		int[] sortedSet = new int[s.length];

		for (int i = 0; i < s.length; i++)
			sortedSet[i] = s[i];

		for (int i = 0; i < sortedSet.length - 1; i++)
			for (int j = i + 1; j < sortedSet.length; j++)
				if (sortedSet[i] > sortedSet[j]) {
					int temp = sortedSet[i];
					sortedSet[i] = sortedSet[j];
					sortedSet[j] = temp;
				}

		return sortedSet;
	}

	// Eliminate those pcs which occur more than once.
	public int[] checkDuplicates(int[] s) {

		int[] pcs1 = new int[s.length];
		int ii = 0, newSize = 0;
		while (ii < s.length) {
			int iii = 1;
			while (ii + iii < s.length) {
				if (s[ii + iii] == s[ii])
					break;
				else
					iii++;
			}
			if (ii + iii == s.length)
				pcs1[newSize++] = s[ii];

			ii++;
		}

		int[] pcs2 = new int[newSize];
		for (int i = 0; i < pcs2.length; i++)
			pcs2[i] = pcs1[i];

		return pcs2;
	}

	// Eliminate those chars which occur more than once.
	public char[] checkDuplicateChars(char[] s) {

		char[] pcs1 = new char[s.length];
		int ii = 0, newSize = 0;
		while (ii < s.length) {
			int iii = 1;
			while (ii + iii < s.length) {
				if (s[ii + iii] == s[ii])
					break;
				else
					iii++;
			}
			if (ii + iii == s.length)
				pcs1[newSize++] = s[ii];

			ii++;
		}

		char[] pcs2 = new char[newSize];
		for (int i = 0; i < pcs2.length; i++)
			pcs2[i] = pcs1[i];

		return pcs2;
	}

	// Eliminates space(s) at the beginning and the end of a tone row.
	public String removeFistLastSpaces(String s) {
		char[] pcs1 = new char[s.length()];
		s.getChars(0, pcs1.length, pcs1, 0);

		int ii = 0;
		while (ii < pcs1.length && pcs1[ii] == ' ')
			ii++;

		int iii = 1;
		while (iii < pcs1.length && pcs1[s.length() - iii] == ' ')
			iii++;

		StringBuffer noSpacesStringBuffer = new StringBuffer();

		for (int i = ii; i <= s.length() - iii; i++)
			noSpacesStringBuffer.append(pcs1[i]);

		return noSpacesStringBuffer.toString();
	}

	// Eliminates consecutive spaces.
	public String removeConsecutiveSpaces(String s) {
		char[] pcs1 = new char[s.length()];
		s.getChars(0, pcs1.length, pcs1, 0);

		StringBuffer noConsecutiveSpacesStringBuffer = new StringBuffer();
		int ii = 0;
		while (ii < pcs1.length) {
			noConsecutiveSpacesStringBuffer.append(pcs1[ii]);
			if (pcs1[ii] == ' ')
				while (ii < pcs1.length && pcs1[ii] == ' ')
					ii++;
			else
				ii++;
		}

		return noConsecutiveSpacesStringBuffer.toString();
	}

	// Eliminates duplicates from a string.
	public String removeDuplicatesInString(String s) {
		char[] pcs1 = new char[s.length()];
		s.getChars(0, pcs1.length, pcs1, 0);

		char[] pcs2 = new char[pcs1.length];
		int ii = 0, newSize = 0;
		while (ii < pcs1.length) {
			int iii = 1;
			while (ii + iii < pcs1.length) {
				if (pcs1[ii + iii] == pcs1[ii] && pcs1[ii] != ' ')
					break;
				else
					iii++;
			}
			if (ii + iii == pcs1.length)
				pcs2[newSize++] = pcs1[ii];

			ii++;
		}

		StringBuffer withoutDuplicatesStringBuffer = new StringBuffer();
		for (int i = 0; i < newSize; i++)
			withoutDuplicatesStringBuffer.append(pcs2[i]);

        return withoutDuplicatesStringBuffer
                .toString();
	}

	// Counts the number of non-space characters.
	public int numberOfNonSpaces(String s) {
		int ii = 0, iii = 0;
		while (ii < s.length()) {
			if (s.charAt(ii) != ' ')
				iii++;

			ii++;
		}

		return iii;
	}

	// Find the Tn type of a pc-set.
	public int[] getBestNormal(int[] s) {

		// Make an array of all the intervals between adjacent pcs.
		int[] intervals = new int[s.length];
		int[] intervalOrder = new int[s.length];

		intervals[0] = ((s[0] + SCALE_STEPS) - s[s.length - 1]) % SCALE_STEPS;
		intervalOrder[0] = 0;

		for (int i = 1; i < s.length; i++) {
			intervals[i] = ((s[i] + SCALE_STEPS) - s[i - 1]) % SCALE_STEPS;
			intervalOrder[i] = i;
		}

		// Sort the intervals in ascending order.
		for (int i = 0; i < s.length - 1; i++)
			for (int ii = i + 1; ii < s.length; ii++)
				if (intervals[i] > intervals[ii]) {
					int tempintvl = intervals[i];
					int tempith = intervalOrder[i];
					intervals[i] = intervals[ii];
					intervalOrder[i] = intervalOrder[ii];
					intervals[ii] = tempintvl;
					intervalOrder[ii] = tempith;
				}

		int numberOfLargestIntervals = 1; // the number of the largest interval
		int indexOfLargestInterval;

		while (intervals[s.length - numberOfLargestIntervals] == intervals[s.length
				- (numberOfLargestIntervals + 1)]
				&& numberOfLargestIntervals < (s.length - 1))
			numberOfLargestIntervals++;

		int[][] next = new int[numberOfLargestIntervals][s.length];
		int[] pcs = new int[s.length];
		if (numberOfLargestIntervals == 1) {
			indexOfLargestInterval = intervalOrder[s.length - 1];
			for (int i = 0; i < s.length; i++)
				pcs[i] = s[(indexOfLargestInterval + i) % s.length];
		} else {
			for (int i = 0; i < numberOfLargestIntervals; i++)
				for (int ii = 0; ii < s.length; ii++)
					next[i][ii] = s[(intervalOrder[s.length - (1 + i)] + ii)
							% s.length];

			int smallest = 0;
			for (int i = 1; i < next.length; i++) {
				int ii = 0;
				do {
					if (((next[smallest][ii + 1] + 12) - next[smallest][ii]) % 12 > ((next[i][ii + 1] + 12) - next[i][ii]) % 12) {
						smallest = i;
						break;
					} else if (((next[smallest][ii + 1] + 12) - next[smallest][ii]) % 12 < ((next[i][ii + 1] + 12) - next[i][ii]) % 12) {
						break;
					} else {
						ii++;
					}
				} while (ii + 1 < next[0].length);
			}

			for (int i = 0; i < s.length; i++)
				pcs[i] = next[smallest][i];
		}

		// Convert pcs[] into the prime form.
		int[] best = new int[pcs.length];
		for (int i = 0; i < pcs.length; i++)
			best[i] = (pcs[i] + SCALE_STEPS - pcs[0]) % SCALE_STEPS;

		return best;
	}

	// Find the interval vector of a pc-set.
	public int[] getIVector() {
		int[] ivector = new int[6];
		// SCALE_STEPS is defined in Set.java
		for (int i = 1; i < set.length; i++)
			for (int ii = 0; ii < i; ii++) {
				int ic = ((set[i] + SCALE_STEPS) - set[ii]) % SCALE_STEPS;
				switch (ic) {
				case 1:
				case 11:
					++ivector[0];
					break;
				case 2:
				case 10:
					++ivector[1];
					break;
				case 3:
				case 9:
					++ivector[2];
					break;
				case 4:
				case 8:
					++ivector[3];
					break;
				case 5:
				case 7:
					++ivector[4];
					break;
				default:
					++ivector[5];
				}
			}
		return ivector;
	}

	// Get the Tn's of pc-set "set" between Tmin and Tmax.
	public int[][] getTns(int min, int max) {
		int[][] tns = new int[max - min + 1][set.length];
		for (int n = min; n <= max; n++)
			// n: that of Tn
			for (int j = 0; j < set.length; j++)
				// j: pitch-class
				tns[n - min][j] = (set[j] + n) % SCALE_STEPS;
		return tns;
	}

	// Return the Tn's of pc-set "st" between T(min) and T(max).
	public int[][] returnTns(int[] st, int min, int max) {
		int[][] tns = new int[max - min + 1][st.length];
		for (int n = min; n <= max; n++)
			// n: that of Tn
			for (int j = 0; j < st.length; j++)
				// j: pitch class
				tns[n - min][j] = (st[j] + n) % SCALE_STEPS;
		return tns;
	}

	// Get all the TnI's of pc-set "set."
	public int[][] getTnIs(int min, int max) {
		int[][] tnis = new int[max - min + 1][set.length];
		for (int n = min; n <= max; n++)
			// n: that of TnI
			for (int j = 0; j < set.length; j++)
				// j: a pitch-class
				tnis[n - min][j] = (SCALE_STEPS - set[j] + n) % SCALE_STEPS;
		return tnis;
	}

	// Return all the TnI's of given pc-set "st."
	public int[][] returnTnIs(int[] st, int min, int max) {
		int[][] tnis = new int[max - min + 1][st.length];
		for (int n = min; n <= max; n++)
			// n: that of TnI
			for (int j = 0; j < st.length; j++)
				// j: a pitch-class
				tnis[n - min][j] = (SCALE_STEPS - st[j] + n) % SCALE_STEPS;
		return tnis;
	}

	// Get all the Mn's of pc-set "set."
	public int[][] getMns(int min, int max) {
		int[][] mns = new int[max - min + 1][set.length];
		for (int n = min; n <= max; n++)
			// n: that of Mn
			for (int j = 0; j < set.length; j++)
				// j: a pitch-class
				mns[n - min][j] = (set[j] * n) % SCALE_STEPS;
		return mns;
	}

	// Return all the Mn's of given pc-set "st."
	public int[][] returnMns(int[] st, int min, int max) {
		int[][] mns = new int[max - min + 1][st.length];
		for (int n = min; n <= max; n++)
			// n: that of Mn
			for (int j = 0; j < st.length; j++)
				// j: a pitch-class
				mns[n - min][j] = (st[j] * n) % SCALE_STEPS;
		return mns;
	}

	// Get all the RTn's of sequence "set."
	public int[][] getRTns(int min, int max) {
		int[][] rtns = new int[max - min + 1][set.length];
		for (int n = min; n <= max; n++)
			// n: that of RTn
			for (int j = 0; j < set.length; j++)
				// j: a pitch-class
				rtns[n - min][set.length - 1 - j] = (set[j] + n) % SCALE_STEPS;
		return rtns;
	}

	// Get all the RTnI's of sequence "set."
	public int[][] getRTnIs(int min, int max) {
		int[][] rtnis = new int[max - min + 1][set.length];
		for (int n = min; n <= max; n++)
			// n: that of RTnI
			for (int j = 0; j < set.length; j++)
				// j: a pitch-class
				rtnis[n - min][set.length - 1 - j] = (SCALE_STEPS - set[j] + n)
						% SCALE_STEPS;
		return rtnis;
	}

	// Get all the rn's of sequence "set." max <= set.length
	public int[][] rotateSequence(int min, int max) {
		int[][] rotated = new int[max - min + 1][set.length];
		for (int n = min; n <= max; n++)
			// n: that of rn
			for (int j = 0; j < set.length; j++)
				// j: a pitch-class
				rotated[n - min][(n + j) % set.length] = set[j];

		return rotated;
	}

	// Get rn of given "n" and sequence "seq." max <= set.length
	public int[] rotateSeqByN(int[] seq, int n) {
		int[] rotatedByN = new int[seq.length];
		for (int i = 0; i < seq.length; i++)
			// n: that of rn
			rotatedByN[(n + i) % seq.length] = seq[i];

		return rotatedByN;
	}
}
