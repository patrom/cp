package cp.genre;

import java.util.List;

import cp.model.melody.MelodyBlock;
import cp.out.instrument.Instrument;

@FunctionalInterface
public interface CompositionGenre {

	public List<MelodyBlock> composeInGenre(List<Instrument> instruments);
}
