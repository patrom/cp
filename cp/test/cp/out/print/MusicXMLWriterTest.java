package cp.out.print;

import static cp.model.note.NoteBuilder.note;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.generator.MusicProperties;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.BeamType;
import cp.model.note.Note;
import cp.out.instrument.Instrument;
import cp.out.instrument.strings.CelloSolo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {DefaultConfig.class, VariationConfig.class})
public class MusicXMLWriterTest {
	
	@Autowired
	private MusicXMLWriter musicXMLWriter;
	private List<MelodyBlock> melodyBlocks;
	private Instrument instrument;

	@Before
	public void setUp() throws Exception {
		melodyBlocks = new ArrayList<>();
		instrument = new CelloSolo(0, 1);
	}

	private void addToMelodyBlock(Instrument instrument, List<Note> notes) {
		CpMelody melody = new CpMelody(notes, 0, 0, 48);
		MelodyBlock melodyBlock = new MelodyBlock(5, 0);
		melodyBlock.setInstrument(instrument);
		melodyBlock.addMelodyBlock(melody);
		melodyBlocks.add(melodyBlock);
	}

	@Test
	public void testGenerateMusicXML() throws Exception {
		List<Note> notes = new ArrayList<>();
		notes.add(note().len(15).pc(4).pitch(64).ocatve(4).pos(0).build());
		notes.add(note().len(3).pc(2).pitch(62).ocatve(5).pos(15).build());
		notes.add(note().len(12).pc(11).pitch(59).ocatve(4).pos(18).build());
		notes.add(note().len(24).pc(7).pitch(55).ocatve(4).pos(30).build());
		
		notes.add(note().len(6).pc(2).pitch(62).ocatve(5).pos(54).build());
		
		addToMelodyBlock(instrument, notes);
		musicXMLWriter.generateMusicXMLForMelodies(melodyBlocks, "test");
	}
	
	@Test
	public void testGenerateMusicXMLTriplet() throws Exception {
		List<Note> notes = new ArrayList<>();
		notes.add(note().len(6).pc(4).pitch(64).ocatve(4).pos(0).build());
		notes.add(note().len(2).pc(2).pitch(62).ocatve(5).pos(6).build());
		notes.add(note().len(2).pc(11).pitch(59).ocatve(4).pos(8).build());
		notes.add(note().len(2).pc(7).pitch(55).ocatve(4).pos(10).build());
		
		notes.add(note().len(12).pc(2).pitch(62).ocatve(5).pos(12).build());
		
		addToMelodyBlock(instrument, notes);
		musicXMLWriter.generateMusicXMLForMelodies(melodyBlocks, "testTriplet");
	}
	
	@Test
	public void testGenerateMusicXMLTriplet2() throws Exception {
		List<Note> notes = new ArrayList<>();
		notes.add(note().len(2).pc(4).pitch(64).ocatve(4).pos(0).build());
		notes.add(note().len(6).pc(2).pitch(62).ocatve(5).pos(2).build());
//		notes.add(note().len(2).pc(2).pitch(62).ocatve(5).pos(6).build());
		notes.add(note().len(2).pc(11).pitch(59).ocatve(4).pos(8).build());
		notes.add(note().len(2).pc(7).pitch(55).ocatve(4).pos(10).build());
		
		notes.add(note().len(12).pc(2).pitch(62).ocatve(5).pos(12).build());
		
		addToMelodyBlock(instrument, notes);
		musicXMLWriter.generateMusicXMLForMelodies(melodyBlocks, "testTriplet");
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
//		notes.add(note().len(12).rest().pos(12).build());
//		notes.add(note().len(24).rest().pos(24).build());
		
		
		notes.add(note().len(8).pc(4).pitch(64).ocatve(4).pos(0).beam(BeamType.BEGIN).build());
		notes.add(note().len(8).pc(2).pitch(62).ocatve(5).pos(8).beam(BeamType.CONTINUE).build());
		notes.add(note().len(8).pc(11).pitch(59).ocatve(4).pos(16).beam(BeamType.END).build());
		notes.add(note().len(4).pc(7).pitch(55).ocatve(4).pos(24).beam(BeamType.BEGIN).build());
		notes.add(note().len(4).pc(7).pitch(55).ocatve(4).pos(28).beam(BeamType.CONTINUE).build());
		notes.add(note().len(4).pc(7).pitch(55).ocatve(4).pos(32).beam(BeamType.END).build());
		notes.add(note().len(4).rest().pos(36).beam(BeamType.BEGIN).build());
		notes.add(note().len(4).pc(7).pitch(55).ocatve(4).pos(40).beam(BeamType.CONTINUE).build());
		notes.add(note().len(4).pc(7).pitch(55).ocatve(4).pos(44).beam(BeamType.END).build());
		notes.forEach(n -> n.setTriplet(true));
		addToMelodyBlock(instrument, notes);
		musicXMLWriter.generateMusicXMLForMelodies(melodyBlocks, "testTriplet");
	}
	
//	@Test
//	public void testUpdateTripletNotes(){
//		List<Note> notes = new ArrayList<>();
////		notes.add(NoteBuilder.note().len(12).pc(4).pitch(64).ocatve(4).pos(0).build());
//		notes.add(NoteBuilder.note().len(2).pc(2).pitch(62).ocatve(5).pos(0).build());
////		notes.add(NoteBuilder.note().len(2).pc(11).pitch(59).ocatve(4).pos(2).build());
//		notes.add(NoteBuilder.note().len(2).pc(7).pitch(55).ocatve(4).pos(4).build());
//		
//		notes.add(NoteBuilder.note().len(2).pc(2).pitch(62).ocatve(5).pos(6).build());
//		notes.add(NoteBuilder.note().len(2).pc(11).pitch(59).ocatve(4).pos(8).build());
//		notes.add(NoteBuilder.note().len(2).pc(7).pitch(55).ocatve(4).pos(10).build());
//		notes.add(NoteBuilder.note().len(24).pc(4).pitch(64).ocatve(4).pos(12).build());
//		musicXMLWriter.updateTripletNotes(notes);
//		assertTrue(notes.get(0).isSextuplet());
//		assertFalse(notes.get(5).isSextuplet());
//	}
//	
}
