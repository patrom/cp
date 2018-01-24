package cp.model.harmony;

import cp.DefaultConfig;
import cp.model.TimeLine;
import cp.model.TimeLineKey;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import cp.out.print.Keys;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static cp.model.note.NoteBuilder.note;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DefaultConfig.class)
public class HarmonyExtractorTest {
	
	@Autowired
	@InjectMocks
	private HarmonyExtractor harmonyExtractor;

	@Mock
	private TimeLine timeLine;

	@Autowired
	private Keys keys;

	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		TimeLineKey timeLineKey = new TimeLineKey(keys.C, Scale.MAJOR_SCALE, 0, 0);
		when(timeLine.getTimeLineKeyAtPosition(anyInt(), anyInt())).thenReturn(timeLineKey);
	}

	@Test
	public void testExtractHarmony() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pitch(60).pc(0).voice(0).positionWeight(4.0).len(DurationConstants.QUARTER).build());
		notes.add(note().pos(DurationConstants.QUARTER).pitch(60).pc(0).voice(0).positionWeight(1.0).len(DurationConstants.QUARTER).build());
		notes.add(note().pos(DurationConstants.HALF).pitch(62).pc(2).voice(0).positionWeight(2.0).len(DurationConstants.QUARTER).build());
		notes.add(note().pos(DurationConstants.SIX_EIGHTS).pitch(61).pc(1).voice(0).positionWeight(4.0).len(DurationConstants.QUARTER).build());
		notes.add(note().pos(DurationConstants.WHOLE).pitch(59).pc(11).voice(0).positionWeight(3.0).len(DurationConstants.QUARTER).build());
		notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.QUARTER).pitch(60).pc(0).voice(0).positionWeight(1.0).len(DurationConstants.QUARTER).build());
		
		notes.add(note().pos(0).pitch(64).pc(4).voice(1).positionWeight(3.0).len(DurationConstants.THREE_EIGHTS).build());
		notes.add(note().pos(DurationConstants.THREE_EIGHTS).pitch(58).pc(10).voice(1).positionWeight(1.0).len(DurationConstants.THREE_EIGHTS).build());
		notes.add(note().pos(DurationConstants.SIX_EIGHTS).pitch(61).pc(1).voice(1).positionWeight(2.0).len(DurationConstants.QUARTER).build());
		notes.add(note().pos(DurationConstants.WHOLE).pitch(59).pc(11).voice(1).positionWeight(3.0).len(DurationConstants.WHOLE).build());
		notes.add(note().pos(DurationConstants.WHOLE * 2).pitch(62).pc(2).voice(1).positionWeight(6.0).len(DurationConstants.QUARTER).build());
		List<CpHarmony> harmonies = harmonyExtractor.extractHarmony(notes, 2);
		assertEquals(8, harmonies.size());
		assertEquals(2, harmonies.get(1).getNotes().size());
		assertEquals(2, harmonies.get(2).getNotes().size());
		assertEquals(60, harmonies.get(2).getNotes().get(0).getPitch());
		assertEquals(58, harmonies.get(2).getNotes().get(1).getPitch());
		assertEquals(2, harmonies.get(3).getNotes().size());
		assertEquals(2, harmonies.get(4).getNotes().size());
	}
	

	@Test
	public void testMapNotesForPosition() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pitch(60).positionWeight(4.0).build());
		notes.add(note().pos(DurationConstants.QUARTER).pitch(60).positionWeight(1.0).build());
		notes.add(note().pos(DurationConstants.HALF).pitch(62).positionWeight(2.0).build());
		notes.add(note().pos(DurationConstants.SIX_EIGHTS).pitch(61).positionWeight(4.0).build());
		notes.add(note().pos(DurationConstants.WHOLE).pitch(59).positionWeight(3.0).build());
		notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.QUARTER).pitch(60).positionWeight(1.0).build());
		
		notes.add(note().pos(0).pitch(64).positionWeight(3.0).build());
		notes.add(note().pos(DurationConstants.THREE_EIGHTS).pitch(58).positionWeight(1.0).build());
		notes.add(note().pos(DurationConstants.SIX_EIGHTS).pitch(61).positionWeight(2.0).build());
		notes.add(note().pos(DurationConstants.WHOLE).pitch(59).positionWeight(3.0).build());
		notes.add(note().pos(DurationConstants.WHOLE * 2).pitch(62).positionWeight(6.0).build());
		Map<Integer, List<Note>> map = harmonyExtractor.mapNotesForPosition(notes);
		assertEquals(2, map.get(0).size());
		assertEquals(1, map.get(DurationConstants.QUARTER).size());
		assertEquals(2, map.get(DurationConstants.SIX_EIGHTS).size());
	}
	
	@Test
	public void testExtractHarmonyDifferentStart() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(6).pitch(60).pc(0).voice(0).positionWeight(1.0).build());
		notes.add(note().pos(DurationConstants.HALF).pitch(62).pc(2).voice(0).positionWeight(2.0).build());
		notes.add(note().pos(DurationConstants.SIX_EIGHTS).pitch(61).pc(1).voice(0).positionWeight(4.0).build());
		notes.add(note().pos(DurationConstants.WHOLE).pitch(59).pc(11).voice(0).positionWeight(3.0).build());
		notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.QUARTER).pitch(60).pc(0).voice(0).positionWeight(1.0).build());
		
		notes.add(note().pos(0).pitch(64).pc(4).voice(1).positionWeight(3.0).build());
		notes.add(note().pos(DurationConstants.THREE_EIGHTS).pitch(58).pc(10).voice(1).positionWeight(1.0).build());
		notes.add(note().pos(DurationConstants.SIX_EIGHTS).pitch(61).pc(1).voice(1).positionWeight(2.0).build());
		notes.add(note().pos(DurationConstants.WHOLE).pitch(59).pc(11).voice(1).positionWeight(3.0).build());
		notes.add(note().pos(DurationConstants.WHOLE * 2).pitch(62).pc(2).voice(1).positionWeight(6.0).build());
		List<CpHarmony> harmonies = harmonyExtractor.extractHarmony(notes, 2);
		assertEquals(7, harmonies.size());
		assertEquals(60, harmonies.get(0).getNotes().get(0).getPitch());
		assertEquals(64, harmonies.get(0).getNotes().get(1).getPitch());
		assertEquals(2, harmonies.get(0).getNotes().size());
		assertEquals(2, harmonies.get(1).getNotes().size());
		assertEquals(60, harmonies.get(1).getNotes().get(0).getPitch());
		assertEquals(58, harmonies.get(1).getNotes().get(1).getPitch());
		assertEquals(2, harmonies.get(2).getNotes().size());
		assertEquals(2, harmonies.get(3).getNotes().size());
	}
	
	
	@Test
	public void testExtractRest() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pitch(60).pc(0).voice(0).positionWeight(4.0).build());
		notes.add(note().pos(DurationConstants.QUARTER).pitch(61).pc(1).voice(0).positionWeight(1.0).build());
		notes.add(note().pos(DurationConstants.THREE_EIGHTS).pitch(62).pc(2).voice(0).positionWeight(2.0).build());
		notes.add(note().pos(DurationConstants.SIX_EIGHTS).pitch(61).pc(1).voice(0).positionWeight(4.0).build());
		notes.add(note().pos(DurationConstants.WHOLE).pitch(59).pc(11).voice(0).positionWeight(3.0).build());
		
		notes.add(note().pos(DurationConstants.EIGHT).pitch(64).pc(4).voice(1).positionWeight(3.0).build());
		notes.add(note().pos(DurationConstants.QUARTER).pitch(58).pc(10).voice(1).positionWeight(1.0).build());
		notes.add(note().pos(DurationConstants.HALF).pitch(61).pc(1).voice(1).positionWeight(2.0).build());
		notes.add(note().pos(DurationConstants.WHOLE).pitch(59).pc(11).voice(1).positionWeight(3.0).build());
		List<CpHarmony> harmonies = harmonyExtractor.extractHarmony(notes, 2);
		assertEquals(6, harmonies.size());
		assertEquals(60, harmonies.get(0).getNotes().get(0).getPitch());
		assertEquals(ChordType.CH2_KLEINE_TERTS, harmonies.get(1).getChord().getChordType());
		assertEquals(ChordType.CH2_GROTE_TERTS, harmonies.get(2).getChord().getChordType());
		assertEquals(ChordType.CH2_KLEINE_SECONDE, harmonies.get(3).getChord().getChordType());
	}

	@Test
	public void testExtractEndRests() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pitch(60).pc(0).voice(0).positionWeight(4.0).build());
		notes.add(note().pos(DurationConstants.QUARTER).pitch(61).pc(1).voice(0).positionWeight(1.0).build());
		notes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER).pitch(62).pc(2).voice(0).positionWeight(2.0).build());
		notes.add(note().pos(DurationConstants.WHOLE).pitch(61).pc(1).voice(0).positionWeight(4.0).build());

		notes.add(note().pos(0).pitch(64).pc(4).voice(1).positionWeight(3.0).build());
		notes.add(note().pos(DurationConstants.EIGHT).pitch(58).pc(10).voice(1).positionWeight(1.0).build());
		notes.add(note().pos(DurationConstants.QUARTER + DurationConstants.EIGHT).pitch(61).pc(1).voice(1).positionWeight(2.0).build());
		notes.add(note().pos(DurationConstants.HALF).pitch(59).pc(11).voice(1).positionWeight(3.0).build());
		List<CpHarmony> harmonies = harmonyExtractor.extractHarmony(notes, 2);
		assertEquals(7, harmonies.size());
		assertEquals(60, harmonies.get(0).getNotes().get(0).getPitch());
		assertEquals(ChordType.CH2_GROTE_TERTS, harmonies.get(0).getChord().getChordType());
		assertEquals(ChordType.CH2_GROTE_SECONDE, harmonies.get(1).getChord().getChordType());
		assertEquals(ChordType.CH2_KLEINE_TERTS, harmonies.get(2).getChord().getChordType());
		assertEquals(ChordType.CH1, harmonies.get(3).getChord().getChordType());
		assertEquals(ChordType.CH2_GROTE_SECONDE, harmonies.get(4).getChord().getChordType());
		assertEquals(ChordType.CH2_KLEINE_TERTS, harmonies.get(5).getChord().getChordType());
		assertEquals(ChordType.CH2_GROTE_SECONDE, harmonies.get(6).getChord().getChordType());
	}

	@Test
	public void testExtractDependantHarmony() {
		List<Note> notes = new ArrayList<>();
		DependantHarmony dependantHarmony = new DependantHarmony();
		dependantHarmony.setChordType(ChordType.CH2_GROTE_SIXT);
		notes.add(note().pos(0).pitch(60).pc(0).voice(0).dep(dependantHarmony).positionWeight(4.0).build());
		notes.add(note().pos(DurationConstants.QUARTER).pitch(60).pc(0).voice(0).positionWeight(1.0).build());
		notes.add(note().pos(DurationConstants.HALF).pitch(62).pc(2).voice(0).positionWeight(2.0).build());
		notes.add(note().pos(DurationConstants.SIX_EIGHTS).pitch(61).pc(1).voice(0).positionWeight(4.0).build());
		notes.add(note().pos(DurationConstants.WHOLE).pitch(59).pc(11).voice(0).positionWeight(3.0).build());
		notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.QUARTER).pitch(60).pc(0).voice(0).positionWeight(1.0).build());

		notes.add(note().pos(0).pitch(64).pc(4).voice(1).positionWeight(3.0).build());
		notes.add(note().pos(DurationConstants.THREE_EIGHTS).pitch(58).pc(10).voice(1).positionWeight(1.0).build());
		notes.add(note().pos(DurationConstants.SIX_EIGHTS).pitch(61).pc(1).voice(1).positionWeight(2.0).build());
        dependantHarmony = new DependantHarmony();
        dependantHarmony.setChordType(ChordType.MAJOR);
		notes.add(note().pos(DurationConstants.WHOLE).pitch(59).pc(11).voice(1).dep(dependantHarmony).positionWeight(3.0).build());
		notes.add(note().pos(DurationConstants.WHOLE * 2).pitch(62).pc(2).voice(1).positionWeight(6.0).build());
		List<CpHarmony> harmonies = harmonyExtractor.extractHarmony(notes, 2);
		assertEquals(8, harmonies.size());
        assertEquals(3, harmonies.get(0).getNotes().size());//+1 dependant
		assertEquals(2, harmonies.get(1).getNotes().size());

		assertEquals(2, harmonies.get(2).getNotes().size());
		assertEquals(60, harmonies.get(2).getNotes().get(0).getPitch());
		assertEquals(58, harmonies.get(2).getNotes().get(1).getPitch());
		assertEquals(2, harmonies.get(3).getNotes().size());
		assertEquals(2, harmonies.get(4).getNotes().size());
		assertEquals(4, harmonies.get(5).getNotes().size());//+2 dependant
	}

	@Test
	public void testExtractHarmony2() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pitch(60).pc(0).voice(0).positionWeight(4.0).len(DurationConstants.QUARTER).build());
		notes.add(note().pos(DurationConstants.QUARTER).pitch(61).pc(1).voice(0).positionWeight(1.0).len(DurationConstants.QUARTER).build());
		notes.add(note().pos(DurationConstants.HALF).pitch(62).pc(2).voice(0).positionWeight(2.0).len(DurationConstants.QUARTER).build());

		notes.add(note().pos(0).pitch(64).pc(4).voice(1).positionWeight(3.0).len(DurationConstants.QUARTER).build());
		List<CpHarmony> harmonies = harmonyExtractor.extractHarmony(notes);
		assertEquals(1, harmonies.size());
        assertEquals(ChordType.CH2_GROTE_TERTS, harmonies.get(0).getChord().getChordType());
	}

	@Test
	public void testExtractHarmony3() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pitch(60).pc(0).voice(0).positionWeight(4.0).len(DurationConstants.QUARTER).build());
		notes.add(note().pos(DurationConstants.QUARTER).pitch(60).pc(0).voice(0).positionWeight(1.0).len(DurationConstants.QUARTER).build());
		notes.add(note().pos(DurationConstants.HALF).pitch(62).pc(2).voice(0).positionWeight(2.0).len(DurationConstants.QUARTER).build());
		notes.add(note().pos(DurationConstants.SIX_EIGHTS).pitch(61).pc(1).voice(0).positionWeight(4.0).len(DurationConstants.QUARTER).build());
		notes.add(note().pos(DurationConstants.WHOLE).pitch(59).pc(11).voice(0).positionWeight(3.0).len(DurationConstants.QUARTER).build());
		notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.QUARTER).pitch(60).pc(0).voice(0).positionWeight(1.0).len(DurationConstants.QUARTER).build());

		notes.add(note().pos(0).pitch(64).pc(4).voice(1).positionWeight(3.0).len(DurationConstants.THREE_EIGHTS).build());
		notes.add(note().pos(DurationConstants.THREE_EIGHTS).pitch(58).pc(10).voice(1).positionWeight(1.0).len(DurationConstants.THREE_EIGHTS).build());
		notes.add(note().pos(DurationConstants.SIX_EIGHTS).pitch(61).pc(1).voice(1).positionWeight(2.0).len(DurationConstants.QUARTER).build());
		notes.add(note().pos(DurationConstants.WHOLE).pitch(59).pc(11).voice(1).positionWeight(3.0).len(DurationConstants.WHOLE).build());
		notes.add(note().pos(DurationConstants.WHOLE * 2).pitch(62).pc(2).voice(1).positionWeight(6.0).len(DurationConstants.QUARTER).build());
