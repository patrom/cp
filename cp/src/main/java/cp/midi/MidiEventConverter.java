package cp.midi;

import cp.model.note.Note;
import cp.out.instrument.Instrument;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.ShortMessage;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by prombouts on 1/04/2017.
 */
public abstract class MidiEventConverter {

    public abstract List<MidiEvent> convertArticulation(int channel, Note note, Instrument instrument) throws InvalidMidiDataException;

    public abstract List<MidiEvent> convertDynamic(int channel, Note note, Instrument instrument) throws InvalidMidiDataException;

    public abstract List<MidiEvent> convertTechnical(int channel, Note note, Instrument instrument) throws InvalidMidiDataException;

    protected List<MidiEvent> createMidiEvents(int channel, Note note, int programChange, int controllerValue) throws InvalidMidiDataException {
        List<MidiEvent> midiEvents = new ArrayList<>();
        MidiEvent programEvent = createProgramChangeMidiEvent(channel,programChange, note.getPosition());
        midiEvents.add(programEvent);
        MidiEvent controllerEvent = createModulationWheelControllerChangeMidiEvent(channel,controllerValue, note.getPosition());
        midiEvents.add(controllerEvent);
        return midiEvents;
    }

    protected MidiEvent createModulationWheelControllerChangeMidiEvent(int channel, int value, int position)
            throws InvalidMidiDataException {
        ShortMessage change = new ShortMessage();
        change.setMessage(ShortMessage.CONTROL_CHANGE, channel, 1, value);
        return new MidiEvent(change, position);
    }

    protected MidiEvent createProgramChangeMidiEvent(int channel, int program, int position)
            throws InvalidMidiDataException {
        ShortMessage change = new ShortMessage();
        change.setMessage(ShortMessage.PROGRAM_CHANGE, channel, program, 0);
        return new MidiEvent(change, position);
    }

    public List<MidiEvent> defaultConverter(String defaultPlaying, int channel, Note note, Instrument instrument) throws InvalidMidiDataException {
        switch (defaultPlaying) {
            case "STACCATO":
                return createMidiEvents(channel, note, 0, 5);
            case "DETACHE":
                return createMidiEvents(channel, note, 0, 15);
            case "SUSTAIN_VIBRATO":
                return createMidiEvents(channel, note, 10, 25);
            case "LEGATO":
                return createMidiEvents(channel, note, 0, 65);

//                case TENUTO:
//                    return createMidiEvents(channel, note, 3, 80);
//                case SPICCATO:
//                    return createMidiEvents(channel, note, 2, 20);
//            case "PIZZICATO":
//                return createMidiEvents(channel, note, 1, 70);
//            case "TREMELO":
//                return createMidiEvents(channel, note, 0, 120);
//            case "PONTICELLO_STACCATO":
//                return createMidiEvents(channel, note, 0, 20);
//            case "PONTICELLO_SUSTAIN":
//                return createMidiEvents(channel, note, 0, 10);
//            case "PONTICELL_TREMELO":
//                return createMidiEvents(channel, note, 0, 120);
//            case "PORTAMENTO":
//                return createMidiEvents(channel, note, 1, 90);
//                case FORTEPIANO:
//                    return createMidiEvents(channel, note, 1, 30);


//            case "HARMONIC":
//                return createMidiEvents(channel, note, 0, 90);
//            case "REPEATLEGATO":
//                return createMidiEvents(channel, note, 1, 70);
//                case SFORZANDO:
//                    return createMidiEvents(channel, note, 1, 80);
            default:
                throw new IllegalArgumentException("Default playing unknown: " + defaultPlaying);
        }
    }


    }
