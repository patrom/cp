package cp.out.orchestration;

import static cp.model.note.NoteName.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import cp.model.note.Note;
import cp.model.note.NoteName;
import cp.out.instrument.Instrument;
import cp.out.instrument.brass.Trombone;
import cp.out.instrument.brass.Trumpet;
import cp.out.instrument.strings.CelloSolo;
import cp.out.instrument.strings.Doublebass;
import cp.out.instrument.strings.ViolaSolo;
import cp.out.instrument.strings.ViolinSolo;
import cp.out.instrument.woodwinds.Bassoon;
import cp.out.instrument.woodwinds.Clarinet;
import cp.out.instrument.woodwinds.Flute;
import cp.out.instrument.woodwinds.Oboe;
import cp.out.print.MusicXMLWriter;
import cp.out.print.ScoreUtilities;
import cp.out.print.note.Key;

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
	private Key C;
	
	Map<Instrument, List<Note>> map = new HashMap<>();
	
	public void orchestrate() throws Exception {
		musicProperties.setKey(C);
		musicProperties.setKeySignature(C.getKeySignature());
		musicProperties.setNumerator(4);
		musicProperties.setDenominator(4);
		ChordOrchestration chordOrchestration = new ChordOrchestration(0,48);
//		map.put(new CelloSolo(0, 1), chordOrchestration.orchestrate(oneNoteEven::pos3, 12, C(4)));
//		map.put(new Doublebass(5, 1), chordOrchestration.orchestrate(twoNoteEven::pos13, 48, C(3), E(3)));
//		map.put(new ViolaSolo(1, 2), chordOrchestration.orchestrate(twoNoteEven::pos13, 24, C(5), E(5)));
//		map.put(new ViolinSolo(2, 2), chordOrchestration.orchestrate(twoNoteEven::pos34, 12, C(6), E(6), G(6)));
//		map.put(new Flute(3, 3), chordOrchestration.orchestrate(fourNoteEven::pos1234, 12, C(6), E(6), G(6), C(7)));
//		map.put(new Oboe(6, 3), chordOrchestration.orchestrate(fourNoteEven::pos1234, 12, G(5), Fsharp(5)));
//		map.put(new Clarinet(7, 3), chordOrchestration.orchestrate(threeNoteUneven::pos123, 12, G(5), Fsharp(5)));
//		map.put(new Bassoon(8, 3), chordOrchestration.orchestrate(sixNoteSexTuplet::pos123456, 12, C(4), E(4)));
//		map.put(new Trombone(4, 4), chordOrchestration.orchestrate(oneNoteEven::pos1, 48, C(5)));
		map.put(new Trumpet(9, 4), chordOrchestration.orchestrate(oneNoteUneven::pos3, 12, C(5), D(5)));
		String id = "test";
		int tempo = 100;
		generateMusicXml(id);
		writeMidi(id, tempo);
	}

	
	private void generateMusicXml(String id) throws Exception{
		musicXMLWriter.createXML(id, map);
	}

	private void writeMidi(String id, double tempo) throws InvalidMidiDataException, IOException {
		for (Entry<Instrument, List<Note>> entry: map.entrySet()) {
			Sequence sequence = midiDevicesUtil.createSequenceNotes(entry.getValue(), entry.getKey());
			midiDevicesUtil.write(sequence, "resources/midi/" + id + ".mid");
		}
	}
}
