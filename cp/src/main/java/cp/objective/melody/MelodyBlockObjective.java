package cp.objective.melody;

import cp.composition.Composition;
import cp.config.MelodyConfig;
import cp.generator.MusicProperties;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Component
public class MelodyBlockObjective implements BlockObjective {

    @Autowired
    protected MusicProperties musicProperties;

    @Autowired
    private Composition composition;

    //	@Autowired
//	@Qualifier(value="TonalDissonance")
//	private Dissonance dissonance;
    @Autowired
    private MelodyConfig melodyConfig;

    @Override
    public double evaluate(MelodyBlock melodyBlock) {
        double totalMelodySum = 0;
        List<Note> notes =  melodyBlock.getMelodyBlockNotes();
        MelodyDissonance melodyDissonance = melodyConfig.getMelodyDissonanceForVoice(melodyBlock.getVoice());
        double melodyValue = evaluateMelody(notes, 1, melodyDissonance);
        totalMelodySum = totalMelodySum + melodyValue;
//			notes = extractNotesOnLevel(notes, 1);
//			for (double level : musicProperties.getFilterLevels()) {
//				List<Note> filteredNotes = filterNotesWithWeightEqualToOrGreaterThan(notes, level);
//				if ((filteredNotes.size() > 1) && filteredNotes.size() != notes.size()) {
//					double value = evaluateMelody(filteredNotes, 1, melodyDissonance);
//					totalMelodySum = totalMelodySum + value;
//					melodyCount++;
//				}
//			}

        //intervals between weightier notes
        melodyValue = calculteIntervalsBetweenWeightierNotes(melodyBlock);
        totalMelodySum = totalMelodySum + melodyValue;
        return totalMelodySum / 2; //2 X coculated
    }

    private double calculteIntervalsBetweenWeightierNotes(MelodyBlock melodyBlock){
        double averageNoteWeight = melodyBlock.getMelodyBlockNotes().stream().mapToDouble(note -> note.getPositionWeight()).average().getAsDouble();
        List<Note> notesAboveAverage = melodyBlock.getMelodyBlockNotes().stream().filter(note -> note.getPositionWeight() > averageNoteWeight).sorted().collect(toList());
        MelodyDissonance melodyDissonance = melodyConfig.getMelodyDissonanceForVoice(melodyBlock.getVoice());
        return evaluateMelody(notesAboveAverage, 1, melodyDissonance);
    }

    public double evaluateMelody(List<Note> notes, int maxDistance, MelodyDissonance melodyDissonance) {
        if (melodyDissonance == null){
            return 0.0;
        }
        if (notes.size() <= 1) {
            return Double.MIN_VALUE;
        }
        // control repetion with interval weight
//		List<Note> notes = extractDifferentPitches(notes);//repeated note intervals are not calculated!
        if (notes.isEmpty()) {
            return 0.8;//only one note (pedal)
        }
        double totalPositionWeight = getTotalPositionWeiht(notes);
        Note[] notePositions = notes.toArray(new Note[notes.size()]);
        double melodyIntervalValueSum = 0;
        for (int distance = 1; distance <= maxDistance; distance++) {
            for (int j = 0; j < notePositions.length - distance; j++) {
                Note note = notePositions[j];
                Note nextNote = notePositions[j + distance];
                double intervalPositionWeight = (note.getPositionWeight() + nextNote.getPositionWeight())/totalPositionWeight;
                double intervalMelodicValue = getIntervalMelodicValue(note, nextNote, melodyDissonance);
                double intervalValue = intervalMelodicValue * intervalPositionWeight;
                melodyIntervalValueSum = melodyIntervalValueSum + intervalValue;
            }
        }
        return melodyIntervalValueSum/maxDistance;
    }

    public static <T> Stream<List<T>> sliding(List<T> list, int size) {
        if(size > list.size())
            return Stream.empty();
        return IntStream.range(0, list.size()-size+1)
                .mapToObj(start -> list.subList(start, start+size));
    }

    private double getIntervalMelodicValue(Note note, Note nextNote, MelodyDissonance melodyDissonance) {
        int difference = 0;
        if (note.getPitch() != 0 && nextNote.getPitch() != 0) {
            difference = nextNote.getPitch() - note.getPitch();
        }
//		else{
//			difference = note.getPitchClass() - nextNote.getPitchClass();
//		}
        return melodyDissonance.getMelodicValue(difference);
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

//	protected double evaluateTriadicValueMelody(Collection<Note> notes) {
//		Note[] notePositions = notes.toArray(new Note[notes.size()]);
//		double harmonicValue = 0;
//		for (int i = 0; i < notePositions.length - 2; i++) {
//				Note firstNote = notePositions[i];
//				Note secondNote = notePositions[i + 1];
//				Note thirdNote = notePositions[i + 2];
//				Chord chord = new Chord(firstNote.getPitchClass());
//				chord.addPitchClass(firstNote.getPitchClass());
//				chord.addPitchClass(secondNote.getPitchClass());
//				chord.addPitchClass(thirdNote.getPitchClass());
//				if ("3-11".equals(chord.getForteName())) {
//					harmonicValue = harmonicValue + dissonance.getDissonance(chord);
//				}
//		}
//		return (harmonicValue == 0)? 0:harmonicValue/(notePositions.length - 2);
//	}

}
