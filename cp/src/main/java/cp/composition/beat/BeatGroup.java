package cp.composition.beat;

import cp.combination.RhythmCombination;
import cp.combination.RhythmCombinations;
import cp.composition.voice.NoteSizeValueObject;
import cp.generator.pitchclass.*;
import cp.model.TimeLineKey;
import cp.model.melody.Tonality;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import cp.out.print.Keys;
import cp.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BeatGroup {

    protected List<Tonality> tonalities = new ArrayList<>();
    protected Scale motiveScale;
    protected List<int[]> motivePitchClasses = new ArrayList<>();
    protected List<TimeLineKey> timeLineKeys = new ArrayList<>();
    protected List<PitchClassGenerator> pitchClassGenerators = new ArrayList<>();
    protected List<int[]> indexesMotivePitchClasses = new ArrayList<>();
    protected List<int[]> inverseIndexesMotivePitchClasses= new ArrayList<>();

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

    protected Map<Integer, List<RhythmCombination>> customCombi;

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

    protected Map<Integer, List<RhythmCombination>> getEvenBeatGroups(){
        Map<Integer, List<RhythmCombination>> map = new HashMap<>();
        List<RhythmCombination> beatGroups = new ArrayList<>();
        beatGroups.add(rhythmCombinations.fourNoteEven::pos1234);
        map.put(2, beatGroups);
        return map;
    }

    public List<Tonality> getTonalities() {
        return tonalities;
    }

    public List<int[]> getMotivePitchClasses() {
        return motivePitchClasses;
    }

    public List<int[]> getIndexesMotivePitchClasses() {
        return indexesMotivePitchClasses;
    }

    public List<int[]> getInverseIndexesMotivePitchClasses() {
        return inverseIndexesMotivePitchClasses;
    }

    public Scale getMotiveScale() {
        return motiveScale;
    }
}
