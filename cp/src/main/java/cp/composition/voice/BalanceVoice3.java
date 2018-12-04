package cp.composition.voice;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Component
public class BalanceVoice3 extends Voice {

    @PostConstruct
    public void init() {
        setTimeconfig();
        allBeatgroups = Arrays.asList(
                beatgroups.beatGroupBalance30_6in30gon
        );
    }
}