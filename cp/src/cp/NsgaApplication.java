package cp;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.JFrame;

import jmetal.core.Algorithm;
import jmetal.core.Operator;
import jmetal.core.Problem;
import jmetal.core.SolutionSet;
import jmetal.operators.selection.SelectionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Import;

import cp.generator.Generator;
import cp.generator.MelodyGenerator;
import cp.generator.MusicProperties;
import cp.generator.RandomNotesGenerator;
import cp.generator.TonalChordGenerator;
import cp.generator.TonalChords;
import cp.model.Motive;
import cp.nsga.MusicSolutionType;
import cp.nsga.operator.mutation.harmony.HarmonyNoteToPitch;
import cp.nsga.operator.mutation.harmony.SwapHarmonyNotes;
import cp.nsga.operator.mutation.melody.MelodyNoteToHarmonyNote;
import cp.nsga.operator.mutation.melody.OneNoteMutation;
import cp.out.print.Display;

@Import({DefaultConfig.class, VariationConfig.class})
public class NsgaApplication extends JFrame implements CommandLineRunner{
	
	private static Logger LOGGER = Logger.getLogger(NsgaApplication.class.getName());
//	
//	@Autowired
//	private Problem problem;
	@Autowired
	private MusicSolutionType solutionType;
	@Autowired 
//	@Qualifier(value="crossover")
	private Operator crossover;
	@Autowired
	private OneNoteMutation oneNoteMutation;
	@Autowired
	private SwapHarmonyNotes swapHarmonyNotes;
	@Autowired
	private HarmonyNoteToPitch harmonyNoteToPitch;
	@Autowired
	private MelodyNoteToHarmonyNote melodyNoteToHarmonyNote;
	@Autowired
	private Operator pitchSpaceMutation;
	@Autowired
	private Algorithm algorithm;
	@Autowired
	private Display display;
	@Autowired
	private MusicProperties musicProperties;
	@Autowired
	private HashMap<String, Object> parameters;
	@Autowired
	private String midiFilesPath;
	@Autowired
	private MelodyGenerator melodyGenerator;
	
	public static AtomicInteger COUNTER = new AtomicInteger();
	
	public static void main(final String[] args) throws IOException {
		for (int i = 0; i < 1; i++) {
			LOGGER.info("RUN: " + i + " START");
			SpringApplication app = new SpringApplication(NsgaApplication.class);
		    app.setShowBanner(false);
		    app.run(args);
		    LOGGER.info("RUN: " + i + " END");
		}
	}

