package cp.model.note;

import static cp.model.note.NoteBuilder.note;

public class NoteName {

	public static Note C(int octave){
		return getNote(octave, 0);
	}
	
	public static Note E(int octave){
		return getNote(octave, 4);
	}
	
	private static Note getNote(int octave, int pitchClass){
		int pitch = octave * 12;
		return note().pitch(pitch).ocatve(octave).pc(pitch + pitchClass).build();
	}
}
