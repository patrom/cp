package cp.objective.tonality;

import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TonalityFunctionsTest {
	
	private static Logger LOGGER = LoggerFactory.getLogger(TonalityFunctionsTest.class.getName());

	@Before
	public void setUp() throws Exception {
	}

//	@Test
//	public void testMajorTonality() {
//		List<CpMelody> melodies = new ArrayList<>();
//		List<HarmonicMelody> harmonicMelodies = new ArrayList<HarmonicMelody>();
//		List<Note> notes = new ArrayList<>();
//		notes.add(NoteBuilder.note().len(DurationConstants.QUARTER).pc(0).pitch(60).positionWeight(100.0).innerWeight(100.0).build());
//		notes.add(NoteBuilder.note().len(DurationConstants.QUARTER).pc(2).pitch(62).positionWeight(100.0).innerWeight(100.0).build());
//		notes.add(NoteBuilder.note().len(DurationConstants.QUARTER).pc(4).pitch(64).positionWeight(100.0).innerWeight(100.0).build());
//		notes.add(NoteBuilder.note().len(DurationConstants.QUARTER).pc(5).pitch(65).positionWeight(100.0).innerWeight(100.0).build());
//		notes.add(NoteBuilder.note().len(DurationConstants.QUARTER).pc(7).pitch(67).positionWeight(100.0).innerWeight(100.0).build());
//		notes.add(NoteBuilder.note().len(DurationConstants.QUARTER).pc(9).pitch(69).positionWeight(100.0).innerWeight(100.0).build());
//		notes.add(NoteBuilder.note().len(DurationConstants.QUARTER).pc(11).pitch(71).positionWeight(100.0).innerWeight(100.0).build());
//		notes.add(NoteBuilder.note().len(DurationConstants.QUARTER).pc(0).pitch(72).positionWeight(100.0).innerWeight(100.0).build());
//		HarmonicMelody harmonicMelody = new HarmonicMelody(NoteBuilder.note().build(), notes, 0, 0);
//		harmonicMelodies.add(harmonicMelody);
//		Melody melody = new Melody(harmonicMelodies, 0);
//		melodies.add(melody);
//		double maxTonality = TonalityFunctions.getMaxCorrelationTonality(melodies, TonalityFunctions.vectorMajorTemplate);
//		LOGGER.info("maxTonality : " + maxTonality);
//	}
//	
//	@Test
//	public void testChromaticTonality() {
//		List<Melody> melodies = new ArrayList<>();
//		List<HarmonicMelody> harmonicMelodies = new ArrayList<HarmonicMelody>();
//		List<Note> notes = new ArrayList<>();
//		for (int i = 0; i < 12; i++) {
//			notes.add(NoteBuilder.note().len(DurationConstants.QUARTER).pc(i).pitch(60 + i).positionWeight(100.0).innerWeight(100.0).build());
//		}
//		HarmonicMelody harmonicMelody = new HarmonicMelody(NoteBuilder.note().build(), notes, 0, 0);
//		harmonicMelodies.add(harmonicMelody);
//		Melody melody = new Melody(harmonicMelodies, 0);
//		melodies.add(melody);
//		double maxTonality = TonalityFunctions.getMaxCorrelationTonality(melodies, TonalityFunctions.vectorMajorTemplate);
//		LOGGER.info("maxTonality : " + maxTonality);
//	}
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
