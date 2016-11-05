package cp.midi;

import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import cp.out.instrument.Instrument;
import cp.out.play.InstrumentConfig;
import cp.out.play.InstrumentMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Component
public class MidiDevicesUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(MidiDevicesUtil.class.getName());

	private final int RESOLUTION = DurationConstants.QUARTER;
	@Autowired
	private InstrumentConfig instrumentConfig;
	
	public void playOnDevice(Sequence sequence, int tempo, cp.out.instrument.MidiDevice kontakt) {
		LOGGER.info("tempo:" + tempo);
		MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
		for (int i = 0; i < infos.length; i++) {
			try {
//				LOGGER.info(infos[i].getName());
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
//				e.printStackTrace();
			} catch (InvalidMidiDataException e) {
				e.printStackTrace();
			}
		}
	}

	public Sequence createSequence(Map<InstrumentMapping, List<Note>> map, int tempo)
			throws InvalidMidiDataException {
		Sequence sequence = new Sequence(Sequence.PPQ, RESOLUTION);
		for (Entry<InstrumentMapping, List<Note>> entry: map.entrySet()) {
			InstrumentMapping instrumentMapping = entry.getKey();
			createTrackGeneralMidi(sequence, entry.getValue(), instrumentMapping.getInstrument(), tempo, instrumentMapping.getChannel());
		}
		return sequence;
	}
	
//	public Sequence createSequence(List<CpMelody> motives, List<Instrument> instruments)
//			throws InvalidMidiDataException {
//		int motiveSize = motives.size();
//		Sequence sequence = new Sequence(Sequence.PPQ, RESOLUTION);
//		for (int i = 0; i < motiveSize; i++) {
//			List<Note> notes = motives.get(i).getNotes();
//			createTrack(sequence, notes, instruments.get(i));
//		}
//		return sequence;
//	}
	
	public Sequence createSequenceNotes(List<Note> notes, Instrument instrument, int channel)
			throws InvalidMidiDataException {
		Sequence sequence = new Sequence(Sequence.PPQ, RESOLUTION);
		if (!notes.isEmpty()) {
			createTrack(sequence, notes, instrument, channel);
		}
		return sequence;
	}
	
