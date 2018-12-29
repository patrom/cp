package cp.out.instrument;

import cp.midi.GeneralMidi;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.out.instrument.register.InstrumentRegister;
import cp.util.RandomUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Instrument {

	protected GeneralMidi generalMidi;
	//musicXML properties
	protected String instrumentName;
	protected String instrumentSound;
	protected String virtualLibrary = "Sibelius 7 Sounds";
	protected String virtualName;
	protected String clef = "G";
	private InstrumentRegister instrumentRegister = new InstrumentRegister();
	protected int order;
	protected InstrumentGroup instrumentGroup;

	public Instrument() {
	}
	
	public void updateNotesInRange(List<Note> notes){
        for (Note note : notes) {
            while (note.getPitch() > getHighest()) {
                note.transposeOctaveDown();
            }
            while (note.getPitch() < getLowest()) {
                note.transposeOctaveUp();
            }
        }
    }

    public void updateNoteInRange(Note note){
        while (note.getPitch() > getHighest()) {
            note.transposeOctaveDown();
        }
        while (note.getPitch() < getLowest()) {
            note.transposeOctaveUp();
        }
    }
	
	public List<Note> removeMelodyNotInRange(List<Note> notes){
		return notes.stream()
				.filter(n -> !n.isRest() && inRange(n.getPitch()))//TODO map to rest removed note???
				.collect(toList());
	}

    public boolean hasNotesOutOfRange(List<Note> notes){
        return notes.stream().anyMatch(n -> !n.isRest() && !inRange(n.getPitch()));
    }
	
	public List<Note> updateInQualityRange(List<Note> notes){
		List<Note> rangeNotes = new ArrayList<>();
		for (Note note : notes) {
			if (note.isRest()) {
				rangeNotes.add(note);
				continue;
			}
			//in range
			if (inRange(note.getPitch())) {
				rangeNotes.add(note);
			}else{
				//note is higher than range
				if (note.getPitch() > getHighest()) {
					int pitchOctaveDown = note.getPitch() - 12;
					while (pitchOctaveDown > getHighest()) {
						pitchOctaveDown = pitchOctaveDown - 12;
					}
					if (!inRange(pitchOctaveDown) && pitchOctaveDown < getLowest()) {
						pitchOctaveDown = pitchOctaveDown + 12;
					}
					Note copy = note.clone();
					copy.setPitch(pitchOctaveDown);
					copy.setOctave((int) Math.ceil(pitchOctaveDown/12));
					rangeNotes.add(copy);
				//note is lower than range
				} else {
					int pitchOctaveUp = note.getPitch() + 12;
					while (pitchOctaveUp < getLowest()) {
						pitchOctaveUp = pitchOctaveUp + 12;
					}
					if (!inRange(pitchOctaveUp) && pitchOctaveUp > getHighest()) {
						pitchOctaveUp = pitchOctaveUp - 12;
					}
					Note copy = note.clone();
					copy.setPitch(pitchOctaveUp);
					copy.setOctave((int) Math.ceil(pitchOctaveUp/12));
					rangeNotes.add(copy);
				}
			}
		}
		return rangeNotes;
	}

	public boolean inRange(int pitch) {
		return pitch >= getLowest() && pitch <= getHighest();
	}
	
	public int pickRandomNoteFromRange(Scale scale){
		int max = instrumentRegister.getHigh() + 1;
		int min = instrumentRegister.getLow();
		int pitch = RandomUtil.randomInt(min, max);
		if (scale == null) {
			return pitch;
		}else{
			int octave = pitch / 12;
			int pc = pitch - (12 * octave);
			while (!scale.contains(pc)) {
				pitch = RandomUtil.randomInt(min, max);
				octave = pitch / 12;
				pc = pitch - (12 * octave);
			}
			return pitch;
		}
	}
	
	public int pickRandomOctaveFromRange(){
		int max = instrumentRegister.getHigh() + 1;
		int min = instrumentRegister.getLow();
		int pitch = RandomUtil.randomInt(min, max);
		return pitch / 12;
	}
	
	public Scale filterScale(Scale scale) {
        if((getHighest() - getLowest()) <= 12){
            int pc1 = getLowest() % 12;
            int pc2 = getHighest() % 12;
            int[] sub;
            if (pc1 < pc2) {
                sub = Arrays.stream(scale.getPitchClasses()).filter(i ->  i >= pc1 && i <= pc2).toArray();
            } else {
                sub = Arrays.stream(scale.getPitchClasses()).filter(i ->  i <= pc2 || i >= pc1).toArray();
            }
            return new Scale(sub);
        }
		return scale;
    }

	public int getLowest() {
		return instrumentRegister.getLow();
	}
	public void setLowest(int lowest) {
		this.instrumentRegister.setLow(lowest);
	}

	public int getHighest() {
		return instrumentRegister.getHigh();
	}
	public void setHighest(int highest) {
		this.instrumentRegister.setHigh(highest);
	}
	public GeneralMidi getGeneralMidi() {
		return generalMidi;
	}
	public void setGeneralMidi(GeneralMidi generalMidi) {
		this.generalMidi = generalMidi;
	}
	public String getInstrumentName() {
		return instrumentName;
	}
	public void setInstrumentName(String instrumentName) {
		this.instrumentName = instrumentName;
	}
	public String getInstrumentSound() {
		return instrumentSound;
	}
	public void setInstrumentSound(String instrumentSound) {
		this.instrumentSound = instrumentSound;
	}
	public String getVirtualLibrary() {
		return virtualLibrary;
	}
	public void setVirtualLibrary(String virtualLibrary) {
		this.virtualLibrary = virtualLibrary;
	}
	public String getVirtualName() {
		return virtualName;
	}
	public void setVirtualName(String virtualName) {
		this.virtualName = virtualName;
	}
	public String getClef() {
		return clef;
	}
	public void setClef(String clef) {
		this.clef = clef;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((instrumentName == null) ? 0 : instrumentName.hashCode());
		result = prime * result
				+ ((instrumentSound == null) ? 0 : instrumentSound.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Instrument other = (Instrument) obj;
		if (instrumentName == null) {
			if (other.instrumentName != null)
				return false;
		} else if (!instrumentName.equals(other.instrumentName))
			return false;
		if (instrumentSound == null) {
			if (other.instrumentSound != null)
				return false;
		} else if (!instrumentSound.equals(other.instrumentSound))
			return false;
		return true;
	}

	public InstrumentRegister getInstrumentRegister() {
		return instrumentRegister;
	}

	public void setInstrumentRegister(InstrumentRegister instrumentRegister) {
		this.instrumentRegister = instrumentRegister;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public InstrumentGroup getInstrumentGroup() {
		return instrumentGroup;
	}

	public void setInstrumentGroup(InstrumentGroup instrumentGroup) {
		this.instrumentGroup = instrumentGroup;
	}

}
