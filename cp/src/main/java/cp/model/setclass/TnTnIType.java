// TnTnIType.java, used for
// JDubiel.java: computer program for pc-set analysis
// by Akira Takaoka, akira@music.columbia.edu
// Copyright (C) 1994-2010 Akira Takaoka
// All rights reserved.

package cp.model.setclass;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;

@Component
public class TnTnIType extends TnIType {

	public final Set[] prime2 = new Set[6];
	public final Set[] prime3 = new Set[12];
	public final Set[] prime4 = new Set[29];
	public final Set[] prime5 = new Set[38];
	public final Set[] prime6 = new Set[50];
	public final Set[] prime7 = new Set[38];
	public final Set[] prime8 = new Set[29];
	public final Set[] prime9 = new Set[12];

	// public Set prime = new Set();
	public Set prime;

    @PostConstruct
	public void init() {
        initPrime2();
        initPrime3();
        initPrime4();
        initPrime5();
        initPrime6();
        initPrime7();
        initPrime8();
        initPrime9();
    }

	// Constructors
	public TnTnIType() {
		this.set = new int[SCALE_STEPS];
		this.m7set = new int[SCALE_STEPS];
	}

	public TnTnIType(int[] s) {
		set = new int[s.length];
		for (int i = 0; i < s.length; i++)
			set[i] = s[i];
	}

	public TnTnIType(char[] t) {
		charSet = new char[t.length];
		for (int i = 0; i < t.length; i++)
			charSet[i] = t[i];
	}

	public TnTnIType(int size) {
		set = new int[size];
		m7set = new int[size];
		tntype = new int[size];
		tnitype = new int[size];
		tntnitype = new int[size];
	}

	public void getTnTnIType() {
		getTnIType();
		tntnitype = tnOrTnIType(tntype, tnitype);
	}

	public int[] returnTnTnIType(int[] st) {
		return tnOrTnIType(returnTnType(st), returnTnIType(st));
	}

	// Choose either tnType or tniType for tnTnIType.
	public int[] tnOrTnIType(int[] tn, int[] tni) {
		int[] pcs = new int[tni.length];
		int n, ni, i = 0;

		do {
			n = (tn[i + 1] - tn[i]);
			ni = (tni[i + 1] - tni[i]);
			i++;
		} while (n == ni && i != tn.length - 1);

		if (n < ni)
			for (i = 0; i < tn.length; i++)
				pcs[i] = tn[i];
		else if (n > ni)
			for (i = 0; i < tni.length; i++)
				pcs[i] = tni[i];
		else
			for (i = 0; i < tn.length; i++)
				pcs[i] = tn[i];

		return pcs;
	}

	// Find the Tn/TnI type and interval vector through a Forte name.
	public Set getPrimeByName(String name) {

		int j = 0;
		char c = name.charAt(0);

		switch (c) {
		case '2':
			initPrime2();
		case '3':
			initPrime3();

			do {
				if (name.equalsIgnoreCase(prime3[j].name))
					break;
				else
					j++;
			} while (j < 12);

			if (j == 12) {
				JOptionPane.showMessageDialog(null, "The name is mistyped.",
						"Warning", JOptionPane.WARNING_MESSAGE);
				break;
			} else {
				prime = prime3[j];
				break;
			}
		case '4':
			initPrime4();

			do {
				if (name.equalsIgnoreCase(prime4[j].name))
					break;
				else
					j++;
			} while (j < 29);

			if (j == 29) {
				JOptionPane.showMessageDialog(null, "The name is mistyped.",
						"Warning", JOptionPane.WARNING_MESSAGE);
				break;
			} else {
				prime = prime4[j];
				break;
			}
		case '5':
			initPrime5();

			do {
				if (name.equalsIgnoreCase(prime5[j].name))
					break;
				else
					j++;
			} while (j < 38);

			if (j == 38) {
				JOptionPane.showMessageDialog(null, "The name is mistyped.",
						"Warning", JOptionPane.WARNING_MESSAGE);
				break;
			} else {
				prime = prime5[j];
				break;
			}
		case '6':
			initPrime6();

			do {
				if (name.equalsIgnoreCase(prime6[j].name))
					break;
				else
					j++;
			} while (j < 50);

			if (j == 50) {
				JOptionPane.showMessageDialog(null, "The name is mistyped.",
						"Warning", JOptionPane.WARNING_MESSAGE);
				break;
			} else {
				prime = prime6[j];
				break;
			}
		case '7':
			initPrime7();

			do {
				if (name.equalsIgnoreCase(prime7[j].name))
					break;
				else
					j++;
			} while (j < 38);

			if (j == 38) {
				JOptionPane.showMessageDialog(null, "The name is mistyped.",
						"Warning", JOptionPane.WARNING_MESSAGE);
				break;
			} else {
				prime = prime7[j];
				break;
			}
		case '8':
			initPrime8();

			do {
				if (name.equalsIgnoreCase(prime8[j].name))
					break;
				else
					j++;
			} while (j < 29);

			if (j == 29) {
				JOptionPane.showMessageDialog(null, "The name is mistyped.",
						"Warning", JOptionPane.WARNING_MESSAGE);
				break;
			} else {
				prime = prime8[j];
				break;
			}
		case '9':
			initPrime9();

			do {
				if (name.equalsIgnoreCase(prime9[j].name))
					break;
				else
					j++;
			} while (j < 12);

			if (j == 12) {
				JOptionPane.showMessageDialog(null, "The name is mistyped.",
						"Warning", JOptionPane.WARNING_MESSAGE);
				break;
			} else {
				prime = prime9[j];
				break;
			}
		default:
			JOptionPane.showMessageDialog(null, "The name is mistyped.",
					"Warning", JOptionPane.WARNING_MESSAGE);
			break;
		}
		return prime;
	}

	// Find the Forte name and the Tn/TnI type through an interval vector.
	public Set getPrimeByIVector(int[] ivector) {

		int c = 0, i, j = 0;

		for (i = 0; i < 6; i++)
			c += ivector[i];

		i = 0;

		switch (c) {
		case 2:
			initPrime2();
		case 3:
			initPrime3();
			do {
				if (ivector[i] == prime3[j].ivector[i])
					i++;
				else {
					j++;
					i = 0;
				}
			} while (i < 6 && j < 12);
			if (j == 12) {
				JOptionPane.showMessageDialog(null,
						"There doesn't exist such an " + "interval vector.",
						"Warning", JOptionPane.WARNING_MESSAGE);
				break;
			} else {
				prime = prime3[j];
				break;
			}
		case 6:
			initPrime4();
			do {
				if (ivector[i] == prime4[j].ivector[i])
					i++;
				else {
					j++;
					i = 0;
				}
			} while (i < 6 && j < 29);
			if (j == 29) {
				JOptionPane.showMessageDialog(null,
						"There doesn't exist such an " + "interval vector.",
						"Warning", JOptionPane.WARNING_MESSAGE);
				break;
			} else {
				prime = prime4[j];
				break;
			}
		case 10:
			initPrime5();
			do {
				if (ivector[i] == prime5[j].ivector[i])
					i++;
				else {
					j++;
					i = 0;
				}
			} while (i < 6 && j < 38);
			if (j == 38) {
				JOptionPane.showMessageDialog(null,
						"There doesn't exist such an " + "interval vector.",
						"Warning", JOptionPane.WARNING_MESSAGE);
				break;
			} else {
				prime = prime5[j];
				break;
			}
		case 15:
			initPrime6();
			do {
				if (ivector[i] == prime6[j].ivector[i])
					i++;
				else {
					j++;
					i = 0;
				}
			} while (i < 6 && j < 50);
			if (j == 50) {
				JOptionPane.showMessageDialog(null,
						"There doesn't exist such an " + "interval vector.",
						"Warning", JOptionPane.WARNING_MESSAGE);
				break;
			} else {
				prime = prime6[j];
				break;
			}
		case 21:
			initPrime7();
			do {
				if (ivector[i] == prime7[j].ivector[i])
					i++;
				else {
					j++;
					i = 0;
				}
			} while (i < 6 && j < 38);
			if (j == 38) {
				JOptionPane.showMessageDialog(null,
						"There doesn't exist such an " + "interval vector.",
						"Warning", JOptionPane.WARNING_MESSAGE);
				break;
			} else {
				prime = prime7[j];
				break;
			}
		case 28:
			initPrime8();
			do {
				if (ivector[i] == prime8[j].ivector[i])
					i++;
				else {
					j++;
					i = 0;
				}
			} while (i < 6 && j < 29);
			if (j == 29) {
				JOptionPane.showMessageDialog(null,
						"There doesn't exist such an " + "interval vector.",
						"Warning", JOptionPane.WARNING_MESSAGE);
				break;
			} else {
				prime = prime8[j];
				break;
			}
		case 36:
			initPrime9();
			do {
				if (ivector[i] == prime9[j].ivector[i])
					i++;
				else {
					j++;
					i = 0;
				}
			} while (i < 6 && j < 12);
			if (j == 12) {
				JOptionPane.showMessageDialog(null,
						"There doesn't exist such an " + "interval vector.",
						"Warning", JOptionPane.WARNING_MESSAGE);
				break;
			} else {
				prime = prime9[j];
				break;
			}
		default:
			JOptionPane.showMessageDialog(null, "There doesn't exist such an "
					+ "interval vector.", "Warning",
					JOptionPane.WARNING_MESSAGE);
			break;
		}
		return prime;
	}

	// Find the Forte name and interval vector of a pc-set through TnTnI.
	public Set getPrimeByTnTnI() {

		int i = 1, j = 0;

		switch (tntnitype.length) {
		case 2:
			initPrime2();
			do {
				if (tntnitype[i] == prime2[j].tntnitype[i])
					i++;
				else {
					j++;
					i = 1;
				}
			} while (i < 2 && j < 6);
			if (j == 6)
				break;
			else {
				prime = prime2[j];
				break;
			}
		case 3:
			initPrime3();
			do {
				if (tntnitype[i] == prime3[j].tntnitype[i])
					i++;
				else {
					j++;
					i = 1;
				}
			} while (i < 3 && j < 12);
			if (j == 12)
				break;
			else {
				prime = prime3[j];
				break;
			}
		case 4:
			initPrime4();
			do {
				if (tntnitype[i] == prime4[j].tntnitype[i])
					i++;
				else {
					j++;
					i = 1;
				}
			} while (i < 4 && j < 29);
			if (j == 29)
				break;
			else {
				prime = prime4[j];
				break;
			}
		case 5:
			initPrime5();
			do {
				if (tntnitype[i] == prime5[j].tntnitype[i])
					i++;
				else {
					j++;
					i = 1;
				}
			} while (i < 5 && j < 38);
			if (j == 38)
				break;
			else {
				prime = prime5[j];
				break;
			}
		case 6:
			initPrime6();
			do {
				if (tntnitype[i] == prime6[j].tntnitype[i])
					i++;
				else {
					j++;
					i = 1;
				}
			} while (i < 6 && j < 50);
			if (j == 50)
				break;
			else {
				prime = prime6[j];
				break;
			}
		case 7:
			initPrime7();
			do {
				if (tntnitype[i] == prime7[j].tntnitype[i])
					i++;
				else {
					j++;
					i = 1;
				}
			} while (i < 7 && j < 38);
			if (j == 38)
				break;
			else {
				prime = prime7[j];
				break;
			}
		case 8:
			initPrime8();
			do {
				if (tntnitype[i] == prime8[j].tntnitype[i])
					i++;
				else {
					j++;
					i = 1;
				}
			} while (i < 8 && j < 29);
			if (j == 29)
				break;
			else {
				prime = prime8[j];
				break;
			}
		case 9:
			initPrime9();
			do {
				if (tntnitype[i] == prime9[j].tntnitype[i])
					i++;
				else {
					j++;
					i = 1;
				}
			} while (i < 9 && j < 12);
			if (j == 12)
				break;
			else {
				prime = prime9[j];
				break;
			}
		default:
			break;
		}
		return prime;
	}

