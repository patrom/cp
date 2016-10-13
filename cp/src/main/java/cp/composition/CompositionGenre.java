package cp.composition;

import cp.model.melody.MelodyBlock;

import java.util.List;

@FunctionalInterface
public interface CompositionGenre {

	public List<MelodyBlock> composeInGenre();
}
