package cp.evaluation;

import static cp.model.harmony.HarmonyBuilder.harmony;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cp.AbstractTest;
import cp.DefaultConfig;
import cp.generator.MusicProperties;
import cp.model.Motive;
import cp.model.harmony.Harmony;
import cp.out.instrument.Ensemble;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DefaultConfig.class, loader = SpringApplicationContextLoader.class)
public class FitnessEvaluationTest extends AbstractTest{

	private static Logger LOGGER = Logger.getLogger(FitnessEvaluationTest.class.getName());
	@Autowired
	private FitnessEvaluationTemplate fitnessEvaluation;
	private Motive motive;

	@Before
	public void setUp(){
		musicProperties.setMinimumLength(6);
		musicProperties.setChordSize(3);
		musicProperties.setInstruments(Ensemble.getStringQuartet());
		List<Harmony> harmonies = new ArrayList<>();
		harmonies.add(harmony().pos(0).len(6).notes(0,4,7).positionWeight(1.0).build());
		harmonies.add(harmony().pos(6).len(6).notes(1,4,6).positionWeight(0.5).build());
		harmonies.add(harmony().pos(12).len(12).notes(11,2,7).positionWeight(1.0).build());
		harmonies.add(harmony().pos(24).len(12).notes(0,4,9).positionWeight(0.5).build());
		motive = new Motive(harmonies, musicProperties);
	}
	
	@After
	public void objectivesInfo() {
		LOGGER.info(objectives.toString());
	}
	
	@Test
	public void evaluationTest() {
		objectives = fitnessEvaluation.evaluate(motive);
	}

}
