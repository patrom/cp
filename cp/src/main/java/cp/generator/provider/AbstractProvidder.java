package cp.generator.provider;

import cp.composition.beat.BeatGroup;
import cp.model.melody.CpMelody;
import cp.model.melody.Tonality;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import cp.nsga.operator.mutation.MutationType;
import cp.out.print.Keys;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static cp.model.note.NoteBuilder.note;

/**
 * Created by prombouts on 26/06/2017.
 */
public abstract class AbstractProvidder {

    @Autowired
    protected Keys keys;

    protected List<CpMelody> melodies = new ArrayList<>();

    protected List<CpMelody> getAtonalMelodies(){
        int voice0 = 0;

        List<Note> notes = new ArrayList<>();
        notes.add(note().pos(0).pc(0).len(DurationConstants.QUARTER).build());
        notes.add(note().pos(DurationConstants.QUARTER).pc(1).len(DurationConstants.QUARTER).build());
        notes.add(note().pos(DurationConstants.QUARTER * 2).pc(5).len(DurationConstants.QUARTER).build());
        notes.add(note().pos(DurationConstants.QUARTER * 3).pc(4).len(DurationConstants.QUARTER * 3).build());
        CpMelody melody = new CpMelody(notes, voice0, 0, DurationConstants.HALF);
//        melody.setBeatGroup(new BeatGroupThree(DurationConstants.QUARTER * 2));
        melody.setNotesSize(4);
        melody.setTonality(Tonality.ATONAL);
        melody.setMutationType(MutationType.ALL);
        melodies.add(melody);
        //variation1
        notes = new ArrayList<>();
        notes.add(note().pos(0).pc(0).len(DurationConstants.QUARTER).build());
        notes.add(note().pos(DurationConstants.QUARTER).pc(1).len(DurationConstants.QUARTER + DurationConstants.EIGHT).build());
        notes.add(note().pos(DurationConstants.QUARTER * 2 + DurationConstants.EIGHT).pc(5).len(DurationConstants.EIGHT).build());
        notes.add(note().pos(DurationConstants.QUARTER * 3).pc(4).len(DurationConstants.QUARTER * 3).build());
        melody = new CpMelody(notes, voice0, 0, DurationConstants.QUARTER * 6);
//        melody.setBeatGroup(new BeatGroupThree(DurationConstants.QUARTER * 2));
        melody.setNotesSize(4);
        melody.setTonality(Tonality.ATONAL);
        melody.setMutationType(MutationType.ALL);
        melodies.add(melody);
//        //variation2
//        notes = new ArrayList<>();
//        notes.add(note().pos(0).pc(0).len(DurationConstants.EIGHT + DurationConstants.SIXTEENTH).build());
//        notes.add(note().pos(DurationConstants.EIGHT + DurationConstants.SIXTEENTH).pc(1).len(DurationConstants.SIXTEENTH).build());
//        notes.add(note().pos(DurationConstants.QUARTER).pc(5).len(DurationConstants.EIGHT).build());
//        notes.add(note().pos(DurationConstants.QUARTER + DurationConstants.EIGHT).pc(4).len(DurationConstants.EIGHT).build());
//        melody = new CpMelody(notes, voice0, 0, DurationConstants.HALF);
//        melody.setBeatGroup(new BeatGroupThree(DurationConstants.QUARTER));
//        melody.setNotesSize(4);
//        melody.setTonality(Tonality.ATONAL);
//        melodies.add(melody);

        notes = new ArrayList<>();
        notes.add(note().pos(0).pc(0).len(DurationConstants.QUARTER).build());
        notes.add(note().pos(DurationConstants.QUARTER).pc(1).len(DurationConstants.QUARTER).build());
        notes.add(note().pos(DurationConstants.QUARTER * 2).pc(3).len(DurationConstants.QUARTER).build());
        notes.add(note().pos(DurationConstants.QUARTER * 3).pc(2).len(DurationConstants.QUARTER * 3).build());
        melody = new CpMelody(notes, voice0, 0, DurationConstants.QUARTER * 6);
//        melody.setBeatGroup(new BeatGroupThree(DurationConstants.QUARTER * 2));
        melody.setNotesSize(4);
        melody.setTonality(Tonality.ATONAL);
        melody.setMutationType(MutationType.ALL);
        melodies.add(melody);

//        notes = new ArrayList<>();
//        notes.add(note().pos(0).pc(0).len(DurationConstants.QUARTER).build());
//        melody = new CpMelody(notes, voice0, 0, DurationConstants.QUARTER);
//        melody.setBeatGroup(new BeatGroupThree(DurationConstants.EIGHT));
//        melody.setNotesSize(1);
//        melody.setTonality(Tonality.ATONAL);
//        melodies.add(melody);

        return melodies;
    }

    protected CpMelody getRest(int voice, int duration){
        List<Note> notes = new ArrayList<>();
        notes.add(note().pos(0).rest().len(duration).build());
        CpMelody melody = new CpMelody(notes, voice, 0, duration);
//        melody.setBeatGroup(new BeatGroupTwo(duration/2));
        melody.setNotesSize(0);
        melody.setMutationType(MutationType.ALL);
        return melody;
    }

