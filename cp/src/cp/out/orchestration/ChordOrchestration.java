package cp.out.orchestration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cp.combination.RhythmCombination;
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
import cp.model.melody.CpMelody;
import cp.model.note.Note;
import cp.model.note.NoteBuilder;
import cp.util.RandomUtil;
import net.sourceforge.jFuzzyLogic.fcl.FclParser.main_return;

@Component
public class ChordOrchestration {
	
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
	
	public List<Note> getRhythmNotes(RhythmCombination rhythmCombination, int start, int length, int beat) {
		List<Note> rhythmNotes = new ArrayList<>();
		int end = start + beat;
		while (end <= length) {
			List<Note> rNotes = rhythmCombination.getNotes(beat);
			for (Note note : rNotes) {
				note.setPosition(note.getPosition() + start);
				rhythmNotes.add(note);
			}
			start = end;
			end = start + beat;
		}
		return rhythmNotes;
	}
	
	public List<Note> orchestrateChord(List<Note> rhythmNotes, List<Note> chordNotes){
		List<Note> notes = new ArrayList<>();
		int size = rhythmNotes.size();
		for (int i = 0; i < size; i++) {
			Note chordNote = getNextChordNote(chordNotes, i);
			Note rhythmNote = rhythmNotes.get(i).clone();
			rhythmNote.setPitch(chordNote.getPitch());
			rhythmNote.setPitchClass(chordNote.getPitchClass());
			rhythmNote.setOctave(chordNote.getOctave());
			notes.add(rhythmNote);
		}
		return notes;
	}
	
	private Note getNextChordNote(List<Note> chordNotes, int i) {
		return chordNotes.get(i % chordNotes.size());
	}

}
