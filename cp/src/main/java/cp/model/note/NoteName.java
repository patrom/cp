package cp.model.note;

import static cp.model.note.NoteBuilder.note;

public class NoteName {

	public static Note C(int octave){
		return getNote(octave, 0);
	}
	
	public static Note Csharp(int octave){
		return getNote(octave, 1);
	}
	
	public static Note D(int octave){
		return getNote(octave, 2);
	}
	
	public static Note Eflat(int octave){
		return getNote(octave, 3);
	}
	
	public static Note E(int octave){
		return getNote(octave, 4);
	}
	
	public static Note F(int octave){
		return getNote(octave, 5);
	}
	
	public static Note Fsharp(int octave){
		return getNote(octave, 6);
	}
	
	public static Note G(int octave){
		return getNote(octave, 7);
	}
	
	public static Note Gsharp(int octave){
		return getNote(octave, 8);
	}
	
	public static Note A(int octave){
		return getNote(octave, 9);
	}
	
	public static Note Bflat(int octave){
		return getNote(octave, 10);
	}
	
	public static Note B(int octave){
		return getNote(octave, 11);
	}
	
	private static Note getNote(int octave, int pitchClass){
		int pitch = octave * 12;
		return note().pitch(pitch + pitchClass).octave(octave).pc(pitchClass).build();
	}
}
