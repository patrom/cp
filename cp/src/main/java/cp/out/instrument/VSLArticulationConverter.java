package cp.out.instrument;

import cp.midi.MidiEventConverter;
import cp.model.note.Note;
import cp.out.orchestration.template.OrchestralTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import java.util.List;

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
                                return orchestralTemplate.fortePiano(note, channel, 10, instrument );
                            case SFZ:
                                return orchestralTemplate.sforzando(note, channel,10, instrument );
                        }
                    }
                    if(note.getArticulation() != null){
                        switch (note.getArticulation()) {
                            case STACCATO:
                            case STACCATISSIMO:
                            case SPICCATO:
                                return orchestralTemplate.staccato(note, channel, 10, instrument);
                            case MARCATO:
                            case STRONG_ACCENT:
                                return orchestralTemplate.marcato(note, channel, 10, instrument);
                            case TENUTO:
                            case DETACHED_LEGATO:
                                return orchestralTemplate.detache(note, channel, 10, instrument);
                            case PORTAMENTO:
                                return orchestralTemplate.portamento(note, channel, 10, instrument);
                        }
                    }
                    return orchestralTemplate.senzaVibrato(note, channel, instrument);
                case VIBRATO:
                    if(note.getDynamic() != null){
                        switch (note.getDynamic()) {
                            case FP:
                                return orchestralTemplate.fortePiano(note, channel, 20, instrument);
                            case SFZ:
                                return orchestralTemplate.sforzando(note, channel,20, instrument);
                        }
                    }
                    if(note.getArticulation() != null){
                        switch (note.getArticulation()) {
                            case STACCATO:
                            case STACCATISSIMO:
                            case SPICCATO:
                                return orchestralTemplate.staccato(note, channel, 10, instrument);
                            case MARCATO:
                            case STRONG_ACCENT:
                                return orchestralTemplate.marcato(note, channel, 10, instrument);
                            case TENUTO:
                            case DETACHED_LEGATO:
                                return orchestralTemplate.detache(note, channel, 10, instrument);
                            case PORTAMENTO:
                                return orchestralTemplate.portamento(note, channel, 10, instrument);
                        }
                    }
                    return orchestralTemplate.vibrato(note, channel, instrument);
                case DETACHE_SHORT:
                    if(note.getDynamic() != null){
                        switch (note.getDynamic()) {
                            case FP:
                                return orchestralTemplate.fortePiano(note, channel, 10, instrument);
                            case SFZ:
                                return orchestralTemplate.sforzando(note, channel,10, instrument);
                        }
                    }
                    if(note.getArticulation() != null){
                        switch (note.getArticulation()) {
                            case STACCATO:
                            case STACCATISSIMO:
                            case SPICCATO:
                                return orchestralTemplate.staccato(note, channel, 10, instrument);
                            case MARCATO:
                            case STRONG_ACCENT:
                                return orchestralTemplate.marcato(note, channel, 10, instrument);
                            case TENUTO:
                            case DETACHED_LEGATO:
                                return orchestralTemplate.detache(note, channel, 10, instrument);
                            case PORTAMENTO:
                                return orchestralTemplate.portamento(note, channel, 10, instrument);
                        }
                    }
                    return orchestralTemplate.detache(note, channel, 10, instrument);
                case LEGATO:
                    if(note.getDynamic() != null){
                        switch (note.getDynamic()) {
                            case FP:
                                return orchestralTemplate.fortePiano(note, channel, 10, instrument);
                            case SFZ:
                                return orchestralTemplate.sforzando(note, channel,10, instrument);
                        }
                    }
                    if(note.getArticulation() != null){
                        switch (note.getArticulation()) {
                            case STACCATO:
                            case STACCATISSIMO:
                            case SPICCATO:
                                return orchestralTemplate.staccato(note, channel, 10, instrument);
                            case MARCATO:
                            case STRONG_ACCENT:
                                return orchestralTemplate.marcato(note, channel, 10, instrument);
                            case TENUTO:
                            case DETACHED_LEGATO:
                                return orchestralTemplate.detache(note, channel, 10, instrument);
                            case PORTAMENTO:
                                return orchestralTemplate.portamento(note, channel, 10, instrument);
                        }
                    }
                    return orchestralTemplate.legato(note, channel, instrument);
                case TREMELO:
                    if(note.getDynamic() != null){
                        switch (note.getDynamic()) {
                            case FP:
                                return orchestralTemplate.fortePiano(note, channel, 10, instrument);
                            case SFZ:
                                return orchestralTemplate.sforzando(note, channel,10, instrument);
                        }
                    }
                    if(note.getArticulation() != null){
                        switch (note.getArticulation()) {
                            case STACCATO:
                            case STACCATISSIMO:
                            case SPICCATO:
                                return orchestralTemplate.staccato(note, channel, 10, instrument);
                            case MARCATO:
                            case STRONG_ACCENT:
                                return orchestralTemplate.marcato(note, channel, 10, instrument);
                            case TENUTO:
                            case DETACHED_LEGATO:
                                return orchestralTemplate.detache(note, channel, 10, instrument);
                            case PORTAMENTO:
                                return orchestralTemplate.portamento(note, channel, 10, instrument);
                        }
                    }
                    return orchestralTemplate.tremelo(note, channel, 10, instrument);
                case PIZZ:
                    if(note.getDynamic() != null){
                        switch (note.getDynamic()) {
                            case FP:
                                return orchestralTemplate.fortePiano(note, channel, 10, instrument);
                            case SFZ:
                                return orchestralTemplate.sforzando(note, channel,10, instrument);
                        }
                    }
                    if(note.getArticulation() != null){
                        switch (note.getArticulation()) {
                            case STACCATO:
                            case STACCATISSIMO:
                            case SPICCATO:
                                return orchestralTemplate.staccato(note, channel, 10, instrument);
                            case MARCATO:
                            case STRONG_ACCENT:
                                return orchestralTemplate.marcato(note, channel, 10, instrument);
                            case TENUTO:
                            case DETACHED_LEGATO:
                                return orchestralTemplate.detache(note, channel, 10, instrument);
                            case PORTAMENTO:
                                return orchestralTemplate.portamento(note, channel, 10, instrument);
                        }
                    }
                    return orchestralTemplate.pizzicato(note, channel, instrument);
                case CON_SORDINO:
                    if(note.getDynamic() != null){
                        switch (note.getDynamic()) {
                            case FP:
                                return orchestralTemplate.fortePiano(note, channel, CON_SORDINO_CC, instrument);
                            case SFZ:
                                return orchestralTemplate.sforzando(note, channel,CON_SORDINO_CC, instrument);
                        }
                    }
                    if(note.getArticulation() != null){
                        switch (note.getArticulation()) {
                            case STACCATO:
                            case STACCATISSIMO:
                            case SPICCATO:
                                return orchestralTemplate.staccato(note, channel, CON_SORDINO_CC, instrument);
                            case MARCATO:
                            case STRONG_ACCENT:
                                return orchestralTemplate.marcato(note, channel, CON_SORDINO_CC, instrument);
                            case TENUTO:
                            case DETACHED_LEGATO:
                                return orchestralTemplate.detache(note, channel, CON_SORDINO_CC, instrument);
                            case PORTAMENTO:
                                return orchestralTemplate.portamento(note, channel, CON_SORDINO_CC, instrument);
                        }
                    }
                    return orchestralTemplate.conSordino(note, channel, CON_SORDINO_CC, instrument);
