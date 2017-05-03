package cp.util;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.model.note.Scale;
import cp.out.print.note.Key;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by prombouts on 26/04/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {DefaultConfig.class, VariationConfig.class})
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


}