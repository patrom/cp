package cp.midi;

import cp.config.InstrumentConfig;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import cp.out.play.InstrumentMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Component
public class MidiParser {

	private static final Logger LOGGER = LoggerFactory.getLogger(MidiParser.class.getName());
	
	private final int RESOLUTION = DurationConstants.QUARTER;
	public final int TRACK_TIMESIGNATURE = 0x58;
	private final Random random = new Random();
	@Autowired
	private InstrumentConfig instrumentConfig;

	public final int NOTE_ON = 0x90;
	public final int NOTE_OFF = 0x80;
	
	public MidiInfo readMidi(String path) throws InvalidMidiDataException, IOException{
		File file = new File(path);
		return readMidi(file);
	}

	public MidiInfo readMidi(File midiFile)
			throws InvalidMidiDataException, IOException {
		Sequence sequence = MidiSystem.getSequence(midiFile);
		LOGGER.debug("Resolution: " + sequence.getResolution());
		LOGGER.debug("DivisionType: " + sequence.getDivisionType());
		Track[] tracks = sequence.getTracks();
//		int voice = tracks.length - 1;
		Map<Integer, MelodyInstrument> map = new TreeMap<>();
		MidiInfo midiInfo = new MidiInfo();
		for (int j = 0; j < tracks.length; j++) {
			Track track = tracks[j];
			List<Note> notes = new ArrayList<>();
			List<MidiEvent> events = new ArrayList<>();
			for (int i = 0; i < track.size(); i++) {
				MidiEvent event = track.get(i);
				LOGGER.debug("@" + event.getTick() + " ");
				MidiMessage message = event.getMessage();
				if (message instanceof ShortMessage) {
					LOGGER.debug("Voice:" + j);
					int ticks = (int) Math
							.round((event.getTick() / (double) sequence
									.getResolution()) * RESOLUTION) ;
					ShortMessage sm = (ShortMessage) message;
					LOGGER.debug("Pitch: " + sm.getData1() + " ");
					if (sm.getCommand() == ShortMessage.CONTROL_CHANGE || sm.getCommand() == ShortMessage.PROGRAM_CHANGE ) {
						events.add(new MidiEvent(sm, ticks));
					}

					// Er zijn twee manieren om een note-off commando te
					// versturen.
					// // Er bestaat een echt note-off commando, maar de meeste
					// apparaten versturen in plaats van een note-off commando
					// // een note-on commando met velocitywaarde 0. Een noot
					// die aangeschakeld wordt met velocitywaarde 0 is voor midi
					// hetzelfde als die noot uitschakelen.
					if (sm.getCommand() == ShortMessage.NOTE_ON
							&& sm.getData2() != 0) {
						LOGGER.debug("on: " + ticks + " ");
						Note jNote = createNote(j, ticks, sm);
						notes.add(jNote);
					}
					if (sm.getCommand() == ShortMessage.NOTE_OFF
							|| (sm.getCommand() == ShortMessage.NOTE_ON && sm
									.getData2() == 0)) {
						LOGGER.debug("off:" + ticks);
						int key = sm.getData1();
						int l = notes.size();
						for (int k = l - 1; k > -1; k--) {// find note on belonging to note off
							Note noteOn = notes.get(k);
							if (noteOn.getPitch() == key) {
								noteOn.setLength(ticks - noteOn.getPosition());
								noteOn.setDisplayLength(noteOn.getLength());
								break;
							}
						}
					}
				} else if (message instanceof MetaMessage){
					decodeMessage((MetaMessage) message, midiInfo);
				}
			}

			MelodyInstrument melody = new MelodyInstrument(notes, j);
			InstrumentMapping instrumentMapping = instrumentConfig.getInstrumentMapping(j);
			melody.setMidiEvents(events);
			melody.setInstrumentMapping(instrumentMapping);
			map.put(j, melody);
		}
		List<MelodyInstrument> melodies = new ArrayList<>(map.values());
		midiInfo.setMelodies(melodies);
		return midiInfo;
	}

