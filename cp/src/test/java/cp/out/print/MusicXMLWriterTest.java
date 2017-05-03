package cp.out.print;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.BeamType;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import cp.out.instrument.Instrument;
import cp.out.instrument.strings.CelloSolo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import static cp.model.note.NoteBuilder.note;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {DefaultConfig.class, VariationConfig.class})
public class MusicXMLWriterTest {
	
	@Autowired
	private MusicXMLWriter musicXMLWriter;
	private List<MelodyBlock> melodyBlocks;
	private Instrument instrument;
	@javax.annotation.Resource(name="fileTestResource")
	private Resource fileTestResource;
	private String path;

	@Before
	public void setUp() throws Exception {
		melodyBlocks = new ArrayList<>();
		instrument = new CelloSolo();
		path = fileTestResource.getFile().getPath();
	}

	private void addToMelodyBlock(Instrument instrument, List<Note> notes) {
		CpMelody melody = new CpMelody(notes, 0, 0, 48);
		MelodyBlock melodyBlock = new MelodyBlock(5, 0);
		melodyBlock.addMelodyBlock(melody);
		melodyBlocks.add(melodyBlock);
	}

	@Test
	public void testGenerateMusicXML() throws Exception {
		List<Note> notes = new ArrayList<>();
		notes.add(note().len(15).pc(4).pitch(64).octave(4).pos(0).build());
		notes.add(note().len(3).pc(2).pitch(62).octave(5).pos(15).build());
		notes.add(note().len(DurationConstants.QUARTER).pc(11).pitch(59).octave(4).pos(DurationConstants.THREE_EIGHTS).build());
		notes.add(note().len(DurationConstants.HALF).pc(7).pitch(55).octave(4).pos(30).build());
		
		notes.add(note().len(DurationConstants.EIGHT).pc(2).pitch(62).octave(5).pos(54).build());
		
		addToMelodyBlock(instrument, notes);
		musicXMLWriter.generateMusicXMLForMelodies(melodyBlocks, new FileOutputStream(path + "test.xml"));
	}
	
	@Test
	public void testGenerateMusicXMLTriplet() throws Exception {
		List<Note> notes = new ArrayList<>();
		notes.add(note().len(DurationConstants.EIGHT).pc(4).pitch(64).octave(4).pos(0).build());
		notes.add(note().len(2).pc(2).pitch(62).octave(5).pos(6).build());
		notes.add(note().len(2).pc(11).pitch(59).octave(4).pos(8).build());
		notes.add(note().len(2).pc(7).pitch(55).octave(4).pos(10).build());
		
		notes.add(note().len(DurationConstants.QUARTER).pc(2).pitch(62).octave(5).pos(DurationConstants.QUARTER).build());
		
		addToMelodyBlock(instrument, notes);
		musicXMLWriter.generateMusicXMLForMelodies(melodyBlocks, new FileOutputStream(path +  "testTriplet.xml"));
	}
	
	@Test
	public void testGenerateMusicXMLTriplet2() throws Exception {
		List<Note> notes = new ArrayList<>();
		notes.add(note().len(2).pc(4).pitch(64).octave(4).pos(0).build());
		notes.add(note().len(DurationConstants.EIGHT).pc(2).pitch(62).octave(5).pos(2).build());
//		notes.add(note().len(2).pc(2).pitch(62).ocatve(5).pos(6).build());
		notes.add(note().len(2).pc(11).pitch(59).octave(4).pos(8).build());
		notes.add(note().len(2).pc(7).pitch(55).octave(4).pos(10).build());
		
		notes.add(note().len(DurationConstants.QUARTER).pc(2).pitch(62).octave(5).pos(DurationConstants.QUARTER).build());
		
		addToMelodyBlock(instrument, notes);
		musicXMLWriter.generateMusicXMLForMelodies(melodyBlocks,  new FileOutputStream(path + "testTriplet.xml"));
	}
	
