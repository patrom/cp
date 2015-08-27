package cp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cp.variation.nonchordtone.Variation;
import cp.variation.nonchordtone.anticipation.Anticipation;
import cp.variation.nonchordtone.appoggiatura.AppoggiatureScaleDown;
import cp.variation.nonchordtone.appoggiatura.AppoggiatureScaleUp;
import cp.variation.nonchordtone.escape.EscapeScaleDown;
import cp.variation.nonchordtone.escape.EscapeScaleUp;
import cp.variation.nonchordtone.neighbor.ChromaticNeigborDown;
import cp.variation.nonchordtone.neighbor.ChromaticNeigborUp;
import cp.variation.nonchordtone.neighbor.NeighborScaleDown;
import cp.variation.nonchordtone.neighbor.NeighborScaleUp;
import cp.variation.nonchordtone.passing.AccentedPassingDown;
import cp.variation.nonchordtone.passing.ChromaticPassingDown;
import cp.variation.nonchordtone.passing.ChromaticPassingUp;
import cp.variation.nonchordtone.passing.PassingDown;
import cp.variation.nonchordtone.passing.PassingUp;
import cp.variation.nonchordtone.suspension.FreeSuspensionChromaticUp;
import cp.variation.nonchordtone.suspension.FreeSuspensionScaleDown;
import cp.variation.nonchordtone.suspension.FreeSuspensionScaleUp;
import cp.variation.nonchordtone.suspension.Suspension;
import cp.variation.pattern.VariationPattern;

@Configuration
public class VariationConfig {
	
	@Autowired
	@Qualifier(value="NeigborVariationPattern")
	private VariationPattern neigborVariationPattern;
	@Autowired
	@Qualifier(value="PassingVariationPattern")
	private VariationPattern passingVariationPattern;
	@Autowired
	@Qualifier(value="SuspensionVariationPattern")
	private VariationPattern suspensionVariationPattern;
	@Autowired
	@Qualifier(value="AnticipationVariationPattern")
	private VariationPattern anticipationVariationPattern;
	@Autowired
	@Qualifier(value="FreeSuspensionVariationPattern")
	private VariationPattern freeSuspensionVariationPattern;
	@Autowired
	@Qualifier(value="EscapeVariationPattern")
	private VariationPattern escapeVariationPattern;
	@Autowired
	@Qualifier(value="AppogiatureVariationPattern")
	private VariationPattern apogiatureVariationPattern;
	
	@Autowired
	private NeighborScaleDown neighborScaleDown;
	@Autowired
	private NeighborScaleUp neighborScaleUp;
	@Autowired
	private ChromaticNeigborDown chromaticNeigborDown;
	@Autowired
	private ChromaticNeigborUp chromaticNeigborUp;
	@Autowired
	private ChromaticPassingUp chromaticPassingUp;
	@Autowired
	private ChromaticPassingDown chromaticPassingDown;
	@Autowired
	private PassingDown passingDown;
	@Autowired
	private AccentedPassingDown accentedPassingDown;
	@Autowired
	private PassingUp passingUp;
	@Autowired
	private Suspension suspension;
	@Autowired
	private Anticipation anticipation;
	@Autowired
	private FreeSuspensionScaleDown freeSuspensionScaleDown;
	@Autowired
	private FreeSuspensionScaleUp freeSuspensionScaleUp;
	@Autowired
	private FreeSuspensionChromaticUp freeSuspensionChromaticUp;
	@Autowired
	private EscapeScaleUp escapeScaleUp;
	@Autowired
	private EscapeScaleDown escapeScaleDown;
	@Autowired
	private AppoggiatureScaleDown appoggiatureScaleDown;
	@Autowired
	private AppoggiatureScaleUp appoggiatureScaleUp;
	
	@Bean
	public List<Variation> variations() {
	    List<Variation> variationList = new ArrayList<>();
	    neighborScaleDown.setVariationPattern(neigborVariationPattern);
	    neighborScaleUp.setVariationPattern(neigborVariationPattern);
	    chromaticNeigborDown.setVariationPattern(neigborVariationPattern);
	    chromaticNeigborUp.setVariationPattern(neigborVariationPattern);
	    anticipation.setVariationPattern(anticipationVariationPattern);
	    freeSuspensionScaleDown.setVariationPattern(freeSuspensionVariationPattern);
	    freeSuspensionScaleUp.setVariationPattern(freeSuspensionVariationPattern);
	    freeSuspensionChromaticUp.setVariationPattern(freeSuspensionVariationPattern);
	    variationList.add(neighborScaleDown);
	    variationList.add(neighborScaleUp);
	    variationList.add(chromaticNeigborDown);
	    variationList.add(chromaticNeigborUp);
	    variationList.add(anticipation);
	    variationList.add(freeSuspensionScaleDown);
	    variationList.add(freeSuspensionScaleUp);
	    variationList.add(freeSuspensionChromaticUp);
	    return variationList;
	}
	
