package cp;

import static cp.model.note.NoteBuilder.note;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Import;

import cp.generator.MelodyGenerator;
import cp.generator.MusicProperties;
import cp.generator.pitchclass.PassingPitchClasses;
import cp.generator.pitchclass.RandomPitchClasses;
import cp.model.Motive;
import cp.model.TimeLine;
import cp.model.TimeLineKey;
import cp.model.dissonance.IntervalAndTriads;
import cp.model.dissonance.IntervalDissonance;
import cp.model.dissonance.TonalDissonance;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.melody.OperatorType;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.nsga.MusicSolutionType;
import cp.nsga.operator.mutation.melody.ArticulationMutation;
import cp.nsga.operator.mutation.melody.OneNoteMutation;
import cp.nsga.operator.mutation.melody.ReplaceMelody;
import cp.objective.harmony.HarmonicObjective;
import cp.out.instrument.Instrument;
import cp.out.instrument.Piano;
import cp.out.instrument.brass.FrenchHorn;
import cp.out.instrument.register.InstrumentRegister;
import cp.out.instrument.register.ViolaPleasant;
import cp.out.instrument.strings.CelloSolo;
import cp.out.instrument.strings.ViolaSolo;
import cp.out.instrument.strings.ViolinSolo;
import cp.out.instrument.woodwinds.Clarinet;
import cp.out.instrument.woodwinds.Flute;
import cp.out.print.Display;
import cp.out.print.note.Key;
import jmetal.core.Algorithm;
import jmetal.core.Operator;
import jmetal.core.SolutionSet;
import jmetal.operators.selection.SelectionFactory;
import jmetal.util.JMException;

@Import({DefaultConfig.class, VariationConfig.class})
public class CpApplication extends JFrame implements CommandLineRunner{
	
	private static Logger LOGGER = LoggerFactory.getLogger(CpApplication.class.getName());
	
	@Autowired
	private Key C;
	@Autowired
	private Key Csharp;
	@Autowired
	private Key D;
	@Autowired
	private Key Dsharp;
	@Autowired
	private Key Eflat;
	@Autowired
	private Key E;
	@Autowired
	private Key F;
	@Autowired
	private Key Fsharp;
	@Autowired
	private Key G;
	@Autowired
	private Key Gsharp;
	@Autowired
	private Key A;
	@Autowired
	private Key Bflat;
	@Autowired
	private Key B;
	
	@Autowired
	private MusicSolutionType solutionType;
	@Autowired 
	private Operator crossover;
	@Autowired
	private OneNoteMutation oneNoteMutation;
	@Autowired
	private ArticulationMutation articulationMutation;
	@Autowired
	private ReplaceMelody replaceMelody;
	@Autowired
	private Algorithm algorithm;
	@Autowired
	private Display display;
	@Autowired
	private MusicProperties musicProperties;
	@Autowired
	private HashMap<String, Object> parameters;
	@Autowired
	private MelodyGenerator melodyGenerator;

	@Autowired
	private RandomPitchClasses randomPitchClasses;
	@Autowired
	private PassingPitchClasses passingPitchClasses;
	
	@Autowired
	private HarmonicObjective harmonicObjective;
	@Autowired
	private IntervalDissonance intervalDissonance;
	@Autowired
	private IntervalAndTriads intervalAndTriads;
	@Autowired
	private TimeLine timeLine;
	
	public static AtomicInteger COUNTER = new AtomicInteger();
	
	public static void main(final String[] args) throws IOException {
		clean();
		SpringApplication app = new SpringApplication(CpApplication.class);
	    app.setBannerMode(Mode.OFF);
	    app.run(args);
	}
	
	@Override
	public void run(String... arg0) throws Exception {
		musicProperties.setOutputCountRun(2);
		composeInMeter(4,4);
		List<TimeLineKey> keys = new ArrayList<>();
		keys.add(new TimeLineKey(C, Scale.HARMONIC_MINOR_SCALE, 0, 192));
//		keys.add(new TimeLineKey(C, Scale.MAJOR_SCALE, 24, 48));
//		keys.add(new TimeLineKey(A, Scale.HARMONIC_MINOR_SCALE, 48, 96));
//		keys.add(new TimeLineKey(E, Scale.HARMONIC_MINOR_SCALE, 96, 144));
//		keys.add(new TimeLineKey(G, Scale.MAJOR_SCALE, 144, 192));
		timeLine.setKeys(keys);
		composeInKey(C);
		inTempo(70);
		replaceMelody.setPitchClassGenerator(passingPitchClasses::updatePitchClasses);
		melodyGenerator.setPitchClassGenerator(passingPitchClasses::updatePitchClasses);
		harmonicObjective.setDissonance(intervalAndTriads::getDissonance);
		
		for (int i = 0; i < 10; i++) {
			LOGGER.info("RUN: " + i + " START");		
			compose();
		    LOGGER.info("RUN: " + i + " END");
		}
	}

