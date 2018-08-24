package cp.out.print;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.composition.beat.BeatGroupConfig;
import cp.model.harmony.ChordType;
import cp.model.harmony.DependantHarmony;
import cp.model.note.Note;
import cp.model.note.NoteBuilder;
import cp.model.rhythm.DurationConstants;
import cp.out.instrument.Articulation;
import jm.music.data.Score;
import jm.util.View;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by prombouts on 29/05/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DefaultConfig.class, VariationConfig.class, BeatGroupConfig.class})
public class ScoreUtilitiesTest extends JFrame {

    @Autowired
    private ScoreUtilities scoreUtilities;
    @Test
    public void createMelody() throws Exception {
        List<Note> notes = new ArrayList<>();
        DependantHarmony dependantHarmony = new DependantHarmony();
        dependantHarmony.setChordType(ChordType.CH2_GROTE_SIXT);

        notes.add(NoteBuilder.note().len(DurationConstants.QUARTER).pc(4).pitch(64).octave(4).pos(0).art(Articulation.STACCATO).dep(dependantHarmony).build());
        notes.add(NoteBuilder.note().len(DurationConstants.HALF).pc(2).pitch(62).octave(4).pos(DurationConstants.QUARTER).art(Articulation.STACCATO).build());
        notes.add(NoteBuilder.note().len(DurationConstants.HALF).pc(4).pitch(64).octave(4).pos(DurationConstants.HALF + DurationConstants.QUARTER).art(Articulation.STACCATO).build());
        notes.add(NoteBuilder.note().len(DurationConstants.EIGHT).pc(5).pitch(65).octave(4).pos(DurationConstants.WHOLE + DurationConstants.QUARTER).art(Articulation.STACCATO).build());
        notes.add(NoteBuilder.note().len(DurationConstants.EIGHT).pc(7).pitch(67).octave(4).pos(DurationConstants.WHOLE + DurationConstants.THREE_EIGHTS).art(Articulation.STACCATO).build());
        notes.add(NoteBuilder.note().len(DurationConstants.QUARTER).pc(9).pitch(69).octave(4).pos(DurationConstants.WHOLE + DurationConstants.HALF).art(Articulation.STACCATO).build());
        notes.add(NoteBuilder.note().len(DurationConstants.HALF).pc(11).pitch(71).octave(4).pos(DurationConstants.WHOLE + DurationConstants.SIX_EIGHTS).art(Articulation.STACCATO).build());
        notes.add(NoteBuilder.note().len(DurationConstants.HALF).pc(0).pitch(60).octave(4).pos(2 * DurationConstants.WHOLE + DurationConstants.QUARTER).art(Articulation.STACCATO).build());
        Score score = scoreUtilities.createMelody(notes);
        View.notate(score);
        Thread.sleep(10000);
    }

}