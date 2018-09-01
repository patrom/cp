package cp.objective.tonality;

import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.model.note.NoteBuilder;
import cp.model.rhythm.DurationConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class TonalityFunctionsTest {
	
	private static Logger LOGGER = LoggerFactory.getLogger(TonalityFunctionsTest.class.getName());

	@BeforeEach
	public void setUp() throws Exception {
	}

	@Test
	public void testMajorTonality() {
		List<MelodyBlock> melodies = new ArrayList<>();
		List<Note> notes = new ArrayList<>();
		notes.add(NoteBuilder.note().len(DurationConstants.QUARTER).pc(0).pitch(60).positionWeight(100.0).build());
		notes.add(NoteBuilder.note().len(DurationConstants.QUARTER).pc(2).pitch(62).positionWeight(100.0).build());
		notes.add(NoteBuilder.note().len(DurationConstants.QUARTER).pc(4).pitch(64).positionWeight(100.0).build());
		notes.add(NoteBuilder.note().len(DurationConstants.QUARTER).pc(5).pitch(65).positionWeight(100.0).build());
		notes.add(NoteBuilder.note().len(DurationConstants.QUARTER).pc(7).pitch(67).positionWeight(100.0).build());
		notes.add(NoteBuilder.note().len(DurationConstants.QUARTER).pc(9).pitch(69).positionWeight(100.0).build());
		notes.add(NoteBuilder.note().len(DurationConstants.QUARTER).pc(11).pitch(71).positionWeight(100.0).build());
		notes.add(NoteBuilder.note().len(DurationConstants.QUARTER).pc(0).pitch(72).positionWeight(100.0).build());
		MelodyBlock melodyBlock = new MelodyBlock(5, 0);
		CpMelody melody = new CpMelody(notes, 0, 0 , 0);
		melodyBlock.addMelodyBlock(melody);
		melodies.add(melodyBlock);
		double maxTonality = TonalityFunctions.getMaxCorrelationTonality(0, melodies, TonalityFunctions.vectorMajorTemplate);
		LOGGER.info("maxTonality : " + maxTonality);
	}

	@Test
	public void testMajorTonality2() {
		List<MelodyBlock> melodies = new ArrayList<>();
		List<Note> notes = new ArrayList<>();
		notes.add(NoteBuilder.note().len(DurationConstants.QUARTER).pc(1).positionWeight(100.0).build());
		notes.add(NoteBuilder.note().len(DurationConstants.QUARTER).pc(4).positionWeight(100.0).build());
		notes.add(NoteBuilder.note().len(DurationConstants.QUARTER).pc(4).positionWeight(100.0).build());
		notes.add(NoteBuilder.note().len(DurationConstants.QUARTER).pc(7).positionWeight(100.0).build());
		notes.add(NoteBuilder.note().len(DurationConstants.QUARTER).pc(7).positionWeight(100.0).build());
		notes.add(NoteBuilder.note().len(DurationConstants.QUARTER).pc(4).positionWeight(100.0).build());
		notes.add(NoteBuilder.note().len(DurationConstants.QUARTER).pc(1).positionWeight(100.0).build());
		notes.add(NoteBuilder.note().len(DurationConstants.QUARTER).pc(1).positionWeight(100.0).build());
		notes.add(NoteBuilder.note().len(DurationConstants.QUARTER).pc(7).positionWeight(100.0).build());
		MelodyBlock melodyBlock = new MelodyBlock(5, 0);
		CpMelody melody = new CpMelody(notes, 0, 0 , 0);
		melodyBlock.addMelodyBlock(melody);
		melodies.add(melodyBlock);
		double maxTonality = TonalityFunctions.getMaxCorrelationTonality(0, melodies, TonalityFunctions.vectorMajorTemplate);
		LOGGER.info("maxTonality : " + maxTonality);
	}


	@Test
	public void testChromaticTonality() {
		List<MelodyBlock> melodies = new ArrayList<>();
		List<Note> notes = new ArrayList<>();
//		for (int i = 0; i < 20; i++) {
//			notes.add(NoteBuilder.note().len(DurationConstants.QUARTER).pc(i).pitch(60 + i).positionWeight(100.0).innerWeight(100.0).build());
//		}
		notes.add(NoteBuilder.note().len(DurationConstants.QUARTER).pc(0).pitch(60).positionWeight(100.0).build());
		notes.add(NoteBuilder.note().len(DurationConstants.QUARTER).pc(2).pitch(62).positionWeight(100.0).build());
		notes.add(NoteBuilder.note().len(DurationConstants.QUARTER).pc(7).pitch(67).positionWeight(100.0).build());
		MelodyBlock melodyBlock = new MelodyBlock(5, 0);
		CpMelody melody = new CpMelody(notes, 0, 0 , 0);
		melodyBlock.addMelodyBlock(melody);
		melodies.add(melodyBlock);
		double maxTonality = TonalityFunctions.getMaxCorrelationTonality(melodies, TonalityFunctions.vectorMajorTemplate);
		LOGGER.info("maxTonality : " + maxTonality);
	}
//	
//	@Test
//	public void testRegisterMajorTonality() {
//		List<Melody> melodies = new ArrayList<>();
//		List<HarmonicMelody> harmonicMelodies = new ArrayList<HarmonicMelody>();
//		List<Note> notes = new ArrayList<>();
//		notes.add(NoteBuilder.note().len(DurationConstants.QUARTER).pc(0).pitch(48).positionWeight(100.0).innerWeight(100.0).build());
//		notes.add(NoteBuilder.note().len(DurationConstants.QUARTER).pc(2).pitch(62).positionWeight(100.0).innerWeight(100.0).build());
//		notes.add(NoteBuilder.note().len(DurationConstants.QUARTER).pc(4).pitch(64).positionWeight(100.0).innerWeight(100.0).build());
//		notes.add(NoteBuilder.note().len(DurationConstants.QUARTER).pc(5).pitch(65).positionWeight(100.0).innerWeight(100.0).build());
//		notes.add(NoteBuilder.note().len(DurationConstants.QUARTER).pc(7).pitch(55).positionWeight(100.0).innerWeight(100.0).build());
//		notes.add(NoteBuilder.note().len(DurationConstants.QUARTER).pc(9).pitch(69).positionWeight(100.0).innerWeight(100.0).build());
//		notes.add(NoteBuilder.note().len(DurationConstants.QUARTER).pc(11).pitch(71).positionWeight(100.0).innerWeight(100.0).build());
//		notes.add(NoteBuilder.note().len(DurationConstants.QUARTER).pc(0).pitch(60).positionWeight(100.0).innerWeight(100.0).build());
//		HarmonicMelody harmonicMelody = new HarmonicMelody(NoteBuilder.note().build(), notes, 0, 0);
//		harmonicMelodies.add(harmonicMelody);
//		Melody melody = new Melody(harmonicMelodies, 0);
//		melodies.add(melody);
//		double maxTonality = TonalityFunctions.getMaxCorrelationTonality(melodies, TonalityFunctions.vectorMajorTemplate);
//		LOGGER.info("maxTonality : " + maxTonality);
//	}

}