	// Return the Forte name and interval vector of a pc-set through TnTnI.
	public Set returnPrimeByTnTnI(int[] st) {

		int i = 0, j = 0;

		switch (st.length) {
		case 2:
			initPrime2();
		case 3:
			initPrime3();
			do {
				if (st[i] == prime3[j].tntnitype[i])
					i++;
				else {
					j++;
					i = 1;
				}
			} while (i < 3 && j < 12);
			if (j == 12)
				break;
			else {
				prime = prime3[j];
				break;
			}
		case 4:
			initPrime4();
			do {
				if (st[i] == prime4[j].tntnitype[i])
					i++;
				else {
					j++;
					i = 1;
				}
			} while (i < 4 && j < 29);
			if (j == 29)
				break;
			else {
				prime = prime4[j];
				break;
			}
		case 5:
			initPrime5();
			do {
				if (st[i] == prime5[j].tntnitype[i])
					i++;
				else {
					j++;
					i = 1;
				}
			} while (i < 5 && j < 38);
			if (j == 38)
				break;
			else {
				prime = prime5[j];
				break;
			}
		case 6:
			initPrime6();
			do {
				if (st[i] == prime6[j].tntnitype[i])
					i++;
				else {
					j++;
					i = 1;
				}
			} while (i < 6 && j < 50);
			if (j == 50)
				break;
			else {
				prime = prime6[j];
				break;
			}
		case 7:
			initPrime7();
			do {
				if (st[i] == prime7[j].tntnitype[i])
					i++;
				else {
					j++;
					i = 1;
				}
			} while (i < 7 && j < 38);
			if (j == 38)
				break;
			else {
				prime = prime7[j];
				break;
			}
		case 8:
			initPrime8();
			do {
				if (st[i] == prime8[j].tntnitype[i])
					i++;
				else {
					j++;
					i = 1;
				}
			} while (i < 8 && j < 29);
			if (j == 29)
				break;
			else {
				prime = prime8[j];
				break;
			}
		case 9:
			initPrime9();
			do {
				if (st[i] == prime9[j].tntnitype[i])
					i++;
				else {
					j++;
					i = 1;
				}
			} while (i < 9 && j < 12);
			if (j == 12)
				break;
			else {
				prime = prime9[j];
				break;
			}
		default:
			break;
		}
		return prime;
	}

	// List the Forte name, prime form, and interval vector of a pc-set.
	public String[][] listPrime(int cardinality) {

		switch (cardinality) {
		case 2:
			initPrime2();
		case 3:
			initPrime3();
			String[][] lists3 = new String[12][];

			for (int i = 0; i < lists3.length; i++) {
				lists3[i] = new String[5];
				lists3[i][0] = "       ".concat(prime3[i].name);
				lists3[i][1] = "      ".concat("[")
						.concat(prime3[i].tntnitypeToString()).concat("]");
				lists3[i][2] = "  ".concat("<")
						.concat(prime3[i].ivectorToString()).concat(">");
				lists3[i][3] = "       ".concat("[")
						.concat(prime3[i].m7setToString()).concat("]");
				lists3[i][4] = "     ".concat(" ")
						.concat(prime3[i].symmetryToString()).concat(" ");
			}
			return lists3;
		case 4:
			initPrime4();
			String[][] lists4 = new String[29][];

			for (int i = 0; i < lists4.length; i++) {
				lists4[i] = new String[5];
				lists4[i][0] = "      ".concat(prime4[i].name);
				lists4[i][1] = "     ".concat("[")
						.concat(prime4[i].tntnitypeToString()).concat("]");
				lists4[i][2] = "  ".concat("<")
						.concat(prime4[i].ivectorToString()).concat(">");
				lists4[i][3] = "     ".concat("[")
						.concat(prime4[i].m7setToString()).concat("]");
				lists4[i][4] = "     ".concat(" ")
						.concat(prime4[i].symmetryToString()).concat(" ");
			}
			return lists4;
		case 5:
			initPrime5();
			String[][] lists5 = new String[38][];

			for (int i = 0; i < lists5.length; i++) {
				lists5[i] = new String[5];
				lists5[i][0] = "      ".concat(prime5[i].name);
				lists5[i][1] = "     ".concat("[")
						.concat(prime5[i].tntnitypeToString()).concat("]");
				lists5[i][2] = "  ".concat("<")
						.concat(prime5[i].ivectorToString()).concat(">");
				lists5[i][3] = "    ".concat("[")
						.concat(prime5[i].m7setToString()).concat("]");
				lists5[i][4] = "     ".concat(" ")
						.concat(prime5[i].symmetryToString()).concat(" ");
			}
			return lists5;
		case 6:
			initPrime6();
			String[][] lists6 = new String[50][];

			for (int i = 0; i < lists6.length; i++) {
				lists6[i] = new String[6];
				lists6[i][0] = "       ".concat(prime6[i].name);
				lists6[i][1] = "    ".concat("[")
						.concat(prime6[i].tntnitypeToString()).concat("]");
				lists6[i][2] = "  ".concat("<")
						.concat(prime6[i].ivectorToString()).concat(">");
				lists6[i][3] = "    ".concat("[")
						.concat(prime6[i].m7setToString()).concat("]");
				lists6[i][4] = "     ".concat(" ")
						.concat(prime6[i].symmetryToString()).concat(" ");
				lists6[i][5] = "   ".concat(" ")
						.concat(prime6[i].combinatorialToString()).concat(" ");
			}
			return lists6;
		case 7:
			initPrime7();
			String[][] lists7 = new String[38][];

			for (int i = 0; i < lists7.length; i++) {
				lists7[i] = new String[4];
				lists7[i][0] = "       ".concat(prime7[i].name);
				lists7[i][1] = "   ".concat("[")
						.concat(prime7[i].tntnitypeToString()).concat("]");
				lists7[i][2] = "   ".concat("<")
						.concat(prime7[i].ivectorToString()).concat(">");
				lists7[i][3] = "      ".concat(" ")
						.concat(prime7[i].symmetryToString()).concat(" ");
			}
			return lists7;
		case 8:
			initPrime8();
			String[][] lists8 = new String[29][];

			for (int i = 0; i < lists8.length; i++) {
				lists8[i] = new String[4];
				lists8[i][0] = "       ".concat(prime8[i].name);
				lists8[i][1] = "   ".concat("[")
						.concat(prime8[i].tntnitypeToString()).concat("]");
				lists8[i][2] = "   ".concat("<")
						.concat(prime8[i].ivectorToString()).concat(">");
				lists8[i][3] = "      ".concat(" ")
						.concat(prime8[i].symmetryToString()).concat(" ");
			}
			return lists8;
		case 9:
			initPrime9();
			String[][] lists9 = new String[12][];

			for (int i = 0; i < lists9.length; i++) {
				lists9[i] = new String[4];
				lists9[i][0] = "         ".concat(prime9[i].name);
				lists9[i][1] = "   ".concat("[")
						.concat(prime9[i].tntnitypeToString()).concat("]");
				lists9[i][2] = "    ".concat("<")
						.concat(prime9[i].ivectorToString()).concat(">");
				lists9[i][3] = "       ".concat(" ")
						.concat(prime9[i].symmetryToString()).concat(" ");
			}
			return lists9;
		default:
            return new String[1][1];
		}
	}

	// Initialize Set objects with
	// Forte names, Tn/TnI type, i-vectors, and M7.
	public void initPrime2() {

		for (int i = 0; i < 6; i++)
			prime2[i] = new Set(2);

		prime2[0].name = "2-1";
		prime2[1].name = "2-2";
		prime2[2].name = "2-3";
		prime2[3].name = "2-4";
		prime2[4].name = "2-5";
		prime2[5].name = "2-6";

		prime2[0].description = "2-1";
		prime2[1].description = "2-2";
		prime2[2].description = "2-3";
		prime2[3].description = "2-4";
		prime2[4].description = "2-5";
		prime2[5].description = "2-6";

		prime2[0].tntnitype = new int[] { 0, 1 };
		prime2[1].tntnitype = new int[] { 0, 2 };
		prime2[2].tntnitype = new int[] { 0, 3 };
		prime2[3].tntnitype = new int[] { 0, 4 };
		prime2[4].tntnitype = new int[] { 0, 5 };
		prime2[5].tntnitype = new int[] { 0, 6 };

		prime2[0].ivector = new int[] { 1, 0, 0, 0, 0, 0 };
		prime2[1].ivector = new int[] { 0, 1, 0, 0, 0, 0 };
		prime2[2].ivector = new int[] { 0, 0, 1, 0, 0, 0 };
		prime2[3].ivector = new int[] { 0, 0, 0, 1, 0, 0 };
		prime2[4].ivector = new int[] { 0, 0, 0, 0, 1, 0 };
		prime2[5].ivector = new int[] { 0, 0, 0, 0, 0, 1 };

		prime2[0].m7set = new int[] {};
		prime2[1].m7set = new int[] {};
		prime2[2].m7set = new int[] {};
		prime2[3].m7set = new int[] {};
		prime2[4].m7set = new int[] {};
		prime2[5].m7set = new int[] {};

		prime2[0].symmetry = new int[] {};
		prime2[1].symmetry = new int[] {};
		prime2[2].symmetry = new int[] {};
		prime2[3].symmetry = new int[] {};
		prime2[4].symmetry = new int[] {};
		prime2[5].symmetry = new int[] {};
	}

	
	public void initPrime3() {

		for (int i = 0; i < 12; i++)
			prime3[i] = new Set(3);

		prime3[0].name = "3-1";
		prime3[1].name = "3-2";
		prime3[2].name = "3-3";
		prime3[3].name = "3-4";
		prime3[4].name = "3-5";
		prime3[5].name = "3-6";
		prime3[6].name = "3-7";
		prime3[7].name = "3-8";
		prime3[8].name = "3-9";
		prime3[9].name = "3-10";
		prime3[10].name = "3-11";
		prime3[11].name = "3-12";

		prime3[0].description = "3-1";
		prime3[1].description = "3-2";
		prime3[2].description = "3-3 minmaj7";
		prime3[3].description = "3-4 maj7";
		prime3[4].description = "3-5";
		prime3[5].description = "3-6 add9";
		prime3[6].description = "3-7 min7";
		prime3[7].description = "3-8 dom/lyd";
		prime3[8].description = "3-9 sus9/11";
		prime3[9].description = "3-10 dim";
		prime3[10].description = "3-11 major/minor";
		prime3[11].description = "3-12 augm";

		prime3[0].tntnitype = new int[] { 0, 1, 2 };
		prime3[1].tntnitype = new int[] { 0, 1, 3 };
		prime3[2].tntnitype = new int[] { 0, 1, 4 };
		prime3[3].tntnitype = new int[] { 0, 1, 5 };
		prime3[4].tntnitype = new int[] { 0, 1, 6 };
		prime3[5].tntnitype = new int[] { 0, 2, 4 };
		prime3[6].tntnitype = new int[] { 0, 2, 5 };
		prime3[7].tntnitype = new int[] { 0, 2, 6 };
		prime3[8].tntnitype = new int[] { 0, 2, 7 };
		prime3[9].tntnitype = new int[] { 0, 3, 6 };
		prime3[10].tntnitype = new int[] { 0, 3, 7 };
		prime3[11].tntnitype = new int[] { 0, 4, 8 };

		prime3[0].ivector = new int[] { 2, 1, 0, 0, 0, 0 };
		prime3[1].ivector = new int[] { 1, 1, 1, 0, 0, 0 };
		prime3[2].ivector = new int[] { 1, 0, 1, 1, 0, 0 };
		prime3[3].ivector = new int[] { 1, 0, 0, 1, 1, 0 };
		prime3[4].ivector = new int[] { 1, 0, 0, 0, 1, 1 };
		prime3[5].ivector = new int[] { 0, 2, 0, 1, 0, 0 };
		prime3[6].ivector = new int[] { 0, 1, 1, 0, 1, 0 };
		prime3[7].ivector = new int[] { 0, 1, 0, 1, 0, 1 };
		prime3[8].ivector = new int[] { 0, 1, 0, 0, 2, 0 };
		prime3[9].ivector = new int[] { 0, 0, 2, 0, 0, 1 };
		prime3[10].ivector = new int[] { 0, 0, 1, 1, 1, 0 };
		prime3[11].ivector = new int[] { 0, 0, 0, 3, 0, 0 };

		prime3[0].m7set = new int[] { 0, 2, 7 };
		prime3[1].m7set = new int[] { 0, 2, 5 };
		prime3[2].m7set = new int[] { 0, 3, 7 };
		prime3[3].m7set = new int[] { 0, 1, 5 };
		prime3[4].m7set = new int[] { 0, 1, 6 };
		prime3[5].m7set = new int[] { 0, 2, 4 };
		prime3[6].m7set = new int[] { 0, 1, 3 };
		prime3[7].m7set = new int[] { 0, 2, 6 };
		prime3[8].m7set = new int[] { 0, 1, 2 };
		prime3[9].m7set = new int[] { 0, 3, 6 };
		prime3[10].m7set = new int[] { 0, 1, 4 };
		prime3[11].m7set = new int[] { 0, 4, 8 };

		prime3[0].symmetry = new int[] { 1, 1 };
		prime3[1].symmetry = new int[] { 1, 0 };
		prime3[2].symmetry = new int[] { 1, 0 };
		prime3[3].symmetry = new int[] { 1, 0 };
		prime3[4].symmetry = new int[] { 1, 0 };
		prime3[5].symmetry = new int[] { 1, 1 };
		prime3[6].symmetry = new int[] { 1, 0 };
		prime3[7].symmetry = new int[] { 1, 0 };
		prime3[8].symmetry = new int[] { 1, 1 };
		prime3[9].symmetry = new int[] { 1, 1 };
		prime3[10].symmetry = new int[] { 1, 0 };
		prime3[11].symmetry = new int[] { 3, 3 };
	}

