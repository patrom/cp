package cp.model.note;

import org.apache.commons.lang.ArrayUtils;

import java.util.Random;

public class Scale {
	
	public static final Scale 
            CHROMATIC_SCALE = new Scale(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11}),
            MAJOR_SCALE = new Scale(new int[]{0, 2, 4, 5, 7, 9, 11}),
            HARMONIC_MINOR_SCALE = new Scale(new int[]{0, 2, 3, 5, 7, 8, 11}),
//            HARMONIC_MINOR_SCALE_VI = new Scale(new int[]{0, 2, 4, 5, 8, 9, 11}),
//            HARMONIC_MINOR_SCALE_II = new Scale(new int[]{1, 2, 4, 5, 7, 9, 10}),
            MELODIC_MINOR_SCALE = new Scale(new int[]{0, 2, 3, 5, 7, 8, 9, 10, 11}), // mix of ascend and descend
            AEOLIAN_SCALE = new Scale(new int[]{0, 2, 3, 5, 7, 8, 10}),
            DORIAN_SCALE = new Scale(new int[]{0, 2, 3, 5, 7, 9, 10}),	
            LYDIAN_SCALE = new Scale(new int[]{0, 2, 4, 6, 7, 9, 11}),
            MIXOLYDIAN_SCALE = new Scale(new int[]{0, 2, 4, 5, 7, 9, 10}),
            PENTATONIC_SCALE = new Scale(new int[]{0, 2, 4, 7, 9}),
            BLUES_SCALE = new Scale(new int[]{0, 2, 3, 4, 5, 7, 9, 10, 11}),
            TURKISH_SCALE = new Scale(new int[]{0, 1, 3, 5, 7, 10, 11}),
            INDIAN_SCALE = new Scale(new int[]{0, 1, 1, 4, 5, 8, 10}),
            WHOLE_TONE_SCALE = new Scale(new int[]{0, 2, 4, 6, 8, 10}),
			PITCH_SET_0134 = new Scale(new int[]{0, 1, 3, 4}),
			PITCH_SET_FOURTH = new Scale(new int[]{0, 5, 7, 10}),
			MODULATION_DOM = new Scale(new int[]{0, 2, 4, 5, 6, 7, 9, 11}),
			ALL_INTERVAL_TRETRACHORD1 = new Scale(new int[]{0, 1, 4, 6}),
			ALL_INTERVAL_TRETRACHORD2 = new Scale(new int[]{0, 1, 3, 7}),
			ALL_INTERVAL_HEXACHORD = new Scale(new int[]{0, 1, 2, 4, 7, 8}),
			HEXATONIC = new Scale(new int[]{0, 3, 4, 7, 8, 11}),
			
			OCTATCONIC_WHOLE = new Scale(new int[]{0, 2, 3, 5, 6, 8, 9, 11}),
			OCTATCONIC_HALF = new Scale(new int[]{0, 1, 3, 4, 6, 7,  9, 10}),
			ACOUSTIC =  new Scale(new int[]{0, 2, 4, 6, 7, 9, 10}),
	
			WEBERN_TRICHORD_1 = new Scale(new int[]{10, 11, 2}),//op. 24
			WEBERN_TRICHORD_2 = new Scale(new int[]{6, 7, 3}),
			WEBERN_TRICHORD_3 = new Scale(new int[]{4, 5 , 8}),
			WEBERN_TRICHORD_4 = new Scale(new int[]{0, 1, 9}),

			VARIATIONS_FOR_ORCHESTRA_OP31 = new Scale(new int[]{10,4,6,3,5,9,2,1,7,8,11,0}),
			VARIATIONS_FOR_ORCHESTRA_OP31_HEXA1 = new Scale(new int[]{10,4,6,3,5,9}),
			VARIATIONS_FOR_ORCHESTRA_OP31_HEXA2 = new Scale(new int[]{2,1,7,8,11,0}),

			MAJOR_CHORD = new Scale(new int[]{0, 4 , 7}),
			MINOR_CHORD = new Scale(new int[]{0, 3 , 7});

			
	private final Random random = new Random(System.currentTimeMillis());
	private final int[] scale;
	
	public Scale(int[] scale) {
		this.scale = scale;
	}

	public int pickRandomPitchClass(){
	    int index = random.nextInt(scale.length);
	    return scale[index];
	}
	
	public boolean contains(int pitchClass){
		return ArrayUtils.contains(scale, pitchClass);
	}
	
	public int getIndex(int pitchClass){
		for (int i = 0; i < scale.length; i++) {
			if (pitchClass  == scale[i]) {
				return i;
			}
		}
		throw new IllegalArgumentException("Scale doesn't contain pitchClass: " + pitchClass);
	}
	
	/**
	 * @param functionalDegreeCenter functional degree (I = 1, II = 2, ..., VII = 7)
	 * @param pitchClass pc to inverse
	 * @return inverse pc
	 */
	public int getInversedPitchClass(int functionalDegreeCenter, int pitchClass){
		if (functionalDegreeCenter < 1 || functionalDegreeCenter > 7) {
			throw new IllegalArgumentException("Unknown functional degree: " + functionalDegreeCenter);
		}
		int index = getIndex(pitchClass);
		int inversionIndex = (scale.length + (functionalDegreeCenter - 1) + (functionalDegreeCenter - 1 - index)) % scale.length;
		return scale[inversionIndex];
	}
	
	public int transposePitchClass(int pitchClass, int steps){
		for (int i = 0; i < scale.length; i++) {
			if (pitchClass  == scale[i]) {
				int index = (scale.length + (i + steps)) % scale.length;
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
		return pickNextPitchFromScale(pitchClass == 0?11:pitchClass - 1);
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
	
	public int[] getPitchClasses() {
		return scale;
	}

}
