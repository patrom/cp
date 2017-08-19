package cp.generator.provider;

import cp.composition.beat.BeatGroupTwo;
import cp.model.TimeLineKey;
import cp.model.melody.CpMelody;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static cp.model.note.NoteBuilder.note;

@Component(value = "melodyRhythmProvider")
public class MelodyRhythmProvider extends AbstractProvidder implements MelodyProvider {

    @Autowired
    @Qualifier(value = "melodyGeneratorProvider")
    public MelodyGeneratorProvider melodyGeneratorProvider;

    public List<CpMelody> getMelodies(){
        if(melodies.isEmpty()){
//            getRhythmMelodies();
//            melodies.add(getRest(0, DurationConstants.EIGHT));
//            melodies.add(getRest(0, DurationConstants.QUARTER));
            final BeatGroupTwo beatGroupTwo = new BeatGroupTwo(DurationConstants.QUARTER);
            CpMelody cpMelody = melodyGeneratorProvider.generateMelodyConfig(0, beatGroupTwo);
            melodies.add(cpMelody);
        }
        return melodies;
    }

    private List<CpMelody> getRhythmMelodies(){
        int voice0 = 0;
        TimeLineKey timeLineKey = new TimeLineKey(keys.C, Scale.MAJOR_SCALE);
        List<Note> notes = new ArrayList<>();
        notes.add(note().pos(0).pc(0).len(DurationConstants.QUARTER + DurationConstants.EIGHT).build());
        notes.add(note().pos(DurationConstants.QUARTER + DurationConstants.EIGHT).pc(0).len(DurationConstants.EIGHT).build());
        CpMelody melody = new CpMelody(notes, voice0, 0, DurationConstants.HALF);
        melody.setBeatGroup(new BeatGroupTwo(DurationConstants.QUARTER));
        melody.setNotesSize(2);
//        melody.setTonality(Tonality.ATONAL);
        melody.setTimeLineKey(timeLineKey);
        melodies.add(melody);
        //variation1
        notes = new ArrayList<>();
        notes.add(note().pos(0).pc(0).len(DurationConstants.QUARTER).build());
//        notes.add(note().pos(DurationConstants.EIGHT).pc(0).len(DurationConstants.EIGHT ).build());
        melody = new CpMelody(notes, voice0, 0, DurationConstants.QUARTER);
        melody.setBeatGroup(new BeatGroupTwo(DurationConstants.EIGHT ));
        melody.setNotesSize(1);
        melody.setTimeLineKey(timeLineKey);
//        melody.setTonality(Tonality.ATONAL);
        melodies.add(melody);

        notes = new ArrayList<>();
        notes.add(note().pos(0).pc(0).len(DurationConstants.EIGHT).build());
        notes.add(note().pos(DurationConstants.EIGHT).pc(0).len(DurationConstants.QUARTER + DurationConstants.EIGHT).build());
        melody = new CpMelody(notes, voice0, 0, DurationConstants.HALF);
        melody.setBeatGroup(new BeatGroupTwo(DurationConstants.QUARTER));
        melody.setNotesSize(2);
        melody.setTimeLineKey(timeLineKey);
//        melody.setTonality(Tonality.ATONAL);
        melodies.add(melody);


        notes = new ArrayList<>();
        notes.add(note().pos(0).pc(0).len(DurationConstants.HALF).build());
        melody = new CpMelody(notes, voice0, 0, DurationConstants.HALF);
        melody.setBeatGroup(new BeatGroupTwo(DurationConstants.QUARTER));
        melody.setNotesSize(2);
        melody.setTimeLineKey(timeLineKey);
//        melody.setTonality(Tonality.ATONAL);
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

//        notes = new ArrayList<>();
//        notes.add(note().pos(0).pc(0).len(DurationConstants.QUARTER).build());
//        notes.add(note().pos(DurationConstants.QUARTER).pc(1).len(DurationConstants.QUARTER).build());
//        notes.add(note().pos(DurationConstants.QUARTER * 2).pc(3).len(DurationConstants.QUARTER).build());
//        notes.add(note().pos(DurationConstants.QUARTER * 3).pc(2).len(DurationConstants.QUARTER * 3).build());
//        melody = new CpMelody(notes, voice0, 0, DurationConstants.QUARTER * 6);
//        melody.setBeatGroup(new BeatGroupThree(DurationConstants.QUARTER * 2));
//        melody.setNotesSize(4);
//        melody.setTonality(Tonality.ATONAL);
//        melodies.add(melody);

//        notes = new ArrayList<>();
//        notes.add(note().pos(0).pc(0).len(DurationConstants.QUARTER).build());
//        melody = new CpMelody(notes, voice0, 0, DurationConstants.QUARTER);
//        melody.setBeatGroup(new BeatGroupThree(DurationConstants.EIGHT));
//        melody.setNotesSize(1);
//        melody.setTonality(Tonality.ATONAL);
//        melodies.add(melody);

        return melodies;
    }
}
