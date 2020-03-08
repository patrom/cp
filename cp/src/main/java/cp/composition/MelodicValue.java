package cp.composition;

import cp.combination.RhythmCombination;
import cp.model.melody.CpMelody;
import cp.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;

public interface MelodicValue {

    CpMelody pickRandomMelody();

    CpMelody pickRandomMelodyWithMultipleNotes();

    CpMelody pickExhaustiveMelody();

    void setContourType(ContourType contourType);

    MelodicValue clone();
}
