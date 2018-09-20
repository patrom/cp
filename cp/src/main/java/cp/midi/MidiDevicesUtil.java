package cp.midi;

import cp.composition.TwelveToneComposition;
import cp.config.InstrumentConfig;
import cp.config.TextureConfig;
import cp.model.humanize.Humanize;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import cp.model.texture.Texture;
import cp.model.texture.TextureValue;
import cp.out.instrument.Instrument;
import cp.out.instrument.VSLArticulationConverter;
import cp.out.play.InstrumentMapping;
import cp.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

@Component
public class MidiDevicesUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(MidiDevicesUtil.class.getName());
	public static final int PEAK_LEVEL = 10;

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
	@Autowired
	private VSLArticulationConverter vslArticulationConverter;

	@Autowired @Lazy
	@Qualifier(value="twelveToneComposition")
	private TwelveToneComposition twelveToneComposition;

	public void playOnDevice(Sequence sequence, int tempo, MidiDevicePlayer kontakt) {
		LOGGER.info("tempo:" + tempo);
		MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
		for (MidiDevice.Info info : infos) {
			try {
//
//				2- LoopBe Internal MIDI
				if (info.getName().equals(kontakt.getName())) {
//					LOGGER.info(info.getName());
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

//	private List<MidiEvent> createTrack(List<Note> notes, int channel)
//			throws InvalidMidiDataException {
//        List<MidiEvent> events = new ArrayList<>();
//		for (Note notePos : notes) {
//			MidiEvent eventOn = midiEventGenerator.createNoteMidiEvent(ShortMessage.NOTE_ON, notePos, notePos.getPosition(), channel);
//            events.add(eventOn);
//			MidiEvent eventOff = midiEventGenerator.createNoteMidiEvent(ShortMessage.NOTE_OFF, notePos, notePos.getPosition() + notePos.getLength(), channel);
//            events.add(eventOff);
//		}
//		return events;
//	}

	private void createTrackGeneralMidi(Sequence sequence, List<Note> notes, Instrument instrument, int tempo, int channel, boolean isKontakt)
			throws InvalidMidiDataException {
		MidiTempo midiTempo = new MidiTempo();
		MidiEvent midiTempoEvent = midiTempo.getTempoMidiEvent(tempo);

        List<Note> notesNoRest = notes.stream().filter(note -> !note.isRest()).collect(Collectors.toList());
		//Hunmanise notes
        humanize.humanize(notesNoRest, instrument);

		Track trackNotes = sequence.createTrack();

        MidiEvent generalMidiEvent = getGeneralMidiInstrument(instrument, channel);
        trackNotes.add(generalMidiEvent);

//        Track trackMetadata = sequence.createTrack();//controllers,... on seperate track
		TextureValue textureValue = new TextureValue();
		trackNotes.add(midiTempoEvent);
		for (Note note : notesNoRest) {
            createControllerEvents(channel, trackNotes, note);
            createVelocityCrossfadeMidiEvents(channel, trackNotes, note);
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
//				Track trackTexture = sequence.createTrack();
				List<Note> textureNotes = entry.getValue();
                humanize.humanize(textureNotes, instrument);
				for (Note textureNote : textureNotes) {
					createMidiEventsNotes(channel, trackNotes, textureNote);
				}
			}
		}


//		if (!isKontakt) {
//			for (Note note : notesNoRest) {
//				List<MidiEvent> midiEvents = vslArticulationConverter.convertNote(channel, note, instrument);
//				for (MidiEvent midiEvent : midiEvents) {
//                    trackNotes.add(midiEvent);
//				}
//            }
//		}
	}

    private MidiEvent getGeneralMidiInstrument(Instrument instrument, int channel) throws InvalidMidiDataException {
        ShortMessage mm = new ShortMessage();
        mm.setMessage(0xC0, channel, instrument.getGeneralMidi().getEvent(), 0x00);
        return new MidiEvent(mm, (long) 0);
    }

    private void createVelocityCrossfadeMidiEvents(int channel, Track track, Note note) throws InvalidMidiDataException {
		if (note.getHumanization() != null && !note.isRest()) {
			MidiEvent velocityXF = setVelocityXF_On_Off(channel, note);
			track.add(velocityXF);
			MidiEvent midiEventVelodityXF = midiEventGenerator.createControllerChangeMidiEvent(channel, 11, note.getMidiVelocity(), note.getBeforeMidiPosition());
			track.add(midiEventVelodityXF);
			if (note.getHumanization().isLongNote()){
				createVelocityCurve(channel, track, note);
//			int peakPosition = note.getMidiPosition() + RandomUtil.getRandomNumberInRange(note.getMidiPosition() / 4, note.getMidiPosition() / 2);
//			int peakLevel = RandomUtil.getRandomNumberInRange(note.getMidiVelocity() + 5, note.getMidiVelocity() + 15);
//			MidiEvent peakVelodityXF = midiEventGenerator.createControllerChangeMidiEvent(channel, 11, peakLevel, peakPosition);
//			track.add(peakVelodityXF);
//			int endPosition = note.getBeforeMidiPosition() + note.getMidiLength() - 5;
//			MidiEvent endVelodityXF = midiEventGenerator.createControllerChangeMidiEvent(channel, 11, note.getMidiVelocity(), endPosition);
//			track.add(endVelodityXF);
			}
		}
    }

	public void createVelocityCurve(int channel, Track track, Note note) throws InvalidMidiDataException {
		int notePosition = note.getMidiPosition();
		int noteLength = note.getMidiLength();
		if (noteLength < DurationConstants.SIXTEENTH) {
			System.out.println(noteLength);
		}
		int split = RandomUtil.getRandomNumberInRange(5, noteLength - 10);
		int peakLevel = PEAK_LEVEL;
		int positionPerLevelUp =  split / peakLevel;
		int positionPerLevelDown = (noteLength - split) / peakLevel;
		int noteDynamicLevel = note.getDynamicLevel();
		for (int i = 1; i < peakLevel + 1; i++) {
			notePosition = notePosition + positionPerLevelUp;
            int level = noteDynamicLevel + i;
			MidiEvent peakVelodityXF = midiEventGenerator.createControllerChangeMidiEvent(channel, 11, level, notePosition);
			track.add(peakVelodityXF);
		}
		for (int i = peakLevel - 1; i >= 0; i--) {
			notePosition = notePosition + positionPerLevelDown;
            int level = noteDynamicLevel + i;
			MidiEvent peakVelodityXF = midiEventGenerator.createControllerChangeMidiEvent(channel, 11, level, notePosition);
			track.add(peakVelodityXF);
		}
	}


	private MidiEvent setVelocityXF_On_Off(int channel, Note note) throws InvalidMidiDataException {
		if(note.getHumanization().isLongNote()){//velXF on/off
			return midiEventGenerator.createControllerChangeMidiEvent(channel, 28, 127, note.getBeforeMidiPosition());
		} else {
			return midiEventGenerator.createControllerChangeMidiEvent(channel, 28, 0, note.getBeforeMidiPosition());
		}
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
		writeMidi();
	}

    // Meta Message Type Values
    public static final byte META_SEQUENCE_NUMBER = 0x00;
    public static final byte META_TEXT_EVENT = 0x01;
    public static final byte META_COPYRIGHT_NOTICE = 0x02;
    public static final byte META_SEQUENCE_NAME = 0x03;
    public static final byte META_INSTRUMENT_NAME = 0x04;
    public static final byte META_LYRIC = 0x05;
    public static final byte META_MARKER = 0x06;
    public static final byte META_CUE_POINT = 0x07;
    public static final byte META_MIDI_CHANNEL_PREFIX = 0x20;
    public static final byte META_END_OF_TRACK = 0x2F;
    public static final byte META_TEMPO = 0x51;
    public static final byte META_SMTPE_OFFSET = 0x54;
    public static final byte META_TIMESIG = 0x58;
    public static final byte META_KEYSIG = 0x59;
    public static final byte META_VENDOR = 0x7F;

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

//        The status byte 0xFF shows that this is a meta message. The second byte is the meta type 0x20 and signifies that this is a channel prefix meta message. The third byte is 1, which means that one byte follows. The last byte is 2, which means that this is a channel prefix for channel 2. Thus, meta messages that follow are specific to channel 2.

		// **** set tempo (meta event) ****
		MetaMessage mt = new MetaMessage();
		byte[] bt = { 0x02, (byte) 0x00, 0x00 };
		mt.setMessage(0x51, bt, 3);
		me = new MidiEvent(mt, (long) 0);
		t.add(me);

		// **** set track name (meta event) ****
		mt = new MetaMessage();
		String TrackName = "Violin";
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

//        mt = new MetaMessage();
//        byte[] channel = { (byte) 0x01 };//channel
//        mt.setMessage(0x20, channel, 0x01);
//        me = new MidiEvent(mt, (long) 0);
//        t.add(me);

//		//0xFF 0x04 0x04 0x42 0x61 0x73 0x73
//		// **** set instrument to Bass ****
//        mt = new MetaMessage();
//        byte[] name = { (byte) 0x42, 0x61, 0x73, 0x73 };
//        mt.setMessage(0x04, name, 0x04);
//		me = new MidiEvent(mt, (long) 0);
//		t.add(me);

        // **** set instrument to Piano ****
        mm = new ShortMessage();
        mm.setMessage(0xC0, 56, 0x00);//alto sax
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
		File f = new File("E:/temp/midifile.mid");
		MidiSystem.write(s, 1, f);
	}



}