	public void initPrime4() {

		for (int i = 0; i < 29; i++)
			prime4[i] = new Set(4);

		prime4[0].name = "4-1";
		prime4[1].name = "4-2";
		prime4[2].name = "4-3";
		prime4[3].name = "4-4";
		prime4[4].name = "4-5";
		prime4[5].name = "4-6";
		prime4[6].name = "4-7";
		prime4[7].name = "4-8";
		prime4[8].name = "4-9";
		prime4[9].name = "4-10";
		prime4[10].name = "4-11";
		prime4[11].name = "4-12";
		prime4[12].name = "4-13";
		prime4[13].name = "4-14";
		prime4[14].name = "4-Z15";
		prime4[15].name = "4-16";
		prime4[16].name = "4-17";
		prime4[17].name = "4-18";
		prime4[18].name = "4-19";
		prime4[19].name = "4-20";
		prime4[20].name = "4-21";
		prime4[21].name = "4-22";
		prime4[22].name = "4-23";
		prime4[23].name = "4-24";
		prime4[24].name = "4-25";
		prime4[25].name = "4-26";
		prime4[26].name = "4-27";
		prime4[27].name = "4-28";
		prime4[28].name = "4-Z29";

		prime4[0].description = "4-1";
		prime4[1].description = "4-2";
		prime4[2].description = "4-3";
		prime4[3].description = "4-4";
		prime4[4].description = "4-5";
		prime4[5].description = "4-6";
		prime4[6].description = "4-7";
		prime4[7].description = "4-8";
		prime4[8].description = "4-9";
		prime4[9].description = "4-10";
		prime4[10].description = "4-11";
		prime4[11].description = "4-12, dom7b9";
		prime4[12].description = "4-13";
		prime4[13].description = "4-14";
		prime4[14].description = "4-Z15";
		prime4[15].description = "4-16";
		prime4[16].description = "4-17, min-maj";
		prime4[17].description = "4-18";
		prime4[18].description = "4-19, min-maj7";
		prime4[19].description = "4-20, maj7";
		prime4[20].description = "4-21, whole tone";
		prime4[21].description = "4-22, add9/m add11";
		prime4[22].description = "4-23, sus9";
		prime4[23].description = "4-24, aug";
		prime4[24].description = "4-25, dom7b5";
		prime4[25].description = "4-26, minor7";
		prime4[26].description = "4-27, half dim7/dom7";
		prime4[27].description = "4-28, dim";
		prime4[28].description = "4-Z29";

		prime4[0].tntnitype = new int[] { 0, 1, 2, 3 };
		prime4[1].tntnitype = new int[] { 0, 1, 2, 4 };
		prime4[2].tntnitype = new int[] { 0, 1, 3, 4 };
		prime4[3].tntnitype = new int[] { 0, 1, 2, 5 };
		prime4[4].tntnitype = new int[] { 0, 1, 2, 6 };
		prime4[5].tntnitype = new int[] { 0, 1, 2, 7 };
		prime4[6].tntnitype = new int[] { 0, 1, 4, 5 };
		prime4[7].tntnitype = new int[] { 0, 1, 5, 6 };
		prime4[8].tntnitype = new int[] { 0, 1, 6, 7 };
		prime4[9].tntnitype = new int[] { 0, 2, 3, 5 };
		prime4[10].tntnitype = new int[] { 0, 1, 3, 5 };
		prime4[11].tntnitype = new int[] { 0, 2, 3, 6 };
		prime4[12].tntnitype = new int[] { 0, 1, 3, 6 };
		prime4[13].tntnitype = new int[] { 0, 2, 3, 7 };
		prime4[14].tntnitype = new int[] { 0, 1, 4, 6 };
		prime4[15].tntnitype = new int[] { 0, 1, 5, 7 };
		prime4[16].tntnitype = new int[] { 0, 3, 4, 7 };
		prime4[17].tntnitype = new int[] { 0, 1, 4, 7 };
		prime4[18].tntnitype = new int[] { 0, 1, 4, 8 };
		prime4[19].tntnitype = new int[] { 0, 1, 5, 8 };
		prime4[20].tntnitype = new int[] { 0, 2, 4, 6 };
		prime4[21].tntnitype = new int[] { 0, 2, 4, 7 };
		prime4[22].tntnitype = new int[] { 0, 2, 5, 7 };
		prime4[23].tntnitype = new int[] { 0, 2, 4, 8 };
		prime4[24].tntnitype = new int[] { 0, 2, 6, 8 };
		prime4[25].tntnitype = new int[] { 0, 3, 5, 8 };
		prime4[26].tntnitype = new int[] { 0, 2, 5, 8 };
		prime4[27].tntnitype = new int[] { 0, 3, 6, 9 };
		prime4[28].tntnitype = new int[] { 0, 1, 3, 7 };

		prime4[0].ivector = new int[] { 3, 2, 1, 0, 0, 0 };
		prime4[1].ivector = new int[] { 2, 2, 1, 1, 0, 0 };
		prime4[2].ivector = new int[] { 2, 1, 2, 1, 0, 0 };
		prime4[3].ivector = new int[] { 2, 1, 1, 1, 1, 0 };
		prime4[4].ivector = new int[] { 2, 1, 0, 1, 1, 1 };
		prime4[5].ivector = new int[] { 2, 1, 0, 0, 2, 1 };
		prime4[6].ivector = new int[] { 2, 0, 1, 2, 1, 0 };
		prime4[7].ivector = new int[] { 2, 0, 0, 1, 2, 1 };
		prime4[8].ivector = new int[] { 2, 0, 0, 0, 2, 2 };
		prime4[9].ivector = new int[] { 1, 2, 2, 0, 1, 0 };
		prime4[10].ivector = new int[] { 1, 2, 1, 1, 1, 0 };
		prime4[11].ivector = new int[] { 1, 1, 2, 1, 0, 1 };
		prime4[12].ivector = new int[] { 1, 1, 2, 0, 1, 1 };
		prime4[13].ivector = new int[] { 1, 1, 1, 1, 2, 0 };
		prime4[14].ivector = new int[] { 1, 1, 1, 1, 1, 1 };
		prime4[15].ivector = new int[] { 1, 1, 0, 1, 2, 1 };
		prime4[16].ivector = new int[] { 1, 0, 2, 2, 1, 0 };
		prime4[17].ivector = new int[] { 1, 0, 2, 1, 1, 1 };
		prime4[18].ivector = new int[] { 1, 0, 1, 3, 1, 0 };
		prime4[19].ivector = new int[] { 1, 0, 1, 2, 2, 0 };
		prime4[20].ivector = new int[] { 0, 3, 0, 2, 0, 1 };
		prime4[21].ivector = new int[] { 0, 2, 1, 1, 2, 0 };
		prime4[22].ivector = new int[] { 0, 2, 1, 0, 3, 0 };
		prime4[23].ivector = new int[] { 0, 2, 0, 3, 0, 1 };
		prime4[24].ivector = new int[] { 0, 2, 0, 2, 0, 2 };
		prime4[25].ivector = new int[] { 0, 1, 2, 1, 2, 0 };
		prime4[26].ivector = new int[] { 0, 1, 2, 1, 1, 1 };
		prime4[27].ivector = new int[] { 0, 0, 4, 0, 0, 2 };
		prime4[28].ivector = new int[] { 1, 1, 1, 1, 1, 1 };

		prime4[0].m7set = new int[] { 0, 2, 5, 7 };
		prime4[1].m7set = new int[] { 0, 2, 4, 7 };
		prime4[2].m7set = new int[] { 0, 3, 5, 8 };
		prime4[3].m7set = new int[] { 0, 2, 3, 7 };
		prime4[4].m7set = new int[] { 0, 1, 5, 7 };
		prime4[5].m7set = new int[] { 0, 1, 2, 7 };
		prime4[6].m7set = new int[] { 0, 1, 5, 8 };
		prime4[7].m7set = new int[] { 0, 1, 5, 6 };
		prime4[8].m7set = new int[] { 0, 1, 6, 7 };
		prime4[9].m7set = new int[] { 0, 2, 3, 5 };
		prime4[10].m7set = new int[] { 0, 1, 3, 5 };
		prime4[11].m7set = new int[] { 0, 2, 5, 8 };
		prime4[12].m7set = new int[] { 0, 1, 3, 6 };
		prime4[13].m7set = new int[] { 0, 1, 2, 5 };
		prime4[14].m7set = new int[] { 0, 1, 3, 7 };
		prime4[15].m7set = new int[] { 0, 1, 2, 6 };
		prime4[16].m7set = new int[] { 0, 3, 4, 7 };
		prime4[17].m7set = new int[] { 0, 1, 4, 7 };
		prime4[18].m7set = new int[] { 0, 1, 4, 8 };
		prime4[19].m7set = new int[] { 0, 1, 4, 5 };
		prime4[20].m7set = new int[] { 0, 2, 4, 6 };
		prime4[21].m7set = new int[] { 0, 1, 2, 4 };
		prime4[22].m7set = new int[] { 0, 1, 2, 3 };
		prime4[23].m7set = new int[] { 0, 2, 4, 8 };
		prime4[24].m7set = new int[] { 0, 2, 6, 8 };
		prime4[25].m7set = new int[] { 0, 1, 3, 4 };
		prime4[26].m7set = new int[] { 0, 2, 3, 6 };
		prime4[27].m7set = new int[] { 0, 3, 6, 9 };
		prime4[28].m7set = new int[] { 0, 1, 4, 6 };

		prime4[0].symmetry = new int[] { 1, 1 };
		prime4[1].symmetry = new int[] { 1, 0 };
		prime4[2].symmetry = new int[] { 1, 1 };
		prime4[3].symmetry = new int[] { 1, 0 };
		prime4[4].symmetry = new int[] { 1, 0 };
		prime4[5].symmetry = new int[] { 1, 1 };
		prime4[6].symmetry = new int[] { 1, 1 };
		prime4[7].symmetry = new int[] { 1, 1 };
		prime4[8].symmetry = new int[] { 2, 2 };
		prime4[9].symmetry = new int[] { 1, 1 };
		prime4[10].symmetry = new int[] { 1, 0 };
		prime4[11].symmetry = new int[] { 1, 0 };
		prime4[12].symmetry = new int[] { 1, 0 };
		prime4[13].symmetry = new int[] { 1, 0 };
		prime4[14].symmetry = new int[] { 1, 0 };
		prime4[15].symmetry = new int[] { 1, 0 };
		prime4[16].symmetry = new int[] { 1, 1 };
		prime4[17].symmetry = new int[] { 1, 0 };
		prime4[18].symmetry = new int[] { 1, 0 };
		prime4[19].symmetry = new int[] { 1, 1 };
		prime4[20].symmetry = new int[] { 1, 1 };
		prime4[21].symmetry = new int[] { 1, 0 };
		prime4[22].symmetry = new int[] { 1, 1 };
		prime4[23].symmetry = new int[] { 1, 1 };
		prime4[24].symmetry = new int[] { 2, 2 };
		prime4[25].symmetry = new int[] { 1, 1 };
		prime4[26].symmetry = new int[] { 1, 0 };
		prime4[27].symmetry = new int[] { 4, 4 };
		prime4[28].symmetry = new int[] { 1, 0 };
	}

