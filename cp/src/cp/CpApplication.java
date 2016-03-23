package cp;

import static cp.out.orchestration.InstrumentName.FLUTE;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
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
import cp.genre.ComposeInGenre;
import cp.genre.TwoVoiceComposition;
import cp.model.Motive;
import cp.model.TimeLine;
import cp.model.TimeLineKey;
import cp.model.dissonance.IntervalAndTriads;
import cp.model.dissonance.IntervalDissonance;
import cp.model.melody.MelodyBlock;
import cp.model.note.Scale;
import cp.nsga.MusicSolution;
import cp.nsga.MusicSolutionType;
import cp.nsga.MusicVariable;
import cp.nsga.operator.mutation.melody.ArticulationMutation;
import cp.nsga.operator.mutation.melody.OneNoteMutation;
import cp.nsga.operator.mutation.melody.ReplaceMelody;
import cp.objective.harmony.HarmonicObjective;
import cp.out.instrument.Instrument;
import cp.out.instrument.woodwinds.Flute;
import cp.out.instrument.woodwinds.Oboe;
import cp.out.orchestration.InstrumentName;
import cp.out.orchestration.Orchestrator;
import cp.out.orchestration.quality.Pleasant;
import cp.out.print.Display;
import cp.out.print.note.Key;
import jmetal.core.Algorithm;
import jmetal.core.Operator;
import jmetal.core.Solution;
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
	@Autowired
	private ComposeInGenre composeInGenre;
	@Autowired
	private TwoVoiceComposition twoVoiceComposition;
	@Autowired
	private Orchestrator orchestrator;
	@Autowired
	private Pleasant pleasant;
	
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
		List<Instrument> instruments = new ArrayList<Instrument>();
		Instrument instrument1 = pleasant.getInstrument(FLUTE.getName());
		instruments.add(instrument1);
//		instruments.add(new Oboe());
		List<TimeLineKey> keys = new ArrayList<>();
		keys.add(new TimeLineKey(C, instrument1.filterScale(Scale.MAJOR_SCALE), 0, 96));
		keys.add(new TimeLineKey(C, instrument1.filterScale(Scale.HARMONIC_MINOR_SCALE), 48, 192));//match length
//		keys.add(new TimeLineKey(A, Scale.HARMONIC_MINOR_SCALE, 48, 96));
//		keys.add(new TimeLineKey(E, Scale.HARMONIC_MINOR_SCALE, 96, 144));
//		keys.add(new TimeLineKey(G, Scale.MAJOR_SCALE, 144, 192));
		timeLine.addKeysForVoice(keys, 0);
		timeLine.addKeysForVoice(keys, 1);//match instruments
		composeInKey(C);
		inTempo(70);
		replaceMelody.setPitchClassGenerator(passingPitchClasses::updatePitchClasses);
		melodyGenerator.setPitchClassGenerator(passingPitchClasses::updatePitchClasses);
		harmonicObjective.setDissonance(intervalAndTriads::getDissonance);
		
		for (int i = 0; i < 1; i++) {
			LOGGER.info("RUN: " + i + " START");		
			compose(instruments);
		    LOGGER.info("RUN: " + i + " END");
		}
	}

	private void compose(List<Instrument> instruments) throws Exception {
		composeInGenre.setCompositionGenre(twoVoiceComposition::beatEven);
		List<MelodyBlock> melodyBlocks = composeInGenre.composeInGenre(instruments);
		
	    Motive motive = new Motive(melodyBlocks);
	    solutionType.setMotive(motive);
	    
	    configureAlgorithm();
	
	    // Execute the Algorithm
	    SolutionSet population = algorithm.execute();
	    
	    // result
	    population.printObjectivesToFile("SOL");
	    
//	    population.sort(Comparator.comparing(MusicSolution::getMelody).thenComparing(MusicSolution::getHarmony));
	    population.sort(Comparator
	    		.comparing(MusicSolution::getResolution)
	    		.thenComparing(MusicSolution::getHarmony)
	    		.thenComparing(MusicSolution::getMelody));
	    
	    Iterator<Solution> solutionIterator = population.iterator();
	    int i = 1;
	    while (solutionIterator.hasNext() && i < musicProperties.getOutputCountRun()) {
	    	Solution solution = (Solution) solutionIterator.next();
	    	Motive solutionMotive = ((MusicVariable) solution.getDecisionVariables()[0]).getMotive();
	    	String dateID = generateDateID();
			String id = dateID + "_" + CpApplication.COUNTER.getAndIncrement();
			LOGGER.info(id);
			display.view(solutionMotive, id);
			orchestrator.orchestrate(solutionMotive.getMelodyBlocks(), id);
			i++;
		}
	   
	}

	private void configureAlgorithm() throws JMException {
		// Algorithm parameters
	    int populationSize = 30;
	    algorithm.setInputParameter("populationSize", populationSize);
	    algorithm.setInputParameter("maxEvaluations", populationSize * 15);
	    
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
	
	private String generateDateID() {
		LocalDateTime currentDateTime = LocalDateTime.now();
		return currentDateTime.format(DateTimeFormatter.ofPattern("ddMM_HHmm"));
	}
	
}

