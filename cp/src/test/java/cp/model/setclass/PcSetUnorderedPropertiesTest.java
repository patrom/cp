package cp.model.setclass;

import cp.AbstractTest;
import cp.DefaultConfig;
import cp.midi.MidiConverter;
import cp.midi.MidiInfo;
import cp.midi.MidiParser;
import cp.midi.MidiParserTest;
import cp.model.note.Note;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sound.midi.InvalidMidiDataException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toSet;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefaultConfig.class)
public class PcSetUnorderedPropertiesTest extends AbstractTest {

	private PcSetUnorderedProperties pcSetUnorderedProperties;
	private MidiInfo midiInfo;
	@Autowired
	private MidiParser midiParser;
	
	@Before
	public void setUp() throws InvalidMidiDataException, IOException {
		midiInfo = midiParser.readMidi(MidiParserTest.class.getResource("/melodies/Bach-choral227deel1.mid").getPath());
		melodies = midiInfo.getMelodies();
	}
	
	@Test
	public void testGetForteName() {
		Map<Integer, List<Note>> chords = MidiConverter.extractNoteMapFromMelodies(melodies);
		chords.forEach((i,chord) -> {
			java.util.Set<Integer> pitchClasses = chord.stream()
					.map(note -> note.getPitchClass())
					.collect(toSet());
			if (pitchClasses.size() > 2) {// set class minimum size is 3
				Integer[] integerArray = pitchClasses.toArray(new Integer[pitchClasses.size()]);
				int[] set = ArrayUtils.toPrimitive(integerArray);
				pcSetUnorderedProperties = new PcSetUnorderedProperties(set);
				LOGGER.info(pcSetUnorderedProperties.getForteName());
			}
		});
	}
	
	@Test
	public void testForteName() {
		int[] set = new int[3];
		set[0] = 0;
		set[1] = 4;
		set[2] = 7;
		pcSetUnorderedProperties = new PcSetUnorderedProperties(set);
		Assert.assertEquals("3-11", pcSetUnorderedProperties.getForteName());
	}
	
	private PcSetUnorderedProperties toPcSetUnorderedProperties(int...notes ){
		return new PcSetUnorderedProperties(notes);
	}
	
	@Test
	public void testSetClasses(){
		//Perle String quartet 5 ms 1
		List<PcSetUnorderedProperties> sets = new ArrayList<>();
		sets.add(toPcSetUnorderedProperties(11,8,3,7));
		sets.add(toPcSetUnorderedProperties(0,9,4,7));
		sets.add(toPcSetUnorderedProperties(0,9,4,6));
		sets.add(toPcSetUnorderedProperties(11,8,3,6));
		sets.add(toPcSetUnorderedProperties(10,7,2,8));
		sets.add(toPcSetUnorderedProperties(8,4,1,11));
		sets.add(toPcSetUnorderedProperties(8,3,0,5));
		sets.forEach(s -> LOGGER.info(s.getForteName()));
	}
	
	@Test
	public void testSetClasses3(){
		//webern op 24
		List<PcSetUnorderedProperties> sets = new ArrayList<>();
		sets.add(toPcSetUnorderedProperties(0,1,9));
		sets.add(toPcSetUnorderedProperties(11,10,2));
		sets.add(toPcSetUnorderedProperties(3,7,6));
		sets.add(toPcSetUnorderedProperties(8,4,5));
		sets.forEach(s -> LOGGER.info(s.getForteName()));
	}

}
