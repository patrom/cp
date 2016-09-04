package cp.variation.pattern;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import cp.model.rhythm.DurationConstants;

@Component(value="NeigborVariationPattern")
public class NeigborVariationPattern extends VariationPattern {

	public NeigborVariationPattern() {
		setPatterns(new double[][]{{0.5, 0.25, 0.25}});//, {1.0/3.0, 1.0/3.0, 1.0/3.0}
		List<Integer> allowedLengths = new ArrayList<>();
		allowedLengths.add(DurationConstants.QUARTER);
		allowedLengths.add(DurationConstants.HALF);
		setNoteLengths(allowedLengths);
	}

}