	public void initPrime5() {

		for (int i = 0; i < 38; i++)
			prime5[i] = new Set(5);

		prime5[0].name = "5-1";
		prime5[1].name = "5-2";
		prime5[2].name = "5-3";
		prime5[3].name = "5-4";
		prime5[4].name = "5-5";
		prime5[5].name = "5-6";
		prime5[6].name = "5-7";
		prime5[7].name = "5-8";
		prime5[8].name = "5-9";
		prime5[9].name = "5-10";
		prime5[10].name = "5-11";
		prime5[11].name = "5-Z12";
		prime5[12].name = "5-13";
		prime5[13].name = "5-14";
		prime5[14].name = "5-15";
		prime5[15].name = "5-16";
		prime5[16].name = "5-Z17";
		prime5[17].name = "5-Z18";
		prime5[18].name = "5-19";
		prime5[19].name = "5-20";
		prime5[20].name = "5-21";
		prime5[21].name = "5-22";
		prime5[22].name = "5-23";
		prime5[23].name = "5-24";
		prime5[24].name = "5-25";
		prime5[25].name = "5-26";
		prime5[26].name = "5-27";
		prime5[27].name = "5-28";
		prime5[28].name = "5-29";
		prime5[29].name = "5-30";
		prime5[30].name = "5-31";
		prime5[31].name = "5-32";
		prime5[32].name = "5-33";
		prime5[33].name = "5-34";
		prime5[34].name = "5-35";
		prime5[35].name = "5-Z36";
		prime5[36].name = "5-Z37";
		prime5[37].name = "5-Z38";

		prime5[0].description = "5-1";
		prime5[1].description = "5-2";
		prime5[2].description = "5-3";
		prime5[3].description = "5-4";
		prime5[4].description = "5-5";
		prime5[5].description = "5-6";
		prime5[6].description = "5-7";
		prime5[7].description = "5-8";
		prime5[8].description = "5-9";
		prime5[9].description = "5-10";
		prime5[10].description = "5-11";
		prime5[11].description = "5-Z12";
		prime5[12].description = "5-13";
		prime5[13].description = "5-14";
		prime5[14].description = "5-15";
		prime5[15].description = "5-16";
		prime5[16].description = "5-Z17";
		prime5[17].description = "5-Z18";
		prime5[18].description = "5-19";
		prime5[19].description = "5-20";
		prime5[20].description = "5-21";
		prime5[21].description = "5-22";
		prime5[22].description = "5-23";
		prime5[23].description = "5-24";
		prime5[24].description = "5-25";
		prime5[25].description = "5-26";
		prime5[26].description = "5-27";
		prime5[27].description = "5-28";
		prime5[28].description = "5-29";
		prime5[29].description = "5-30";
		prime5[30].description = "5-31";
		prime5[31].description = "5-32";
		prime5[32].description = "5-33";
		prime5[33].description = "5-34";
		prime5[34].description = "5-35";
		prime5[35].description = "5-Z36";
		prime5[36].description = "5-Z37";
		prime5[37].description = "5-Z38";

		prime5[0].tntnitype = new int[] { 0, 1, 2, 3, 4 };
		prime5[1].tntnitype = new int[] { 0, 1, 2, 3, 5 };
		prime5[2].tntnitype = new int[] { 0, 1, 2, 4, 5 };
		prime5[3].tntnitype = new int[] { 0, 1, 2, 3, 6 };
		prime5[4].tntnitype = new int[] { 0, 1, 2, 3, 7 };
		prime5[5].tntnitype = new int[] { 0, 1, 2, 5, 6 };
		prime5[6].tntnitype = new int[] { 0, 1, 2, 6, 7 };
		prime5[7].tntnitype = new int[] { 0, 2, 3, 4, 6 };
		prime5[8].tntnitype = new int[] { 0, 1, 2, 4, 6 };
		prime5[9].tntnitype = new int[] { 0, 1, 3, 4, 6 };
		prime5[10].tntnitype = new int[] { 0, 2, 3, 4, 7 };
		prime5[11].tntnitype = new int[] { 0, 1, 3, 5, 6 };
		prime5[12].tntnitype = new int[] { 0, 1, 2, 4, 8 };
		prime5[13].tntnitype = new int[] { 0, 1, 2, 5, 7 };
		prime5[14].tntnitype = new int[] { 0, 1, 2, 6, 8 };
		prime5[15].tntnitype = new int[] { 0, 1, 3, 4, 7 };
		prime5[16].tntnitype = new int[] { 0, 1, 3, 4, 8 };
		prime5[17].tntnitype = new int[] { 0, 1, 4, 5, 7 };
		prime5[18].tntnitype = new int[] { 0, 1, 3, 6, 7 };
		prime5[19].tntnitype = new int[] { 0, 1, 3, 7, 8 };
		prime5[20].tntnitype = new int[] { 0, 1, 4, 5, 8 };
		prime5[21].tntnitype = new int[] { 0, 1, 4, 7, 8 };
		prime5[22].tntnitype = new int[] { 0, 2, 3, 5, 7 };
		prime5[23].tntnitype = new int[] { 0, 1, 3, 5, 7 };
		prime5[24].tntnitype = new int[] { 0, 2, 3, 5, 8 };
		prime5[25].tntnitype = new int[] { 0, 2, 4, 5, 8 };
		prime5[26].tntnitype = new int[] { 0, 1, 3, 5, 8 };
		prime5[27].tntnitype = new int[] { 0, 2, 3, 6, 8 };
		prime5[28].tntnitype = new int[] { 0, 1, 3, 6, 8 };
		prime5[29].tntnitype = new int[] { 0, 1, 4, 6, 8 };
		prime5[30].tntnitype = new int[] { 0, 1, 3, 6, 9 };
		prime5[31].tntnitype = new int[] { 0, 1, 4, 6, 9 };
		prime5[32].tntnitype = new int[] { 0, 2, 4, 6, 8 };
		prime5[33].tntnitype = new int[] { 0, 2, 4, 6, 9 };
		prime5[34].tntnitype = new int[] { 0, 2, 4, 7, 9 };
		prime5[35].tntnitype = new int[] { 0, 1, 2, 4, 7 };
		prime5[36].tntnitype = new int[] { 0, 3, 4, 5, 8 };
		prime5[37].tntnitype = new int[] { 0, 1, 2, 5, 8 };

		prime5[0].ivector = new int[] { 4, 3, 2, 1, 0, 0 };
		prime5[1].ivector = new int[] { 3, 3, 2, 1, 1, 0 };
		prime5[2].ivector = new int[] { 3, 2, 2, 2, 1, 0 };
		prime5[3].ivector = new int[] { 3, 2, 2, 1, 1, 1 };
		prime5[4].ivector = new int[] { 3, 2, 1, 1, 2, 1 };
		prime5[5].ivector = new int[] { 3, 1, 1, 2, 2, 1 };
		prime5[6].ivector = new int[] { 3, 1, 0, 1, 3, 2 };
		prime5[7].ivector = new int[] { 2, 3, 2, 2, 0, 1 };
		prime5[8].ivector = new int[] { 2, 3, 1, 2, 1, 1 };
		prime5[9].ivector = new int[] { 2, 2, 3, 1, 1, 1 };
		prime5[10].ivector = new int[] { 2, 2, 2, 2, 2, 0 };
		prime5[11].ivector = new int[] { 2, 2, 2, 1, 2, 1 };
		prime5[12].ivector = new int[] { 2, 2, 1, 3, 1, 1 };
		prime5[13].ivector = new int[] { 2, 2, 1, 1, 3, 1 };
		prime5[14].ivector = new int[] { 2, 2, 0, 2, 2, 2 };
		prime5[15].ivector = new int[] { 2, 1, 3, 2, 1, 1 };
		prime5[16].ivector = new int[] { 2, 1, 2, 3, 2, 0 };
		prime5[17].ivector = new int[] { 2, 1, 2, 2, 2, 1 };
		prime5[18].ivector = new int[] { 2, 1, 2, 1, 2, 2 };
		prime5[19].ivector = new int[] { 2, 1, 1, 2, 3, 1 };
		prime5[20].ivector = new int[] { 2, 0, 2, 4, 2, 0 };
		prime5[21].ivector = new int[] { 2, 0, 2, 3, 2, 1 };
		prime5[22].ivector = new int[] { 1, 3, 2, 1, 3, 0 };
		prime5[23].ivector = new int[] { 1, 3, 1, 2, 2, 1 };
		prime5[24].ivector = new int[] { 1, 2, 3, 1, 2, 1 };
		prime5[25].ivector = new int[] { 1, 2, 2, 3, 1, 1 };
		prime5[26].ivector = new int[] { 1, 2, 2, 2, 3, 0 };
		prime5[27].ivector = new int[] { 1, 2, 2, 2, 1, 2 };
		prime5[28].ivector = new int[] { 1, 2, 2, 1, 3, 1 };
		prime5[29].ivector = new int[] { 1, 2, 1, 3, 2, 1 };
		prime5[30].ivector = new int[] { 1, 1, 4, 1, 1, 2 };
		prime5[31].ivector = new int[] { 1, 1, 3, 2, 2, 1 };
		prime5[32].ivector = new int[] { 0, 4, 0, 4, 0, 2 };
		prime5[33].ivector = new int[] { 0, 3, 2, 2, 2, 1 };
		prime5[34].ivector = new int[] { 0, 3, 2, 1, 4, 0 };
		prime5[35].ivector = new int[] { 2, 2, 2, 1, 2, 1 };
		prime5[36].ivector = new int[] { 2, 1, 2, 3, 2, 0 };
		prime5[37].ivector = new int[] { 2, 1, 2, 2, 2, 1 };

		prime5[0].m7set = new int[] { 0, 2, 4, 7, 9 };
		prime5[1].m7set = new int[] { 0, 2, 3, 5, 7 };
		prime5[2].m7set = new int[] { 0, 1, 3, 5, 8 };
		prime5[3].m7set = new int[] { 0, 1, 3, 6, 8 };
		prime5[4].m7set = new int[] { 0, 1, 2, 5, 7 };
		prime5[5].m7set = new int[] { 0, 1, 3, 7, 8 };
		prime5[6].m7set = new int[] { 0, 1, 2, 6, 7 };
		prime5[7].m7set = new int[] { 0, 2, 4, 6, 9 };
		prime5[8].m7set = new int[] { 0, 1, 3, 5, 7 };
		prime5[9].m7set = new int[] { 0, 2, 3, 5, 8 };
		prime5[10].m7set = new int[] { 0, 2, 3, 4, 7 };
		prime5[11].m7set = new int[] { 0, 1, 3, 5, 6 };
		prime5[12].m7set = new int[] { 0, 1, 4, 6, 8 };
		prime5[13].m7set = new int[] { 0, 1, 2, 3, 7 };
		prime5[14].m7set = new int[] { 0, 2, 3, 6, 8 };
		prime5[15].m7set = new int[] { 0, 1, 4, 6, 9 };
		prime5[16].m7set = new int[] { 0, 3, 4, 5, 8 };
		prime5[17].m7set = new int[] { 0, 1, 2, 5, 8 };
		prime5[18].m7set = new int[] { 0, 1, 3, 6, 7 };
		prime5[19].m7set = new int[] { 0, 1, 2, 5, 6 };
		prime5[20].m7set = new int[] { 0, 1, 4, 5, 8 };
		prime5[21].m7set = new int[] { 0, 1, 4, 7, 8 };
		prime5[22].m7set = new int[] { 0, 1, 2, 3, 5 };
		prime5[23].m7set = new int[] { 0, 1, 2, 4, 6 };
		prime5[24].m7set = new int[] { 0, 1, 3, 4, 6 };
		prime5[25].m7set = new int[] { 0, 2, 4, 5, 8 };
		prime5[26].m7set = new int[] { 0, 1, 2, 4, 5 };
		prime5[27].m7set = new int[] { 0, 2, 3, 6, 8 };
		prime5[28].m7set = new int[] { 0, 1, 2, 3, 6 };
		prime5[29].m7set = new int[] { 0, 1, 2, 4, 8 };
		prime5[30].m7set = new int[] { 0, 1, 3, 6, 9 };
		prime5[31].m7set = new int[] { 0, 1, 3, 4, 7 };
		prime5[32].m7set = new int[] { 0, 2, 4, 6, 8 };
		prime5[33].m7set = new int[] { 0, 2, 3, 4, 6 };
		prime5[34].m7set = new int[] { 0, 1, 2, 3, 4 };
		prime5[35].m7set = new int[] { 0, 1, 2, 4, 7 };
		prime5[36].m7set = new int[] { 0, 1, 3, 4, 8 };
		prime5[37].m7set = new int[] { 0, 1, 4, 5, 7 };

		prime5[0].symmetry = new int[] { 1, 1 };
		prime5[1].symmetry = new int[] { 1, 0 };
		prime5[2].symmetry = new int[] { 1, 0 };
		prime5[3].symmetry = new int[] { 1, 0 };
		prime5[4].symmetry = new int[] { 1, 0 };
		prime5[5].symmetry = new int[] { 1, 0 };
		prime5[6].symmetry = new int[] { 1, 0 };
		prime5[7].symmetry = new int[] { 1, 1 };
		prime5[8].symmetry = new int[] { 1, 0 };
		prime5[9].symmetry = new int[] { 1, 0 };
		prime5[10].symmetry = new int[] { 1, 0 };
		prime5[11].symmetry = new int[] { 1, 1 };
		prime5[12].symmetry = new int[] { 1, 0 };
		prime5[13].symmetry = new int[] { 1, 0 };
		prime5[14].symmetry = new int[] { 1, 1 };
		prime5[15].symmetry = new int[] { 1, 0 };
		prime5[16].symmetry = new int[] { 1, 1 };
		prime5[17].symmetry = new int[] { 1, 0 };
		prime5[18].symmetry = new int[] { 1, 0 };
		prime5[19].symmetry = new int[] { 1, 0 };
		prime5[20].symmetry = new int[] { 1, 0 };
		prime5[21].symmetry = new int[] { 1, 1 };
		prime5[22].symmetry = new int[] { 1, 0 };
		prime5[23].symmetry = new int[] { 1, 0 };
		prime5[24].symmetry = new int[] { 1, 0 };
		prime5[25].symmetry = new int[] { 1, 0 };
		prime5[26].symmetry = new int[] { 1, 0 };
		prime5[27].symmetry = new int[] { 1, 0 };
		prime5[28].symmetry = new int[] { 1, 0 };
		prime5[29].symmetry = new int[] { 1, 0 };
		prime5[30].symmetry = new int[] { 1, 0 };
		prime5[31].symmetry = new int[] { 1, 0 };
		prime5[32].symmetry = new int[] { 1, 1 };
		prime5[33].symmetry = new int[] { 1, 1 };
		prime5[34].symmetry = new int[] { 1, 1 };
		prime5[35].symmetry = new int[] { 1, 0 };
		prime5[36].symmetry = new int[] { 1, 1 };
		prime5[37].symmetry = new int[] { 1, 0 };
	}