	@Test
	public void testFindNoteTypeLength(){
		int length = musicXMLWriter.findNoteTypeLength(12);
		assertEquals(12, length);
		length = musicXMLWriter.findNoteTypeLength(14);
		assertEquals(12, length);
		length = musicXMLWriter.findNoteTypeLength(7);
		assertEquals(6, length);
	}
	
	@Test
	public void testGenerateMusicXMLTripletBeaming() throws Exception {
		List<Note> notes = new ArrayList<>();
//		notes.add(note().len(4).rest().pos(0).beam(BeamType.BEGIN).build());
//		notes.add(note().len(4).pc(7).pitch(55).ocatve(4).pos(4).beam(BeamType.CONTINUE).build());
//		notes.add(note().len(4).pc(7).pitch(55).ocatve(4).pos(8).beam(BeamType.END).build());
//		notes.forEach(n -> n.setTriplet(true));
//		
//		notes.add(note().len(DurationConstants.QUARTER).rest().pos(DurationConstants.QUARTER).build());
//		notes.add(note().len(DurationConstants.HALF).rest().pos(DurationConstants.HALF).build());
		
		
		notes.add(note().len(8).pc(4).pitch(64).octave(4).pos(0).beam(BeamType.BEGIN).build());
		notes.add(note().len(8).pc(2).pitch(62).octave(5).pos(8).beam(BeamType.CONTINUE).build());
		notes.add(note().len(8).pc(11).pitch(59).octave(4).pos(16).beam(BeamType.END).build());
		notes.add(note().len(4).pc(7).pitch(55).octave(4).pos(DurationConstants.HALF).beam(BeamType.BEGIN).build());
		notes.add(note().len(4).pc(7).pitch(55).octave(4).pos(28).beam(BeamType.CONTINUE).build());
		notes.add(note().len(4).pc(7).pitch(55).octave(4).pos(32).beam(BeamType.END).build());
		notes.add(note().len(4).rest().pos(DurationConstants.SIX_EIGHTS).beam(BeamType.BEGIN).build());
		notes.add(note().len(4).pc(7).pitch(55).octave(4).pos(40).beam(BeamType.CONTINUE).build());
		notes.add(note().len(4).pc(7).pitch(55).octave(4).pos(44).beam(BeamType.END).build());
		notes.forEach(n -> n.setTriplet(true));
		addToMelodyBlock(instrument, notes);
		musicXMLWriter.generateMusicXMLForMelodies(melodyBlocks,  new FileOutputStream(path + "testTriplet.xml"));
	}
	
//	@Test
//	public void testUpdateTripletNotes(){
//		List<Note> notes = new ArrayList<>();
////		notes.add(NoteBuilder.note().len(DurationConstants.QUARTER).pc(4).pitch(64).ocatve(4).pos(0).build());
//		notes.add(NoteBuilder.note().len(2).pc(2).pitch(62).ocatve(5).pos(0).build());
////		notes.add(NoteBuilder.note().len(2).pc(11).pitch(59).ocatve(4).pos(2).build());
//		notes.add(NoteBuilder.note().len(2).pc(7).pitch(55).ocatve(4).pos(4).build());
//		
//		notes.add(NoteBuilder.note().len(2).pc(2).pitch(62).ocatve(5).pos(6).build());
//		notes.add(NoteBuilder.note().len(2).pc(11).pitch(59).ocatve(4).pos(8).build());
//		notes.add(NoteBuilder.note().len(2).pc(7).pitch(55).ocatve(4).pos(10).build());
//		notes.add(NoteBuilder.note().len(DurationConstants.HALF).pc(4).pitch(64).ocatve(4).pos(DurationConstants.QUARTER).build());
//		musicXMLWriter.updateTripletNotes(notes);
//		assertTrue(notes.get(0).isSextuplet());
//		assertFalse(notes.get(5).isSextuplet());
//	}
//	
}
