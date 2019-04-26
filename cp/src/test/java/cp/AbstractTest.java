package cp;

import cp.evaluation.FitnessObjectiveValues;
import cp.generator.MusicProperties;
import cp.midi.MelodyInstrument;
import cp.midi.MidiDevicePlayer;
import cp.midi.MidiDevicesUtil;
import cp.model.note.Note;
import cp.out.play.InstrumentMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public abstract class AbstractTest {

	protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractTest.class.getName());
	
	@Autowired
	protected MidiDevicesUtil midiDevicesUtil;
	
	protected List<MelodyInstrument> melodies = new ArrayList<>();
	protected FitnessObjectiveValues objectives;
	// protected MusicProperties musicProperties;
	@Autowired
	protected MusicProperties musicProperties;

	protected void playOnKontakt(List<Note> notes, InstrumentMapping instrumentMapping, int tempo, long playTime)  {
		try {
			MelodyInstrument melodyInstrument = new MelodyInstrument(notes, 0);
			melodyInstrument.setInstrumentMapping(instrumentMapping);
			Sequence seq = midiDevicesUtil.createSequenceGeneralMidi(Collections.singletonList(melodyInstrument), tempo, false);
			midiDevicesUtil.playOnDevice(seq, tempo, MidiDevicePlayer.KONTAKT);
			Thread.sleep(playTime);
		} catch (InvalidMidiDataException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	protected void playOnKontakt(List<MelodyInstrument> melodies, int tempo, long playTime ){
		try {
			Sequence sequence = midiDevicesUtil.createSequenceGeneralMidi(melodies, tempo, false);
			midiDevicesUtil.playOnDevice(sequence, tempo, MidiDevicePlayer.KONTAKT);
			Thread.sleep(playTime);
		} catch (InvalidMidiDataException | InterruptedException e) {
			e.printStackTrace();
		}
	}

}
