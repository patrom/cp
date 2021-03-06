package cp.generator.provider;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.config.BeatGroupConfig;
import cp.model.TimeLine;
import cp.model.TimeLineKey;
import cp.model.melody.CpMelody;
import cp.model.note.Scale;
import cp.out.print.Keys;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * Created by prombouts on 13/05/2017.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {DefaultConfig.class, VariationConfig.class, BeatGroupConfig.class})
@TestPropertySource(properties = "composition.voices=4")
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
        Assertions.assertEquals(melody.getEnd(), melody.getBeatGroupLength());

    }
}