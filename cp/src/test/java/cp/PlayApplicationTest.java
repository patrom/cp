package cp;

import cp.midi.MelodyInstrument;
import cp.model.note.Dynamic;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import cp.out.instrument.Articulation;
import cp.out.instrument.Technical;
import cp.out.instrument.strings.Viola;
import cp.out.play.InstrumentMapping;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sound.midi.MidiUnavailableException;
import java.util.ArrayList;
import java.util.List;

import static cp.model.note.NoteBuilder.note;

/**
 * Created by prombouts on 12/04/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefaultConfig.class)
@ExtendWith(SpringExtension.class)
public class PlayApplicationTest extends AbstractTest {

    @Test
    public void playMidiFilesOnKontaktFor() throws MidiUnavailableException {
        List<Note> notes = new ArrayList<>();
        notes.add(note().pos(0).len(DurationConstants.QUARTER).pc(0).pitch(60).octave(5).tech(Technical.TREMELO).dyn(Dynamic.F).build());
        notes.add(note().pos(DurationConstants.QUARTER).len(DurationConstants.QUARTER).pc(0).pitch(60).octave(5).dyn(Dynamic.F).art(Articulation.MARCATO).build());
        notes.add(note().pos(DurationConstants.HALF).len(DurationConstants.QUARTER).pc(2).pitch(62).art(Articulation.STACCATISSIMO).dyn(Dynamic.F).octave(5).build());
        notes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER).len(DurationConstants.QUARTER).pc(5).pitch(65).octave(5).dyn(Dynamic.MF).build());
        MelodyInstrument melodyInstrument = new MelodyInstrument();
        melodyInstrument.setNotes(notes);
        melodyInstrument.setInstrumentMapping(new InstrumentMapping(new Viola(), 2, 0));

        List<MelodyInstrument> melodyInstruments = new ArrayList<>();
        melodyInstruments.add(melodyInstrument);
        playOnKontakt(melodyInstruments, 60, 20000);
    }

}