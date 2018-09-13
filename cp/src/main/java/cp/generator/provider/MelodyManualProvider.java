package cp.generator.provider;

import cp.composition.beat.BeatGroups;
import cp.model.melody.CpMelody;
import cp.model.note.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static cp.model.note.NoteBuilder.note;

/**
 * Created by prombouts on 12/05/2017.
 */
@Component(value = "melodyManualProvider")
public class MelodyManualProvider extends AbstractProvidder implements MelodyProvider{

    @Autowired
    @Qualifier(value = "melodyGeneratorProvider")
    protected MelodyProvider melodyGeneratorProvider;
    @Autowired
    private BeatGroups beatgroups;

    public List<CpMelody> getMelodies(int voice){
        if(melodies.isEmpty()){
//            getTonalMelodies();
//            melodies.add(getNot
//
            List<Note> notes = new ArrayList<>();
            notes.add(note().pc(4).build());
            notes.add(note().pc(5).build());
            notes.add(note().pc(7).build());
            notes.add(note().pc(9).build());
            CpMelody melody = new CpMelody(notes, voice, 0, 0);
//        melody.setBeatGroup(beatgroups.beatGroupHarmonyTwo);//check length is same as melody length!!
//            melody.setTimeLineKey(new TimeLineKey(keys.C, Scale.MAJOR_SCALE));//TODO same scale as timeline composition!!!
            melody.setNotesSize(4);
//            melody.setMutationType(MutationType.NONE);
//        melody.setTonality(Tonality.ATONAL);
//        melody.setContour();
            melodies.add(melody);
// e(0, DurationConstants.THREE_EIGHTS));
//            melodies.add(getRest(0, DurationConstants.QUARTER));
//            melodies.addAll(melodyGeneratorProvider.getMelodies(voice));
        }
        return melodies;
    }

}
