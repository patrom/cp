package cp.model.harmony;

import cp.DefaultConfig;
import cp.model.note.Note;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static cp.model.note.NoteBuilder.note;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefaultConfig.class)
@ExtendWith(SpringExtension.class)
@DisplayName("CpHarmonyTest")
public class CpHarmonyTest {

	private CpHarmony cpHarmony;

	@BeforeEach
	public void setUp() throws Exception {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(60).octave(5).positionWeight(3.0).build());
		notes.add(note().pos(0).pc(4).pitch(76).octave(6).positionWeight(1.0).build());
		notes.add(note().pos(0).pc(7).pitch(79).octave(6).positionWeight(3.0).build());
		cpHarmony = new CpHarmony(notes, 0);
	}

	@Test
	public void testGetHarmonyWeight() {
		double harmonyWeight = cpHarmony.getHarmonyWeight();
		assertEquals(7.0, harmonyWeight);
	}

	@Test
	public void testGetChord() {
		cpHarmony.toChord();
		Chord chord = cpHarmony.getChord();
		assertEquals(ChordType.MAJOR, chord.getChordType());
	}

	@Test
	public void testRegister() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(5).pitch(65).octave(5).positionWeight(3.0).build());
		notes.add(note().pos(0).pc(11).pitch(59).octave(4).positionWeight(1.0).build());
		notes.add(note().pos(0).pc(0).pitch(60).octave(5).positionWeight(3.0).build());
		cpHarmony = new CpHarmony(notes, 0);
		double register = cpHarmony.getRegister(64);
		assertEquals(1.0, register);
	}

	@Test
	public void testGetBassNote() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(5).pitch(65).octave(5).positionWeight(3.0).build());
		notes.add(note().pos(0).pc(11).pitch(59).octave(4).positionWeight(1.0).build());
		notes.add(note().pos(0).pc(0).pitch(60).octave(5).positionWeight(3.0).build());
		cpHarmony = new CpHarmony(notes, 0);
		int bassNote = cpHarmony.getBassNote();
		assertEquals(11, bassNote);
	}

	@Test
	public void testToChordSize() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(9).pitch(69).octave(5).build());
		notes.add(note().pos(0).pc(6).pitch(66).octave(4).build());
		notes.add(note().pos(0).pc(2).pitch(62).octave(5).build());
		notes.add(note().pos(0).pc(0).pitch(72).octave(5).build());
		cpHarmony = new CpHarmony(notes, 0);
		cpHarmony.toChord(3);
		assertEquals(ChordType.MAJOR, cpHarmony.getLowestChord().getChordType());
	}

	@Test
	public void testToChordSize8() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(11).pitch(71).octave(5).build());
		notes.add(note().pos(0).pc(0).pitch(60).octave(4).build());
		notes.add(note().pos(0).pc(2).pitch(74).octave(5).build());
		notes.add(note().pos(0).pc(4).pitch(64).octave(5).build());
		notes.add(note().pos(0).pc(7).pitch(67).octave(5).build());
		cpHarmony = new CpHarmony(notes, 0);
		cpHarmony.toChord(3);
		assertEquals(ChordType.MAJOR, cpHarmony.getLowestChord().getChordType());
	}

	@Test
	public void contains2NoteAnchor() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pitch(80).octave(5).build());
		notes.add(note().pos(0).pitch(60).octave(4).build());
		notes.add(note().pos(0).pitch(70).octave(5).build());
		notes.add(note().pos(0).pitch(64).octave(5).build());
		notes.add(note().pos(0).pitch(66).octave(5).build());
		cpHarmony = new CpHarmony(notes, 0);
		boolean contains2NoteAnchor = cpHarmony.contains2NoteAnchor();
		assertTrue(contains2NoteAnchor);
	}

	@Test
	public void containsNo2NoteAnchor() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pitch(60).octave(4).build());
		notes.add(note().pos(0).pitch(74).octave(5).build());
		notes.add(note().pos(0).pitch(64).octave(5).build());
		notes.add(note().pos(0).pitch(66).octave(5).build());
		cpHarmony = new CpHarmony(notes, 0);
		boolean contains2NoteAnchor = cpHarmony.contains2NoteAnchor();
		assertFalse(contains2NoteAnchor);
	}

	@Test
	public void contains2NoteAnchor2() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pitch(60).octave(4).build());
		notes.add(note().pos(0).pitch(71).octave(5).build());
		notes.add(note().pos(0).pitch(64).octave(5).build());
		cpHarmony = new CpHarmony(notes, 0);
		boolean contains2NoteAnchor = cpHarmony.contains2NoteAnchor();
		assertTrue(contains2NoteAnchor);
	}

	@Test
	public void containsMinorSecond() {
        List<Integer> pitches = Stream.of(60,64,65).collect(toList());
		boolean contains2NoteAnchor = cpHarmony.containsMinorSecond(pitches);
		assertTrue(contains2NoteAnchor);
	}

	@Test
	public void containsNoMinorSecond() {
	    List<Integer> pitches = Stream.of(60,64,66).collect(toList());
		boolean contains2NoteAnchor = cpHarmony.containsMinorSecond(pitches);
		assertFalse(contains2NoteAnchor);
	}

	@Test
	public void containsNoMinorSecondFlatNinth() {
		List<Integer> pitches = Stream.of(60,64,73).collect(toList());
		boolean contains2NoteAnchor = cpHarmony.containsMinorSecond(pitches);
		assertFalse(contains2NoteAnchor);
	}

	@Test
	public void containsMinorSecond2() {
        List<Integer> pitches = Stream.of(60,63,64,62,69).sorted().collect(toList());
		boolean contains2NoteAnchor = cpHarmony.containsMinorSecond(pitches);
		assertTrue(contains2NoteAnchor);
	}

	@Test
	public void containsMinorSecondOctave() {
		List<Integer> pitches = Stream.of(60,64,77).collect(toList());
		boolean contains2NoteAnchor = cpHarmony.containsMinorSecond(pitches);
		assertTrue(contains2NoteAnchor);
	}

	@Test
	public void containsMinorSecondRoot() {
		List<Integer> pitches = Stream.of(60,61,77).collect(toList());
		boolean contains2NoteAnchor = cpHarmony.containsMinorSecond(pitches);
		assertTrue(contains2NoteAnchor);
	}

	@Test
	public void containsConsecutiveSeconds() {
        List<Integer> pitches = Stream.of(60,63,64,66,68).sorted().collect(toList());
		boolean contains2NoteAnchor = cpHarmony.containsConsecutiveSeconds(pitches);
		assertTrue(contains2NoteAnchor);
	}

	@Test
	public void containsNoConsecutiveSeconds() {
        List<Integer> pitches = Stream.of(60,63,64,66,69).sorted().collect(toList());
		boolean contains2NoteAnchor = cpHarmony.containsConsecutiveSeconds(pitches);
		assertFalse(contains2NoteAnchor);
	}

	@Test
	public void isAdditiveHarmony() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(60).build());
		notes.add(note().pos(0).pc(3).pitch(75).build());
		notes.add(note().pos(0).pc(5).pitch(77).build());
		notes.add(note().pos(0).pc(11).pitch(71).build());
		cpHarmony = new CpHarmony(notes, 0);
        ChordType additiveChord = cpHarmony.getAdditiveChord();
        assertEquals(ChordType.ANCHOR_11, additiveChord);
	}

	@Test
	public void isNotAdditiveHarmony() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(60).octave(4).build());
		notes.add(note().pos(0).pc(3).pitch(63).octave(5).build());
		notes.add(note().pos(0).pc(2).pitch(62).octave(5).build());
		notes.add(note().pos(0).pc(11).pitch(71).octave(5).build());
		cpHarmony = new CpHarmony(notes, 0);
        ChordType additiveChord = cpHarmony.getAdditiveChord();
        assertNull(additiveChord);
	}

	@Test
	public void isNotAdditiveHarmony2() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(60).octave(4).build());
		notes.add(note().pos(0).pc(4).pitch(64).octave(5).build());
		notes.add(note().pos(0).pc(2).pitch(62).octave(5).build());
		notes.add(note().pos(0).pc(11).pitch(71).octave(5).build());
		cpHarmony = new CpHarmony(notes, 0);
        ChordType additiveChord = cpHarmony.getAdditiveChord();
        assertNull(additiveChord);
	}

	@Test
	public void isAdditiveHarmony3() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(60).octave(4).build());
		notes.add(note().pos(0).pc(4).pitch(64).octave(5).build());
		notes.add(note().pos(0).pc(9).pitch(69).octave(5).build());
		cpHarmony = new CpHarmony(notes, 0);
        ChordType additiveChord = cpHarmony.getAdditiveChord();
        assertEquals(ChordType.ANCHOR_49_MIN, additiveChord);
	}

    @Test
    public void isAdditiveHarmony4() {
        List<Note> notes = new ArrayList<>();
        notes.add(note().pos(0).pc(0).pitch(60).octave(4).build());
        notes.add(note().pos(0).pc(9).pitch(69).octave(5).build());
        notes.add(note().pos(0).pc(4).pitch(76).octave(6).build());
        cpHarmony = new CpHarmony(notes, 0);
        ChordType additiveChord = cpHarmony.getAdditiveChord();
        assertEquals(ChordType.ANCHOR_49_MIN, additiveChord);
    }

	@Test
	public void isAnchor() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(48).build());
		notes.add(note().pos(0).pc(4).pitch(64).build());
		notes.add(note().pos(0).pc(9).pitch(69).build());
		notes.add(note().pos(0).pc(5).pitch(75).build());
		cpHarmony = new CpHarmony(notes, 0);
		ChordType additiveChord = cpHarmony.getAdditiveChord();
		assertEquals(ChordType.ANCHOR_49_MIN, additiveChord);
	}

	@Test
	public void isNotAdditiveHarmony4() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(60).build());
		notes.add(note().pos(0).pc(7).pitch(67).build());
		notes.add(note().pos(0).pc(0).pitch(72).build());
		cpHarmony = new CpHarmony(notes, 0);
		ChordType additiveChord = cpHarmony.getAdditiveChord();
		assertNull(additiveChord);
	}

	@Test
	public void isAdditiveHarmonyOctaveDoubling() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(48).build());
		notes.add(note().pos(0).pc(0).pitch(60).build());
		notes.add(note().pos(0).pc(1).pitch(61).build());
		notes.add(note().pos(0).pc(10).pitch(58).build());
		cpHarmony = new CpHarmony(notes, 0);
		ChordType additiveChord = cpHarmony.getAdditiveChord();
		assertEquals(ChordType.ANCHOR_10, additiveChord);
	}

    @Test
    public void isNotAdditiveHarmonyMajor() {
        List<Note> notes = new ArrayList<>();
        notes.add(note().pos(0).pc(0).pitch(60).build());
        notes.add(note().pos(0).pc(4).pitch(76).build());
        notes.add(note().pos(0).pc(7).pitch(67).build());
        cpHarmony = new CpHarmony(notes, 0);
        ChordType additiveChord = cpHarmony.getAdditiveChord();
        assertEquals(ChordType.ANCHOR_7, additiveChord);
    }


    @ParameterizedTest
    @MethodSource("anchor7Provider")
    public void anchor7Test(List<Note> notes) {
        cpHarmony = new CpHarmony(notes, 0);
        ChordType additiveChord = cpHarmony.getAdditiveChord();
        assertEquals(ChordType.ANCHOR_7, additiveChord);
        System.out.print("AdditiveChord: " + additiveChord + ": ");
        System.out.print(notes);
        System.out.println();
    }

    private static Stream anchor7Provider() {
        List<Note> notes = new ArrayList<>();
        notes.add(note().pos(0).pc(0).pitch(60).build());
        notes.add(note().pos(0).pc(7).pitch(67).build());
        notes.add(note().pos(0).pc(9).pitch(69).build());

        List<Note> notes2 = new ArrayList<>();
        notes2.add(note().pos(0).pc(0).pitch(60).build());
        notes2.add(note().pos(0).pc(7).pitch(67).build());
        notes2.add(note().pos(0).pc(11).pitch(71).build());

        List<Note> notes3 = new ArrayList<>();
        notes3.add(note().pos(0).pc(0).pitch(60).build());
        notes3.add(note().pos(0).pc(7).pitch(67).build());
        notes3.add(note().pos(0).pc(10).pitch(70).build());
        notes3.add(note().pos(0).pc(0).pitch(72).build());
        notes3.add(note().pos(0).pc(10).pitch(84).build());

        List<Note> notes4 = new ArrayList<>();
        notes4.add(note().pos(0).pc(0).pitch(60).build());
        notes4.add(note().pos(0).pc(7).pitch(67).build());
        notes4.add(note().pos(0).pc(2).pitch(74).build());
        notes4.add(note().pos(0).pc(5).pitch(77).build());

        List<Note> notes5 = new ArrayList<>();
        notes5.add(note().pos(0).pc(0).pitch(60).build());
        notes5.add(note().pos(0).pc(5).pitch(67).build());
        notes5.add(note().pos(0).pc(4).pitch(78).build());

        List<Note> notes6 = new ArrayList<>();
        notes6.add(note().pos(0).pc(0).pitch(60).build());
        notes6.add(note().pos(0).pc(5).pitch(67).build());
        notes6.add(note().pos(0).pc(1).pitch(73).build());//b9
	    return Stream.of(notes, notes2, notes3, notes4, notes5, notes6);
    }

    @ParameterizedTest
    @MethodSource("noAnchorProvider")
    public void noAnchorTest(List<Note> notes) {
        cpHarmony = new CpHarmony(notes, 0);
        ChordType additiveChord = cpHarmony.getAdditiveChord();
        assertNull(additiveChord);
        System.out.print("AdditiveChord: " + additiveChord + ": ");
        System.out.print(notes);
        System.out.println();
    }

    private static Stream noAnchorProvider() {
        List<Note> notes = new ArrayList<>();
        notes.add(note().pos(0).pc(0).pitch(60).build());
        notes.add(note().pos(0).pc(4).pitch(64).build());
        notes.add(note().pos(0).pc(7).pitch(67).build());

        List<Note> notes2 = new ArrayList<>();
        notes2.add(note().pos(0).pc(0).pitch(60).build());
        notes2.add(note().pos(0).pc(5).pitch(65).build());
        notes2.add(note().pos(0).pc(7).pitch(67).build());

        List<Note> notes3 = new ArrayList<>();
        notes3.add(note().pos(0).pc(0).pitch(60).build());
        notes3.add(note().pos(0).pc(7).pitch(67).build());
        notes3.add(note().pos(0).pc(8).pitch(68).build());

        List<Note> notes4 = new ArrayList<>();
        notes4.add(note().pos(0).pc(0).pitch(60).build());
        notes4.add(note().pos(0).pc(7).pitch(67).build());
        notes4.add(note().pos(0).pc(9).pitch(69).build());
        notes4.add(note().pos(0).pc(11).pitch(71).build());

        List<Note> notes5 = new ArrayList<>();
        notes5.add(note().pos(0).pc(0).pitch(60).build());
        notes5.add(note().pos(0).pc(7).pitch(67).build());

        List<Note> notes6 = new ArrayList<>();
        notes6.add(note().pos(0).pc(0).pitch(60).build());
        notes6.add(note().pos(0).pc(7).pitch(67).build());
        notes6.add(note().pos(0).pc(9).pitch(69).build());
        notes6.add(note().pos(0).pc(10).pitch(70).build());

        List<Note> notes7 = new ArrayList<>();
        notes7.add(note().pos(0).pc(7).pitch(55).build());
        notes7.add(note().pos(0).pc(1).pitch(61).build());
        notes7.add(note().pos(0).pc(3).pitch(63).build());
        notes7.add(note().pos(0).pc(5).pitch(65).build());
        notes7.add(note().pos(0).pc(10).pitch(70).build());//Dom9 inversion not allowed??
        return Stream.of(notes, notes2, notes3, notes4, notes5, notes6, notes7);

    }

    @ParameterizedTest
    @MethodSource("anchor68DomProvider")
    public void anchor68DomTest(List<Note> notes) {
        cpHarmony = new CpHarmony(notes, 0);
        ChordType additiveChord = cpHarmony.getAdditiveChord();
        assertEquals(ChordType.ANCHOR_68_DOM, additiveChord);
        System.out.print("AdditiveChord: " + additiveChord + ": ");
        System.out.print(notes);
        System.out.println();
    }

    private static Stream anchor68DomProvider() {
        List<Note> notes = new ArrayList<>();
        notes.add(note().pos(0).pc(7).pitch(55).build());
        notes.add(note().pos(0).pc(1).pitch(61).build());
        notes.add(note().pos(0).pc(3).pitch(63).build());

        List<Note> notes2 = new ArrayList<>();
        notes2.add(note().pos(0).pc(7).pitch(55).build());
        notes2.add(note().pos(0).pc(1).pitch(61).build());
        notes2.add(note().pos(0).pc(3).pitch(63).build());
        notes2.add(note().pos(0).pc(9).pitch(69).build());

        List<Note> notes3 = new ArrayList<>();
        notes3.add(note().pos(0).pc(7).pitch(55).build());
        notes3.add(note().pos(0).pc(1).pitch(61).build());
        notes3.add(note().pos(0).pc(3).pitch(63).build());
        notes3.add(note().pos(0).pc(0).pitch(72).build());
        notes3.add(note().pos(0).pc(10).pitch(84).build());

        List<Note> notes4 = new ArrayList<>();
        notes4.add(note().pos(0).pc(7).pitch(55).build());
        notes4.add(note().pos(0).pc(1).pitch(61).build());
        notes4.add(note().pos(0).pc(3).pitch(63).build());
        notes4.add(note().pos(0).pc(7).pitch(67).build());
        notes4.add(note().pos(0).pc(0).pitch(72).build());
        notes4.add(note().pos(0).pc(5).pitch(77).build());

        List<Note> notes5 = new ArrayList<>();
        notes5.add(note().pos(0).pc(7).pitch(55).build());
        notes5.add(note().pos(0).pc(1).pitch(61).build());
        notes5.add(note().pos(0).pc(3).pitch(63).build());
        notes5.add(note().pos(0).pc(0).pitch(72).build());
        notes5.add(note().pos(0).pc(9).pitch(69).build());
        notes5.add(note().pos(0).pc(5).pitch(77).build());

        List<Note> notes6 = new ArrayList<>();
        notes6.add(note().pos(0).pc(7).pitch(55).build());
        notes6.add(note().pos(0).pc(1).pitch(61).build());
        notes6.add(note().pos(0).pc(3).pitch(63).build());
        notes6.add(note().pos(0).pc(9).pitch(69).build());
        notes6.add(note().pos(0).pc(0).pitch(72).build());

        List<Note> notes7 = new ArrayList<>();
        notes7.add(note().pos(0).pc(7).pitch(55).build());
        notes7.add(note().pos(0).pc(1).pitch(61).build());
        notes7.add(note().pos(0).pc(3).pitch(63).build());
        notes7.add(note().pos(0).pc(8).pitch(68).build());
        notes7.add(note().pos(0).pc(10).pitch(70).build());
        return Stream.of(notes, notes2, notes3, notes4, notes5, notes6, notes7);
    }

}
