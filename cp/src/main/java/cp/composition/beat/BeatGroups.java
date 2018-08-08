package cp.composition.beat;

import cp.composition.beat.harmony.BeatgroupHarmonyOne;
import cp.composition.beat.harmony.BeatgroupHarmonyTwo;
import cp.composition.beat.homophony.HomophonicBeatGroup3;
import cp.composition.beat.homophony.HomophonicBeatgroup2;
import cp.composition.beat.melody.BeatGroupFour;
import cp.composition.beat.melody.BeatGroupOne;
import cp.composition.beat.melody.BeatGroupThree;
import cp.composition.beat.melody.BeatGroupTwo;
import cp.composition.beat.motive.BeatGroupOneMotive;
import cp.composition.beat.motive.BeatgroupFourMotive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BeatGroups {

    @Autowired
    public BeatGroupOne beatGroupOne;
    @Autowired
    public BeatGroupTwo beatGroupTwo;
    @Autowired
    public BeatGroupThree beatGroupThree;
    @Autowired
    public BeatGroupFour beatGroupFour;

    @Autowired
    public HomophonicBeatgroup2 homophonicBeatgroup2;
    @Autowired
    public HomophonicBeatGroup3 homophonicBeatgroup3;

    @Autowired
    public BeatGroupOneMotive beatGroupOneMotive;
    @Autowired
    public BeatgroupFourMotive beatgroupFourMotive;

    @Autowired
    public BeatgroupHarmonyOne beatgroupHarmonyOne;
    @Autowired
    public BeatgroupHarmonyTwo beatgroupHarmonyTwo;
}
