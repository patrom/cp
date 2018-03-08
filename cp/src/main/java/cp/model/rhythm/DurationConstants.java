package cp.model.rhythm;

public interface DurationConstants {

	int SIXTEENTH = 30;
	int EIGHT = 2 * SIXTEENTH;
	int THREE_SIXTEENTH = 3 * SIXTEENTH;
	int QUARTER = 4 * SIXTEENTH;
	int THREE_EIGHTS = 3 * EIGHT;
	int THREE_QUARTERS = 3 * QUARTER;
	int HALF = 8 * SIXTEENTH;
	int SIX_EIGHTS = 2 * THREE_EIGHTS;
	int NINE_EIGHTS = 3 * THREE_EIGHTS;
	int WHOLE = 16 * SIXTEENTH;
	
	int SIXTEENTH_TRIPLET = 20;
	int EIGHT_TRIPLET = 2 * SIXTEENTH_TRIPLET;
	int QUARTER_TRIPLET = 4 * SIXTEENTH_TRIPLET;
	int HALF_TRIPLET = 8 * SIXTEENTH_TRIPLET;
	
	int SIXTEENTH_QUINTUPLET = 24;
	int SIXTEENTH_SEPTUPLET = 17;
	int EIGHT_QUINTUPLET = 48;

	int TIMING = 3;
	int DURATION = 4;

}
