package cp.generator.provider;

import cp.model.melody.CpMelody;

import java.util.List;

/**
 * Created by prombouts on 12/05/2017.
 */
@FunctionalInterface
public interface MelodyProvider {

    List<CpMelody> getMelodies(int voice);
}
