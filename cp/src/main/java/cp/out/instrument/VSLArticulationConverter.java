package cp.out.instrument;

import cp.model.note.Note;
import org.springframework.stereotype.Component;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.ShortMessage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by prombouts on 9/12/2016.
 */
@Component(value = "vslArticulationConverter")
public class VSLArticulationConverter implements ArticulationConverter{

    public List<MidiEvent> convertArticulation(Instrument instrument, int channel, Note note) throws InvalidMidiDataException {
        if (InstrumentGroup.STRINGS == instrument.getInstrumentGroup()) {
            switch (note.getArticulation()) {
                case LEGATO:
                    return createMidiEvents(channel, note, 1, 50);
//                case PORTATO:
//                    return createMidiEvents(channel, note, 3, 80);
                case MARCATO:
                    return createMidiEvents(channel, note, 1, 70);
                case STACCATO:
                    return createMidiEvents(channel, note, 1, 20);
                case STACCATISSIMO:
                    return createMidiEvents(channel, note, 1, 20);
//                case TENUTO:
//                    return createMidiEvents(channel, note, 3, 80);
//                case SPICCATO:
//                    return createMidiEvents(channel, note, 2, 20);
                case PIZZICATO:
                    return createMidiEvents(channel, note, 1, 70);
                case TREMELO:
                    return createMidiEvents(channel, note, 0, 120);
                case PONTICELLO_STACCATO:
                    return createMidiEvents(channel, note, 0, 20);
                case PONTICELLO_SUSTAIN:
                    return createMidiEvents(channel, note, 0, 10);
                case PONTICELL_TREMELO:
                    return createMidiEvents(channel, note, 0, 120);
                case PORTAMENTO:
                    return createMidiEvents(channel, note, 1, 90);
//                case FORTEPIANO:
//                    return createMidiEvents(channel, note, 1, 30);
                case DETACHE:
                    return createMidiEvents(channel, note, 1, 40);
                case SUSTAIN_VIBRATO:
                    return createMidiEvents(channel, note, 1, 10);
                case HARMONIC:
                    return createMidiEvents(channel, note, 0, 90);
                case REPEATLEGATO:
                    return createMidiEvents(channel, note, 1, 70);
//                case SFORZANDO:
//                    return createMidiEvents(channel, note, 1, 80);
            }
        }

        if (InstrumentGroup.WOODWINDS == instrument.getInstrumentGroup()) {
            switch (note.getArticulation()) {
                case STACCATO:
                    return createMidiEvents(channel, note, 0, 5);
                case STACCATISSIMO:
                    return createMidiEvents(channel, note, 0, 5);
//                case LEGATO:
//                    return createMidiEvents(channel, note, 0, 65);
//                case FLUTTER:
//                    return createMidiEvents(channel, note, 1, 25);
//                case DETACHE:
//                    return createMidiEvents(channel, note, 0, 15);
//                case SUSTAIN_VIBRATO:
//                    return createMidiEvents(channel, note, 0, 45);
//                case SUSTAIN_NO_VIBRATO:
//                    return createMidiEvents(channel, note, 0, 25);
            }
            switch (note.getDynamic()) {
                case FP:
                    return createMidiEvents(channel, note, 0, 110);
                case SFZ:
                    return createMidiEvents(channel, note, 0, 120);
            }
        }

        return Collections.emptyList();
    }

    private  List<MidiEvent> createMidiEvents(int channel, Note note, int programChange, int controllerValue) throws InvalidMidiDataException {
        List<MidiEvent> midiEvents = new ArrayList<>();
        MidiEvent programEvent = createProgramChangeMidiEvent(channel,programChange, note.getPosition());
        midiEvents.add(programEvent);
        MidiEvent controllerEvent = createModulationWheelControllerChangeMidiEvent(channel,controllerValue, note.getPosition());
        midiEvents.add(controllerEvent);
        return midiEvents;
    }

    private MidiEvent createModulationWheelControllerChangeMidiEvent(int channel, int value, int position)
            throws InvalidMidiDataException {
        ShortMessage change = new ShortMessage();
        change.setMessage(ShortMessage.CONTROL_CHANGE, channel, 1, value);
        return new MidiEvent(change, position);
    }

    private MidiEvent createProgramChangeMidiEvent(int channel, int program, int position)
            throws InvalidMidiDataException {
        ShortMessage change = new ShortMessage();
        change.setMessage(ShortMessage.PROGRAM_CHANGE, channel, program, 0);
        return new MidiEvent(change, position);
    }
}
