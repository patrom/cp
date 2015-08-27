package cp.model;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cp.generator.MusicProperties;
import cp.model.harmony.Harmony;
import cp.model.melody.HarmonicMelody;
import cp.model.melody.Melody;
import cp.model.note.Note;
import cp.objective.meter.InnerMetricWeightFunctions;

public class Motive {

	private List<Harmony> harmonies;
	private MusicProperties musicProperties;
	private List<Melody> melodies;
		
	public Motive(List<Harmony> harmonies, MusicProperties musicProperties) {
		this.harmonies = harmonies;
		this.musicProperties = musicProperties;
	}

	public List<Harmony> getHarmonies() {
		return harmonies;
	}
	
	public List<Melody> getMelodies() {
		return melodies;
	}
	
	protected List<HarmonicMelody> getMelodyForVoice(int voice){
		return harmonies.stream()
				.flatMap(harmony -> harmony.getHarmonicMelodies().stream())
				.filter(harmonicMelody -> harmonicMelody.getVoice() == voice)
				.collect(toList());
	}
	
	public MusicProperties getMusicProperties() {
		return musicProperties;
	}
	
	public void extractMelodies(){
		this.melodies = new ArrayList<>();
		harmonies.stream()
			.flatMap(harmony -> harmony.getHarmonicMelodies().stream())
			.flatMap(hm -> hm.getMelodyNotes().stream())
			.forEach(note -> note.setPitch(0));
		harmonies.stream().forEach(harmony -> harmony.translateToPitchSpace());
		if(containsZeroPitch()){
			throw new IllegalStateException("Contains 0 pitch!");
		};
		for (int i = 0; i < musicProperties.getChordSize(); i++) {
			Melody melody = new Melody(getMelodyForVoice(i), i);
			melodies.add(melody);
		}
	}

	private boolean containsZeroPitch() {
		return harmonies.stream()
			.flatMap(harmony -> harmony.getHarmonicMelodies().stream())
			.flatMap(hm -> hm.getMelodyNotes().stream())
			.anyMatch(note -> note.getPitch() == 0);
	}
	
	public void updateInnerMetricWeightMelodies() {
		for (Melody melody : melodies) {
			List<Note> notes = melody.getMelodieNotes();
			updateInnerMetricWeightNotes(notes);
		}
	}

	protected void updateInnerMetricWeightNotes(List<Note> notes) {
		Map<Integer, Double> normalizedMap = InnerMetricWeightFunctions.getNormalizedInnerMetricWeight(notes, musicProperties.getMinimumLength());
		for (Note note : notes) {
			Integer key = note.getPosition()/musicProperties.getMinimumLength();
			if (normalizedMap.containsKey(key)) {
				Double innerMetricValue = normalizedMap.get(key);
				note.setInnerMetricWeight(innerMetricValue);
			}
		}
	}
	
	public void updateInnerMetricWeightHarmonies() {
		int[] harmonicRhythm = extractHarmonicRhythm();
		Map<Integer, Double> normalizedMap = InnerMetricWeightFunctions.getNormalizedInnerMetricWeight(harmonicRhythm, musicProperties.getMinimumLength());
		for (Harmony harmony : harmonies) {
			Integer key = harmony.getPosition()/musicProperties.getMinimumLength();
			if (normalizedMap.containsKey(key)) {
				Double innerMetricValue = normalizedMap.get(key);
				harmony.setInnerMetricWeight(innerMetricValue);
			}
		}
	}

	private int[] extractHarmonicRhythm() {
		int[] rhythm = new int[harmonies.size()];
		for (int i = 0; i < rhythm.length; i++) {
			Harmony harmony = harmonies.get(i);
			rhythm[i] = harmony.getPosition();
		}
		return rhythm;
	}

	public void extractChords() {
		harmonies.forEach(harmony -> harmony.toChord());
		
	}
	
}
