package cp.model;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import cp.generator.MusicProperties;
import cp.model.harmony.Harmony;
import cp.model.melody.CpMelody;
import cp.model.melody.HarmonicMelody;
import cp.model.note.Note;
import cp.out.instrument.Instrument;
import cp.util.RandomUtil;

public class Motive implements Cloneable {

	private List<Harmony> harmonies;
	private MusicProperties musicProperties;
	private List<CpMelody> melodies;
	private List<Instrument> instruments;
		
	public Motive(List<CpMelody> melodies, MusicProperties musicProperties) {
		this.melodies = melodies;
		this.musicProperties = musicProperties;
	}

	protected Motive(Motive motive) {
		// TODO clone implementation
		this.melodies = motive.getMelodies().stream().map(m -> (CpMelody) m.clone()).collect(toList());
	}

	public List<Harmony> getHarmonies() {
		return harmonies;
	}
	
	public List<CpMelody> getMelodies() {
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
	
	@Override
	public Object clone() {
		return new Motive(this);
	}
	
	public CpMelody getRandomMutableMelody(){
		List<CpMelody> mutableMelodies = melodies.stream().filter(m -> m.isMutable()).collect(toList());
		return mutableMelodies.get(RandomUtil.random(mutableMelodies.size()));
	}
	
	public void updateMelodiesToInstrumentLimits(){
		for (CpMelody melody : melodies) {
			Optional<Instrument> optionalInstrument = instruments.stream().filter(instr -> (instr.getVoice()) == melody.getVoice()).findFirst();
			if (optionalInstrument.isPresent()) {
				Instrument instrument = optionalInstrument.get();
				melody.updateMelodyBetween(instrument.getLowest(), instrument.getHighest());
			}
		}
	}
	
//	public void extractMelodies(){
//		this.melodies = new ArrayList<>();
//		harmonies.stream()
//			.flatMap(harmony -> harmony.getHarmonicMelodies().stream())
//			.flatMap(hm -> hm.getMelodyNotes().stream())
//			.forEach(note -> note.setPitch(0));
//		harmonies.stream().forEach(harmony -> harmony.translateToPitchSpace());
//		if(containsZeroPitch()){
//			throw new IllegalStateException("Contains 0 pitch!");
//		};
//		for (int i = 0; i < musicProperties.getChordSize(); i++) {
//			Melody melody = new Melody(getMelodyForVoice(i), i);
//			melodies.add(melody);
//		}
//	}
//
//	private boolean containsZeroPitch() {
//		return harmonies.stream()
//			.flatMap(harmony -> harmony.getHarmonicMelodies().stream())
//			.flatMap(hm -> hm.getMelodyNotes().stream())
//			.anyMatch(note -> note.getPitch() == 0);
//	}
//	
//	public void updateInnerMetricWeightMelodies() {
//		for (Melody melody : melodies) {
//			List<Note> notes = melody.getMelodieNotes();
//			updateInnerMetricWeightNotes(notes);
//		}
//	}
//
//	protected void updateInnerMetricWeightNotes(List<Note> notes) {
//		Map<Integer, Double> normalizedMap = InnerMetricWeightFunctions.getNormalizedInnerMetricWeight(notes, musicProperties.getMinimumLength());
//		for (Note note : notes) {
//			Integer key = note.getPosition()/musicProperties.getMinimumLength();
//			if (normalizedMap.containsKey(key)) {
//				Double innerMetricValue = normalizedMap.get(key);
//				note.setInnerMetricWeight(innerMetricValue);
//			}
//		}
//	}
//	
//	public void updateInnerMetricWeightHarmonies() {
//		int[] harmonicRhythm = extractHarmonicRhythm();
//		Map<Integer, Double> normalizedMap = InnerMetricWeightFunctions.getNormalizedInnerMetricWeight(harmonicRhythm, musicProperties.getMinimumLength());
//		for (Harmony harmony : harmonies) {
//			Integer key = harmony.getPosition()/musicProperties.getMinimumLength();
//			if (normalizedMap.containsKey(key)) {
//				Double innerMetricValue = normalizedMap.get(key);
//				harmony.setInnerMetricWeight(innerMetricValue);
//			}
//		}
//	}
//
//	private int[] extractHarmonicRhythm() {
//		int[] rhythm = new int[harmonies.size()];
//		for (int i = 0; i < rhythm.length; i++) {
//			Harmony harmony = harmonies.get(i);
//			rhythm[i] = harmony.getPosition();
//		}
//		return rhythm;
//	}
//
//	public void extractChords() {
//		harmonies.forEach(harmony -> harmony.toChord());
//		
//	}
	
}
