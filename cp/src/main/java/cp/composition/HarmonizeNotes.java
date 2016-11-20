package cp.composition;

import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import cp.musicxml.MusicXMLParser;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static cp.model.note.NoteBuilder.note;
import static cp.model.rhythm.DurationConstants.EIGHT;
import static cp.model.rhythm.DurationConstants.HALF;
import static java.util.stream.Collectors.toList;

@Component
public class HarmonizeNotes {

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
	
	public List<Note> getFileToHarmonize() {
		try {
			MusicXMLParser parser  = new MusicXMLParser("cp/src/main/resources/harmonize/kyrieComplete.xml");
			parser.parseMusicXML();
			Map<String, List<Note>> notesPerInstrument = parser.getNotesPerInstrument();
			String instrument = "P1";
			return notesPerInstrument.entrySet().stream()
					.flatMap(entry -> entry.getValue().stream())
					.filter(n -> n.getInstrument().startsWith(instrument))
					.collect(toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
       return Collections.EMPTY_LIST;
	}
}
