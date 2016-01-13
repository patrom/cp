package cp.objective.voiceleading;


import java.util.ArrayList;
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
import cp.model.harmony.CpHarmony;
import cp.model.note.Note;
import cp.model.note.NoteBuilder;
import cp.model.setclass.Set;
import cp.model.setclass.TnTnIType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DefaultConfig.class, loader = SpringApplicationContextLoader.class)
public class VoiceLeadingTest extends AbstractTest{
	//bug for latest in calculation display duplicates pc?

	@Autowired
	private ChordGenerator chordGenerator;
	
	@Test
	public void testVoiceLeadingForteName() {
		CpHarmony majorHarmonySource = chordGenerator.generateChord("3-11");
		CpHarmony majorHarmonyTarget = chordGenerator.generateChord("3-11");
		compareVoiceLeading(majorHarmonySource, majorHarmonyTarget);
	}
	
	@Test
	public void testVoiceLeadingForteName2() {
		CpHarmony source = chordGenerator.generateChord("3-5");
		CpHarmony target = chordGenerator.generateChord("3-5");
		compareVoiceLeading(source, target);
	}
	
	private CpHarmony createHarmony(int ...pitchClasses){
		List<Note> notes = new ArrayList<>();
		for (int pitchClass : pitchClasses) {
			notes.add(NoteBuilder.note().pc(pitchClass).build());
		}
		CpHarmony harmony = new CpHarmony(notes, 0);
		return harmony;
	}
	
	@Test
	public void testMajorToMajorVoiceLeading() {
		CpHarmony majorHarmonySource = createHarmony(0,4,7);
		CpHarmony majorHarmonyTarget = createHarmony(0,4,7);
		compareVoiceLeading(majorHarmonySource, majorHarmonyTarget);
	}

	@Test
	public void testMajorToMinorVoiceLeading() {
		CpHarmony majorHarmony = createHarmony(0,4,7);
		CpHarmony minorHarmony = createHarmony(0,3,7);
		compareVoiceLeading(majorHarmony, minorHarmony);
	}
	
	@Test
	public void testMinorToMinorVoiceLeading() { // same voice leading results as major to major!
		CpHarmony majorHarmony = createHarmony(0,3,7);
		CpHarmony minorHarmony = createHarmony(0,3,7);
		compareVoiceLeading(majorHarmony, minorHarmony);
	}
	
	@Test
	public void testDom7ToMajorVoiceLeading() {
		CpHarmony majorHarmonySource = createHarmony(0,4,7,10);
		CpHarmony majorHarmonyTarget = createHarmony(0,4,7);
		compareVoiceLeading(majorHarmonySource, majorHarmonyTarget);
	}
	
	@Test
	public void testMin7ToMajorVoiceLeading() {
		CpHarmony majorHarmonySource = createHarmony(0,3,7,10);
		CpHarmony majorHarmonyTarget = createHarmony(0,4,7);
		compareVoiceLeading(majorHarmonySource, majorHarmonyTarget);
	}
	
	@Test
	public void testInterval4ToMajorVoiceLeading() {
		CpHarmony interval4Source = createHarmony(1,5);
		CpHarmony majorHarmonyTarget = createHarmony(0,4,7);
		compareVoiceLeading(interval4Source, majorHarmonyTarget);
	}
	
	private void compareVoiceLeading(CpHarmony source, CpHarmony target) {
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
		LOGGER.info(minimalVoiceLeadingSize.getSourceForteName() + ',' + minimalVoiceLeadingSize.getTargetForteName() + ": size:" + minimalVoiceLeadingSize.getSize()  );
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
				CpHarmony source = chordGenerator.generateChord(set[i].name);
				CpHarmony target = chordGenerator.generateChord(set[j].name);
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
	
	private VoiceLeadingSize getVoiceLeading(CpHarmony source, CpHarmony target) {
		source.toChord();
		target.toChord();
		Multiset<Integer> sourceSet = source.getChord().getPitchClassMultiSet();
		Multiset<Integer> targetSet = target.getChord().getPitchClassMultiSet();
		return VoiceLeading.caculateSize(sourceSet, targetSet);
	}

}
