package cp.out.instrument.register;

import java.util.List;
import java.util.Random;

import cp.model.note.Note;
import cp.model.note.Scale;
import cp.out.instrument.Articulation;
import cp.out.instrument.Instrument;

public abstract class InstrumentRegister {

	protected Random random = new Random();
	protected Note low;
	protected Note high;
	protected Instrument instrument;
	protected Articulation articulation;
	protected String intensity;
	
	public int pickRandomNoteFromRange(Scale scale){
		int max = high.getPitch();
		int min = low.getPitch();
		int pitch = random.nextInt((max - min) + 1) + min;
		if (scale == null) {
			return pitch;
		}else{
			int octave = pitch / 12;
			int pc = pitch - (12 * octave);
			while (!scale.contains(pc)) {
				pitch = random.nextInt((max - min) + 1) + min;
				octave = pitch / 12;
				pc = pitch - (12 * octave);
			}
			return pitch;
		}
	}
	
	public int pickRandomOctaveFromRange(){
		int max = high.getPitch();
		int min = low.getPitch();
		int pitch = random.nextInt((max - min) + 1) + min;
		return pitch / 12;
	}
	
	public void updateMelodyBetween(List<Note> notes){
		for (Note note : notes) {
			while (note.getPitch() < low.getPitch()) {
				note.transposePitch(12);
			}
			while (note.getPitch() > high.getPitch()) {
				note.transposePitch(-12);
			}
		}
	}
	
	public static void main(String[] args) {
		System.out.println(new ViolaPleasant().pickRandomNoteFromRange(Scale.MAJOR_SCALE));
	}
	
	public Instrument getInstrument() {
		return instrument;
	}
}