	@Override
	public void run(String... arg0) throws Exception {
		deleteMidiFiles(midiFilesPath);
//		musicProperties.threeFour();
		musicProperties.fourFour();
//		int[] generatedHamonies = melodyGenerator.generateHarmonyPositions(12, 4, 4);
//		int[][] melodyPositions = melodyGenerator.generateMelodies(generatedHamonies, 12);
		int[] harmonies = {0,12, 24,48,72, 96};
		int[][] melodies = {{18,24},{0,6,18},{6,12},{0, 18, 24},{0,24},{0,12},{0,12},{0, 18, 24},{0,24},{}};
//		int[][] melodies2 = {{18,24},{0, 12},{0, 12},{12,24},{0,12},{0,12},{0,12},{0,12},{0,12,24},{}};
//		int[][] generatedMelodyPositions = new int[harmonies.length - 1][];
//		for (int i = 0, j = 0; i < harmonies.length - 1; i++, j++) {
//			int[] harmony = {0, harmonies[i + 1] - harmonies[i]};
//			int[] melody = melodyGenerator.generateMelodyPositions(harmony, 12, 2);
//			generatedMelodyPositions[j] = melody;
//		}


//		BeginEndChordGenerator generator = beginEndChordGenerator;
		
//		TonalChordGenerator generator = new TonalChordGenerator(harmonies, musicProperties);
////		generator.generateHarmonicMelodiesForVoice(melodies, 3);
//		int key = 0;
//		musicProperties.setKeySignature(key);
//		generator.setChords(TonalChords.getTriads(key));
//		generator.addChords(TonalChords.getTriads(key + 7));
//		generator.addChords(TonalChords.getTriads(key + 5));

		Generator generator = new RandomNotesGenerator(harmonies, musicProperties);
//		generator.generateHarmonicMelodiesForVoice(generatedMelodyPositions, 5);
//		generator.generateHarmonicMelodiesForVoice(melodies, 3);
		
//		Generator generator = new DiffSizeGenerator(harmonies, musicProperties);
//		Generator generator = new PerleChordGenerator(harmonies, musicProperties);
	    Motive motive = generator.generateMotive();
	    solutionType.setMotive(motive);
	    
	    // Algorithm parameters
	    int populationSize = 30;
	    algorithm.setInputParameter("populationSize", populationSize);
	    algorithm.setInputParameter("maxEvaluations", populationSize * 1500);
	    
	    // Mutation and Crossover
	    crossover.setParameter("probabilityCrossover", 1.0); 
	    
	    //harmony
	    List<Integer> allowedDefaultIndexes = allowedDefaultIndexes();
	    harmonyNoteToPitch.setParameter("probabilityHarmonyNoteToPitch", 1.0);
	    harmonyNoteToPitch.setAllowedMelodyMutationIndexes(allowedDefaultIndexes);
//	    harmonyNoteToPitch.setOuterBoundaryIncluded(false);//default == true;
	    
	    swapHarmonyNotes.setParameter("probabilitySwap", 1.0);
	    swapHarmonyNotes.setAllowedMelodyMutationIndexes(allowedDefaultIndexes);
	    
	    //melody
	    melodyNoteToHarmonyNote.setParameter("probabilityMelodyNoteToHarmonyNote", 1.0);
	    melodyNoteToHarmonyNote.setAllowedMelodyMutationIndexes(allowedDefaultIndexes);
//	    melodyNoteToHarmonyNote.setOuterBoundaryIncluded(false);
	    
	    oneNoteMutation.setParameter("probabilityOneNote", 1.0);
	    oneNoteMutation.setAllowedMelodyMutationIndexes(allowedDefaultIndexes);
//	    oneNoteMutation.setOuterBoundaryIncluded(false);
	    
	    //pitch
	    pitchSpaceMutation.setParameter("probabilityPitchSpace", 1.0);
	
	    // Add the operators to the algorithm
	    algorithm.addOperator("crossover", crossover);
	    algorithm.addOperator("oneNoteMutation", oneNoteMutation);
	    algorithm.addOperator("swapHarmonyNotes", swapHarmonyNotes);
	    algorithm.addOperator("harmonyNoteToPitch", harmonyNoteToPitch);
	    algorithm.addOperator("melodyNoteToHarmonyNote", melodyNoteToHarmonyNote);
	    algorithm.addOperator("pitchSpaceMutation", pitchSpaceMutation);
	    algorithm.addOperator("selection", SelectionFactory.getSelectionOperator("BinaryTournament2", parameters));
	
	    // Execute the Algorithm
	    SolutionSet population = algorithm.execute();
	    
	    // result
	    population.printObjectivesToFile("SOL");
	    display.view(population, musicProperties.getTempo());
	}

	private List<Integer> allowedDefaultIndexes() {
		List<Integer> allowedDefaultIndexes = new ArrayList<>();
	    for (int i = 0; i < musicProperties.getChordSize(); i++) {
	    	allowedDefaultIndexes.add(i);
		}
		return allowedDefaultIndexes;
	}
	
	private void deleteMidiFiles(String midiFilesPath) throws IOException{
		List<File> midiFiles = Files.list(new File(midiFilesPath).toPath()).map(p -> p.toFile()).collect(Collectors.toList());
		for (File file : midiFiles) {
			file.delete();
		}
	}
	
}

