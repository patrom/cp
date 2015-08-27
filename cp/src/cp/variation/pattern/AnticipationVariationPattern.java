package cp.variation.pattern;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component(value="AnticipationVariationPattern")
public class AnticipationVariationPattern extends VariationPattern {

	public AnticipationVariationPattern() {
//		setPatterns(new double[][]{{0.75, 0.25}});//,{2.0/3.0, 1.0/3.0}
		setPatterns(new double[][]{{0.5, 0.5}});
		List<Integer> allowedLengths = new ArrayList<>();
//		allowedLengths.add(6);
		allowedLengths.add(12);
		allowedLengths.add(24);
		setNoteLengths(allowedLengths);
	}
}
