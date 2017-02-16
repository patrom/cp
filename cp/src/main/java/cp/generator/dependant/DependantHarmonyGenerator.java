package cp.generator.dependant;

import cp.model.melody.MelodyBlock;

import java.util.List;

/**
 * Created by prombouts on 26/01/2017.
 */
public interface DependantHarmonyGenerator {

     void generateDependantHarmonies(List<MelodyBlock> melodies);

     int getSourceVoice();

     int getDependingVoice();

     int getSecondDependingVoice();

     boolean hasSecondDependingVoice();
}
