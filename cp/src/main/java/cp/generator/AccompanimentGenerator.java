package cp.generator;

import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import cp.model.rhythm.Rhythm;
import cp.out.instrument.Instrument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccompanimentGenerator {

	@Autowired
	private Rhythm rhythm;
	
	public List<Note> fourFourSingleNote(List<Note> chordNotes, int minimum, Instrument instrument, int voice){
		Integer[] sounds = getFixedSounds(DurationConstants.WHOLE, minimum);
		List<Note> accompanimentNotes = rhythm.getRhythmRandomContourTexture(chordNotes, sounds, voice, 1);
		instrument.updateMelodyInRange(accompanimentNotes);
		return accompanimentNotes;
	}
	
	public List<Note> fourFourTexture(List<Note> chordNotes, int minimum, Instrument instrument, int maxTexture, int voice){
		Integer[] sounds = getFixedSounds(DurationConstants.WHOLE, minimum);
		List<Note> accompanimentNotes = rhythm.getRhythmRandomContourTexture(chordNotes, sounds, voice, maxTexture);
		instrument.updateMelodyInRange(accompanimentNotes);
		return accompanimentNotes;
	}
	
	public List<Note> measure(int measureLength, List<Note> chordNotes, int minimum, Instrument instrument, int maxTexture, int voice){
		Integer[] sounds = getFixedSounds(measureLength, minimum);
		List<Note> accompanimentNotes = rhythm.getRhythmRandomContourTexture(chordNotes, sounds, voice, maxTexture);
		instrument.updateMelodyInRange(accompanimentNotes);
		return accompanimentNotes;
	}
	
	private Integer[] getFixedSounds(int length, int minimum){
		Integer[] sounds = new Integer[length/minimum];
		for (int i = 0; i < sounds.length; i++) {
			sounds[i] = minimum * i;
		}
		return sounds;
	}
	
}
