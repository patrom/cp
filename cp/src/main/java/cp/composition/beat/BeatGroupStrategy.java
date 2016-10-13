package cp.composition.beat;

import java.util.List;

@FunctionalInterface
public interface BeatGroupStrategy {

	public List<BeatGroup> getBeatGroups();
}
