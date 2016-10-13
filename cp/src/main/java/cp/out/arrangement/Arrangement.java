package cp.out.arrangement;

import cp.midi.HarmonyPosition;
import cp.model.note.Note;
import cp.model.note.NoteBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static cp.model.note.NoteBuilder.note;

@Component
public class Arrangement {
	
	public List<Note> applyFixedPattern(List<Note> notes, int rhythmicLength){
		List<Note> rhythmicNotes = new ArrayList<>();
		for (Note note : notes) {
			int size = note.getLength()/rhythmicLength;
			for (int i = 0; i < size; i++) {
				rhythmicNotes.add(note().pos(note.getPosition() + (i * rhythmicLength))
										.len(rhythmicLength)
										.pitch(note.getPitch())
										.voice(note.getVoice())
										.build());
			}
		}
		return rhythmicNotes;
	}
	
	public void transpose(List<Note> notes, int step){
		notes.forEach(n -> n.setPitch(n.getPitch() + step));
	}
	
	public List<Note> applyFixedPattern(List<Note> notes, int[] pattern){
		List<Note> rhythmicNotes = new ArrayList<>();
		for (Note note : notes) {
			rhythmicNotes.addAll(applyPattern(note, pattern));
		}
		return rhythmicNotes;
	}
	
	public List<Note> applyPattern(Note note, int[] rhythmicPattern){
		List<Note> rhythmicNotes = new ArrayList<>();
		int patternLength = 0;
		for (int i = 0; i < rhythmicPattern.length - 1; i++) {
			patternLength = patternLength + rhythmicPattern[i];
			if (note.getLength() >= patternLength) {
				rhythmicNotes.add(note().pos(note.getPosition() + patternLength)
						.len(rhythmicPattern[i + 1] - rhythmicPattern[i])
						.pitch(note.getPitch())
						.voice(note.getVoice())
						.build());
			} else {
				break;
			}
		}
		return rhythmicNotes;
	}
	
	public List<Note> accompagnement(List<HarmonyPosition> harmonyPositions, List<Integer[]> compPatterns, Accompagnement[] compStrategy) {
		List<Note> transformedList = new ArrayList<>();
		int j = 0;
		for (HarmonyPosition harmonyPosition : harmonyPositions) {
			Accompagnement compStr = compStrategy[j % compStrategy.length];
			List<List<Note>> rhythmNotes = compStr.applyAccompagnement(harmonyPosition.getNotes());
			Integer[] compPattern = compPatterns.get(j % compPatterns.size());
			for (int i = 0; i < compPattern.length - 1; i++) {
			List<Note> compNotes = rhythmNotes.get(i % rhythmNotes.size());
			for (Note note : compNotes) {
				Note compNote = NoteBuilder.note()
						.pos(harmonyPosition.getPosition() + compPattern[i])
						.pc(note.getPitchClass())
						.pitch(note.getPitch())
						.len(compPattern[i + 1] - compPattern[i]).build();
				transformedList.add(compNote);
				}
			}
			j++;
		}
		transformedList.sort(Comparator.comparing(Note::getPosition));
		return transformedList;
	}
	
	public List<Note> getAccompagnement(List<Note> melodyNotes, List<HarmonyPosition> harmonyPositions, List<List<Note>> patterns, int minimumLengthNote){
		List<Note> pattern = updatePatternPositions(harmonyPositions, patterns);
		return createAccompForPattern(melodyNotes, pattern, minimumLengthNote);
	}
	
	public List<Note> updatePatternPositions(List<HarmonyPosition> harmonyPositions, List<List<Note>> patterns){
		List<Note> melodyPattern = new ArrayList<>();
		for (int i = 0; i < patterns.size(); i++) {
			List<Note> notes = patterns.get(i);
			HarmonyPosition harmonyPosition = harmonyPositions.get(i);
			for (Note note : notes) {
				Note newNote = note.clone();
				newNote.setPosition(note.getPosition() + harmonyPosition.getPosition());
				melodyPattern.add(newNote);
			}
		}
		return melodyPattern;
	}
	
	public List<Note> createAccompForPattern(List<Note> melodyNotes, List<Note> compPattern, int minimumLengthNote){
		int position = 0;
		int tempPosition = 0;
		List<Note> notes = new ArrayList<>();
		for (Note note : melodyNotes) {
			while ((position + note.getLength()) > tempPosition) {
				Optional<Note> patternNote = getPatternNote(compPattern, tempPosition);
				if (patternNote.isPresent()) {
					Note newNote = patternNote.get().clone();
					newNote.setPitch(note.getPitch());
					newNote.setPitchClass(note.getPitchClass());
					newNote.setOctave(note.getOctave());
					newNote.setLength(minimumLengthNote);
					notes.add(newNote);
				}
				tempPosition = tempPosition + minimumLengthNote;
			}
			position = tempPosition;
		}
		return notes;
	}

	private Optional<Note> getPatternNote(List<Note> compPattern, int position) {
		return compPattern.stream().filter(note -> note.getPosition() == position).findFirst();
	}
	
}
