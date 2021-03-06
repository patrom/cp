package cp.model;

import cp.DefaultConfig;
import cp.composition.Composition;
import cp.config.InstrumentConfig;
import cp.model.note.Scale;
import cp.model.rhythm.DurationConstants;
import cp.out.print.note.Key;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by prombouts on 8/12/2016.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DefaultConfig.class)
@TestPropertySource(properties = "composition.voices=4")
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

    @BeforeEach
    public void setup() {
        timeLine.setEnd(2 * DurationConstants.WHOLE);
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

    @Test
    public void randomKeysAndDurationsForVoice() {
        when(instrumentConfig.getSize()).thenReturn(2);
        List<TimeLineKey> timeLineKeys = new ArrayList<>();
        timeLineKeys.add(new TimeLineKey(A, Scale.MAJOR_SCALE, 0 ,0));
        timeLineKeys.add(new TimeLineKey(C, Scale.MAJOR_SCALE, 0 ,0));
        List<Integer> durations = new ArrayList<>();
        durations.add(DurationConstants.QUARTER);
//		durations.add(DurationConstants.SIX_EIGHTS);
        durations.add(DurationConstants.HALF);
//		durations.add(DurationConstants.WHOLE);
        timeLine.randomKeysAndDurationsForVoice(0, timeLineKeys, durations);

        timeLine.getKeysPerVoice().get(0).forEach(timeLineKey -> System.out.println(timeLineKey.getKey() + ", " + timeLineKey.getStart()));
//        assertEquals(timeLine.getTimeLineKeyAtPosition(DurationConstants.QUARTER,0).getKey(), A);
//        assertEquals(timeLine.getTimeLineKeyAtPosition(2 * DurationConstants.WHOLE,0).getKey(), A);
    }

    @Test
    public void addTimeLineKey() {
        timeLine.addTimeLineKey(1, A, Scale.MAJOR_SCALE , DurationConstants.WHOLE);
        timeLine.addTimeLineKey(1, C, Scale.DORIAN_SCALE , DurationConstants.QUARTER);
        timeLine.addTimeLineKey(1, A, Scale.OCTATCONIC_02 , DurationConstants.HALF);
        timeLine.addTimeLineKey(1, C, Scale.HARMONIC_MINOR_SCALE , DurationConstants.QUARTER);

        Map<Integer, List<TimeLineKey>> keysPerVoice = timeLine.getKeysPerVoice();
        List<TimeLineKey> timeLineKeys = keysPerVoice.get(1);
        assertEquals(Scale.MAJOR_SCALE, timeLineKeys.get(0).getScale());
        assertEquals(Scale.DORIAN_SCALE, timeLineKeys.get(1).getScale());
        assertEquals(Scale.OCTATCONIC_02, timeLineKeys.get(2).getScale());
        assertEquals(Scale.HARMONIC_MINOR_SCALE, timeLineKeys.get(3).getScale());

        assertEquals(0, timeLineKeys.get(0).getStart());
        assertEquals(DurationConstants.WHOLE, timeLineKeys.get(1).getStart());
        assertEquals(DurationConstants.WHOLE + DurationConstants.QUARTER, timeLineKeys.get(2).getStart());
        assertEquals(DurationConstants.WHOLE + DurationConstants.HALF +
                DurationConstants.QUARTER, timeLineKeys.get(3).getStart());

        assertEquals(DurationConstants.WHOLE, timeLineKeys.get(0).getEnd());
        assertEquals(DurationConstants.WHOLE + DurationConstants.QUARTER, timeLineKeys.get(1).getEnd());
        assertEquals(DurationConstants.WHOLE + DurationConstants.HALF +
                DurationConstants.QUARTER, timeLineKeys.get(2).getEnd());
        assertEquals(DurationConstants.WHOLE * 2 , timeLineKeys.get(3).getEnd());
    }

    @Test
    public void getTimeLineKeys() {
        timeLine.addTimeLineKey(1, A, Scale.MAJOR_SCALE, DurationConstants.WHOLE);
        timeLine.addTimeLineKey(1, C, Scale.DORIAN_SCALE, DurationConstants.QUARTER);
        timeLine.addTimeLineKey(1, A, Scale.OCTATCONIC_02, DurationConstants.HALF);
        timeLine.addTimeLineKey(1, C, Scale.HARMONIC_MINOR_SCALE, DurationConstants.QUARTER);

        List<TimeLineKey> timelineKeys = timeLine.getTimelineKeys(1, DurationConstants.HALF, DurationConstants.WHOLE + DurationConstants.HALF);
        Assertions.assertEquals(3, timelineKeys.size());
        assertEquals(Scale.MAJOR_SCALE, timelineKeys.get(0).getScale());
        assertEquals(Scale.DORIAN_SCALE, timelineKeys.get(1).getScale());
        assertEquals(Scale.OCTATCONIC_02, timelineKeys.get(2).getScale());
    }

    @Test
    public void getTimeLineKeys2() {
        timeLine.addTimeLineKey(1, A, Scale.MAJOR_SCALE, DurationConstants.WHOLE * 2);

        List<TimeLineKey> timelineKeys = timeLine.getTimelineKeys(1, DurationConstants.HALF, DurationConstants.WHOLE + DurationConstants.HALF);
        Assertions.assertEquals(1, timelineKeys.size());
        assertEquals(Scale.MAJOR_SCALE, timelineKeys.get(0).getScale());
    }

    @Test
    public void randomKeysAndDurationsForVoices() {
        List<TimeLineKey> timeLineKeysForVoices = new ArrayList<>();
        timeLineKeysForVoices.add(new TimeLineKey(0, C, new Scale(new int[]{3, 4}), 0 ,0));
        timeLineKeysForVoices.add(new TimeLineKey(3, C,  new Scale(new int[]{5, 6}), 0 ,0));

        List<TimeLineKey> timeLineKeysForVoices2 = new ArrayList<>();
        timeLineKeysForVoices2.add(new TimeLineKey(0, C, new Scale(new int[]{1, 2}), 0 ,0));
        timeLineKeysForVoices2.add(new TimeLineKey(3, C,  new Scale(new int[]{7, 8}), 0 ,0));

        List<Integer> durations = new ArrayList<>();
//		durations.add(DurationConstants.QUARTER);
//		durations.add(DurationConstants.SIX_EIGHTS);
		durations.add(DurationConstants.HALF);
        durations.add(DurationConstants.WHOLE);
        timeLine.randomKeysAndDurationsForVoices(durations, timeLineKeysForVoices, timeLineKeysForVoices2);
        Map<Integer, List<TimeLineKey>> keysPerVoice = timeLine.getKeysPerVoice();
        for (Map.Entry<Integer, List<TimeLineKey>> entry : keysPerVoice.entrySet()) {
            System.out.println(entry.getKey());
            entry.getValue().forEach(timeLineKey -> {
                System.out.println(timeLineKey.getStart() + ", " + timeLineKey.getEnd());
                System.out.println(Arrays.toString(timeLineKey.getScale().getPitchClasses()));
            });
        }

    }

}