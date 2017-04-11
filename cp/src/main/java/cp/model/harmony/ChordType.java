package cp.model.harmony;

public enum ChordType {

	ALL_INTERVALS(2),

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

	MAJOR_1_CHR(3),
	MAJOR_2_CHR(3),
	MINOR_1_CHR(3),
	MINOR_2_CHR(3);

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
}
