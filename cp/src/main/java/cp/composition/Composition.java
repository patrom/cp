package cp.composition;

import cp.composition.beat.BeatGroupFactory;
import cp.composition.timesignature.TimeConfig;
import cp.composition.voice.*;
import cp.generator.MelodyGenerator;
import cp.generator.MusicProperties;
import cp.generator.dependant.DependantGenerator;
import cp.generator.pitchclass.*;
import cp.model.TimeLine;
import cp.model.TimeLineKey;
import cp.model.contour.Contour;
import cp.model.dissonance.*;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import cp.nsga.operator.mutation.melody.ReplaceMelody;
import cp.nsga.operator.relation.RelationConfig;
import cp.objective.harmony.DissonantResolutionImpl;
import cp.objective.harmony.HarmonicObjective;
import cp.objective.harmony.HarmonicResolutionObjective;
import cp.objective.meter.MeterObjective;
import cp.out.instrument.Ensemble;
import cp.out.instrument.Instrument;
import cp.out.orchestration.quality.BrilliantWhite;
import cp.out.orchestration.quality.MellowPurple;
import cp.out.orchestration.quality.PleasantGreen;
import cp.out.orchestration.quality.RichBlue;
import cp.out.play.InstrumentConfig;
import cp.out.play.InstrumentMapping;
import cp.out.print.note.Key;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.util.*;

public abstract class Composition {

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


	@Autowired
	protected ReplaceMelody replaceMelody;

	@Autowired
	protected MusicProperties musicProperties;
	@Autowired
	protected MelodyGenerator melodyGenerator;

	@Autowired
	protected RandomPitchClasses randomPitchClasses;
	@Autowired
	protected PassingPitchClasses passingPitchClasses;
	@Autowired
	protected RestPitchClasses restPitchClasses;
	@Autowired
	protected RepeatingPitchClasses repeatingPitchClasses;
	@Autowired
	private TwelveTonePitchClasses twelveTonePitchClasses;

	private List<PitchClassGenerator> pitchClassGenerators = new ArrayList<>();

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
	protected TimeLine timeLine;
	@Autowired
	private MeterObjective meterObjective;

	@Autowired
	protected Key C;
	@Autowired
	protected Key Csharp;
	@Autowired
	protected Key D;
	@Autowired
	protected Key Dsharp;
	@Autowired
	protected Key Eflat;
	@Autowired
	protected Key E;
	@Autowired
	protected Key F;
	@Autowired
	protected Key Fsharp;
	@Autowired
	protected Key G;
	@Autowired
	protected Key Gsharp;
	@Autowired
	protected Key A;
	@Autowired
	protected Key Aflat;
	@Autowired
	protected Key Bflat;
	@Autowired
	protected Key B;
	
	@Autowired
	protected PleasantGreen pleasantGreen;
	@Autowired
	protected MellowPurple mellowPurple;
	@Autowired
	protected RichBlue richBlue;
	@Autowired
	protected BrilliantWhite brilliantWhite;
	
//	private InstrumentCombination instrumentCombination;
//	protected List<Instrument> instruments = new ArrayList<>();
	@Autowired
	private Ensemble ensemble;
	
	protected final int start = 0;
	protected final int end = 6 * DurationConstants.WHOLE;
	
	private TimeConfig timeConfig;
	
	@Autowired
	@Qualifier(value="time44")
	protected TimeConfig time44;
	@Autowired
	@Qualifier(value="time34")
	protected TimeConfig time34;
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

	protected HarmonizeMelody harmonizeMelody;
	protected int harmonizeVoice;
	@Autowired
	protected RelationConfig operatorConfig;
	@Autowired
	protected InstrumentConfig instrumentConfig;

	protected Map<Integer, VoiceConfig> voiceConfiguration = new TreeMap<>();
	@Autowired
	protected DependantGenerator dependantGenerator;

