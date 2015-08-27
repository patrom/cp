package cp.model.melody.pitchspace;

import java.util.List;

import cp.model.melody.HarmonicMelody;
import cp.model.note.Note;
import cp.out.instrument.Instrument;
import cp.util.RandomUtil;

public abstract class PitchSpace {

	protected Integer[] octaveLowestPitchClassRange;
	protected int octaveHighestPitchClass;
	protected int size;
	protected List<HarmonicMelody> harmonicMelodies;
	protected List<Instrument> instruments;

	public PitchSpace(Integer[] octaveLowestPitchClassRange, List<Instrument> instruments) {
		this.octaveLowestPitchClassRange = octaveLowestPitchClassRange;
		this.octaveHighestPitchClass = randomIntRange();
		this.instruments = instruments;
	}

	public abstract void translateToPitchSpace();
	
	private int randomIntRange() {
		return RandomUtil.randomInt(octaveLowestPitchClassRange[0], octaveLowestPitchClassRange[octaveLowestPitchClassRange.length - 1] + 1);
	}

	protected void setPitchLowestNote() {
		HarmonicMelody harmonicMelody = getHarmonicMelody(0);
		Instrument instrument = getInstrument(0);
		int octave = instrument.getLowest() / 12;
		int pitch = harmonicMelody.getHarmonyNote().getPitchClass() + (octave * 12);
		while (pitch < instrument.getLowest()) {
			pitch = pitch + 12;
			octave = octave + 1;
		}
		int addOctave = randomIntRange();
		if (addOctave > 0) {
			pitch = pitch + (addOctave * 12);
			octave = octave + addOctave;
		}
		Note harmonyNote = harmonicMelody.getHarmonyNote();
		harmonyNote.setPitch(pitch);
		harmonyNote.setOctave(octave);
		harmonicMelody.updateMelodyPitchesToHarmonyPitch();
	}
	
	protected Note getNote(int voice){
		return harmonicMelodies.stream().filter(h -> h.getVoice() == voice).map(h -> h.getHarmonyNote()).findFirst().get();
	}
	
	protected HarmonicMelody getHarmonicMelody(int voice){
		return harmonicMelodies.stream().filter(h -> h.getVoice() == voice).findFirst().get();
	}
	
	protected Instrument getInstrument(int voice){
		return instruments.stream().filter(i -> i.getVoice() == voice).findFirst().get();
	}
	
	protected void setUniformPitchSpace(){
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
	
	public Integer[] getOctaveHighestPitchClassRange() {
		return octaveLowestPitchClassRange;
	}
	
	public List<Instrument> getInstruments() {
		return instruments;
	}
	
	public int getOctaveHighestPitchClass() {
		return octaveHighestPitchClass;
	}
	
	public void setHarmonicMelodies(List<HarmonicMelody> notes) {
		this.harmonicMelodies = notes;
		this.size = notes.size();
	}

}
