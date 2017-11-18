package cp.out.instrument;

import cp.midi.MidiEventConverter;
import cp.model.note.Dynamic;
import cp.model.note.Note;
import cp.out.orchestration.template.OrchestralTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import java.util.Collections;
import java.util.List;

import static cp.out.instrument.InstrumentGroup.WOODWINDS;

/**
 * Created by prombouts on 9/12/2016.
 */
@Component(value = "vslMidiEventConverter")
public class VSLArticulationConverter extends MidiEventConverter{

    @Autowired
    @Qualifier(value = "stringQuartetTemplate")
    private OrchestralTemplate orchestralTemplate;

    @Override
    public List<MidiEvent> convertArticulation(int channel, Note note, Instrument instrument) throws InvalidMidiDataException {
        if(note.getArticulation() != null){
            switch (instrument.getInstrumentGroup()){
                case WOODWINDS:
                case BRASS:
                    switch (note.getArticulation()) {
                        case STACCATO:
                        case STACCATISSIMO:
                            return createMidiEvents(channel, note, 0, 5);
                    }
                    break;
                case STRINGS:
                    switch (note.getArticulation()) {
                        case STACCATO:
                        case STACCATISSIMO:
                            return orchestralTemplate.staccatoSoloStrings(note, channel);
                        case MARCATO:
                        case STRONG_ACCENT:
                            note.setDynamicLevel(Dynamic.PP.getLevel());//reduction level
                            return createMidiEvents(channel, note, 1, 65);
                    }
                    break;
                case ORCHESTRAL_STRINGS:
                    switch (note.getArticulation()) {
                        case STACCATO:
                        case STACCATISSIMO:
                            return orchestralTemplate.staccatoOrchestralStrings(note, channel);
//                    case MARCATO:
//                    case STRONG_ACCENT:
//                        note.setDynamicLevel(Dynamic.PP.getLevel());//reduction level
//                        return createMidiEvents(channel, note, 1, 65);
                    }
                    if (note.getTechnical() == Technical.SUL_PONTICELLO){
                        switch (note.getArticulation()) {
                            case STACCATO:
                            case STACCATISSIMO:
                                return orchestralTemplate.staccatoSulPonticelloOrchestralStrings(note, channel);
//                    case MARCATO:
//                    case STRONG_ACCENT:
//                        note.setDynamicLevel(Dynamic.PP.getLevel());//reduction level
//                        return createMidiEvents(channel, note, 1, 65);
                        }
                    }
                    break;
            }
        }
        return Collections.emptyList();
    }

    @Override
    public List<MidiEvent> convertDynamic(int channel, Note note, Instrument instrument) throws InvalidMidiDataException {
        if (instrument.getInstrumentGroup() == WOODWINDS
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
                    return orchestralTemplate.fortePianoSoloStrings(note, channel);
                case SFZ:
                    return orchestralTemplate.sforzandoSoloStrings(note, channel);
            }
        }
        if (instrument.getInstrumentGroup() == InstrumentGroup.ORCHESTRAL_STRINGS){
            switch (note.getDynamic()) {
                case FP:
                    return orchestralTemplate.sforzandoOrchestralStrings(note, channel);
            }
        }
        return Collections.emptyList();
    }

    @Override
    public List<MidiEvent> convertTechnical(int channel, Note note, Instrument instrument) throws InvalidMidiDataException {
        if (instrument.getInstrumentGroup() == WOODWINDS
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
                    return orchestralTemplate.vibratoSoloStrings(note, channel);
//                case "molto vibrato":
                case DETACHE_SHORT:
                    return orchestralTemplate.detacheSoloStrings(note, channel);
                case PIZZ:
                    return orchestralTemplate.pizzicatoSoloStrings(note, channel);
//                case "con sordino":
//                case "arco":
//                case "col legno":
                case SUL_PONTICELLO:
                    return createMidiEvents(channel, note, 3, 5);
//                case "progressive vibrato":
//                case "sus":
//                    return createMidiEvents(channel, note, 0, 5);
                case TREMELO:
                    return orchestralTemplate.tremeloSoloStrings(note, channel);
//                case "sul tasto":
//                case "con sordino":
//                case "arco":
//                case "col legno":
//                case "slap"://flute/saxophones
                case STACCATO:
                    return orchestralTemplate.staccatoSoloStrings(note, channel);
//                case "staccatissimo":
//                    return createMidiEvents(channel, note, 0, 5);
                case LEGATO:
                    return orchestralTemplate.legatoSoloStrings(note, channel);
                case PORTATO:
                    return orchestralTemplate.portatoSoloStrings(note, channel);
            }
        }
        if (instrument.getInstrumentGroup() == InstrumentGroup.ORCHESTRAL_STRINGS){
            switch (note.getTechnical()) {
                case VIBRATO:
                    return orchestralTemplate.vibratoOrchestralStrings(note, channel);
//                case "molto vibrato":
                case DETACHE_SHORT:
                    return orchestralTemplate.detacheOrchestralStrings(note, channel);
                case PIZZ:
                    return orchestralTemplate.pizzicatoOrchestralStrings(note, channel);
//                case "con sordino":
//                case "arco":
//                case "col legno":
                case SUL_PONTICELLO:
                    return orchestralTemplate.sulPonticelloOrchestralStrings(note, channel);
//                case "progressive vibrato":
//                case "sus":
//                    return createMidiEvents(channel, note, 0, 5);
                case TREMELO:
                    return orchestralTemplate.tremeloOrchestralStrings(note, channel);
//                case "sul tasto":
//                case "con sordino":
//                case "arco":
//                case "col legno":
//                case "slap"://flute/saxophones
                case STACCATO:
                    return orchestralTemplate.staccatoOrchestralStrings(note, channel);
//                case "staccatissimo":
//                    return createMidiEvents(channel, note, 0, 5);
                case LEGATO:
                    return orchestralTemplate.legatoOrchestralStrings(note, channel);
                case PORTATO:
                    return orchestralTemplate.portatoOrchestralStrings(note, channel);
            }
        }
        return Collections.emptyList();
    }

}
