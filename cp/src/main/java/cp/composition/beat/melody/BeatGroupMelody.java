package cp.composition.beat.melody;

import cp.composition.beat.BeatGroup;
import cp.composition.voice.NoteSizeValueObject;
import cp.generator.pitchclass.PitchClassGenerator;
import cp.model.note.Note;
import cp.rhythm.BeatGroupRhythm;
import cp.rhythm.RhythmCombinationVO;

import java.util.List;

public class BeatGroupMelody extends BeatGroup {

    public BeatGroupMelody(int length, BeatGroupRhythm beatGroupRhythm, List<PitchClassGenerator> pitchClassGenerators) {
        super(length, beatGroupRhythm, pitchClassGenerators);
    }

    public BeatGroupMelody(int length, int pulse, BeatGroupRhythm beatGroupRhythm, List<PitchClassGenerator> pitchClassGenerators) {
        super(length, pulse, beatGroupRhythm, pitchClassGenerators);
    }

    public List<Note> getRhythmNotesForBeatgroupType(int size){
        RhythmCombinationVO rhythmCombinationVO = beatGroupRhythm.getRandomRhythmNotesForBeatgroupType(size);
        return rhythmCombinationVO.getRhythmCombination().getNotes(this.length, this.pulse);
    }

    public NoteSizeValueObject getRandomRhythmNotesForBeatgroupType(){
        RhythmCombinationVO rhythmCombinationVO = beatGroupRhythm.getRandomRhythmNotesForBeatgroupType();
        return new NoteSizeValueObject(rhythmCombinationVO.getSize(), rhythmCombinationVO.getRhythmCombination().getNotes(this.length, this.pulse));
    }

}