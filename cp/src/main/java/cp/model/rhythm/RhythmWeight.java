package cp.model.rhythm;

import cp.composition.voice.Voice;
import cp.model.note.Dynamic;
import cp.model.note.Note;
import cp.out.instrument.Articulation;
import cp.out.instrument.Technical;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RhythmWeight {
	
	private List<Note> notes;

	private static final double ARTICULATION_WEIGHT = 1.0;
	private static final double TECHNICAL_WEIGHT = 1.0;
	private static final double DYNAMIC_WEIGHT = 1.0;
	private static final double DIASTEMATY_WEIGHT = 1.0;
	private static final double TEXTURE_WEIGHT = 1.0;

	protected double getMinimumNoteValue() {
//		updateNotesLength();
		return notes.stream().mapToDouble(note -> note.getLength()).min().getAsDouble();
	}
	
	protected void clearPositionWeights(){
		notes.forEach(note -> note.setPositionWeight(0));
	}
	
	protected void updateNotesLength(){
		Note lastNote = notes.get(notes.size() - 1);
		if (lastNote.getLength() == 0) {
			lastNote.setLength(Voice.DEFAULT_LENGTH);
		}
		int size = notes.size() - 1;
		for (int i = 0; i < size; i++) {
			Note note = notes.get(i);
			Note nextNote = notes.get(i + 1);
			note.setLength(nextNote.getPosition() - note.getPosition());
		}
	}

	protected void updateRhythmWeightSounds(double min) {
//		updateNotesLength();
//		notes.get(notes.size() - 1).setLength((int) min);
		notes.forEach(note -> note.setPositionWeight(note.getPositionWeight() + (note.getLength()/min))); 
	}

	protected void updateRhythmWeightPitch(List<Note> pitches, double min) {
		int size = pitches.size() - 1;
		for (int i = 0; i < size; i++) {
			Note note = pitches.get(i);
			Note nextNote = pitches.get(i + 1);
			int length = nextNote.getPosition() - note.getPosition();
			note.setPositionWeight(note.getPositionWeight() + (length/min));
		}
	}

	public List<Note> extractDifferentPitches() {
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

	protected void updateRhythmWeightDiastematy(List<Note> pitches) {
		int size = pitches.size() - 1;
		boolean positive = false;
		boolean negative = false;
		for (int i = 0; i < size; i++) {
			Note note = pitches.get(i);
			Note nextNote = pitches.get(i + 1);
			int interval = nextNote.getPitch() - note.getPitch();
			if (i == 0) {
				note.setPositionWeight(note.getPositionWeight() + DIASTEMATY_WEIGHT);
				if (interval > 0) {
					note.setKeel(true);
				} else {
					note.setCrest(true);
				}
			}
			if (interval > 0) {
				positive = true;
				if (negative) {
					//change keel
					note.setPositionWeight(note.getPositionWeight() + DIASTEMATY_WEIGHT);
					note.setKeel(true);
					negative = false;
				}
			}else{
				negative = true;
				if (positive) {
					//change crest
					note.setPositionWeight(note.getPositionWeight() + DIASTEMATY_WEIGHT);
					note.setCrest(true);
					positive = false;
				}
			}
		}
	}

	protected void updateRhythmWeightDynamics() {
		Dynamic prevDynamic = null;
		for (Note note : notes) {
			Dynamic dynamic = note.getDynamic();
			if (dynamic != prevDynamic){
				note.setPositionWeight(note.getPositionWeight() + DYNAMIC_WEIGHT);
			}
			prevDynamic = dynamic;
		}
	}

	protected void updateRhythmWeightArticulation() {
		Articulation prevArticulation = null;
		for (Note note : notes) {
			Articulation articulation = note.getArticulation();
			if (articulation != null && articulation != prevArticulation){
				note.setPositionWeight(note.getPositionWeight() + ARTICULATION_WEIGHT);
			}
			prevArticulation = articulation;
		}
	}

	protected void updateRhythmWeightTechnical() {
		Technical prevTechnical = null;
		for (Note note : notes) {
			Technical technical = note.getTechnical();
			if (technical != null && technical != prevTechnical){
				note.setPositionWeight(note.getPositionWeight() + TECHNICAL_WEIGHT);
			}
			prevTechnical = technical;
		}
	}

	protected void updateTexture() {
		for (Note note : notes) {
			if (note.getDependantHarmony() != null ){
				note.setPositionWeight(note.getPositionWeight() + TECHNICAL_WEIGHT);
			}
		}
	}
	
	public void updateRhythmWeightMinimum(double min){
		clearPositionWeights();
		updateRhythmWeightSounds(min);
		List<Note> pitches = extractDifferentPitches();
		updateRhythmWeightPitch(pitches, min);
		updateRhythmWeightDiastematy(pitches);
		updateRhythmWeightDynamics();
		updateRhythmWeightArticulation();
		updateRhythmWeightTechnical();
		updateTexture();
	}
	
	public void updateRhythmWeight(){
		double min = getMinimumNoteValue();
		updateRhythmWeightMinimum(min);
	}
	
	public List<Note> filterRhythmWeigths(double minimumWeight){
		return notes.stream().filter(note -> note.getPositionWeight() >= minimumWeight).collect(Collectors.toList());
	}
	
	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}
	
}
