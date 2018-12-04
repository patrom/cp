package cp.composition.voice;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
@Component
public class BalanceVoice4 extends Voice {

    @PostConstruct
    public void init() {
        setTimeconfig();
        allBeatgroups = Arrays.asList(
                beatgroups.beatGroupBalance30
        );
    }
}