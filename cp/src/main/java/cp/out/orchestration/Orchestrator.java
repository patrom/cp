package cp.out.orchestration;


import cp.combination.even.FourNoteEven;
import cp.combination.even.OneNoteEven;
import cp.combination.even.ThreeNoteEven;
import cp.combination.even.TwoNoteEven;
import cp.combination.uneven.*;
import cp.config.InstrumentConfig;
import cp.generator.MusicProperties;
import cp.midi.MidiDevicesUtil;
import cp.model.note.Note;
import cp.out.instrument.Articulation;
import cp.out.orchestration.notetemplate.TwoNoteTemplate;
import cp.out.orchestration.orchestra.Orchestra;
import cp.out.orchestration.orchestra.StringOrchestra;
import cp.out.orchestration.quality.BrilliantWhite;
import cp.out.orchestration.quality.PleasantGreen;
import cp.out.play.InstrumentMapping;
import cp.out.print.MusicXMLWriter;
import cp.out.print.ScoreUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Orchestrator {
	
	private static Logger LOGGER = LoggerFactory.getLogger(Orchestrator.class);


	
	@Autowired
	private ScoreUtilities scoreUtilities;
	@Autowired
	private MusicXMLWriter musicXMLWriter;
	@Autowired
	private MidiDevicesUtil midiDevicesUtil;
	@Autowired
	private MusicProperties musicProperties;
	
	@Autowired
	private OneNoteEven oneNoteEven;
	@Autowired
	private TwoNoteEven twoNoteEven;
	@Autowired
	private ThreeNoteEven threeNoteEven;
	@Autowired
	private FourNoteEven fourNoteEven;
	
	@Autowired
	private ThreeNoteTriplet threeNoteUneven;
	@Autowired
	private TwoNoteTriplet twoNoteUneven;
	@Autowired
	private OneNoteTriplet oneNoteUneven;
	@Autowired
	private ThreeNoteSexTuplet threeNoteSexTuplet;
	@Autowired
	private FourNoteSexTuplet fourNoteSexTuplet;
	@Autowired
	private FiveNoteSexTuplet fiveNoteSexTuplet;
	@Autowired
	private SixNoteSexTuplet sixNoteSexTuplet;
	
	@Autowired
	private TwoNoteTemplate twoNoteTemplate;
	@Autowired
	private InstrumentConfig instrumentConfig;
	
	@Autowired
	private PleasantGreen pleasantGreen;
	@Autowired
	private BrilliantWhite brilliantWhite;

	public void orchestrate(String id) throws Exception {
		id = id + "_orch";

		//get voices and notes
		Map<Integer, List<Note>> notesPerVoice = new HashMap<>();
		//configuration instruments
		StringOrchestra orchestra = new StringOrchestra();
		orchestra.setNotesPerVoice(notesPerVoice);
		orchestra.setViolin(new MelodyOrchestrationBuilder()
				.setVoice(0)
				.setArticulation(Articulation.STACCATO)
				.setTechnical(null)
				.setDynamic(null)
				.setOrchestralQuality(pleasantGreen)
				.setOrchestralTechnique(null));

		//execute cofiguration
		orchestra.execute();

		//play vsl
		writeMidi(id, orchestra);
		//generate xml
		generateMusicXml(id, orchestra.getOrchestra());


//		for (int i = 0; i < 5; i++) {
//			Instrument instrumentToUpdate = orchestra.getRandomEmptyInstrument();
//			if(brilliantWhite.hasInstrument(instrumentToUpdate)){
//				Instrument instrumentRegister = brilliantWhite.getInstrument(instrumentToUpdate.getInstrumentName());
//				orchestra.setInstrument(orchestra.getClarinet(), instrumentToUpdate, instrumentRegister::updateInQualityRange);
//			};
//		}
//		Map<InstrumentMapping, List<Note>> melodiesForInstrument = new TreeMap<>();
//		for (Map.Entry<Integer,InstrumentMapping> entry : instrumentConfig.getInstruments().entrySet()) {
//			MelodyBlock melodyBlock = melodyBlocks.get(entry.getTimeLineKey());
//			InstrumentMapping instrumentMapping = entry.getValue();
//			melodiesForInstrument.put(instrumentMapping, melodyBlock.getMelodyBlockNotesWithRests());
//			for (InstrumentMapping dependantInstrumentMapping : instrumentMapping.getDependantInstruments()) {
//				List<Note> notes = dependantInstrumentMapping.duplicate(melodyBlock.getMelodyBlockNotesWithRests(), 0);
//				melodiesForInstrument.put(dependantInstrumentMapping, notes);
//			}
//		}
		
//		orchestra.setFlute(orchestra.duplicate(orchestra.getOboe()));
//		orchestra.setOboe(melodyBlocks.get(0).getMelodyBlockNotesWithRests());
//		orchestra.setClarinet(melodyBlocks.get(1).getMelodyBlockNotesWithRests());
//		orchestra.setClarinet(orchestra.duplicate(orchestra.getFlute()), pleasantGreen.getInstrument(InstrumentName.CLARINET.getName())::updateInQualityRange);
//		orchestra.setBassoon(orchestra.duplicate(orchestra.getOboe(), -12));
//
////		orchestra.setTrumpet(orchestra.duplicate(orchestra.getClarinet()), brilliantWhite.getInstrument(InstrumentName.TRUMPET.getName())::updateInQualityRange);
//
//		orchestra.setViolin1(orchestra.duplicate(orchestra.getClarinet()), pleasantGreen.getBasicInstrument(InstrumentName.VIOLIN_I.getName())::updateInQualityRange);
//		orchestra.setViola(orchestra.duplicate(orchestra.getClarinet()), pleasantGreen.getBasicInstrument(InstrumentName.VIOLA.getName())::updateInQualityRange);
//		orchestra.setCello(orchestra.duplicate(orchestra.getFlute(), 0), pleasantGreen.getInstrument(InstrumentName.CELLO.getName())::updateInQualityRange);
//		orchestra.setBass(notes);
//		ChordOrchestration chordOrchestration = new ChordOrchestration(0, 48, 5);
//		map.put(new CelloSolo(0, 1), chordOrchestration.orchestrate(oneNoteEven::pos3, 12, C(4)));
//		map.put(new DoubleBass(5, 1), chordOrchestration.orchestrate(twoNoteEven::pos13, 48, C(3), E(3)));
//		map.put(new ViolaSolo(1, 2), chordOrchestration.orchestrate(twoNoteEven::pos13, 24, C(5), E(5)));
//		map.put(new ViolinSolo(2, 2), chordOrchestration.orchestrate(twoNoteEven::pos34, 12, C(6), E(6), G(6)));
//		map.put(new Flute(3, 3), chordOrchestration.orchestrate(fourNoteEven::pos1234, 12, C(6), E(6), G(6), C(7)));
//		map.put(new Oboe(6, 3), chordOrchestration.orchestrate(fourNoteEven::pos1234, 12, G(5), Fsharp(5)));
//		map.put(new Clarinet(7, 3), chordOrchestration.orchestrate(threeNoteUneven::pos123, 12, G(5), Fsharp(5)));
//		map.put(new Bassoon(8, 3), chordOrchestration.orchestrate(sixNoteSexTuplet::pos123456, 12, C(4), E(4)));
//		orchestra.setOboe(chordOrchestration.orchestrate(new int[]{0,4},twoNoteTemplate::note011Repetition,threeNoteUneven::pos123, 12, twoNoteUneven::pos13, 12, Articulation.STACCATO));
//		map.put(new Trumpet(9, 4), chordOrchestration.orchestrate(new int[]{4,0},twoNoteTemplate::note01,twoNoteEven::pos13, 12));
//		generateMusicXml(id, melodiesForInstrument);
//		writeMidi(id, melodiesForInstrument);
	}
	
	private void generateMusicXml(String id, Map<InstrumentMapping, List<Note>> orchestra) throws Exception{
		Resource resource = new FileSystemResource("");
		String path = resource.getFile().getPath() + "cp/src/main/resources/xml/";
		musicXMLWriter.createXML(new FileOutputStream(path  + id + ".xml"), orchestra);
	}

	private void writeMidi(String id, Orchestra orchestra) throws InvalidMidiDataException, IOException {
			Sequence sequence = midiDevicesUtil.createSequence(orchestra.getOrchestra(), musicProperties.getTempo());
			Resource resource = new FileSystemResource("");
			midiDevicesUtil.write(sequence, resource.getFile().getPath()+ "cp/src/main/resources/orch/" + id + ".mid");
	}
	
}
