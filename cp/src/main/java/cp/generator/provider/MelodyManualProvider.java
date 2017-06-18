package cp.generator.provider;

import cp.composition.beat.BeatGroupTwo;
import cp.model.TimeLineKey;
import cp.model.melody.CpMelody;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import cp.out.print.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static cp.model.note.NoteBuilder.note;

/**
 * Created by prombouts on 12/05/2017.
 */
@Component(value = "melodyManualProvider")
public class MelodyManualProvider implements MelodyProvider{

    @Autowired
    private Keys keys;

    public List<CpMelody> getMelodies(){
        int voice0 = 0;

        List<CpMelody> melodies = new ArrayList<>();
        List<Note> notes = new ArrayList<>();
        notes.add(note().pos(0).pc(0).len(DurationConstants.EIGHT).build());
        notes.add(note().pos(DurationConstants.EIGHT).pc(2).len(DurationConstants.EIGHT).build());
//        notes.add(note().pos(DurationConstants.HALF).pc(5).pitch(89).octave(7).build());
//        notes.add(note().pos(DurationConstants.WHOLE).pc(7).pitch(55).octave(4).build());
        CpMelody melody = new CpMelody(notes, voice0, 0, DurationConstants.QUARTER);
        melody.setBeatGroup(new BeatGroupTwo(DurationConstants.EIGHT));
        melody.setTimeLineKey(new TimeLineKey(keys.C, Scale.MAJOR_SCALE));
        melody.setNotesSize(2);
//        melody.setTonality(Tonality.ATONAL);
//        melody.setContour();
        melodies.add(melody);

        notes = new ArrayList<>();
        notes.add(note().pos(0).pc(0).len(DurationConstants.SIXTEENTH).build());
        notes.add(note().pos(DurationConstants.SIXTEENTH).pc(2).len(DurationConstants.SIXTEENTH).build());
        notes.add(note().pos(DurationConstants.EIGHT).pc(4).len(DurationConstants.SIXTEENTH).build());
        notes.add(note().pos(DurationConstants.EIGHT + DurationConstants.SIXTEENTH).pc(5).len(DurationConstants.SIXTEENTH).build());
        melody = new CpMelody(notes, voice0, 0, DurationConstants.QUARTER);
        melody.setBeatGroup(new BeatGroupTwo(DurationConstants.EIGHT));
        melody.setTimeLineKey(new TimeLineKey(keys.C, Scale.MAJOR_SCALE));
        melody.setNotesSize(4);
//        melody.setTonality(Tonality.ATONAL);
        melodies.add(melody);

        notes = new ArrayList<>();
        notes.add(note().pos(0).pc(0).len(DurationConstants.QUARTER).build());
        melody = new CpMelody(notes, voice0, 0, DurationConstants.QUARTER);
        melody.setBeatGroup(new BeatGroupTwo(DurationConstants.EIGHT));
        melody.setTimeLineKey(new TimeLineKey(keys.C, Scale.MAJOR_SCALE));
        melody.setNotesSize(0);
//        melody.setTonality(Tonality.ATONAL);
        melodies.add(melody);

        return melodies;
    }
}
