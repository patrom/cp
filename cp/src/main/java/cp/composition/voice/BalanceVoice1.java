package cp.composition.voice;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Component
public class BalanceVoice1 extends Voice {

    @PostConstruct
    public void init() {
        allBeatgroups = Arrays.asList(
                beatgroups.beatGroupBalance30_5gon
        );
        setTimeconfig();
    }
}