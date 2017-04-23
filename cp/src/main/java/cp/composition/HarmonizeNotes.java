package cp.composition;

import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import cp.musicxml.XMLParser;
import cp.musicxml.parsed.ComplexElement;
import cp.musicxml.parsed.ElementWrapper;
import cp.out.play.InstrumentConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static cp.model.note.NoteBuilder.note;
import static cp.model.rhythm.DurationConstants.EIGHT;
import static cp.model.rhythm.DurationConstants.HALF;

@Component
public class HarmonizeNotes {

	@Autowired
	private InstrumentConfig instrumentConfig;

	public List<Note> getMelodieToHarmonize() {
		List<Note> notes = new ArrayList<>();
		notes.add(note().pos(0).rest().len(HALF + EIGHT).len(HALF + EIGHT).build());
		notes.add(note().pos(HALF + EIGHT).pc(5).len(DurationConstants.EIGHT).build());
		notes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER).pc(4).len(DurationConstants.SIXTEENTH).build());
		notes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER + DurationConstants.SIXTEENTH).pc(8).len(DurationConstants.SIXTEENTH).build());
		notes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER + DurationConstants.EIGHT).pc(11).len(DurationConstants.SIXTEENTH).build());
		notes.add(note().pos(DurationConstants.HALF + DurationConstants.QUARTER + DurationConstants.EIGHT + DurationConstants.SIXTEENTH).pc(10).len(DurationConstants.SIXTEENTH).build());
		
		notes.add(note().pos(DurationConstants.WHOLE).pc(2).len(3).len(DurationConstants.QUARTER).build());
		notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.QUARTER).pc(10).len(DurationConstants.QUARTER).build());
		notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.HALF).pc(11).len(DurationConstants.EIGHT).build());
		notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.HALF + DurationConstants.EIGHT).pc(2).len(DurationConstants.EIGHT).build());
		notes.add(note().pos(DurationConstants.WHOLE + DurationConstants.HALF + DurationConstants.QUARTER).pc(11).len(DurationConstants.QUARTER).build());
		return notes;
	}
	
	public Map<String, List<Note>> getFileToHarmonize() {
		try {
			XMLParser xmlParser = new XMLParser();
			xmlParser.setInstrumentConfig(instrumentConfig);
			xmlParser.startParsing("cp/src/main/resources/harmonize/kyrie3.xml");
			ComplexElement partList = xmlParser.getScore().getPartList();
			xmlParser.setInstrumentNames(partList);
			ArrayList<ElementWrapper> body = xmlParser.getScore().getBody();
			xmlParser.traverse(body);
			return xmlParser.getNotesPerInstrument();
		} catch (IOException | XMLStreamException e) {
			e.printStackTrace();
		}
		return Collections.emptyMap();
	}
}
