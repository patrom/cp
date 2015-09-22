package cp.model;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;

import cp.model.harmony.CpHarmony;
import cp.model.melody.CpMelody;
import cp.out.instrument.Instrument;
import cp.util.RandomUtil;

public class Motive implements Cloneable {

	private List<CpHarmony> harmonies;
	private List<CpMelody> melodies;
	private List<Instrument> instruments;
	
	public Motive(List<CpMelody> melodies){
		this.melodies = melodies;
	}

	protected Motive(Motive motive) {
		// TODO clone implementation
		this.melodies = motive.getMelodies().stream().map(m -> (CpMelody) m.clone()).collect(toList());
	}

	public List<CpHarmony> getHarmonies() {
		return harmonies;
	}
	
	public void setHarmonies(List<CpHarmony> harmonies) {
		this.harmonies = harmonies;
	}
	
	public List<CpMelody> getMelodies() {
		return melodies;
	}
	
	@Override
	public Object clone() {
		return new Motive(this);
	}
	
	public CpMelody getRandomMutableMelody(){
		List<CpMelody> mutableMelodies = melodies.stream().filter(m -> m.isMutable()).collect(toList());
		return mutableMelodies.get(RandomUtil.random(mutableMelodies.size()));
	}
	
	public CpMelody getRandomRhythmMutableMelody(){
		List<CpMelody> mutableMelodies = melodies.stream().filter(m -> m.isRhythmMutable()).collect(toList());
		return mutableMelodies.get(RandomUtil.random(mutableMelodies.size()));
	}
	
	public void updateMelodiesToInstrumentLimits(){
		for (CpMelody melody : melodies) {
			Optional<Instrument> optionalInstrument = instruments.stream().filter(instr -> (instr.getVoice()) == melody.getVoice()).findFirst();
			if (optionalInstrument.isPresent()) {
				Instrument instrument = optionalInstrument.get();
				melody.updateMelodyBetween(instrument.getLowest(), instrument.getHighest());
			}
		}
	}
	
}
