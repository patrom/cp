package cp.composition;

import cp.composition.timesignature.TimeConfig;
import cp.config.InstrumentConfig;
import cp.config.TimbreConfig;
import cp.config.VoiceConfig;
import cp.generator.MelodyGenerator;
import cp.generator.MusicProperties;
import cp.generator.pitchclass.PassingPitchClasses;
import cp.generator.pitchclass.RandomPitchClasses;
import cp.generator.pitchclass.RepeatingPitchClasses;
import cp.generator.pitchclass.TwelveTonePitchClasses;
import cp.model.TimeLine;
import cp.model.TimeLineKey;
import cp.model.common.CommonNote;
import cp.model.dissonance.*;
import cp.model.dissonance.subset.*;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import cp.model.timbre.Timbre;
import cp.nsga.operator.mutation.melody.NoteSizeMutation;
import cp.nsga.operator.relation.RelationConfig;
import cp.objective.harmony.DissonantResolutionImpl;
import cp.objective.harmony.HarmonicObjective;
import cp.objective.harmony.HarmonicResolutionObjective;
import cp.objective.meter.MeterObjective;
import cp.out.instrument.Instrument;
import cp.out.play.InstrumentMapping;
import cp.out.print.Keys;
import cp.out.print.note.Key;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public abstract class Composition {

	public int axisHigh = 2;
	public int axisLow = 2;

	protected int voice0 = 0;
	protected int voice1 = 1;
	protected int voice2 = 2;
	protected int voice3 = 3;
	protected int voice4 = 4;
	protected int voice5 = 5;
	protected int voice6 = 6;
	protected int voice7 = 7;
	protected Instrument instrument1;
	protected Instrument instrument2;
	protected Instrument instrument3;
	protected Instrument instrument4;
	protected Instrument instrument5;
	protected Instrument instrument6;

	@Autowired
	protected NoteSizeMutation noteSizeMutation;
	@Autowired
	protected MusicProperties musicProperties;
	@Autowired
	protected MelodyGenerator melodyGenerator;

	@Autowired
	protected RandomPitchClasses randomPitchClasses;
	@Autowired
	protected PassingPitchClasses passingPitchClasses;
	@Autowired
	protected RepeatingPitchClasses repeatingPitchClasses;
	@Autowired
	private TwelveTonePitchClasses twelveTonePitchClasses;

    @Autowired
	private HarmonicResolutionObjective harmonicResolutionObjective;
	@Autowired
	private DissonantResolutionImpl dissonantResolutionImpl;
	@Autowired
	protected HarmonicObjective harmonicObjective;
	@Autowired
	protected IntervalDissonance intervalDissonance;
	@Autowired
	protected DyadTriadsTetraAndPentaChordal dyadTriadsTetraAndPentaChordal;
	@Autowired
	protected TorkeDissonance torkeDissonance;
	@Autowired
	protected SetClassDissonance setClassDissonance;
	@Autowired
	private TonalDissonance tonalDissonance;
	@Autowired
	private TonalSetClassDissonance tonalSetClassDissonance;
	@Autowired
	private PolyTonality polyTonality;
	@Autowired
	private FourCubeTrio fourCubeTrio;
	@Autowired
	private Pentatonic pentatonic;
	@Autowired
	private SymmetryDissonance symmetryDissonance;
	@Autowired
	private AdditiveDissonance additiveDissonance;
    @Autowired
	private MajorScale_7_35 majorScale_7_35;
    @Autowired
    private SubSets_8_25 subSets_8_25;
    @Autowired
    private SubSets_6_27 subSets_6_27;
    @Autowired
    private SubSets_6_7 subSets_6_7;
    @Autowired
    private SubSets_6_32 subSets_6_32;
    @Autowired
    private SubSetDissonance subSetDissonance;

	@Autowired
	protected TimeLine timeLine;
	@Autowired
	private MeterObjective meterObjective;
	@Autowired
	private TimbreConfig timbreConfig;

	@Autowired
	protected Keys keys;
	
	protected final int start = 0;
	protected int end;
	
	private TimeConfig timeConfig;
	
	@Autowired
	@Qualifier(value="time44")
	protected TimeConfig time44;
	@Autowired
	@Qualifier(value="time24")
	protected TimeConfig time24;
	@Autowired
	@Qualifier(value="time34")
	protected TimeConfig time34;
	@Autowired
	@Qualifier(value="time68")
	protected TimeConfig time68;
	@Autowired
	@Qualifier(value="time98")
	protected TimeConfig time98;
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

    @Value("${composition.voices}")
	protected int numberOfVoices;

	protected HarmonizeMelody harmonizeMelody;
	protected int harmonizeVoice;
	@Autowired
	protected RelationConfig operatorConfig;
	@Autowired
	protected InstrumentConfig instrumentConfig;
	@Autowired
	protected VoiceConfig voiceConfig;
    @Autowired
	private CommonNote commonNote;

	protected CompositionGenre compositionGenre;

	public void setCompositionGenre(CompositionGenre compositionGenre) {
		this.compositionGenre = compositionGenre;
	}

	@PostConstruct
	public void init(){
		composeInKey(keys.C);
		inTempo(93);
		musicProperties.setNumerator(numerator);
		musicProperties.setDenominator(denominator);
		meterObjective.setComposition(this);
		
//		instruments = Choral.getSATB();
//		instruments = ensemble.getFluteClarinetBassoonGreen();
//		instruments = ensemble.getStrings(mellowPurple);
//		instruments = ensemble.getStringDuo();
//		instruments = ensemble.getStringTrio();
//		instruments = ensemble.getPiano(3);
//		instruments = ensemble.getStringQuartet();

		setTimeconfig();
		end = 4  * getTimeConfig().getMeasureDuration();
		timeLine.setEnd(end);
		//time line
		List<TimeLineKey> timeLineKeys = new ArrayList<>();
		timeLineKeys.add(new TimeLineKey(keys.C, Scale.MAJOR_SCALE, 0 ,0));
//		timeLineKeys.add(new TimeLineKey(keys.F, Scale.MELODIC_MINOR_SCALE, 0 ,0));
//		timeLineKeys.add(new TimeLineKey(keys.C, Scale.DORIAN_SCALE, 0 ,0));
//		timeLineKeys.add(new TimeLineKey(keys.C, Scale.HARMONIC_MINOR_SCALE, 0 ,0));
//		timeLineKeys.add(new TimeLineKey(keys.G, Scale.MAJOR_SCALE, 0 ,0));

		List<Integer> durations = new ArrayList<>();
//		durations.add(DurationConstants.QUARTER);
//		durations.add(DurationConstants.SIX_EIGHTS);
//		durations.add(DurationConstants.HALF);
		durations.add(DurationConstants.WHOLE);
//		durations.add(DurationConstants.THREE_QUARTERS);

        //1. timeline for all voices
//		timeLine.randomKeysAndDurations(timeLineKeys, durations);

//        List<List<TimeLineKey>> timeLineKeysList = new ArrayList<>();
//        List<CommonNoteValueObject> commonNotes = commonNote.getCommonNotes("6-32", 3);
//        for (CommonNoteValueObject commonNoteValueObject : commonNotes) {
//            System.out.println("Common ");
//            commonNoteValueObject.getCommonPitchClasses().forEach(integer -> System.out.println(integer));
//            System.out.println("Disjunct ");
//            commonNoteValueObject.getDisjunctPitchClasses1().forEach(integer -> System.out.println(integer));
//            System.out.println("Disjunct ");
//            commonNoteValueObject.getDisjunctPitchClasses2().forEach(integer -> System.out.println(integer));
//
//            //2. or add timeline key per voice
//            List<TimeLineKey> timeLineKeysForVoices = new ArrayList<>();
//            timeLineKeysForVoices.add(new TimeLineKey(0, keys.C, commonNoteValueObject.getDisjunct1Scale(), 0 ,0));//first disjunct notes
////            timeLineKeysForVoices.add(new TimeLineKey(1, keys.C, commonNoteValueObject.getDisjunct1Scale(), 0 ,0));//first disjunct notes in different voices
//            timeLineKeysForVoices.add(new TimeLineKey(1, keys.C, commonNoteValueObject.getCommonScale(), 0 ,0));
//
////        timeLineKeysForVoices.add(new TimeLineKey(3, keys.C,  Scale.OCTATCONIC_01, 0 ,0));//melody
//            timeLineKeysList.add(timeLineKeysForVoices);
//            List<TimeLineKey> timeLineKeysForVoices2 = new ArrayList<>();
//            timeLineKeysForVoices2.add(new TimeLineKey(0, keys.C, commonNoteValueObject.getDisjunct2Scale(), 0 ,0));//second disjunct notes in diff voices
////            timeLineKeysForVoices2.add(new TimeLineKey(1, keys.C, commonNoteValueObject.getDisjunct2Scale(), 0 ,0));
//            timeLineKeysForVoices2.add(new TimeLineKey(1, keys.C, commonNoteValueObject.getCommonScale(), 0 ,0));
////        timeLineKeysForVoices2.add(new TimeLineKey(3, keys.C,  Scale.OCTATCONIC_12, 0 ,0));//melody
////        timeLineKeysForVoice0.add(new TimeLineKey(keys.C, Scale.OCTATCONIC_WHOLE, 0 ,0));
//            timeLineKeysList.add(timeLineKeysForVoices2);
//        }
//        timeLine.randomKeysAndDurationsForVoices(durations, timeLineKeysList);

        //2. or add timeline key per voice
        List<TimeLineKey> timeLineKeysForVoices = new ArrayList<>();
        timeLineKeysForVoices.add(new TimeLineKey(0, keys.C, Scale.SET_6_32, 0 ,0));
        timeLineKeysForVoices.add(new TimeLineKey(1, keys.C, Scale.SET_6_32, 0 ,0));
        timeLineKeysForVoices.add(new TimeLineKey(2, keys.C, Scale.SET_6_32, 0 ,0));
//        timeLineKeysForVoices.add(new TimeLineKey(2, keys.C, Scale.SET_8_25, 0 ,0));
//        timeLineKeysForVoices.add(new TimeLineKey(3, keys.C, Scale.SET_8_25, 0 ,0));
        timeLine.randomKeysAndDurationsForVoices(durations, timeLineKeysForVoices);


//        List<TimeLineKey> timeLineKeysForVoices2 = new ArrayList<>();
//        timeLineKeysForVoices2.add(new TimeLineKey(0, keys.C, new Scale(new int[]{8, 11}), 0 ,0));//second disjunct notes in diff voices
//        timeLineKeysForVoices2.add(new TimeLineKey(1, keys.C,  new Scale(new int[]{8, 11}), 0 ,0));
//        timeLineKeysForVoices2.add(new TimeLineKey(3, keys.C,  Scale.OCTATCONIC_12, 0 ,0));//melody
//        timeLineKeysForVoice0.add(new TimeLineKey(keys.C, Scale.OCTATCONIC_WHOLE, 0 ,0));
//        timeLine.randomKeysAndDurationsForVoices(durations, timeLineKeysForVoices, timeLineKeysForVoices2);


//        List<TimeLineKey> timeLineKeysForVoice1 = new ArrayList<>();
//        timeLineKeysForVoice1.add(new TimeLineKey(keys.C,  commonNotes.get(0).getCommonScale(), 0 ,0));//common notes
////        timeLineKeysForVoice1.add(new TimeLineKey(keys.C, Scale.OCTATCONIC_WHOLE, 0 ,0));
//        timeLine.randomKeysAndDurationsForVoice(2, timeLineKeysForVoice1, durations);

//        List<TimeLineKey> timeLineKeysForVoice2 = new ArrayList<>();
//        timeLineKeysForVoice2.add(new TimeLineKey(keys.C,  new Scale(new int[]{0, 3}), 0 ,0));
//        timeLineKeysForVoice2.add(new TimeLineKey(keys.C, Scale.OCTATCONIC_WHOLE, 0 ,0));
//        timeLine.randomKeysAndDurationsForVoice(2, timeLineKeysForVoice2, durations);

//        List<TimeLineKey> timeLineKeysForVoice3 = new ArrayList<>();
//        timeLineKeysForVoice3.add(new TimeLineKey(keys.C, Scale.CHROMATIC_SCALE, 0 ,0));
//        timeLineKeysForVoice3.add(new TimeLineKey(keys.C, Scale.OCTATCONIC_WHOLE, 0 ,0));
//        timeLine.randomKeysAndDurationsForVoice(3, timeLineKeysForVoice3, durations);


//		List<Contour> contouren = new ArrayList<>();
//		contouren.add(new Contour(0 ,DurationConstants.WHOLE, 1));
//		contouren.add(new Contour(DurationConstants.WHOLE ,2* DurationConstants.WHOLE, -1));
//		contouren.add(new Contour(2* DurationConstants.WHOLE ,3* DurationConstants.WHOLE, 1));
//		contouren.add(new Contour(3* DurationConstants.WHOLE ,4* DurationConstants.WHOLE, -1));
//		contouren.add(new Contour(4* DurationConstants.WHOLE ,5* DurationConstants.WHOLE, 1));
//		contouren.add(new Contour(5* DurationConstants.WHOLE ,6* DurationConstants.WHOLE, -1));
//		contouren.add(new Contour(6* DurationConstants.WHOLE ,7* DurationConstants.WHOLE, 1));
//		contouren.add(new Contour(7* DurationConstants.WHOLE ,8* DurationConstants.WHOLE, -1));
//		timeLine.addContourForVoice(contouren, voice3);

//		voiceConfiguration.put(voice5, fixedVoice);
//		voiceConfiguration.put(voice6, fixedVoice);
//		voiceConfiguration.put(voice7, harmonyVoice);

//		timeLine.repeatContourPattern(DurationConstants.HALF, voice3, new int[]{1,-1});
//		timeLine.repeatContourPattern(DurationConstants.HALF, voice4, new int[]{1,-1});
//		timeLine.repeatContourPattern(DurationConstants.HALF, voice7, new int[]{1,-1});

//		List<TimeLineKey> major = new ArrayList<>();
//		major.add(new TimeLineKey(keys.C, Scale.MAJOR_CHORD, start, DurationConstants.SIX_EIGHTS * 2));
//		major.add(new TimeLineKey(keys.G, Scale.MAJOR_CHORD,DurationConstants.SIX_EIGHTS * 2, end));
//		timeLine.addKeysForVoice(major, 0);
//
//        List<TimeLineKey> major1 = new ArrayList<>();
//        major1.add(new TimeLineKey(keys.E, Scale.DORIAN_SCALE, start, DurationConstants.HALF));
//        major1.add(new TimeLineKey(keys.C, Scale.LYDIAN_SCALE, DurationConstants.HALF, DurationConstants.WHOLE));
//        major1.add(new TimeLineKey(keys.G, Scale.DORIAN_SCALE, DurationConstants.WHOLE , DurationConstants.WHOLE + DurationConstants.HALF));
//        major1.add(new TimeLineKey(keys.Eflat, Scale.LYDIAN_SCALE, DurationConstants.WHOLE + DurationConstants.HALF, end));
//        timeLine.addKeysForVoice(major1, 1);

//        for (int i = 0; i < numberOfVoices; i++) {
//            timeLine.addTimeLineKey(i , keys.E, Scale.DORIAN_SCALE, DurationConstants.WHOLE);
//            timeLine.addTimeLineKey(i , keys.C, Scale.LYDIAN_SCALE, DurationConstants.WHOLE);
//            timeLine.addTimeLineKey(i , keys.G, Scale.DORIAN_SCALE, DurationConstants.WHOLE);
//            timeLine.addTimeLineKey(i , keys.Eflat, Scale.LYDIAN_SCALE, DurationConstants.WHOLE);
            //repeat
//            timeLine.addTimeLineKey(i , keys.E, Scale.DORIAN_SCALE, DurationConstants.HALF);
//            timeLine.addTimeLineKey(i , keys.C, Scale.LYDIAN_SCALE, DurationConstants.HALF);
//            timeLine.addTimeLineKey(i , keys.G, Scale.DORIAN_SCALE, DurationConstants.HALF);
//            timeLine.addTimeLineKey(i , keys.Eflat, Scale.LYDIAN_SCALE, DurationConstants.HALF);
//        }

//		List<TimeLineKey> major2 = new ArrayList<>();
//		major2.add(new TimeLineKey(keys.C, Scale.MAJOR_CHORD, start, DurationConstants.SIX_EIGHTS * 2));
//		major2.add(new TimeLineKey(keys.G, Scale.MAJOR_CHORD, DurationConstants.SIX_EIGHTS * 2, end));
//		timeLine.addKeysForVoice(major1, 2);
//
//		List<TimeLineKey> major3 = new ArrayList<>();
//		major3.add(new TimeLineKey(keys.C, Scale.MAJOR_SCALE, start, DurationConstants.SIX_EIGHTS * 2));
//		major3.add(new TimeLineKey(keys.C, Scale.MAJOR_SCALE, DurationConstants.SIX_EIGHTS * 2, end));
//		timeLine.addKeysForVoice(major3, 3);

		//polytonality
//		List<TimeLineKey> minor = new ArrayList<>();
//		minor.add(new TimeLineKey(C, Scale.MAJOR_SCALE, start, end));
//		timeLine.addKeysForVoice(minor, 1);

		//bartok
//		List<TimeLineKey> bartok = new ArrayList<>();
//		bartok.add(new TimeLineKey(keys.C, Scale.Z, start, end));
//		timeLine.addKeysForVoice(bartok, 0);
//
//		List<TimeLineKey> bartok1 = new ArrayList<>();
//		bartok1.add(new TimeLineKey(keys.Fsharp, Scale.WHOLE_TONE_SCALE_1, start, end));
//		timeLine.addKeysForVoice(bartok1, 1);
//
//		List<TimeLineKey> bartok2 = new ArrayList<>();
//		bartok2.add(new TimeLineKey(keys.Fsharp, Scale.WHOLE_TONE_SCALE_0, start, end));
//		timeLine.addKeysForVoice(bartok2, 2);
//
//		List<TimeLineKey> bartok3 = new ArrayList<>();
//		bartok3.add(new TimeLineKey(keys.Fsharp, Scale.WHOLE_TONE_SCALE_0, start, end));
//		timeLine.addKeysForVoice(bartok3, 3);


		//composition - A string trio
//		List<TimeLineKey> webern1 = new ArrayList<>();
//		webern1.add(new TimeLineKey(C, Scale.WEBERN_TRICHORD_1, start, DurationConstants.HALF + DurationConstants.QUARTER + DurationConstants.EIGHT));
//		webern1.add(new TimeLineKey(C, Scale.WEBERN_TRICHORD_2, DurationConstants.HALF + DurationConstants.QUARTER + DurationConstants.EIGHT, end));
////		webern1.add(new TimeLineKey(C, Scale.WEBERN_TRICHORD_3, 2 * DurationConstants.WHOLE, end));
//		timeLine.addKeysForVoice(webern1, 0);
//
//		List<TimeLineKey> webern2 = new ArrayList<>();
//		webern2.add(new TimeLineKey(C, Scale.WEBERN_TRICHORD_2, start,DurationConstants.HALF + DurationConstants.QUARTER + DurationConstants.EIGHT));
//		webern2.add(new TimeLineKey(C, Scale.WEBERN_TRICHORD_3, DurationConstants.HALF + DurationConstants.QUARTER + DurationConstants.EIGHT, end));
////		webern2.add(new TimeLineKey(C, Scale.WEBERN_TRICHORD_1, 2 * DurationConstants.WHOLE, end));
//		timeLine.addKeysForVoice(webern2, 1);
//
//		List<TimeLineKey> webern3 = new ArrayList<>();
//		webern3.add(new TimeLineKey(C, Scale.WEBERN_TRICHORD_3, start, DurationConstants.HALF + DurationConstants.QUARTER + DurationConstants.EIGHT));
//		webern3.add(new TimeLineKey(C, Scale.WEBERN_TRICHORD_1, DurationConstants.HALF + DurationConstants.QUARTER + DurationConstants.EIGHT, end));
////		webern3.add(new TimeLineKey(C, Scale.WEBERN_TRICHORD_1, 2 * DurationConstants.WHOLE, end));
//		timeLine.addKeysForVoice(webern3, 2);

//		harmonicObjective.setDissonance(tonalDissonance::getDissonance);
//		harmonicObjective.setDissonance(symmetryDissonance::getDissonance);
		harmonicObjective.setDissonance(subSets_6_32::getDissonance);
//		harmonicObjective.setDissonance(additiveDissonance::getDissonance);
		harmonicResolutionObjective.setDissonantResolution(dissonantResolutionImpl::isDissonant);

	}

	private void setTimeconfig(){
		if (numerator == 2 && denominator == 4) {
			timeConfig = time24;
		} else if (numerator == 4 && denominator == 4) {
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
		} else if (numerator == 5 && denominator == 4) {
            timeConfig = time54;
        }
	}
	
	public TimeConfig getTimeConfig(){
		return timeConfig;
	}
	
	protected void inTempo(int tempo) {
		musicProperties.setTempo(tempo);
	}

	protected void composeInKey(Key key) {
		musicProperties.setKeySignature(key.getKeySignature());
		musicProperties.setKey(key);
	}
	
	public int getStart() {
		return start;
	}
	
	public int getEnd() {
		return end;
	}

	public void setHarmonizeMelody(HarmonizeMelody harmonizeMelody) {
		this.harmonizeMelody = harmonizeMelody;
	}

	public void setHarmonizeVoice(int harmonizeVoice) {
		this.harmonizeVoice = harmonizeVoice;
	}

	protected List<Integer> getContour(List<Note> notes){
		List<Integer> contour = new ArrayList<>();
		int size = notes.size() - 1;
		for (int i = 0; i < size; i++) {
			Note note = notes.get(i);
			Note nextNote = notes.get(i + 1);
			int difference = nextNote.getPitch() - note.getPitch();
			if(difference < 0){
				contour.add(-1);
			}else{
				contour.add(1);
			}
		}
		return contour;
	}

	public List<MelodyBlock> harmonize(){
		List<MelodyBlock> melodyBlocks = new ArrayList<>();
		//harmonization
        Map<String, List<Note>> notesPerInstrument = harmonizeMelody.getNotesToHarmonize();
		Timbre timbreConfig = this.timbreConfig.getTimbreConfigForVoice(harmonizeVoice);
        notesPerInstrument.entrySet().stream().flatMap(entry -> entry.getValue().stream()).forEach(n -> {
            n.setVoice(harmonizeVoice);
            if(n.getDynamic() == null){
                n.setDynamic(timbreConfig.getDynamic());
                n.setDynamicLevel(timbreConfig.getDynamic().getLevel());
            }
            if(n.getTechnical() == null){
                n.setTechnical(timbreConfig.getTechnical());
            }
        });
        List<Note> notes = notesPerInstrument.entrySet().stream()
                            .flatMap(entry -> entry.getValue().stream())
                            .filter(n -> Character.getNumericValue(n.getInstrument().charAt(1)) == harmonizeVoice)
                            .collect(toList());

		InstrumentMapping instrumentHarmonize = instrumentConfig.getInstrumentMappingForVoice(harmonizeVoice);
		CpMelody melody = new CpMelody(notes, harmonizeVoice, start, end);
		List<Integer> contour = getContour(notes);
		melody.setContour(contour);
		MelodyBlock melodyBlockHarmonize = new MelodyBlock(6, harmonizeVoice);
		melodyBlockHarmonize.addMelodyBlock(melody);
		melodyBlockHarmonize.setMutable(false);
//		melodyBlockHarmonize.I();

		melodyBlocks.add(melodyBlockHarmonize);
		int size = instrumentConfig.getSize();
		for (int i = 0; i < size; i++) {
			if (i != harmonizeVoice) {
				Instrument instrument = instrumentConfig.getInstrumentForVoice(i);
				MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlockConfig(i, instrument.pickRandomOctaveFromRange());
				melodyBlocks.add(melodyBlock);
			}
		}

		return melodyBlocks;
	}

}
