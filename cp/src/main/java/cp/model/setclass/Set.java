// TnIType.java, used for
// JDubiel.java: computer program for pc-set analysis
// by Akira Takaoka, akira@music.columbia.edu
// Copyright (C) 1994-2010 Akira Takaoka
// All rights reserved.
package cp.model.setclass;

import java.lang.reflect.Array;

public class Set {

	final int SCALE_STEPS = 12;
	public int[] set;
	public int[] ivector = new int[6];
	public int[] m7set;
	public int[] symmetry = new int[2];
	public int[] combinatorial = new int[4];
	public char[] charSet;
	public String name; // Forte name
	public String description;

	// Duodecimal numbers, ab, AB, te, or TE.
	public String base12String;

	public int[] tntype;
	public int[] tnitype;
	public int[] tntnitype;

	// Constructors
	public Set() {

	}

	public Set(int[] s) {
		set = new int[s.length];
		for (int i = 0; i < s.length; i++)
			set[i] = s[i];
	}

	public Set(char[] t) {
		charSet = new char[t.length];
		for (int i = 0; i < t.length; i++)
			charSet[i] = t[i];
	}

	public Set(int size) {
		set = new int[size];
		m7set = new int[size];
		tntype = new int[size];
		tnitype = new int[size];
		tntnitype = new int[size];
	}

	// Check if there are non-numerical characters
	public String checkNonNumerals(String pcsetString) {
		StringBuffer nonNumericalChars = new StringBuffer(" ");
		for (int i = 0; i < pcsetString.length(); i++) {
			if (pcsetString.charAt(i) != '0' && pcsetString.charAt(i) != '1'
					&& pcsetString.charAt(i) != '2'
					&& pcsetString.charAt(i) != '3'
					&& pcsetString.charAt(i) != '4'
					&& pcsetString.charAt(i) != '5'
					&& pcsetString.charAt(i) != '6'
					&& pcsetString.charAt(i) != '7'
					&& pcsetString.charAt(i) != '8'
					&& pcsetString.charAt(i) != '9'
					&& pcsetString.charAt(i) != 'a'
					&& pcsetString.charAt(i) != 'A'
					&& pcsetString.charAt(i) != 'b'
					&& pcsetString.charAt(i) != 'B'
					&& pcsetString.charAt(i) != 't'
					&& pcsetString.charAt(i) != 'T'
					&& pcsetString.charAt(i) != 'e'
					&& pcsetString.charAt(i) != ' '
					&& pcsetString.charAt(i) != 'E') {
				nonNumericalChars.append("\"" + pcsetString.charAt(i) + "\" ");
			}
		}
		return nonNumericalChars.toString();
	}

	// Convert a char array into an int one.
	public void charSetToSet() {

		set = new int[charSet.length];

		for (int i = 0; i < charSet.length; i++) {
			int pc = Array.getInt(charSet, i);
			// Convert 'A' and 'B' into 10 and 11 respectively.
			switch (pc) {
			case 'a':
				base12String = new String("ab");
				set[i] = 10;
				break;
			case 'A':
				base12String = new String("AB");
				set[i] = 10;
				break;
			case 't':
				base12String = new String("te");
				set[i] = 10;
				break;
			case 'T':
				base12String = new String("TE");
				set[i] = 10;
				break;
			case 'b':
				base12String = new String("ab");
				set[i] = 11;
				break;
			case 'B':
				base12String = new String("AB");
				set[i] = 11;
				break;
			case 'e':
				base12String = new String("te");
				set[i] = 11;
				break;
			case 'E':
				base12String = new String("TE");
				set[i] = 11;
				break;
			default:
				set[i] = pc - '0';
			}
		}

		if (base12String == null)
			base12String = new String("NotUsed");
	}

	// Convert a set of letters (charSet) into an int array.
	public void letterSetToSet() {

		int[] in = new int[charSet.length];
		int i = 0, size = 0;

		while (i < charSet.length) {
			int pc = Array.getInt(charSet, i);

			switch (pc) {
			case 'c':
			case 'C':
				in[size] = 0;
				size++;
				break;
			case 'd':
			case 'D':
				in[size] = 2;
				size++;
				break;
			case 'e':
			case 'E':
				in[size] = 4;
				size++;
				break;
			case 'f':
			case 'F':
				in[size] = 5;
				size++;
				break;
			case 'g':
			case 'G':
				in[size] = 7;
				size++;
				break;
			case 'a':
			case 'A':
				in[size] = 9;
				size++;
				break;
			case 'B':
				in[size] = 11;
				size++;
				break;
			case '#':
				in[size - 1] += 1;
				break;
			case 'b':
				if (charSet[i - 1] == 'c' || charSet[i - 1] == 'C'
						|| charSet[i - 1] == 'd' || charSet[i - 1] == 'D'
						|| charSet[i - 1] == 'e' || charSet[i - 1] == 'E'
						|| charSet[i - 1] == 'f' || charSet[i - 1] == 'F'
						|| charSet[i - 1] == 'g' || charSet[i - 1] == 'G'
						|| charSet[i - 1] == 'a' || charSet[i - 1] == 'A'
						|| charSet[i - 1] == 'b' || charSet[i - 1] == 'B') {
					in[size - 1] -= 1;
				} else {
					in[size] = 11;
					size++;
				}
				break;
			default:
				break;
			}

			i++;
		}
		set = new int[size];
		for (int j = 0; j < size; j++)
			set[j] = in[j];
	}

