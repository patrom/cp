package cp.out.orchestration.template;

import cp.model.note.Note;
import cp.out.print.note.Pitch;
import org.springframework.stereotype.Component;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import java.util.List;

@Component (value = "stringQuartetTemplate")
public class StringQuartetTemplate extends OrchestralTemplate {

    @Override
    public List<MidiEvent> legatoSoloStrings(Note note, int channel) throws InvalidMidiDataException {
        return getControllers(Pitch.D0, 50, note, channel);
    }

    @Override
    public List<MidiEvent> staccatoSoloStrings(Note note, int channel) throws InvalidMidiDataException{
        return getControllers(Pitch.D0, 10, note, channel);
    }

    @Override
    public List<MidiEvent> vibratoSoloStrings(Note note, int channel) throws InvalidMidiDataException{
        return getControllers(Pitch.C0, 10, note, channel);
    }

    @Override
    public List<MidiEvent> detacheSoloStrings(Note note, int channel) throws InvalidMidiDataException{
        return getControllers(Pitch.D0, 30, note, channel);
    }

    @Override
    public List<MidiEvent> sulPonticelloSoloStrings(Note note, int channel) throws InvalidMidiDataException{
        return getControllers(Pitch.D0, 50, note, channel);
    }

    @Override
    public List<MidiEvent> tremeloSoloStrings(Note note, int channel) throws InvalidMidiDataException{
        return getControllers(Pitch.C0, 50, note, channel);
    }

    @Override
    public List<MidiEvent> pizzicatoSoloStrings(Note note, int channel) throws InvalidMidiDataException{
        return getControllers(Pitch.C0, 70, note, channel);
    }

    @Override
    public List<MidiEvent> portatoSoloStrings(Note note, int channel) throws InvalidMidiDataException{
        return getControllers(Pitch.D0, 90, note, channel);
    }

    @Override
    public List<MidiEvent> fortePianoSoloStrings(Note note, int channel) throws InvalidMidiDataException{
        return getControllers(Pitch.C0, 30, note, channel);
    }

    @Override
    public List<MidiEvent> sforzandoSoloStrings(Note note, int channel) throws InvalidMidiDataException{
        return getControllers(Pitch.C0, Pitch.C1, note, channel);
    }
}
