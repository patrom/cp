package cp.objective.voiceleading;

import cp.AbstractTest;
import cp.DefaultConfig;
import cp.objective.Objective;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DefaultConfig.class)
public class VoiceLeadingObjectiveTest extends AbstractTest {
	
	@Autowired
	private Objective voiceLeadingObjective;

	@Before
	public void setUp() {
		musicProperties.setHarmonyBeatDivider(12);
	}

//	@Test
//	public void testMajorToMajorFirstInv() {
//		List<Harmony> harmonies = new ArrayList<>();
//		harmonies.add(harmony().pos(0).len(DurationConstants.QUARTER).notes(0,4,7).build());
//		harmonies.add(harmony().pos(DurationConstants.QUARTER).len(DurationConstants.QUARTER).notes(11,2,7).build());
//		harmonies.forEach(harmony -> harmony.toChord());
//		double VoiceLeadingValue = voiceLeadingObjective.evaluate(new Motive(harmonies, musicProperties));
//		LOGGER.info("VoiceLeadingValue : " + VoiceLeadingValue);
//		assertEquals("Wrong VoiceLeading value", 3, VoiceLeadingValue, 0);
//	}
//	
//	@Test
//	public void testMajorToMajorRootPosition() {
//		List<Harmony> harmonies = new ArrayList<>();
//		harmonies.add(harmony().pos(0).len(DurationConstants.QUARTER).notes(0,4,7).build());
//		harmonies.add(harmony().pos(DurationConstants.QUARTER).len(DurationConstants.QUARTER).notes(11,2,7).build());
//		harmonies.forEach(harmony -> harmony.toChord());
//		double VoiceLeadingValue = voiceLeadingObjective.evaluate(new Motive(harmonies, musicProperties));
//		LOGGER.info("VoiceLeadingValue : " + VoiceLeadingValue);
//		assertEquals("Wrong VoiceLeading value", 3, VoiceLeadingValue, 0);
//	}
//	
//	@Test
//	public void testProgression(){
//		List<Harmony> harmonies = new ArrayList<>();
//		harmonies.add(harmony().pos(0).len(DurationConstants.EIGHT).notes(0,4,7).build());
//		harmonies.add(harmony().pos(6).len(DurationConstants.EIGHT).notes(10,2,7).build());
//		harmonies.add(harmony().pos(DurationConstants.QUARTER).len(DurationConstants.EIGHT).notes(11,2,7).build());
//		harmonies.add(harmony().pos(DurationConstants.THREE_EIGHTS).len(DurationConstants.EIGHT).notes(0,4,7).build());
//		harmonies.forEach(harmony -> harmony.toChord());
//		double VoiceLeadingValue = voiceLeadingObjective.evaluate(new Motive(harmonies, musicProperties));
//		LOGGER.info("VoiceLeadingValue : " + VoiceLeadingValue);
//		assertEquals("Wrong VoiceLeading value", 8/3d, VoiceLeadingValue, 0);
//	}

}
