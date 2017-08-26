package cp;

import cp.config.InstrumentConfig;
import cp.config.OrchestralQualityConfig;
import cp.generator.MelodyGenerator;
import cp.generator.MusicProperties;
import cp.midi.*;
import cp.model.note.Note;
import cp.model.rhythm.Rhythm;
import cp.musicxml.XMLParser;
import cp.musicxml.parsed.ComplexElement;
import cp.musicxml.parsed.ElementWrapper;
import cp.out.arrangement.Arrangement;
import cp.out.arrangement.Pattern;
import cp.out.orchestration.orchestra.ClassicalOrchestra;
import cp.out.play.InstrumentMapping;
import cp.out.print.MusicXMLWriter;
import cp.out.print.ScoreUtilities;
import cp.variation.Embellisher;
import jm.music.data.Score;
import jm.util.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;
import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Import({DefaultConfig.class, VariationConfig.class})
public class PlayApplication extends JFrame implements CommandLineRunner{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PlayApplication.class.getName());
	
	@Autowired
	private MidiParser midiParser;
	@Autowired
	private MidiDevicesUtil midiDevicesUtil;
	@Autowired
	private MusicProperties musicProperties;
	@Autowired
	private Arrangement arrangement;
	@Autowired
	private ScoreUtilities scoreUtilities;
	@Autowired
	private  Embellisher embellisher;
	@Autowired
	private Rhythm rhythm;
	@Autowired
	private MelodyGenerator melodyGenerator;
	@Autowired
	private MusicXMLWriter musicXMLWriter;
	@Autowired
	private InstrumentConfig instrumentConfig;
	@Autowired
	private OrchestralQualityConfig orchestralQualityConfig;

	private final ClassicalOrchestra classicalOrchestra = new ClassicalOrchestra();
	
	public static void main(final String[] args) {
	 	SpringApplication app = new SpringApplication(PlayApplication.class);
	    app.setBannerMode(Mode.OFF);
	    app.run(args);
	}

	@Override
	public void run(String... arg0) throws Exception {
//		playMidiFilesOnKontaktFor();
//		MusicXMLParser parser = new MusicXMLParser("cp/src/main/resources/test.xml");
//		parser.parseMusicXML();
//		List<Note> notes = parser.getNotes();
//		notes.forEach(n -> System.out.println(n));
//		Map<String, List<Note>> notesPerInstrument = parser.getNotesPerInstrument();
//		notesPerInstrument.forEach((k,v) -> System.out.println(k + ":" + v));
//		parser.getNotesForVoice(2).forEach(n -> System.out.println(n));

		final Resource resource = new FileSystemResource("cp/src/main/resources/xml");
		File dir = resource.getFile();
		for (File xmlFile : dir.listFiles()) {
			XMLParser xmlParser = new XMLParser();
			xmlParser.setInstrumentConfig(instrumentConfig);
			xmlParser.startParsing(xmlFile.getPath());
			ComplexElement partList = xmlParser.getScore().getPartList();
			xmlParser.setInstrumentNames(partList);
			ArrayList<ElementWrapper> body = xmlParser.getScore().getBody();
			xmlParser.traverse(body);
			Map<String, List<Note>> notesPerInstrument = xmlParser.getNotesPerInstrument();

			List<MelodyInstrument> melodyInstruments = new ArrayList<>();

			for (Map.Entry<String,List<Note>> entry : notesPerInstrument.entrySet()) {
				int order = Character.getNumericValue(entry.getKey().charAt(1));
				InstrumentMapping instrumentMapping = instrumentConfig.getInstrumentMappingForScoreOrder(order);
				MelodyInstrument melodyInstrument = new MelodyInstrument();
				melodyInstrument.setInstrumentMapping(instrumentMapping);
				melodyInstrument.setNotes(entry.getValue());
				melodyInstruments.add(melodyInstrument);
			}

			//random orcherstration
//            List<Instrument> instrumentsConfigured = instrumentConfig.getOrchestraInstuments();
//			for (Map.Entry<String,List<Note>> entry : notesPerInstrument.entrySet()) {
//				int order = Character.getNumericValue(entry.getTimeLineKey().charAt(1));
//				OrchestralQuality ochestralQuality = orchestralQualityConfig.getOchestralQualityForVoice(order);
//                List<Instrument> basicInstruments = ochestralQuality.getBasicInstruments();
//                Collection<Instrument> instruments = CollectionUtils.intersection(instrumentsConfigured, basicInstruments);
//                Instrument instrument = RandomUtil.getRandomFromList(new ArrayList<>(instruments));
//
//				//getInstrumentMapping
//				InstrumentMapping instrumentMapping = instrumentConfig.getOrchestraInstrumentMappingForInstrument(instrument);
//				MelodyInstrument melodyInstrument = new MelodyInstrument();
//				melodyInstrument.setInstrumentMapping(instrumentMapping);
//				List<Note> notes = instrument.updateInQualityRange(entry.getValue());
//				melodyInstrument.setNotes(notes);
//				melodyInstruments.add(melodyInstrument);
//
//                instrumentsConfigured.remove(instrument);
//			}

			Score score = scoreUtilities.createScoreFromMelodyInstrument(melodyInstruments, xmlParser.getBpm());
            score.setTitle(xmlFile.getName());
            View.notate(score);

            Sequence sequence = midiDevicesUtil.createSequenceGeneralMidi(melodyInstruments, xmlParser.getBpm(), false);
            playOnKontakt(melodyInstruments, sequence, xmlParser.getBpm());
            Resource outResource = new FileSystemResource("");
            midiDevicesUtil.write(sequence, outResource.getFile().getPath()+ "cp/src/main/resources/orch/" + xmlFile.getName() + ".mid");
			Thread.sleep(15000);
		}
	}
	
	public void playMidiFilesOnKontaktFor() throws Exception {
//		final Resource resource = new FileSystemResource("cp/src/main/resources/orch");
		final Resource resource = new FileSystemResource("cp/src/main/resources/midi");
		File dir = resource.getFile();
		for (File midiFile : dir.listFiles()) {
			LOGGER.info(midiFile.getName());
			MidiInfo midiInfo = midiParser.readMidi(midiFile);
			List<MelodyInstrument> parsedMelodies = midiInfo.getMelodies();
//			musicProperties.setInstruments(Ensemble.getStringQuartet());
//			mapInstruments(parsedMelodies);
			//split
//			int size = parsedMelodies.size();
//			List<MelodyInstrument> melodies = new ArrayList<>(parsedMelodies.subList(0, size/2));
//			List<MelodyInstrument> harmonies = new ArrayList<>(parsedMelodies.subList(size/2, size));
//			arrangement.transpose(melodies.get(3).getNotes(), 12);
//			arrangement.transpose(melodies.get(4).getNotes(), 12);
//			arrangement.transpose(melodies.get(5).getNotes(), 12);
//			List<Integer> voicesForAccomp = new ArrayList<>();
//			voicesForAccomp.add(1);
//			voicesForAccomp.add(2);
//			voicesForAccomp.add(3);
//			List<MelodyInstrument> accompMelodies = filterAccompagnementMelodies(voicesForAccomp, melodies);
//			createAccompagnement(accompMelodies, melodies, midiInfo.getHarmonyPositionsForVoice(0));
			
//			int[] sounds = {0,12,24,36,48,60,72};
//			Integer[] texture = {1,1,1,1, 1 ,1,1,1};
//			Integer[] contour = {1,1,1, 1,  -1,-1,-1,-1,1,1,1, 1,  -1,-1,-1,-1,1,1,1, 1,  -1,-1,-1,-1,1,1,1, 1,  -1,-1,-1,-1,1,1,1, 1,  -1,-1,-1,-1,1,1,1, 1,  -1,-1,-1,-1,1,1,1, 1,  -1,-1,-1,-1,1,1,1, 1,  -1,-1,-1,-1,1,1,1, 1,  -1,-1,-1,-1,1,1,1, 1,  -1,-1,-1,-1,1,1,1, 1,  -1,-1,-1,-1};
//			List<Note> accompNotes = rhythm.getRhythm(midiInfo.getHarmonyPositions(), contour, 1, 1, 6);
//			List<Note> accompNotes = rhythm.getRhythm(midiInfo.getHarmonyPositions(), sounds, texture, contour, 1);
//			melodies.get(1).setNotes(accompNotes);
//			arrangement.transpose(melodies.get(1).getNotes(), -12);
			
//			embellish(melodies);
//			parsedMelodies = parsedMelodies.stream().filter(m -> m.getVoice() != 1 && m.getVoice() != 4).collect(Collectors.toList());
			Collections.sort(parsedMelodies);
			Collections.reverse(parsedMelodies);
			Score score = scoreUtilities.createScoreFromMelodyInstrument(parsedMelodies, midiInfo.getTempo());
			score.setTitle(midiFile.getName());
			View.notate(score);
			//			parsedMelodies.stream().filter(m -> m.getVoice() == 4).flatMap(m -> m.getNotes().stream()).forEach(n -> n.setPitch(n.getPitch() + 12));// for miroslav string quartet
//			parsedMelodies.stream().flatMap(m -> m.getNotes().stream()).forEach(n -> n.setPitch(n.getPitch() + 12));// for miroslav string quartet


//			playOnKontakt(parsedMelodies,  midiInfo.getTempo());

//			write(parsedMelodies , "resources/transform/" + midiFile.getName(), midiInfo.getTempo());
//			generateMusicXml(parsedMelodies, midiFile.getName());
			Thread.sleep(40000);
		}
	}

	private void embellish(List<MelodyInstrument> melodies) {
		for (int i = melodies.size() - 1; i >= 0; i--) {
			MelodyInstrument melodyInstrument = melodies.get(i);
			List<Note> embellishedNotes = embellisher.embellish(melodyInstrument.getNotes());
			melodyInstrument.setNotes(embellishedNotes);
		}
	}

