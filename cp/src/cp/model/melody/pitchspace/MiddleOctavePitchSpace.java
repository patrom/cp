package cp.model.melody.pitchspace;

import java.util.List;

import cp.model.melody.HarmonicMelody;
import cp.model.note.Note;
import cp.out.instrument.Instrument;

public class MiddleOctavePitchSpace extends PitchSpace {
	
	public MiddleOctavePitchSpace(Integer[] octaveHighestPitchClassRange,
			List<Instrument> instruments) {
		super(octaveHighestPitchClassRange, instruments);
	}

	@Override
	public void translateToPitchSpace() {
		setPitchLowestNote();
		for (int i = 1; i < size; i++) {
			Note prevNote = getNote(i - 1);
			HarmonicMelody harmonicMelody = getHarmonicMelody(i);
			Note note = harmonicMelody.getHarmonyNote();
			int prevPc = prevNote.getPitchClass();
			int pc = note.getPitchClass();
			int pitch;
			int octave;
			if (prevPc > pc) {
				pitch = prevNote.getPitch() + (12 + (pc - prevPc));
				octave = prevNote.getOctave() + 1;
			} else {
				pitch = prevNote.getPitch() + (pc - prevPc);
				octave = prevNote.getOctave();
			}
			if (i == 2) {
				pitch = pitch + 12;
				octave++;
			}
			Instrument instrument = getInstrument(i);
			while (pitch < instrument.getLowest()) {
				pitch = pitch + 12;
				octave++;
			}
			while (pitch > instrument.getHighest()) {
				pitch = pitch - 12;
				octave--;
			}
			note.setPitch(pitch);
			note.setOctave(octave);
			harmonicMelody.updateMelodyPitchesToHarmonyPitch();
		}
	}

}
