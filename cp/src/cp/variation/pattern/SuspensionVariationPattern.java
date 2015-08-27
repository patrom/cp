package cp.variation.pattern;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component(value="SuspensionVariationPattern")
public class SuspensionVariationPattern extends VariationPattern{

	public SuspensionVariationPattern() {
		//pattern is for second note only!
		setPatterns(new double[][]{{0.5, 0.5}});
		List<Integer> allowedLengths = new ArrayList<>();
		allowedLengths.add(6);
		allowedLengths.add(12);
		allowedLengths.add(24);
		setNoteLengths(allowedLengths);
		List<Integer> allowedSecondLengths = new ArrayList<>();
//		allowedSecondLengths.add(6);
		allowedSecondLengths.add(12);
		allowedSecondLengths.add(24);
		setSecondNoteLengths(allowedSecondLengths);
	}
}