	public void initPrime6() {

		for (int i = 0; i < 50; i++)
			prime6[i] = new Set(6);

		prime6[0].name = "6-1";
		prime6[1].name = "6-2";
		prime6[2].name = "6-Z3";
		prime6[3].name = "6-Z4";
		prime6[4].name = "6-5";
		prime6[5].name = "6-Z6";
		prime6[6].name = "6-7";
		prime6[7].name = "6-8";
		prime6[8].name = "6-9";
		prime6[9].name = "6-Z10";
		prime6[10].name = "6-Z11";
		prime6[11].name = "6-Z12";
		prime6[12].name = "6-Z13";
		prime6[13].name = "6-14";
		prime6[14].name = "6-15";
		prime6[15].name = "6-16";
		prime6[16].name = "6-Z17";
		prime6[17].name = "6-18";
		prime6[18].name = "6-Z19";
		prime6[19].name = "6-20";
		prime6[20].name = "6-21";
		prime6[21].name = "6-22";
		prime6[22].name = "6-Z23";
		prime6[23].name = "6-Z24";
		prime6[24].name = "6-Z25";
		prime6[25].name = "6-Z26";
		prime6[26].name = "6-27";
		prime6[27].name = "6-Z28";
		prime6[28].name = "6-Z29";
		prime6[29].name = "6-30";
		prime6[30].name = "6-31";
		prime6[31].name = "6-32";
		prime6[32].name = "6-33";
		prime6[33].name = "6-34";
		prime6[34].name = "6-35";
		prime6[35].name = "6-Z36";
		prime6[36].name = "6-Z37";
		prime6[37].name = "6-Z38";
		prime6[38].name = "6-Z39";
		prime6[39].name = "6-Z40";
		prime6[40].name = "6-Z41";
		prime6[41].name = "6-Z42";
		prime6[42].name = "6-Z43";
		prime6[43].name = "6-Z44";
		prime6[44].name = "6-Z45";
		prime6[45].name = "6-Z46";
		prime6[46].name = "6-Z47";
		prime6[47].name = "6-Z48";
		prime6[48].name = "6-Z49";
		prime6[49].name = "6-Z50";

        prime6[0].description = "6-1";
        prime6[1].description = "6-2";
        prime6[2].description = "6-Z3";
        prime6[3].description = "6-Z4";
        prime6[4].description = "6-5";
        prime6[5].description = "6-Z6";
        prime6[6].description = "6-7";
        prime6[7].description = "6-8";
        prime6[8].description = "6-9";
        prime6[9].description = "6-Z10";
        prime6[10].description = "6-Z11";
        prime6[11].description = "6-Z12";
        prime6[12].description = "6-Z13";
        prime6[13].description = "6-14";
        prime6[14].description = "6-15";
        prime6[15].description = "6-16";
        prime6[16].description = "6-Z17";
        prime6[17].description = "6-18";
        prime6[18].description = "6-Z19";
        prime6[19].description = "6-20";
        prime6[20].description = "6-21";
        prime6[21].description = "6-22";
        prime6[22].description = "6-Z23";
        prime6[23].description = "6-Z24";
        prime6[24].description = "6-Z25";
        prime6[25].description = "6-Z26";
        prime6[26].description = "6-27";
        prime6[27].description = "6-Z28";
        prime6[28].description = "6-Z29";
        prime6[29].description = "6-30";
        prime6[30].description = "6-31";
        prime6[31].description = "6-32";
        prime6[32].description = "6-33";
        prime6[33].description = "6-34";
        prime6[34].description = "6-35";
        prime6[35].description = "6-Z36";
        prime6[36].description = "6-Z37";
        prime6[37].description = "6-Z38";
        prime6[38].description = "6-Z39";
        prime6[39].description = "6-Z40";
        prime6[40].description = "6-Z41";
        prime6[41].description = "6-Z42";
        prime6[42].description = "6-Z43";
        prime6[43].description = "6-Z44";
        prime6[44].description = "6-Z45";
        prime6[45].description = "6-Z46";
        prime6[46].description = "6-Z47";
        prime6[47].description = "6-Z48";
        prime6[48].description = "6-Z49";
        prime6[49].description = "6-Z50";

		prime6[0].tntnitype = new int[] { 0, 1, 2, 3, 4, 5 };
		prime6[1].tntnitype = new int[] { 0, 1, 2, 3, 4, 6 };
		prime6[2].tntnitype = new int[] { 0, 1, 2, 3, 5, 6 };
		prime6[3].tntnitype = new int[] { 0, 1, 2, 4, 5, 6 };
		prime6[4].tntnitype = new int[] { 0, 1, 2, 3, 6, 7 };
		prime6[5].tntnitype = new int[] { 0, 1, 2, 5, 6, 7 };
		prime6[6].tntnitype = new int[] { 0, 1, 2, 6, 7, 8 };
		prime6[7].tntnitype = new int[] { 0, 2, 3, 4, 5, 7 };
		prime6[8].tntnitype = new int[] { 0, 1, 2, 3, 5, 7 };
		prime6[9].tntnitype = new int[] { 0, 1, 3, 4, 5, 7 };
		prime6[10].tntnitype = new int[] { 0, 1, 2, 4, 5, 7 };
		prime6[11].tntnitype = new int[] { 0, 1, 2, 4, 6, 7 };
		prime6[12].tntnitype = new int[] { 0, 1, 3, 4, 6, 7 };
		prime6[13].tntnitype = new int[] { 0, 1, 3, 4, 5, 8 };
		prime6[14].tntnitype = new int[] { 0, 1, 2, 4, 5, 8 };
		prime6[15].tntnitype = new int[] { 0, 1, 4, 5, 6, 8 };
		prime6[16].tntnitype = new int[] { 0, 1, 2, 4, 7, 8 };
		prime6[17].tntnitype = new int[] { 0, 1, 2, 5, 7, 8 };
		prime6[18].tntnitype = new int[] { 0, 1, 3, 4, 7, 8 };
		prime6[19].tntnitype = new int[] { 0, 1, 4, 5, 8, 9 };
		prime6[20].tntnitype = new int[] { 0, 2, 3, 4, 6, 8 };
		prime6[21].tntnitype = new int[] { 0, 1, 2, 4, 6, 8 };
		prime6[22].tntnitype = new int[] { 0, 2, 3, 5, 6, 8 };
		prime6[23].tntnitype = new int[] { 0, 1, 3, 4, 6, 8 };
		prime6[24].tntnitype = new int[] { 0, 1, 3, 5, 6, 8 };
		prime6[25].tntnitype = new int[] { 0, 1, 3, 5, 7, 8 };
		prime6[26].tntnitype = new int[] { 0, 1, 3, 4, 6, 9 };
		prime6[27].tntnitype = new int[] { 0, 1, 3, 5, 6, 9 };
		prime6[28].tntnitype = new int[] { 0, 1, 3, 6, 8, 9 };
		prime6[29].tntnitype = new int[] { 0, 1, 3, 6, 7, 9 };
		prime6[30].tntnitype = new int[] { 0, 1, 3, 5, 8, 9 };
		prime6[31].tntnitype = new int[] { 0, 2, 4, 5, 7, 9 };
		prime6[32].tntnitype = new int[] { 0, 2, 3, 5, 7, 9 };
		prime6[33].tntnitype = new int[] { 0, 1, 3, 5, 7, 9 };
		prime6[34].tntnitype = new int[] { 0, 2, 4, 6, 8, 10 };
		prime6[35].tntnitype = new int[] { 0, 1, 2, 3, 4, 7 };
		prime6[36].tntnitype = new int[] { 0, 1, 2, 3, 4, 8 };
		prime6[37].tntnitype = new int[] { 0, 1, 2, 3, 7, 8 };
		prime6[38].tntnitype = new int[] { 0, 2, 3, 4, 5, 8 };
		prime6[39].tntnitype = new int[] { 0, 1, 2, 3, 5, 8 };
		prime6[40].tntnitype = new int[] { 0, 1, 2, 3, 6, 8 };
		prime6[41].tntnitype = new int[] { 0, 1, 2, 3, 6, 9 };
		prime6[42].tntnitype = new int[] { 0, 1, 2, 5, 6, 8 };
		prime6[43].tntnitype = new int[] { 0, 1, 2, 5, 6, 9 };
		prime6[44].tntnitype = new int[] { 0, 2, 3, 4, 6, 9 };
		prime6[45].tntnitype = new int[] { 0, 1, 2, 4, 6, 9 };
		prime6[46].tntnitype = new int[] { 0, 1, 2, 4, 7, 9 };
		prime6[47].tntnitype = new int[] { 0, 1, 2, 5, 7, 9 };
		prime6[48].tntnitype = new int[] { 0, 1, 3, 4, 7, 9 };
		prime6[49].tntnitype = new int[] { 0, 1, 4, 6, 7, 9 };

		prime6[0].ivector = new int[] { 5, 4, 3, 2, 1, 0 };
		prime6[1].ivector = new int[] { 4, 4, 3, 2, 1, 1 };
		prime6[2].ivector = new int[] { 4, 3, 3, 2, 2, 1 };
		prime6[3].ivector = new int[] { 4, 3, 2, 3, 2, 1 };
		prime6[4].ivector = new int[] { 4, 2, 2, 2, 3, 2 };
		prime6[5].ivector = new int[] { 4, 2, 1, 2, 4, 2 };
		prime6[6].ivector = new int[] { 4, 2, 0, 2, 4, 3 };
		prime6[7].ivector = new int[] { 3, 4, 3, 2, 3, 0 };
		prime6[8].ivector = new int[] { 3, 4, 2, 2, 3, 1 };
		prime6[9].ivector = new int[] { 3, 3, 3, 3, 2, 1 };
		prime6[10].ivector = new int[] { 3, 3, 3, 2, 3, 1 };
		prime6[11].ivector = new int[] { 3, 3, 2, 2, 3, 2 };
		prime6[12].ivector = new int[] { 3, 2, 4, 2, 2, 2 };
		prime6[13].ivector = new int[] { 3, 2, 3, 4, 3, 0 };
		prime6[14].ivector = new int[] { 3, 2, 3, 4, 2, 1 };
		prime6[15].ivector = new int[] { 3, 2, 2, 4, 3, 1 };
		prime6[16].ivector = new int[] { 3, 2, 2, 3, 3, 2 };
		prime6[17].ivector = new int[] { 3, 2, 2, 2, 4, 2 };
		prime6[18].ivector = new int[] { 3, 1, 3, 4, 3, 1 };
		prime6[19].ivector = new int[] { 3, 0, 3, 6, 3, 0 };
		prime6[20].ivector = new int[] { 2, 4, 2, 4, 1, 2 };
		prime6[21].ivector = new int[] { 2, 4, 1, 4, 2, 2 };
		prime6[22].ivector = new int[] { 2, 3, 4, 2, 2, 2 };
		prime6[23].ivector = new int[] { 2, 3, 3, 3, 3, 1 };
		prime6[24].ivector = new int[] { 2, 3, 3, 2, 4, 1 };
		prime6[25].ivector = new int[] { 2, 3, 2, 3, 4, 1 };
		prime6[26].ivector = new int[] { 2, 2, 5, 2, 2, 2 };
		prime6[27].ivector = new int[] { 2, 2, 4, 3, 2, 2 };
		prime6[28].ivector = new int[] { 2, 2, 4, 2, 3, 2 };
		prime6[29].ivector = new int[] { 2, 2, 4, 2, 2, 3 };
		prime6[30].ivector = new int[] { 2, 2, 3, 4, 3, 1 };
		prime6[31].ivector = new int[] { 1, 4, 3, 2, 5, 0 };
		prime6[32].ivector = new int[] { 1, 4, 3, 2, 4, 1 };
		prime6[33].ivector = new int[] { 1, 4, 2, 4, 2, 2 };
		prime6[34].ivector = new int[] { 0, 6, 0, 6, 0, 3 };
		prime6[35].ivector = new int[] { 4, 3, 3, 2, 2, 1 };
		prime6[36].ivector = new int[] { 4, 3, 2, 3, 2, 1 };
		prime6[37].ivector = new int[] { 4, 2, 1, 2, 4, 2 };
		prime6[38].ivector = new int[] { 3, 3, 3, 3, 2, 1 };
		prime6[39].ivector = new int[] { 3, 3, 3, 2, 3, 1 };
		prime6[40].ivector = new int[] { 3, 3, 2, 2, 3, 2 };
		prime6[41].ivector = new int[] { 3, 2, 4, 2, 2, 2 };
		prime6[42].ivector = new int[] { 3, 2, 2, 3, 3, 2 };
		prime6[43].ivector = new int[] { 3, 1, 3, 4, 3, 1 };
		prime6[44].ivector = new int[] { 2, 3, 4, 2, 2, 2 };
		prime6[45].ivector = new int[] { 2, 3, 3, 3, 3, 1 };
		prime6[46].ivector = new int[] { 2, 3, 3, 2, 4, 1 };
		prime6[47].ivector = new int[] { 2, 3, 2, 3, 4, 1 };
		prime6[48].ivector = new int[] { 2, 2, 4, 3, 2, 2 };
		prime6[49].ivector = new int[] { 2, 2, 4, 2, 3, 2 };

		prime6[0].m7set = new int[] { 0, 2, 4, 5, 7, 9 };
		prime6[1].m7set = new int[] { 0, 2, 3, 5, 7, 9 };
		prime6[2].m7set = new int[] { 0, 1, 3, 5, 6, 8 };
		prime6[3].m7set = new int[] { 0, 1, 3, 5, 7, 8 };
		prime6[4].m7set = new int[] { 0, 1, 2, 5, 7, 8 };
		prime6[5].m7set = new int[] { 0, 1, 2, 3, 7, 8 };
		prime6[6].m7set = new int[] { 0, 1, 2, 6, 7, 8 };
		prime6[7].m7set = new int[] { 0, 2, 3, 4, 5, 7 };
		prime6[8].m7set = new int[] { 0, 1, 2, 3, 5, 7 };
		prime6[9].m7set = new int[] { 0, 1, 2, 4, 6, 9 };
		prime6[10].m7set = new int[] { 0, 1, 2, 3, 5, 8 };
		prime6[11].m7set = new int[] { 0, 1, 2, 4, 6, 7 };
		prime6[12].m7set = new int[] { 0, 1, 4, 6, 7, 9 };
		prime6[13].m7set = new int[] { 0, 1, 3, 4, 5, 8 };
		prime6[14].m7set = new int[] { 0, 1, 3, 5, 8, 9 };
		prime6[15].m7set = new int[] { 0, 1, 4, 5, 6, 8 };
		prime6[16].m7set = new int[] { 0, 1, 2, 4, 7, 8 };
		prime6[17].m7set = new int[] { 0, 1, 2, 3, 6, 7 };
		prime6[18].m7set = new int[] { 0, 1, 2, 5, 8, 9 };
		prime6[19].m7set = new int[] { 0, 1, 4, 5, 8, 9 };
		prime6[20].m7set = new int[] { 0, 1, 3, 5, 7, 9 };
		prime6[21].m7set = new int[] { 0, 1, 2, 4, 6, 8 };
		prime6[22].m7set = new int[] { 0, 2, 3, 5, 6, 8 };
		prime6[23].m7set = new int[] { 0, 2, 3, 4, 5, 8 };
		prime6[24].m7set = new int[] { 0, 1, 2, 3, 5, 6 };
		prime6[25].m7set = new int[] { 0, 1, 2, 4, 5, 6 };
		prime6[26].m7set = new int[] { 0, 1, 3, 4, 6, 9 };
		prime6[27].m7set = new int[] { 0, 1, 3, 5, 6, 9 };
		prime6[28].m7set = new int[] { 0, 1, 2, 3, 6, 9 };
		prime6[29].m7set = new int[] { 0, 1, 3, 6, 7, 9 };
		prime6[30].m7set = new int[] { 0, 1, 2, 4, 5, 8 };
		prime6[31].m7set = new int[] { 0, 1, 2, 3, 4, 5 };
		prime6[32].m7set = new int[] { 0, 1, 2, 3, 4, 6 };
		prime6[33].m7set = new int[] { 0, 2, 3, 4, 6, 8 };
		prime6[34].m7set = new int[] { 0, 2, 4, 6, 8, 10 };
		prime6[35].m7set = new int[] { 0, 1, 2, 4, 7, 9 };
		prime6[36].m7set = new int[] { 0, 1, 2, 5, 7, 9 };
		prime6[37].m7set = new int[] { 0, 1, 2, 5, 6, 7 };
		prime6[38].m7set = new int[] { 0, 1, 3, 4, 6, 8 };
		prime6[39].m7set = new int[] { 0, 1, 2, 4, 5, 7 };
		prime6[40].m7set = new int[] { 0, 1, 2, 3, 6, 8 };
		prime6[41].m7set = new int[] { 0, 1, 3, 6, 8, 9 };
		prime6[42].m7set = new int[] { 0, 1, 2, 5, 6, 8 };
		prime6[43].m7set = new int[] { 0, 1, 3, 4, 7, 8 };
		prime6[44].m7set = new int[] { 0, 2, 3, 4, 6, 9 };
		prime6[45].m7set = new int[] { 0, 1, 3, 4, 5, 7 };
		prime6[46].m7set = new int[] { 0, 1, 2, 3, 4, 7 };
		prime6[47].m7set = new int[] { 0, 1, 2, 3, 4, 8 };
		prime6[48].m7set = new int[] { 0, 1, 3, 4, 7, 9 };
		prime6[49].m7set = new int[] { 0, 1, 3, 4, 6, 7 };

		prime6[0].symmetry = new int[] { 1, 1 };
		prime6[1].symmetry = new int[] { 1, 0 };
		prime6[2].symmetry = new int[] { 1, 0 };
		prime6[3].symmetry = new int[] { 1, 1 };
		prime6[4].symmetry = new int[] { 1, 0 };
		prime6[5].symmetry = new int[] { 1, 1 };
		prime6[6].symmetry = new int[] { 2, 2 };
		prime6[7].symmetry = new int[] { 1, 1 };
		prime6[8].symmetry = new int[] { 1, 0 };
		prime6[9].symmetry = new int[] { 1, 0 };
		prime6[10].symmetry = new int[] { 1, 0 };
		prime6[11].symmetry = new int[] { 1, 0 };
		prime6[12].symmetry = new int[] { 1, 1 };
		prime6[13].symmetry = new int[] { 1, 0 };
		prime6[14].symmetry = new int[] { 1, 0 };
		prime6[15].symmetry = new int[] { 1, 0 };
		prime6[16].symmetry = new int[] { 1, 0 };
		prime6[17].symmetry = new int[] { 1, 0 };
		prime6[18].symmetry = new int[] { 1, 0 };
		prime6[19].symmetry = new int[] { 3, 3 };
		prime6[20].symmetry = new int[] { 1, 0 };
		prime6[21].symmetry = new int[] { 1, 0 };
		prime6[22].symmetry = new int[] { 1, 1 };
		prime6[23].symmetry = new int[] { 1, 0 };
		prime6[24].symmetry = new int[] { 1, 0 };
		prime6[25].symmetry = new int[] { 1, 1 };
		prime6[26].symmetry = new int[] { 1, 0 };
		prime6[27].symmetry = new int[] { 1, 1 };
		prime6[28].symmetry = new int[] { 1, 1 };
		prime6[29].symmetry = new int[] { 2, 0 };
		prime6[30].symmetry = new int[] { 1, 0 };
		prime6[31].symmetry = new int[] { 1, 1 };
		prime6[32].symmetry = new int[] { 1, 0 };
		prime6[33].symmetry = new int[] { 1, 0 };
		prime6[34].symmetry = new int[] { 6, 6 };
		prime6[35].symmetry = new int[] { 1, 0 };
		prime6[36].symmetry = new int[] { 1, 1 };
		prime6[37].symmetry = new int[] { 1, 1 };
		prime6[38].symmetry = new int[] { 1, 0 };
		prime6[39].symmetry = new int[] { 1, 0 };
		prime6[40].symmetry = new int[] { 1, 0 };
		prime6[41].symmetry = new int[] { 1, 1 };
		prime6[42].symmetry = new int[] { 1, 0 };
		prime6[43].symmetry = new int[] { 1, 0 };
		prime6[44].symmetry = new int[] { 1, 1 };
		prime6[45].symmetry = new int[] { 1, 0 };
		prime6[46].symmetry = new int[] { 1, 0 };
		prime6[47].symmetry = new int[] { 1, 1 };
		prime6[48].symmetry = new int[] { 1, 1 };
		prime6[49].symmetry = new int[] { 1, 1 };

		prime6[0].combinatorial = new int[] { 1, 1, 1, 1 };
		prime6[1].combinatorial = new int[] { 0, 1, 1, 0 };
		prime6[2].combinatorial = new int[] { 0, 1, 0, 0 };
		prime6[3].combinatorial = new int[] { 0, 1, 0, 1 };
		prime6[4].combinatorial = new int[] { 0, 1, 1, 0 };
		prime6[5].combinatorial = new int[] { 0, 1, 0, 1 };
		prime6[6].combinatorial = new int[] { 2, 2, 2, 2 };
		prime6[7].combinatorial = new int[] { 1, 1, 1, 1 };
		prime6[8].combinatorial = new int[] { 0, 1, 1, 0 };
		prime6[9].combinatorial = new int[] { 0, 1, 0, 0 };
		prime6[10].combinatorial = new int[] { 0, 1, 0, 0 };
		prime6[11].combinatorial = new int[] { 0, 1, 0, 0 };
		prime6[12].combinatorial = new int[] { 0, 1, 0, 1 };
		prime6[13].combinatorial = new int[] { 1, 1, 0, 0 };
		prime6[14].combinatorial = new int[] { 0, 1, 1, 0 };
		prime6[15].combinatorial = new int[] { 0, 1, 1, 0 };
		prime6[16].combinatorial = new int[] { 0, 1, 0, 0 };
		prime6[17].combinatorial = new int[] { 0, 1, 1, 0 };
		prime6[18].combinatorial = new int[] { 0, 1, 0, 0 };
		prime6[19].combinatorial = new int[] { 3, 3, 3, 3 };
		prime6[20].combinatorial = new int[] { 0, 1, 1, 0 };
		prime6[21].combinatorial = new int[] { 0, 1, 1, 0 };
		prime6[22].combinatorial = new int[] { 0, 1, 0, 1 };
		prime6[23].combinatorial = new int[] { 0, 1, 0, 0 };
		prime6[24].combinatorial = new int[] { 0, 1, 0, 0 };
		prime6[25].combinatorial = new int[] { 0, 1, 0, 1 };
		prime6[26].combinatorial = new int[] { 0, 1, 1, 0 };
		prime6[27].combinatorial = new int[] { 0, 1, 0, 1 };
		prime6[28].combinatorial = new int[] { 0, 1, 0, 1 };
		prime6[29].combinatorial = new int[] { 0, 2, 1, 0 };
		prime6[30].combinatorial = new int[] { 0, 1, 1, 0 };
		prime6[31].combinatorial = new int[] { 1, 1, 1, 1 };
		prime6[32].combinatorial = new int[] { 0, 1, 1, 0 };
		prime6[33].combinatorial = new int[] { 0, 1, 1, 0 };
		prime6[34].combinatorial = new int[] { 6, 6, 6, 6 };
		prime6[35].combinatorial = new int[] { 0, 1, 0, 0 };
		prime6[36].combinatorial = new int[] { 0, 1, 0, 1 };
		prime6[37].combinatorial = new int[] { 0, 1, 0, 1 };
		prime6[38].combinatorial = new int[] { 0, 1, 0, 0 };
		prime6[39].combinatorial = new int[] { 0, 1, 0, 0 };
		prime6[40].combinatorial = new int[] { 0, 1, 0, 0 };
		prime6[41].combinatorial = new int[] { 0, 1, 0, 1 };
		prime6[42].combinatorial = new int[] { 0, 1, 0, 0 };
		prime6[43].combinatorial = new int[] { 0, 1, 0, 0 };
		prime6[44].combinatorial = new int[] { 0, 1, 0, 1 };
		prime6[45].combinatorial = new int[] { 0, 1, 0, 0 };
		prime6[46].combinatorial = new int[] { 0, 1, 0, 0 };
		prime6[47].combinatorial = new int[] { 0, 1, 0, 1 };
		prime6[48].combinatorial = new int[] { 0, 1, 0, 1 };
		prime6[49].combinatorial = new int[] { 0, 1, 0, 1 };
	}

