package cp.composition.voice;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;


@Component
public class AccompVoice2 extends Voice {

    @PostConstruct
    public void init(){
        allBeatgroups = Arrays.asList(
                beatgroups.beatGroupAccomp2
//                beatgroups.beatGroupHomophonicFour
//                beatgroups.beatGroupMotiveTwo,
//                beatgroups.beatGroupMotiveThree,
//                beatgroups.beatGroupMotiveFour
        );
        setTimeconfig();
    }

}