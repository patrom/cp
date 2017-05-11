package cp.composition;

import cp.composition.beat.BeatGroupTwo;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static cp.model.note.NoteBuilder.note;

/**
 * Created by prombouts on 20/11/2016.
 */
@Component(value = "melodyComposition")
@ConditionalOnProperty(name = "composition.voices", havingValue = "1")
public class MelodyComposition extends Composition {

    public List<MelodyBlock> melody(){
        List<MelodyBlock> melodyBlocks = new ArrayList<>();

        MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlockConfig(voice0);
        melodyBlocks.add(melodyBlock);

        return melodyBlocks;
    }

    public List<MelodyBlock> melodyProvided(){
        List<MelodyBlock> melodyBlocks = new ArrayList<>();

        List<CpMelody> melodies = new ArrayList<>();
        List<Note> notes = new ArrayList<>();
        notes.add(note().pos(0).pc(0).len(DurationConstants.EIGHT).build());
        notes.add(note().pos(DurationConstants.EIGHT).pc(2).len(DurationConstants.EIGHT).build());
//        notes.add(note().pos(DurationConstants.HALF).pc(5).pitch(89).octave(7).build());
//        notes.add(note().pos(DurationConstants.WHOLE).pc(7).pitch(55).octave(4).build());
        CpMelody melody = new CpMelody(notes, voice0, 0, DurationConstants.QUARTER);
        melody.setBeatGroup(new BeatGroupTwo(DurationConstants.EIGHT, 2));
        melody.setKey(C);
//        melody.setStructure(Structure.ATONAL);
//        melody.setContour();
        melodies.add(melody);

        notes = new ArrayList<>();
        notes.add(note().pos(0).pc(4).len(DurationConstants.EIGHT).build());
        notes.add(note().pos(DurationConstants.EIGHT).pc(1).len(DurationConstants.EIGHT).build());
//        notes.add(note().pos(DurationConstants.HALF).pc(5).pitch(89).octave(7).build());
//        notes.add(note().pos(DurationConstants.WHOLE).pc(7).pitch(55).octave(4).build());
        melody = new CpMelody(notes, voice0, 0, DurationConstants.QUARTER);
        melody.setBeatGroup(new BeatGroupTwo(DurationConstants.EIGHT, 2));
        melody.setKey(C);
//        melody.setStructure(Structure.ATONAL);
        melodies.add(melody);
        MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlockConfig(voice0, melodies);
        melodyBlocks.add(melodyBlock);

        return melodyBlocks;
    }
}
