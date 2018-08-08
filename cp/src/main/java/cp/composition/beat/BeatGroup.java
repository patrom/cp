package cp.composition.beat;

import cp.combination.RhythmCombination;
import cp.combination.RhythmCombinations;
import cp.composition.voice.NoteSizeValueObject;
import cp.generator.ChordGenerator;
import cp.generator.pitchclass.*;
import cp.model.TimeLineKey;
import cp.model.melody.Tonality;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import cp.out.print.Keys;
import cp.util.RandomUtil;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BeatGroup {

    protected Tonality tonality;
    protected Scale motiveScale;
    protected List<Scale> motivePitchClasses = new ArrayList<>();
    protected List<TimeLineKey> timeLineKeys = new ArrayList<>();
    protected List<PitchClassGenerator> pitchClassGenerators = new ArrayList<>();
    protected List<int[]> indexesMotivePitchClasses = new ArrayList<>();
    protected List<int[]> reversedIndexesMotivePitchClasses = new ArrayList<>();
    protected List<int[]> inverseIndexesMotivePitchClasses= new ArrayList<>();
    protected List<int[]> reverseInverseIndexesMotivePitchClasses= new ArrayList<>();

    @Autowired
    protected ChordGenerator chordGenerator;

    @Autowired
    protected Keys keys;

    @Autowired
    protected RandomPitchClasses randomPitchClasses;
    @Autowired
    protected PassingPitchClasses passingPitchClasses;
    @Autowired
    protected RepeatingPitchClasses repeatingPitchClasses;
    @Autowired
    protected OrderPitchClasses orderPitchClasses;
    @Autowired
    protected OrderRandomNotePitchClasses orderRandomNotePitchClasses;
    @Autowired
    protected OrderNoteRepetitionPitchClasses orderNoteRepetitionPitchClasses;

    protected static Logger LOGGER = LoggerFactory.getLogger(BeatGroup.class);

    @Resource(name = "defaultUnevenCombinations")
    protected Map<Integer, List<RhythmCombination>> defaultUnEvenCombinations;

    @Resource(name = "defaultEvenCombinations")
    protected Map<Integer, List<RhythmCombination>> defaultEvenCombinations;

    @Resource(name = "homophonicEven")
    protected  Map<Integer, List<RhythmCombination>> homophonicEven;

    @Resource(name = "homophonicUneven")
    protected Map<Integer, List<RhythmCombination>> homophonicUneven;

    @Resource(name = "fixedEven")
    protected Map<Integer, List<RhythmCombination>> fixedEven;

    @Resource(name = "fixedUneven")
    protected Map<Integer, List<RhythmCombination>> fixedUneven;

    protected Map<Integer, List<RhythmCombination>> rhythmCombinationMap;

    protected Map<Integer, List<RhythmCombination>> rhythmCombinationsHarmonyMap;

    protected Map<Integer, List<RhythmCombination>> rhythmCombinationsMotiveMap;

    @Value("${composition.denominator:4}")
    protected int denominator;

    @Autowired
    protected RhythmCombinations rhythmCombinations;

    public abstract int getType();

    public int getBeatLength() {
        if (denominator == 8) {
            return getType() * DurationConstants.EIGHT;
        } else if (denominator == 2) {
            return getType() * DurationConstants.HALF;
        }
        return getType() * DurationConstants.QUARTER;
	}

    public abstract List<Note> getRhythmNotesForBeatgroupType(int size);

    public abstract NoteSizeValueObject getRandomRhythmNotesForBeatgroupType();

    protected List<Note> getNotes(List<RhythmCombination> rhythmCombinations) {
        RhythmCombination rhythmCombination = RandomUtil.getRandomFromList(rhythmCombinations);
        return rhythmCombination.getNotes(this.getBeatLength());
    }

    public abstract int getRandomNoteSize();

    public List<TimeLineKey> getTimeLineKeys() {
        return timeLineKeys;
    }

    public List<PitchClassGenerator> getPitchClassGenerators() {
        return pitchClassGenerators;
    }

    @PostConstruct
    public void init() {
        timeLineKeys.add(new TimeLineKey(keys.C, Scale.MAJOR_SCALE));
        timeLineKeys.add(new TimeLineKey(keys.F, Scale.MAJOR_SCALE));
        timeLineKeys.add(new TimeLineKey(keys.A, Scale.HARMONIC_MINOR_SCALE));
        timeLineKeys.add(new TimeLineKey(keys.D, Scale.HARMONIC_MINOR_SCALE));
    }

    protected Map<Integer, List<RhythmCombination>> getEvenBeatGroups(){
        Map<Integer, List<RhythmCombination>> map = new HashMap<>();
        List<RhythmCombination> beatGroups = new ArrayList<>();
//        beatGroups.add(rhythmCombinations.fourNoteEven::pos1234);
//        map.put(4, beatGroups);

        beatGroups = new ArrayList<>();
        beatGroups.add(rhythmCombinations.threeNoteEven::pos134);
        beatGroups.add(rhythmCombinations.threeNoteEven::pos234);
        map.put(3, beatGroups);

        beatGroups = new ArrayList<>();
        beatGroups.add(rhythmCombinations.twoNoteEven::pos13);
        map.put(2, beatGroups);

        beatGroups = new ArrayList<>();
        beatGroups.add(rhythmCombinations.oneNoteEven::pos1);
        map.put(1, beatGroups);

        beatGroups = new ArrayList<>();
        beatGroups.add(rhythmCombinations.oneNoteEven::rest);
        map.put(0, beatGroups);

        return map;
    }

    public Tonality getTonality() {
        return tonality;
    }

    public List<Scale> getMotivePitchClasses() {
        return motivePitchClasses;
    }

    public List<int[]> getIndexesMotivePitchClasses() {
        return indexesMotivePitchClasses;
    }

    public List<int[]> getInverseIndexesMotivePitchClasses() {
        return inverseIndexesMotivePitchClasses;
    }

    public List<int[]> getReversedIndexesMotivePitchClasses() {
        return reversedIndexesMotivePitchClasses;
    }

    public List<int[]> getReverseInverseIndexesMotivePitchClasses() {
        return reverseInverseIndexesMotivePitchClasses;
    }

    public Scale getMotiveScale() {
        return motiveScale;
    }

    protected void extractIndexes() {
        if (tonality == Tonality.TONAL) {
            for (Scale motivePitchClass : motivePitchClasses) {
                int[] indexes = getMotiveScale().getIndexes(motivePitchClass.getPitchClasses());
                indexesMotivePitchClasses.add(indexes);

                int[] reversesIndexes = new int[indexes.length];
                System.arraycopy(indexes, 0, reversesIndexes, 0 , indexes.length);
                ArrayUtils.reverse(reversesIndexes);
                reversedIndexesMotivePitchClasses.add(reversesIndexes);

                int[] inversedIndexes = new int[indexes.length];
                for (int i = 0; i < indexes.length; i++) {
                    int index = indexes[i];
                    int inversedIndex = Scale.MAJOR_SCALE.getInversedIndex(1, index);
                    inversedIndexes[i] = inversedIndex;
                }
                inverseIndexesMotivePitchClasses.add(inversedIndexes);

                int[] reversesInverseIndexes = new int[inversedIndexes.length];
                System.arraycopy(inversedIndexes, 0, reversesInverseIndexes, 0 , inversedIndexes.length);
                ArrayUtils.reverse(reversesInverseIndexes);
                reverseInverseIndexesMotivePitchClasses.add(reversesInverseIndexes);
            }
        }
    }
}
