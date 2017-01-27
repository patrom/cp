package cp.nsga.operator.relation;

import cp.model.Motive;
import cp.model.harmony.DependantHarmony;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.nsga.MusicVariable;
import jmetal.core.Solution;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prombouts on 22/01/2017.
 */
public class HarmonyRelation {

    private int source;
    private int target;

    public void setSource(int source) {
        this.source = source;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public Solution execute(Solution solution){
        Motive motive = ((MusicVariable)solution.getDecisionVariables()[0]).getMotive();
        MelodyBlock melodyBlockSource = motive.getRandomMelodyForVoice(source);
        MelodyBlock dependantMelodyBlock = motive.getRandomMelodyForVoice(target);

        List<Note> melodyBlockNotes = melodyBlockSource.getMelodyBlockNotes();
        List<Note> notes = new ArrayList<>();
        for (int i = 0; i < melodyBlockNotes.size(); i++) {
            Note note = melodyBlockNotes.get(i);
            DependantHarmony dependantHarmony = note.getDependantHarmony();
            Note targetNote = note.clone();
            targetNote.setPitchClass((note.getPitchClass() + 4) % 12);//test grote terts hoger
            targetNote.setPitch(note.getPitch() + 4);
            notes.add(targetNote);
        }
        dependantMelodyBlock.setNotes(notes);
        return solution;
    }
}
