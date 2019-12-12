package cp.objective.meter;

import cp.DefaultConfig;
import cp.composition.Composition;
import cp.composition.timesignature.TimeConfig;
import cp.model.rhythm.DurationConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DefaultConfig.class)
@TestPropertySource(properties = "composition.voices=4")
public class MeterObjectiveTest {
	
	@Autowired
	private MeterObjective meterObjective;
	@MockBean(name = "fourVoiceComposition")
	private Composition composition;
	@Autowired
	@Qualifier(value="time44")
	private TimeConfig time44;

	@BeforeEach
	public void setUp() throws Exception {
		meterObjective.setComposition(composition);
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
        Assertions.assertEquals(1.0 , profileAverage, 0);
	}


}
