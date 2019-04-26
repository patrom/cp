package cp.midi;

import cp.AbstractTest;
import cp.DefaultConfig;
import cp.generator.MusicProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sound.midi.InvalidMidiDataException;
import java.io.File;
import java.io.IOException;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DefaultConfig.class)
@ExtendWith(SpringExtension.class)
public class MidiParserTest extends AbstractTest {
	
	private MidiInfo midiInfo;
	@Autowired
	private MidiParser midiparser;
	
	@BeforeEach
	public void setUp() throws InvalidMidiDataException, IOException{
		musicProperties = new MusicProperties();
//		midiInfo = midiparser.readMidi(MidiParserTest.class.getResource("/melodies/Wagner-Tristan.mid").getPath());;
//		midiInfo = midiparser.readMidi(MidiParserTest.class.getResource("/midi").getPath());
        final Resource resource = new FileSystemResource("src/main/resources/midi");
//        final Resource resource = new FileSystemResource("E:/temp/midifileSibelius.mid");
        File dir = resource.getFile();
        for (File midiFile : dir.listFiles()) {
            midiInfo = midiparser.readMidi(midiFile.getPath());
        }
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
