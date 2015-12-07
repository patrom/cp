package cp.midi;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;
import javax.sound.midi.Track;
import javax.sound.midi.Transmitter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.out.instrument.Instrument;

@Component
public class MidiDevicesUtil {

	private static Logger LOGGER = LoggerFactory.getLogger(MidiDevicesUtil.class.getName());

	private final int RESOLUTION = 12;
	
	public void playOnDevice(Sequence sequence, int tempo, cp.out.instrument.MidiDevice kontakt) {
		LOGGER.info("tempo:" + tempo);
		MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
		for (int i = 0; i < infos.length; i++) {
			try {
				LOGGER.info(infos[i].toString());
				if (infos[i].getName().equals(kontakt.getName())) {
					final MidiDevice device = MidiSystem
							.getMidiDevice(infos[i]);
					device.open();

					final Sequencer sequencer = MidiSystem.getSequencer(false);

					/*
					 * There is a bug in the Sun jdk1.3/1.4. It prevents correct
					 * termination of the VM. So we have to exit ourselves. To
					 * accomplish this, we register a Listener to the Sequencer.
					 * It is called when there are "meta" events. Meta event 47
					 * is end of track.
					 */
					sequencer.addMetaEventListener(new MetaEventListener() {
						public void meta(MetaMessage event) {//sequencer will close in case of looping files
//							if (event.getType() == 47) {
//								sequencer.close();
//								if (device != null) {
//									device.close();
//								}
//								System.exit(0);
//							}
						}
					});
					sequencer.open();
					
					sequencer.setSequence(sequence);

					Receiver receiver = device.getReceiver();
					Transmitter seqTransmitter = sequencer.getTransmitter();
					seqTransmitter.setReceiver(receiver);
					sequencer.setTempoInBPM(tempo);
					sequencer.start();
					break;
				}
			} catch (MidiUnavailableException e) {
				e.printStackTrace();
			} catch (InvalidMidiDataException e) {
				e.printStackTrace();
			}
		}
	}

	public Sequence createSequence(List<CpMelody> motives, List<Instrument> instruments)
			throws InvalidMidiDataException {
		int motiveSize = motives.size();
		Sequence sequence = new Sequence(Sequence.PPQ, RESOLUTION);
		for (int i = 0; i < motiveSize; i++) {
			List<Note> notes = motives.get(i).getNotes();
			createTrack(sequence, notes, instruments.get(i));
		}
		return sequence;
	}
	
	public Sequence createSequenceNotes(List<Note> notes, Instrument instrument)
			throws InvalidMidiDataException {
		Sequence sequence = new Sequence(Sequence.PPQ, RESOLUTION);
		createTrack(sequence, notes, instrument);
		return sequence;
	}
	
	public Sequence createSequence(List<CpMelody> motives, Instrument instrument)
			throws InvalidMidiDataException {
		int motiveSize = motives.size();
		Sequence sequence = new Sequence(Sequence.PPQ, RESOLUTION);
		for (int i = 0; i < motiveSize; i++) {
			List<Note> notes = motives.get(i).getNotes();
			createTrack(sequence, notes, instrument);
		}
		return sequence;
	}
	
	public Sequence createSequence(List<MelodyInstrument> melodies)
			throws InvalidMidiDataException {
		Sequence sequence = new Sequence(Sequence.PPQ, RESOLUTION);
		for (MelodyInstrument melodyInstrument : melodies) {
			if (melodyInstrument.getInstrument() != null) {
				createTrack(sequence, melodyInstrument.getNotes(), melodyInstrument.getInstrument());
			}
		}
		return sequence;
	}
	
	public Sequence createSequenceGeneralMidi(List<MelodyInstrument> melodies, int tempo)
			throws InvalidMidiDataException {
		Sequence sequence = new Sequence(Sequence.PPQ, RESOLUTION);
		for (MelodyInstrument melodyInstrument : melodies) {
			if (melodyInstrument.getInstrument() != null) {
				createTrackGeneralMidi(sequence, melodyInstrument.getNotes(), melodyInstrument.getInstrument(), tempo);
			}
		}
		return sequence;
	}
	
