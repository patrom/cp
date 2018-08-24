package cp.generator.provider;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.composition.beat.BeatGroupConfig;
import cp.model.TimeLine;
import cp.model.TimeLineKey;
import cp.model.melody.CpMelody;
import cp.model.note.Scale;
import cp.out.print.Keys;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * Created by prombouts on 13/05/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DefaultConfig.class, VariationConfig.class, BeatGroupConfig.class})
public class MelodyGeneratorProviderTest {

    @Autowired
    private MelodyGeneratorProvider melodyGeneratorProvider;

    @MockBean
    private TimeLine timeLine;
    @Autowired
    private Keys keys;

    @Test
    public void testGenerateMelodyConfig(){
        TimeLineKey timeLineKey = new TimeLineKey(keys.A, Scale.MAJOR_SCALE);
        when(timeLine.getTimeLineKeyAtPosition(anyInt(), anyInt())).thenReturn(timeLineKey);
        CpMelody melody = melodyGeneratorProvider.generateMelodyConfig(0);
        melody.getNotes().forEach(n -> System.out.println(n));
        System.out.println("Start: " + melody.getStart());
        System.out.println("End: " + melody.getEnd());
        System.out.println("Beatgroup length: " + melody.getBeatGroupLength());
        assertEquals(melody.getEnd(), melody.getBeatGroupLength());

    }
}