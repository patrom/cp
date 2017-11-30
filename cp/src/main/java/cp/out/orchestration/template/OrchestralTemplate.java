package cp.out.orchestration.template;

import cp.model.note.Note;
import org.springframework.stereotype.Component;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.ShortMessage;
import java.util.ArrayList;
import java.util.List;

@Component(value = "orchestralTemplate")
public class OrchestralTemplate {

    //Orchestral strings
    public List<MidiEvent> legatoOrchestralStrings(Note note, int channel) throws InvalidMidiDataException{
        return null;
    }

    public List<MidiEvent> staccatoOrchestralStrings(Note note, int channel) throws InvalidMidiDataException{
        return null;
    }

    public List<MidiEvent> staccatoSulPonticelloOrchestralStrings(Note note, int channel) throws InvalidMidiDataException{
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

    public List<MidiEvent> sforzandoOrchestralStrings(Note note, int channel) throws InvalidMidiDataException{
        return null;
    }

   //Solo Strings
    public List<MidiEvent> legatoSoloStrings(Note note, int channel) throws InvalidMidiDataException{
        return null;
    }

    public List<MidiEvent> staccatoSoloStrings(Note note, int channel) throws InvalidMidiDataException{
        return null;
    }

    public List<MidiEvent> vibratoSoloStrings(Note note, int channel) throws InvalidMidiDataException{
        return null;
    }

    public List<MidiEvent> detacheSoloStrings(Note note, int channel) throws InvalidMidiDataException{
        return null;
    }

    public List<MidiEvent> sulPonticelloSoloStrings(Note note, int channel) throws InvalidMidiDataException{
        return null;
    }

    public List<MidiEvent> tremeloSoloStrings(Note note, int channel) throws InvalidMidiDataException{
        return null;
    }

    public List<MidiEvent> pizzicatoSoloStrings(Note note, int channel) throws InvalidMidiDataException{
        return null;
    }

    public List<MidiEvent> portatoSoloStrings(Note note, int channel) throws InvalidMidiDataException{
        return null;
    }

    public List<MidiEvent> fortePianoSoloStrings(Note note, int channel) throws InvalidMidiDataException{
        return null;
    }

    public List<MidiEvent> sforzandoSoloStrings(Note note, int channel) throws InvalidMidiDataException{
        return null;
    }


    public List<MidiEvent> createKeySwitch(int pitch, int position, int length, int channel) throws InvalidMidiDataException {
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

//    protected List<MidiEvent> getKeyswitches(int program, int keyswitch, Note note, int channel) throws InvalidMidiDataException {
//        List<MidiEvent> programEvents = createKeySwitch(program, note.getBeforeMidiPosition(), note.getMidiLength(), channel);
//        List<MidiEvent> keyswitchEvents = createKeySwitch(keyswitch, note.getBeforeMidiPosition(), note.getMidiLength(), channel);
//        programEvents.addAll(programEvents.size(), keyswitchEvents);
//        return  programEvents;
//    }

    protected List<MidiEvent> getMidiEvents(int program, int controllerValue, Note note, int channel) throws InvalidMidiDataException {
        List<MidiEvent> programEvents = createKeySwitch(program, note.getBeforeMidiPosition(), note.getMidiControllerLength(), channel);
        MidiEvent controllerEvent = createControllerChangeMidiEvent(channel,controllerValue, note.getBeforeMidiPosition());
        programEvents.add(controllerEvent);
        return  programEvents;
    }

//    protected List<MidiEvent> createMidiEvents(int channel, Note note, int programChange, int controllerValue) throws InvalidMidiDataException {
//        List<MidiEvent> midiEvents = new ArrayList<>();
//        MidiEvent programEvent = createProgramChangeMidiEvent(channel,programChange, note.getBeforeMidiPosition());
//        midiEvents.add(programEvent);
//        MidiEvent controllerEvent = createModulationWheelControllerChangeMidiEvent(channel,controllerValue, note.getBeforeMidiPosition());
//        midiEvents.add(controllerEvent);
//        return midiEvents;
//    }

    protected MidiEvent createControllerChangeMidiEvent(int channel, int value, int position)
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
