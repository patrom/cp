package cp.composition.voice;

import cp.combination.RhythmCombination;
import cp.combination.RhythmCombinations;
import cp.composition.beat.BeatGroup;
import cp.composition.beat.BeatGroups;
import cp.composition.timesignature.TimeConfig;
import cp.generator.pitchclass.*;
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

import javax.annotation.Resource;
import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * Created by prombouts on 22/11/2016.
 */

public abstract class Voice {

    private static final Logger LOGGER = LoggerFactory.getLogger(Voice.class);

    public static final int DEFAULT_DYNAMIC_LEVEL = Dynamic.MF.getLevel();
    public static final Technical DEFAULT_TECHNICAL = Technical.LEGATO;
    public static final Dynamic DEFAULT_DYNAMIC = Dynamic.MF;
    public static final int DEFAULT_LENGTH = DurationConstants.QUARTER;

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

    @Autowired
    protected RhythmCombinations rhythmCombinations;

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


    protected Map<Integer, List<RhythmCombination>> evenRhythmCombinationsPerNoteSize;
    protected Map<Integer, List<RhythmCombination>> unevenRhythmCombinationsPerNoteSize;

    protected List<BeatGroup> allBeatgroups = new ArrayList<>();
    @Autowired
    protected BeatGroups beatgroups;

    protected void setTimeconfig(){
        if (numerator == 4 && denominator == 4) {
            timeConfig = time44;
            allBeatgroups = allBeatgroups.stream().filter(beatGroup -> beatGroup.getType() == 2
                || beatGroup.getType() == 4).collect(toList());
        } else if (numerator == 3 && denominator == 4) {
            timeConfig = time34;
            allBeatgroups = allBeatgroups.stream().filter(beatGroup -> beatGroup.getType() == 3).collect(toList());
        } else if (numerator == 6 && denominator == 8) {
            timeConfig = time68;
            allBeatgroups = allBeatgroups.stream().filter(beatGroup -> beatGroup.getType() == 3).collect(toList());
        } else if (numerator == 9 && denominator == 8) {
            timeConfig = time98;
            allBeatgroups = allBeatgroups.stream().filter(beatGroup -> beatGroup.getType() == 3).collect(toList());
        }else if (numerator == 12 && denominator == 8) {
            timeConfig = time128;
            allBeatgroups = allBeatgroups.stream().filter(beatGroup -> beatGroup.getType() == 3).collect(toList());
        } else if (numerator == 5 && denominator == 8) {
            timeConfig = time58;
            allBeatgroups = Arrays.asList(beatgroups.beatGroupTwo, beatgroups.beatGroupThree);
        } else if (numerator == 5 && denominator == 4) {
            timeConfig = time58;
            allBeatgroups = Arrays.asList(beatgroups.beatGroupTwo, beatgroups.beatGroupThree);
        } else if (numerator == 2 && denominator == 4) {
            timeConfig = time24;
            allBeatgroups = allBeatgroups.stream().filter(beatGroup -> beatGroup.getType() == 2).collect(toList());
        }

        evenRhythmCombinationsPerNoteSize = defaultEvenCombinations;
        unevenRhythmCombinationsPerNoteSize = defaultUnEvenCombinations;

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

    protected Map<Integer, List<RhythmCombination>> getCombinations(){
        Map<Integer, List<RhythmCombination>> map = new HashMap<>();
        //rest
//        List<RhythmCombination> zeroCombinations = new ArrayList<>();
//        zeroCombinations.add(oneNoteEven::rest);
//        map.put(0, zeroCombinations);

        List<RhythmCombination> oneCombinations = new ArrayList<>();
//        oneCombinations.add(rhythmCombinations.combiNoteEven::pos23pos12);
//        oneCombinations.add(oneNoteEven::pos3);
//		oneCombinations.add(oneNoteEven::pos4);
//        map.put(1, oneCombinations);

        List<RhythmCombination> twoCombinations = new ArrayList<>();
//////		twoCombinations.add(twoNoteEven::pos12);
        twoCombinations.add(rhythmCombinations.twoNoteEven::pos13);
//        twoCombinations.add(twoNoteEven::pos14);
//////		twoCombinations.add(twoNoteEven::pos34);
////        //twoCombinations.add(twoNoteEven::pos23);
////        //twoCombinations.add(twoNoteEven::pos24);
//        twoCombinations.add(rhythmCombinations.twoNoteEven::pos14);
        map.put(2, twoCombinations);
//
        List<RhythmCombination> threeCombinations = new ArrayList<>();
//        threeCombinations.add(threeNoteEven::pos123);
//        threeCombinations.add(threeNoteEven::pos134);
////		threeCombinations.add(threeNoteEven::pos124);
//        threeCombinations.add(threeNoteEven::pos234);
//        threeCombinations.add(rhythmCombinations.threeNoteEven::pos14_and);
//        threeCombinations.add(rhythmCombinations.threeNoteEven::pos1pos34);
//        threeCombinations.add(rhythmCombinations.threeNoteEven::pos1pos13);
//        threeCombinations.add(rhythmCombinations.threeNoteEven::pos1pos24);
//        map.put(3, threeCombinations);
//
//        List<RhythmCombination> fourCombinations = new ArrayList<>();
//        fourCombinations.add(fourNoteEven::pos1234);
//        map.put(4, fourCombinations);

//        oneCombinations.add(oneNoteUneven::pos1);

//		threeCombinations.add(threeNoteUneven::pos123);
////		map.put(3, threeCombinations);
//
//		twoCombinations.add(twoNoteUneven::pos23);
//		twoCombinations.add(twoNoteUneven::pos12);
//		twoCombinations.add(twoNoteUneven::pos13);
//        		map.put(2, twoCombinations);

//        List<RhythmCombination> fiveCombinations = new ArrayList<>();
//		fiveCombinations.add(quintuplet::pos12345);
//		map.put(5, fiveCombinations);
        return map;
    }

}