	public void initPrime7() {

		for (int i = 0; i < 38; i++)
			prime7[i] = new Set(7);

		prime7[0].name = "7-1";
		prime7[1].name = "7-2";
		prime7[2].name = "7-3";
		prime7[3].name = "7-4";
		prime7[4].name = "7-5";
		prime7[5].name = "7-6";
		prime7[6].name = "7-7";
		prime7[7].name = "7-8";
		prime7[8].name = "7-9";
		prime7[9].name = "7-10";
		prime7[10].name = "7-11";
		prime7[11].name = "7-Z12";
		prime7[12].name = "7-13";
		prime7[13].name = "7-14";
		prime7[14].name = "7-15";
		prime7[15].name = "7-16";
		prime7[16].name = "7-Z17";
		prime7[17].name = "7-Z18";
		prime7[18].name = "7-19";
		prime7[19].name = "7-20";
		prime7[20].name = "7-21";
		prime7[21].name = "7-22";
		prime7[22].name = "7-23";
		prime7[23].name = "7-24";
		prime7[24].name = "7-25";
		prime7[25].name = "7-26";
		prime7[26].name = "7-27";
		prime7[27].name = "7-28";
		prime7[28].name = "7-29";
		prime7[29].name = "7-30";
		prime7[30].name = "7-31";
		prime7[31].name = "7-32";
		prime7[32].name = "7-33";
		prime7[33].name = "7-34";
		prime7[34].name = "7-35";
		prime7[35].name = "7-Z36";
		prime7[36].name = "7-Z37";
		prime7[37].name = "7-Z38";

        prime7[0].description = "7-1";
        prime7[1].description = "7-2";
        prime7[2].description = "7-3";
        prime7[3].description = "7-4";
        prime7[4].description = "7-5";
        prime7[5].description = "7-6";
        prime7[6].description = "7-7";
        prime7[7].description = "7-8";
        prime7[8].description = "7-9";
        prime7[9].description = "7-10";
        prime7[10].description = "7-11";
        prime7[11].description = "7-Z12";
        prime7[12].description = "7-13";
        prime7[13].description = "7-14";
        prime7[14].description = "7-15";
        prime7[15].description = "7-16";
        prime7[16].description = "7-Z17";
        prime7[17].description = "7-Z18";
        prime7[18].description = "7-19";
        prime7[19].description = "7-20";
        prime7[20].description = "7-21";
        prime7[21].description = "7-22";
        prime7[22].description = "7-23";
        prime7[23].description = "7-24";
        prime7[24].description = "7-25";
        prime7[25].description = "7-26";
        prime7[26].description = "7-27";
        prime7[27].description = "7-28";
        prime7[28].description = "7-29";
        prime7[29].description = "7-30";
        prime7[30].description = "7-31";
        prime7[31].description = "7-32";
        prime7[32].description = "7-33";
        prime7[33].description = "7-34";
        prime7[34].description = "7-35";
        prime7[35].description = "7-Z36";
        prime7[36].description = "7-Z37";
        prime7[37].description = "7-Z38";

		prime7[0].tntnitype = new int[] { 0, 1, 2, 3, 4, 5, 6 };
		prime7[1].tntnitype = new int[] { 0, 1, 2, 3, 4, 5, 7 };
		prime7[2].tntnitype = new int[] { 0, 1, 2, 3, 4, 5, 8 };
		prime7[3].tntnitype = new int[] { 0, 1, 2, 3, 4, 6, 7 };
		prime7[4].tntnitype = new int[] { 0, 1, 2, 3, 5, 6, 7 };
		prime7[5].tntnitype = new int[] { 0, 1, 2, 3, 4, 7, 8 };
		prime7[6].tntnitype = new int[] { 0, 1, 2, 3, 6, 7, 8 };
		prime7[7].tntnitype = new int[] { 0, 2, 3, 4, 5, 6, 8 };
		prime7[8].tntnitype = new int[] { 0, 1, 2, 3, 4, 6, 8 };
		prime7[9].tntnitype = new int[] { 0, 1, 2, 3, 4, 6, 9 };
		prime7[10].tntnitype = new int[] { 0, 1, 3, 4, 5, 6, 8 };
		prime7[11].tntnitype = new int[] { 0, 1, 2, 3, 4, 7, 9 };
		prime7[12].tntnitype = new int[] { 0, 1, 2, 4, 5, 6, 8 };
		prime7[13].tntnitype = new int[] { 0, 1, 2, 3, 5, 7, 8 };
		prime7[14].tntnitype = new int[] { 0, 1, 2, 4, 6, 7, 8 };
		prime7[15].tntnitype = new int[] { 0, 1, 2, 3, 5, 6, 9 };
		prime7[16].tntnitype = new int[] { 0, 1, 2, 4, 5, 6, 9 };
		prime7[17].tntnitype = new int[] { 0, 1, 2, 3, 5, 8, 9 }; // Forte
		// prime7[17].tntnitype = new int[] {0,1,4,5,6,7,9}; // Rahn
		prime7[18].tntnitype = new int[] { 0, 1, 2, 3, 6, 7, 9 };
		prime7[19].tntnitype = new int[] { 0, 1, 2, 4, 7, 8, 9 }; // Forte
		// prime7[19].tntnitype = new int[] {0,1,2,5,6,7,9}; // Rahn
		prime7[20].tntnitype = new int[] { 0, 1, 2, 4, 5, 8, 9 };
		prime7[21].tntnitype = new int[] { 0, 1, 2, 5, 6, 8, 9 };
		prime7[22].tntnitype = new int[] { 0, 2, 3, 4, 5, 7, 9 };
		prime7[23].tntnitype = new int[] { 0, 1, 2, 3, 5, 7, 9 };
		prime7[24].tntnitype = new int[] { 0, 2, 3, 4, 6, 7, 9 };
		prime7[25].tntnitype = new int[] { 0, 1, 3, 4, 5, 7, 9 };
		prime7[26].tntnitype = new int[] { 0, 1, 2, 4, 5, 7, 9 };
		prime7[27].tntnitype = new int[] { 0, 1, 3, 5, 6, 7, 9 };
		prime7[28].tntnitype = new int[] { 0, 1, 2, 4, 6, 7, 9 };
		prime7[29].tntnitype = new int[] { 0, 1, 2, 4, 6, 8, 9 };
		prime7[30].tntnitype = new int[] { 0, 1, 3, 4, 6, 7, 9 };
		prime7[31].tntnitype = new int[] { 0, 1, 3, 4, 6, 8, 9 };
		prime7[32].tntnitype = new int[] { 0, 1, 2, 4, 6, 8, 10 };
		prime7[33].tntnitype = new int[] { 0, 1, 3, 4, 6, 8, 10 };
		prime7[34].tntnitype = new int[] { 0, 1, 3, 5, 6, 8, 10 };
		prime7[35].tntnitype = new int[] { 0, 1, 2, 3, 5, 6, 8 };
		prime7[36].tntnitype = new int[] { 0, 1, 3, 4, 5, 7, 8 };
		prime7[37].tntnitype = new int[] { 0, 1, 2, 4, 5, 7, 8 };

		prime7[0].ivector = new int[] { 6, 5, 4, 3, 2, 1 };
		prime7[1].ivector = new int[] { 5, 5, 4, 3, 3, 1 };
		prime7[2].ivector = new int[] { 5, 4, 4, 4, 3, 1 };
		prime7[3].ivector = new int[] { 5, 4, 4, 3, 3, 2 };
		prime7[4].ivector = new int[] { 5, 4, 3, 3, 4, 2 };
		prime7[5].ivector = new int[] { 5, 3, 3, 4, 4, 2 };
		prime7[6].ivector = new int[] { 5, 3, 2, 3, 5, 3 };
		prime7[7].ivector = new int[] { 4, 5, 4, 4, 2, 2 };
		prime7[8].ivector = new int[] { 4, 5, 3, 4, 3, 2 };
		prime7[9].ivector = new int[] { 4, 4, 5, 3, 3, 2 };
		prime7[10].ivector = new int[] { 4, 4, 4, 4, 4, 1 };
		prime7[11].ivector = new int[] { 4, 4, 4, 3, 4, 2 };
		prime7[12].ivector = new int[] { 4, 4, 3, 5, 3, 2 };
		prime7[13].ivector = new int[] { 4, 4, 3, 3, 5, 2 };
		prime7[14].ivector = new int[] { 4, 4, 2, 4, 4, 3 };
		prime7[15].ivector = new int[] { 4, 3, 5, 4, 3, 2 };
		prime7[16].ivector = new int[] { 4, 3, 4, 5, 4, 1 };
		prime7[17].ivector = new int[] { 4, 3, 4, 4, 4, 2 };
		prime7[18].ivector = new int[] { 4, 3, 4, 3, 4, 3 };
		prime7[19].ivector = new int[] { 4, 3, 3, 4, 5, 2 };
		prime7[20].ivector = new int[] { 4, 2, 4, 6, 4, 1 };
		prime7[21].ivector = new int[] { 4, 2, 4, 5, 4, 2 };
		prime7[22].ivector = new int[] { 3, 5, 4, 3, 5, 1 };
		prime7[23].ivector = new int[] { 3, 5, 3, 4, 4, 2 };
		prime7[24].ivector = new int[] { 3, 4, 5, 3, 4, 2 };
		prime7[25].ivector = new int[] { 3, 4, 4, 5, 3, 2 };
		prime7[26].ivector = new int[] { 3, 4, 4, 4, 5, 1 };
		prime7[27].ivector = new int[] { 3, 4, 4, 4, 3, 3 };
		prime7[28].ivector = new int[] { 3, 4, 4, 3, 5, 2 };
		prime7[29].ivector = new int[] { 3, 4, 3, 5, 4, 2 };
		prime7[30].ivector = new int[] { 3, 3, 6, 3, 3, 3 };
		prime7[31].ivector = new int[] { 3, 3, 5, 4, 4, 2 };
		prime7[32].ivector = new int[] { 2, 6, 2, 6, 2, 3 };
		prime7[33].ivector = new int[] { 2, 5, 4, 4, 4, 2 };
		prime7[34].ivector = new int[] { 2, 5, 4, 3, 6, 1 };
		prime7[35].ivector = new int[] { 4, 4, 4, 3, 4, 2 };
		prime7[36].ivector = new int[] { 4, 3, 4, 5, 4, 1 };
		prime7[37].ivector = new int[] { 4, 3, 4, 4, 4, 2 };

		prime7[0].symmetry = new int[] { 1, 1 };
		prime7[1].symmetry = new int[] { 1, 0 };
		prime7[2].symmetry = new int[] { 1, 0 };
		prime7[3].symmetry = new int[] { 1, 0 };
		prime7[4].symmetry = new int[] { 1, 0 };
		prime7[5].symmetry = new int[] { 1, 0 };
		prime7[6].symmetry = new int[] { 1, 0 };
		prime7[7].symmetry = new int[] { 1, 1 };
		prime7[8].symmetry = new int[] { 1, 0 };
		prime7[9].symmetry = new int[] { 1, 0 };
		prime7[10].symmetry = new int[] { 1, 0 };
		prime7[11].symmetry = new int[] { 1, 1 };
		prime7[12].symmetry = new int[] { 1, 0 };
		prime7[13].symmetry = new int[] { 1, 0 };
		prime7[14].symmetry = new int[] { 1, 1 };
		prime7[15].symmetry = new int[] { 1, 0 };
		prime7[16].symmetry = new int[] { 1, 1 };
		prime7[17].symmetry = new int[] { 1, 0 };
		prime7[18].symmetry = new int[] { 1, 0 };
		prime7[19].symmetry = new int[] { 1, 0 };
		prime7[20].symmetry = new int[] { 1, 0 };
		prime7[21].symmetry = new int[] { 1, 1 };
		prime7[22].symmetry = new int[] { 1, 0 };
		prime7[23].symmetry = new int[] { 1, 0 };
		prime7[24].symmetry = new int[] { 1, 0 };
		prime7[25].symmetry = new int[] { 1, 0 };
		prime7[26].symmetry = new int[] { 1, 0 };
		prime7[27].symmetry = new int[] { 1, 0 };
		prime7[28].symmetry = new int[] { 1, 0 };
		prime7[29].symmetry = new int[] { 1, 0 };
		prime7[30].symmetry = new int[] { 1, 0 };
		prime7[31].symmetry = new int[] { 1, 0 };
		prime7[32].symmetry = new int[] { 1, 1 };
		prime7[33].symmetry = new int[] { 1, 1 };
		prime7[34].symmetry = new int[] { 1, 1 };
		prime7[35].symmetry = new int[] { 1, 0 };
		prime7[36].symmetry = new int[] { 1, 1 };
		prime7[37].symmetry = new int[] { 1, 0 };
	}