	private MelodyInstrument createMelody(List<Note> notes, int voice) {
//		Note firstNote = notes.get(0);
//		Note lastNote = notes.get(notes.size() - 1);
//		int length = lastNote.getPosition() + lastNote.getLength()
//				- firstNote.getPosition();
		return new MelodyInstrument(notes, voice);
	}

	private Note createNote(int voice, long ticks, ShortMessage sm) {
		Note jNote = new Note();
		int key = sm.getData1();
		jNote.setPitch(key);
		jNote.setPitchClass(key % 12);
		jNote.setOctave((int) Math.floor(key/12));
		jNote.setVoice(voice);
		jNote.setPosition((int) ticks);
		int velocity = sm.getData2();
		jNote.setDynamicLevel(velocity);
		return jNote;
	}

	public float randomTempoFloat() {
		float r = random.nextFloat();
		if (r < 0.5) {
			r = (r * 100) + 100;
		} else {
			r = r * 100;
		}
		//tempo between 50 - 150
		return r;
	}
	
	private String decodeMessage(MetaMessage message, MidiInfo midiInfo){
		byte[]	abData = message.getData();
		String	strMessage = null;
        int type = message.getType();
        switch (message.getType()){
		case 0:
		        int	nSequenceNumber;
		        if (abData.length == 0)
			    nSequenceNumber = 0;
		        else
			    nSequenceNumber = ((abData[0] & 0xFF) << 8) | (abData[1] & 0xFF);
			strMessage = "Sequence Number: " + nSequenceNumber;
			break;
		case 1:
			String	strText = new String(abData);
			strMessage = "Text Event: " + strText;
			break;
		case 3:
			String	strTrackName = new String(abData);
			strMessage = "Sequence/Track Name: " +  strTrackName;
			break;
		case 4:
			String	strInstrumentName = new String(abData); // 	C1 	46
			strMessage = "Instrument Name: " + strInstrumentName;
			break;
		case 5:
			String	strLyrics = new String(abData);
			if (strLyrics.equals("\r\n"))
			    strLyrics = "\\n";
			strMessage = "Lyric: " + strLyrics;
			break;
		case 6:
			String	strMarkerText = new String(abData);
			strMessage = "Marker: " + strMarkerText;
			break;
		case 7:
			String	strCuePointText = new String(abData);
			strMessage = "Cue Point: " + strCuePointText;
			break;
		case 0x20:
			int	nChannelPrefix = abData[0] & 0xFF;
			strMessage = "MIDI Channel Prefix: " + nChannelPrefix;
			break;
		case 0x2F:
			strMessage = "End of Track";
			break;
		case 0x51:
			int	nTempo = ((abData[0] & 0xFF) << 16)
					| ((abData[1] & 0xFF) << 8)
					| (abData[2] & 0xFF);           // tempo in microseconds per beat
			float bpm = convertTempo(nTempo);
			// truncate it to 2 digits after dot
			bpm = (Math.round(bpm*100.0f)/100.0f);
			midiInfo.setTempo((int) bpm);
			break;
		case 0x54:
			strMessage = "SMTPE Offset: "
				+ (abData[0] & 0xFF) + ":"
				+ (abData[1] & 0xFF) + ":"
				+ (abData[2] & 0xFF) + "."
				+ (abData[3] & 0xFF) + "."
				+ (abData[4] & 0xFF);
			break;
		case 0x58:
			String timeSignature = (abData[0] & 0xFF) + "/" + (1 << (abData[1] & 0xFF));
			midiInfo.setTimeSignature(timeSignature);
			break;
		}
		return strMessage;
	}

	// convert from microseconds per quarter note to beats per minute and vice versa
	private float convertTempo(float value) {
		if (value <= 0) {
			value = 0.1f;
		}
		return 60000000.0f / value;
	}
	
}
