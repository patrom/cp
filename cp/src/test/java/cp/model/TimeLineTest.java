package cp.model;

import cp.DefaultConfig;
import cp.composition.Composition;
import cp.config.InstrumentConfig;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import cp.out.print.note.Key;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Created by prombouts on 8/12/2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DefaultConfig.class)
public class TimeLineTest {

    @Autowired
    private TimeLine timeLine;
    @Autowired
    private Key A;
    @Autowired
    private Key C;
    @MockBean
    private InstrumentConfig instrumentConfig;
    @MockBean
    private Composition composition;

    @Before
    public void setup() {
    }

    @Test
    public void randomKeys() {
        when(instrumentConfig.getSize()).thenReturn(2);
        List<TimeLineKey> timeLineKeys = new ArrayList<>();
        timeLineKeys.add(new TimeLineKey(A, Scale.MAJOR_SCALE, 0 ,0));
        timeLineKeys.add(new TimeLineKey(C, Scale.MAJOR_SCALE, 0 ,0));
        timeLine.randomKeys(timeLineKeys, DurationConstants.WHOLE, DurationConstants.WHOLE, DurationConstants.HALF, DurationConstants.HALF);
//        assertEquals(timeLine.getTimeLineKeyAtPosition(DurationConstants.QUARTER,0).getKey(), A);
//        assertEquals(timeLine.getTimeLineKeyAtPosition(2 * DurationConstants.WHOLE,0).getKey(), A);
    }

    @Test
    public void randomKeysAndDurations() {
        when(instrumentConfig.getSize()).thenReturn(2);
        when(composition.getEnd()).thenReturn(DurationConstants.WHOLE);
        List<TimeLineKey> timeLineKeys = new ArrayList<>();
        timeLineKeys.add(new TimeLineKey(A, Scale.MAJOR_SCALE, 0 ,0));
        timeLineKeys.add(new TimeLineKey(C, Scale.MAJOR_SCALE, 0 ,0));
        List<Integer> durations = new ArrayList<>();
        durations.add(DurationConstants.QUARTER);
        durations.add(DurationConstants.EIGHT);
        durations.add(DurationConstants.HALF);
        timeLine.randomKeysAndDurations(timeLineKeys, durations);
//        assertEquals(timeLine.getTimeLineKeyAtPosition(DurationConstants.QUARTER,0).getKey(), A);

    }

}