package cp.out.instrument;

import static cp.model.note.NoteBuilder.note;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import cp.midi.GeneralMidi;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.out.instrument.keyswitch.KeySwitch;
import cp.out.instrument.register.InstrumentRegister;
import cp.util.RandomUtil;

public class Instrument implements Comparable<Instrument> {

	protected int voice;
//	protected int lowest;
//	protected int highest;
	protected int channel;
	protected KeySwitch keySwitch;
	protected GeneralMidi generalMidi;
	//musicXML properties
	protected String instrumentName;
	protected String instrumentSound;
	protected String virtualLibrary = "Sibelius 7 Sounds";
	protected String virtualName;
	protected String clef = "G";
	private InstrumentRegister instrumentRegister = new InstrumentRegister();
	
	public Instrument(int voice, int channel) {
		this.voice = voice;
		this.channel = channel;
	}
	
	public Instrument() {
	}
	
	public int getArticulation(Articulation articulation) {
		if (hasKeySwitch()) {
			return keySwitch.getArticulation(articulation);
		} else {
			return 0;
		}
	}
	
	public void updateMelodyBetween(List<Note> notes){
		for (Note note : notes) {
			while (note.getPitch() < getLowest()) {
				note.transposePitch(12);
			}
			while (note.getPitch() > getHighest()) {
				note.transposePitch(-12);
			}
		}
	}
	
	public List<Note> removeMelodyNotBetween(List<Note> notes){
		return notes.stream()
				.filter(n -> n.getPitch() >= getLowest() || n.getPitch() <= getHighest())
				.collect(toList());
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
	
	public int getVoice() {
		return voice;
	}
	public void setVoice(int voice) {
		this.voice = voice;
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
	public int getChannel() {
		return channel;
	}
	public void setChannel(int channel) {
		this.channel = channel;
	}
	public boolean hasKeySwitch() {
		return keySwitch != null;
	}
	public void setKeySwitch(KeySwitch keySwitch) {
		this.keySwitch = keySwitch;
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
	@Override
	public int compareTo(Instrument instrument) {
		if (this.voice < instrument.getVoice()) {
			return -1;
		}else if (this.voice > instrument.getVoice()){
			return 1;
		}
		return 0;
	}

	public InstrumentRegister getInstrumentRegister() {
		return instrumentRegister;
	}

	public void setInstrumentRegister(InstrumentRegister instrumentRegister) {
		this.instrumentRegister = instrumentRegister;
	}
	
}
