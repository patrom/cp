package cp.genre;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import cp.combination.NoteCombination;
import cp.combination.RhythmCombination;
import cp.combination.even.FourNoteEven;
import cp.combination.even.OneNoteEven;
import cp.combination.even.ThreeNoteEven;
import cp.combination.even.TwoNoteEven;
import cp.combination.uneven.FiveNoteSexTuplet;
import cp.combination.uneven.FourNoteSexTuplet;
import cp.combination.uneven.OneNoteUneven;
import cp.combination.uneven.SixNoteSexTuplet;
import cp.combination.uneven.ThreeNoteSexTuplet;
import cp.combination.uneven.ThreeNoteUneven;
import cp.combination.uneven.TwoNoteUneven;
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
import cp.objective.harmony.HarmonicObjective;
import cp.out.instrument.Instrument;
import cp.out.instrument.keyboard.Piano;
import cp.out.instrument.plucked.Guitar;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.instrument.strings.Cello;
import cp.out.instrument.strings.Viola;
import cp.out.instrument.strings.ViolinsI;
import cp.out.instrument.woodwinds.Oboe;
import cp.out.orchestration.InstrumentName;
import cp.out.orchestration.quality.Brilliant;
import cp.out.orchestration.quality.Pleasant;
import cp.out.print.note.Key;

public abstract class Composition {
	
	@Autowired
	protected OneNoteEven oneNoteEven;
	@Autowired
	protected TwoNoteEven twoNoteEven;
	@Autowired
	protected ThreeNoteEven threeNoteEven;
	@Autowired
	protected FourNoteEven fourNoteEven;
	
	@Autowired
	protected ThreeNoteUneven threeNoteUneven;
	@Autowired
	protected TwoNoteUneven twoNoteUneven;
	@Autowired
	protected OneNoteUneven oneNoteUneven;
	@Autowired
	protected ThreeNoteSexTuplet threeNoteSexTuplet;
	@Autowired
	protected FourNoteSexTuplet fourNoteSexTuplet;
	@Autowired
	protected FiveNoteSexTuplet fiveNoteSexTuplet;
	@Autowired
	protected SixNoteSexTuplet sixNoteSexTuplet;

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
	protected NoteCombination noteCombination;

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
	
	protected List<Integer> beats = new ArrayList<>();
	protected List<Integer> beats2X = new ArrayList<>();
	protected List<Integer> beatsAll = new ArrayList<>();
	
	protected int offset;
	
	@PostConstruct
	public void init(){
		composeInEvenMeter(4,4);
//		composeInUnevenMeter(3,4);
		composeInKey(C);
		inTempo(70);
		
//		instruments.add(new ViolinsI());
////		Instrument instrument1 = pleasant.getInstrument(InstrumentName.CLARINET.getName());
//		instruments.add(new Viola());
//		instruments.add(new Cello());
		
		instruments.add(new Guitar(new InstrumentRegister(40, 55)));
		instruments.add(new Guitar(new InstrumentRegister(50, 64)));
		instruments.add(new Guitar(new InstrumentRegister(67, 71)));
		
		List<TimeLineKey> keys = new ArrayList<>();
		keys.add(new TimeLineKey(C, Scale.MAJOR_SCALE, start, end));
//		keys.add(new TimeLineKey(E, Scale.HARMONIC_MINOR_SCALE, 72, 108));
//		keys.add(new TimeLineKey(D, Scale.MAJOR_SCALE, 108, 144));
//		keys.add(new TimeLineKey(G, Scale.MAJOR_SCALE, 144, end));
//		keys.add(new TimeLineKey(C, instrument1.filterScale(Scale.HARMONIC_MINOR_SCALE), 48, 192));//match length
//		keys.add(new TimeLineKey(A, Scale.HARMONIC_MINOR_SCALE, 48, 96));
//		keys.add(new TimeLineKey(E, Scale.HARMONIC_MINOR_SCALE, 96, 144));
//		keys.add(new TimeLineKey(G, Scale.MAJOR_SCALE, 144, 192));
		int instrumentSize = instruments.size();
		for (int i = 0; i < instrumentSize; i++) {
			timeLine.addKeysForVoice(keys, i);
		}
//		timeLine.addKeysForVoice(keys, 0);
//		timeLine.addKeysForVoice(keys, 1);
//		timeLine.addKeysForVoice(keys, 2);//match instruments
		
		replaceMelody.setPitchClassGenerator(passingPitchClasses::updatePitchClasses);
		melodyGenerator.setPitchClassGenerator(passingPitchClasses::updatePitchClasses);
		harmonicObjective.setDissonance(intervalAndTriads::getDissonance);
	}

