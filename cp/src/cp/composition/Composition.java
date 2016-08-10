package cp.composition;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import cp.combination.NoteCombination;
import cp.composition.beat.BeatGroup;
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
import cp.model.note.Scale;
import cp.nsga.operator.mutation.melody.ReplaceMelody;
import cp.objective.Objective;
import cp.objective.harmony.HarmonicObjective;
import cp.objective.meter.MeterObjective;
import cp.out.instrument.Instrument;
import cp.out.instrument.plucked.Guitar;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.instrument.woodwinds.Bassoon;
import cp.out.orchestration.InstrumentName;
import cp.out.orchestration.quality.Brilliant;
import cp.out.orchestration.quality.Pleasant;
import cp.out.print.note.Key;

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
	protected HarmonicObjective harmonicObjective;
	@Autowired
	protected IntervalDissonance intervalDissonance;
	@Autowired
	protected IntervalAndTriads intervalAndTriads;
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
	protected Brilliant brilliant;
	
	protected List<Instrument> instruments = new ArrayList<Instrument>();
	
	protected int start = 0;
	protected int end = 196;
	
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
	
	@PostConstruct
	public void init(){
		composeInKey(C);
		inTempo(90);
		musicProperties.setNumerator(numerator);
		musicProperties.setDenominator(denominator);
		meterObjective.setComposition(this);
//		instruments.add(new ViolinsI());
		Instrument bassoon = pleasant.getBasicInstrument(InstrumentName.BASSOON.getName());
		Instrument clarinet = pleasant.getBasicInstrument(InstrumentName.CLARINET.getName());
		Instrument flute = pleasant.getBasicInstrument(InstrumentName.FLUTE.getName());

//		instruments.add(new Viola());
//		instruments.add(new Cello());
		
//		instruments.add(new Guitar(new InstrumentRegister(40, 55)));
//		instruments.add(new Guitar(new InstrumentRegister(50, 67)));
//		instruments.add(new Guitar(new InstrumentRegister(67, 76)));
		
		instruments.add(bassoon);
		instruments.add(clarinet);
		instruments.add(flute);
		setTimeconfig();
		List<TimeLineKey> keys = new ArrayList<>();
		keys.add(new TimeLineKey(C, Scale.MAJOR_SCALE, start, end));
//		keys.add(new TimeLineKey(E, Scale.HARMONIC_MINOR_SCALE, start, end));
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
//		timeLine.addKeysForVoice(keys, 0);
		
//		List<TimeLineKey> minor = new ArrayList<>();
//		minor.add(new TimeLineKey(C, Scale.MAJOR_SCALE, start, end));
//		timeLine.addKeysForVoice(minor, 1);
		
		melodyGenerator.setCompostion(this);
		melodyGenerator.setBeatGroupStrategy(timeConfig::getAllBeats);
		replaceMelody.setPitchClassGenerator(passingPitchClasses::updatePitchClasses);
		replaceMelody.setComposition(this);
		melodyGenerator.setPitchClassGenerator(passingPitchClasses::updatePitchClasses);
		harmonicObjective.setDissonance(intervalAndTriads::getDissonance);
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
	
}
