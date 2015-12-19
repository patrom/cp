package cp;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import cp.generator.pitchclass.PassingPitchClasses;
import cp.generator.pitchclass.PitchClassGenerator;
import cp.generator.pitchclass.RandomPitchClasses;
import cp.model.Motive;
import cp.model.dissonance.IntervalDissonance;
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
import cp.out.instrument.KontaktLibCello;
import cp.out.instrument.KontaktLibViolin;
import cp.out.print.Display;
import cp.out.print.note.NoteStep;

@Import({DefaultConfig.class, VariationConfig.class})
public class CpApplication extends JFrame implements CommandLineRunner{
	
	private static Logger LOGGER = LoggerFactory.getLogger(CpApplication.class.getName());
	
	@Autowired
	private NoteStep C;
	@Autowired
	private NoteStep Csharp;
	@Autowired
	private NoteStep D;
	@Autowired
	private NoteStep Dsharp;
	@Autowired
	private NoteStep E;
	@Autowired
	private NoteStep F;
	@Autowired
	private NoteStep Fsharp;
	@Autowired
	private NoteStep G;
	@Autowired
	private NoteStep Gsharp;
	@Autowired
	private NoteStep A;
	@Autowired
	private NoteStep Bflat;
	@Autowired
	private NoteStep B;
	
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
	private String midiFilesPath;
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
		composeInMeter(4,4);
		composeInKey(D);
		inTempo(100);
		replaceMelody.setPitchClassGenerator(passingPitchClasses::updatePitchClasses);
		melodyGenerator.setPitchClassGenerator(passingPitchClasses::updatePitchClasses);
		
		harmonicObjective.setDissonance(intervalDissonance::getDissonance);
		
		List<Integer> beats = new ArrayList<>();
//		beats.add(12);
		beats.add(24);
//		beats.add(48);
		
		List<MelodyBlock> melodyBlocks = new ArrayList<>();
		
		//harmonization
//		Instrument cello = new KontaktLibCello(0, 3);
//		List<Note> notes = new ArrayList<>();
//		notes.add(note().pos(0).pc(2).len(24).build());
//		notes.add(note().pos(24).pc(4).len(24).build());
//		notes.add(note().pos(48).pc(6).len(24).build());
//		notes.add(note().pos(72).pc(7).len(24).build());
//		notes.add(note().pos(96).pc(2).len(12).build());
//		CpMelody melody = new CpMelody(notes, Scale.MAJOR_SCALE, cello.getVoice());
//		MelodyBlock melodyBlock = new MelodyBlock(3, cello.getVoice());
//		melodyBlock.addMelodyBlock(melody);
//		melodyBlock.setMutable(false);
//		melodyBlock.setInstrument(cello);
//		
//		melodyBlocks.add(melodyBlock);
		
		Instrument cello = new KontaktLibCello(0, 3);
		MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlock(cello.getVoice(), Scale.OCTATCONIC_HALF, 0, 192, 3, beats);
		melodyBlock.setInstrument(cello);
		melodyBlocks.add(melodyBlock);
		
		List<Integer> beats2 = new ArrayList<>();
		beats2.add(12);
//		beats2.add(24);
		
		Instrument violin = new KontaktLibViolin(1, 2);
		melodyBlock = melodyGenerator.generateMelodyBlock(violin.getVoice(), Scale.OCTATCONIC_HALF, 0, 192, 5, beats2);
		melodyBlock.setInstrument(violin);
		melodyBlocks.add(melodyBlock);
	
		//fugue
//		Instrument cello = new KontaktLibCello(0, 3);
//		MelodyBlock melodyBlock2 = new MelodyBlock(3, cello.getVoice());
//		melodyBlock2.setVoice(cello.getVoice());
//		melodyBlock2.setOffset(48);
//		OperatorType operatorType = new OperatorType(cp.model.melody.Operator.I_RELATIVE);
////		operatorType.setSteps(1);
//		operatorType.setFunctionalDegreeCenter(3);
//		melodyBlock2.setOperatorType(operatorType);
//		melodyBlock2.dependsOn(melodyBlock.getVoice());
//		melodyBlock2.setInstrument(cello);
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

	private void composeInKey(NoteStep key) {
		musicProperties.setKeySignature(key.getKeySignature());
		musicProperties.setKey(key);
	}

	private void deleteMidiFiles(String midiFilesPath) throws IOException{
		List<File> midiFiles = Files.list(new File(midiFilesPath).toPath()).map(p -> p.toFile()).collect(Collectors.toList());
		for (File file : midiFiles) {
			file.delete();
		}
	}
	
	private void composeInMeter(int numerator, int denominator){
		musicProperties.setNumerator(numerator);
		musicProperties.setDenominator(denominator);
	}
}

