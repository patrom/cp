package cp.generator.dependant;

import cp.model.melody.MelodyBlock;

import java.util.List;

/**
 * Created by prombouts on 26/01/2017.
 */
@FunctionalInterface
public interface DependantHarmonyGenerator {

     void generateDependantHarmonies(List<MelodyBlock> melodies);
}
