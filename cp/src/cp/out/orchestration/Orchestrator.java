package cp.out.orchestration;


import java.io.IOException;
import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.Sequence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cp.combination.even.FourNoteEven;
import cp.combination.even.OneNoteEven;
import cp.combination.even.ThreeNoteEven;
import cp.combination.even.TwoNoteEven;
import cp.combination.uneven.FiveNoteSexTuplet;
import cp.combination.uneven.FourNoteSexTuplet;
import cp.combination.uneven.OneNoteUneven;
import cp.combination.uneven.SixNoteSexTuplet;
import cp.combination.uneven.ThreeNoteSexTuplet;
import cp.combination.uneven.ThreeNoteUneven;
import cp.combination.uneven.TwoNoteUneven;
import cp.generator.MusicProperties;
import cp.midi.MidiDevicesUtil;
import cp.model.melody.MelodyBlock;
import cp.out.orchestration.notetemplate.TwoNoteTemplate;
import cp.out.orchestration.orchestra.ClassicalOrchestra;
import cp.out.orchestration.orchestra.Orchestra;
import cp.out.orchestration.quality.Pleasant;
import cp.out.print.MusicXMLWriter;
import cp.out.print.ScoreUtilities;

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
	private ThreeNoteUneven threeNoteUneven;
	@Autowired
	private TwoNoteUneven twoNoteUneven;
	@Autowired
	private OneNoteUneven oneNoteUneven;
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
	private ClassicalOrchestra orchestra;
	@Autowired
	private Pleasant pleasant;

	public void orchestrate(List<MelodyBlock> melodyBlocks, String id) throws Exception {
		id = id + "_orch";
		MelodyBlock melodyBlock = melodyBlocks.get(0);
		orchestra.setFlute(melodyBlock.getMelodyBlockNotesWithRests());
		
		orchestra.setOboe(melodyBlocks.get(1).getMelodyBlockNotesWithRests());
		orchestra.setClarinet(orchestra.duplicate(orchestra.getFlute(), -12), orchestra.getClarinet()::removeMelodyNotInRange);
		orchestra.setBassoon(orchestra.duplicate(orchestra.getOboe(), -12), orchestra.getBassoon()::removeMelodyNotInRange);
		orchestra.setViolin1(orchestra.duplicate(orchestra.getFlute(), 0), pleasant.getInstrument(InstrumentName.VIOLIN_I.getName())::updateInQualityRange);
		orchestra.setCello(orchestra.duplicate(orchestra.getOboe(), -24), orchestra.getCello()::removeMelodyNotInRange);
		ChordOrchestration chordOrchestration = new ChordOrchestration(0, 48, 5);
//		map.put(new CelloSolo(0, 1), chordOrchestration.orchestrate(oneNoteEven::pos3, 12, C(4)));
//		map.put(new Doublebass(5, 1), chordOrchestration.orchestrate(twoNoteEven::pos13, 48, C(3), E(3)));
//		map.put(new ViolaSolo(1, 2), chordOrchestration.orchestrate(twoNoteEven::pos13, 24, C(5), E(5)));
//		map.put(new ViolinSolo(2, 2), chordOrchestration.orchestrate(twoNoteEven::pos34, 12, C(6), E(6), G(6)));
//		map.put(new Flute(3, 3), chordOrchestration.orchestrate(fourNoteEven::pos1234, 12, C(6), E(6), G(6), C(7)));
//		map.put(new Oboe(6, 3), chordOrchestration.orchestrate(fourNoteEven::pos1234, 12, G(5), Fsharp(5)));
//		map.put(new Clarinet(7, 3), chordOrchestration.orchestrate(threeNoteUneven::pos123, 12, G(5), Fsharp(5)));
//		map.put(new Bassoon(8, 3), chordOrchestration.orchestrate(sixNoteSexTuplet::pos123456, 12, C(4), E(4)));
//		orchestra.setOboe(chordOrchestration.orchestrate(new int[]{0,4},twoNoteTemplate::note011Repetition,threeNoteUneven::pos123, 12, twoNoteUneven::pos13, 12, Articulation.STACCATO));
//		map.put(new Trumpet(9, 4), chordOrchestration.orchestrate(new int[]{4,0},twoNoteTemplate::note01,twoNoteEven::pos13, 12));
		generateMusicXml(id, orchestra);
		writeMidi(id, orchestra);
	}
	
	private void generateMusicXml(String id, Orchestra orchestra) throws Exception{
		musicXMLWriter.createXML(id, orchestra.getOrchestra());
	}

	private void writeMidi(String id, Orchestra orchestra) throws InvalidMidiDataException, IOException {
			Sequence sequence = midiDevicesUtil.createSequence(orchestra.getOrchestra(), musicProperties.getTempo());
			midiDevicesUtil.write(sequence, "resources/midi/" + id + ".mid");
	}
	
}
