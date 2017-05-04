package cp.composition.voice;

import cp.combination.RhythmCombination;
import cp.combination.even.FourNoteEven;
import cp.combination.even.OneNoteEven;
import cp.combination.even.ThreeNoteEven;
import cp.combination.even.TwoNoteEven;
import cp.combination.uneven.*;
import cp.composition.beat.BeatGroup;
import cp.composition.timesignature.TimeConfig;
import cp.generator.pitchclass.*;
import cp.model.harmony.ChordType;
import cp.model.harmony.DependantHarmony;
import cp.model.note.Dynamic;
import cp.model.note.Note;
import cp.out.instrument.Articulation;
import cp.out.instrument.InstrumentGroup;
import cp.out.instrument.Technical;
import cp.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Created by prombouts on 22/11/2016.
 */

public abstract class Voice {

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

    @Value("${composition.numerator:4}")
    protected int numerator;
    @Value("${composition.denominator:4}")
    protected int denominator;

    protected List<PitchClassGenerator> pitchClassGenerators = new ArrayList<>();

    protected TimeConfig timeConfig;

    protected List<RhythmCombination> rhythmCombinations1;

    protected boolean randomBeats;
    protected boolean randomRhythmCombinations = true;
    protected Dynamic dynamic = Dynamic.MF;
    protected Technical technical = Technical.PORTATO;

    protected boolean hasDependentHarmony;
    protected List<ChordType> chordTypes = new ArrayList<>();


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
        rhythmCombinations = timeConfig.getAllBeats();
    }

    public TimeConfig getTimeConfig(){
        return timeConfig;
    }

    public PitchClassGenerator getRandomPitchClassGenerator() {
        return RandomUtil.getRandomFromList(pitchClassGenerators);
    }

    public List<Note> getNotes(BeatGroup beatGroup) {
        List<Note> notes;
        if (randomRhythmCombinations) {
            notes = getNotesRandom(beatGroup.getBeatLength());
        }else{
            notes = getNotes(beatGroup.getBeatLength());
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

    public void setRandomRhythmCombinations(boolean randomRhythmCombinations) {
        this.randomRhythmCombinations = randomRhythmCombinations;
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
                return stringArticulations;
            case WOODWINDS:
            case BRASS:
                return woodwindArticulations;
        }
        return Collections.emptyList();
    }

    public List<Technical> getTechnicals(InstrumentGroup instrumentGroup) {
        switch (instrumentGroup){
            case STRINGS:
                return stringTechnicals;
            case WOODWINDS:
            case BRASS:
                return woodwindTechnicals;
        }
        return Collections.emptyList();
    }

    public List<Dynamic> getDynamics() {
        return dynamics;
    }

    public List<Dynamic> getDynamics(InstrumentGroup instrumentGroup) {
        switch (instrumentGroup){
            case STRINGS:
            case WOODWINDS:
            case BRASS:
                return Arrays.asList(Dynamic.values());
        }
        return Collections.emptyList();
    }


    protected List<RhythmCombination> rhythmCombinations = new ArrayList<>();

    public List<Note> getNotes(int length) {
        return rhythmCombinations.get(0).getNotes(length);
//		int size = rhythmCombinations.size();
//		int beatLength = getBeatLength() / size;
//		RhythmCombination rhythmCombination = rhythmCombinations.get(0);
//		List<Note> notes = rhythmCombination.getNotes(beatLength);
//		List<Note> melodyNotes = new ArrayList<>(notes);
//		for (int i = 1; i < size; i++) {
//			rhythmCombination = rhythmCombinations.get(i);
//			int beatPosition = i * beatLength;
//			notes = rhythmCombination.getNotes(beatLength);
//			notes.forEach(n -> {n.setPosition(n.getPosition() + beatPosition);});
//			melodyNotes.addAll(notes);
//		}
//		return melodyNotes;
    }

    public List<Note> getNotesRandom(int length) {
        RhythmCombination rhythmCombination = RandomUtil.getRandomFromList(rhythmCombinations);
        return rhythmCombination.getNotes(length);
    }

    public void setRhythmCombinations(List<RhythmCombination> rhythmCombinations) {
        this.rhythmCombinations = rhythmCombinations;
    }
}
