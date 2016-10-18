package cp.midi;

import cp.AbstractTest;
import cp.DefaultConfig;
import cp.model.note.Note;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sound.midi.InvalidMidiDataException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DefaultConfig.class)
public class MidiConverterTest extends AbstractTest {
	
	private MidiInfo midiInfo;
	@Autowired
	private MidiParser midiParser;
	
	@Before
	public void setUp() throws InvalidMidiDataException, IOException{
		midiInfo = midiParser.readMidi(MidiParserTest.class.getResource("/melodies/Wagner-Tristan.mid").getPath());
		melodies = midiInfo.getMelodies();
	}

	@Test
	public void testExtractNoteMapFromMelodies() {
		Map<Integer, List<Note>> chords = MidiConverter.extractNoteMapFromMelodies(melodies);
		chords.forEach((k, n) -> LOGGER.info(k + ": " + n));
	}
	
	@Test
	public void testUpdatePositionNotes(){
		MidiConverter.updatePositionNotes(melodies, midiInfo.getTimeSignature());
	}

}