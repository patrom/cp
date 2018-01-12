package cp.composition.timesignature;

import cp.composition.beat.BeatGroup;
import cp.model.rhythm.DurationConstants;
import org.springframework.stereotype.Component;

@Component(value="time44")
//@ConditionalOnProperty(name = "composition.timesignature", havingValue = "4/4")
public class Time44 extends TimeConfig{

	@Override
	public void init() {
		super.init();
		BeatGroup defaultGroup6 = beatGroupFactory.getBeatGroupEven(DurationConstants.EIGHT);// X 2
		BeatGroup defaultGroup12 = beatGroupFactory.getBeatGroupEven(DurationConstants.QUARTER);// X 2
		allBeatgroups.add(defaultGroup6);
//		allBeatgroups.add(defaultGroup12);
		minimumLength = DurationConstants.QUARTER;
		distance = new int[]{2,4,8,10,12,14,16,18,20,21,22,24,26,27,28,30,32};//minimumRhythmicValue = 12 - 4/4
		offset = DurationConstants.WHOLE;

		measureDuration = DurationConstants.WHOLE;
	}

}
