package cp.objective.voiceleading;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cp.AbstractTest;
import cp.DefaultConfig;
import cp.objective.Objective;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DefaultConfig.class)
public class VoiceLeadingObjectiveTest extends AbstractTest{
	
	@Autowired
	private Objective voiceLeadingObjective;

	@Before
	public void setUp() {
		musicProperties.setHarmonyBeatDivider(12);
	}

//	@Test
//	public void testMajorToMajorFirstInv() {
//		List<Harmony> harmonies = new ArrayList<>();
//		harmonies.add(harmony().pos(0).len(12).notes(0,4,7).build());
//		harmonies.add(harmony().pos(12).len(12).notes(11,2,7).build());
//		harmonies.forEach(harmony -> harmony.toChord());
//		double VoiceLeadingValue = voiceLeadingObjective.evaluate(new Motive(harmonies, musicProperties));
//		LOGGER.info("VoiceLeadingValue : " + VoiceLeadingValue);
//		assertEquals("Wrong VoiceLeading value", 3, VoiceLeadingValue, 0);
//	}
//	
//	@Test
//	public void testMajorToMajorRootPosition() {
//		List<Harmony> harmonies = new ArrayList<>();
//		harmonies.add(harmony().pos(0).len(12).notes(0,4,7).build());
//		harmonies.add(harmony().pos(12).len(12).notes(11,2,7).build());
//		harmonies.forEach(harmony -> harmony.toChord());
//		double VoiceLeadingValue = voiceLeadingObjective.evaluate(new Motive(harmonies, musicProperties));
//		LOGGER.info("VoiceLeadingValue : " + VoiceLeadingValue);
//		assertEquals("Wrong VoiceLeading value", 3, VoiceLeadingValue, 0);
//	}
//	
//	@Test
//	public void testProgression(){
//		List<Harmony> harmonies = new ArrayList<>();
//		harmonies.add(harmony().pos(0).len(6).notes(0,4,7).build());
//		harmonies.add(harmony().pos(6).len(6).notes(10,2,7).build());
//		harmonies.add(harmony().pos(12).len(6).notes(11,2,7).build());
//		harmonies.add(harmony().pos(18).len(6).notes(0,4,7).build());
//		harmonies.forEach(harmony -> harmony.toChord());
//		double VoiceLeadingValue = voiceLeadingObjective.evaluate(new Motive(harmonies, musicProperties));
//		LOGGER.info("VoiceLeadingValue : " + VoiceLeadingValue);
//		assertEquals("Wrong VoiceLeading value", 8/3d, VoiceLeadingValue, 0);
//	}

}
