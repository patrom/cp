package cp.composition.beat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BeatGroups {

    @Autowired
    public BeatGroup beatGroupOne;
    @Autowired
    public BeatGroup beatGroupTwo;
    @Autowired
    public BeatGroup beatGroupThree;
    @Autowired
    public BeatGroup beatGroupFour;

    @Autowired
    public BeatGroup beatGroupHomophonicOne;
    @Autowired
    public BeatGroup beatGroupHomophonicTwo;
    @Autowired
    public BeatGroup beatGroupHomophonicThree;
    @Autowired
    public BeatGroup beatGroupHomophonicFour;

    @Autowired
    public BeatGroup beatGroupMotiveOne;

    @Autowired
    public BeatGroup beatGroupMotiveTwo;


    @Autowired
    public BeatGroup beatGroupHarmonyOne;
    @Autowired
    public BeatGroup beatGroupHarmonyTwo;
}
