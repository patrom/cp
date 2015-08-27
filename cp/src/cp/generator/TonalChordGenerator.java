package cp.generator;

import java.util.ArrayList;
import java.util.List;

import cp.model.harmony.Harmony;
import cp.model.harmony.HarmonyBuilder;
import cp.model.melody.HarmonicMelody;
import cp.model.melody.pitchspace.UniformPitchSpace;
import cp.util.RandomUtil;

public class TonalChordGenerator extends Generator{

	private List<int[]> chords;
	
	public TonalChordGenerator(int[] positions, MusicProperties musicProperties) {
		super(positions, musicProperties);
	}

	protected int[] pickRandomChord() {
		int[] chord = RandomUtil.getRandomFromList(chords);
		int size = musicProperties.getChordSize();
		if (chord.length < size) {
			return expandChordToSize(chord, size);
		}
		return chord;
	}

	@Override
	public List<Harmony> generateHarmonies(){
		List<Harmony> harmonies = new ArrayList<>();
		for (HarmonyBuilder harmonyBuilder : harmonyBuilders) {
			int[] chord = pickRandomChord();
			List<HarmonicMelody> harmonicMelodies = new ArrayList<>(); 
			for (int voice = 0; voice < musicProperties.getChordSize(); voice++) {
				HarmonicMelody harmonicMelody = getHarmonicMelody(
						harmonyBuilder.getPosition(), voice, harmonyBuilder.getLength(), chord[voice]);
				harmonicMelody.updateHarmonyAndMelodyNotes(chord[voice], n -> n.setPitchClass(RandomUtil.getRandomFromIntArray(chord)));
				harmonicMelodies.add(harmonicMelody);
			}
			Harmony harmony = new Harmony(harmonyBuilder.getPosition(), harmonyBuilder.getLength(), harmonicMelodies);
			double totalWeight = calculatePositionWeight(harmonyBuilder.getPosition(), harmonyBuilder.getLength());
			harmony.setPositionWeight(totalWeight);
			harmony.setPitchSpace(new UniformPitchSpace(musicProperties.getOctaveLowestPitchClassRange(), musicProperties.getInstruments()));
			harmonies.add(harmony);	
		}
		return harmonies;
	}
	
	public void setChords(List<int[]> chords) {
		this.chords = chords;
	}
	
	public void addChords(List<int[]> chords){
		this.chords.addAll(chords);
	}

}
