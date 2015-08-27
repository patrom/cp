package cp.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import cp.model.harmony.Harmony;
import cp.model.harmony.HarmonyBuilder;
import cp.model.melody.HarmonicMelody;
import cp.model.melody.pitchspace.UniformPitchSpace;
import cp.model.note.Note;
import cp.util.RandomUtil;

public class BeginEndChordGenerator  extends Generator{

	private int[] beginPitchClasses;
	private int[] endPitchClasses;
	
	public BeginEndChordGenerator(int[] positions, MusicProperties musicProperties) {
		super(positions, musicProperties);
	}

//	@Override
//	public void generateHarmonyBuilders() {
//		for (int i = 0; i < positions.length - 1; i++) {
//			harmonyBuilders.add(harmony().pos(positions[i][0]).len(positions[i + 1][0] - positions[i][0]));
////			if (i == 0) {
////				harmonyBuilder.pitchClasses(beginPitchClasses);
////			}
////			if(i == positions.length - 2){
////				harmonyBuilder.pitchClasses(endPitchClasses);
////			}
////			harmonyBuilders.add(harmonyBuilder);
//		}
//	}
	
	public void setBeginPitchClasses(int[] beginPitchClasses) {
		this.beginPitchClasses = beginPitchClasses;
	}
	
	public void setEndPitchClasses(int[] endPitchClasses) {
		this.endPitchClasses = endPitchClasses;
	}
	
	@Override
	public List<Harmony> generateHarmonies(){
		List<Harmony> harmonies = new ArrayList<>();
		int size = musicProperties.getChordSize();
		int[] chord = new int[size];
		int lastPosition = positions[positions.length - 2];
		for (HarmonyBuilder harmonyBuilder : harmonyBuilders) {
			if (harmonyBuilder.getPosition() == 0) {
				if (beginPitchClasses.length < size) {
					chord = expandChordToSize(beginPitchClasses, size);
				}else{
					chord = beginPitchClasses;
				}
			}
			if(harmonyBuilder.getPosition() == lastPosition){
				if (endPitchClasses.length < size) {
					chord = expandChordToSize(endPitchClasses, size);
				}else{
					chord = endPitchClasses;
				}
			}
			List<HarmonicMelody> harmonicMelodies = new ArrayList<>(); 
			for (int voice = 0; voice < size; voice++) {
				if (harmonyBuilder.getPosition() == 0 || harmonyBuilder.getPosition() == lastPosition) {
					HarmonicMelody harmonicMelody = getHarmonicMelody(
							harmonyBuilder.getPosition(), voice, harmonyBuilder.getLength(), chord);
					harmonicMelodies.add(harmonicMelody);
				} else {
					HarmonicMelody harmonicMelody = getHarmonicMelody(harmonyBuilder.getPosition(), voice, harmonyBuilder.getLength(), musicProperties.getScale().pickRandomPitchClass());
					harmonicMelodies.add(harmonicMelody);
				}
			}
			Harmony harmony = new Harmony(harmonyBuilder.getPosition(), harmonyBuilder.getLength(), harmonicMelodies);
			double totalWeight = calculatePositionWeight(harmonyBuilder.getPosition(), harmonyBuilder.getLength());
			harmony.setPositionWeight(totalWeight);
			harmony.setPitchSpace(new UniformPitchSpace(musicProperties.getOctaveLowestPitchClassRange(), musicProperties.getInstruments()));
			harmonies.add(harmony);	
		}
		return harmonies;
	}
	
	protected HarmonicMelody getHarmonicMelody(int position, int voice, int length, int[] chord) {
		Optional<HarmonicMelody> optional = getHarmonicMelodyForVoiceAndPosition(voice, position);
		if (optional.isPresent()) {
			HarmonicMelody harmonicMelody = optional.get();
            harmonicMelody.updateHarmonyAndMelodyNotes(chord[voice], n -> n.setPitchClass(RandomUtil.getRandomFromIntArray(chord)));
			return harmonicMelody;
		} else {
			Note harmonyNote = new Note(chord[voice], voice , position, length);
			harmonyNote.setPositionWeight(calculatePositionWeight(harmonyNote.getPosition(),  harmonyNote.getLength()));
			return new HarmonicMelody(harmonyNote, harmonyNote.getVoice(), harmonyNote.getPosition());
		}
	}

}
