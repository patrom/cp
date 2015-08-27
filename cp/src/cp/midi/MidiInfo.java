package cp.midi;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import cp.model.note.Note;

public class MidiInfo {

	private List<MelodyInstrument> melodies;
	private String timeSignature;
	private int tempo;
	
	public String getTimeSignature() {
		return timeSignature;
	}
	public void setTimeSignature(String timeSignature) {
		this.timeSignature = timeSignature;
	}
	public int getTempo() {
		return tempo;
	}
	public void setTempo(int tempo) {
		this.tempo = tempo;
	}
	public List<MelodyInstrument> getMelodies() {
		return melodies;
	}
	public void setMelodies(List<MelodyInstrument> melodies) {
		this.melodies = melodies;
	}
	
	public List<HarmonyPosition> getHarmonyPositions(){
		int size = melodies.size();
		List<MelodyInstrument> harmonies = new ArrayList<>(melodies.subList(size/2, size));
		List<Note> harmonyNotes = harmonies.stream()
										.flatMap(m -> m.getNotes().stream())
										.collect(Collectors.toList());
		return collectHarmonyNotes(harmonyNotes);
	}
	
	public List<HarmonyPosition> getHarmonyPositionsForVoices(int... voices){
		List<Note> harmonyNotes = melodies.stream()
									.filter(m -> { 
										for (int i = 0; i < voices.length; i++) {
											if(m.getVoice() == voices[i]){
												return true;
											};
										}
										return false;
									})
									.flatMap(m -> m.getNotes().stream())
									.collect(toList());
		return collectHarmonyNotes(harmonyNotes);
	}
	
	private List<HarmonyPosition> collectHarmonyNotes(List<Note> harmonyNotes) {
		return harmonyNotes.stream()
			 .collect(collectingAndThen(
					 	groupingBy(note -> note.getPosition(), HarmonyCollector.toHarmonyCollector()),
					 			(value) -> { 
					 				return value.values().stream()
					 						.flatMap(h -> h.stream())
					 						.sorted()
					 						.collect(Collectors.toList());}
					 	));
	}
	
}