//	public Sequence createSequence(List<CpMelody> motives, Instrument instrument)
//			throws InvalidMidiDataException {
//		int motiveSize = motives.size();
//		Sequence sequence = new Sequence(Sequence.PPQ, RESOLUTION);
//		for (int i = 0; i < motiveSize; i++) {
//			List<Note> notes = motives.get(i).getNotes();
//			createTrack(sequence, notes, instrument);
//		}
//		return sequence;
//	}
	
	public Sequence createSequence(List<MelodyInstrument> melodies)
			throws InvalidMidiDataException {
		Sequence sequence = new Sequence(Sequence.PPQ, RESOLUTION);
		for (MelodyInstrument melodyInstrument : melodies) {
			if (melodyInstrument.getInstrumentMapping() != null) {
				createTrack(sequence, melodyInstrument.getNotes(), melodyInstrument.getInstrumentMapping().getInstrument(), melodyInstrument.getInstrumentMapping().getChannel() );
			}
		}
		return sequence;
	}
	
	public Sequence createSequenceGeneralMidi(List<MelodyInstrument> melodies, int tempo)
			throws InvalidMidiDataException {
		Sequence sequence = new Sequence(Sequence.PPQ, RESOLUTION);
		for (MelodyInstrument melodyInstrument : melodies) {
			if (melodyInstrument.getInstrumentMapping() != null) {
//				createTrackGeneralMidi(sequence, melodyInstrument.getNotes(), melodyInstrument.getInstrument(), tempo);
			}
		}
		return sequence;
	}
	
	public Sequence createSequence(List<MelodyBlock> melodies, int tempo)
			throws InvalidMidiDataException {
		Sequence sequence = new Sequence(Sequence.PPQ, RESOLUTION);
		for (MelodyBlock melody : melodies) {
			int channel = instrumentConfig.getChannelForVoice(melody.getVoice());
			Instrument instrument = instrumentConfig.getInstrumentForVoice(melody.getVoice());
			createTrackGeneralMidi(sequence, melody.getMelodyBlockNotesWithRests(), instrument, tempo, channel);
		}
		return sequence;
	}
	
	private void createTrack(Sequence sequence, List<Note> notes, Instrument instrument, int channel)
			throws InvalidMidiDataException {
		Track track = sequence.createTrack();
		int prevArticulation = 0;
		for (Note notePos : notes) {
			int articulation = instrument.getArticulation(notePos.getArticulation());
			if (articulation != prevArticulation) {
				MidiEvent changeEvent = createInstrumentChange(instrument, articulation, notePos.getPosition(), channel);
				track.add(changeEvent);
				prevArticulation = articulation;
			}
							
			MidiEvent eventOn = createNoteMidiEvent(ShortMessage.NOTE_ON, notePos, notePos.getPosition(), channel);
			track.add(eventOn);
			MidiEvent eventOff = createNoteMidiEvent(ShortMessage.NOTE_OFF, notePos, notePos.getPosition() + notePos.getLength(), channel);
			track.add(eventOff);	
		}
	}
	
	private void createTrackGeneralMidi(Sequence sequence, List<Note> notes, Instrument instrument, int tempo, int channel)
			throws InvalidMidiDataException {
		Track track = sequence.createTrack();

		MidiTempo midiTempo = new MidiTempo();
		MidiEvent midiTempoEvent = midiTempo.getTempoMidiEvent(tempo);
		track.add(midiTempoEvent);
		
		MidiEvent event = createGeneralMidiEvent(instrument, channel);
		track.add(event);
		
		int prevArticulation = 0;
		for (Note notePos : notes) {
			int articulation = instrument.getArticulation(notePos.getArticulation());
			if (articulation != prevArticulation) {
				MidiEvent changeEvent = createInstrumentChange(instrument, articulation, notePos.getPosition(), channel);
				track.add(changeEvent);
				prevArticulation = articulation;
			}
			
			MidiEvent eventOn = createNoteMidiEvent(ShortMessage.NOTE_ON, notePos, notePos.getPosition(), channel);
			track.add(eventOn);
			MidiEvent eventOff = createNoteMidiEvent(ShortMessage.NOTE_OFF, notePos, notePos.getPosition() + notePos.getLength(), channel);
			track.add(eventOff);	
		}
	}

	private MidiEvent createGeneralMidiEvent(Instrument instrument, int channel)
			throws InvalidMidiDataException {
		ShortMessage change = new ShortMessage();
		if (instrument.getGeneralMidi() != null) {
			change.setMessage(ShortMessage.PROGRAM_CHANGE, channel, instrument.getGeneralMidi().getEvent(), 0);
		}
		return new MidiEvent(change, 0);
	}

	private MidiEvent createInstrumentChange(Instrument instrument, int performance, int position, int channel) throws InvalidMidiDataException {
		if (instrument.hasKeySwitch()) {
			Note keySwitch = createKeySwitch(performance);
			return createNoteMidiEvent(ShortMessage.NOTE_ON, keySwitch, position, channel);
		} else {
			return createProgramChangeMidiEvent(channel, instrument.getGeneralMidi().getEvent(), performance);
		}
	}

	private Note createKeySwitch(int articulation) {
		Note keySwitch = new Note();
		keySwitch.setPitch(articulation);
		keySwitch.setDynamicLevel(80);
		return keySwitch;
	}

//	public Sequence createSequenceFromStructures(List<CpMelody> motives, List<Instrument> instruments)
//			throws InvalidMidiDataException {
//		Sequence sequence = new Sequence(Sequence.PPQ, RESOLUTION, motives.size());
//		int i = 0;
//		for (CpMelody motive : motives) {
//			List<Note> notes = motive.getNotes();
//			createTrack(sequence, notes, instruments.get(i));
//			i++;
//		}
//		return sequence;
//	}

	private MidiEvent createNoteMidiEvent(int cmd, Note notePos, int position, int channel)
			throws InvalidMidiDataException {
		ShortMessage note = new ShortMessage();
		if (notePos.isRest()) {
			note.setMessage(cmd, channel, 0, 0);
		} else {
			note.setMessage(cmd, channel,
					notePos.getPitch(), notePos.getDynamicLevel());
		}

		return new MidiEvent(note, position);
	}
	
	private MidiEvent createProgramChangeMidiEvent(int channel, int pc, int position)
			throws InvalidMidiDataException {
		ShortMessage change = new ShortMessage();
		change.setMessage(ShortMessage.PROGRAM_CHANGE, channel, pc, 0);
		return new MidiEvent(change, position);
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
		String TrackName = "midifile track";
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
