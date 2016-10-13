package cp.midi;

import cp.AbstractTest;
import cp.DefaultConfig;
import cp.generator.MusicProperties;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sound.midi.InvalidMidiDataException;
import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DefaultConfig.class)
public class MidiParserTest extends AbstractTest {
	
	private MidiInfo midiInfo;
	@Autowired
	private MidiParser midiparser;
	
	@Before
	public void setUp() throws InvalidMidiDataException, IOException{
		musicProperties = new MusicProperties();
		midiInfo = midiparser.readMidi(MidiParserTest.class.getResource("/melodies/Wagner-Tristan.mid").getPath());
		melodies = midiInfo.getMelodies();
	}
	
	@Test
	public void testMidiInfo() {
		melodies.forEach(n -> System.out.println(n.getNotes()));
		String timeSignature = midiInfo.getTimeSignature();
		System.out.println(timeSignature);
		System.out.println(midiInfo.getTempo());
		String[] split = timeSignature.split("/");
	}

}