	@PostConstruct
	public void init(){
		composeInKey(G);
		inTempo(100);
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
		timeLine.setEnd(end);
		//time line
		List<TimeLineKey> timeLineKeys = new ArrayList<>();
//		timeLineKeys.add(new TimeLineKey(A, Scale.HARMONIC_MINOR_SCALE, 0 ,0));
		timeLineKeys.add(new TimeLineKey(G, Scale.MAJOR_SCALE, 0 ,0));
		timeLineKeys.add(new TimeLineKey(E, Scale.HARMONIC_MINOR_SCALE, 0 ,0));
//		timeLineKeys.add(new TimeLineKey(B, Scale.MINOR_CHORD, 0 ,0));
//		timeLineKeys.add(new TimeLineKey(A, Scale.MAJOR_CHORD, 0 ,0));
//		timeLineKeys.add(new TimeLineKey(C, Scale.MAJOR_CHORD, 0 ,0));
		List<Integer> durations = new ArrayList<>();
		durations.add(DurationConstants.QUARTER);
		durations.add(DurationConstants.WHOLE);
		durations.add(DurationConstants.HALF);
//		timeLine.randomKeysAndDurations(timeLineKeys, durations);
		timeLine.randomKeys(timeLineKeys, DurationConstants.WHOLE, DurationConstants.WHOLE, 4 * DurationConstants.WHOLE);

		timeLine.addKeysForVoice(Collections.singletonList(new TimeLineKey(G, Scale.LYDIAN_SCALE, 0 ,end)),4);

		List<Contour> contouren = new ArrayList<>();
		contouren.add(new Contour(0 ,DurationConstants.WHOLE, 1));
		contouren.add(new Contour(DurationConstants.WHOLE ,2* DurationConstants.WHOLE, -1));
		contouren.add(new Contour(2* DurationConstants.WHOLE ,3* DurationConstants.WHOLE, 1));
		contouren.add(new Contour(3* DurationConstants.WHOLE ,4* DurationConstants.WHOLE, -1));
		contouren.add(new Contour(4* DurationConstants.WHOLE ,5* DurationConstants.WHOLE, 1));
		contouren.add(new Contour(5* DurationConstants.WHOLE ,6* DurationConstants.WHOLE, -1));
		contouren.add(new Contour(6* DurationConstants.WHOLE ,7* DurationConstants.WHOLE, 1));
		contouren.add(new Contour(7* DurationConstants.WHOLE ,8* DurationConstants.WHOLE, -1));
		timeLine.addContourForVoice(contouren, voice5);

		voiceConfiguration.put(voice5, fixedVoice);
		voiceConfiguration.put(voice6, fixedVoice);
		voiceConfiguration.put(voice7, harmonyVoice);

		timeLine.repeatContourPattern(DurationConstants.HALF, voice5, new int[]{1,-1});
		timeLine.repeatContourPattern(DurationConstants.HALF, voice6, new int[]{1,-1});
		timeLine.repeatContourPattern(DurationConstants.HALF, voice7, new int[]{1,-1});

//		List<TimeLineKey> major = new ArrayList<>();
//		major.add(new TimeLineKey(C, Scale.MAJOR_SCALE, start, DurationConstants.WHOLE));
//		major.add(new TimeLineKey(C, Scale.MAJOR_SCALE, DurationConstants.WHOLE, end));
//		timeLine.addKeysForVoice(major, 0);
//
//		List<TimeLineKey> major1 = new ArrayList<>();
//		major1.add(new TimeLineKey(C, Scale.MAJOR_CHORD, start, DurationConstants.WHOLE));
//		major1.add(new TimeLineKey(G, Scale.MAJOR_CHORD, DurationConstants.WHOLE, end));
//		timeLine.addKeysForVoice(major1, 1);
//
//		List<TimeLineKey> major2 = new ArrayList<>();
//		major2.add(new TimeLineKey(C, Scale.MAJOR_CHORD, start, DurationConstants.WHOLE));
//		major2.add(new TimeLineKey(G, Scale.MAJOR_CHORD, DurationConstants.WHOLE, end));
//		timeLine.addKeysForVoice(major1, 2);
//
//		List<TimeLineKey> major3 = new ArrayList<>();
//		major3.add(new TimeLineKey(C, Scale.MAJOR_SCALE, start, DurationConstants.WHOLE));
//		major3.add(new TimeLineKey(C, Scale.MAJOR_SCALE, DurationConstants.WHOLE, end));
//		timeLine.addKeysForVoice(major3, 3);

		//polytonality
//		List<TimeLineKey> minor = new ArrayList<>();
//		minor.add(new TimeLineKey(C, Scale.MAJOR_SCALE, start, end));
//		timeLine.addKeysForVoice(minor, 1);

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
		pitchClassGenerators.add(repeatingPitchClasses::updatePitchClasses);
		pitchClassGenerators.add(randomPitchClasses::randomPitchClasses);
		pitchClassGenerators.add(passingPitchClasses::updatePitchClasses);
//		pitchClassGenerators.add(restPitchClasses::updatePitchClasses);
//		pitchClassGenerators.add(twelveTonePitchClasses::updatePitchClasses);

		melodyGenerator.setBeatGroupStrategy(timeConfig::getAllBeats);
		harmonicObjective.setDissonance(dyadTriadsTetraAndPentaChordal::getDissonance);
		harmonicResolutionObjective.setDissonantResolution(dissonantResolutionImpl::isDissonant);

	}

