package cp.composition.voice;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

/**
 * Created by prombouts on 25/11/2016.
 */
@Component
public class BassVoice extends Voice {

    @PostConstruct
    public void init(){
        allBeatgroups = Arrays.asList(beatgroups.beatGroupFour);
        setTimeconfig();

    }

}