//	private void createAccompagnement(List<MelodyInstrument> accompMelodies, List<MelodyInstrument> melodies, List<HarmonyPosition> harmonyPositions) {
//		for (MelodyInstrument melodyInstrument : accompMelodies) {
//			melodies.remove(melodyInstrument);
//			MelodyInstrument accomp = getAccomp(melodyInstrument.getNotes(), harmonyPositions, melodyInstrument.getVoice());
//			accomp.setInstrument(melodyInstrument.getInstrument());
//			melodies.add(accomp);
//		}
//		melodies.sort(Comparator.comparing(m -> m.getVoice()));
//	}
	
	private List<MelodyInstrument> filterAccompagnementMelodies(List<Integer> voicesForAccomp,
			List<MelodyInstrument> melodies){
		return melodies.stream().filter(m -> voicesForAccomp.contains(m.getVoice())).collect(Collectors.toList());
	}

	public List<List<Note>> chordal(List<Note> harmonyNotes) {
		ArrayList<List<Note>> list = new ArrayList<>();
		list.add(harmonyNotes);
		return list;
	}
	
	private MelodyInstrument getAccomp(List<Note> melodies, List<HarmonyPosition> harmonyPositions, int voice) {
		List<List<Note>> patterns = new ArrayList<>();
		harmonyPositions.forEach(h -> patterns.add(Pattern.repeat(12, 24)));
		List<Note> accompagnement = arrangement.getAccompagnement(melodies, harmonyPositions, patterns, 12);
		return new MelodyInstrument(accompagnement, voice);
	}

