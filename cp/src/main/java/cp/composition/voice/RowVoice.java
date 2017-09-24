package cp.composition.voice;

import cp.model.note.Dynamic;
import cp.out.instrument.Technical;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Created by prombouts on 20/05/2017.
 */
@Component
public class RowVoice extends Voice {

    @PostConstruct
    public void init(){
        setTimeconfig();
        dynamics = Stream.of(Dynamic.MF, Dynamic.F).collect(toList());
        technical = Technical.LEGATO;
    }

}