	public Sequence createSequence(List<MelodyBlock> melodies, int tempo)
			throws InvalidMidiDataException {
		Sequence sequence = new Sequence(Sequence.PPQ, RESOLUTION);
		for (MelodyBlock melody : melodies) {
				createTrackGeneralMidi(sequence, melody.getMelodyBlockNotesWithRests(), melody.getInstrument(), tempo);
		}
		return sequence;
	}
	
	private void createTrack(Sequence sequence, List<Note> notes, Instrument instrument)
			throws InvalidMidiDataException {
		Track track = sequence.createTrack();
		int prevArticulation = 0;
		for (Note notePos : notes) {
			int articulation = instrument.getArticulation(notePos.getArticulation());
			if (articulation != prevArticulation) {
				MidiEvent changeEvent = createInstrumentChange(instrument, articulation, notePos.getPosition());
				track.add(changeEvent);
				prevArticulation = articulation;
			}
							
			MidiEvent eventOn = createNoteMidiEvent(ShortMessage.NOTE_ON, notePos, notePos.getPosition(), instrument.getChannel());
			track.add(eventOn);
			MidiEvent eventOff = createNoteMidiEvent(ShortMessage.NOTE_OFF, notePos, notePos.getPosition() + notePos.getDisplayLength(), instrument.getChannel());
			track.add(eventOff);	
		}
	}
	
	private void createTrackGeneralMidi(Sequence sequence, List<Note> notes, Instrument instrument, int tempo)
			throws InvalidMidiDataException {
		Track track = sequence.createTrack();

		MidiTempo midiTempo = new MidiTempo();
		MidiEvent midiTempoEvent = midiTempo.getTempoMidiEvent(tempo);
		track.add(midiTempoEvent);
		
		MidiEvent event = createGeneralMidiEvent(instrument);
		track.add(event);
		
		int prevArticulation = 0;
		for (Note notePos : notes) {
			int articulation = instrument.getArticulation(notePos.getArticulation());
			if (articulation != prevArticulation) {
				MidiEvent changeEvent = createInstrumentChange(instrument, articulation, notePos.getPosition());
				track.add(changeEvent);
				prevArticulation = articulation;
			}
			
			MidiEvent eventOn = createNoteMidiEvent(ShortMessage.NOTE_ON, notePos, notePos.getPosition(), instrument.getChannel());
			track.add(eventOn);
			MidiEvent eventOff = createNoteMidiEvent(ShortMessage.NOTE_OFF, notePos, notePos.getPosition() + notePos.getDisplayLength(), instrument.getChannel());
			track.add(eventOff);	
		}
	}

	private MidiEvent createGeneralMidiEvent(Instrument instrument)
			throws InvalidMidiDataException {
		ShortMessage change = new ShortMessage();
		change.setMessage(ShortMessage.PROGRAM_CHANGE, instrument.getChannel(), instrument.getGeneralMidi().getEvent(), 0);
		MidiEvent event = new MidiEvent(change, 0);
		return event;
	}

	private MidiEvent createInstrumentChange(Instrument instrument, int performance, int position) throws InvalidMidiDataException {
		if (instrument.isKeySwitch()) {
			Note keySwitch = createKeySwitch(performance);
			MidiEvent change = createNoteMidiEvent(ShortMessage.NOTE_ON, keySwitch, position, instrument.getChannel());
			return change;
		} else {
			MidiEvent event = createProgramChangeMidiEvent(instrument.getChannel(), instrument.getGeneralMidi().getEvent(), performance);
			return event;
		}
	}

	private Note createKeySwitch(int articulation) {
		Note keySwitch = new Note();
		keySwitch.setPitch(articulation);
		keySwitch.setDynamicLevel(80);
		return keySwitch;
	}

	public Sequence createSequenceFromStructures(List<CpMelody> motives, List<Instrument> instruments)
			throws InvalidMidiDataException {
		Sequence sequence = new Sequence(Sequence.PPQ, RESOLUTION, motives.size());
		int i = 0;
		for (CpMelody motive : motives) {
			List<Note> notes = motive.getNotes();
			createTrack(sequence, notes, instruments.get(i));
			i++;
		}
		return sequence;
	}

