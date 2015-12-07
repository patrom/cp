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
import cp.model.Motive;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.Scale;
import cp.nsga.MusicSolutionType;
import cp.nsga.operator.mutation.melody.ArticulationMutation;
import cp.nsga.operator.mutation.melody.OneNoteMutation;
import cp.nsga.operator.mutation.melody.ReplaceMelody;
import cp.nsga.operator.mutation.rhythm.AddRhythm;
import cp.nsga.operator.mutation.rhythm.RemoveRhythm;
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
	private AddRhythm addRhythm;
	@Autowired
	private RemoveRhythm removeRhythm;
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
	
	private void composeInMeter(int numerator, int denominator){
		musicProperties.setNumerator(numerator);
		musicProperties.setDenominator(denominator);
	}
	
	@Override
	public void run(String... arg0) throws Exception {
		deleteMidiFiles(midiFilesPath);
		
		composeInMeter(4,4);
		composeInKey(C);
		
		List<Integer> beats = new ArrayList<>();
		beats.add(12);
//		beats.add(24);
//		beats.add(48);
		
		List<MelodyBlock> melodyBlocks = new ArrayList<>();
		
//		Instrument cello = new KontaktLibCello(0, 3);
//		MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlock(cello.getVoice(), Scale.MAJOR_SCALE, C.getInterval(), 0, 96, 3, beats);
//		melodyBlock.setInstrument(cello);
//		melodyBlocks.add(melodyBlock);
		
		
		Instrument violin = new KontaktLibViolin(1, 2);
		MelodyBlock melodyBlock = melodyGenerator.generateMelodyBlock(violin.getVoice(), Scale.MAJOR_SCALE, C.getInterval(), 12, 96, 5, beats);
		melodyBlock.setInstrument(violin);
		melodyBlocks.add(melodyBlock);
		
//		int voice = 0;
//		int key = 0;
//		MelodyBlock melodyBlock = new MelodyBlock(5);
//		melodyBlock.setVoice(voice);
//		CpMelody melody = melodyGenerator.generateMelody(voice, Scale.MAJOR_SCALE, key, 0, 12);
//		melodyBlock.addMelodyBlock(melody);
//		
//		melody = melodyGenerator.generateMelody(voice, Scale.MAJOR_SCALE, key, 12, 18);
//		melodyBlock.addMelodyBlock(melody);
//		
//		key = 9;
//		melody = melodyGenerator.generateMelody(voice, Scale.HARMONIC_MINOR_SCALE, key, 30, 12);
//		melodyBlock.addMelodyBlock(melody);
//		
//		melody = melodyGenerator.generateMelody(voice, Scale.HARMONIC_MINOR_SCALE, key, 42, 18);
//		melodyBlock.addMelodyBlock(melody);
//		
//		melodyBlock.setInstrument(musicProperties.findInstrument(voice));
//		melodyBlocks.add(melodyBlock);
	
		
//		melody = cpMelodyBuilder.start(24).voice(voice).build();
//		melodyBlock.addMelodyBlock(melody);
//		
//		melody = cpMelodyBuilder.start(48).voice(voice).build();
//		melodyBlock.addMelodyBlock(melody);
//		
//		melody = cpMelodyBuilder.start(72).voice(voice).beat(12).build();
//		melodyBlock.addMelodyBlock(melody);
//		melody = cpMelodyBuilder.start(84).voice(voice).beat(12).build();
//		melodyBlock.addMelodyBlock(melody);
//		
//		Note note = NoteBuilder.note().pc(0).voice(voice).pos(96).len(12).build();
//		melody = new CpMelody(Collections.singletonList(note), Scale.MAJOR_SCALE, voice, 96, 108);
//		melody.setReplaceable(false);
//		melodyBlock.addMelodyBlock(melody);
//		melodyBlock.setInstrument(musicProperties.findInstrument(voice));
//		melodyBlocks.add(melodyBlock);
//		
	
		//fugue
//		MelodyBlock melodyBlock2 = new MelodyBlock(4);
//		melodyBlock2.setVoice(1);
//		melodyBlock2.setOffset(24);
//		melodyBlock2.setOperatorType(new OperatorType(0, cp.model.melody.Operator.T));
//		melodyBlock2.dependsOn(melodyBlock.getVoice());
//		melodyBlock2.setInstrument(musicProperties.findInstrument(1));
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
	    algorithm.addOperator("addRhythm", addRhythm);
	    algorithm.addOperator("removeRhythm", removeRhythm);
	    algorithm.addOperator("articulationMutation", articulationMutation);
	    algorithm.addOperator("replaceMelody", replaceMelody);
	    algorithm.addOperator("selection", SelectionFactory.getSelectionOperator("BinaryTournament2", parameters));
	
	    // Execute the Algorithm
	    SolutionSet population = algorithm.execute();
	    
	    // result
	    population.printObjectivesToFile("SOL");
	    display.view(population, musicProperties.getTempo());
	}

	private void composeInKey(NoteStep key) {
		musicProperties.setKeySignature(key.getKeySignature());
	}

	private void deleteMidiFiles(String midiFilesPath) throws IOException{
		List<File> midiFiles = Files.list(new File(midiFilesPath).toPath()).map(p -> p.toFile()).collect(Collectors.toList());
		for (File file : midiFiles) {
			file.delete();
		}
	}
}

