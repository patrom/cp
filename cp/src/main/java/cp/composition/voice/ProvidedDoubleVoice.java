package cp.composition.voice;

import cp.generator.provider.MelodyProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

/**
 * Created by prombouts on 26/06/2017.
 */
@Component
public class ProvidedDoubleVoice extends Voice {

    @Autowired
    @Qualifier(value = "melodyDoubleProvider")//melodyManualProvider - melodyGeneratorProvider
    protected MelodyProvider melodyDoubleProvider;

    @PostConstruct
    public void init(){
        melodiesProvided = true;
        allBeatgroups = Arrays.asList(beatgroups.beatGroupHarmonyTwo);
        setTimeconfig();
    }

}