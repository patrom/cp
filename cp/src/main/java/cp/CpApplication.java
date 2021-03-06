package cp;

import cp.combination.even.FourNoteEven;
import cp.composition.*;
import cp.config.BeatGroupConfig;
import cp.generator.MusicProperties;
import cp.model.Motive;
import cp.model.melody.MelodyBlock;
import cp.nsga.MusicSolution;
import cp.nsga.MusicSolutionType;
import cp.nsga.MusicVariable;
import cp.out.orchestration.HarmonyOrchestrator;
import cp.out.orchestration.Orchestrator;
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

@Import({BeatGroupConfig.class, DefaultConfig.class, VariationConfig.class})
public class CpApplication extends JFrame implements CommandLineRunner{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CpApplication.class);
	
	@Autowired
	private MusicSolutionType solutionType;
	@Autowired 
	private Operator crossover;
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
	@Autowired @Lazy
	@Qualifier(value="sixVoiceComposition")
	private SixVoiceComposition sixVoiceComposition;
	@Autowired @Lazy
	@Qualifier(value="twelveToneComposition")
	private TwelveToneComposition twelveToneComposition;
	@Autowired
	private Orchestrator orchestrator;
	@Autowired
	private HarmonizeNotes harmonizeNotes;
	@Autowired
	private HarmonyOrchestrator harmonyOrchestrator;

	@Autowired
	private FourNoteEven fourNoteEven;
	
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
//		composeInGenres.add(melodyComposition::compositionMap);

		//TWO VOICES
//		composeInGenres.add(twoVoiceComposition::random);
//		composeInGenres.add(twoVoiceComposition::compositionMap);
//		composeInGenres.add(twoVoiceComposition::beatEven);
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

		//THREE VOICES
//		composeInGenres.add(threeVoiceComposition::canon2Voice1Acc);
//		composeInGenres.add(threeVoiceComposition::operatorTplusAcc);
//		composeInGenres.add(threeVoiceComposition::operatorT);
//		threeVoiceComposition.setHarmonizeMelody(harmonizeNotes::getFileToHarmonize);
//		threeVoiceComposition.setHarmonizeVoice(2);
//		composeInGenres.add(threeVoiceComposition::harmonize);
//		composeInGenres.add(threeVoiceComposition::threeOverXX);
//		composeInGenres.add(threeVoiceComposition::allRandom);
// 		composeInGenres.add(threeVoiceComposition::compositionMap);

		//FOUR VOICES
//		composeInGenres.add(fourVoiceComposition::canonA3);
//		composeInGenres.add(fourVoiceComposition::canonA4);
//		composeInGenres.add(fourVoiceComposition::doubleCanon);
//		composeInGenres.add(fourVoiceComposition::allRandom);
//		fourVoiceComposition.setHarmonizeMelody(harmonizeNotes::getFileToHarmonize);
//		fourVoiceComposition.setHarmonizeVoice(1);
//		composeInGenres.add(fourVoiceComposition::harmonize);
//        composeInGenres.add(fourVoiceComposition::compositionMap);

		//FIVE VOICES
//		composeInGenres.add(fiveVoiceComposition::allRandom);
//		composeInGenres.add(fiveVoiceComposition::partAugmentation);
//		composeInGenres.add(fiveVoiceComposition::homophonicRhythm);
//		fiveVoiceComposition.setHarmonizeMelody(harmonizeNotes::getFileToHarmonize);
//		fiveVoiceComposition.setHarmonizeVoice(4);
//		composeInGenres.add(fiveVoiceComposition::harmonize);
//		composeInGenres.add(fiveVoiceComposition::doubleCanon);
//        composeInGenres.add(fiveVoiceComposition::compositionMap);

		//SIX VOICE
//		composeInGenres.add(sixVoiceComposition::allRandom);
        composeInGenres.add(sixVoiceComposition::compositionMap);

		//12 tone
//		composeInGenres.add(twelveToneComposition::composeMerge);

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
						.comparing(MusicSolution::getMelodicHarmonic)
                        .thenComparing(MusicSolution::getMelody)
                        .thenComparing(MusicSolution::getHarmony));
//                        .thenComparing(MusicSolution::getResolution));
////						.thenComparing(MusicSolution::getTransformation)
//						.thenComparing(MusicSolution::getResolution)
//			    		.thenComparing(MusicSolution::getMelody));

			    
			    Iterator<Solution> solutionIterator = population.iterator();
			    int i = 1;
			    while (solutionIterator.hasNext() && i < musicProperties.getOutputCountRun()) {
			    	Solution solution = solutionIterator.next();

			    	Motive solutionMotive = ((MusicVariable) solution.getDecisionVariables()[0]).getMotive();

//					Predicate<Note> harmonyFilter = n -> n.getVoice() != 5;
//					fiveVoiceComposition.addVoiceConfiguration(5 , fixedVoice);
//			    	MelodyBlock melodyBlock = harmonyOrchestrator.getChordsRhythmDependant(solutionMotive,5,fixedVoice, harmonyFilter, 2 );
//					solutionMotive.getMelodyBlocks().add(melodyBlock);

//					ArrayList<Integer> accompContour = new ArrayList<>();
//					accompContour.add(1);
//					accompContour.add(1);
//					accompContour.add(1);
//					BeatGroup beatGroup = new BeatGroupTwo(DurationConstants.EIGHT, Collections.singletonList(fourNoteEven::pos1234));
//					AccompGroup accompGroup = new AccompGroup(beatGroup, accompContour);
//					MelodyBlock melodyBlock = harmonyOrchestrator.updateAccomp(solutionMotive, accompGroup, 1, harmonyFilter);
//					solutionMotive.getMelodyBlocks().add(melodyBlock);

//			    	MelodyBlock block = harmonyOrchestrator.varyOriginalNote(solutionMotive, 2, 5);
//					solutionMotive.getMelodyBlocks().add(block);
//
//
//					block = harmonyOrchestrator.varyNextHarmonyNote(solutionMotive, 3, 6, harmonyFilter);
//					solutionMotive.getMelodyBlocks().add(block);

			    	String dateID = generateDateID();
					String id = dateID + "_" + CpApplication.COUNTER.getAndIncrement();
					LOGGER.info(id);
					display.view(solutionMotive, id);
					MusicSolution musicSolution = (MusicSolution) solution;
					LOGGER.info(musicSolution.toString());
//					orchestrator.orchestrate(solutionMotive.getMelodyBlocks(), id);
					i++;
				}
			   
		} 
	}

	private void configureAlgorithm() throws JMException {
		// Algorithm parameters
	    int populationSize = 30;
	    algorithm.setInputParameter("populationSize", populationSize);
	    algorithm.setInputParameter("maxEvaluations", populationSize * 2000);
	    
	    // Mutation and Crossover
	    crossover.setParameter("probabilityCrossover", 1.0); 
	
	    // Add the operators to the algorithm
	    algorithm.addOperator("crossover", crossover);
	    algorithm.addOperator("selection", SelectionFactory.getSelectionOperator("BinaryTournament2", parameters));
	}
	
	private static void clean(){
		deleteFiles("src/main/resources/midi");
		deleteFiles("src/main/resources/orch");
		deleteFiles("src/main/resources/xml");
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

