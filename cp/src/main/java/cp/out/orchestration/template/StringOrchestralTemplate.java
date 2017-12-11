package cp.out.orchestration.template;

import cp.model.note.Note;
import cp.out.print.note.Pitch;
import org.springframework.stereotype.Component;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import java.util.List;

@Component( value = "stringOrchestralTemplate")
public class StringOrchestralTemplate extends OrchestralTemplate{

    @Override
    public List<MidiEvent> legato(Note note, int channel) throws InvalidMidiDataException {
        return getMidiEvents(Pitch.C0, Pitch.C1, note, channel);
    }

    @Override
    public List<MidiEvent> vibrato(Note note, int channel) throws InvalidMidiDataException{
        return staccato(note, channel);
    }

    @Override
    public List<MidiEvent> pizzicato(Note note, int channel) throws InvalidMidiDataException{
        return staccato(note, channel);
    }

    @Override
    public List<MidiEvent> portatoOrchestralStrings(Note note, int channel) throws InvalidMidiDataException{
        return staccato(note, channel);
    }


}
