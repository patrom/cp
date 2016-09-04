package cp.variation.pattern;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import cp.model.rhythm.DurationConstants;

@Component(value="AppogiatureVariationPattern")
public class AppogiatureVariationPattern extends VariationPattern {

	public AppogiatureVariationPattern() {
//		setPatterns(new double[][]{{0.25, 0.75},{0.5, 0.5},{0.75, 0.25}});//,{2.0/3.0, 1.0/3.0}
		setPatterns(new double[][]{{0.5, 0.5}});
		List<Integer> allowedLengths = new ArrayList<>();
//		allowedLengths.add(6);
		allowedLengths.add(DurationConstants.QUARTER);
		allowedLengths.add(DurationConstants.HALF);
		setNoteLengths(allowedLengths);
	}
}
