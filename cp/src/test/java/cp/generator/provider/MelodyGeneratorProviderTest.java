package cp.generator.provider;

import cp.DefaultConfig;
import cp.VariationConfig;
import cp.model.melody.CpMelody;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

/**
 * Created by prombouts on 13/05/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {DefaultConfig.class, VariationConfig.class})
public class MelodyGeneratorProviderTest {

    @Autowired
    private MelodyGeneratorProvider melodyGeneratorProvider;

    @Test
    public void testGenerateMelodyConfig(){
        CpMelody melody = melodyGeneratorProvider.generateMelodyConfig(0);
        melody.getNotes().forEach(n -> System.out.println(n));
        System.out.println("Start: " + melody.getStart());
        System.out.println("End: " + melody.getEnd());
        System.out.println("Beatgroup length: " + melody.getBeatGroupLength());
        assertEquals(melody.getEnd(), melody.getBeatGroupLength());

    }
}