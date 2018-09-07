package cp.composition.voice;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

/**
 * Created by prombouts on 7/01/2017.
 */
@Component
public class HarmonyVoice extends Voice {

    @PostConstruct
    public void init(){
        allBeatgroups = Arrays.asList(beatgroups.beatGroupHarmonyTwo);
        setTimeconfig();
    }

}



