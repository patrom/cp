package cp.out.print;

import cp.model.rhythm.DurationConstants;

public enum NoteType {

	sixteenth("16th", false, false, DurationConstants.SIXTEENTH), eighth("eighth", false, false, DurationConstants.EIGHT), quarter("quarter", false, false, DurationConstants.QUARTER), half("half", false, false, DurationConstants.HALF), whole("whole", false, false, DurationConstants.WHOLE),
	eighthDot("eighth", true, false, DurationConstants.THREE_SIXTEENTH), quarterDot("quarter", true, false, DurationConstants.THREE_EIGHTS), halfDot("half", true, false, DurationConstants.SIX_EIGHTS), 
	sixteenthTriplet("16th", false, true, DurationConstants.SIXTEENTH_TRIPLET), eighthTriplet("eighth", false, true, DurationConstants.EIGHT_TRIPLET),quarterTriplet("quarter", false, true, DurationConstants.QUARTER_TRIPLET), halfTriplet("half", false, true, DurationConstants.HALF_TRIPLET),
    breve("breve", false, false, DurationConstants.WHOLE);
	private final String name;
	private final boolean dot;
	private final boolean triplet;
	private final int length;
	
	NoteType(String name, boolean dot, boolean triplet, int length) {
		this.name = name;
		this.dot = dot;
		this.triplet = triplet;
		this.length = length;
	}
	
	public static NoteType getNoteType(int length) {
		switch (length) {
			case 64:
			case 51://quintuplet
				return sixteenth;
			case 128:
			case 102://quintuplet
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
		return breve;
//		throw new IllegalArgumentException("MusicXML note type not defined for length: " + length);
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
