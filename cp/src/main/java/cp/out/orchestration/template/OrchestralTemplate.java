package cp.out.orchestration.template;

import cp.model.note.Note;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.ShortMessage;
import java.util.ArrayList;
import java.util.List;

public abstract class OrchestralTemplate {

    public List<MidiEvent> legatoOrchestralStrings(Note note, int channel) throws InvalidMidiDataException{
        return null;
    }

    public List<MidiEvent> staccatoOrchestralStrings(Note note, int channel) throws InvalidMidiDataException{
        return null;
    }

    public List<MidiEvent> vibratoOrchestralStrings(Note note, int channel) throws InvalidMidiDataException{
        return null;
    }

    public List<MidiEvent> detacheOrchestralStrings(Note note, int channel) throws InvalidMidiDataException{
        return null;
    }

    public List<MidiEvent> sulPonticelloOrchestralStrings(Note note, int channel) throws InvalidMidiDataException{
        return null;
    }

    public List<MidiEvent> tremeloOrchestralStrings(Note note, int channel) throws InvalidMidiDataException{
        return null;
    }

    public List<MidiEvent> pizzicatoOrchestralStrings(Note note, int channel) throws InvalidMidiDataException{
        return null;
    }

    public List<MidiEvent> portatoOrchestralStrings(Note note, int channel) throws InvalidMidiDataException{
        return null;
    }

    public List<MidiEvent> createNote(int pitch, int position, int length, int channel) throws InvalidMidiDataException {
        List<MidiEvent> midiEvents = new ArrayList<>();
        MidiEvent eventOn = createNoteMidiEvent(ShortMessage.NOTE_ON, pitch, position, channel);
        midiEvents.add(eventOn);
        MidiEvent eventOff = createNoteMidiEvent(ShortMessage.NOTE_OFF, pitch, position + length, channel);
        midiEvents.add(eventOff);
        return midiEvents;
    }

    private MidiEvent createNoteMidiEvent(int cmd, int pitch, int position, int channel)
            throws InvalidMidiDataException {
        ShortMessage shortMessage = new ShortMessage();
        shortMessage.setMessage(cmd, channel, pitch, 80);
        return new MidiEvent(shortMessage, position);
    }

    protected List<MidiEvent> getMidiEvents(int program, int keyswitch, Note note, int channel) throws InvalidMidiDataException {
        List<MidiEvent> programEvents = createNote(program, note.getPosition(), note.getLength(), channel);
        List<MidiEvent> keyswitchEvents = createNote(keyswitch, note.getPosition(), note.getLength(), channel);
        programEvents.addAll(programEvents.size(), keyswitchEvents);
        return  programEvents;
    }

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
}
