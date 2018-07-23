package cp.composition.beat;

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
    public BeatgroupFourMotive beatgroupFourMotive;
}
