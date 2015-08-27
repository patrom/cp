package cp.model.note;

import java.util.Random;

public class Scale {
	
	public static final int[] 
            CHROMATIC_SCALE = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11},
            MAJOR_SCALE = {0, 2, 4, 5, 7, 9, 11},
            HARMONIC_MINOR_SCALE = {0, 2, 3, 5, 7, 8, 11},
            MELODIC_MINOR_SCALE = {0, 2, 3, 5, 7, 8, 9, 10, 11}, // mix of ascend and descend
            AEOLIAN_SCALE = {0, 2, 3, 5, 7, 8, 10},
            DORIAN_SCALE = {0, 2, 3, 5, 7, 9, 10},	
            LYDIAN_SCALE = {0, 2, 4, 6, 7, 9, 11},
            MIXOLYDIAN_SCALE = {0, 2, 4, 5, 7, 9, 10},
            PENTATONIC_SCALE = {0, 2, 4, 7, 9},
            BLUES_SCALE = {0, 2, 3, 4, 5, 7, 9, 10, 11},
            TURKISH_SCALE = {0, 1, 3, 5, 7, 10, 11},
            INDIAN_SCALE = {0, 1, 1, 4, 5, 8, 10},
            WHOLE_TONE_SCALE = {0, 2, 4, 6, 8, 10},
			PITCH_SET_0134 = {0, 1, 3, 4},
			PITCH_SET_FOURTH = {0, 5, 7, 10},
			MODULATION_DOM = {0, 2, 4, 5, 6, 7, 9, 11},
			ALL_INTERVAL_TRETRACHORD1 = {0, 1, 4, 6},
			ALL_INTERVAL_TRETRACHORD2 = {0, 1, 3, 7},
			ALL_INTERVAL_HEXACHORD = {0, 1, 2, 4, 7, 8},
			HEXATONIC = {0, 3, 4, 7, 8, 11},
			
			OCTATCONIC_WHOLE = {0, 2, 3, 5, 6, 8, 9, 11},
			OCTATCONIC_HALF = {0, 1, 3, 4, 6, 7,  9, 10},
			ACOUSTIC =  {0, 2, 4, 6, 7, 9, 10};
	
	private Random random = new Random(System.currentTimeMillis());
	private int[] scale;
	
	public Scale() {
	}
	
	public Scale(int[] scale) {
		this.scale = scale;
	}

	public int pickRandomPitchClass(){
	    int index = random.nextInt(scale.length);
	    return scale[index];
	}
	
	public int transposePitchClass(int pitchClass, int steps){
		for (int i = 0; i < scale.length; i++) {
			if (pitchClass  == scale[i]) {
				int index = (i + steps)% scale.length;
				return scale[index];
			}
		}
		return pitchClass;
	}
	
	public int pickNextPitchFromScale(int pitchClass){
		for (int i = 0; i < scale.length; i++) {
			if (pitchClass == scale[i]) {
				if (i == scale.length - 1) {
					return scale[0];//end of array, pick first
				} else {
					return scale[i + 1];
				}
			}
		}
		return pickNextPitchFromScale((pitchClass - 1) % 12);
	}
	
	public int pickPreviousPitchFromScale(int pitchClass){
		for (int i = 0; i < scale.length; i++) {
			if (pitchClass == scale[i]) {
				if (i == 0) {
					return scale[scale.length - 1];//begin of array, pick last
				} else {
					return scale[i - 1];
				}
			}
		}
		return pickPreviousPitchFromScale((pitchClass + 1) % 12);
	}
	
	public int pickLowerStepFromScale(int pitchClass, int lowerStep){
		for (int i = 0; i < scale.length; i++) {
			if (pitchClass == scale[i]) {
				int step = i - lowerStep;
				if (step < 0) {
					step = step + scale.length;
					return scale[step];
				} else {
					return scale[step];
				}
			}
		}
		return pitchClass;
	}

}
