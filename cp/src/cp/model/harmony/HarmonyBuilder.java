package cp.model.harmony;

import java.util.ArrayList;
import java.util.List;

import cp.model.melody.HarmonicMelody;
import cp.model.melody.pitchspace.UniformPitchSpace;
import cp.model.note.Note;
import cp.out.instrument.Ensemble;

public class HarmonyBuilder {
	
	private int position;
	private List<HarmonicMelody> melodyNotes = new ArrayList<>();
	private int length;
	private double positionWeight;
	private List<Integer> pitchClasses = new ArrayList<>();
	
	public static HarmonyBuilder harmony(){
		return new HarmonyBuilder();
	}
	
	public HarmonyBuilder pos(int position){
		this.position = position;
		return this;
	}
	
	public HarmonyBuilder len(int length){
		this.length = length;
		return this;
	}
	
	public HarmonyBuilder notes(int ... pitchClass){
		for (int i = 0; i < pitchClass.length; i++) {
			Note note = new Note(pitchClass[i] , i , position, length);
			HarmonicMelody harmonicMelody = new HarmonicMelody(note, i, position);
			melodyNotes.add(harmonicMelody);	
		}
		return this;
	}
	
	public HarmonyBuilder notes(List<Note> notes){
		for (int i = 0; i < notes.size(); i++) {
			HarmonicMelody harmonicMelody = new HarmonicMelody(notes.get(i) ,i, position);
			melodyNotes.add(harmonicMelody);
		}
		return this;
	}
	
	public HarmonyBuilder pitchClasses(int ... pitchClass){
		for (int i = 0; i < pitchClass.length; i++) {
			pitchClasses.add(pitchClass[i]);	
		}
		return this;
	}
	
	public HarmonyBuilder positionWeight(double positionWeight){
		this.positionWeight = positionWeight;
		return this;
	}
	
	public HarmonyBuilder melodyBuilder(HarmonicMelody harmonicMelody){
		this.melodyNotes.add(harmonicMelody);
		return this;
	}

	public Harmony build(){
		Harmony harmony = new Harmony(position, length, melodyNotes);
		harmony.setPositionWeight(positionWeight);
		Integer[] range = {5, 6};
		harmony.setPitchSpace(new UniformPitchSpace(range, Ensemble.getStringQuartet()));
		return harmony;
	}
	
	public int getPosition() {
		return position;
	}

	public int getLength() {
		return length;
	}
	
	public boolean containsNotes(){
		return !pitchClasses.isEmpty();
	}
	
	public List<Integer> getPitchClasses() {
		return pitchClasses;
	}
	
}
