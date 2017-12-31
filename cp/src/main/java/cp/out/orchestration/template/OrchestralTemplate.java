package cp.out.orchestration.template;

import cp.midi.MidiEventGenerator;
import cp.model.note.Note;
import cp.out.instrument.Instrument;
import cp.out.orchestration.InstrumentName;
import cp.out.print.note.Pitch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.ShortMessage;
import java.util.ArrayList;
import java.util.List;

@Component(value = "orchestralTemplate")
public class OrchestralTemplate {

    @Autowired
    private MidiEventGenerator midiEventGenerator;

    //Orchestral strings
    public List<MidiEvent> legato(Note note, int channel, Instrument instrument) throws InvalidMidiDataException{
        if(instrument.getInstrumentName().equals(InstrumentName.BASS.getName())){
            return getMidiEvents(Pitch.ds5, 20, note, channel);
        }
        return getMidiEvents(Pitch.ds1, 20, note, channel);
    }

    public List<MidiEvent> staccato(Note note, int channel, int controllerValue, Instrument instrument) throws InvalidMidiDataException{
        if(instrument.getInstrumentName().equals(InstrumentName.BASS.getName())){
            return getMidiEvents(Pitch.cs5, controllerValue, note, channel);
        }
        return getMidiEvents(Pitch.cs1, controllerValue, note, channel);
    }

    public List<MidiEvent> marcato(Note note, int channel, int controllerValue, Instrument instrument) throws InvalidMidiDataException{
        if(instrument.getInstrumentName().equals(InstrumentName.BASS.getName())){
            return getMidiEvents(Pitch.gs5, controllerValue, note, channel);
        }
        return getMidiEvents(Pitch.gs1, controllerValue, note, channel);
    }

    public List<MidiEvent> vibrato(Note note, int channel, Instrument instrument) throws InvalidMidiDataException{
        if(instrument.getInstrumentName().equals(InstrumentName.BASS.getName())){
            return getMidiEvents(Pitch.c5, 20, note, channel);
        }
        return getMidiEvents(Pitch.c1, 20, note, channel);
    }

    public List<MidiEvent> senzaVibrato(Note note, int channel, Instrument instrument) throws InvalidMidiDataException{
        if(instrument.getInstrumentName().equals(InstrumentName.BASS.getName())){
            return getMidiEvents(Pitch.c5, 20, note, channel);
        }
        return getMidiEvents(Pitch.c1, 10, note, channel);
    }

    public List<MidiEvent> detache(Note note, int channel, int controllerValue, Instrument instrument) throws InvalidMidiDataException{
        if(instrument.getInstrumentName().equals(InstrumentName.BASS.getName())){
            return getMidiEvents(Pitch.d5, controllerValue, note, channel);
        }
        return getMidiEvents(Pitch.d1, controllerValue, note, channel);
    }

    public List<MidiEvent> sulPonticello(Note note, int channel, int controllerValue, Instrument instrument) throws InvalidMidiDataException{
        if(instrument.getInstrumentName().equals(InstrumentName.BASS.getName())){
            return getMidiEvents(Pitch.c5, controllerValue, note, channel);
        }
        return getMidiEvents(Pitch.c1, controllerValue, note, channel);
    }

    public List<MidiEvent> tremelo(Note note, int channel, int controllerValue, Instrument instrument) throws InvalidMidiDataException{
        if(instrument.getInstrumentName().equals(InstrumentName.BASS.getName())){
            return getMidiEvents(Pitch.fs5, controllerValue, note, channel);
        }
        return getMidiEvents(Pitch.fs1, controllerValue, note, channel);
    }

    public List<MidiEvent> pizzicato(Note note, int channel, Instrument instrument) throws InvalidMidiDataException{
        if(instrument.getInstrumentName().equals(InstrumentName.BASS.getName())){
            return getMidiEvents(Pitch.g5, 20, note, channel);
        }
        return getMidiEvents(Pitch.g1, 20, note, channel);
    }

    public List<MidiEvent> conSordino(Note note, int channel, int controllerValue, Instrument instrument) throws InvalidMidiDataException{
        if(instrument.getInstrumentName().equals(InstrumentName.BASS.getName())){
            return getMidiEvents(Pitch.c5, controllerValue, note, channel);
        }
        return getMidiEvents(Pitch.c1, controllerValue, note, channel);
    }

