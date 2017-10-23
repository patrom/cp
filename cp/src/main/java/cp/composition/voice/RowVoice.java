package cp.composition.voice;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by prombouts on 20/05/2017.
 */
@Component
public class RowVoice extends Voice {

    @PostConstruct
    public void init(){
        setTimeconfig();
    }

}
