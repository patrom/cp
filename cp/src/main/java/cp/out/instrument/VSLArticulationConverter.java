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
import java.util.List;

import static cp.out.instrument.InstrumentGroup.WOODWINDS;
import static java.util.Collections.emptyList;

/**
 * Created by prombouts on 9/12/2016.
 */
@Component(value = "vslMidiEventConverter")
public class VSLArticulationConverter extends MidiEventConverter{

    public static final int PONTICELLO_CC = 50;
    public static final int CON_SORDINO_CC = 100;
    @Autowired
    @Qualifier(value = "orchestralTemplate")
    private OrchestralTemplate orchestralTemplate;

    public List<MidiEvent> convertNote(int channel, Note note, Instrument instrument) throws InvalidMidiDataException {
        if (instrument.getInstrumentGroup() == InstrumentGroup.ORCHESTRAL_STRINGS){
            switch (note.getTechnical()) {
                case SENZA_VIBRATO:
                case ARCO:
                    if(note.getDynamic() != null){
                        switch (note.getDynamic()) {
                            case FP:
                                return orchestralTemplate.fortePiano(note, channel, 10);
                            case SFZ:
                                return orchestralTemplate.sforzando(note, channel,10);
                        }
                    }
                    if(note.getArticulation() != null){
                        switch (note.getArticulation()) {
                            case STACCATO:
                            case STACCATISSIMO:
                            case SPICCATO:
                                return orchestralTemplate.staccato(note, channel, 10);
                            case MARCATO:
                            case STRONG_ACCENT:
                                return orchestralTemplate.marcato(note, channel, 10);
                            case TENUTO:
                            case DETACHED_LEGATO:
                                return orchestralTemplate.detache(note, channel, 10);
                            case PORTAMENTO:
                                return orchestralTemplate.portamento(note, channel, 10);
                        }
                    }
                    return orchestralTemplate.senzaVibrato(note, channel);
                case VIBRATO:
                    if(note.getDynamic() != null){
                        switch (note.getDynamic()) {
                            case FP:
                                return orchestralTemplate.fortePiano(note, channel, 20);
                            case SFZ:
                                return orchestralTemplate.sforzando(note, channel,20);
                        }
                    }
                    if(note.getArticulation() != null){
                        switch (note.getArticulation()) {
                            case STACCATO:
                            case STACCATISSIMO:
                            case SPICCATO:
                                return orchestralTemplate.staccato(note, channel, 10);
                            case MARCATO:
                            case STRONG_ACCENT:
                                return orchestralTemplate.marcato(note, channel, 10);
                            case TENUTO:
                            case DETACHED_LEGATO:
                                return orchestralTemplate.detache(note, channel, 10);
                            case PORTAMENTO:
                                return orchestralTemplate.portamento(note, channel, 10);
                        }
                    }
                    return orchestralTemplate.vibrato(note, channel);
                case DETACHE_SHORT:
                    if(note.getDynamic() != null){
                        switch (note.getDynamic()) {
                            case FP:
                                return orchestralTemplate.fortePiano(note, channel, 10);
                            case SFZ:
                                return orchestralTemplate.sforzando(note, channel,10);
                        }
                    }
                    if(note.getArticulation() != null){
                        switch (note.getArticulation()) {
                            case STACCATO:
                            case STACCATISSIMO:
                            case SPICCATO:
                                return orchestralTemplate.staccato(note, channel, 10);
                            case MARCATO:
                            case STRONG_ACCENT:
                                return orchestralTemplate.marcato(note, channel, 10);
                            case TENUTO:
                            case DETACHED_LEGATO:
                                return orchestralTemplate.detache(note, channel, 10);
                            case PORTAMENTO:
                                return orchestralTemplate.portamento(note, channel, 10);
                        }
                    }
                    return orchestralTemplate.detache(note, channel, 10);
                case LEGATO:
                    if(note.getDynamic() != null){
                        switch (note.getDynamic()) {
                            case FP:
                                return orchestralTemplate.fortePiano(note, channel, 10);
                            case SFZ:
                                return orchestralTemplate.sforzando(note, channel,10);
                        }
                    }
                    if(note.getArticulation() != null){
                        switch (note.getArticulation()) {
                            case STACCATO:
                            case STACCATISSIMO:
                            case SPICCATO:
                                return orchestralTemplate.staccato(note, channel, 10);
                            case MARCATO:
                            case STRONG_ACCENT:
                                return orchestralTemplate.marcato(note, channel, 10);
                            case TENUTO:
                            case DETACHED_LEGATO:
                                return orchestralTemplate.detache(note, channel, 10);
                            case PORTAMENTO:
                                return orchestralTemplate.portamento(note, channel, 10);
                        }
                    }
                    return orchestralTemplate.legato(note, channel);
                case TREMELO:
                    if(note.getDynamic() != null){
                        switch (note.getDynamic()) {
                            case FP:
                                return orchestralTemplate.fortePiano(note, channel, 10);
                            case SFZ:
                                return orchestralTemplate.sforzando(note, channel,10);
                        }
                    }
                    if(note.getArticulation() != null){
                        switch (note.getArticulation()) {
                            case STACCATO:
                            case STACCATISSIMO:
                            case SPICCATO:
                                return orchestralTemplate.staccato(note, channel, 10);
                            case MARCATO:
                            case STRONG_ACCENT:
                                return orchestralTemplate.marcato(note, channel, 10);
                            case TENUTO:
                            case DETACHED_LEGATO:
                                return orchestralTemplate.detache(note, channel, 10);
                            case PORTAMENTO:
                                return orchestralTemplate.portamento(note, channel, 10);
                        }
                    }
                    return orchestralTemplate.tremelo(note, channel, 10);
                case PIZZ:
                    if(note.getDynamic() != null){
                        switch (note.getDynamic()) {
                            case FP:
                                return orchestralTemplate.fortePiano(note, channel, 10);
                            case SFZ:
                                return orchestralTemplate.sforzando(note, channel,10);
                        }
                    }
                    if(note.getArticulation() != null){
                        switch (note.getArticulation()) {
                            case STACCATO:
                            case STACCATISSIMO:
                            case SPICCATO:
                                return orchestralTemplate.staccato(note, channel, 10);
                            case MARCATO:
                            case STRONG_ACCENT:
                                return orchestralTemplate.marcato(note, channel, 10);
                            case TENUTO:
                            case DETACHED_LEGATO:
                                return orchestralTemplate.detache(note, channel, 10);
                            case PORTAMENTO:
                                return orchestralTemplate.portamento(note, channel, 10);
                        }
                    }
                    return orchestralTemplate.pizzicato(note, channel);
                case CON_SORDINO:
                    if(note.getDynamic() != null){
                        switch (note.getDynamic()) {
                            case FP:
                                return orchestralTemplate.fortePiano(note, channel, CON_SORDINO_CC);
                            case SFZ:
                                return orchestralTemplate.sforzando(note, channel,CON_SORDINO_CC);
                        }
                    }
                    if(note.getArticulation() != null){
                        switch (note.getArticulation()) {
                            case STACCATO:
                            case STACCATISSIMO:
                            case SPICCATO:
                                return orchestralTemplate.staccato(note, channel, CON_SORDINO_CC);
                            case MARCATO:
                            case STRONG_ACCENT:
                                return orchestralTemplate.marcato(note, channel, CON_SORDINO_CC);
                            case TENUTO:
                            case DETACHED_LEGATO:
                                return orchestralTemplate.detache(note, channel, CON_SORDINO_CC);
                            case PORTAMENTO:
                                return orchestralTemplate.portamento(note, channel, CON_SORDINO_CC);
                        }
                    }
                    return orchestralTemplate.conSordino(note, channel, CON_SORDINO_CC);
//                case "arco":
//                case "col legno":
                case SUL_PONTICELLO:
                    if(note.getDynamic() != null){
                        switch (note.getDynamic()) {
                            case FP:
                                return orchestralTemplate.fortePiano(note, channel, PONTICELLO_CC);
                            case SFZ:
                                return orchestralTemplate.sforzando(note, channel, PONTICELLO_CC);
                        }
                    }
                    if(note.getArticulation() != null){
                        switch (note.getArticulation()) {
                            case STACCATO:
                            case STACCATISSIMO:
                            case SPICCATO:
                                return orchestralTemplate.staccato(note, channel, PONTICELLO_CC);
                            case MARCATO:
                            case STRONG_ACCENT:
                                return orchestralTemplate.marcato(note, channel, PONTICELLO_CC);
                            case TENUTO:
                            case DETACHED_LEGATO:
                                return orchestralTemplate.detache(note, channel, PONTICELLO_CC);
                            case PORTAMENTO:
                                return orchestralTemplate.portamento(note, channel, PONTICELLO_CC);
                        }
                    }
                    return orchestralTemplate.sulPonticello(note, channel, PONTICELLO_CC);
//                case "progressive vibrato":
//                case "sus":
//                    return createMidiEvents(channel, note, 0, 5);

//                case "sul tasto":
                case COL_LEGNO:
                    return orchestralTemplate.colLegno(note, channel);
                case STACCATO:
                    if(note.getDynamic() != null){
                        switch (note.getDynamic()) {
                            case FP:
                                return orchestralTemplate.fortePiano(note, channel, 10);
                            case SFZ:
                                return orchestralTemplate.sforzando(note, channel,10);
                        }
                    }
                    if(note.getArticulation() != null){
                        switch (note.getArticulation()) {
                            case STACCATO:
                            case STACCATISSIMO:
                            case SPICCATO:
                                return orchestralTemplate.staccato(note, channel, 10);
                            case MARCATO:
                            case STRONG_ACCENT:
                                return orchestralTemplate.marcato(note, channel, 10);
                            case TENUTO:
                            case DETACHED_LEGATO:
                                return orchestralTemplate.detache(note, channel, 10);
                            case PORTAMENTO:
                                return orchestralTemplate.portamento(note, channel, 10);
                        }
                    }
                    return orchestralTemplate.staccato(note, channel ,10);
                case PORTATO:
                    if(note.getDynamic() != null){
                        switch (note.getDynamic()) {
                            case FP:
                                return orchestralTemplate.fortePiano(note, channel, 10);
                            case SFZ:
                                return orchestralTemplate.sforzando(note, channel,10);
                        }
                    }
                    if(note.getArticulation() != null){
                        switch (note.getArticulation()) {
                            case STACCATO:
                            case STACCATISSIMO:
                            case SPICCATO:
                                return orchestralTemplate.staccato(note, channel, 10);
                            case MARCATO:
                            case STRONG_ACCENT:
                                return orchestralTemplate.marcato(note, channel, 10);
                            case TENUTO:
                            case DETACHED_LEGATO:
                                return orchestralTemplate.detache(note, channel, 10);
                            case PORTAMENTO:
                                return orchestralTemplate.portamento(note, channel, 10);
                        }
                    }
                    return orchestralTemplate.detache(note, channel, 10);
            }
        }
        return emptyList();
    }


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
                            return orchestralTemplate.staccato(note, channel,10);
//                    case MARCATO:
//                    case STRONG_ACCENT:
//                        note.setDynamicLevel(Dynamic.PP.getLevel());//reduction level
//                        return createMidiEvents(channel, note, 1, 65);
                    }
                    if (note.getTechnical() == Technical.SUL_PONTICELLO){
                        switch (note.getArticulation()) {
                            case STACCATO:
                            case STACCATISSIMO:
//                    case MARCATO:
//                    case STRONG_ACCENT:
//                        note.setDynamicLevel(Dynamic.PP.getLevel());//reduction level
//                        return createMidiEvents(channel, note, 1, 65);
                        }
                    }
                    break;
            }
        }
        return emptyList();
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
//                    return orchestralTemplate.fortePiano(note, channel);
            }
        }
        return emptyList();
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
                case SENZA_VIBRATO:
                    return orchestralTemplate.senzaVibrato(note, channel);
                case VIBRATO:
                    return orchestralTemplate.vibrato(note, channel);
//                case "molto vibrato":
                case DETACHE_SHORT:
                    return orchestralTemplate.detache(note, channel, 10);
                case PIZZ:
                    return orchestralTemplate.pizzicato(note, channel);
//                case "con sordino":
//                case "arco":
//                case "col legno":
                case SUL_PONTICELLO:
                    return orchestralTemplate.sulPonticello(note, channel, PONTICELLO_CC);
//                case "progressive vibrato":
//                case "sus":
//                    return createMidiEvents(channel, note, 0, 5);
                case TREMELO:
                    return orchestralTemplate.tremelo(note, channel, 10);
//                case "sul tasto":
//                case "con sordino":
//                case "arco":
//                case "col legno":
//                case "slap"://flute/saxophones
                case STACCATO:
                    return orchestralTemplate.staccato(note, channel);
//                case "staccatissimo":
//                    return createMidiEvents(channel, note, 0, 5);
                case LEGATO:
                    return orchestralTemplate.legato(note, channel);
                case PORTATO:
                    return orchestralTemplate.portatoOrchestralStrings(note, channel);
            }
        }
        return emptyList();
    }

}
