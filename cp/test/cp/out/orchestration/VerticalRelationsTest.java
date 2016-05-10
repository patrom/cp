package cp.out.orchestration;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cp.DefaultConfig;
import cp.model.note.Note;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.brass.FrenchHorn;
import cp.out.instrument.strings.Viola;
import cp.out.instrument.strings.ViolinsI;
import cp.out.orchestration.quality.OrchestralQuality;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DefaultConfig.class)
public class VerticalRelationsTest {

	@Autowired
	private VerticalRelations verticalRelations;
	
	@Before
	public void setUp() throws Exception {
	}


	@Test
	public void testSuperpositionSplit1() {
		fail("Not yet implemented");
	}

	@Test
	public void testSuperpositionSplit3() {
		fail("Not yet implemented");
	}

	@Test
	public void testOneOrchestralQuality() {
		InstrumentNoteMapping instrumentNoteMapping = verticalRelations.oneOrchestralQuality(new int[]{67, 64,60,57}, new ViolinsI(), null);
		Map<Instrument, List<Note>> map = instrumentNoteMapping.getNotesForInstrument();
		List<Note> notes = map.get(new ViolinsI());
		assertEquals(notes.get(0).getPitch(), 67);
		assertEquals(notes.get(1).getPitch(), 64);
		assertEquals(notes.get(2).getPitch(), 60);
		assertEquals(notes.get(3).getPitch(), 57);
	}

	@Test
	public void testOverlapping2() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testEnclosing() {
		InstrumentNoteMapping instrumentNoteMapping = verticalRelations.enclosing(new int[]{67, 64,60,57}, new ViolinsI(), new Viola());
		Map<Instrument, List<Note>> map = instrumentNoteMapping.getNotesForInstrument();
		List<Note> notes = map.get(new ViolinsI());
		assertEquals(notes.get(0).getPitch(), 67);
		assertEquals(notes.get(1).getPitch(), 57);
		notes = map.get(new Viola());
		assertEquals(notes.get(0).getPitch(), 64);
		assertEquals(notes.get(1).getPitch(), 60);
	}
	
	@Test
	public void testCrossing() {
		InstrumentNoteMapping instrumentNoteMapping = verticalRelations.crossing(new int[]{67, 64,60,57}, new ViolinsI(), new Viola());
		Map<Instrument, List<Note>> map = instrumentNoteMapping.getNotesForInstrument();
		List<Note> notes = map.get(new ViolinsI());
		assertEquals(notes.get(0).getPitch(), 67);
		assertEquals(notes.get(1).getPitch(), 60);
		notes = map.get(new Viola());
		assertEquals(notes.get(0).getPitch(), 64);
		assertEquals(notes.get(1).getPitch(), 57);
	}
	
	@Test
	public void testSuperpositionSplit2() {
		InstrumentNoteMapping instrumentNoteMapping = verticalRelations.overlapping2(new int[]{67, 64,60}, new ViolinsI(), new Viola());
		Map<Instrument, List<Note>> map = instrumentNoteMapping.getNotesForInstrument();
		List<Note> notes = map.get(new ViolinsI());
		assertEquals(notes.get(0).getPitch(), 67);
		assertEquals(notes.get(1).getPitch(), 64);
		notes = map.get(new Viola());
		assertEquals(notes.get(0).getPitch(), 64);
		assertEquals(notes.get(1).getPitch(), 60);
	}

}