	@Bean
	public List<Variation> majorSecondUpIntervalVariations() {
	    List<Variation> variationList = new ArrayList<>();
	    chromaticPassingUp.setVariationPattern(passingVariationPattern);
	    variationList.add(chromaticPassingUp);
	    escapeScaleDown.setVariationPattern(escapeVariationPattern);
	    variationList.add(escapeScaleDown);
	    appoggiatureScaleDown.setVariationPattern(apogiatureVariationPattern);
	    variationList.add(appoggiatureScaleDown);
	    return variationList;
	}
	
	@Bean
	public List<Variation> minorSecondDownIntervalVariations() {
	    List<Variation> variationList = new ArrayList<>();
	    escapeScaleUp.setVariationPattern(escapeVariationPattern);
	    variationList.add(escapeScaleUp);
	    appoggiatureScaleUp.setVariationPattern(apogiatureVariationPattern);
	    variationList.add(appoggiatureScaleUp);
	    return variationList;
	}
	
	@Bean
	public List<Variation> minorSecondUpIntervalVariations() {
	    List<Variation> variationList = new ArrayList<>();
	    escapeScaleDown.setVariationPattern(escapeVariationPattern);
	    variationList.add(escapeScaleDown);
	    appoggiatureScaleDown.setVariationPattern(apogiatureVariationPattern);
	    variationList.add(appoggiatureScaleDown);
	    return variationList;
	}
	
	@Bean
	public List<Variation> majorSecondDownIntervalVariations() {
	    List<Variation> variationList = new ArrayList<>();
	    chromaticPassingDown.setVariationPattern(passingVariationPattern);
	    variationList.add(chromaticPassingDown);
	    suspension.setVariationPattern(suspensionVariationPattern);
	    variationList.add(suspension);
	    escapeScaleUp.setVariationPattern(escapeVariationPattern);
	    variationList.add(escapeScaleUp);
	    appoggiatureScaleUp.setVariationPattern(apogiatureVariationPattern);
	    variationList.add(appoggiatureScaleUp);
	    return variationList;
	}
	
	@Bean
	public List<Variation> thirdDownIntervalVariations() {
	    List<Variation> variationList = new ArrayList<>();
	    passingDown.setVariationPattern(passingVariationPattern);
	    accentedPassingDown.setVariationPattern(passingVariationPattern);
	    variationList.add(passingDown);
	    variationList.add(accentedPassingDown);
	    appoggiatureScaleUp.setVariationPattern(apogiatureVariationPattern);
	    variationList.add(appoggiatureScaleUp);
	    appoggiatureScaleUp.setVariationPattern(apogiatureVariationPattern);
	    variationList.add(appoggiatureScaleUp);
	    return variationList;
	}
	
	@Bean
	public List<Variation> upIntervalVariations() {
	    List<Variation> variationList = new ArrayList<>();
	    appoggiatureScaleDown.setVariationPattern(apogiatureVariationPattern);
	    variationList.add(appoggiatureScaleDown);
	    return variationList;
	}
	
	@Bean
	public List<Variation> thirdUpIntervalVariations() {
	    List<Variation> variationList = new ArrayList<>();
	    passingUp.setVariationPattern(passingVariationPattern);
	    variationList.add(passingUp);  
	    appoggiatureScaleDown.setVariationPattern(apogiatureVariationPattern);
	    variationList.add(appoggiatureScaleDown);
	    appoggiatureScaleUp.setVariationPattern(apogiatureVariationPattern);
	    variationList.add(appoggiatureScaleUp);
	    appoggiatureScaleUp.setVariationPattern(apogiatureVariationPattern);
	    variationList.add(appoggiatureScaleUp);
	    return variationList;
	}
	
	@Bean
	public List<Variation> unisonoIntervalVariations() {
	    List<Variation> variationList = new ArrayList<>();
	    appoggiatureScaleDown.setVariationPattern(apogiatureVariationPattern);
	    variationList.add(appoggiatureScaleDown);
	    appoggiatureScaleUp.setVariationPattern(apogiatureVariationPattern);
	    variationList.add(appoggiatureScaleUp);
	    return variationList;
	}
	
	@Bean(name="intervalVariations")
	public Map<Integer, List<Variation>> singleNoteInterval() {
		Map<Integer, List<Variation>> map = new TreeMap<>();
		map.put(0, unisonoIntervalVariations());
		map.put(1, minorSecondUpIntervalVariations());
		map.put(-1, minorSecondDownIntervalVariations());
		map.put(2, majorSecondUpIntervalVariations());
		map.put(-2, majorSecondDownIntervalVariations());
		map.put(3, thirdUpIntervalVariations());
		map.put(-3, thirdDownIntervalVariations());
		map.put(4, thirdUpIntervalVariations());
		
//		map.put(5, upIntervalVariations());
//		map.put(6, upIntervalVariations());
//		map.put(7, upIntervalVariations());
//		
//		map.put(-4, thirdDownIntervalVariations());
//		map.put(8, thirdDownIntervalVariations());
//		map.put(-8, thirdUpIntervalVariations());
//		map.put(9, thirdDownIntervalVariations());
//		map.put(-9, thirdUpIntervalVariations());
		return map;
	}

}