	public void initPrime8() {

		for (int i = 0; i < 29; i++)
			prime8[i] = new Set(8);

		prime8[0].name = "8-1";
        prime8[1].name = "8-2";
        prime8[2].name = "8-3";
        prime8[3].name = "8-4";
        prime8[4].name = "8-5";
        prime8[5].name = "8-6";
        prime8[6].name = "8-7";
        prime8[7].name = "8-8";
        prime8[8].name = "8-9";
        prime8[9].name = "8-10";
        prime8[10].name = "8-11";
        prime8[11].name = "8-12";
        prime8[12].name = "8-13";
        prime8[13].name = "8-14";
        prime8[14].name = "8-Z15";
        prime8[15].name = "8-16";
        prime8[16].name = "8-17";
        prime8[17].name = "8-18";
        prime8[18].name = "8-19";
        prime8[19].name = "8-20";
        prime8[20].name = "8-21";
        prime8[21].name = "8-22";
        prime8[22].name = "8-23";
        prime8[23].name = "8-24";
        prime8[24].name = "8-25";
        prime8[25].name = "8-26";
        prime8[26].name = "8-27";
        prime8[27].name = "8-28";
        prime8[28].name = "8-Z29";

        prime8[0].description = "8-1";
        prime8[1].description = "8-2";
        prime8[2].description = "8-3";
        prime8[3].description = "8-4";
        prime8[4].description = "8-5";
        prime8[5].description = "8-6";
        prime8[6].description = "8-7";
        prime8[7].description = "8-8";
        prime8[8].description = "8-9";
        prime8[9].description = "8-10";
        prime8[10].description = "8-11";
        prime8[11].description = "8-12";
        prime8[12].description = "8-13";
        prime8[13].description = "8-14";
        prime8[14].description = "8-Z15";
        prime8[15].description = "8-16";
        prime8[16].description = "8-17";
        prime8[17].description = "8-18";
        prime8[18].description = "8-19";
        prime8[19].description = "8-20";
        prime8[20].description = "8-21";
        prime8[21].description = "8-22";
        prime8[22].description = "8-23";
        prime8[23].description = "8-24";
        prime8[24].description = "8-25";
        prime8[25].description = "8-26";
        prime8[26].description = "8-27";
        prime8[27].description = "8-28";
        prime8[28].description = "8-Z29";

		prime8[0].tntnitype = new int[] { 0, 1, 2, 3, 4, 5, 6, 7 };
		prime8[1].tntnitype = new int[] { 0, 1, 2, 3, 4, 5, 6, 8 };
		prime8[2].tntnitype = new int[] { 0, 1, 2, 3, 4, 5, 6, 9 };
		prime8[3].tntnitype = new int[] { 0, 1, 2, 3, 4, 5, 7, 8 };
		prime8[4].tntnitype = new int[] { 0, 1, 2, 3, 4, 6, 7, 8 };
		prime8[5].tntnitype = new int[] { 0, 1, 2, 3, 5, 6, 7, 8 };
		prime8[6].tntnitype = new int[] { 0, 1, 2, 3, 4, 5, 8, 9 };
		prime8[7].tntnitype = new int[] { 0, 1, 2, 3, 4, 7, 8, 9 };
		prime8[8].tntnitype = new int[] { 0, 1, 2, 3, 6, 7, 8, 9 };
		prime8[9].tntnitype = new int[] { 0, 2, 3, 4, 5, 6, 7, 9 };
		prime8[10].tntnitype = new int[] { 0, 1, 2, 3, 4, 5, 7, 9 };
		prime8[11].tntnitype = new int[] { 0, 1, 3, 4, 5, 6, 7, 9 };
		prime8[12].tntnitype = new int[] { 0, 1, 2, 3, 4, 6, 7, 9 };
		prime8[13].tntnitype = new int[] { 0, 1, 2, 4, 5, 6, 7, 9 };
		prime8[14].tntnitype = new int[] { 0, 1, 2, 3, 4, 6, 8, 9 };
		prime8[15].tntnitype = new int[] { 0, 1, 2, 3, 5, 7, 8, 9 };
		prime8[16].tntnitype = new int[] { 0, 1, 3, 4, 5, 6, 8, 9 };
		prime8[17].tntnitype = new int[] { 0, 1, 2, 3, 5, 6, 8, 9 };
		prime8[18].tntnitype = new int[] { 0, 1, 2, 4, 5, 6, 8, 9 };
		prime8[19].tntnitype = new int[] { 0, 1, 2, 4, 5, 7, 8, 9 };
		prime8[20].tntnitype = new int[] { 0, 1, 2, 3, 4, 6, 8, 10 };
		prime8[21].tntnitype = new int[] { 0, 1, 2, 3, 5, 6, 8, 10 };
		prime8[22].tntnitype = new int[] { 0, 1, 2, 3, 5, 7, 8, 10 };
		prime8[23].tntnitype = new int[] { 0, 1, 2, 4, 5, 6, 8, 10 };
		prime8[24].tntnitype = new int[] { 0, 1, 2, 4, 6, 7, 8, 10 };
		prime8[25].tntnitype = new int[] { 0, 1, 2, 4, 5, 7, 9, 10 };
		prime8[26].tntnitype = new int[] { 0, 1, 2, 4, 5, 7, 8, 10 };
		prime8[27].tntnitype = new int[] { 0, 1, 3, 4, 6, 7, 9, 10 };
		prime8[28].tntnitype = new int[] { 0, 1, 2, 3, 5, 6, 7, 9 };

		prime8[0].ivector = new int[] { 7, 6, 5, 4, 4, 2 };
		prime8[1].ivector = new int[] { 6, 6, 5, 5, 4, 2 };
		prime8[2].ivector = new int[] { 6, 5, 6, 5, 4, 2 };
		prime8[3].ivector = new int[] { 6, 5, 5, 5, 5, 2 };
		prime8[4].ivector = new int[] { 6, 5, 4, 5, 5, 3 };
		prime8[5].ivector = new int[] { 6, 5, 4, 4, 6, 3 };
		prime8[6].ivector = new int[] { 6, 4, 5, 6, 5, 2 };
		prime8[7].ivector = new int[] { 6, 4, 4, 5, 6, 3 };
		prime8[8].ivector = new int[] { 6, 4, 4, 4, 6, 4 };
		prime8[9].ivector = new int[] { 5, 6, 6, 4, 5, 2 };
		prime8[10].ivector = new int[] { 5, 6, 5, 5, 5, 2 };
		prime8[11].ivector = new int[] { 5, 5, 6, 5, 4, 3 };
		prime8[12].ivector = new int[] { 5, 5, 6, 4, 5, 3 };
		prime8[13].ivector = new int[] { 5, 5, 5, 5, 6, 2 };
		prime8[14].ivector = new int[] { 5, 5, 5, 5, 5, 3 };
		prime8[15].ivector = new int[] { 5, 5, 4, 5, 6, 3 };
		prime8[16].ivector = new int[] { 5, 4, 6, 6, 5, 2 };
		prime8[17].ivector = new int[] { 5, 4, 6, 5, 5, 3 };
		prime8[18].ivector = new int[] { 5, 4, 5, 7, 5, 2 };
		prime8[19].ivector = new int[] { 5, 4, 5, 6, 6, 2 };
		prime8[20].ivector = new int[] { 4, 7, 4, 6, 4, 3 };
		prime8[21].ivector = new int[] { 4, 6, 5, 5, 6, 2 };
		prime8[22].ivector = new int[] { 4, 6, 5, 4, 7, 2 };
		prime8[23].ivector = new int[] { 4, 6, 4, 7, 4, 3 };
		prime8[24].ivector = new int[] { 4, 6, 4, 6, 4, 4 };
		prime8[25].ivector = new int[] { 4, 5, 6, 5, 6, 2 };
		prime8[26].ivector = new int[] { 4, 5, 6, 5, 5, 3 };
		prime8[27].ivector = new int[] { 4, 4, 8, 4, 4, 4 };
		prime8[28].ivector = new int[] { 5, 5, 5, 5, 5, 3 };

		prime8[0].symmetry = new int[] { 1, 1 };
		prime8[1].symmetry = new int[] { 1, 0 };
		prime8[2].symmetry = new int[] { 1, 1 };
		prime8[3].symmetry = new int[] { 1, 0 };
		prime8[4].symmetry = new int[] { 1, 0 };
		prime8[5].symmetry = new int[] { 1, 1 };
		prime8[6].symmetry = new int[] { 1, 1 };
		prime8[7].symmetry = new int[] { 1, 1 };
		prime8[8].symmetry = new int[] { 2, 2 };
		prime8[9].symmetry = new int[] { 1, 1 };
		prime8[10].symmetry = new int[] { 1, 0 };
		prime8[11].symmetry = new int[] { 1, 0 };
		prime8[12].symmetry = new int[] { 1, 0 };
		prime8[13].symmetry = new int[] { 1, 0 };
		prime8[14].symmetry = new int[] { 1, 0 };
		prime8[15].symmetry = new int[] { 1, 0 };
		prime8[16].symmetry = new int[] { 1, 1 };
		prime8[17].symmetry = new int[] { 1, 0 };
		prime8[18].symmetry = new int[] { 1, 0 };
		prime8[19].symmetry = new int[] { 1, 1 };
		prime8[20].symmetry = new int[] { 1, 1 };
		prime8[21].symmetry = new int[] { 1, 0 };
		prime8[22].symmetry = new int[] { 1, 1 };
		prime8[23].symmetry = new int[] { 1, 1 };
		prime8[24].symmetry = new int[] { 2, 2 };
		prime8[25].symmetry = new int[] { 1, 1 };
		prime8[26].symmetry = new int[] { 1, 0 };
		prime8[27].symmetry = new int[] { 4, 4 };
		prime8[28].symmetry = new int[] { 1, 0 };
	}

