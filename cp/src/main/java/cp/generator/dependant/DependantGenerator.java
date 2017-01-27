package cp.generator.dependant;

import cp.model.TimeLine;
import cp.model.TimeLineKey;
import cp.model.harmony.DependantHarmony;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prombouts on 26/01/2017.
 */
@Component
public class DependantGenerator {

    @Autowired
    private TimeLine timeLine;

    private int sourceVoice;
    private int dependantVoice;

    public void generateDependantHarmonies(List<MelodyBlock> melodies) {
        MelodyBlock MelodyBlock = melodies.stream().filter(m -> m.getVoice() == sourceVoice).findFirst().get();
        MelodyBlock dependantMelodyBlock = melodies.stream().filter(m -> m.getVoice() == dependantVoice).findFirst().get();
        List<Note> notes = new ArrayList<>();
        List<Note> melodyBlockNotesWithRests = MelodyBlock.getMelodyBlockNotesWithRests();
        for (Note note : melodyBlockNotesWithRests) {
            DependantHarmony dependantHarmony = note.getDependantHarmony();
            Note clone = note.clone();
            clone.setVoice(dependantMelodyBlock.getVoice());
            if(!note.isRest()){
                int pitchClass;
                switch (dependantHarmony.getChordType()){
                    case ALL:
                        TimeLineKey timeLineKeyAtPosition = timeLine.getTimeLineKeyAtPosition(note.getPosition(), note.getVoice());
                        pitchClass = timeLineKeyAtPosition.getScale().pickRandomPitchClass();
                        pitchClass = (pitchClass + timeLineKeyAtPosition.getKey().getInterval()) % 12;
                        int interval = ((12 + pitchClass) - note.getPitchClass()) % 12;
                        clone.setPitch(note.getPitch() + interval);
                        break;
                    case CH2_GROTE_TERTS:
                        pitchClass = getDependantPitchClass(note, 2);
                        int ic = Util.intervalClass(pitchClass - note.getPitchClass());
                        clone.setPitch(note.getPitch() + ic);
                        break;
                    case CH2_GROTE_SIXT:
                        pitchClass = getDependantPitchClass(note, 5);
                        ic = Util.intervalClass(pitchClass - note.getPitchClass());
                        clone.setPitch(note.getPitch() + (12 - ic));
                        break;
                    case CH2_KWART:
                        pitchClass = getDependantPitchClass(note, 3);
                        ic = Util.intervalClass(pitchClass - note.getPitchClass());
                        clone.setPitch(note.getPitch() + ic);
                        break;
                    case CH2_KWINT:
                        pitchClass = getDependantPitchClass(note, 4);
                        ic = Util.intervalClass(pitchClass - note.getPitchClass());
                        clone.setPitch(note.getPitch() + 12 - ic);
                        break;
                    default:
                        throw new IllegalArgumentException("Dependant harmony not set for note: " + note);
                }
                clone.setPitchClass(pitchClass);
            }
            notes.add(clone);
        }
        dependantMelodyBlock.setNotes(notes);
    }

    protected int getDependantPitchClass(Note note, int step) {
        TimeLineKey timeLineKeyAtPosition = timeLine.getTimeLineKeyAtPosition(note.getPosition(), note.getVoice());
        int pcInKeyOfC = Util.convertToKeyOfC(note.getPitchClass(), timeLineKeyAtPosition.getKey().getInterval());
        int pitchClass = timeLineKeyAtPosition.getScale().pickHigerStepFromScale(pcInKeyOfC, step);
        pitchClass = (pitchClass + timeLineKeyAtPosition.getKey().getInterval()) % 12;
        return pitchClass;
    }

    public void setSourceVoice(int sourceVoice) {
        this.sourceVoice = sourceVoice;
    }

    public void setDependantVoice(int dependantVoice) {
        this.dependantVoice = dependantVoice;
    }
}