    public List<MidiEvent> colLegno(Note note, int channel, Instrument instrument) throws InvalidMidiDataException{
        if(instrument.getInstrumentName().equals(InstrumentName.BASS.getName())){
            return getMidiEvents(Pitch.a5, 20, note, channel);
        }
        return getMidiEvents(Pitch.a1, 10 , note, channel);
    }

    public List<MidiEvent> portamento(Note note, int channel, int controllerValue, Instrument instrument) throws InvalidMidiDataException{
        if(instrument.getInstrumentName().equals(InstrumentName.BASS.getName())){
            return getMidiEvents(Pitch.as5, controllerValue, note, channel);
        }
        return getMidiEvents(Pitch.as1, controllerValue , note, channel);
    }

    public List<MidiEvent> sforzando(Note note, int channel, int controllerValue, Instrument instrument) throws InvalidMidiDataException{
        if(instrument.getInstrumentName().equals(InstrumentName.BASS.getName())){
            return getMidiEvents(Pitch.f5, controllerValue, note, channel);
        }
        return getMidiEvents(Pitch.f1, controllerValue, note, channel);
    }

    public List<MidiEvent> fortePiano(Note note, int channel, int controllerValue, Instrument instrument) throws InvalidMidiDataException{
        if(instrument.getInstrumentName().equals(InstrumentName.BASS.getName())){
            return getMidiEvents(Pitch.e5, controllerValue, note, channel);
        }
        return getMidiEvents(Pitch.e1, controllerValue, note, channel);
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

    protected List<MidiEvent> getMidiEvents(int matrixKeySwitch, int controllerValue, Note note, int channel) throws InvalidMidiDataException {
        List<MidiEvent> matrixEvents = createKeySwitch(matrixKeySwitch, note.getBeforeMidiPosition(), note.getMidiControllerLength(), channel);
        MidiEvent controllerEvent = createModulationWheelControllerChangeMidiEvent(channel,controllerValue, note.getBeforeMidiPosition());
        matrixEvents.add(controllerEvent);
        return  matrixEvents;
    }

    protected List<MidiEvent> getMidiEvents(int matrixKeySwitch, int keySwitch, int controllerValue, Note note, int channel) throws InvalidMidiDataException {
        List<MidiEvent> matrixEvents = createKeySwitch(matrixKeySwitch, note.getBeforeMidiPosition(), note.getMidiControllerLength(), channel);
        List<MidiEvent> programEvents = createKeySwitch(keySwitch, note.getBeforeMidiPosition(), note.getMidiControllerLength(), channel);
        matrixEvents.addAll(programEvents);
        MidiEvent controllerEvent = createModulationWheelControllerChangeMidiEvent(channel,controllerValue, note.getBeforeMidiPosition());
        matrixEvents.add(controllerEvent);
        return  matrixEvents;
    }

    protected MidiEvent createModulationWheelControllerChangeMidiEvent(int channel, int value, int position)
            throws InvalidMidiDataException {
        ShortMessage change = new ShortMessage();
        change.setMessage(ShortMessage.CONTROL_CHANGE, channel, 1, value);
        return new MidiEvent(change, position);
    }

    protected List<MidiEvent> getProgramSwitches(int program, int controllerValue, Note note, int channel) throws InvalidMidiDataException {
        ArrayList<MidiEvent> midiEvents = new ArrayList<>();
        //not working with VEP!!!!
        MidiEvent programEvent = midiEventGenerator.createProgramChangeMidiEvent(channel, program, note.getBeforeMidiPosition());
        MidiEvent controllerChangeMidiEvent = midiEventGenerator.createControllerChangeMidiEvent(channel, 1, controllerValue, note.getBeforeMidiPosition());
        midiEvents.add(programEvent);
        midiEvents.add(controllerChangeMidiEvent);
        return  midiEvents;
    }

    protected MidiEvent createProgramChangeMidiEvent(int channel, int program, int position)
            throws InvalidMidiDataException {
        ShortMessage change = new ShortMessage();
        change.setMessage(ShortMessage.PROGRAM_CHANGE, channel, program, 0);
        return new MidiEvent(change, position);
    }
}