	protected void composeInEvenMeter(int numerator, int denominator){
		offset = 48;//canon,...
		beats.add(12);
		beats2X.add(24);
		beatsAll.add(12);
		beatsAll.add(24);
		musicProperties.setNumerator(numerator);
		musicProperties.setDenominator(denominator);
	}
	
	protected void composeInUnevenMeter(int numerator, int denominator){
		offset = 36;//canon,...
		beats.add(18);
		beats2X.add(36);
		beatsAll.add(18);
		beatsAll.add(36);
		musicProperties.setNumerator(numerator);
		musicProperties.setDenominator(denominator);
		int[] distance = {3,6,9,12,15,18,20,21,22,24,26,27,28,30,32};//minimumRhythmicValue = 12 -  3/4
		musicProperties.setDistance(distance);
	}
	
	protected void inTempo(int tempo) {
		musicProperties.setTempo(tempo);
	}

	protected void composeInKey(Key key) {
		musicProperties.setKeySignature(key.getKeySignature());
		musicProperties.setKey(key);
	}
	
	protected List<RhythmCombination> evenBeat(){
		List<RhythmCombination> rhythmCombinations = new ArrayList<>();
		rhythmCombinations.add(oneNoteEven::pos1);
//		rhythmCombinations.add(oneNoteEven::pos2);
//		rhythmCombinations.add(oneNoteEven::pos3);
//		rhythmCombinations.add(oneNoteEven::pos4);
//		
//		rhythmCombinations.add(twoNoteEven::pos12);
		rhythmCombinations.add(twoNoteEven::pos13);
		rhythmCombinations.add(twoNoteEven::pos14);
//		rhythmCombinations.add(twoNoteEven::pos34);
//		rhythmCombinations.add(twoNoteEven::pos23);
//		rhythmCombinations.add(twoNoteEven::pos24);
		
//		rhythmCombinations.add(threeNoteEven::pos123);
		rhythmCombinations.add(threeNoteEven::pos134);
//		rhythmCombinations.add(threeNoteEven::pos124);
//		rhythmCombinations.add(threeNoteEven::pos234);
		
//		rhythmCombinations.add(fourNoteEven::pos1234);
//		
//		rhythmCombinations.add(threeNoteUneven::pos123);
		return rhythmCombinations;
	}
	
	protected List<RhythmCombination> fixedBeat(){
		List<RhythmCombination> rhythmCombinations = new ArrayList<>();
//		rhythmCombinations.add(oneNoteEven::pos1);
//		rhythmCombinations.add(oneNoteEven::pos2);
//		rhythmCombinations.add(oneNoteEven::pos3);
//		rhythmCombinations.add(oneNoteEven::pos4);
//		
		rhythmCombinations.add(twoNoteEven::pos12);
		rhythmCombinations.add(twoNoteEven::pos13);
//		rhythmCombinations.add(twoNoteEven::pos14);
//		rhythmCombinations.add(twoNoteEven::pos34);
//		rhythmCombinations.add(twoNoteEven::pos23);
//		rhythmCombinations.add(twoNoteEven::pos24);
		
//		rhythmCombinations.add(threeNoteEven::pos123);
//		rhythmCombinations.add(threeNoteEven::pos134);
//		rhythmCombinations.add(threeNoteEven::pos124);
//		rhythmCombinations.add(threeNoteEven::pos234);
		
//		rhythmCombinations.add(fourNoteEven::pos1234);
//		
//		rhythmCombinations.add(threeNoteUneven::pos123);
		return rhythmCombinations;
	}

}
