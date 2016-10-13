package cp.nsga;


import cp.model.Motive;
import jmetal.core.Variable;
import jmetal.util.JMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MusicVariable extends Variable {

	private static Logger LOGGER = LoggerFactory.getLogger(MusicVariable.class.getName());
	private Motive motive;
	
	public MusicVariable(MusicVariable musicVariable) throws JMException {
		this.motive = (Motive) musicVariable.getMotive().clone();
	}

	public MusicVariable(Motive motive) {
		this.motive = motive;
	}
	
	public Motive getMotive() {
		return motive;
	}

//	protected Motive cloneMotives(Motive motive) {
//		this.motive = (Motive) motive.clone();
//		return newMotive;
//	}
//	
//	private HarmonicMelody copyHarmonicMelody(HarmonicMelody harmonicMelody) {
//		List<Note> melodyNotes = copyNotes(harmonicMelody.getMelodyNotes());
//		Note harmonyNote = harmonicMelody.getHarmonyNote().copy();
//		return new HarmonicMelody(harmonyNote, melodyNotes, harmonicMelody.getVoice(), harmonicMelody.getPosition());
//	}
//	
//	private Harmony copyHarmony(Harmony harmony){
//		List<HarmonicMelody> harmonicMelodies = new ArrayList<>();
//		for (HarmonicMelody harmonicMelody : harmony.getHarmonicMelodies()) {
//			HarmonicMelody newHarmonicMelody = copyHarmonicMelody(harmonicMelody);
//			harmonicMelodies.add(newHarmonicMelody);
//		}
//		Harmony copyHarmony = new Harmony(harmony.getPosition(), harmony.getLength(), harmonicMelodies);
//		PitchSpace newPitchSpace = clonePitchClass(harmony.getPitchSpace());
//		newPitchSpace.setHarmonicMelodies(copyHarmony.getHarmonicMelodies());
//		copyHarmony.setPitchSpace(newPitchSpace);
//		copyHarmony.setPositionWeight(harmony.getPositionWeight());
//		return copyHarmony;
//	}
//
//	private List<Note> copyNotes(List<Note> notePositions) {
//		List<Note> newNotes = new ArrayList<Note>();
//		int l = notePositions.size();
//		for (int i = 0; i < l; i++) {	
//			Note note = notePositions.get(i);
//			Note newNote = note.copy();
//			newNotes.add(newNote);
//		}
//		return newNotes;
//	}
//	
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	private PitchSpace clonePitchClass(PitchSpace pitchSpace) {
//		PitchSpace newPitchSpaceStrategy = null;
//		try {
//			Class pitchSpaceStrategyClass = Class.forName(pitchSpace.getClass().getName());
//			Constructor<PitchSpace> constructor = pitchSpaceStrategyClass.getConstructor(Integer[].class, List.class);
//			newPitchSpaceStrategy = constructor.newInstance((Object)pitchSpace.getOctaveHighestPitchClassRange(), pitchSpace.getInstruments());
//		} catch (InvocationTargetException |IllegalArgumentException |SecurityException | NoSuchMethodException 
//				| ClassNotFoundException | InstantiationException | IllegalAccessException e) {
//			throw new IllegalArgumentException("PitchSpace not cloneable: " + pitchSpace.getClass().getName());
//		}
//		return newPitchSpaceStrategy;
//	}

	@Override
	public Variable deepCopy() {
		try {
	      return new MusicVariable(this);
	    } catch (JMException e) {
	    	LOGGER.error("MusicVariable.deepCopy.execute: JMException");
	      return null ;
	    }
	}
	
}

