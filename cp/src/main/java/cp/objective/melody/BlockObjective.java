package cp.objective.melody;

import cp.model.Motive;
import cp.model.melody.MelodyBlock;

import java.util.List;

public interface BlockObjective {

    double evaluate(MelodyBlock melodyBlocks);
}
