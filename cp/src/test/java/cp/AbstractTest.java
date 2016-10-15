package cp;

import cp.evaluation.FitnessObjectiveValues;
import cp.generator.MusicProperties;
import cp.midi.MelodyInstrument;
import cp.midi.MidiDevicesUtil;
import cp.model.note.Note;
import cp.out.instrument.Instrument;
import cp.out.instrument.MidiDevice;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;
import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public abstract class AbstractTest extends JFrame {

	protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractTest.class.getName());
	
	@Autowired
	protected MidiDevicesUtil midiDevicesUtil;
	
	protected List<MelodyInstrument> melodies = new ArrayList<>();
	protected FitnessObjectiveValues objectives;
	// protected MusicProperties musicProperties;
	@Autowired
	protected MusicProperties musicProperties;

	protected void playOnKontakt(List<Note> notes, Instrument instrument, int tempo, long playTime){
		try {
			MelodyInstrument melodyInstrument = new MelodyInstrument(notes, instrument.getVoice());
			melodyInstrument.setInstrument(instrument);
			Sequence seq = midiDevicesUtil.createSequence(Collections.singletonList(melodyInstrument));
			midiDevicesUtil.playOnDevice(seq, tempo, MidiDevice.KONTAKT);
			Thread.sleep(playTime);
		} catch (InvalidMidiDataException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	protected void playOnKontakt(List<MelodyInstrument> melodies, int tempo, long playTime ){
		try {
			Sequence seq = midiDevicesUtil.createSequence(melodies);
			midiDevicesUtil.playOnDevice(seq, tempo, MidiDevice.KONTAKT);
			Thread.sleep(playTime);
		} catch (InvalidMidiDataException | InterruptedException e) {
			e.printStackTrace();
		}
	}

}
