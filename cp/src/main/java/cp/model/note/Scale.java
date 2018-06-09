package cp.model.note;

import cp.util.RandomUtil;
import org.apache.commons.lang.ArrayUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static java.util.stream.Collectors.toList;

public class Scale {
	
	public static final Scale 
            CHROMATIC_SCALE = new Scale(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11}),
            MAJOR_SCALE = new Scale(new int[]{0, 2, 4, 5, 7, 9, 11}),
            HARMONIC_MINOR_SCALE = new Scale(new int[]{0, 2, 3, 5, 7, 8, 11}),
//            HARMONIC_MINOR_SCALE_VI = new Scale(new int[]{0, 2, 4, 5, 8, 9, 11}),
//            HARMONIC_MINOR_SCALE_II = new Scale(new int[]{1, 2, 4, 5, 7, 9, 10}),
            MELODIC_MINOR_SCALE = new Scale(new int[]{0, 2, 3, 5, 7, 9, 11}),
            AEOLIAN_SCALE = new Scale(new int[]{0, 2, 3, 5, 7, 8, 10}),
            DORIAN_SCALE = new Scale(new int[]{0, 2, 3, 5, 7, 9, 10}),	
            LYDIAN_SCALE = new Scale(new int[]{0, 2, 4, 6, 7, 9, 11}),
            MIXOLYDIAN_SCALE = new Scale(new int[]{0, 2, 4, 5, 7, 9, 10}),
            PENTATONIC_SCALE_MAJOR = new Scale(new int[]{0, 2, 4, 7, 9}),
            PENTATONIC_SCALE_MINOR = new Scale(new int[]{0, 3, 5, 7, 10}),
            BLUES_SCALE = new Scale(new int[]{0, 2, 3, 4, 5, 7, 9, 10, 11}),
            TURKISH_SCALE = new Scale(new int[]{0, 1, 3, 5, 7, 10, 11}),
            INDIAN_SCALE = new Scale(new int[]{0, 1, 1, 4, 5, 8, 10}),
            WHOLE_TONE_SCALE_0 = new Scale(new int[]{0, 2, 4, 6, 8, 10}),
            WHOLE_TONE_SCALE_1 = new Scale(new int[]{1, 3, 5, 7, 9, 11}),
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

	TEST1 = new Scale(new int[]{0, 1,2}),

	TEST2 = new Scale(new int[]{4,5,6}),

			MAJOR_CHORD = new Scale(new int[]{0, 4 , 7}),
			MINOR_CHORD = new Scale(new int[]{0, 3 , 7}),
			HALF_DIMINISHED_CHORD = new Scale(new int[]{0, 3 , 6, 10}),
			DIMINISHED_CHORD = new Scale(new int[]{0, 3 , 6, 9}),
			CHORD = new Scale(new int[]{0, 1 , 5}),
			PEDAL = new Scale(new int[]{0}),

			DORIAN = new Scale(new int[]{0,2,3,5}),
					DORIAN_TRITIONE = new Scale(new int[]{3,5,6,8}),
			SYMMETRY_1 =  new Scale(new int[]{4,5,7,9}),
					SYMMETRY_2 =  new Scale(new int[]{10,0,2,3}),
			Z = new Scale(new int[]{10,3,4,9}),

			MINOR_PART =  new Scale(new int[]{0,2,3,5,7}),
			BARTOK_CONCERTO =  new Scale(new int[]{0,3,4,7,8,11}),
	ALL_COMBINATORIAL_HEXAHCORD_A = new Scale(new int[]{0,1,2,3,4,5}),//(012), (013), (014), (024)
	ALL_COMBINATORIAL_HEXAHCORD_B = new Scale(new int[]{0,2,3,4,5,7}),//(013), (015), (024), (025)
	ALL_COMBINATORIAL_HEXAHCORD_C = new Scale(new int[]{4, 5, 9, 7, 0, 2}),//(024), (025), (027), (037)
	ALL_COMBINATORIAL_HEXAHCORD_C_COMPLEMENT = new Scale(new int[]{11, 3, 1, 6, 8, 10}),

	ALL_COMBINATORIAL_HEXAHCORD_C_I = new Scale(new int[]{4, 3, 11, 1, 8, 6}),
			ALL_COMBINATORIAL_HEXAHCORD_C_I_COMPLEMENT = new Scale(new int[]{9, 5, 7, 2, 0, 10}),
			ALL_COMBINATORIAL_HEXAHCORD_C_R = new Scale(new int[]{10, 8, 6, 1, 3, 11}),
			ALL_COMBINATORIAL_HEXAHCORD_C_R_COMPLEMENT = new Scale(new int[]{2, 0, 7, 9, 5, 4}),
			ALL_COMBINATORIAL_HEXAHCORD_C_I3 = new Scale(new int[]{5, 4, 0, 2, 9, 7}),
	ALL_COMB_C_1  = new Scale(new int[]{4,5,9}),
	ALL_COMB_C_2  = new Scale(new int[]{7,0,2}),
	ALL_COMB_C_3  = new Scale(new int[]{6,8,10}),
	ALL_COMB_C_4  = new Scale(new int[]{11,3,1}),