	// Convert a char array into an int one.
	public int[] charArrayToIntOne(char[] c) {

		int[] s = new int[c.length];

		for (int i = 0; i < c.length; i++) {
			int pc = Array.getInt(c, i);
			// Convert 'A' and 'B' into 10 and 11 respectively.
			switch (pc) {
			case 'a':
				base12String = new String("ab");
				s[i] = 10;
				break;
			case 'A':
				base12String = new String("AB");
				s[i] = 10;
				break;
			case 't':
				base12String = new String("te");
				s[i] = 10;
				break;
			case 'T':
				base12String = new String("TE");
				s[i] = 10;
				break;
			case 'b':
				base12String = new String("ab");
				s[i] = 11;
				break;
			case 'B':
				base12String = new String("AB");
				s[i] = 11;
				break;
			case 'e':
				base12String = new String("te");
				s[i] = 11;
				break;
			case 'E':
				base12String = new String("TE");
				s[i] = 11;
				break;
			default:
				s[i] = pc - '0';
			}
		}

		if (base12String == null)
			base12String = new String("NotUsed");

		return s;
	}

	// Convert an int array into a char one
	public void setToCharSet() {

		charSet = new char[set.length];

		for (int i = 0; i < set.length; i++) {
			switch (set[i]) {
			case 10:
				if (base12String == null)
					charSet[i] = 'A';
				else if (base12String.equals("NotUsed"))
					charSet[i] = 'A';
				else if (base12String.equals("ab"))
					charSet[i] = 'a';
				else if (base12String.equals("AB"))
					charSet[i] = 'A';
				else if (base12String.equals("te"))
					charSet[i] = 't';
				else if (base12String.equals("TE"))
					charSet[i] = 'T';
				else
					charSet[i] = 'A';
				break;
			case 11:
				if (base12String == null)
					charSet[i] = 'B';
				else if (base12String.equals("NotUsed"))
					charSet[i] = 'B';
				else if (base12String.equals("ab"))
					charSet[i] = 'b';
				else if (base12String.equals("AB"))
					charSet[i] = 'B';
				else if (base12String.equals("te"))
					charSet[i] = 'e';
				else if (base12String.equals("TE"))
					charSet[i] = 'E';
				else
					charSet[i] = 'B';
				break;
			default:
				charSet[i] = (char) (set[i] + '0');
				break;
			}
		}
	}

	// Convert an int array into a char one
	public char[] intArrayToCharOne(int[] s) {

		char[] c = new char[s.length];

		for (int i = 0; i < s.length; i++) {
			switch (s[i]) {
			case 10:
				if (base12String == null)
					c[i] = 'A';
				else if (base12String.equals("NotUsed"))
					c[i] = 'A';
				else if (base12String.equals("ab"))
					c[i] = 'a';
				else if (base12String.equals("AB"))
					c[i] = 'A';
				else if (base12String.equals("te"))
					c[i] = 't';
				else if (base12String.equals("TE"))
					c[i] = 'T';
				else
					c[i] = 'A';
				break;
			case 11:
				if (base12String == null)
					c[i] = 'B';
				else if (base12String.equals("NotUsed"))
					c[i] = 'B';
				else if (base12String.equals("ab"))
					c[i] = 'b';
				else if (base12String.equals("AB"))
					c[i] = 'B';
				else if (base12String.equals("te"))
					c[i] = 'e';
				else if (base12String.equals("TE"))
					c[i] = 'E';
				else
					c[i] = 'B';
				break;
			default:
				c[i] = (char) (s[i] + '0');
				break;
			}
		}
		return c;
	}

	// Convert an int array to a String.
	// public String setToString() {
	// return pcSetArrayToString(set);
	// }

	// Convert an int array to a String.
	public String tntypeToString() {
		return pcSetArrayToString(tntype);
	}

	// Convert an int array to a String.
	public String tnitypeToString() {
		return pcSetArrayToString(tnitype);
	}

	// Convert an int array to a String.
	public String tntnitypeToString() {
		return pcSetArrayToString(tntnitype);
	}

	// Convert an int array to a String.
	public String ivectorToString() {
		return pcSetArrayToString(ivector);
	}

	// Convert an int array to a String.
	public String m7setToString() {
		return pcSetArrayToString(m7set);
	}

	// Convert an int array to a String.
	public String symmetryToString() {

		StringBuffer stStringBuffer = new StringBuffer();

		stStringBuffer.append(symmetry[0]);
		stStringBuffer.append(", ");
		stStringBuffer.append(symmetry[1]);

		return stStringBuffer.toString();
	}

