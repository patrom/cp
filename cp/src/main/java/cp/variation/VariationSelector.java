package cp.variation;

import cp.util.RandomUtil;
import cp.util.Util;
import cp.variation.nonchordtone.DefaultVariation;
import cp.variation.nonchordtone.Variation;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class VariationSelector {

	@Resource(name="intervalVariations")
	private Map<Integer, List<Variation>> intervalVariations;
	@Resource(name="variations")
	private List<Variation> variations;
	@Autowired
	private DefaultVariation defaultVariation;
	
	public Variation getVariation(){
		if (variations.isEmpty()) {
			return null;
		}
		return RandomUtil.getRandomFromList(variations);
	}
	
	public List<Variation> getIntervalVariations(Integer interval){
		List<Variation> singleNoteVariations = intervalVariations.getOrDefault(interval, Collections.emptyList());
		return singleNoteVariations;
	}
	
	public Variation selectVariation(int interval) {
		List<Variation> variations = new ArrayList<>();
		variations.add(defaultVariation);
		List<Integer> profiles = new ArrayList<>();
		profiles.add(defaultVariation.getProfile());
		List<Variation> intervalVariations = getIntervalVariations(interval);
		for (Variation variation : intervalVariations) {
			variations.add(variation);
			profiles.add(variation.getProfile());
		}
		Variation singleNote = getVariation();
		if (singleNote != null) {
			variations.add(singleNote);
			profiles.add(singleNote.getProfile());
		}
		int[] profilesArray = ArrayUtils.toPrimitive(profiles.toArray(new Integer[profiles.size()]));
		Variation variation = Util.selectFromListProbability(variations, profilesArray);
		return variation;
	}
	
}
