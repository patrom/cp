package cp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.swing.JFrame;

import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cp.evaluation.FitnessObjectiveValues;
import cp.generator.MusicProperties;
import cp.midi.MelodyInstrument;

public abstract class AbstractTest extends JFrame{
	
	protected static Logger LOGGER = LoggerFactory.getLogger(AbstractTest.class.getName());
	
	protected List<MelodyInstrument> melodies = new ArrayList<>();
	protected FitnessObjectiveValues objectives;
//	protected MusicProperties musicProperties;
	@Autowired
	protected MusicProperties musicProperties;
	@Before
	public void abstractSetUp() throws InvalidMidiDataException, IOException{
//		musicProperties = new MusicProperties();
//		motives = MidiParser.readMidi("/Users/parm/git/neo/neo/src/test/neo/Bach-choral227 deel1.mid");
	}

}
