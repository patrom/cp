package cp.out.print;


public enum NoteType {

	sixteenth("16th", false, false, 3), eighth("eighth", false, false, 6), quarter("quarter", false, false, 12), half("half", false, false, 24), whole("whole", false, false, 48),
	eighthDot("eighth", true, false, 9), quarterDot("quarter", true, false, 18), halfDot("half", true, false, 36), 
	sixteenthTriplet("16th", false, true, 2), eighthTriplet("eighth", false, true, 4),quarterTriplet("quarter", false, true, 8), halfTriplet("half", false, true, 16);
	
	private String name;
	private boolean dot;
	private boolean triplet;
	private int length;
	
	private NoteType(String name, boolean dot, boolean triplet, int length) {
		this.name = name;
		this.dot = dot;
		this.triplet = triplet;
		this.length = length;
	}
	
	public static NoteType getNoteType(int length) {
		switch (length) {
			case 64:
				return sixteenth;
//			case 96://dot
//				return sixteenthDot;
			case 128:
				return eighth;
			case 192:
				return eighthDot;
			case 256:
				return quarter;
			case 384:
				return quarterDot;
			case 512:
				return half;
			case 768:
				return halfDot;
			case 1024:
				return whole;
//			case 1536:
//				return wholeDot;
		}
		//triplets
		if (40 <= length && length <= 45) {
			return sixteenthTriplet;
		} else if (80 <= length && length <= 90) {
			return eighthTriplet;
		} else if (160 <= length && length <= 180) {
			return quarterTriplet;
		} else if (320 <= length && length <= 360) {
			return NoteType.halfTriplet;
		}
		throw new IllegalArgumentException("MusicXML note type not defined for length: " + length);
	}

	public String getName() {
		return name;
	}

	public boolean isDot() {
		return dot;
	}

	public boolean isTriplet() {
		return triplet;
	}
	
	public int getLength(){
		return length;
	}
	
}
