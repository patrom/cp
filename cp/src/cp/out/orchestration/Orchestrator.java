package cp.out.orchestration;

import static cp.model.note.NoteName.C;
import static cp.model.note.NoteName.E;

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

import cp.combination.even.TwoNoteEven;
import cp.midi.MidiDevicesUtil;
import cp.model.note.Note;
import cp.out.instrument.Instrument;
import cp.out.instrument.strings.Cello;
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
	private TwoNoteEven twoNoteEven;
	Map<Instrument, List<Note>> map = new HashMap<>();
	
	public void orchestrate() throws Exception {
		ChordOrchestration chordOrchestration = new ChordOrchestration(0,48);
		List<Note> notes = chordOrchestration.orchestrate(twoNoteEven::pos12, 24, C(5), E(5));
		map.put(new Cello(0, 1), notes);
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