	private MidiEvent createNoteMidiEvent(int cmd, Note notePos, int position, int channel)
			throws InvalidMidiDataException {
		ShortMessage note = new ShortMessage();
		if (notePos.isRest()) {
			note.setMessage(cmd, channel, 0, 0);
		} else {
			note.setMessage(cmd, channel,
					notePos.getPitch(), notePos.getDynamicLevel());
		}
		
		MidiEvent event = new MidiEvent(note, position);
		return event;
	}
	
	private MidiEvent createProgramChangeMidiEvent(int channel, int pc, int position)
			throws InvalidMidiDataException {
		ShortMessage change = new ShortMessage();
		change.setMessage(ShortMessage.PROGRAM_CHANGE, channel, pc, 0);
		MidiEvent event = new MidiEvent(change, position);
		return event;
	}
	
	public void write(Sequence in, String ouputPath) throws IOException{
		MidiSystem.write(in, 1, new File(ouputPath));//1 = multi-track
	}
	
	// write a meta event to current track at certain tick
    private void writeMetaEvent(int id, byte[] val, int b3, int tick, Track currentTrack) {
        MetaMessage mt = new MetaMessage();
        try {
            mt.setMessage(id, val, b3);
        } catch (InvalidMidiDataException e) {
            System.out.println(e.toString());
        }
        MidiEvent me = new MidiEvent(mt, (long) tick);
        currentTrack.add(me);
    }
	
	public static void main(String[] args) throws IOException, InvalidMidiDataException {
		writeMidi();
	}
	
	public static void writeMidi() throws IOException, InvalidMidiDataException {
		// **** Create a new MIDI sequence with 24 ticks per beat ****
		Sequence s = new Sequence(javax.sound.midi.Sequence.PPQ, 24);

		// **** Obtain a MIDI track from the sequence ****
		Track t = s.createTrack();

		// **** General MIDI sysex -- turn on General MIDI sound set ****
		byte[] b = { (byte) 0xF0, 0x7E, 0x7F, 0x09, 0x01, (byte) 0xF7 };
		SysexMessage sm = new SysexMessage();
		sm.setMessage(b, 6);
		MidiEvent me = new MidiEvent(sm, (long) 0);
		t.add(me);

		// **** set tempo (meta event) ****
		MetaMessage mt = new MetaMessage();
		byte[] bt = { 0x02, (byte) 0x00, 0x00 };
		mt.setMessage(0x51, bt, 3);
		me = new MidiEvent(mt, (long) 0);
		t.add(me);

		// **** set track name (meta event) ****
		mt = new MetaMessage();
		String TrackName = new String("midifile track");
		mt.setMessage(0x03, TrackName.getBytes(), TrackName.length());
		me = new MidiEvent(mt, (long) 0);
		t.add(me);

		// **** set omni on ****
		ShortMessage mm = new ShortMessage();
		mm.setMessage(0xB0, 0x7D, 0x00);
		me = new MidiEvent(mm, (long) 0);
		t.add(me);

		// **** set poly on ****
		mm = new ShortMessage();
		mm.setMessage(0xB0, 0x7F, 0x00);
		me = new MidiEvent(mm, (long) 0);
		t.add(me);

		// **** set instrument to Piano ****
		mm = new ShortMessage();
		mm.setMessage(0xC0, 0x00, 0x00);
		me = new MidiEvent(mm, (long) 0);
		t.add(me);

		// **** note on - middle C ****
		mm = new ShortMessage();
		mm.setMessage(0x90, 0x3C, 0x60);
		me = new MidiEvent(mm, (long) 1);
		t.add(me);

		// **** note off - middle C - 120 ticks later ****
		mm = new ShortMessage();
		mm.setMessage(0x80, 0x3C, 0x40);
		me = new MidiEvent(mm, (long) 121);
		t.add(me);

		// **** set end of track (meta event) 19 ticks later ****
		mt = new MetaMessage();
		byte[] bet = {}; // empty array
		mt.setMessage(0x2F, bet, 0);
		me = new MidiEvent(mt, (long) 140);
		t.add(me);

		// **** write the MIDI sequence to a MIDI file ****
		File f = new File("midifile.mid");
		MidiSystem.write(s, 1, f);
	}

}
