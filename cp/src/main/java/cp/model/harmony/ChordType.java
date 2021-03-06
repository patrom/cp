package cp.model.harmony;

public enum ChordType {

	SYMMETRY(-1),
	TWELVE_TONE(12),
	SETCLASS(100),
	SETCLASS_COMPOSITION(101),

	ALL_INTERVALS(2),
	NO_INTERVALS(0),

	CH0(0),
	CH1(1),
	
	CH2_KLEINE_SECONDE(2),
	CH2_GROTE_SECONDE(2),
	CH2_KLEINE_TERTS(2),
	CH2_GROTE_TERTS(2),
	CH2_KWART(2),
	CH2_TRITONE(2),
	CH2_KWINT(2),
	CH2_KLEINE_SIXT(2),
	CH2_GROTE_SIXT(2),
	CH2_KLEIN_SEPTIEM(2),
	CH2_GROOT_SEPTIEM(2),

	ANCHOR_7(2),
	ANCHOR_10(2),
	ANCHOR_11(2),
	
	CH3(3),
	MAJOR(3),
	MAJOR_1(3),
	MAJOR_2(3),
	MINOR(3),
	MINOR_1(3),
	MINOR_2(3),
	DIM(3),
	AUGM(3),
	DOM(3),
	ADD9(3),
	MINOR7_OMIT5(3),
	MINOR7_OMIT5_1(3),
	MAJOR7_OMIT5(3),
	KWARTEN(3),

	ANCHOR_38_MAJ(3),
	ANCHOR_59_MAJ(3),
	ANCHOR_49_MIN(3),
	ANCHOR_58_MIN(3),
	ANCHOR_68_DOM(3),
	ANCHOR_35_DOM(3),
	ANCHOR_26_DOM(3),
	ANCHOR_29_DOM(3),
	ANCHOR_46(3),
	ANCHOR_25(3),
	ANCHOR_28(3),
	ANCHOR_87_MAJ7(3),
	ANCHOR_54_MAJ7(3),
	ANCHOR_36_DIM(3),
	ANCHOR_39_DIM(3),
	ANCHOR_69_DIM(3),

	//Interval between octave (texture)
//	CH2_KLEINE_SECONDE_OCTAVE(3),
//	CH2_GROTE_SECONDE_OCTAVE(3),
//	CH2_KLEINE_TERTS_OCTAVE(3),
	CH2_GROTE_TERTS_OCTAVE(3),
	CH2_KWART_OCTAVE(3),
//	CH2_TRITONE_OCTAVE(3),
	CH2_KWINT_OCTAVE(3),
//	CH2_KLEINE_SIXT_OCTAVE(3),
	CH2_GROTE_SIXT_OCTAVE(3),
//	CH2_KLEIN_SEPTIEM_OCTAVE(3),
//	CH2_GROOT_SEPTIEM_OCTAVE(3),

	CH4(4),
	MAJOR7(4),
	MAJOR7_1(4),
	MAJOR7_2(4),
	MAJOR7_3(4),
	MINOR7(4),
	MINOR7_1(4),
	MINOR7_2(4),
	MINOR7_3(4),
	DOM7(4),
	HALFDIM7(4),
	DIM7(4),
	
	CH5(5),
	
	CH6(6),
	CH7(7),
	CH8(8),
	CH9(9),
	CH10(10),
	CH11(11),

	//chromatic
	CH2_KLEINE_SECONDE_CHR(2,1),
	CH2_GROTE_SECONDE_CHR(2,2),
	CH2_KLEINE_TERTS_CHR(2,3),
	CH2_GROTE_TERTS_CHR(2,4),
	CH2_KWART_CHR(2,5),
	CH2_TRITONE_CHR(2,6),
	CH2_KWINT_CHR(2,7),
	CH2_KLEINE_SIXT_CHR(2,8),
	CH2_GROTE_SIXT_CHR(2,9),
	CH2_KLEIN_SEPTIEM_CHR(2,10),
	CH2_GROOT_SEPTIEM_CHR(2,11),
	CH2_OCTAVE(2,12),

	MAJOR_CHR(3),
	MAJOR_1_CHR(3),
	MAJOR_2_CHR(3),
	MINOR_CHR(3),
	MINOR_1_CHR(3),
	MINOR_2_CHR(3),
	DOM_CHR_1(3),
	DOM_CHR_2(3);

	public enum Inversion {
		ROOT, INVERSION1, INVERSION2, OTHER
	}
	
	private final int size;
	private int interval;
	
	ChordType(int size){
		this.size = size;
	}

	ChordType(int size, int interval){
		this.size = size;
		this.interval = interval;
	}
	
	public int getSize() {
		return size;
	}

	public int getInterval() {
		return interval;
	}

	public static ChordType getChordType(int interval){
		switch (interval){
			case 1:
				return CH2_KLEINE_SECONDE_CHR;
			case 2:
				return CH2_GROTE_SECONDE_CHR;
			case 3:
				return CH2_KLEINE_TERTS_CHR;
			case 4:
				return CH2_GROTE_TERTS_CHR;
			case 5:
				return CH2_KWART_CHR;
			case 6:
				return CH2_TRITONE_CHR;
			case 7:
				return CH2_KWINT_CHR;
			case 8:
				return CH2_KLEINE_SIXT_CHR;
			case 9:
				return CH2_GROTE_SIXT_CHR;
			case 10:
				return CH2_KLEIN_SEPTIEM_CHR;
			case 11:
				return CH2_GROOT_SEPTIEM_CHR;
			default:
				throw new IllegalArgumentException("Chord type not found for interval: " + interval);
		}
	}
}
