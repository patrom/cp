package cp.objective.melody;

import cp.composition.Composition;
import cp.model.Motive;
import cp.model.dissonance.Dissonance;
import cp.model.harmony.Chord;
import cp.model.melody.MelodyBlock;
import cp.model.note.Interval;
import cp.model.note.Note;
import cp.objective.Objective;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.*;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Component
public class MelodicObjective extends Objective {
	
	@Autowired
	@Qualifier(value="TonalDissonance")
	private Dissonance dissonance;

	@Autowired
	protected Composition composition;
	
	@Override
	public double evaluate(Motive motive) {
		double totalMelodySum = 0;
		List<MelodyBlock> melodies = motive.getMelodyBlocks();
		int melodyCount = melodies.size();
//		int beats = composition.getEnd()/ composition.getTimeConfig().getMinimumLength();
//		int minNotes = beats  / 3;
		for(MelodyBlock melody: melodies){
			List<Note> notes =  melody.getMelodyBlockNotes();
//			if(notes.size() < minNotes){
//				return 0;
//			}
			double melodyValue = evaluateMelody(notes, 2);
//			notes = extractNotesOnLevel(notes, 1);
			for (double level : musicProperties.getFilterLevels()) {
				List<Note> filteredNotes = filterNotesWithWeightEqualToOrGreaterThan(notes, level);
				if ((filteredNotes.size() > 1) && filteredNotes.size() != notes.size()) {
					double value = evaluateMelody(filteredNotes, 1);
					totalMelodySum = totalMelodySum + value;
					melodyCount++;
				}
			}
			totalMelodySum = totalMelodySum + melodyValue;
		}
		return totalMelodySum/melodyCount;
	}
	
	protected List<Note> filterNotesWithWeightEqualToOrGreaterThan(Collection<Note> notes, double filterValue){
		return notes.stream().filter(n -> n.getPositionWeight() > filterValue).collect(toList());
	}

	protected List<Note> extractNotesOnLevel(Collection<Note> notes, int level) {
		Map<Double, List<Note>> unsortMap = notes.stream().collect(groupingBy(n -> n.getBeat(musicProperties.getHarmonyBeatDivider() * level)));
		Map<Double, List<Note>> treeMap = new TreeMap<>(unsortMap);
		List<Note> notePosition = new ArrayList<>();
		for (List<Note> noteList : treeMap.values()) {
			Optional<Note> maxNote = noteList.stream().max(comparing(Note::getPositionWeight));
			if (maxNote.isPresent()) {
				notePosition.add(maxNote.get());
			}
		}
		return notePosition;
	}
	
	protected double evaluateTriadicValueMelody(Collection<Note> notes) {
		Note[] notePositions = notes.toArray(new Note[notes.size()]);
		double harmonicValue = 0;
		for (int i = 0; i < notePositions.length - 2; i++) {
				Note firstNote = notePositions[i];
				Note secondNote = notePositions[i + 1];
				Note thirdNote = notePositions[i + 2];
				Chord chord = new Chord(firstNote.getPitchClass());
				chord.addPitchClass(firstNote.getPitchClass());
				chord.addPitchClass(secondNote.getPitchClass());
				chord.addPitchClass(thirdNote.getPitchClass());
				if ("3-11".equals(chord.getForteName())) {
					harmonicValue = harmonicValue + dissonance.getDissonance(chord);
				}
		}
		return (harmonicValue == 0)? 0:harmonicValue/(notePositions.length - 2);
	}

	private double getIntervalMelodicValue(Note note, Note nextNote) {
		int difference = 0;
		if (note.getPitch() != 0 && nextNote.getPitch() != 0) {
			difference = nextNote.getPitch() - note.getPitch();
		}
//		else{
//			difference = note.getPitchClass() - nextNote.getPitchClass();
//		}	
		return Interval.getEnumInterval(difference).getMelodicValue();
	}
	
	public double evaluateMelody(List<Note> notes, int maxDistance) {
		if (notes.size() <= 1) {
			return Double.MIN_VALUE;
		}
		List<Note> pitches = extractDifferentPitches(notes);//repeated note intervals are not calculated!
		if (pitches.isEmpty()) {
			return 0.8;//only one note (pedal)
		}
		double totalPositionWeight = getTotalPositionWeiht(pitches);
		Note[] notePositions = pitches.toArray(new Note[pitches.size()]);
		double melodyIntervalValueSum = 0;
		for (int distance = 1; distance <= maxDistance; distance++) {
			for (int j = 0; j < notePositions.length - distance; j++) {
				Note note = notePositions[j];
				Note nextNote = notePositions[j + distance];
				double intervalPositionWeight = (note.getPositionWeight() + nextNote.getPositionWeight())/totalPositionWeight;
				double intervalMelodicValue = getIntervalMelodicValue(note, nextNote);
				double intervalValue = intervalMelodicValue * intervalPositionWeight;
				melodyIntervalValueSum = melodyIntervalValueSum + intervalValue;
			}
		}
		return melodyIntervalValueSum/maxDistance;
	}
	
	private double getTotalPositionWeiht(List<Note> notes){
		double sumPositionWeights = notes.stream().mapToDouble(n -> n.getPositionWeight()).sum();
        return (sumPositionWeights * 2) - notes.get(0).getPositionWeight() - notes.get(notes.size() - 1).getPositionWeight();
	}
	
	public List<Note> filterKeelNotes(List<Note> notes){
		return notes.stream().filter(n -> n.isKeel()).collect(toList());
	}
	
	public List<Note> filterCrestNotes(List<Note> notes){
		return notes.stream().filter(n -> n.isCrest()).collect(toList());
	}
	
	public List<Note> extractDifferentPitches(List<Note> notes) {
		List<Note> pitches = new ArrayList<>();
		int prevPitch = 0;
		for (Note note : notes) {
			if (note.getPitch() != prevPitch) {
				pitches.add(note);
			}
			prevPitch = note.getPitch();
		}
		return pitches;
	}

}
