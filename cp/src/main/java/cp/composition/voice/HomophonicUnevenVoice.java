package cp.composition.voice;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Component
public class HomophonicUnevenVoice extends Voice {

    @PostConstruct
    public void init(){
        allBeatgroups = Arrays.asList(
                beatgroups.beatGroupHomophonicThree
//                beatgroups.beatGroupHomophonicFour
//                beatgroups.beatGroupMotiveTwo
//                beatgroups.beatGroupMotiveThree,
//                beatgroups.beatGroupMotiveFour
        );
        setTimeconfig();
    }

}
