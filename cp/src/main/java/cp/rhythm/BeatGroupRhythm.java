package cp.rhythm;


public interface BeatGroupRhythm {

    RhythmCombinationVO getRandomRhythmNotesForBeatgroupType();


    RhythmCombinationVO getRandomRhythmNotesForBeatgroupType(Integer size);
}
