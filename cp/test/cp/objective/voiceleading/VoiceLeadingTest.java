package cp.objective.voiceleading;

import static cp.model.harmony.HarmonyBuilder.harmony;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Multiset;

import cp.AbstractTest;
import cp.DefaultConfig;
import cp.generator.ChordGenerator;
import cp.model.harmony.Harmony;
import cp.model.setclass.Set;
import cp.model.setclass.TnTnIType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DefaultConfig.class, loader = SpringApplicationContextLoader.class)
public class VoiceLeadingTest extends AbstractTest{
	
	@Autowired
	private ChordGenerator chordGenerator;
	
	@Test
	public void testVoiceLeadingForteName() {
		Harmony majorHarmonySource = chordGenerator.generateChord("3-11");
		Harmony majorHarmonyTarget = chordGenerator.generateChord("3-11");
		compareVoiceLeading(majorHarmonySource, majorHarmonyTarget);
	}
	
	@Test
	public void testVoiceLeadingForteName2() {
		Harmony source = chordGenerator.generateChord("3-5");
		Harmony target = chordGenerator.generateChord("3-5");
		compareVoiceLeading(source, target);
	}
	
	@Test
	public void testMajorToMajorVoiceLeading() {
		//bug for 0,4,7 with 11,3,6?
		Harmony majorHarmonySource = harmony().notes(0,4,7).build();
		Harmony majorHarmonyTarget = harmony().notes(0,4,7).build();
		compareVoiceLeading(majorHarmonySource, majorHarmonyTarget);
	}

	@Test
	public void testMajorToMinorVoiceLeading() {
		Harmony majorHarmony = harmony().notes(0,4,7).build();
		Harmony minorHarmony = harmony().notes(0,3,7).build();
		compareVoiceLeading(majorHarmony, minorHarmony);
	}
	
	@Test
	public void testMinorToMinorVoiceLeading() { // same voice leading results as major to major!
		Harmony majorHarmony = harmony().notes(0,3,7).build();
		Harmony minorHarmony = harmony().notes(0,3,7).build();
		compareVoiceLeading(majorHarmony, minorHarmony);
	}
	
	@Test
	public void testDom7ToMajorVoiceLeading() {
		Harmony majorHarmonySource = harmony().notes(0,4,7,10).build();
		Harmony majorHarmonyTarget = harmony().notes(0,4,7).build();
		compareVoiceLeading(majorHarmonySource, majorHarmonyTarget);
	}
	
	@Test
	public void testMin7ToMajorVoiceLeading() {
		Harmony majorHarmonySource = harmony().notes(0,3,7,10).build();
		Harmony majorHarmonyTarget = harmony().notes(0,4,7).build();
		compareVoiceLeading(majorHarmonySource, majorHarmonyTarget);
	}
	
	@Test
	public void testInterval4ToMajorVoiceLeading() {
		Harmony interval4Source = harmony().notes(1,5).build();
		Harmony majorHarmonyTarget = harmony().notes(0,4,7).build();
		compareVoiceLeading(interval4Source, majorHarmonyTarget);
	}
	
	private void compareVoiceLeading(Harmony source, Harmony target) {
		source.toChord();
		target.toChord();
		Multiset<Integer> sourceSet = source.getChord().getPitchClassMultiSet();
		for (int i = 0; i < 11; i++) {
			target.transpose(1);
			Multiset<Integer> targetSet = target.getChord().getPitchClassMultiSet();
			VoiceLeadingSize minimalVoiceLeadingSize = VoiceLeading.caculateSize(sourceSet, targetSet);
			print(minimalVoiceLeadingSize);
			
		}
	}

	private void print(VoiceLeadingSize minimalVoiceLeadingSize) {
		LOGGER.info(minimalVoiceLeadingSize.getSourceForteName() + ',' +minimalVoiceLeadingSize.getTargetForteName() + ": size:" + minimalVoiceLeadingSize.getSize()  );
		LOGGER.info(minimalVoiceLeadingSize.getVlSource().toString());
		LOGGER.info(minimalVoiceLeadingSize.getVlTarget().toString());
	}
	
	@Test
	public void testAllChordsVoiceLeading(){
		java.util.Set<VoiceLeadingSize> voiceLeadingSizes = new TreeSet<>();
		TnTnIType type = new TnTnIType();
		type.initPrime3();
		Set[] set = type.prime3;
		for (int i = 0; i < set.length; i++) {
			for (int j = 0; j < set.length; j++) {
				Harmony source = chordGenerator.generateChord(set[i].name);
				Harmony target = chordGenerator.generateChord(set[j].name);
				for (int pcLoop = 0; pcLoop < 11; pcLoop++) {
					target.transpose(1);
					VoiceLeadingSize voiceLeadingSize = getVoiceLeading(source, target);
					if (voiceLeadingSize.getSize() <= 3) {
						voiceLeadingSize.setSourceForteName(set[i].name);
						voiceLeadingSize.setTargetForteName(set[j].name);
						voiceLeadingSizes.add(voiceLeadingSize);
					}
				}
			}
		}
		
		for (VoiceLeadingSize voiceLeadingSize : voiceLeadingSizes) {
			print(voiceLeadingSize);
		}
	}
	
	private VoiceLeadingSize getVoiceLeading(Harmony source, Harmony target) {
		source.toChord();
		target.toChord();
		Multiset<Integer> sourceSet = source.getChord().getPitchClassMultiSet();
		Multiset<Integer> targetSet = target.getChord().getPitchClassMultiSet();
		return VoiceLeading.caculateSize(sourceSet, targetSet);
	}

}
