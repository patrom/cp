package cp.objective.meter;

import cp.DefaultConfig;
import cp.composition.Composition;
import cp.composition.timesignature.TimeConfig;
import cp.model.rhythm.DurationConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
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
	public void testGetProfileMergedMelodies() {
		when(composition.getTimeConfig()).thenReturn(time44);
		List<Integer> positions = new ArrayList<>();
		positions.add(0);
		positions.add(DurationConstants.QUARTER);
		positions.add(DurationConstants.HALF);
		positions.add(DurationConstants.HALF + DurationConstants.EIGHT);
		positions.add(DurationConstants.WHOLE);
		positions.add(DurationConstants.WHOLE + DurationConstants.QUARTER);
		double profileAverage = meterObjective.getProfileMergedMelodiesAverage(positions);
		assertEquals(1.0 , profileAverage, 0);
	}


}