//                case "arco":
//                case "col legno":
                case SUL_PONTICELLO:
                    if(note.getDynamic() != null){
                        switch (note.getDynamic()) {
                            case FP:
                                return orchestralTemplate.fortePiano(note, channel, PONTICELLO_CC, instrument);
                            case SFZ:
                                return orchestralTemplate.sforzando(note, channel, PONTICELLO_CC, instrument);
                        }
                    }
                    if(note.getArticulation() != null){
                        switch (note.getArticulation()) {
                            case STACCATO:
                            case STACCATISSIMO:
                            case SPICCATO:
                                return orchestralTemplate.staccato(note, channel, PONTICELLO_CC, instrument);
                            case MARCATO:
                            case STRONG_ACCENT:
                                return orchestralTemplate.marcato(note, channel, PONTICELLO_CC, instrument);
                            case TENUTO:
                            case DETACHED_LEGATO:
                                return orchestralTemplate.detache(note, channel, PONTICELLO_CC,instrument );
                            case PORTAMENTO:
                                return orchestralTemplate.portamento(note, channel, PONTICELLO_CC, instrument);
                        }
                    }
                    return orchestralTemplate.sulPonticello(note, channel, PONTICELLO_CC, instrument);
//                case "progressive vibrato":
//                case "sus":
//                    return createMidiEvents(channel, note, 0, 5);

//                case "sul tasto":
                case COL_LEGNO:
                    return orchestralTemplate.colLegno(note, channel, instrument);
                case STACCATO:
                    if(note.getDynamic() != null){
                        switch (note.getDynamic()) {
                            case FP:
                                return orchestralTemplate.fortePiano(note, channel, 10, instrument);
                            case SFZ:
                                return orchestralTemplate.sforzando(note, channel,10 ,instrument);
                        }
                    }
                    if(note.getArticulation() != null){
                        switch (note.getArticulation()) {
                            case STACCATO:
                            case STACCATISSIMO:
                            case SPICCATO:
                                return orchestralTemplate.staccato(note, channel, 10, instrument);
                            case MARCATO:
                            case STRONG_ACCENT:
                                return orchestralTemplate.marcato(note, channel, 10, instrument);
                            case TENUTO:
                            case DETACHED_LEGATO:
                                return orchestralTemplate.detache(note, channel, 10, instrument);
                            case PORTAMENTO:
                                return orchestralTemplate.portamento(note, channel, 10, instrument);
                        }
                    }
                    return orchestralTemplate.staccato(note, channel ,10, instrument);
                case PORTATO:
                    if(note.getDynamic() != null){
                        switch (note.getDynamic()) {
                            case FP:
                                return orchestralTemplate.fortePiano(note, channel, 10, instrument);
                            case SFZ:
                                return orchestralTemplate.sforzando(note, channel,10, instrument);
                        }
                    }
                    if(note.getArticulation() != null){
                        switch (note.getArticulation()) {
                            case STACCATO:
                            case STACCATISSIMO:
                            case SPICCATO:
                                return orchestralTemplate.staccato(note, channel, 10, instrument);
                            case MARCATO:
                            case STRONG_ACCENT:
                                return orchestralTemplate.marcato(note, channel, 10, instrument);
                            case TENUTO:
                            case DETACHED_LEGATO:
                                return orchestralTemplate.detache(note, channel, 10, instrument);
                            case PORTAMENTO:
                                return orchestralTemplate.portamento(note, channel, 10, instrument);
                        }
                    }
                    return orchestralTemplate.detache(note, channel, 10, instrument);
            }
        }
        return emptyList();
    }

}
