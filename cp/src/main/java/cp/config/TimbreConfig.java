package cp.config;

import cp.model.note.Dynamic;
import cp.model.timbre.Timbre;
import cp.out.instrument.Articulation;
import cp.out.instrument.Technical;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.TreeMap;

@Component
public class TimbreConfig {

    private Map<Integer, Timbre> timbreConfig = new TreeMap<>();

    @PostConstruct
    public void init() {
        Timbre defaultTimbre = new Timbre(null, Technical.LEGATO, Dynamic.MF);
        Timbre pp = new Timbre(null, Technical.LEGATO, Dynamic.PP);
//        defaultTimbre.setDynamics(Stream.of(Dynamic.P, Dynamic.MF, Dynamic.F).collect(toList()););

        //voice!!!
        timbreConfig.put(0, pp);
        timbreConfig.put(1, pp);
        timbreConfig.put(2, pp);
        timbreConfig.put(3, pp);
        timbreConfig.put(4, pp);
        timbreConfig.put(5, new Timbre(Articulation.DETACHED_LEGATO, Technical.LEGATO, Dynamic.MF));
    }

    public Timbre getTimbreConfigForVoice(int voice){
        return timbreConfig.get(voice);
    }

}