package cp.model.note;

import cp.composition.voice.Voice;
import cp.model.harmony.DependantHarmony;
import cp.model.humanize.Humanization;
import cp.out.instrument.Articulation;
import cp.out.instrument.Technical;


public class Note implements Comparable<Note>{

	/** The pitch value which indicates a rest. */
	public static final int REST = Integer.MIN_VALUE;
	public static final int DEFAULT_INTONATION = 64;
	/** default dynamic*/

	private int pitch;
	private int dynamicLevel = Voice.DEFAULT_DYNAMIC_LEVEL;
	private double rhythmValue;
//	private double duration;

	private int length;
	private int displayLength;
	private int position;

	private double positionWeight;

	private int octave;
	private int pitchClass;
	private int voice;
	
	private boolean tieStart;
	private boolean tieEnd;
	
	private boolean keel;
	private boolean crest;
	
	//writing musicXML
	//time modification
	private boolean triplet;
	private boolean sextuplet;
	private boolean quintuplet;
	private boolean bracket;
	private String timeModification;
	private boolean printDynamic;
	private boolean printTechnical;
	private Technical technical;
	
	private BeamType beamType;
	//begin or end of tuplet
	private TupletType tupletType;
	
	//parsing musicXML
	private String instrument;
	private DependantHarmony dependantHarmony;
	private boolean chord = false;
	
	private Articulation articulation;
	private Dynamic dynamic = Voice.DEFAULT_DYNAMIC;

	private Humanization humanization;

	public Note() {
	}

	public Note(int pitchClass, int voice, int position, int length) {
		this.pitchClass = pitchClass;
		this.voice = voice;
		this.position = position;
		this.length = length;
	}
	
	private Note(Note anotherNote) {
		this.dependantHarmony = anotherNote.getDependantHarmony() == null?null:anotherNote.getDependantHarmony().clone();
		this.length = anotherNote.getLength();
		this.position =anotherNote.getPosition();
		this.pitch = anotherNote.getPitch();
		this.pitchClass = anotherNote.getPitchClass();
//		this.setDuration(anotherNote.getDuration());
		this.voice = anotherNote.getVoice();
		this.rhythmValue = anotherNote.getRhythmValue();
		this.dynamicLevel = anotherNote.getDynamicLevel();
		this.octave = anotherNote.getOctave();
		this.positionWeight = anotherNote.getPositionWeight();
		this.articulation = anotherNote.getArticulation();
		this.dynamic = anotherNote.getDynamic();
		this.technical = anotherNote.getTechnical();
		this.keel = anotherNote.isKeel();
		this.crest = anotherNote.isCrest();
		this.displayLength = anotherNote.getDisplayLength();
		this.beamType = anotherNote.getBeamType();
		this.triplet = anotherNote.isTriplet();
		this.sextuplet = anotherNote.isSextuplet();
		this.quintuplet = anotherNote.isQuintuplet();
		this.tupletType = anotherNote.getTupletType();
		this.bracket = anotherNote.isBracket();
		this.timeModification = anotherNote.getTimeModification();
		this.dependantHarmony = anotherNote.getDependantHarmony();
	}

