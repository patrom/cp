package cp.model.harmony;

public enum ChordType {

	ALL(12),

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
	
	CH6(6);
	
	public enum Inversion {
		ROOT, INVERSION1, INVERSION2, OTHER
	}
	
	private final int size;
	
	ChordType(int size){
		this.size = size;
	}
	
	public int getSize() {
		return size;
	}
}
