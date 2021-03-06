package cp.composition.beat;

import cp.combination.RhythmCombination;
import cp.combination.RhythmCombinations;
import cp.composition.voice.NoteSizeValueObject;
import cp.generator.ChordGenerator;
import cp.generator.pitchclass.PitchClassGenerator;
import cp.model.TimeLineKey;
import cp.model.harmony.ChordType;
import cp.model.melody.Tonality;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.out.print.Keys;
import cp.rhythm.BeatGroupRhythm;
import cp.util.RandomUtil;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

public abstract class BeatGroup {

    protected static final Logger LOGGER = LoggerFactory.getLogger(BeatGroup.class);

    protected List<BeatGroup> beatGroups = new ArrayList<>();

    protected int length;
    protected int pulse;
    protected BeatGroupRhythm beatGroupRhythm;
    protected List<PitchClassGenerator> pitchClassGenerators = new ArrayList<>();

    public BeatGroup(int length) {
        this.length = length;
    }

    public BeatGroup(int length, BeatGroupRhythm beatGroupRhythm, List<PitchClassGenerator> pitchClassGenerators) {
        this.length = length;
        this.beatGroupRhythm = beatGroupRhythm;
        this.pitchClassGenerators = pitchClassGenerators;
    }

    public BeatGroup(int length, int pulse, BeatGroupRhythm beatGroupRhythm, List<PitchClassGenerator> pitchClassGenerators) {
        this.length = length;
        this.pulse = pulse;
        this.beatGroupRhythm = beatGroupRhythm;
        this.pitchClassGenerators = pitchClassGenerators;
    }

    protected Tonality tonality;
    protected Scale motiveScale;
    protected List<Scale> motivePitchClasses = new ArrayList<>();
    protected List<TimeLineKey> timeLineKeys = new ArrayList<>();
    protected List<ChordType> chordTypes = new ArrayList<>();

    public void setTonality(Tonality tonality) {
        this.tonality = tonality;
    }

    public void setMotiveScale(Scale motiveScale) {
        this.motiveScale = motiveScale;
    }

    public void addMotivePitchClasses(Scale scale) {
        motivePitchClasses.add(scale);
    }

    public void addTimeLineKey(TimeLineKey timeLineKey) {
        timeLineKeys.add(timeLineKey);
    }

    protected List<int[]> indexesMotivePitchClasses = new ArrayList<>();
    protected List<int[]> reversedIndexesMotivePitchClasses = new ArrayList<>();
    protected List<int[]> inverseIndexesMotivePitchClasses= new ArrayList<>();
    protected List<int[]> reverseInverseIndexesMotivePitchClasses= new ArrayList<>();

    @Autowired
    protected ChordGenerator chordGenerator;

    @Autowired
    protected Keys keys;

    @Value("${composition.denominator:4}")
    protected int denominator;

    @Autowired
    protected RhythmCombinations rhythmCombinations;

    public void setPulse(int pulse) {
        this.pulse = pulse;
    }

    public int getPulse() {
        return pulse;
    }

    public int getBeatLength() {
        return length;
	}

    public abstract List<Note> getRhythmNotesForBeatgroupType(int size);

    public abstract NoteSizeValueObject getRandomRhythmNotesForBeatgroupType();

    protected List<Note> getNotes(List<RhythmCombination> rhythmCombinations) {
        RhythmCombination rhythmCombination = RandomUtil.getRandomFromList(rhythmCombinations);
        return rhythmCombination.getNotes(this.getBeatLength(), this.pulse);
    }

    public List<TimeLineKey> getTimeLineKeys() {
        return timeLineKeys;
    }

    public List<PitchClassGenerator> getPitchClassGenerators() {
        return pitchClassGenerators;
    }

    public boolean hasPitchClassGenerators() {
        return !pitchClassGenerators.isEmpty();
    }

    public boolean hasMelody() {
        return !motivePitchClasses.isEmpty();
    }

    @PostConstruct
    public void init() {
        timeLineKeys.add(new TimeLineKey(keys.C, Scale.MAJOR_SCALE));
        timeLineKeys.add(new TimeLineKey(keys.F, Scale.MAJOR_SCALE));
        timeLineKeys.add(new TimeLineKey(keys.A, Scale.HARMONIC_MINOR_SCALE));
        timeLineKeys.add(new TimeLineKey(keys.D, Scale.HARMONIC_MINOR_SCALE));
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

    public List<ChordType> getChordTypes() {
        return chordTypes;
    }

    public boolean hasChordTypes() {
        return !chordTypes.isEmpty();
    }

    public Scale getMotiveScale() {
        return motiveScale;
    }

    public void setPitchClassGenerators(List<PitchClassGenerator> pitchClassGenerators) {
        this.pitchClassGenerators = pitchClassGenerators;
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
