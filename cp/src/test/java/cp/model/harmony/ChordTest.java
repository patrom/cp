package cp.model.harmony;


import cp.DefaultConfig;
import cp.generator.ChordGenerator;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import cp.util.RowMatrix;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static cp.model.note.NoteBuilder.note;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DefaultConfig.class)
@TestPropertySource(properties = "composition.voices=4")
public class ChordTest {

    @Autowired
    private ChordGenerator chordGenerator;

	@Test
	public void testTriadType() {
		Chord chord = new Chord(-1);
		chord.addPitchClass(11);
		chord.addPitchClass(4);
		chord.addPitchClass(5);
		assertEquals(ChordType.CH3, chord.getChordType());
	}
	
	@Test
	public void testMajorTriadType() {
		Chord chord = new Chord(2);
		chord.addPitchClass(2);
		chord.addPitchClass(6);
		chord.addPitchClass(9);
		assertEquals(ChordType.MAJOR, chord.getChordType(), "Chord type wrong");
        assertEquals(2, chord.getRoot());
	}
	
	@Test
	public void testMajorInv1TriadType() {
		Chord chord = new Chord(6);
		chord.addPitchClass(2);
		chord.addPitchClass(6);
		chord.addPitchClass(9);
		assertEquals(ChordType.MAJOR_1, chord.getChordType(), "Chord type wrong");
        assertEquals(2, chord.getRoot());
	}
	
	@Test
	public void testMajorInv2TriadType() {
		Chord chord = new Chord(9);
		chord.addPitchClass(2);
		chord.addPitchClass(6);
		chord.addPitchClass(9);
		assertEquals(ChordType.MAJOR_2, chord.getChordType(), "Chord type wrong");
        assertEquals(2, chord.getRoot());
	}
	
	@Test
	public void testMinorTriadType() {
		Chord chord = new Chord(2);
		chord.addPitchClass(2);
		chord.addPitchClass(5);
		chord.addPitchClass(9);
		assertEquals(ChordType.MINOR, chord.getChordType(), "Chord type wrong");
        assertEquals(2, chord.getRoot());
	}
	
	@Test
	public void testMinorInv1TriadType() {
		Chord chord = new Chord(5);
		chord.addPitchClass(2);
		chord.addPitchClass(5);
		chord.addPitchClass(9);
		assertEquals(ChordType.MINOR_1, chord.getChordType(), "Chord type wrong");
        assertEquals(2, chord.getRoot());
	}
	
	@Test
	public void testMinorInv2TriadType() {
		Chord chord = new Chord(9);
		chord.addPitchClass(2);
		chord.addPitchClass(5);
		chord.addPitchClass(9);
		assertEquals(ChordType.MINOR_2, chord.getChordType(), "Chord type wrong");
        assertEquals(2, chord.getRoot());
	}
	
	@Test
	public void testCH3Type() {
		Chord chord = new Chord(-1);
		chord.addPitchClass(2);
		chord.addPitchClass(0);
		chord.addPitchClass(9);
		assertEquals(ChordType.CH3, chord.getChordType(), "Chord type wrong");
	}
	
	@Test
	public void testDOMType() {
		Chord chord = new Chord(-1);
		chord.addPitchClass(11);
		chord.addPitchClass(7);
		chord.addPitchClass(5);
		assertEquals(ChordType.DOM, chord.getChordType(), "Chord type wrong");
	}
	
	@Test
	public void testCH2Kwint() {
		Chord chord = new Chord(2);
		chord.addPitchClass(2);
		chord.addPitchClass(2);
		chord.addPitchClass(9);
		assertEquals(ChordType.CH2_KWINT, chord.getChordType(), "Chord type wrong");
	}
	
	@Test
	public void testMajor7Type() {
		Chord chord = new Chord(0);
		chord.addPitchClass(0);
		chord.addPitchClass(4);
		chord.addPitchClass(7);
		chord.addPitchClass(11);
		assertEquals(ChordType.MAJOR7, chord.getChordType(), "Chord type wrong");
	}
	
	@Test
	public void testMajor7Inv1Type() {
		Chord chord = new Chord(4);
		chord.addPitchClass(0);
		chord.addPitchClass(4);
		chord.addPitchClass(7);
		chord.addPitchClass(11);
		assertEquals(ChordType.MAJOR7_1, chord.getChordType(), "Chord type wrong");
	}
	
	@Test
	public void testMajor7Inv2Type() {
		Chord chord = new Chord(7);
		chord.addPitchClass(0);
		chord.addPitchClass(4);
		chord.addPitchClass(7);
		chord.addPitchClass(11);
		assertEquals(ChordType.MAJOR7_2, chord.getChordType(), "Chord type wrong");
	}
	
