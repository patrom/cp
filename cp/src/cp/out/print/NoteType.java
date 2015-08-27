package cp.out.print;

public enum NoteType {

	sixteenth("16th", false, false), eighth("eighth", false, false), quarter("quarter", false, false), half("half", false, false), whole("whole", false, false),
	sixteenthDot("16th", true, false), eighthDot("eighth", true, false), quarterDot("quarter", true, false), halfDot("half", true, false), wholeDot("whole", true, false),
	sixteenthTriplet("16th", false, true), eighthTriplet("eighth", false, true),quarterTriplet("quarter", false, true), halfTriplet("half", false, true), wholeTriplet("whole", false, true);
	
	private String name;
	private boolean dot;
	private boolean triplet;
	
	private NoteType(String name, boolean dot, boolean triplet) {
		this.name = name;
		this.dot = dot;
		this.triplet = triplet;
	}
	
	public static NoteType getNoteType(int length) {
		switch (length) {
			case 64:
				return sixteenth;
			case 96://dot
				return sixteenthDot;
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
			case 1536:
				return wholeDot;
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
}
