package cp.midi;

import cp.model.note.Note;
import org.springframework.stereotype.Component;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.ShortMessage;
import java.util.ArrayList;
import java.util.List;

@Component
public class MidiEventGenerator {

    public List<MidiEvent> createMidiEvents(int channel, int position, int programChange, int controllerValue) throws InvalidMidiDataException {
        List<MidiEvent> midiEvents = new ArrayList<>();
        MidiEvent programEvent = createProgramChangeMidiEvent(channel,programChange, position);
        midiEvents.add(programEvent);
        MidiEvent controllerEvent = createControllerChangeMidiEvent(channel, 1 ,controllerValue, position);
        midiEvents.add(controllerEvent);
        return midiEvents;
    }

    public MidiEvent createControllerChangeMidiEvent(int channel, int controller, int value, int position)
            throws InvalidMidiDataException {
        ShortMessage change = new ShortMessage();
        change.setMessage(ShortMessage.CONTROL_CHANGE, channel, controller, value);
        return new MidiEvent(change, position);
    }

    public MidiEvent createPitchBendMidiEvent(int channel, int value, int position)
            throws InvalidMidiDataException {
        ShortMessage change = new ShortMessage();
        change.setMessage(ShortMessage.PITCH_BEND, channel, value , value );
        return new MidiEvent(change, position);
    }

    /**
     * creates a pitch bend event.
     * <br/><br/>
     * @param cumulativeTime  the previous midi event's time value plus the previous midi event's duration (delta time value).
     * @param channel         the midi channel on which the event will be sent.
     * @param pitch           the volume level.
     * Pitch bend has two data bytes ,
     * LSB followed by MSB
     * <br/><br/>
     * @throws InvalidMidiDataException  if the parameter values do not specify a valid midi message.
     */
    public static MidiEvent createEventPitchBend(long cumulativeTime, int channel, int pitch) throws InvalidMidiDataException
    {
        ShortMessage message = new ShortMessage();
        message.setMessage(ShortMessage.PITCH_BEND, channel,pitch & 0x7F,pitch >>7);
        return new MidiEvent(message, cumulativeTime);
    }

    public MidiEvent createProgramChangeMidiEvent(int channel, int program, int position)
            throws InvalidMidiDataException {
        ShortMessage change = new ShortMessage();
        change.setMessage(ShortMessage.PROGRAM_CHANGE, channel, program, 0);
        return new MidiEvent(change, position);
    }

    public MidiEvent createNoteMidiEvent(int cmd, Note notePos, int position, int channel)
            throws InvalidMidiDataException {
        ShortMessage note = new ShortMessage();
        if (notePos.isRest()) {
            note.setMessage(cmd, channel, 0, 0);
        } else {
            note.setMessage(cmd, channel,
                    notePos.getPitch(), notePos.getMidiVelocity());
        }
        return new MidiEvent(note, position);
    }
}