	public void initPrime9() {

		for (int i = 0; i < 12; i++)
			prime9[i] = new Set(9);

		prime9[0].name = "9-1";
		prime9[1].name = "9-2";
		prime9[2].name = "9-3";
		prime9[3].name = "9-4";
		prime9[4].name = "9-5";
		prime9[5].name = "9-6";
		prime9[6].name = "9-7";
		prime9[7].name = "9-8";
		prime9[8].name = "9-9";
		prime9[9].name = "9-10";
		prime9[10].name = "9-11";
		prime9[11].name = "9-12";

        prime9[0].description = "9-1";
        prime9[1].description = "9-2";
        prime9[2].description = "9-3";
        prime9[3].description = "9-4";
        prime9[4].description = "9-5";
        prime9[5].description = "9-6";
        prime9[6].description = "9-7";
        prime9[7].description = "9-8";
        prime9[8].description = "9-9";
        prime9[9].description = "9-10";
        prime9[10].description = "9-11";
        prime9[11].description = "9-12";

		prime9[0].tntnitype = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
		prime9[1].tntnitype = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 9 };
		prime9[2].tntnitype = new int[] { 0, 1, 2, 3, 4, 5, 6, 8, 9 };
		prime9[3].tntnitype = new int[] { 0, 1, 2, 3, 4, 5, 7, 8, 9 };
		prime9[4].tntnitype = new int[] { 0, 1, 2, 3, 4, 6, 7, 8, 9 };
		prime9[5].tntnitype = new int[] { 0, 1, 2, 3, 4, 5, 6, 8, 10 };
		prime9[6].tntnitype = new int[] { 0, 1, 2, 3, 4, 5, 7, 8, 10 };
		prime9[7].tntnitype = new int[] { 0, 1, 2, 3, 4, 6, 7, 8, 10 };
		prime9[8].tntnitype = new int[] { 0, 1, 2, 3, 5, 6, 7, 8, 10 };
		prime9[9].tntnitype = new int[] { 0, 1, 2, 3, 4, 6, 7, 9, 10 };
		prime9[10].tntnitype = new int[] { 0, 1, 2, 3, 5, 6, 7, 9, 10 };
		prime9[11].tntnitype = new int[] { 0, 1, 2, 4, 5, 6, 8, 9, 10 };

		prime9[0].ivector = new int[] { 8, 7, 6, 6, 6, 3 };
		prime9[1].ivector = new int[] { 7, 7, 7, 6, 6, 3 };
		prime9[2].ivector = new int[] { 7, 6, 7, 7, 6, 3 };
		prime9[3].ivector = new int[] { 7, 6, 6, 7, 7, 3 };
		prime9[4].ivector = new int[] { 7, 6, 6, 6, 7, 4 };
		prime9[5].ivector = new int[] { 6, 8, 6, 7, 6, 3 };
		prime9[6].ivector = new int[] { 6, 7, 7, 6, 7, 3 };
		prime9[7].ivector = new int[] { 6, 7, 6, 7, 6, 4 };
		prime9[8].ivector = new int[] { 6, 7, 6, 6, 8, 3 };
		prime9[9].ivector = new int[] { 6, 6, 8, 6, 6, 4 };
		prime9[10].ivector = new int[] { 6, 6, 7, 7, 7, 3 };
		prime9[11].ivector = new int[] { 6, 6, 6, 9, 6, 3 };

		prime9[0].symmetry = new int[] { 1, 1 };
		prime9[1].symmetry = new int[] { 1, 0 };
		prime9[2].symmetry = new int[] { 1, 0 };
		prime9[3].symmetry = new int[] { 1, 0 };
		prime9[4].symmetry = new int[] { 1, 0 };
		prime9[5].symmetry = new int[] { 1, 1 };
		prime9[6].symmetry = new int[] { 1, 0 };
		prime9[7].symmetry = new int[] { 1, 0 };
		prime9[8].symmetry = new int[] { 1, 1 };
		prime9[9].symmetry = new int[] { 1, 1 };
		prime9[10].symmetry = new int[] { 1, 0 };
		prime9[11].symmetry = new int[] { 3, 3 };
	}

}
