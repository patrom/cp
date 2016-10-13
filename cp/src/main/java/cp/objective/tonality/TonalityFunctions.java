package cp.objective.tonality;

import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

import java.util.*;
import java.util.logging.Logger;

public class TonalityFunctions {
	
	private static Logger LOGGER = Logger.getLogger(TonalityFunctions.class.getName());
	
	public static final double[] vectorMajorTemplate;
	public static final double[] vectorMinorTemplate;
	
	static {
		vectorMajorTemplate = new double[12];
		vectorMajorTemplate[0] = 6.35;
		vectorMajorTemplate[1] = 2.23;
		vectorMajorTemplate[2] = 3.48;
		vectorMajorTemplate[3] = 2.33;
		vectorMajorTemplate[4] = 4.38;
		vectorMajorTemplate[5] = 4.09;
		vectorMajorTemplate[6] = 2.52;
		vectorMajorTemplate[7] = 5.19;
		vectorMajorTemplate[8] = 2.39;
		vectorMajorTemplate[9] = 3.66;
		vectorMajorTemplate[10] = 2.29;
		vectorMajorTemplate[11] = 2.88;
		vectorMinorTemplate = new double[12];
		vectorMinorTemplate[0] = 6.33;
		vectorMinorTemplate[1] = 2.68;
		vectorMinorTemplate[2] = 3.52;
		vectorMinorTemplate[3] = 5.38;
		vectorMinorTemplate[4] = 2.60;
		vectorMinorTemplate[5] = 3.53;
		vectorMinorTemplate[6] = 2.54;
		vectorMinorTemplate[7] = 4.75;
		vectorMinorTemplate[8] = 3.98;
		vectorMinorTemplate[9] = 2.69;
		vectorMinorTemplate[10] = 3.34;
		vectorMinorTemplate[11] = 3.17;
	}

	public static Integer checkMajorTonality(Note[] melody) {
		double[] melodyVector = createVector(melody);
		return getTonality(vectorMajorTemplate, melodyVector);
	}

	public static Integer checkMinorTonality(Note[] melody) {
		double[] melodyVector = createVector(melody);
		return getTonality(vectorMinorTemplate, melodyVector);
	}

	private static Integer getTonality(double[] template, double[] melodyVector) {
		Map<Integer, Double> map = getCorrelationMap(template, melodyVector);
		Double max = getMaximumCorrelation(template, melodyVector);
		for (Integer key : map.keySet()) {
			Double corr = map.get(key);
			if (corr.equals(max)) {
				return key;
			}
		}
		return null;
	}

	
	public static double getMaxCorrelationTonality(List<MelodyBlock> melodyBlocks,
			double[] template) {
		double[] durationVector = new double[12];
		for (MelodyBlock melodyBlock: melodyBlocks) {
			List<cp.model.note.Note> notePositions = melodyBlock.getMelodyBlockNotes();
			for (cp.model.note.Note note : notePositions) {
				int pitchClass = note.getPitch() % 12;
				if (pitchClass < 0) {
					System.out.println("pcsdfsdf sd");
				}
				durationVector[pitchClass] = durationVector[pitchClass]
						+ note.getLength();
			}
		}
		return getMaximumCorrelation(template, durationVector);
	}

//	public static double getMaxCorrelationTonality(
//			List<CpMelody> structures, double[] template,
//			double[] context) {
//		double[] durationVector = new double[12];
//		for (CpMelody musicalStructure : structures) {
//			List<Note> notePositions = musicalStructure.getNotesNoRest();
//			for (Note note : notePositions) {
//				if (!note.isRest()) {
//					double registerValue = calculateRegisterValue(note.getPitch());
//					double rhythmValue = note.getRhythmValue();
//					durationVector[note.getPitchClass()] = durationVector[note.getPitchClass()]
//							+ (rhythmValue * context[note.getPosition()])
//							* registerValue;
//				}
//			}
//		}
//		LOGGER.finest("Tonality vector: " + Arrays.toString(durationVector));
//		Double max = getMaximumCorrelation(template, durationVector);
//		return max;
//	}
	
	public static double calculateRegisterValue(double pitch){
		return 1 - (pitch / 100);
	}

//	public static double getMaxCorrelationTonality(
//			List<CpMelody> melodies, double[] template) {
//		double[] durationVector = new double[12];
//		for (CpMelody melody : melodies) {
//			List<Note> notes = melody.getNotesNoRest();
//			for (Note note : notes) {
//				if (!note.isRest()) {
//					double registerValue = 1 - (note.getPitch() / 100d);
//					double length = note.getLength();
//					double weight = note.getPositionWeight();
//					durationVector[note.getPitchClass()] = durationVector[note.getPitchClass()]
//							+ (length * registerValue * weight);
//				}
//			}
//		}
//		LOGGER.finer(Arrays.toString(durationVector));
//		Double max = getMaximumCorrelation(template, durationVector);
//		return max;
//	}

	private static double[] createVector(Note[] melody) {
		double[] durationVector = new double[12];
		for (int i = 0; i < melody.length; i++) {
			int pitchClass = melody[i].getPitchClass();
			durationVector[pitchClass] = durationVector[pitchClass]
					+ melody[i].getRhythmValue();
		}
		return durationVector;
	}

	private static Double getMaximumCorrelation(double[] vectorScale,
			double[] vectorWeights) {
		List<Double> correlations = new ArrayList<Double>();
		correlations.add(getPearsonCorrelation(vectorScale, vectorWeights));
		for (int i = 1; i < vectorWeights.length; i++) {
			rotate(vectorWeights);
			correlations.add(getPearsonCorrelation(vectorScale, vectorWeights));
		}
		Double maximumCorrelation = Collections.max(correlations);
		return maximumCorrelation;
	}

	private static Map<Integer, Double> getCorrelationMap(double[] vectorScale,
			double[] vectorWeights) {
		Map<Integer, Double> correlations = new TreeMap<Integer, Double>();
		correlations.put(60, getPearsonCorrelation(vectorScale, vectorWeights));
		for (int i = 1; i < vectorWeights.length; i++) {
			rotate(vectorWeights);
			correlations.put(60 + i,
					getPearsonCorrelation(vectorScale, vectorWeights));
		}
		return correlations;
	}

	private static double getPearsonCorrelation(double[] vectorTemplate,
			double[] melody) {
		return new PearsonsCorrelation().correlation(vectorTemplate, melody);
	}
	
	public static void rotate(double[] theArray) {
		double a = theArray[0];
		int i;
		for(i = 0; i < theArray.length-1; i++)
		theArray[i] = theArray[i+1];
		theArray[i]= a;
	}

}