	// Convert an int array to a String.
	public String combinatorialToString() {

		StringBuffer stStringBuffer = new StringBuffer();

		for (int i = 0; i < combinatorial.length; i++)
			stStringBuffer.append(combinatorial[i]);

		return stStringBuffer.toString();
	}

	// Convert an int array to a String.
	public String intArrayToString(int[] s) {

		StringBuffer stStringBuffer = new StringBuffer();

		for (int i = 0; i < s.length; i++)
			stStringBuffer.append(s[i]);

		return stStringBuffer.toString();
	}

	// Convert an int array to a String.
	public String pcSetArrayToString(int[] pcst) {

		StringBuffer stStringBuffer = new StringBuffer();

		for (int i = 0; i < pcst.length; i++) {

			switch (pcst[i]) {
			case 10:
				if (base12String == null)
					stStringBuffer.append('A');
				else if (base12String.equals("NotUsed"))
					stStringBuffer.append('A');
				else if (base12String.equals("ab"))
					stStringBuffer.append('a');
				else if (base12String.equals("AB"))
					stStringBuffer.append('A');
				else if (base12String.equals("te"))
					stStringBuffer.append('t');
				else if (base12String.equals("TE"))
					stStringBuffer.append('T');
				else
					stStringBuffer.append('A');
				break;
			case 11:
				if (base12String == null)
					stStringBuffer.append('B');
				else if (base12String.equals("NotUsed"))
					stStringBuffer.append('B');
				else if (base12String.equals("ab"))
					stStringBuffer.append('b');
				else if (base12String.equals("AB"))
					stStringBuffer.append('B');
				else if (base12String.equals("te"))
					stStringBuffer.append('e');
				else if (base12String.equals("TE"))
					stStringBuffer.append('E');
				else
					stStringBuffer.append('B');
				break;
			default:
				stStringBuffer.append(pcst[i]);
				break;
			}
		}

		return stStringBuffer.toString();
	}

	// Convert an int to a char under mod 12.
	public char intToChar(int s) {

		switch (s) {
		case 10:
			if (base12String == null)
				return 'A';
			else if (base12String.equals("NotUsed"))
				return 'A';
			else if (base12String.equals("ab"))
				return 'a';
			else if (base12String.equals("AB"))
				return 'A';
			else if (base12String.equals("te"))
				return 't';
			else if (base12String.equals("TE"))
				return 'T';
			else
				return 'A';
		case 11:
			if (base12String == null)
				return 'B';
			if (base12String.equals("NotUsed"))
				return 'B';
			else if (base12String.equals("ab"))
				return 'b';
			else if (base12String.equals("AB"))
				return 'B';
			else if (base12String.equals("te"))
				return 'e';
			else if (base12String.equals("TE"))
				return 'E';
			else
				return 'B';
		default:
			return (char) (s + '0');
		}
	}

	// Print pc-sets on the standard output.
	public void printPcSet() {

		for (int i = 0; i < set.length; i++) {
			switch (set[i]) {
			case 10:
				if (base12String == null)
					System.out.print("A");
				else if (base12String.equals("NotUsed"))
					System.out.print("A");
				else if (base12String.equals("ab"))
					System.out.print("a");
				else if (base12String.equals("AB"))
					System.out.print("A");
				else if (base12String.equals("te"))
					System.out.print("t");
				else if (base12String.equals("TE"))
					System.out.print("T");
				else
					System.out.print("A");
				break;
			case 11:
				if (base12String == null)
					System.out.print("B");
				else if (base12String.equals("NotUsed"))
					System.out.print("B");
				else if (base12String.equals("ab"))
					System.out.print("b");
				else if (base12String.equals("AB"))
					System.out.print("B");
				else if (base12String.equals("te"))
					System.out.print("e");
				else if (base12String.equals("TE"))
					System.out.print("E");
				else
					System.out.print("B");
				break;
			default:
				System.out.print(set[i]);
			}
		}
	}

	// Given tnI (TnI), returns "n" of corresponding In.
	// p0[] is a prime series and tnI, TnI.
	public int tnItoIn(int[] p0, int[] tnI) {
		return ((tnI[0] + 12) - p0[0]) % 12;
	}

	/*
	 * public int tnItoIn(int[] p0, int[] tnI) { int n; for(n = 0; n <
	 * p0.length; n++) if(p0[n] == tnI[0]) break;
	 * 
	 * return n; }
	 */

	// Constructs a 12x12 matrix of a twelve-tone series.
	public int[][] matrix(int[] series) {
		int[][] m12 = new int[series.length][series.length];
		for (int i = 0; i < m12.length; i++) {
			m12[i][0] = ((12 + (2 * series[0])) - series[i]) % 12;
			for (int ii = 1; ii < m12[i].length; ii++)
				m12[i][ii] = ((12 + m12[i][0]) - series[0] + series[ii]) % 12;
		}
		return m12;
	}
}
