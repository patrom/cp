package cp.model.melody.pitchspace;

import java.util.List;

import cp.out.instrument.Instrument;


public class UniformPitchSpace extends PitchSpace {

	public UniformPitchSpace(Integer[] octaveHighestPitchClassRange,
			List<Instrument> instruments) {
		super(octaveHighestPitchClassRange, instruments);
	}

	@Override
	public void translateToPitchSpace() {
		setUniformPitchSpace();
	}

}
