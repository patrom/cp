package cp.composition.voice;

import cp.combination.RhythmCombinations;
import cp.composition.beat.BeatGroup;
import cp.composition.beat.BeatGroups;
import cp.composition.timesignature.TimeConfig;
import cp.generator.pitchclass.PitchClassGenerator;
import cp.model.harmony.ChordType;
import cp.model.note.Dynamic;
import cp.model.rhythm.DurationConstants;
import cp.nsga.operator.mutation.MutationType;
import cp.out.instrument.Technical;
import cp.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by prombouts on 22/11/2016.
 */

public abstract class Voice {

    private static final Logger LOGGER = LoggerFactory.getLogger(Voice.class);

    public static final int DEFAULT_DYNAMIC_LEVEL = Dynamic.MF.getLevel();
    public static final Technical DEFAULT_TECHNICAL = Technical.LEGATO;
    public static final Dynamic DEFAULT_DYNAMIC = Dynamic.MF;
    public static final int DEFAULT_LENGTH = DurationConstants.QUARTER;

    @Autowired
    protected RhythmCombinations rhythmCombinations;

    @Autowired
    protected BeatGroup beatGroupThree;

    @Autowired
    @Qualifier(value="time44")
    protected TimeConfig time44;
    @Autowired
    @Qualifier(value="timeDouble44")
    protected TimeConfig timeDouble44;
    @Autowired
    @Qualifier(value="time34")
    protected TimeConfig time34;
    @Autowired
    @Qualifier(value="time24")
    protected TimeConfig time24;
    @Autowired
    @Qualifier(value="time98")
    protected TimeConfig time98;
    @Autowired
    @Qualifier(value="time68")
    protected TimeConfig time68;
    @Autowired
    @Qualifier(value="time128")
    protected TimeConfig time128;
    @Autowired
    @Qualifier(value="time58")
    protected TimeConfig time58;
    @Autowired
    @Qualifier(value="time54")
    protected TimeConfig time54;
    @Autowired
    @Qualifier(value="timeRandom")
    protected TimeConfig timeRandom;

    @Value("${composition.numerator:4}")
    protected int numerator;
    @Value("${composition.denominator:4}")
    protected int denominator;

    protected List<PitchClassGenerator> pitchClassGenerators = new ArrayList<>();

    protected List<MutationType> mutationTypes = new ArrayList<>();

    protected TimeConfig timeConfig;

    protected boolean melodiesProvided = false;

    protected boolean hasDependentHarmony;
    protected List<ChordType> chordTypes = new ArrayList<>();

    protected List<BeatGroup> allBeatgroups = new ArrayList<>();
    @Autowired
    protected BeatGroups beatgroups;

    protected void setTimeconfig(){
        if (numerator == 4 && denominator == 4) {
            timeConfig = time44;
            allBeatgroups.forEach(beatGroup -> beatGroup.setPulse(DurationConstants.QUARTER));
//            allBeatgroups = allBeatgroups.stream().filter(beatGroup -> beatGroup.getType() == 1
//                    || beatGroup.getType() == 2 || beatGroup.getType() == 4).collect(toList());
        } else if (numerator == 3 && denominator == 4) {
            timeConfig = time34;
            allBeatgroups.forEach(beatGroup -> beatGroup.setPulse(DurationConstants.QUARTER));
//            allBeatgroups = allBeatgroups.stream().filter(beatGroup -> beatGroup.getType() == 3).collect(toList());
        } else if (numerator == 6 && denominator == 8) {
            timeConfig = time68;
            allBeatgroups.forEach(beatGroup -> beatGroup.setPulse(DurationConstants.EIGHT));
//            allBeatgroups = allBeatgroups.stream().filter(beatGroup -> beatGroup.getType() == 3).collect(toList());
        } else if (numerator == 9 && denominator == 8) {
            timeConfig = time98;
            allBeatgroups.forEach(beatGroup -> beatGroup.setPulse(DurationConstants.EIGHT));
//            allBeatgroups = allBeatgroups.stream().filter(beatGroup -> beatGroup.getType() == 3).collect(toList());
        }else if (numerator == 12 && denominator == 8) {
            timeConfig = time128;
            allBeatgroups.forEach(beatGroup -> beatGroup.setPulse(DurationConstants.EIGHT));
//            allBeatgroups = allBeatgroups.stream().filter(beatGroup -> beatGroup.getType() == 3).collect(toList());
        } else if (numerator == 5 && denominator == 8) {
            timeConfig = time58;
            allBeatgroups = Arrays.asList(beatgroups.beatGroupTwo, beatgroups.beatGroupThree);
            allBeatgroups.forEach(beatGroup -> beatGroup.setPulse(DurationConstants.EIGHT));
        } else if (numerator == 5 && denominator == 4) {
            timeConfig = time58;
            allBeatgroups = Arrays.asList(beatgroups.beatGroupTwo, beatgroups.beatGroupThree);
            allBeatgroups.forEach(beatGroup -> beatGroup.setPulse(DurationConstants.QUARTER));
        } else if (numerator == 2 && denominator == 4) {
            timeConfig = time24;
            allBeatgroups.forEach(beatGroup -> beatGroup.setPulse(DurationConstants.QUARTER));
//            allBeatgroups = allBeatgroups.stream().filter(beatGroup -> beatGroup.getType() == 2).collect(toList());
        }

        mutationTypes = Collections.singletonList(MutationType.ALL);
    }

    public BeatGroup getRandomBeatgroup(){
        if(allBeatgroups.size() > 1){
            return RandomUtil.getRandomFromList(allBeatgroups);
        }
        return allBeatgroups.get(0);
    }

    public List<BeatGroup> getBeatGroups() {
        return allBeatgroups;
    }

    public TimeConfig getTimeConfig(){
        return timeConfig;
    }

    public PitchClassGenerator getRandomPitchClassGenerator() {
        return RandomUtil.getRandomFromList(pitchClassGenerators);
    }

    public void hasDependentHarmony(boolean hasDependentHarmony) {
        this.hasDependentHarmony = hasDependentHarmony;
    }

    public void addChordType(ChordType chordType){
        chordTypes.add(chordType);
    }

    public int getNumerator() {
        return numerator;
    }

    public boolean isMelodiesProvided(){
        return melodiesProvided;
    }

    public List<MutationType> getMutationTypes() {
        return mutationTypes;
    }

}
