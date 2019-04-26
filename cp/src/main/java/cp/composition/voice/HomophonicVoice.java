package cp.composition.voice;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

/**
 * Created by prombouts on 23/11/2016.
 */
@Component
public class HomophonicVoice extends Voice {

    @PostConstruct
    public void init(){
        allBeatgroups = Arrays.asList(
//                beatgroups.beatGroupHomophonicThree
                beatgroups.beatGroupHomophonicFour
//                beatgroups.beatGroupMotiveTwo
//                beatgroups.beatGroupMotiveThree,
//                beatgroups.beatGroupMotiveFour
        );
        setTimeconfig();
    }

}
