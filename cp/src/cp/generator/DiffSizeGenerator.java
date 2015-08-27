package cp.generator;

import static cp.model.melody.HarmonicMelodyBuilder.harmonicMelody;
import static cp.model.note.NoteBuilder.note;

import java.util.ArrayList;
import java.util.List;

import cp.model.harmony.Harmony;
import cp.model.melody.HarmonicMelody;
import cp.model.melody.pitchspace.UniformPitchSpace;

public class DiffSizeGenerator extends Generator{

	public DiffSizeGenerator(int[] positions, MusicProperties musicProperties) {
		super(positions, musicProperties);
	}

	@Override
	public List<Harmony> generateHarmonies() {
		List<Harmony> harmonies = new ArrayList<>();
		List<HarmonicMelody> harmonicMelodies = new ArrayList<>();
		HarmonicMelody harmonicMelody0 = harmonicMelody()
				.harmonyNote(note().pc(0).build())
				.voice(0)
				.notes(note().pc(0).pos(0).len(6).build(), 
					   note().pc(3).pos(6).len(6).build())
				.build();
		harmonicMelodies.add(harmonicMelody0);
		HarmonicMelody harmonicMelody1 = harmonicMelody()
				.harmonyNote(note().pc(1).build())
				.voice(1)
				.notes(note().pc(1).pos(0).len(6).build(), 
					   note().pc(0).pos(6).len(6).build())
				.build();
		harmonicMelodies.add(harmonicMelody1);
//		HarmonicMelody harmonicMelody2 = harmonicMelody()
//				.harmonyNote(note().pc(2).build())
//				.voice(2)
//				.build();
//		harmonicMelodies.add(harmonicMelody2);
		HarmonicMelody harmonicMelody3 = harmonicMelody()
				.harmonyNote(note().pc(3).build())
				.voice(3)
				.build();
		harmonicMelodies.add(harmonicMelody3);
		Harmony harmony = new Harmony(0, 12, harmonicMelodies);
		double totalWeight = calculatePositionWeight(harmony.getPosition(), harmony.getLength());
		harmony.setPositionWeight(totalWeight);
		harmony.setPitchSpace(new UniformPitchSpace(musicProperties.getOctaveLowestPitchClassRange(), musicProperties.getInstruments()));
		harmonies.add(harmony);
		
		harmonicMelodies = new ArrayList<>();
		harmonicMelody0 = harmonicMelody()
				.harmonyNote(note().pc(0).build())
				.voice(0)
				.notes(note().pc(4).pos(12).len(6).build(), 
					   note().pc(3).pos(18).len(6).build())
				.build();
		harmonicMelodies.add(harmonicMelody0);
//		harmonicMelody1 = harmonicMelody()
//				.harmonyNote(note().pc(1).build())
//				.voice(1)
//				.notes(note().pc(1).pos(0).len(6).build(), 
//					   note().pc(0).pos(6).len(6).build())
//				.build();
//		harmonicMelodies.add(harmonicMelody1);
		HarmonicMelody harmonicMelody2 = harmonicMelody()
				.harmonyNote(note().pc(2).build())
				.voice(2)
				.build();
		harmonicMelodies.add(harmonicMelody2);
		harmonicMelody3 = harmonicMelody()
				.harmonyNote(note().pc(3).build())
				.voice(3)
				.build();
		harmonicMelodies.add(harmonicMelody3);
		harmony = new Harmony(12, 24, harmonicMelodies);
		totalWeight = calculatePositionWeight(harmony.getPosition(), harmony.getLength());
		harmony.setPositionWeight(totalWeight);
		harmony.setPitchSpace(new UniformPitchSpace(musicProperties.getOctaveLowestPitchClassRange(), musicProperties.getInstruments()));
		harmonies.add(harmony);
		
		harmonicMelodies = new ArrayList<>();
//		harmonicMelody0 = harmonicMelody()
//				.harmonyNote(note().pc(0).build())
//				.voice(0)
//				.notes(note().pc(0).pos(0).len(6).build(), 
//					   note().pc(3).pos(6).len(6).build())
//				.build();
//		harmonicMelodies.add(harmonicMelody0);
		harmonicMelody1 = harmonicMelody()
				.harmonyNote(note().pc(1).build())
				.voice(1)
				.notes(note().pc(1).pos(24).len(6).build(), 
					   note().pc(8).pos(30).len(6).build())
				.build();
		harmonicMelodies.add(harmonicMelody1);
		harmonicMelody2 = harmonicMelody()
				.harmonyNote(note().pc(2).build())
				.voice(2)
				.build();
		harmonicMelodies.add(harmonicMelody2);
		harmonicMelody3 = harmonicMelody()
				.harmonyNote(note().pc(3).build())
				.voice(3)
				.build();
		harmonicMelodies.add(harmonicMelody3);
		harmony = new Harmony(24, 36, harmonicMelodies);
		totalWeight = calculatePositionWeight(harmony.getPosition(), harmony.getLength());
		harmony.setPositionWeight(totalWeight);
		harmony.setPitchSpace(new UniformPitchSpace(musicProperties.getOctaveLowestPitchClassRange(), musicProperties.getInstruments()));
		harmonies.add(harmony);	
		
		
		return harmonies;
	}

}
