package cp.composition;

import cp.composition.beat.BeatGroupFactory;
import cp.composition.timesignature.TimeConfig;
import cp.generator.MelodyGenerator;
import cp.generator.MusicProperties;
import cp.generator.pitchclass.PassingPitchClasses;
import cp.generator.pitchclass.RandomPitchClasses;
import cp.model.TimeLine;
import cp.model.TimeLineKey;
import cp.model.dissonance.IntervalAndTriads;
import cp.model.dissonance.IntervalDissonance;
import cp.model.dissonance.SetClassDissonance;
import cp.model.dissonance.TonalDissonance;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import cp.nsga.operator.mutation.melody.ReplaceMelody;
import cp.objective.harmony.DissonantResolutionImpl;
import cp.objective.harmony.HarmonicObjective;
import cp.objective.harmony.HarmonicResolutionObjective;
import cp.objective.meter.MeterObjective;
import cp.out.instrument.Ensemble;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentCombination;
import cp.out.orchestration.quality.Brilliant;
import cp.out.orchestration.quality.Mellow;
import cp.out.orchestration.quality.Pleasant;
import cp.out.orchestration.quality.Rich;
import cp.out.print.note.Key;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

public abstract class Composition {
	
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
	private HarmonicResolutionObjective harmonicResolutionObjective;
	@Autowired
	private DissonantResolutionImpl dissonantResolutionImpl;
	@Autowired
	protected HarmonicObjective harmonicObjective;
	@Autowired
	protected IntervalDissonance intervalDissonance;
	@Autowired
	protected IntervalAndTriads intervalAndTriads;
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
	protected Key Bflat;
	@Autowired
	protected Key B;
	
	@Autowired
	protected Pleasant pleasant;
	@Autowired
	protected Mellow mellow;
	@Autowired
	protected Rich rich;
	@Autowired
	protected Brilliant brilliant;
	
	private InstrumentCombination instrumentCombination;
	protected List<Instrument> instruments = new ArrayList<>();
	@Autowired
	private Ensemble ensemble;
	
	protected final int start = 0;
	protected final int end = 4 * DurationConstants.WHOLE;
	
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

	@PostConstruct
	public void init(){
		composeInKey(C);
		inTempo(90);
		musicProperties.setNumerator(numerator);
		musicProperties.setDenominator(denominator);
		meterObjective.setComposition(this);
		
//		instruments = Choral.getSATB();
//		instruments = ensemble.getFluteClarinetBassoonGreen();
//		instruments = ensemble.getStrings(mellow);
//		instruments = ensemble.getStringDuo();
		instruments = ensemble.getStringTrio();
//		instruments = ensemble.getPiano(3);

		setTimeconfig();
		List<TimeLineKey> keys = new ArrayList<>();
		keys.add(new TimeLineKey(C, Scale.MAJOR_SCALE, start, DurationConstants.WHOLE));
		keys.add(new TimeLineKey(E, Scale.MAJOR_SCALE, DurationConstants.WHOLE, end + DurationConstants.WHOLE));
//		keys.add(new TimeLineKey(D, Scale.MAJOR_SCALE, 108, 144));
//		keys.add(new TimeLineKey(G, Scale.MAJOR_SCALE, 144, end));
//		keys.add(new TimeLineKey(C, clarinet.filterScale(Scale.HARMONIC_MINOR_SCALE), 48, 192));//match length
//		keys.add(new TimeLineKey(A, Scale.HARMONIC_MINOR_SCALE, 48, 96));
//		keys.add(new TimeLineKey(E, Scale.HARMONIC_MINOR_SCALE, 96, 144));
//		keys.add(new TimeLineKey(G, Scale.MAJOR_SCALE, 144, 192));
		int instrumentSize = instruments.size();
		for (int i = 0; i < instrumentSize; i++) {
			timeLine.addKeysForVoice(keys, i);
		}
		timeLine.addKeysForVoice(keys, 0);
		
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
		
		melodyGenerator.setCompostion(this);
		melodyGenerator.setBeatGroupStrategy(timeConfig::getAllBeats);
		replaceMelody.setPitchClassGenerator(passingPitchClasses::updatePitchClasses);
		replaceMelody.setComposition(this);
		melodyGenerator.setPitchClassGenerator(passingPitchClasses::updatePitchClasses);
		harmonicObjective.setDissonance(tonalDissonance::getDissonance);
		harmonicResolutionObjective.setDissonantResolution(dissonantResolutionImpl::isDissonant);
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
	
}