    protected CpMelody getNote(int voice, int duration){
        List<Note> notes = new ArrayList<>();
        notes.add(note().pos(0).pc(0).len(duration).build());
        CpMelody melody = new CpMelody(notes, voice, 0, duration);
//        melody.setBeatGroup(new BeatGroupTwo(duration/2));
        melody.setNotesSize(1);
        melody.setMutationType(MutationType.ALL);
        return melody;
    }

    protected CpMelody augmentationOrDiminuation(CpMelody melody, double times){
        CpMelody clone = melody.clone();
        clone.getNotes().forEach(n -> {
            n.setPosition((int) (n.getPosition() * times));
            n.setDisplayLength((int) (n.getDisplayLength() * times));
            n.setLength((int) (n.getLength() * times));}
        );
        clone.setEnd((int) (clone.getEnd() * times));
        final BeatGroup beatGroup = melody.getBeatGroup();
        clone.setBeatGroup(beatGroup);
        clone.setMutationType(MutationType.ALL);
        return clone;
    }

    protected List<CpMelody> getTonalMelodies(){
        int voice0 = 0;

        List<Note> notes = new ArrayList<>();
        notes.add(note().pos(0).pc(4).len(DurationConstants.EIGHT).build());
        notes.add(note().pos(DurationConstants.EIGHT).pc(5).len(DurationConstants.EIGHT).build());
        notes.add(note().pos(DurationConstants.QUARTER).pc(7).len(DurationConstants.EIGHT).build());
        notes.add(note().pos(DurationConstants.THREE_EIGHTS).pc(9).len(DurationConstants.THREE_EIGHTS).build());
        CpMelody melody = new CpMelody(notes, voice0, 0, DurationConstants.SIX_EIGHTS);
//        melody.setBeatGroup(new BeatGroupThree(DurationConstants.QUARTER));//check length is same as melody length!!
//        melody.setTimeLineKey(new TimeLineKey(keys.C, Scale.MAJOR_SCALE));//TODO same scale as timeline composition!!!
        melody.setNotesSize(4);
        melody.setMutationType(MutationType.SYMMETRY);
//        melody.setTonality(Tonality.ATONAL);
//        melody.setContour();
        melodies.add(melody);

        notes = new ArrayList<>();
        notes.add(note().pos(0).pc(5).len(DurationConstants.EIGHT).build());
        notes.add(note().pos(DurationConstants.EIGHT).pc(2).len(DurationConstants.EIGHT).build());
        notes.add(note().pos(DurationConstants.QUARTER).pc(4).len(DurationConstants.EIGHT).build());
        notes.add(note().pos(DurationConstants.THREE_EIGHTS).pc(0).len(DurationConstants.THREE_EIGHTS).build());
        melody = new CpMelody(notes, voice0, 0, DurationConstants.SIX_EIGHTS);
//        melody.setBeatGroup(new BeatGroupThree(DurationConstants.QUARTER));//check length is same as melody length!!
//        melody.setTimeLineKey(new TimeLineKey(keys.C, Scale.MAJOR_SCALE));//TODO same scale as timeline composition!!!
        melody.setNotesSize(4);
        melody.setMutationType(MutationType.SYMMETRY);
//        melody.setTonality(Tonality.ATONAL);
//        melody.setContour();
        melodies.add(melody);


//        notes = new ArrayList<>();
//        notes.add(note().pos(0).pc(2).len(DurationConstants.EIGHT).build());
//        notes.add(note().pos(DurationConstants.EIGHT).pc(5).len(DurationConstants.EIGHT).build());
//        notes.add(note().pos(DurationConstants.QUARTER).pc(3).len(DurationConstants.EIGHT).build());
//        notes.add(note().pos(DurationConstants.THREE_EIGHTS).pc(0).len(DurationConstants.THREE_EIGHTS).build());
//        melody = new CpMelody(notes, voice0, 0, DurationConstants.SIX_EIGHTS);
//        melody.setBeatGroup(new BeatGroupThree(DurationConstants.EIGHT));
//        melody.setTimeLineKey(new TimeLineKey(keys.C, Scale.DORIAN));
//        melody.setNotesSize(4);
////        melody.setTonality(Tonality.ATONAL);
//        melodies.add(melody);

//        notes = new ArrayList<>();
//        notes.add(note().pos(0).rest().len(DurationConstants.QUARTER).build());
//        melody = new CpMelody(notes, voice0, 0, DurationConstants.QUARTER);
//        melody.setBeatGroup(new BeatGroupTwo(DurationConstants.EIGHT));
//        melody.setTimeLineKey(new TimeLineKey(keys.C, Scale.MAJOR_SCALE));
//        melody.setNotesSize(0);
////        melody.setTonality(Tonality.ATONAL);
//        melodies.add(melody);
        return melodies;
    }
}