//		List<CpHarmony> harmonies = harmonyExtractor.extractHarmony(notes, 2);
		List<CpHarmony> harmonies = harmonyExtractor.extractHarmony(notes);
		assertEquals(7, harmonies.size());
		assertEquals(2, harmonies.get(1).getNotes().size());
		assertEquals(2, harmonies.get(2).getNotes().size());
		assertEquals(60, harmonies.get(2).getNotes().get(0).getPitch());
		assertEquals(58, harmonies.get(2).getNotes().get(1).getPitch());
		assertEquals(2, harmonies.get(3).getNotes().size());
		assertEquals(2, harmonies.get(4).getNotes().size());
	}

	@Test
    public void testExtractHarmony4() {
        List<Note> notes = new ArrayList<>();
        notes.add(note().pos(0).rest().voice(0).positionWeight(4.0).len(DurationConstants.QUARTER).build());
        notes.add(note().pos(DurationConstants.QUARTER).pitch(61).pc(1).voice(0).positionWeight(1.0).len(DurationConstants.QUARTER).build());
        notes.add(note().pos(DurationConstants.HALF).pitch(62).pc(2).voice(0).positionWeight(2.0).len(DurationConstants.QUARTER).build());

        notes.add(note().pos(0).pitch(64).pc(4).voice(1).positionWeight(3.0).len(DurationConstants.HALF).build());
        List<CpHarmony> harmonies = harmonyExtractor.extractHarmony(notes);
        assertEquals(1, harmonies.size());
        assertEquals(ChordType.CH2_KLEINE_TERTS, harmonies.get(0).getChord().getChordType());
    }

    @Test
    public void testExtractHarmony5() {
        List<Note> notes = new ArrayList<>();
        notes.add(note().pos(0).pitch(60).pc(0).voice(0).positionWeight(4.0).len(DurationConstants.QUARTER).build());
        notes.add(note().pos(DurationConstants.QUARTER).pitch(61).pc(1).voice(0).positionWeight(1.0).len(DurationConstants.QUARTER).build());
        notes.add(note().pos(DurationConstants.HALF).pitch(62).pc(2).voice(0).positionWeight(2.0).len(DurationConstants.QUARTER).build());

        notes.add(note().pos(0).pitch(64).pc(4).voice(1).positionWeight(3.0).len(DurationConstants.EIGHT).build());
		notes.add(note().pos(DurationConstants.EIGHT).pitch(58).pc(10).voice(1).positionWeight(1.0).len(DurationConstants.QUARTER).build());
		notes.add(note().pos(DurationConstants.QUARTER + DurationConstants.EIGHT).pitch(57).pc(9).voice(1).positionWeight(1.0).len(DurationConstants.QUARTER).build());

        List<CpHarmony> harmonies = harmonyExtractor.extractHarmony(notes);
        assertEquals(5, harmonies.size());
        assertEquals(ChordType.CH2_GROTE_TERTS, harmonies.get(0).getChord().getChordType());
        assertEquals(ChordType.CH2_GROTE_SECONDE, harmonies.get(1).getChord().getChordType());
        assertEquals(ChordType.CH2_KLEINE_TERTS, harmonies.get(2).getChord().getChordType());
        assertEquals(ChordType.CH2_GROTE_TERTS, harmonies.get(3).getChord().getChordType());
        assertEquals(ChordType.CH2_KWART, harmonies.get(4).getChord().getChordType());
    }

    @Test
    public void testExtractHarmony6() {
        List<Note> notes = new ArrayList<>();
        notes.add(note().pos(0).pitch(60).pc(0).voice(0).positionWeight(4.0).len(DurationConstants.QUARTER).build());
        notes.add(note().pos(DurationConstants.QUARTER).rest().voice(0).positionWeight(1.0).len(DurationConstants.QUARTER).build());
        notes.add(note().pos(DurationConstants.HALF).pitch(62).pc(2).voice(0).positionWeight(2.0).len(DurationConstants.QUARTER).build());
        notes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER).pitch(64).pc(4).voice(0).positionWeight(2.0).len(DurationConstants.QUARTER).build());

        notes.add(note().pos(0).pitch(64).pc(4).voice(1).positionWeight(3.0).len(DurationConstants.HALF + DurationConstants.QUARTER).build());
        notes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER).pitch(55).pc(7).voice(1).positionWeight(1.0).len(DurationConstants.QUARTER).build());


        notes.add(note().pos(0).pitch(67).pc(7).voice(3).positionWeight(4.0).len(DurationConstants.QUARTER).build());
        notes.add(note().pos(DurationConstants.QUARTER).pitch(60).pc(0).voice(3).positionWeight(1.0).len(DurationConstants.QUARTER).build());
        notes.add(note().pos(DurationConstants.HALF).pitch(66).pc(6).voice(3).positionWeight(2.0).len(DurationConstants.QUARTER).build());
        notes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER).pitch(60).pc(0).voice(3).positionWeight(2.0).len(DurationConstants.QUARTER).build());

        List<CpHarmony> harmonies = harmonyExtractor.extractHarmony(notes);
        assertEquals(4, harmonies.size());
        assertEquals(ChordType.MAJOR, harmonies.get(0).getChord().getChordType());
        assertEquals(ChordType.CH2_GROTE_TERTS, harmonies.get(1).getChord().getChordType());
        assertEquals(ChordType.ADD9, harmonies.get(2).getChord().getChordType());
        assertEquals(ChordType.MAJOR_2, harmonies.get(3).getChord().getChordType());
    }


	
	

}
