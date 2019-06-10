package cp.composition.beat.harmony;

import cp.composition.beat.BeatGroup;
import cp.composition.voice.NoteSizeValueObject;
import cp.generator.pitchclass.PitchClassGenerator;
import cp.model.TimeLineKey;
import cp.model.melody.Tonality;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.rhythm.BeatGroupRhythm;
import cp.rhythm.RhythmCombinationVO;
import cp.util.RowMatrix;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class BeatgroupHarmony extends BeatGroup {

    public BeatgroupHarmony(int length, BeatGroupRhythm beatGroupRhythm, List<PitchClassGenerator> pitchClassGenerators) {
        super(length, beatGroupRhythm, pitchClassGenerators);
    }

    public List<Note> getRhythmNotesForBeatgroupType(int size){
        RhythmCombinationVO rhythmCombinationVO = beatGroupRhythm.getRandomRhythmNotesForBeatgroupType(size);
        return rhythmCombinationVO.getRhythmCombination().getNotes(this.length, this.pulse);
    }

    public NoteSizeValueObject getRandomRhythmNotesForBeatgroupType(){
        RhythmCombinationVO rhythmCombinationVO = beatGroupRhythm.getRandomRhythmNotesForBeatgroupType();
        return new NoteSizeValueObject(rhythmCombinationVO.getSize(), rhythmCombinationVO.getRhythmCombination().getNotes(this.length, this.pulse));
    }

    @PostConstruct
    public void init() {
        timeLineKeys.add(new TimeLineKey(keys.C, Scale.MAJOR_SCALE));
//        timeLineKeys.add(new TimeLineKey(keys.F, Scale.MAJOR_SCALE));
//        timeLineKeys.add(new TimeLineKey(keys.A, Scale.HARMONIC_MINOR_SCALE));
//        timeLineKeys.add(new TimeLineKey(keys.D, Scale.HARMONIC_MINOR_SCALE));

        motiveScale = Scale.MAJOR_SCALE;

        tonality = Tonality.ATONAL;

        Scale scale = new Scale(new int[]{4, 0, 7});
        motivePitchClasses.add(scale);
//        scale = new Scale(new int[]{0, 5});
//        motivePitchClasses.add(scale);

//        chordTypes.add(ChordType.MINOR_CHR);
//        chordTypes.add(ChordType.MAJOR_CHR);

        int[] setClass = chordGenerator.generatePitchClasses("2-5");
        List<Integer> pitchClasses = Arrays.stream(setClass).boxed().collect(toList());
        RowMatrix rowMatrix = new RowMatrix(setClass.length, pitchClasses);

        extractIndexes();
    }



}

