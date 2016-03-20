package cp.generator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cp.model.note.Note;
import cp.model.rhythm.Rhythm;
import cp.out.instrument.Instrument;

@Component
public class AccompanimentGenerator {

	@Autowired
	private Rhythm rhythm;
	
	public List<Note> fourFourSingleNote(List<Note> chordNotes, int minimum, Instrument instrument){
		Integer[] sounds = getFixedSounds(48, minimum);
		List<Note> accompanimentNotes = rhythm.getRhythmRandomContourTexture(chordNotes, sounds, instrument.getVoice(), 1);
		instrument.updateMelodyInRange(accompanimentNotes);
		return accompanimentNotes;
	}
	
	public List<Note> fourFourTexture(List<Note> chordNotes, int minimum, Instrument instrument, int maxTexture){
		Integer[] sounds = getFixedSounds(48, minimum);
		List<Note> accompanimentNotes = rhythm.getRhythmRandomContourTexture(chordNotes, sounds, instrument.getVoice(), maxTexture);
		instrument.updateMelodyInRange(accompanimentNotes);
		return accompanimentNotes;
	}
	
	public List<Note> measure(int measureLength, List<Note> chordNotes, int minimum, Instrument instrument, int maxTexture){
		Integer[] sounds = getFixedSounds(measureLength, minimum);
		List<Note> accompanimentNotes = rhythm.getRhythmRandomContourTexture(chordNotes, sounds, instrument.getVoice(), maxTexture);
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
