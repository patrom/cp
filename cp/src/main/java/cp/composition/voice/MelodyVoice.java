package cp.composition.voice;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

/**
 * Created by prombouts on 23/11/2016.
 */
@Component
public class MelodyVoice extends Voice {

    @PostConstruct
    public void init(){

        allBeatgroups = Arrays.asList(beatgroups.beatGroupOneMotive, beatgroups.beatgroupFourMotive);
//        allBeatgroups = Arrays.asList(beatgroups.beatGroupTwo, beatgroups.beatGroupThree, beatgroups.beatGroupFour, beatgroups.beatgroupFourMotive);
        setTimeconfig();
    }

}
