package cp.variation.pattern;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import cp.model.rhythm.DurationConstants;

@Component(value="SuspensionVariationPattern")
public class SuspensionVariationPattern extends VariationPattern{

	public SuspensionVariationPattern() {
		//pattern is for second note only!
		setPatterns(new double[][]{{0.5, 0.5}});
		List<Integer> allowedLengths = new ArrayList<>();
		allowedLengths.add(6);
		allowedLengths.add(DurationConstants.QUARTER);
		allowedLengths.add(DurationConstants.HALF);
		setNoteLengths(allowedLengths);
		List<Integer> allowedSecondLengths = new ArrayList<>();
//		allowedSecondLengths.add(6);
		allowedSecondLengths.add(DurationConstants.QUARTER);
		allowedSecondLengths.add(DurationConstants.HALF);
		setSecondNoteLengths(allowedSecondLengths);
	}
}
