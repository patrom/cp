package cp.composition.voice;

import cp.combination.RhythmCombination;
import cp.combination.even.FourNoteEven;
import cp.combination.even.OneNoteEven;
import cp.combination.even.ThreeNoteEven;
import cp.combination.even.TwoNoteEven;
import cp.combination.uneven.*;
import cp.composition.beat.BeatGroup;
import cp.composition.timesignature.TimeConfig;
import cp.generator.pitchclass.PassingPitchClasses;
import cp.generator.pitchclass.PitchClassGenerator;
import cp.generator.pitchclass.RandomPitchClasses;
import cp.generator.pitchclass.RepeatingPitchClasses;
import cp.model.harmony.ChordType;
import cp.model.note.Dynamic;
import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import cp.nsga.operator.mutation.MutationType;
import cp.out.instrument.Articulation;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.Technical;
import cp.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
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
    protected OneNoteEven oneNoteEven;
    @Autowired
    protected TwoNoteEven twoNoteEven;
    @Autowired
    protected ThreeNoteEven threeNoteEven;
    @Autowired
    protected FourNoteEven fourNoteEven;

    @Autowired
    protected ThreeNoteTriplet threeNoteUneven;
    @Autowired
    protected TwoNoteTriplet twoNoteUneven;
    @Autowired
    protected OneNoteTriplet oneNoteUneven;
    @Autowired
    protected ThreeNoteSexTuplet threeNoteSexTuplet;
    @Autowired
    protected FourNoteSexTuplet fourNoteSexTuplet;
    @Autowired
    protected FiveNoteSexTuplet fiveNoteSexTuplet;
    @Autowired
    protected SixNoteSexTuplet sixNoteSexTuplet;

    @Autowired
    protected FiveNoteQuintuplet fiveNoteQuintuplet;

    @Autowired
    protected RandomPitchClasses randomPitchClasses;
    @Autowired
    protected PassingPitchClasses passingPitchClasses;
    @Autowired
    protected RepeatingPitchClasses repeatingPitchClasses;

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

    protected Dynamic dynamic = Dynamic.MF;
    protected Technical technical = Technical.PORTATO;

    protected boolean hasDependentHarmony;
    protected List<ChordType> chordTypes = new ArrayList<>();


    protected Map<Integer, List<RhythmCombination>> evenRhythmCombinationsPerNoteSize;
    protected Map<Integer, List<RhythmCombination>> unevenRhythmCombinationsPerNoteSize;

    public Voice() {
        stringArticulations = Stream.of(
                Articulation.MARCATO,
                Articulation.STRONG_ACCENT,
                Articulation.STACCATO,
                Articulation.TENUTO,
                Articulation.DETACHED_LEGATO,//a tenuto line and staccato dot
                Articulation.STACCATISSIMO,
                Articulation.SPICCATO
        ).collect(toList());
        woodwindArticulations = Stream.of(
                Articulation.MARCATO,
                Articulation.STRONG_ACCENT,
                Articulation.STACCATO,
                Articulation.TENUTO,
                Articulation.DETACHED_LEGATO,//a tenuto line and staccato dot
                Articulation.STACCATISSIMO
        ).collect(toList());

        stringTechnicals = Stream.of(
                Technical.LEGATO,
                Technical.PIZZ,
                Technical.VIBRATO,
                Technical.PORTATO,
                Technical.SENZA_VIBRATO,
                Technical.STACCATO
//                Technical.SUL_PONTICELLO
        ).collect(toList());

        woodwindTechnicals = Stream.of(
                Technical.LEGATO,
                Technical.VIBRATO,
                Technical.PORTATO,
                Technical.SENZA_VIBRATO,
                Technical.STACCATO
//                Technical.FLUTTER_TONGUE
        ).collect(toList());

        dynamics = Stream.of(
                Dynamic.F,
                Dynamic.MF,
                Dynamic.MP,
                Dynamic.P
        ).collect(toList());
    }

    protected void setTimeconfig(){
        if (numerator == 4 && denominator == 4) {
            timeConfig = time44;
        } else if (numerator == 3 && denominator == 4) {
            timeConfig = time34;
        } else if (numerator == 6 && denominator == 8) {
            timeConfig = time68;
        } else if (numerator == 9 && denominator == 8) {
            timeConfig = time98;
        }else if (numerator == 12 && denominator == 8) {
            timeConfig = time128;
        } else if (numerator == 5 && denominator == 8) {
            timeConfig = time58;
        } else if (numerator == 2 && denominator == 4) {
            timeConfig = time24;
        }

        evenRhythmCombinationsPerNoteSize = defaultEvenCombinations;
        unevenRhythmCombinationsPerNoteSize = defaultUnEvenCombinations;

        mutationTypes = Collections.singletonList(MutationType.ALL);

    }

    public TimeConfig getTimeConfig(){
        return timeConfig;
    }

    public PitchClassGenerator getRandomPitchClassGenerator() {
        return RandomUtil.getRandomFromList(pitchClassGenerators);
    }

    public Dynamic getDynamic(){
        return dynamic;
    }

    public Technical getTechnical(){
        return technical;
    }

    public void hasDependentHarmony(boolean hasDependentHarmony) {
        this.hasDependentHarmony = hasDependentHarmony;
    }

    public void addChordType(ChordType chordType){
        chordTypes.add(chordType);
    }

    protected List<Articulation> stringArticulations = new ArrayList<>();
    protected List<Articulation> woodwindArticulations = new ArrayList<>();
    protected List<Dynamic> dynamics = new ArrayList<>();

    protected List<Technical> woodwindTechnicals = new ArrayList<>();
    protected List<Technical> stringTechnicals = new ArrayList<>();

    public List<Articulation> getArticulations(InstrumentGroup instrumentGroup) {
        switch (instrumentGroup){
            case STRINGS:
            case ORCHESTRAL_STRINGS:
                return stringArticulations;
            case WOODWINDS:
            case BRASS:
                return woodwindArticulations;
        }
        return emptyList();
    }

    public List<Technical> getTechnicals(InstrumentGroup instrumentGroup) {
        switch (instrumentGroup){
            case STRINGS:
            case ORCHESTRAL_STRINGS:
                return stringTechnicals;
            case WOODWINDS:
            case BRASS:
                return woodwindTechnicals;
        }
        return emptyList();
    }

    public List<Dynamic> getDynamics() {
        return dynamics;
    }

    public List<Dynamic> getDynamics(InstrumentGroup instrumentGroup) {
        switch (instrumentGroup){
            case STRINGS:
            case ORCHESTRAL_STRINGS:
            case WOODWINDS:
            case BRASS:
                return Arrays.asList(Dynamic.values());
        }
        return emptyList();
    }

    public List<Note> getRhythmNotesForBeatgroupType(BeatGroup beatGroup, int size){
        List<RhythmCombination> rhythmCombinations = new ArrayList<>();
        if (beatGroup.getType() == 2) {
            rhythmCombinations = this.evenRhythmCombinationsPerNoteSize.get(size);
            if(rhythmCombinations == null){
                LOGGER.info("No (provided) combination found for size: " + size);
                return emptyList();
            }

        }
        if (beatGroup.getType() == 3) {
            rhythmCombinations = this.unevenRhythmCombinationsPerNoteSize.get(size);
            if(rhythmCombinations == null){
                LOGGER.info("No (provided) combination found for size: " + size);
                return emptyList();
            }
        }
        return getNotes(beatGroup, rhythmCombinations);
    }

    public NoteSizeValueObject getRandomRhythmNotesForBeatgroupType(BeatGroup beatGroup){
        if (beatGroup.getType() == 2) {
            Object[] keys = evenRhythmCombinationsPerNoteSize.keySet().toArray();
            Integer key = (Integer) keys[new Random().nextInt(keys.length)];
            List<RhythmCombination> rhythmCombinations = evenRhythmCombinationsPerNoteSize.get(key);
            RhythmCombination rhythmCombination = RandomUtil.getRandomFromList(rhythmCombinations);
            return new NoteSizeValueObject(key, rhythmCombination);
        }
        if (beatGroup.getType() == 3) {
            Object[] keys = unevenRhythmCombinationsPerNoteSize.keySet().toArray();
            Integer key = (Integer) keys[new Random().nextInt(keys.length)];
            List<RhythmCombination> rhythmCombinations = unevenRhythmCombinationsPerNoteSize.get(key);
            RhythmCombination rhythmCombination = RandomUtil.getRandomFromList(rhythmCombinations);
            return new NoteSizeValueObject(key, rhythmCombination);
        }
        throw new IllegalStateException("No beatgroup found");
    }

    private List<Note> getNotes(BeatGroup beatGroup, List<RhythmCombination> rhythmCombinations) {
        RhythmCombination rhythmCombination = RandomUtil.getRandomFromList(rhythmCombinations);
        return rhythmCombination.getNotes(beatGroup.getBeatLength());
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
