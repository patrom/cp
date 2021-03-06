package cp.util;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.config.BeatGroupConfig;
import cp.model.note.Scale;
import cp.out.print.note.Key;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

/**
 * Created by prombouts on 26/04/2017.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {DefaultConfig.class, VariationConfig.class, BeatGroupConfig.class})
@TestPropertySource(properties = "composition.voices=4")
public class UtilTest {

    @Autowired
    private Key E;

    @Test
    public void test(){
        Scale scale = Scale.MAJOR_SCALE;
        for (int pc : scale.getPitchClasses()) {
            System.out.println(Util.convertToKeyOfC(pc, E.getInterval() ));
        }
    }

    @Test
    public void rotate(){
        int[] pitchClasses = Scale.MAJOR_SCALE.getPitchClasses();
        System.out.println(Arrays.toString(Util.rotateArray(pitchClasses,2)));
    }
}