	@Test
	public void testMajor7Inv3Type() {
		Chord chord = new Chord(11);
		chord.addPitchClass(0);
		chord.addPitchClass(4);
		chord.addPitchClass(7);
		chord.addPitchClass(11);
		assertEquals(ChordType.MAJOR7_3, chord.getChordType(), "Chord type wrong");
	}
	
	@Test
	public void testMinor7Type() {
		Chord chord = new Chord(0);
		chord.addPitchClass(0);
		chord.addPitchClass(3);
		chord.addPitchClass(7);
		chord.addPitchClass(10);
		assertEquals(ChordType.MINOR7, chord.getChordType(), "Chord type wrong");
	}
	
	@Test
	public void testMinor7Inv1Type() {
		Chord chord = new Chord(3);
		chord.addPitchClass(0);
		chord.addPitchClass(3);
		chord.addPitchClass(7);
		chord.addPitchClass(10);
		assertEquals(ChordType.MINOR7_1, chord.getChordType(), "Chord type wrong");
	}
	
	@Test
	public void testMinor7Inv2Type() {
		Chord chord = new Chord(7);
		chord.addPitchClass(0);
		chord.addPitchClass(3);
		chord.addPitchClass(7);
		chord.addPitchClass(10);
		assertEquals(ChordType.MINOR7_2, chord.getChordType(), "Chord type wrong");
	}
	
	@Test
	public void testMinor7Inv3Type() {
		Chord chord = new Chord(10);
		chord.addPitchClass(0);
		chord.addPitchClass(3);
		chord.addPitchClass(7);
		chord.addPitchClass(10);
		assertEquals(ChordType.MINOR7_3, chord.getChordType(), "Chord type wrong");
	}
	
	@Test
	public void testChordNotes() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(60).octave(5).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(4).pitch(76).octave(6).build());
		notes.add(note().pos(DurationConstants.WHOLE).pc(7).pitch(79).octave(6).build());
		Chord chord = new Chord(0, notes);
		assertEquals(ChordType.MAJOR, chord.getChordType(), "Chord type wrong");
	}
	
	@Test
	public void testInterval4() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(60).octave(5).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(4).pitch(76).octave(6).build());
		Chord chord = new Chord(0, notes);
		assertEquals(ChordType.CH2_GROTE_TERTS, chord.getChordType(), "Chord type wrong");
	}
	
	@Test
	public void testInterval8() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).pc(0).pitch(60).octave(5).build());
		notes.add(note().pos(DurationConstants.QUARTER).pc(4).pitch(76).octave(6).build());
		Chord chord = new Chord(4, notes);
		assertEquals(ChordType.CH2_KLEINE_SIXT, chord.getChordType(), "Chord type wrong");
	}

    @Test
    void testDomAndHalfsDimChords() {
        int[] generatedPitchClasses = chordGenerator.generatePitchClasses("4-27");
        List<Integer> pitchClasses = Arrays.stream(generatedPitchClasses).boxed().collect(toList());
        RowMatrix rowMatrix = new RowMatrix(generatedPitchClasses.length, pitchClasses);
        for (int i = 0; i < generatedPitchClasses.length; i++) {
            int[] row = rowMatrix.getRow(i);
            Arrays.sort(row);
            createChord(row);

            int[] column = rowMatrix.getColumn(i);
            Arrays.sort(column);
            createChord(column);
        }
    }

    @Test
    void testMinor7Chords() {
        int[] generatedPitchClasses = chordGenerator.generatePitchClasses("4-26");
        List<Integer> pitchClasses = Arrays.stream(generatedPitchClasses).boxed().collect(toList());
        RowMatrix rowMatrix = new RowMatrix(generatedPitchClasses.length, pitchClasses);
        for (int i = 0; i < generatedPitchClasses.length; i++) {
            int[] row = rowMatrix.getRow(i);
            Arrays.sort(row);
            createChord(row);

            int[] column = rowMatrix.getColumn(i);
            Arrays.sort(column);
            createChord(column);
        }
    }

    @Test
    void testMajorChords() {
        int[] generatedPitchClasses = chordGenerator.generatePitchClasses("4-20");
        List<Integer> pitchClasses = Arrays.stream(generatedPitchClasses).boxed().collect(toList());
        RowMatrix rowMatrix = new RowMatrix(generatedPitchClasses.length, pitchClasses);
        for (int i = 0; i < generatedPitchClasses.length; i++) {
            int[] row = rowMatrix.getRow(i);
            Arrays.sort(row);
            createChord(row);

            int[] column = rowMatrix.getColumn(i);
            Arrays.sort(column);
            createChord(column);
        }
    }

    private void createChord(int[] pitchClasses) {
        Chord chord = new Chord(0, new ArrayList<>());
        for (int generatedPitchClass : pitchClasses) {
            chord.addPitchClass(generatedPitchClass);
        }
        ChordType chordType = chord.getChordType();
        System.out.print(Arrays.toString(pitchClasses) + " , ");
        System.out.println(chordType + ", root:" + chord.getRoot());
    }

}