	public void updateNote(Note note){
		this.pitchClass = note.getPitchClass();
		this.pitch = note.getPitch();
		this.octave = note.getOctave();
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getPosition() {
		return position;
	}

	public int getEndPostion(){
		return position + displayLength;
	}

	public int getMidiPosition(){
		if (humanization != null) {
			int midiPosition = position + humanization.getTiming();
			if(midiPosition < 0){
                return position;
            }
			return midiPosition;
		} else {
			return position;
		}
	}

	public int getBeforeMidiPosition(){
		if (humanization != null) {
			int midiPosition = position + humanization.getTiming() - 1;
			if(midiPosition < 0){
                return position;
            }
			return midiPosition;
		} else {
		    return position;
		}
	}

    public int getMidiLength(){
		if (humanization != null) {
			return humanization.getDuration();
		} else {
			return length;
		}
	}

	public int getMidiControllerLength(){
		return length / 2;
	}

	public int getMidiAttack(){
		if (humanization != null) {
			return humanization.getAttack();
		} else {
			return 0;
		}
	}

	public int getMidiVelocity(){
		if (humanization != null) {
			return dynamicLevel + humanization.getVelocity();
		} else {
			return dynamicLevel;
		}
	}

	public int getMidiIntonation(){
		if (humanization != null) {
			return DEFAULT_INTONATION + humanization.getIntonation();
		} else {
			return DEFAULT_INTONATION;
		}
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getPitch() {
		return pitch;
	}

	public double getRegisterValue(){
		return pitch/100d;
	}

	public void setPitch(int pitch) {
		this.pitch = pitch;
	}

	public int getDynamicLevel() {
		return dynamicLevel;
	}

	public void setDynamicLevel(int dynamic) {
		this.dynamicLevel = dynamic;
	}

	public double getRhythmValue() {
		return rhythmValue;
	}

	public void setRhythmValue(double rhythmValue) {
		this.rhythmValue = rhythmValue;
	}

//	public double getDuration() {
//		return duration;
//	}
//
//	public void setDuration(double duration) {
//		this.duration = duration;
//	}

	public boolean isRest() {
		return this.pitch == REST;
	}

	public boolean samePitch(Note note) {
		return this.getPitch() == note.getPitch();
	}

	public int getOctave() {
		return octave;
	}

	public void setOctave(int octave) {
		this.octave = octave;
	}

	public int getPitchClass() {
		return pitchClass;
	}

	public void setPitchClass(int pitchClass) {
		this.pitchClass = pitchClass;
	}

	public int getVoice() {
		return voice;
	}

	public void setVoice(int voice) {
		this.voice = voice;
	}

	public DependantHarmony getDependantHarmony() {
		return dependantHarmony;
	}

	public void setDependantHarmony(DependantHarmony dependantHarmony) {
		this.dependantHarmony = dependantHarmony;
	}

	@Override
	public String toString() {
		return "np[p=" + ((pitch == Integer.MIN_VALUE) ? "Rest":pitch) + ", pc=" + pitchClass
		+ ", v=" + voice + ", o=" + octave + ", pos=" + position +  ", l=" + length + ", dl= " + displayLength + ", pos w="
		+ positionWeight + ", a=" + articulation +  ", d= " + dynamic.name() + ", t= " + technical + "]";
	}

	public double getPositionWeight() {
		return positionWeight;
	}

	public void setPositionWeight(double positionWeight) {
		this.positionWeight = positionWeight;
	}

	public int compareTo(Note note) {
		return Integer.compare(position, note.getPosition());
//		if (getPosition() < note.getPosition()) {
//			return -1;
//		} if (getPosition() > note.getPosition()) {
//			return 1;
//		} else {
//			return 0;
//		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + pitchClass;
		result = prime * result + position;
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
		Note other = (Note) obj;
		if (pitchClass != other.pitchClass)
			return false;
		if (position != other.position)
			return false;
		return true;
	}

	public Note clone() {
		return new Note(this);
	}

	public Articulation getArticulation() {
		return articulation;
	}
	
	public boolean hasArticulation(){
		return articulation != null;
	}
	
	public void setArticulation(Articulation articulation) {
		this.articulation = articulation;
	}
	
	public boolean isTieStart() {
		return tieStart;
	}

	public void setTieStart(boolean tieStart) {
		this.tieStart = tieStart;
	}

	public boolean isTieEnd() {
		return tieEnd;
	}

	public void setTieEnd(boolean tieEnd) {
		this.tieEnd = tieEnd;
	}
	
	public boolean hasDynamic(){
		return dynamicLevel != Voice.DEFAULT_DYNAMIC_LEVEL;
	}

	public Dynamic getDynamic() {
		return dynamic;
	}

	public void setDynamic(Dynamic dynamic) {
		this.dynamic = dynamic;
	}

	public boolean isKeel() {
		return keel;
	}

	public void setKeel(boolean keel) {
		this.keel = keel;
	}

	public boolean isCrest() {
		return crest;
	}

	public void setCrest(boolean crest) {
		this.crest = crest;
	}

	public void transposePitch(int steps){
		this.pitch = pitch + steps;
		this.pitchClass = pitch % 12;
		this.octave  = (int) Math.ceil(pitch/12);
	}

	public void transposeOctaveUp(){
		if(this.pitch != REST){
			this.pitch = pitch + 12;
			this.octave = octave + 1;
		}
	}

	public void transposeOctaveDown(){
		if(this.pitch != REST){
			this.pitch = pitch - 12;
			this.octave = octave - 1;
		}
	}

	public void transposeOctave(int octaveDiff){
		if(this.pitch != REST){
			this.pitch = pitch + (octaveDiff * 12);
			this.octave = octave + octaveDiff;
		}
	}

	
	public double getBeat(int divider) {
		return Math.floor(position / divider);
	}
	
	public int beat(int beat){
		return position/beat;
	}

	public boolean isTriplet() {
		return triplet;
	}

	public void setTriplet(boolean triplet) {
		this.triplet = triplet;
	}

	public boolean isSextuplet() {
		return sextuplet;
	}

	public void setSextuplet(boolean sextuplet) {
		this.sextuplet = sextuplet;
	}
	
	

	public int getDisplayLength() {
		return displayLength;
	}

	public void setDisplayLength(int displayLength) {
		this.displayLength = displayLength;
	}

	public BeamType getBeamType() {
		return beamType;
	}
	
	public boolean hasBeamType() {
		return beamType != null;
	}
	
	
	
//	public boolean hasBeamTypeBeginOrEnd() {
//		return beamType != null && (beamType.equals(BeamType.BEGIN) || beamType.equals(BeamType.END));
//	}

	public void setBeamType(BeamType beamType) {
		this.beamType = beamType;
	}

	public boolean hasDoubleBeaming() {
		return beamType != null && beamType.isDoubleBeam();
	}
	
	public void setTupletType(TupletType tupletType) {
		this.tupletType = tupletType;
	}
	
	public TupletType getTupletType() {
		return tupletType;
	}
	
	public boolean hasTupletType() {
		return tupletType != null;
	}

	public boolean isQuintuplet() {
		return quintuplet;
	}

	public void setQuintuplet(boolean quintuplet) {
		this.quintuplet = quintuplet;
	}

	public boolean isBracket() {
		return bracket;
	}

	public void setBracket(boolean bracket) {
		this.bracket = bracket;
	}

	public String getInstrument() {
		return instrument;
	}

	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}

	public String getTimeModification() {
		return timeModification;
	}

	public void setTimeModification(String timeModification) {
		this.timeModification = timeModification;
	}

	public boolean isPrintDynamic() {
		return printDynamic;
	}

	public void setPrintDynamic(boolean printDynamic) {
		this.printDynamic = printDynamic;
	}

	public Technical getTechnical() {
		return technical;
	}

	public void setTechnical(Technical technical) {
		this.technical = technical;
	}

	public boolean isPrintTechnical() {
		return printTechnical;
	}

	public void setPrintTechnical(boolean printTechnical) {
		this.printTechnical = printTechnical;
	}

	public boolean isChord() {
		return chord;
	}

	public void setChord(boolean chord) {
		this.chord = chord;
	}

	public void setHumanization(Humanization humanization) {
		this.humanization = humanization;
	}

	public Humanization getHumanization() {
		return humanization;
	}

	public boolean hasTexture(){
		return dependantHarmony != null;
	}
}
