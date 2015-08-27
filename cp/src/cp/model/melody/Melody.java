package cp.model.melody;

import static java.util.stream.Collectors.toList;

import java.util.List;

import cp.model.note.Note;


public class Melody{
	
	private int voice;
	private List<HarmonicMelody> harmonicMelodies;

	public Melody(List<HarmonicMelody> harmonicMelodies, int voice) {
		this.harmonicMelodies = harmonicMelodies;
		this.voice = voice;
	}
	
	public List<HarmonicMelody> getHarmonicMelodies() {
		return harmonicMelodies;
	}
	
	public int getVoice() {
		return voice;
	}
	
	public List<Note> getMelodieNotes(){
		return harmonicMelodies.stream()
					.flatMap(harmonicMelody -> harmonicMelody.getMelodyNotes().stream())
					.sorted()
					.collect(toList());
	}
	
	public List<Note> getHarmonyNotes(){
		return harmonicMelodies.stream()
					.map(harmonicMelody -> harmonicMelody.getHarmonyNote())
					.sorted()
					.collect(toList());
	}

}
