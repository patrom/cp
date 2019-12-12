package cp.generator;

import cp.composition.MelodicValue;
import cp.model.melody.CpMelody;

import java.util.List;

public interface MelodySelector {

    CpMelody getMelody(int voice, List<MelodicValue> melodicValues);

}
