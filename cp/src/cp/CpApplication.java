package cp;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Import;

import cp.composition.ComposeInGenre;
import cp.composition.CompositionGenre;
import cp.composition.ThreeVoiceComposition;
import cp.composition.TwoVoiceComposition;
import cp.generator.MusicProperties;
import cp.model.Motive;
import cp.model.melody.MelodyBlock;
import cp.nsga.MusicSolution;
import cp.nsga.MusicSolutionType;
import cp.nsga.MusicVariable;
import cp.nsga.operator.mutation.melody.ArticulationMutation;
import cp.nsga.operator.mutation.melody.OneNoteMutation;
import cp.nsga.operator.mutation.melody.ReplaceMelody;
import cp.out.orchestration.Orchestrator;
import cp.out.orchestration.quality.Brilliant;
import cp.out.orchestration.quality.Pleasant;
import cp.out.print.Display;
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
	private ComposeInGenre composeInGenre;
	@Autowired
	@Qualifier(value="twoVoiceComposition")
	private TwoVoiceComposition twoVoiceComposition;
	@Autowired
	@Qualifier(value="threeVoiceComposition")
	private ThreeVoiceComposition threeVoiceComposition;
	@Autowired
	private Orchestrator orchestrator;
	@Autowired
	private Pleasant pleasant;
	@Autowired
	private Brilliant brilliant;
	
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
		for (int i = 0; i < 10; i++) {
			LOGGER.info("RUN: " + i + " START");		
			compose();
		    LOGGER.info("RUN: " + i + " END");
		}
	}

	private void compose() throws Exception {
		List<CompositionGenre> composeInGenres = new ArrayList<>();
		composeInGenres.add(twoVoiceComposition::beatEven);
		composeInGenres.add(twoVoiceComposition::canon);
		composeInGenres.add(twoVoiceComposition::fugueInverse);
		composeInGenres.add(twoVoiceComposition::operatorT);
		composeInGenres.add(twoVoiceComposition::operatorI);
		//TO FIX:
//		composeInGenres.add(twoVoiceComposition::operatorR);
//		composeInGenres.add(twoVoiceComposition::operatorM);

		composeInGenres.add(threeVoiceComposition::canon2Voice1Acc);
		composeInGenres.add(threeVoiceComposition::accFixedRhythm);
		composeInGenres.add(threeVoiceComposition::operatorTplusAcc);
		composeInGenres.add(threeVoiceComposition::operatorT);
		
		for (CompositionGenre compositionGenre : composeInGenres) {
			composeInGenre.setCompositionGenre(compositionGenre);
			List<MelodyBlock> melodyBlocks = composeInGenre.composeInGenre();
			
			  Motive motive = new Motive(melodyBlocks);
			    solutionType.setMotive(motive);
			    
			    configureAlgorithm();
			
			    // Execute the Algorithm
			    SolutionSet population = algorithm.execute();
			    
			    // result
			    population.printObjectivesToFile("SOL");
			    
//			    population.sort(Comparator.comparing(MusicSolution::getMelody).thenComparing(MusicSolution::getHarmony));
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
//					orchestrator.orchestrate(solutionMotive.getMelodyBlocks(), id);
					i++;
				}
			   
		} 
	}

	private void configureAlgorithm() throws JMException {
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
	}
	
	private static void clean() throws IOException{
		deleteFiles("resources/midi");
		deleteFiles("resources/orch");
		deleteFiles("resources/xml");
	}

	private static void deleteFiles(String path) throws IOException{
		File dir = new File(path);
		for (File file : dir.listFiles()) {
			file.delete();
		}
	}
	
	private String generateDateID() {
		LocalDateTime currentDateTime = LocalDateTime.now();
		return currentDateTime.format(DateTimeFormatter.ofPattern("ddMM_HHmm"));
	}
	
}

