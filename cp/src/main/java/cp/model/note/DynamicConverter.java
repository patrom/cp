package cp.model.note;

import cp.out.instrument.Instrument;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import java.util.List;

/**
 * Created by prombouts on 1/04/2017.
 */
@FunctionalInterface
public interface DynamicConverter {

    List<MidiEvent> convertDynamic(Instrument instrument, int channel, Note note) throws InvalidMidiDataException;
}
