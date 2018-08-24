package cp.composition.voice;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by prombouts on 5/12/2016.
 */
@Component
public class DoubleTimeVoice extends Voice {

    @PostConstruct
    public void init() {
        setTimeconfig();

    }


}
