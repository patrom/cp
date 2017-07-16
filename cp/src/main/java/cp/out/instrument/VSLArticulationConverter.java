package cp.out.instrument;

import cp.midi.MidiEventConverter;
import cp.model.note.Dynamic;
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
        if(note.getArticulation() != null){
            if (instrument.getInstrumentGroup() == InstrumentGroup.WOODWINDS
                    || instrument.getInstrumentGroup() == InstrumentGroup.BRASS){
                switch (note.getArticulation()) {
                    case STACCATO:
                        return createMidiEvents(channel, note, 0, 5);
                    case STACCATISSIMO:
                        return createMidiEvents(channel, note, 0, 5);
                }
            }
            if (instrument.getInstrumentGroup() == InstrumentGroup.STRINGS){
                switch (note.getArticulation()) {
                    case STACCATO:
                        if (note.getTechnical() == Technical.SUL_PONTICELLO){
                            return createMidiEvents(channel, note, 3, 20);
                        }
                        return createMidiEvents(channel, note, 2, 20);
                    case STACCATISSIMO:
                        if (note.getTechnical() == Technical.SUL_PONTICELLO){
                            return createMidiEvents(channel, note, 3, 20);
                        }
                        return createMidiEvents(channel, note, 2, 20);
                    case MARCATO:
                    case STRONG_ACCENT:
                        note.setDynamicLevel(Dynamic.PP.getLevel());//reduction level
                        return createMidiEvents(channel, note, 1, 65);
                }
            }
            if (instrument.getInstrumentGroup() == InstrumentGroup.ORCHESTRAL_STRINGS){
                switch (note.getArticulation()) {
                    case STACCATO:
                    case STACCATISSIMO:
                        if (note.getTechnical() == Technical.SUL_PONTICELLO){
                            return createMidiEvents(channel, note, 2, 20);
                        }
                        return createMidiEvents(channel, note, 1, 10);
//                    case MARCATO:
//                    case STRONG_ACCENT:
//                        note.setDynamicLevel(Dynamic.PP.getLevel());//reduction level
//                        return createMidiEvents(channel, note, 1, 65);
                }
            }
        }
        return Collections.emptyList();
    }

    @Override
    public List<MidiEvent> convertDynamic(int channel, Note note, Instrument instrument) throws InvalidMidiDataException {
        if (instrument.getInstrumentGroup() == InstrumentGroup.WOODWINDS
                ||instrument.getInstrumentGroup() == InstrumentGroup.BRASS){
            switch (note.getDynamic()) {
                case FP:
                    return createMidiEvents(channel, note, 0, 110);
                case SFZ:
                    return createMidiEvents(channel, note, 0, 120);

            }
        }
        if (instrument.getInstrumentGroup() == InstrumentGroup.STRINGS){
            switch (note.getDynamic()) {
                case FP:
                    return createMidiEvents(channel, note, 0, 25);
                case SFZ:
                    return createMidiEvents(channel, note, 2, 85);
            }
        }
        if (instrument.getInstrumentGroup() == InstrumentGroup.ORCHESTRAL_STRINGS){
            switch (note.getDynamic()) {
                case FP:
                    return createMidiEvents(channel, note, 0, 25);
            }
        }
        return Collections.emptyList();
    }

    @Override
    public List<MidiEvent> convertTechnical(int channel, Note note, Instrument instrument) throws InvalidMidiDataException {
        if (instrument.getInstrumentGroup() == InstrumentGroup.WOODWINDS
                || instrument.getInstrumentGroup() == InstrumentGroup.BRASS){
            switch (note.getTechnical()) {
                case VIBRATO:
                    return createMidiEvents(channel, note, 0, 35);
//                case "molto vibrato":
                case SENZA_VIBRATO:
                    return createMidiEvents(channel, note, 0, 25);
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
        if (instrument.getInstrumentGroup() == InstrumentGroup.STRINGS){
            switch (note.getTechnical()) {
                case VIBRATO:
                    return createMidiEvents(channel, note, 2, 5);
//                case "molto vibrato":
                case DETACHE_SHORT:
                    return createMidiEvents(channel, note, 2, 35);
                case PIZZ:
                    return createMidiEvents(channel, note, 0, 65);
//                case "con sordino":
//                case "arco":
//                case "col legno":
                case SUL_PONTICELLO:
                    return createMidiEvents(channel, note, 3, 5);
//                case "progressive vibrato":
//                case "sus":
//                    return createMidiEvents(channel, note, 0, 5);
                case TREMELO:
                    return createMidiEvents(channel, note, 2, 115);
//                case "sul tasto":
//                case "con sordino":
//                case "arco":
//                case "col legno":
//                case "slap"://flute/saxophones
                case STACCATO:
                    return createMidiEvents(channel, note, 2, 5);
//                case "staccatissimo":
//                    return createMidiEvents(channel, note, 0, 5);
                case LEGATO:
                    return createMidiEvents(channel, note, 2, 50);
                case PORTATO:
                    return createMidiEvents(channel, note, 2, 35);
            }
        }
        if (instrument.getInstrumentGroup() == InstrumentGroup.ORCHESTRAL_STRINGS){
            switch (note.getTechnical()) {
                case VIBRATO:
                    return createMidiEvents(channel, note, 0, 5);
//                case "molto vibrato":
                case DETACHE_SHORT:
                    return createMidiEvents(channel, note, 1, 35);
                case PIZZ:
                    return createMidiEvents(channel, note, 0, 65);
//                case "con sordino":
//                case "arco":
//                case "col legno":
                case SUL_PONTICELLO:
                    return createMidiEvents(channel, note, 3, 5);
//                case "progressive vibrato":
//                case "sus":
//                    return createMidiEvents(channel, note, 0, 5);
                case TREMELO:
                    return createMidiEvents(channel, note, 0, 50);
//                case "sul tasto":
//                case "con sordino":
//                case "arco":
//                case "col legno":
//                case "slap"://flute/saxophones
                case STACCATO:
                    return createMidiEvents(channel, note, 1, 5);
//                case "staccatissimo":
//                    return createMidiEvents(channel, note, 0, 5);
                case LEGATO:
                    return createMidiEvents(channel, note, 1, 50);
                case PORTATO:
                    return createMidiEvents(channel, note, 1, 90);
            }
        }
        return Collections.emptyList();
    }

}
