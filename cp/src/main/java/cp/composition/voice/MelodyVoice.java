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
        allBeatgroups = Arrays.asList(
//                beatgroups.beatGroupMotiveTwo,
//                beatgroups.beatGroupMotiveThree,
//                beatgroups.beatGroupMotiveFour
                beatgroups.beatGroupBalance30
//                beatgroups.beatGroupOne,
//                beatgroups.beatGroupTwo);
//                beatgroups.beatGroupFour

        );
//        allBeatgroups = Arrays.asList(beatgroups.beatGroupMotiveOne, beatgroups.beatGroupMotiveTwo);
        setTimeconfig();
    }

}
