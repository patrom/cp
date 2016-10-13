package cp.variation.pattern;

import cp.model.rhythm.DurationConstants;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component(value="EscapeVariationPattern")
public class EscapeVariationPattern extends VariationPattern {

	public EscapeVariationPattern() {
//		setPatterns(new double[][]{{0.75, 0.25}});//,{2.0/3.0, 1.0/3.0}
		setPatterns(new double[][]{{0.5, 0.5}});
		List<Integer> allowedLengths = new ArrayList<>();
		allowedLengths.add(DurationConstants.QUARTER);
		allowedLengths.add(DurationConstants.HALF);
		setNoteLengths(allowedLengths);
	}
}
