package cp.out.orchestration.template;

import cp.model.note.Note;
import cp.out.print.note.Pitch;
import org.springframework.stereotype.Component;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import java.util.List;

@Component
public class StringOrchestralTemplate extends OrchestralTemplate{

    @Override
    public List<MidiEvent> legatoOrchestralStrings(Note note, int channel) throws InvalidMidiDataException {
        return getMidiEvents(Pitch.C0, Pitch.C1, note, channel);
    }

    @Override
    public List<MidiEvent> staccatoOrchestralStrings(Note note, int channel) throws InvalidMidiDataException {
        return getMidiEvents(39,27, note, channel);
    }

    @Override
    public List<MidiEvent> vibratoOrchestralStrings(Note note, int channel) throws InvalidMidiDataException{
        return getMidiEvents(39,27, note, channel);
    }

    @Override
    public List<MidiEvent> detacheOrchestralStrings(Note note, int channel) throws InvalidMidiDataException{
        return getMidiEvents(39,27, note, channel);
    }

    @Override
    public List<MidiEvent> sulPonticelloOrchestralStrings(Note note, int channel) throws InvalidMidiDataException{
        return getMidiEvents(39,27, note, channel);
    }

    @Override
    public List<MidiEvent> tremeloOrchestralStrings(Note note, int channel) throws InvalidMidiDataException{
        return getMidiEvents(39,27, note, channel);
    }

    @Override
    public List<MidiEvent> pizzicatoOrchestralStrings(Note note, int channel) throws InvalidMidiDataException{
        return getMidiEvents(39,27, note, channel);
    }

    @Override
    public List<MidiEvent> portatoOrchestralStrings(Note note, int channel) throws InvalidMidiDataException{
        return getMidiEvents(39,27, note, channel);
    }
}
