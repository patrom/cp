package cp;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
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
import cp.model.melody.CpMelody;
import cp.model.note.Scale;
import cp.nsga.MusicSolutionType;
import cp.nsga.operator.mutation.harmony.HarmonyNoteToPitch;
import cp.nsga.operator.mutation.harmony.SwapHarmonyNotes;
import cp.nsga.operator.mutation.melody.MelodyNoteToHarmonyNote;
import cp.nsga.operator.mutation.melody.OneNoteMutation;
import cp.nsga.operator.mutation.rhythm.AddRhythm;
import cp.out.print.Display;

@Import({DefaultConfig.class, VariationConfig.class})
public class CpApplication extends JFrame implements CommandLineRunner{
	
	private static Logger LOGGER = Logger.getLogger(CpApplication.class.getName());
	
	@Autowired
	private MusicSolutionType solutionType;
	@Autowired 
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
	private AddRhythm addRhythm;
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
			SpringApplication app = new SpringApplication(CpApplication.class);
		    app.setShowBanner(false);
		    app.run(args);
		    LOGGER.info("RUN: " + i + " END");
		}
	}

	@Override
	public void run(String... arg0) throws Exception {
		deleteMidiFiles(midiFilesPath);
//		musicProperties.fourFour();

		CpMelody cpMelody = melodyGenerator.generateMelody(Scale.MAJOR_SCALE, new int[]{0,60}, 6, 0);
		cpMelody.updatePitches(5);
	    Motive motive = new Motive(Collections.singletonList(cpMelody), musicProperties);
	    solutionType.setMotive(motive);
	    
	    // Algorithm parameters
	    int populationSize = 30;
	    algorithm.setInputParameter("populationSize", populationSize);
	    algorithm.setInputParameter("maxEvaluations", populationSize * 1500);
	    
	    // Mutation and Crossover
	    crossover.setParameter("probabilityCrossover", 1.0); 
	    
	    //harmony
	    List<Integer> allowedDefaultIndexes = allowedDefaultIndexes();
	    harmonyNoteToPitch.setParameter("probabilityHarmonyNoteToPitch", 0.0);
	    harmonyNoteToPitch.setAllowedMelodyMutationIndexes(allowedDefaultIndexes);
//	    harmonyNoteToPitch.setOuterBoundaryIncluded(false);//default == true;
	    
	    swapHarmonyNotes.setParameter("probabilitySwap", 0.0);
	    swapHarmonyNotes.setAllowedMelodyMutationIndexes(allowedDefaultIndexes);
	    
	    //melody
	    melodyNoteToHarmonyNote.setParameter("probabilityMelodyNoteToHarmonyNote", 0.0);
	    melodyNoteToHarmonyNote.setAllowedMelodyMutationIndexes(allowedDefaultIndexes);
//	    melodyNoteToHarmonyNote.setOuterBoundaryIncluded(false);
	    
//	    oneNoteMutation.setParameter("probabilityOneNote", 1.0);
//	    oneNoteMutation.setAllowedMelodyMutationIndexes(allowedDefaultIndexes);
//	    oneNoteMutation.setOuterBoundaryIncluded(false);
	    
	    //pitch
	    pitchSpaceMutation.setParameter("probabilityPitchSpace", 0.0);
	
	    // Add the operators to the algorithm
	    algorithm.addOperator("crossover", crossover);
	    algorithm.addOperator("oneNoteMutation", oneNoteMutation);
//	    algorithm.addOperator("swapHarmonyNotes", swapHarmonyNotes);
//	    algorithm.addOperator("harmonyNoteToPitch", harmonyNoteToPitch);
//	    algorithm.addOperator("melodyNoteToHarmonyNote", melodyNoteToHarmonyNote);
	    algorithm.addOperator("addRhythm", addRhythm);
//	    algorithm.addOperator("pitchSpaceMutation", pitchSpaceMutation);
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