	@Autowired
	protected MelodyVoice melodyVoice;
	@Autowired
	protected HomophonicVoice homophonicVoice;
	@Autowired
	protected BassVoice bassVoice;
	@Autowired
	protected FixedVoice fixedVoice;
	@Autowired
	protected DoubleTimeVoice doubleTimeVoice;
	@Autowired
	protected timeVoice timeVoice;
	@Autowired
	protected HarmonyVoice harmonyVoice;

	public VoiceConfig getVoiceConfiguration(int voice){
		return voiceConfiguration.get(voice);
	}

	public PitchClassGenerator getRandomPitchClassGenerator(int voice) {
		return voiceConfiguration.get(voice).getRandomPitchClassGenerator();
	}
	
	private void setTimeconfig(){
		if (numerator == 4 && denominator == 4) {
			timeConfig = time44;
		} else if (numerator == 3 && denominator == 4) {
			timeConfig = time34;
		} else if (numerator == 6 && denominator == 8) {
			timeConfig = time68;
		} else if (numerator == 5 && denominator == 8) {
			timeConfig = time58;
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
		List<Note> notes = harmonizeMelody.getNotesToHarmonize();

		InstrumentMapping instrumentHarmonize = instrumentConfig.getInstrumentMappingForVoice(harmonizeVoice);
		CpMelody melody = new CpMelody(notes, harmonizeVoice, start, end);
		List<Integer> contour = getContour(notes);
		melody.setContour(contour);
		MelodyBlock melodyBlockHarmonize = new MelodyBlock(6, harmonizeVoice);
		melodyBlockHarmonize.addMelodyBlock(melody);
		melodyBlockHarmonize.setMutable(false);
		melodyBlockHarmonize.setInstrument(instrumentHarmonize.getInstrument());
//		melodyBlockHarmonize.I();

		melodyBlocks.add(melodyBlockHarmonize);
		int size = instrumentConfig.getSize();
		for (int i = 0; i < size; i++) {
			if (i != harmonizeVoice) {
				Instrument instrument = instrumentConfig.getInstrumentForVoice(i);
				MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlockConfig(i, instrument.pickRandomOctaveFromRange());
				melodyBlock.setInstrument(instrument);
				melodyBlocks.add(melodyBlock);
			}
		}

		return melodyBlocks;
	}


}
