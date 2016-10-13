package cp.composition.beat;

import java.util.List;

@FunctionalInterface
public interface BeatGroupStrategy {

	List<BeatGroup> getBeatGroups();
}
