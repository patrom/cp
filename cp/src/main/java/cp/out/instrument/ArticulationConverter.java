package cp.out.instrument;

import cp.model.note.Note;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import java.util.List;

/**
 * Created by prombouts on 9/12/2016.
 */
@FunctionalInterface
public interface ArticulationConverter {

    List<MidiEvent> convertArticulation(Instrument instrument, int channel, Note note) throws InvalidMidiDataException;
}
