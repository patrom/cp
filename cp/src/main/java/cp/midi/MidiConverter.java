package cp.midi;

import cp.model.note.Note;

import java.util.*;

import static java.util.stream.Collectors.toCollection;

public class MidiConverter {
	
	private static final double DEFAULT_WEIGHT = 0.25;
	private static Map<Integer, Double> fourFourOrTwoFourRhythmWeightValues = new TreeMap<>();
	private static Map<Integer, Double> fourThreeRhythmWeightValues = new TreeMap<>();
	private static Map<Integer, Double> SixEightRhythmWeightValues = new TreeMap<>();
	
	static{
		for (int i = 0; i < 96; i = i + 6) {
			if (i % 12 == 0) {
				fourFourOrTwoFourRhythmWeightValues.put(i, 1.0);
			} else {
				fourFourOrTwoFourRhythmWeightValues.put(i, 0.5);
			}
		}
		
		for (int i = 0; i < 72; i = i + 6) {
			if (i % 18 == 0) {
				fourThreeRhythmWeightValues.put(i, 1.0);
			} else {
				fourThreeRhythmWeightValues.put(i, 0.5);
			}
		}
		
		for (int i = 0; i < 144; i = i + 6) {
			if (i % 36 == 0) {
				SixEightRhythmWeightValues.put(i, 1.0);
			} else if (i % 18 == 0) {
				SixEightRhythmWeightValues.put(i, 0.75);
			} else {
				SixEightRhythmWeightValues.put(i, 0.5);
			}
		}
	}
	
	public static void updatePositionNotes(List<MelodyInstrument> melodies, String timeSignature){
		Map<Integer, Double> weights = null;
		if (timeSignature.equals("4/4") || timeSignature.equals("2/4") || timeSignature.equals("2/2")) {
			weights = fourFourOrTwoFourRhythmWeightValues;
		} else if (timeSignature.equals("3/4")) {
			weights = fourThreeRhythmWeightValues;
		} else if (timeSignature.equals("6/8") || timeSignature.equals("6/4")) {
			weights = SixEightRhythmWeightValues;
		} else {
			throw new IllegalArgumentException("Weights time signature not implemented :" + timeSignature);
		}
		for (MelodyInstrument melody : melodies) {
			List<Note> notes = melody.getNotes();
			for (Note notePos : notes) {
				if (weights.containsKey(notePos.getPosition())) {
					notePos.setPositionWeight(weights.get(notePos.getPosition()));
				} else{ //set default value
					notePos.setPositionWeight(DEFAULT_WEIGHT);
				}
			}
		}
	}

	public static Map<Integer, List<Note>> extractNoteMapFromMelodies(List<MelodyInstrument> melodies) {
		Map<Integer, List<Note>> chords = new TreeMap<>();
		Set<Integer> positions = melodies.stream().flatMap(m -> m.getNotes().stream())
											.map(note -> note.getPosition())
											.collect(toCollection(TreeSet::new));
		int voice = 0;
		for (MelodyInstrument melody : melodies) {
			List<Note> notes = melody.getNotes();
			int melodyLength = notes.size() - 1;
			Iterator<Integer> iterator = positions.iterator();
			Integer position = iterator.next();
			for (int i = 0; i < melodyLength; i++) {
				Note firstNote = notes.get(i);
				Note secondNote = notes.get(i + 1);			
				while (position < secondNote.getPosition()) {
					addNoteToChordMap(chords, firstNote, voice);
					position = iterator.next();
				}
			}
			if (melodyLength > 0) {
				Note lastNote = notes.get(melodyLength);
				addNoteToChordMap(chords, lastNote, voice);
			}
			voice++;
		}
		return chords;
	}
	

	private static void addNoteToChordMap(Map<Integer, List<Note>> chords, Note note,
			 int voice) {
		int position = note.getPosition();
		List<Note> chord = null;
		if (chords.containsKey(position)) {
			chord = chords.get(position);
		} else {
			chord = new ArrayList<>();
		}
		Note notePos = new Note(note.getPitchClass(), voice , position, note.getLength());
		notePos.setPitch(note.getPitch());
		chord.add(notePos);
		chords.put(position, chord);
	}
	
}
