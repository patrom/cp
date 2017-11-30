package cp.midi;

import cp.config.InstrumentConfig;
import cp.config.TextureConfig;
import cp.model.humanize.Humanize;
import cp.model.melody.MelodyBlock;
import cp.model.note.Dynamic;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import cp.model.texture.Texture;
import cp.model.texture.TextureValue;
import cp.out.instrument.Articulation;
import cp.out.instrument.Instrument;
import cp.out.instrument.Technical;
import cp.out.play.InstrumentMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

@Component
public class MidiDevicesUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(MidiDevicesUtil.class.getName());

    private final int RESOLUTION = DurationConstants.QUARTER;
    @Autowired
    private InstrumentConfig instrumentConfig;
    @Autowired
    private MidiEventConverter midiEventConverter;
	@Autowired
    private TextureConfig textureConfig;
	@Autowired
	private Texture texture;
    @Autowired
	private Humanize humanize;
	@Autowired
	private MidiEventGenerator midiEventGenerator;

	public void playOnDevice(Sequence sequence, int tempo, MidiDevicePlayer kontakt) {
		LOGGER.info("tempo:" + tempo);
		MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
		for (MidiDevice.Info info : infos) {
			try {
//				LOGGER.info(info.getName());
//				2- LoopBe Internal MIDI
				if (info.getName().equals(kontakt.getName())) {
					final MidiDevice device = MidiSystem.getMidiDevice(info);
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
					if (tempo > 0) {
						sequencer.setTempoInBPM(tempo);
					}
					sequencer.start();
					break;
				}
			} catch (MidiUnavailableException e) {
//				e.printStackTrace();
			} catch (InvalidMidiDataException e) {
//				e.printStackTrace();
			}
		}
	}

	public Sequence createSequence(Map<InstrumentMapping, List<Note>> map, int tempo)
			throws InvalidMidiDataException {
		Sequence sequence = new Sequence(Sequence.PPQ, RESOLUTION);
		for (Entry<InstrumentMapping, List<Note>> entry: map.entrySet()) {
			InstrumentMapping instrumentMapping = entry.getKey();
			createTrackGeneralMidi(sequence, entry.getValue(), instrumentMapping.getInstrument(), tempo, instrumentMapping.getChannel(), false);
		}
		return sequence;
	}

	public Sequence createSequence(List<MelodyInstrument> melodies)
			throws InvalidMidiDataException {
		Sequence sequence = new Sequence(Sequence.PPQ, RESOLUTION);
		for (MelodyInstrument melodyInstrument : melodies) {
			if (melodyInstrument.getInstrumentMapping() != null) {
                Track track = sequence.createTrack();
                List<MidiEvent> events = createTrack(melodyInstrument.getNotes(), melodyInstrument.getInstrumentMapping().getChannel() );
                events.addAll(melodyInstrument.getMidiEvents());
                Collections.sort(events, new Comparator<MidiEvent>() {
                    @Override
                    public int compare(MidiEvent event1, MidiEvent event2) {
                        if (event1.getTick() < event2.getTick()) {
                            return -1;
                        }
                        if (event1.getTick() < event2.getTick()) {
                            return 1;
                        }
                        return 0;
                    }
                });
                for (MidiEvent event : events) {
                    track.add(event);
                }
            }
		}
		return sequence;
	}

	public Sequence createSequenceGeneralMidi(List<MelodyInstrument> melodies, int tempo, boolean isKontakt)
			throws InvalidMidiDataException {
		Sequence sequence = new Sequence(Sequence.PPQ, RESOLUTION);
		for (MelodyInstrument melodyInstrument : melodies) {
			InstrumentMapping instrumentMapping = melodyInstrument.getInstrumentMapping();
			createTrackGeneralMidi(sequence, melodyInstrument.getNotes(), instrumentMapping.getInstrument(), tempo, instrumentMapping.getChannel(), isKontakt);
		}
		return sequence;
	}

	public Sequence createSequence(List<MelodyBlock> melodies, int tempo)
			throws InvalidMidiDataException {
		Sequence sequence = new Sequence(Sequence.PPQ, RESOLUTION);
		for (MelodyBlock melody : melodies) {
			InstrumentMapping instrumentMapping = instrumentConfig.getInstrumentMappingForVoice(melody.getVoice());
			createTrackGeneralMidi(sequence, melody.getMelodyBlockNotesWithRests(), instrumentMapping.getInstrument(), tempo, instrumentMapping.getChannel(), false);
		}
		return sequence;
	}

	private List<MidiEvent> createTrack(List<Note> notes, int channel)
			throws InvalidMidiDataException {
        List<MidiEvent> events = new ArrayList<>();
		for (Note notePos : notes) {
			MidiEvent eventOn = midiEventGenerator.createNoteMidiEvent(ShortMessage.NOTE_ON, notePos, notePos.getPosition(), channel);
            events.add(eventOn);
			MidiEvent eventOff = midiEventGenerator.createNoteMidiEvent(ShortMessage.NOTE_OFF, notePos, notePos.getPosition() + notePos.getLength(), channel);
            events.add(eventOff);
		}
		return events;
	}

	private void createTrackGeneralMidi(Sequence sequence, List<Note> notes, Instrument instrument, int tempo, int channel, boolean isKontakt)
			throws InvalidMidiDataException {
		MidiTempo midiTempo = new MidiTempo();
		MidiEvent midiTempoEvent = midiTempo.getTempoMidiEvent(tempo);

		//Hunmanise notes
        humanize.humanize(notes, instrument);

		Track trackNotes = sequence.createTrack();
        Track trackMetadata = sequence.createTrack();
		TextureValue textureValue = new TextureValue();
		trackNotes.add(midiTempoEvent);
		for (Note note : notes) {
            createControllerEvents(channel, trackMetadata, note);
            createVelocityCrossfadeMidiEvents(channel, trackMetadata, note);
			createMidiEventsNotes(channel, trackNotes, note);

            if(note.hasTexture()){
                List<Note> textureNotes = texture.getTextureForNote(note);
                if (!textureNotes.isEmpty()) {
					for (int i = 0; i < textureNotes.size(); i++) {
						textureValue.addTextureNotes(i, textureNotes.get(i));
					}
                }
            }
		}

		if(!textureValue.getTextureNotesPerLine().isEmpty()){
			for (Entry<Integer,List<Note>> entry : textureValue.getTextureNotesPerLine().entrySet()) {
				Track trackTexture = sequence.createTrack();
				List<Note> textureNotes = entry.getValue();
                humanize.humanize(textureNotes, instrument);
				for (Note textureNote : textureNotes) {
					createMidiEventsNotes(channel, trackTexture, textureNote);
				}
			}
		}

//		MidiEvent event = createGeneralMidiEvent(instrument, channel);
//		track.add(event);

		if (!isKontakt) {
			Dynamic prevDynamic = null;
			Technical prevTechinal = null;
			Articulation prevArticulation = null;
			for (Note note : notes) {
                if (!note.isRest()) {
                    Technical technical = note.getTechnical();
                    List<MidiEvent> technicalEvents;
                    if(prevArticulation != note.getArticulation() || prevDynamic != note.getDynamic() || technical != prevTechinal){
                        technicalEvents = midiEventConverter.convertTechnical(channel, note, instrument);
                        for (MidiEvent midiEvent : technicalEvents) {
                            trackMetadata.add(midiEvent);
                        }
                        prevTechinal = technical;
                    }
                    Articulation articulation = note.getArticulation();
                    if (articulation != null) {
                        List<MidiEvent> articulationEvents = midiEventConverter.convertArticulation(channel, note, instrument);
                        for (MidiEvent midiEvent : articulationEvents) {
                            trackMetadata.add(midiEvent);
                        }
                        prevArticulation = articulation;
                    }

                    Dynamic dynamic = note.getDynamic();
                    if(dynamic != prevDynamic){
                        List<MidiEvent> dynamicEvents = midiEventConverter.convertDynamic(channel, note, instrument);
                        for (MidiEvent midiEvent : dynamicEvents) {
                            trackMetadata.add(midiEvent);
                        }
                        prevDynamic = dynamic;
                    }
                }
            }
		}
	}

    private void createVelocityCrossfadeMidiEvents(int channel, Track track, Note note) throws InvalidMidiDataException {
        MidiEvent velocityXF = setVelocityXF_On_Off(channel, note);
        track.add(velocityXF);
        MidiEvent midiEventVelodityXF = midiEventGenerator.createControllerChangeMidiEvent(channel, 11, note.getMidiVelocity(), note.getBeforeMidiPosition());
        track.add(midiEventVelodityXF);
    }

    private MidiEvent setVelocityXF_On_Off(int channel, Note note) throws InvalidMidiDataException {
        if(note.getHumanization().isLongNote()){//velXF on/off
            return midiEventGenerator.createControllerChangeMidiEvent(channel, 28, 127, note.getBeforeMidiPosition());
        } else {
            return midiEventGenerator.createControllerChangeMidiEvent(channel, 28, 0, note.getBeforeMidiPosition());
        }

        //long notes -> create velocity crossfade curve??
    }

    private void createControllerEvents(int channel, Track track, Note note) throws InvalidMidiDataException {
        //create atteck
        MidiEvent midiEventAttack = midiEventGenerator.createControllerChangeMidiEvent(channel, 22, note.getMidiAttack(), note.getBeforeMidiPosition());
        track.add(midiEventAttack);
        //create intonation
        MidiEvent midiEventIntonation = midiEventGenerator.createPitchBendMidiEvent(channel,  note.getMidiIntonation(),  note.getBeforeMidiPosition());
        track.add(midiEventIntonation);
    }

    private void createMidiEventsNotes(int channel, Track trackNotes, Note note) throws InvalidMidiDataException {
		MidiEvent eventOn = midiEventGenerator.createNoteMidiEvent(ShortMessage.NOTE_ON, note, note.getMidiPosition(), channel);
		trackNotes.add(eventOn);
		MidiEvent eventOff = midiEventGenerator.createNoteMidiEvent(ShortMessage.NOTE_OFF, note, note.getMidiPosition() + note.getMidiLength(), channel);
		trackNotes.add(eventOff);
	}

	public void write(Sequence in, String ouputPath) throws IOException{
		MidiSystem.write(in, 1, new File(ouputPath));//1 = multi-track
	}

	public static void main(String[] args) throws IOException, InvalidMidiDataException {
		MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
		for (MidiDevice.Info info : infos) {
			System.out.println(info);
		}
//		writeMidi();
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
