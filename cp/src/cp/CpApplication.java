package cp;

import static cp.model.note.NoteBuilder.note;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.swing.JFrame;

import jmetal.core.Algorithm;
import jmetal.core.Operator;
import jmetal.core.SolutionSet;
import jmetal.operators.selection.SelectionFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Import;

import cp.generator.MelodyGenerator;
import cp.generator.MusicProperties;
import cp.model.Motive;
import cp.model.melody.CpMelody;
import cp.model.melody.Transposition;
import cp.model.note.Note;
import cp.model.note.NoteBuilder;
import cp.model.note.Scale;
import cp.nsga.MusicSolutionType;
import cp.nsga.operator.mutation.melody.ArticulationMutation;
import cp.nsga.operator.mutation.melody.OneNoteMutation;
import cp.nsga.operator.mutation.rhythm.AddRhythm;
import cp.nsga.operator.mutation.rhythm.RemoveRhythm;
import cp.out.instrument.Instrument;
import cp.out.print.Display;

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
	private AddRhythm addRhythm;
	@Autowired
	private RemoveRhythm removeRhythm;
	@Autowired
	private ArticulationMutation articulationMutation;
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
		musicProperties.fourFour();
		List<CpMelody> melodies = new ArrayList<CpMelody>();
	
			CpMelody melody = melodyGenerator.generateMelody(Scale.MAJOR_SCALE, new int[]{0,144}, 6, 0);
			melody.setInstrument(musicProperties.findInstrument(0));
			melody.updatePitches(5);
			melodies.add(melody);
			
//			List<Note> notes = new ArrayList<>();
//			notes.add(note().pos(0).pc(0).build());
//			notes.add(note().pos(24).pc(5).build());
//			notes.add(note().pos(48).pc(0).build());
//			notes.add(note().pos(72).pc(0).build());
//			notes.add(note().pos(96).pc(0).build());
//			
//			CpMelody melody2 = melodyGenerator.generateMelody(Scale.HARMONIC_MINOR_SCALE_II, new int[]{0,96}, 6, 1);
//			melody2.setInstrument(musicProperties.findInstrument(1));
//			melody2.updatePitches(4);
//			melodies.add(melody2);
			
//			CpMelody melody2 = new CpMelody(notes, Scale.HARMONIC_MINOR_SCALE_VI, 1);
//			melody2.setRhythmMutable(false);
//			melody2.setInstrument(musicProperties.findInstrument(1));
//			melody2.updatePitches(4);
//			melodies.add(melody2);
			
			//fugue - set fugue in template!
			CpMelody comes = new CpMelody(Scale.HARMONIC_MINOR_SCALE, 1, 24, 144);
			comes.copyMelody(melody,  -10 , Transposition.RELATIVE);
			comes.setMutable(false);
			comes.setInstrument(musicProperties.findInstrument(1));
			melodies.add(comes);
			
	    Motive motive = new Motive(melodies);
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
	    algorithm.addOperator("addRhythm", addRhythm);
	    algorithm.addOperator("removeRhythm", removeRhythm);
	    algorithm.addOperator("articulationMutation", articulationMutation);
	    algorithm.addOperator("selection", SelectionFactory.getSelectionOperator("BinaryTournament2", parameters));
	
	    // Execute the Algorithm
	    SolutionSet population = algorithm.execute();
	    
	    // result
	    population.printObjectivesToFile("SOL");
	    display.view(population, musicProperties.getTempo());
	}

	private void deleteMidiFiles(String midiFilesPath) throws IOException{
		List<File> midiFiles = Files.list(new File(midiFilesPath).toPath()).map(p -> p.toFile()).collect(Collectors.toList());
		for (File file : midiFiles) {
			file.delete();
		}
	}
	
}

