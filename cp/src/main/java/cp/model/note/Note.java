package cp.model.note;

import cp.model.rhythm.DurationConstants;
import cp.out.instrument.Articulation;


public class Note implements Comparable<Note>, Cloneable{

	/** The pitch value which indicates a rest. */
	public static final int REST = Integer.MIN_VALUE;
	   /** default dynamic*/
    public static final int DEFAULT_DYNAMIC_LEVEL = Dynamic.MF.getLevel();
    public static final Articulation DEFAULT_ARTICULATION = Articulation.LEGATO;
    public static final Dynamic DEFAULT_DYNAMIC = Dynamic.MF;
    public static final int DEFAULT_LENGTH = DurationConstants.QUARTER;

	private int pitch;
	private int dynamicLevel = DEFAULT_DYNAMIC_LEVEL;
	private double rhythmValue;
//	private double duration;

	private int length;
	private int displayLength;
	private int position;

	private double positionWeight;
	private double innerMetricWeight;

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
	
	private BeamType beamType;
	//begin or end of tuplet
	private TupletType tupletType;
	
	//parsing musicXML
	private String instrument;

	
	private Articulation articulation = DEFAULT_ARTICULATION;
	private Dynamic dynamic = DEFAULT_DYNAMIC;

	public Note() {
	}

	public Note(int pitchClass, int voice, int position, int length) {
		this.pitchClass = pitchClass;
		this.voice = voice;
		this.position = position;
		this.length = length;
	}
	
	public Note(Note anotherNote) {
		this.length = anotherNote.getLength();
		this.position =anotherNote.getPosition();
		this.pitch = anotherNote.getPitch();
		this.pitchClass = anotherNote.getPitchClass();
//		this.setDuration(anotherNote.getDuration());
		this.voice = anotherNote.getVoice();
		this.innerMetricWeight = anotherNote.getInnerMetricWeight();
		this.rhythmValue = anotherNote.getRhythmValue();
		this.dynamicLevel = anotherNote.getDynamicLevel();
		this.octave = anotherNote.getOctave();
		this.positionWeight = anotherNote.getPositionWeight();
		this.articulation = anotherNote.getArticulation();
		this.dynamic = anotherNote.getDynamic();
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

	public void setPosition(int position) {
		this.position = position;
	}

	public int getPitch() {
		return pitch;
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
		if (this.getPitch() == REST)
			return true;
		else
			return false;
	}

	public boolean samePitch(Note note) {
		return this.getPitch() == note.getPitch();
	}

	public double getWeightedSum() {
		return (positionWeight + innerMetricWeight)/2;
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

	@Override
	public String toString() {
		return "np[p=" + ((pitch == Integer.MIN_VALUE) ? "Rest":pitch) + ", pc=" + pitchClass
		+ ", v=" + voice + ", o=" + octave + ", pos=" + position +  ", l=" + length + ", dl= " + displayLength + ", pos w="
		+ positionWeight + ", a=" + articulation + "]";
	}

	public double getPositionWeight() {
		return positionWeight;
	}

	public void setPositionWeight(double positionWeight) {
		this.positionWeight = positionWeight;
	}

	public double getInnerMetricWeight() {
		return innerMetricWeight;
	}

	public void setInnerMetricWeight(double innerMetricWeight) {
		this.innerMetricWeight = innerMetricWeight;
	}

	public int compareTo(Note note) {
		if (getPosition() < note.getPosition()) {
			return -1;
		} if (getPosition() > note.getPosition()) {
			return 1;
		} else {
			return 0;
		}
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
	
	@Override
	public Note clone() {
		return new Note(this);
	}

	public Articulation getArticulation() {
		return articulation;
	}
	
	public boolean hasArticulation(){
		return !articulation.equals(DEFAULT_ARTICULATION);
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
		return dynamicLevel != DEFAULT_DYNAMIC_LEVEL;
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

}