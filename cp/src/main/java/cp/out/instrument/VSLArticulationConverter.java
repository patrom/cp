package cp.out.instrument;

import cp.midi.MidiEventConverter;
import cp.model.note.Note;
import org.springframework.stereotype.Component;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import java.util.Collections;
import java.util.List;

/**
 * Created by prombouts on 9/12/2016.
 */
@Component(value = "vslMidiEventConverter")
public class VSLArticulationConverter extends MidiEventConverter{

    @Override
    public List<MidiEvent> convertArticulation(int channel, Note note, Instrument instrument) throws InvalidMidiDataException {
        if (instrument.getInstrumentGroup() == InstrumentGroup.WOODWINDS){
            if (note.getArticulation() != null) {
                switch (note.getArticulation()) {
                    case STACCATO:
                        return createMidiEvents(channel, note, 0, 5);
                    case STACCATISSIMO:
                        return createMidiEvents(channel, note, 0, 5);
                }
            }
        }
        return Collections.emptyList();
    }

    @Override
    public List<MidiEvent> convertDynamic(int channel, Note note, Instrument instrument) throws InvalidMidiDataException {
        if (instrument.getInstrumentGroup() == InstrumentGroup.WOODWINDS
                ){
            switch (note.getDynamic()) {
                case FP:
                    return createMidiEvents(channel, note, 0, 110);
                case SFZ:
                    return createMidiEvents(channel, note, 0, 120);

            }
        }
        return Collections.emptyList();
    }

    @Override
    public List<MidiEvent> convertTechnical(int channel, Note note, Instrument instrument) throws InvalidMidiDataException {
        if (instrument.getInstrumentGroup() == InstrumentGroup.WOODWINDS){
            switch (note.getTechnical()) {
                case VIBRATO:
                    return createMidiEvents(channel, note, 0, 35);
//                case "molto vibrato":
                case SENZA_VIBRATO:
                    return createMidiEvents(channel, note, 0, 25);
//                case "progressive vibrato":
//                case "sus":
//                    return createMidiEvents(channel, note, 0, 5);
//                case "tremelo":
//                case "sul tasto":
//                case "pizz":
//                case "con sordino":
//                case "arco":
//                case "col legno":
//                case "sul ponticello":
//                case "short"://detache short
//                case "slap"://flute/saxophones
                case STACCATO:
                    return createMidiEvents(channel, note, 0, 5);
//                case "staccatissimo":
//                    return createMidiEvents(channel, note, 0, 5);
                case LEGATO:
                    return createMidiEvents(channel, note, 0, 65);
                case FLUTTER_TONGUE:
                    return createMidiEvents(channel, note, 1, 25);
                case PORTATO:
                    return createMidiEvents(channel, note, 0, 15);
            }
        }
        return Collections.emptyList();
    }

}