//	private List<MelodyInstrument> playOnInstrumentsAccomp(MidiInfo midiInfo) {
//		List<MelodyInstrument> playList = new ArrayList<>();
//		List<MelodyInstrument> melodies = midiInfo.getMelodies();
//
//		//accompagnement
//		List<HarmonyPosition> harmonyPositions = midiInfo.getHarmonyPositions();
//		Integer[] compPattern = {6,12,18,24};
//		List<Integer[]> compPatterns = new ArrayList<>();
//		compPatterns.add(compPattern);
//		Accompagnement[] compStrategy = {Accompagnement::arpeggio};
//		List<Note> accompagnement = arrangement.accompagnement(harmonyPositions, compPatterns, compStrategy);
//		MelodyInstrument accomp = new MelodyInstrument(accompagnement, melodies.size() + 1);
//		accomp.setInstrument(new Piano());
//		arrangement.transpose(accomp.getNotes(), -12);
//		accomp.addNotes(melodies.get(0).getNotes());//add bass notes
//		playList.add(accomp);
////		arrangement.applyFixedPattern(melodies.get(0).getNotes(), 6);
//		//harmony
//		melodies.get(1).setInstrument(new Piano());
//		melodies.get(2).setInstrument(new Piano());
//		arrangement.transpose(melodies.get(3).getNotes(), -12);
//		melodies.get(3).setInstrument(new Piano());
//		//melody
//		arrangement.transpose(melodies.get(7).getNotes(), -12);
//		melodies.get(7).setInstrument(new ViolinSolo());
//		playList.add(melodies.get(7));
//		return playList;
//	}
	