// R	[10, 8, 6, 1, 3, 11, 2, 0, 7, 9, 5, 4]
//	I		[4, 3, 11, 1, 8, 6, 9, 5, 7, 2, 0, 10]
//	RI		[10, 0, 2, 7, 5, 9, 6, 8, 1, 11, 3, 4]

	// I3 [5, 4, 0, 2, 9, 7, 10, 6, 8, 3, 1, 11] T0  +   T6  or  I3 necessary to produce haxachordal complementation
	ALL_COMBINATORIAL_HEXAHCORD_D = new Scale(new int[]{0,1,2,6,7,8}),//(012), (015), (016), (027)
	ALL_COMBINATORIAL_HEXAHCORD_E = new Scale(new int[]{0,1,4,5,8,9}),//(014)3, (015), (037)3, (048)
	ALL_COMBINATORIAL_HEXAHCORD_F = new Scale(new int[]{0,2,4,6,8,10});//(024)3, (026), (048)

			
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
		throw new IllegalArgumentException("Scale doesn't contain pitchClass: " + pitchClass + ", scale: " + Arrays.toString(scale));
	}
	
	/**
	 * @param functionalDegreeCenter eg:functional degree (I = 1, II = 2, ..., VII = 7)
	 * @param pitchClass pc to inverse
	 * @return inverse pc
	 */
	public int getInversedPitchClass(int functionalDegreeCenter, int pitchClass){
		int index = getIndex(pitchClass);
		int inversionIndex = (scale.length + (functionalDegreeCenter - 1) + (functionalDegreeCenter - 1 - index)) % scale.length;
		return scale[inversionIndex];
	}

	public int getInversedPitchClassIndex(int functionalDegreeCenter, int index){
		int inversionIndex = (scale.length + (functionalDegreeCenter - 1) + (functionalDegreeCenter - 1 - index)) % scale.length;
		return scale[inversionIndex];
	}

	public int inverse(Scale oldScale, int pcOldScale, int functionalDegreeCenter){
		int indexPitchClass = oldScale.getIndex(pcOldScale);
		return this.getInversedPitchClassIndex(functionalDegreeCenter, indexPitchClass);
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

	public int pickHigerStepFromScale(int pitchClass, int higherStep){
		for (int i = 0; i < scale.length; i++) {
			if (pitchClass == scale[i]) {
				int step = (i + higherStep) % scale.length;
				return scale[step];
			}
		}
		return pickHigerStepFromScalePCNotInScale(pitchClass, higherStep);
	}

    public int pickHigerStepFromScalePCNotInScale(int pitchClass, int higherStep){
        int length = scale.length - 1;
        for (int i = 0; i < length; i++) {
            if (pitchClass > scale[i] && pitchClass < scale[i+1]) {
                int step = (i + higherStep) % scale.length;
                return scale[step];
            }
        }
        return pitchClass;
    }
	
	public int[] getPitchClasses() {
		return scale;
	}

    public int[] getPitchClassesRandomized() {
        int[] randomizedPitchClasses = new int[scale.length];
        List<Integer> allPitchClasses = Arrays.stream(scale).boxed().collect(toList());
        for (int i = 0; i < scale.length; i++) {
            Integer pitchClass = RandomUtil.getRandomFromList(allPitchClasses);
            randomizedPitchClasses[i] = pitchClass;
            allPitchClasses.remove(pitchClass);
        }
        return randomizedPitchClasses;
    }

}
