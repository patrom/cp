package cp.model.melody;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.config.BeatGroupConfig;
import cp.model.TimeLine;
import cp.model.note.Scale;
import cp.out.print.Keys;
import org.apache.commons.lang.ArrayUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DefaultConfig.class, VariationConfig.class, BeatGroupConfig.class})
@ExtendWith(SpringExtension.class)
public class ScaleMelodyTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScaleMelodyTest.class);

    @Autowired
    private Keys keys;

    @MockBean
    private TimeLine timeLine;

    @Test
    public void testScaleTranspose(){
        int[] pitchClassesMotive = {0,2,5,4};
        int[] indexes = Scale.MAJOR_SCALE.getIndexes(pitchClassesMotive);
        int steps = 0;
        List<Integer> pitchClasses = Scale.MELODIC_MINOR_SCALE.getPitchClasses(indexes, steps, keys.A);
        pitchClasses.forEach(pc -> System.out.println(pc));
        assertEquals(9, pitchClasses.get(0).intValue());
        assertEquals(11, pitchClasses.get(1).intValue());
        assertEquals(2, pitchClasses.get(2).intValue());
        assertEquals(0, pitchClasses.get(3).intValue());
    }

    @Test
    public void testScaleInverse(){
        int[] pitchClassesMotive = {0,2,5,4};
        int[] indexes = Scale.MAJOR_SCALE.getIndexes(pitchClassesMotive);
        int[] inversedIndexes = new int[indexes.length];
        for (int i = 0; i < indexes.length; i++) {
            int index = indexes[i];
            int inversedIndex = Scale.MAJOR_SCALE.getInversedIndex(1,index);
            inversedIndexes[i] = inversedIndex;
        }
        int steps = 1;
        List<Integer> pitchClasses = Scale.HARMONIC_MINOR_SCALE.getPitchClasses(inversedIndexes, steps, keys.A);
        pitchClasses.forEach(pc -> System.out.println(pc));
        assertEquals(11, pitchClasses.get(0).intValue());
        assertEquals(9, pitchClasses.get(1).intValue());
        assertEquals(5, pitchClasses.get(2).intValue());
        assertEquals(8, pitchClasses.get(3).intValue());
    }

    @Test
    public void testScaleRetogradeTranspose(){
        int[] pitchClassesMotive = {0,2,5,4};
        ArrayUtils.reverse(pitchClassesMotive);
        int[] indexes = Scale.MAJOR_SCALE.getIndexes(pitchClassesMotive);
        int steps = 0;
        List<Integer> pitchClasses = Scale.MELODIC_MINOR_SCALE.getPitchClasses(indexes, steps, keys.A);
        pitchClasses.forEach(pc -> System.out.println(pc));
        assertEquals(0, pitchClasses.get(0).intValue());
        assertEquals(2, pitchClasses.get(1).intValue());
        assertEquals(11, pitchClasses.get(2).intValue());
        assertEquals(9, pitchClasses.get(3).intValue());
    }


    @Test
    public void testScaleRetrogradeInverse(){
        int[] pitchClassesMotive = {0,2,5,4};
        ArrayUtils.reverse(pitchClassesMotive);
        int[] indexes = Scale.MAJOR_SCALE.getIndexes(pitchClassesMotive);
        int[] inversedIndexes = new int[indexes.length];
        for (int i = 0; i < indexes.length; i++) {
            int index = indexes[i];
            int inversedIndex = Scale.MAJOR_SCALE.getInversedIndex(1,index);
            inversedIndexes[i] = inversedIndex;
        }
        int steps = 1;
        List<Integer> pitchClasses = Scale.HARMONIC_MINOR_SCALE.getPitchClasses(inversedIndexes, steps, keys.A);
        pitchClasses.forEach(pc -> System.out.println(pc));
        assertEquals(8, pitchClasses.get(0).intValue());
        assertEquals(5, pitchClasses.get(1).intValue());
        assertEquals(9, pitchClasses.get(2).intValue());
        assertEquals(11, pitchClasses.get(3).intValue());
    }

    @Test
    public void testScaleTranspose2(){
        int[] pitchClassesMotive = {0,2,5,4};
        int[] indexes = Scale.MAJOR_SCALE.getIndexes(pitchClassesMotive);
        int steps = 0;
        for (int i = 0; i < 7; i++) {
            List<Integer> pitchClasses = Scale.HARMONIC_MINOR_SCALE.getPitchClasses(indexes, i, keys.D);
            pitchClasses.forEach(pc -> System.out.println(pc));
            System.out.println("-----------------");
        }

    }


}
