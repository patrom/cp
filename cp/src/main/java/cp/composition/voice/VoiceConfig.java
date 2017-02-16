package cp.composition.voice;

import cp.combination.even.FourNoteEven;
import cp.combination.even.OneNoteEven;
import cp.combination.even.ThreeNoteEven;
import cp.combination.even.TwoNoteEven;
import cp.combination.uneven.*;
import cp.composition.beat.BeatGroup;
import cp.composition.beat.BeatGroupFactory;
import cp.composition.beat.BeatGroupStrategy;
import cp.composition.timesignature.TimeConfig;
import cp.generator.pitchclass.*;
import cp.model.harmony.ChordType;
import cp.model.harmony.DependantHarmony;
import cp.model.note.Dynamic;
import cp.model.note.Note;
import cp.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prombouts on 22/11/2016.
 */

public abstract class VoiceConfig {

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
    protected RestPitchClasses restPitchClasses;
    @Autowired
    protected RepeatingPitchClasses repeatingPitchClasses;

    @Autowired
    @Qualifier(value="time44")
    protected TimeConfig time44;
    @Autowired
    @Qualifier(value="time34")
    protected TimeConfig time34;
    @Autowired
    @Qualifier(value="time24")
    protected TimeConfig time24;
    @Autowired
    @Qualifier(value="time68")
    protected TimeConfig time68;
    @Autowired
    @Qualifier(value="time58")
    protected TimeConfig time58;

    @Autowired
    protected BeatGroupFactory beatGroupFactory;
    @Value("${composition.numerator:4}")
    protected int numerator;
    @Value("${composition.denominator:4}")
    protected int denominator;

    protected List<PitchClassGenerator> pitchClassGenerators = new ArrayList<>();

    protected TimeConfig timeConfig;

    protected BeatGroupStrategy beatGroupStrategy;

    protected boolean randomBeats;
    protected boolean randomRhytmCombinations = true;
    protected int volume = Dynamic.MF.getLevel();

    protected boolean hasDependentHarmony;
    protected List<ChordType> chordTypes = new ArrayList<>();

    protected void setTimeconfig(){
        if (numerator == 4 && denominator == 4) {
            randomBeats = true;
            timeConfig = time44;
        } else if (numerator == 3 && denominator == 4) {
            randomBeats = true;
            timeConfig = time34;
        } else if (numerator == 6 && denominator == 8) {
            randomBeats = true;
            timeConfig = time68;
        } else if (numerator == 5 && denominator == 8) {
            randomBeats = false;
            timeConfig = time58;
        } else if (numerator == 2 && denominator == 4) {
            randomBeats = true;
            timeConfig = time24;
        }
        beatGroupStrategy = timeConfig::getAllBeats;
    }

    public TimeConfig getTimeConfig(){
        return timeConfig;
    }

    public PitchClassGenerator getRandomPitchClassGenerator() {
        return RandomUtil.getRandomFromList(pitchClassGenerators);
    }

    public List<Note> getNotes(BeatGroup beatGroup) {
        List<Note> notes = new ArrayList<>();
        if (randomRhytmCombinations) {
            notes = beatGroup.getNotesRandom();
        }else{
            notes =  beatGroup.getNotes();
        }
        if(hasDependentHarmony){
            for (Note note : notes) {
                DependantHarmony dependantHarmony = new DependantHarmony();
                dependantHarmony.setChordType(RandomUtil.getRandomFromList(chordTypes));
                note.setDependantHarmony(dependantHarmony);
            }
        }
        return notes;
    }

    public BeatGroup getBeatGroup(int index){
        List<BeatGroup> beatGroups = beatGroupStrategy.getBeatGroups();
        int size = beatGroups.size();
        if (randomBeats) {
            return RandomUtil.getRandomFromList(beatGroups);
        }
        return beatGroups.get(index % size);
    }

    public void setRandomBeats(boolean randomBeats) {
        this.randomBeats = randomBeats;
    }

    public void setRandomRhytmCombinations(boolean randomRhytmCombinations) {
        this.randomRhytmCombinations = randomRhytmCombinations;
    }

    public int getVolume() {
        return volume;
    }

    public void hasDependentHarmony(boolean hasDependentHarmony) {
        this.hasDependentHarmony = hasDependentHarmony;
    }

    public void addChordType(ChordType chordType){
        chordTypes.add(chordType);
    }
}
