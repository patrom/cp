package cp;

import cp.composition.*;
import cp.generator.MusicProperties;
import cp.model.Motive;
import cp.model.melody.MelodyBlock;
import cp.nsga.MusicSolution;
import cp.nsga.MusicSolutionType;
import cp.nsga.MusicVariable;
import cp.nsga.operator.mutation.melody.*;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Import({DefaultConfig.class, VariationConfig.class})
public class CpApplication extends JFrame implements CommandLineRunner{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CpApplication.class.getName());
	
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
	private CopyMelody copyMelody;
	@Autowired
	private ReplaceMelodyBlock replaceMelodyBlock;
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
	@Autowired @Lazy
	@Qualifier(value="melodyComposition")
	private MelodyComposition melodyComposition;
	@Autowired @Lazy
	@Qualifier(value="twoVoiceComposition")
	private TwoVoiceComposition twoVoiceComposition;
	@Autowired @Lazy
	@Qualifier(value="threeVoiceComposition")
	private ThreeVoiceComposition threeVoiceComposition;
	@Autowired @Lazy
	@Qualifier(value="fourVoiceComposition")
	private FourVoiceComposition fourVoiceComposition;
	@Autowired @Lazy
	@Qualifier(value="fiveVoiceComposition")
	private FiveVoiceComposition fiveVoiceComposition;
	@Autowired
	private Orchestrator orchestrator;
	@Autowired
	private Pleasant pleasant;
	@Autowired
	private Brilliant brilliant;
	@Autowired
	private HarmonizeNotes harmonizeNotes;
	
	private static final AtomicInteger COUNTER = new AtomicInteger();
	
	public static void main(final String[] args)  {
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
//		composeInGenres.add(melodyComposition::melody);

//		composeInGenres.add(twoVoiceComposition::voiceConfig);

//		composeInGenres.add(twoVoiceComposition::beatEven);
//		composeInGenres.add(twoVoiceComposition::canon);
//		composeInGenres.add(twoVoiceComposition::fugueInverse);
//		composeInGenres.add(twoVoiceComposition::operatorT);
//		composeInGenres.add(twoVoiceComposition::operatorI);
		//TODO:
//		composeInGenres.add(twoVoiceComposition::operatorR);
//		composeInGenres.add(twoVoiceComposition::operatorM);
//		composeInGenres.add(twoVoiceComposition::augmentation);
//		composeInGenres.add(twoVoiceComposition::diminution);
//		twoVoiceComposition.setHarmonizeMelody(harmonizeNotes::getFileToHarmonize);
//		twoVoiceComposition.setHarmonizeVoice(1);
//		composeInGenres.add(twoVoiceComposition::harmonize);

		composeInGenres.add(threeVoiceComposition::canon2Voice1Acc);
//		composeInGenres.add(threeVoiceComposition::accFixedRhythm);
//		composeInGenres.add(threeVoiceComposition::operatorTplusAcc);
//		composeInGenres.add(threeVoiceComposition::operatorT);
//		threeVoiceComposition.setHarmonizeMelody(harmonizeNotes::getFileToHarmonize);
//		threeVoiceComposition.setHarmonizeVoice(2);
//		composeInGenres.add(threeVoiceComposition::harmonize);
//		composeInGenres.add(threeVoiceComposition::halfTimeHomophonicRhythm);
//		composeInGenres.add(threeVoiceComposition::threeOverXX);
//		composeInGenres.add(threeVoiceComposition::accDuplicateRhythm);
//		composeInGenres.add(threeVoiceComposition::allRandom);

//		composeInGenres.add(fourVoiceComposition::canon);
//		composeInGenres.add(fourVoiceComposition::accDuplicateRhythm);
//		composeInGenres.add(fourVoiceComposition::doubleCanon);
//		fourVoiceComposition.setHarmonizeMelody(harmonizeNotes::getFileToHarmonize);
//		fourVoiceComposition.setHarmonizeVoice(1);
//		composeInGenres.add(fourVoiceComposition::harmonize);

//		composeInGenres.add(fiveVoiceComposition::accDuplicateRhythm);
//		fiveVoiceComposition.setHarmonizeMelody(harmonizeNotes::getFileToHarmonize);
//		fiveVoiceComposition.setHarmonizeVoice(4);
//		composeInGenres.add(fiveVoiceComposition::harmonize);

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
						.comparing(MusicSolution::getMelody)
						.thenComparing(MusicSolution::getResolution)
			    		.thenComparing(MusicSolution::getVoiceLeading)
			    		.thenComparing(MusicSolution::getHarmony));

			    
			    Iterator<Solution> solutionIterator = population.iterator();
			    int i = 1;
			    while (solutionIterator.hasNext() && i < musicProperties.getOutputCountRun()) {
			    	Solution solution = solutionIterator.next();
			    	Motive solutionMotive = ((MusicVariable) solution.getDecisionVariables()[0]).getMotive();
			    	String dateID = generateDateID();
					String id = dateID + "_" + CpApplication.COUNTER.getAndIncrement();
					LOGGER.info(id);
					display.view(solutionMotive, id);
					orchestrator.orchestrate(solutionMotive.getMelodyBlocks(), id);
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
		algorithm.addOperator("copyMelody", copyMelody);
		algorithm.addOperator("replaceMelodyBlock", replaceMelodyBlock);
	    algorithm.addOperator("selection", SelectionFactory.getSelectionOperator("BinaryTournament2", parameters));
	}
	
	private static void clean(){
		deleteFiles("cp/src/main/resources/midi");
		deleteFiles("cp/src/main/resources/orch");
		deleteFiles("cp/src/main/resources/xml");
	}

	private static void deleteFiles(String path){
		final Resource resource = new FileSystemResource(path);

        try {
            final File dir = resource.getFile();
            for (File file : dir.listFiles()) {
                file.delete();
            }
        } catch (IOException e) {
			LOGGER.error("Can't delete files in: " + path);
        }
    }
	
	private String generateDateID() {
		LocalDateTime currentDateTime = LocalDateTime.now();
		return currentDateTime.format(DateTimeFormatter.ofPattern("ddMM_HHmm"));
	}
	
}

