package cp.objective.meter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cp.DefaultConfig;
import cp.composition.Composition;
import cp.composition.timesignature.TimeConfig;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DefaultConfig.class)
public class MeterObjectiveTest {
	
	@Autowired
	@InjectMocks
	private MeterObjective meterObjective;
	@Mock
	private Composition composition;
	@Autowired
	@Qualifier(value="time44")
	private TimeConfig time44;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testEvaluate() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetProfileMergedMelodies() {
		when(composition.getTimeConfig()).thenReturn(time44);
		List<Integer> positions = new ArrayList<>();
		positions.add(0);
		positions.add(12);
		positions.add(24);
		positions.add(36);
		positions.add(48);
		positions.add(60);
		double profileAverage = meterObjective.getProfileMergedMelodiesAverage(positions);
		assertEquals(1.0 , profileAverage, 0);
	}


}