	private void compose() throws Exception {
		//harmonization
//		Instrument piano = new Piano(0, 3);
//		List<Note> notes = new ArrayList<>();
//		notes.add(note().pos(6).pc(2).len(3).build());
//		notes.add(note().pos(9).pc(9).len(3).build());
//		notes.add(note().pos(12).pc(6).len(3).build());
//		notes.add(note().pos(15).pc(9).len(3).build());
//		notes.add(note().pos(18).pc(2).len(6).build());
//		
//		notes.add(note().pos(33).pc(1).len(3).build());
//		notes.add(note().pos(36).pc(11).len(6).build());
//		notes.add(note().pos(42).pc(1).len(3).build());
//		notes.add(note().pos(45).pc(11).len(3).build());
//		notes.add(note().pos(48).pc(9).len(12).build());
//		CpMelody melody = new CpMelody(notes, Scale.MAJOR_SCALE, piano.getVoice());
//		MelodyBlock melodyBlock = new MelodyBlock(3, piano.getVoice());
//		melodyBlock.addMelodyBlock(melody);
//		melodyBlock.setMutable(false);
//		melodyBlock.setInstrument(piano);
//		
//		melodyBlocks.add(melodyBlock);
		
		List<MelodyBlock> melodyBlocks = new ArrayList<>();
		
		List<Integer> beats = new ArrayList<>();
		beats.add(12);
		beats.add(24);
//		beats.add(48);
//		beats.add(36);

//		InstrumentRegister violaPleasant = new ViolaPleasant();
		Instrument frenchHorn = new FrenchHorn(0, 3);
//		cello.setKeySwitch(new KontactStringsKeySwitch());
//		violaPleasant.getInstrument().setVoice(0);
//		MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlock(violaPleasant.getInstrument().getVoice(), violaPleasant.pickRandomOctaveFromRange(), 192, 4, beats);

		MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlock(frenchHorn.getVoice(), 0, 192, 4, beats);
		melodyBlock.setInstrument(frenchHorn);
//		melodyBlock.setInstrumentRegister(violaPleasant);
		melodyBlocks.add(melodyBlock);
		
		List<Integer> beats2 = new ArrayList<>();
		beats2.add(12);
		beats2.add(24);
//		beats2.add(36);
		

//		Instrument clarinet = new Clarinet(1, 2);
//		melodyBlock = melodyGenerator.generateMelodyBlock(clarinet.getVoice(), 0, 192, 5, beats);
//		melodyBlock.setInstrument(clarinet);
//		melodyBlocks.add(melodyBlock);
		
//		Instrument clarinet = new Clarinet(1, 2);
//		MelodyBlock melodyBlock2 = new MelodyBlock(5, clarinet.getVoice());
//		melodyBlock2.setVoice(clarinet.getVoice());
//		melodyBlock2.setOffset(48);
//		OperatorType operatorType = new OperatorType(cp.model.melody.Operator.T_RELATIVE);
////		operatorType.setSteps(1);
////		operatorType.setFunctionalDegreeCenter(1);
//		melodyBlock2.setOperatorType(operatorType);
//		melodyBlock2.dependsOn(melodyBlock.getVoice());
//		melodyBlock2.setInstrument(clarinet);
//		melodyBlocks.add(melodyBlock2);

//		Instrument flute = new Flute(2, 1);
//		melodyBlock = melodyGenerator.generateMelodyBlock(flute.getVoice(), 0, 144, 6, beats2);
//		melodyBlock.setInstrument(flute);
//
//		melodyBlocks.add(melodyBlock);
	
		//fugue
//		Instrument flute = new Flute(2, 1);
//		melodyBlock2 = new MelodyBlock(6, flute.getVoice());
//		melodyBlock2.setVoice(flute.getVoice());
//		melodyBlock2.setOffset(96);
//		operatorType = new OperatorType(cp.model.melody.Operator.T_RELATIVE);
////		operatorType.setSteps(1);
////		operatorType.setFunctionalDegreeCenter(1);
//		melodyBlock2.setOperatorType(operatorType);
//		melodyBlock2.dependsOn(melodyBlock.getVoice());
//		melodyBlock2.setInstrument(flute);
//		melodyBlocks.add(melodyBlock2);
		
	    Motive motive = new Motive(melodyBlocks);
	    solutionType.setMotive(motive);
	    
	    // Algorithm parameters
	    int populationSize = 30;
	    algorithm.setInputParameter("populationSize", populationSize);
	    algorithm.setInputParameter("maxEvaluations", populationSize * 1500);
	    
	    // Mutation and Crossover
	    crossover.setParameter("probabilityCrossover", 1.0); 
	
	    // Add the operators to the algorithm
	    algorithm.addOperator("crossover", crossover);
	    algorithm.addOperator("oneNoteMutation", oneNoteMutation);
//	    algorithm.addOperator("addRhythm", addRhythm);
//	    algorithm.addOperator("removeRhythm", removeRhythm);
	    algorithm.addOperator("articulationMutation", articulationMutation);
	    algorithm.addOperator("replaceMelody", replaceMelody);
	    algorithm.addOperator("selection", SelectionFactory.getSelectionOperator("BinaryTournament2", parameters));
	
	    // Execute the Algorithm
	    SolutionSet population = algorithm.execute();
	    
	    // result
	    population.printObjectivesToFile("SOL");
	    display.view(population, musicProperties.getTempo());
	}

	private void inTempo(int tempo) {
		musicProperties.setTempo(tempo);
	}

	private void composeInKey(Key key) {
		musicProperties.setKeySignature(key.getKeySignature());
		musicProperties.setKey(key);
	}
	
	private static void clean() throws IOException{
		deleteFiles("resources/midi");
		deleteFiles("resources/xml");
	}

	private static void deleteFiles(String path) throws IOException{
		File dir = new File(path);
		for (File file : dir.listFiles()) {
			file.delete();
		}
	}
	
	private void composeInMeter(int numerator, int denominator){
		musicProperties.setNumerator(numerator);
		musicProperties.setDenominator(denominator);
	}
}

