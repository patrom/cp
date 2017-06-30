package cp.model.note;

import cp.composition.voice.Voice;
import cp.model.harmony.DependantHarmony;
import cp.out.instrument.Articulation;
import cp.out.instrument.Technical;


public class NoteBuilder {
	
	private int pitchClass;
	private int position;
	private int length;
	private double positionWeight;
	private int pitch;
	private int octave;
	private int voice;
	private int dynamicLevel;
	private Articulation articulation;
	private Dynamic dynamic = Dynamic.MF;
	private int displayLength;
	private BeamType beamType;
	private TupletType tupletType;
	private String timeModification;
	private Technical technical = Voice.DEFAULT_TECHNICAL;
	private DependantHarmony dependantHarmony;

	public static NoteBuilder note(){
		return new NoteBuilder();
	}

	public NoteBuilder pc(int pitchClass){
		this.pitchClass = pitchClass;
		return this;
	}
	
	public NoteBuilder pos(int position){
		this.position = position;
		return this;
	}
	
	public NoteBuilder len(int length){
		this.length = length;
		this.displayLength = length;
		return this;
	}
	
	public NoteBuilder pitch(int pitch){
		this.pitch = pitch;
		return this;
	}
	
	public NoteBuilder octave(int octave){
		this.octave = octave;
		return this;
	}
	
	public NoteBuilder voice(int voice){
		this.voice = voice;
		return this;
	}
	
	public NoteBuilder dyn(Dynamic dynamic){
		this.dynamic = dynamic;
		return this;
	}

	public NoteBuilder lev(int dynamicLevel){
		this.dynamicLevel = dynamicLevel;
		return this;
	}
	
	public NoteBuilder art(Articulation articulation){
		this.articulation = articulation;
		return this;
	}
	
	public NoteBuilder positionWeight(Double positionWeight) {
		this.positionWeight = positionWeight;
		return this;
	}
	
	public NoteBuilder rest() {
		this.pitch = Note.REST;
		return this;
	}
	
	public NoteBuilder beam(BeamType beamType) {
		this.beamType = beamType;
		return this;
	}
	
	public NoteBuilder tuplet(TupletType tupletType) {
		this.tupletType = tupletType;
		return this;
	}
	
	public NoteBuilder timeMod(String timeModification) {
		this.timeModification = timeModification;
		return this;
	}

	public NoteBuilder tech(Technical technical) {
		this.technical = technical;
		return this;
	}

	public NoteBuilder dep(DependantHarmony dependantHarmony) {
		this.dependantHarmony = dependantHarmony;
		return this;
	}
	
	public Note build(){
		Note note = new Note();
		note.setPitchClass(pitchClass);
		note.setPosition(position);
		note.setPositionWeight(positionWeight);
		note.setLength(length);
		note.setPitch(pitch);
		note.setOctave(octave);
		note.setVoice(voice);
		if (dynamicLevel == 0 && dynamic != null) {
			note.setDynamicLevel(dynamic.getLevel());
		} else {
			note.setDynamicLevel(Voice.DEFAULT_DYNAMIC_LEVEL);
		}
		note.setArticulation(articulation);
		note.setDynamic(dynamic);
		note.setDisplayLength(displayLength);
		note.setBeamType(beamType);
		note.setTupletType(tupletType);
		note.setTimeModification(timeModification);
		note.setTechnical(technical);
		note.setDependantHarmony(dependantHarmony);
		return note;
	}

}