//	private void mapInstruments(List<MelodyInstrument> melodies) {
//		for (MelodyInstrument melody : melodies) {
//			melody.instrumentConfig.getInstrumentMappingForVoice(melody.getVoice());
//		}
//		Map<InstrumentMapping, List<Note>> orchestra = classicalOrchestra.getOrchestra();
//		for (int i = 0; i < melodies.size(); i++) {
//			MelodyInstrument melodyInstrument = melodies.get(i);
//			Optional<Instrument> instrument = orchestra.keySet().stream().filter(instr -> (instr.getVoice()) == melodyInstrument.getVoice()).findFirst();
//			if (instrument.isPresent()) {
//				melodyInstrument.setInstrument(instrument.get());
//			}else{
//				throw new IllegalArgumentException("Instrument for voice " + i + " is missing!");
//			}
//		}
//	}
	
	private void playOnKontakt(List<MelodyInstrument> melodies, Sequence sequence,
			int tempo) throws InvalidMidiDataException {
		midiDevicesUtil.playOnDevice(sequence, tempo, MidiDevicePlayer.KONTAKT);
	}

//	public void playMidiFilesOnKontaktFor(Instrument instrument) throws IOException, InvalidMidiDataException, InterruptedException {
//		File dir = new File("resources/midi");
//		for (File midiFile : dir.listFiles()) {
//			LOGGER.info(midiFile.getName());
//			MidiInfo midiInfo = midiParser.readMidi(midiFile);
//			List<MelodyInstrument> melodies = midiInfo.getMelodies();
//			melodies.forEach(m -> m.setInstrument(instrument));
//			playOnKontakt(melodies, midiInfo.getTempo());
//			Thread.sleep(8000);
//		}
//	}

	
//	private void write(List<MelodyInstrument> melodies, String outputPath, int tempo) throws InvalidMidiDataException, IOException{
//		Sequence seq;
//		if (containsInstrument(melodies, GeneralMidi.PIANO)) {
//			MelodyInstrument piano = mergeMelodies(melodies, 2, new Piano());
//			List<MelodyInstrument> otherInstruments = melodies.stream()
//				.filter(m -> !m.getInstrument().getGeneralMidi().equals(GeneralMidi.PIANO))
//				.collect(Collectors.toList());
//			List<MelodyInstrument> instruments = new ArrayList<>();
//			instruments.addAll(otherInstruments);
//			instruments.add(piano);
//			seq = midiDevicesUtil.createSequenceGeneralMidi(instruments, tempo);
//		}else{
//			seq = midiDevicesUtil.createSequenceGeneralMidi(melodies, tempo);
//		}
//		midiDevicesUtil.write(seq, outputPath);
//	}
//
//	private MelodyInstrument mergeMelodies(List<MelodyInstrument> melodies, int generalMidi, Instrument instrument) {
//		List<Note> notes = melodies.stream()
//				.filter(m -> m.getInstrument().getGeneralMidi().equals(GeneralMidi.PIANO))
//				.flatMap(m -> m.getNotes().stream())
//				.sorted()
//				.collect(Collectors.toList());
//		MelodyInstrument melodyInstrument = new MelodyInstrument(notes, instrument.getVoice());
//		melodyInstrument.setInstrument(instrument);
//		return melodyInstrument;
//	}
//
//	private boolean containsInstrument(List<MelodyInstrument> melodies, GeneralMidi gm) {
//		return melodies.stream().anyMatch(m -> m.getInstrument().getGeneralMidi().equals(gm));
//	}
//
//	private void generateMusicXml(List<MelodyInstrument> melodies, String id) throws Exception{
//		musicXMLWriter.generateMusicXML(melodies, id);
//	}

	
}
