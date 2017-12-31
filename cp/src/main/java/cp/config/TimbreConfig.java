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
        Timbre defaultTimbre = new Timbre(null, Technical.DETACHE_SHORT, Dynamic.MF);
//        defaultTimbre.setDynamics(Stream.of(Dynamic.P, Dynamic.MF, Dynamic.F).collect(toList()););

        //voice!!!
        timbreConfig.put(0, defaultTimbre);
        timbreConfig.put(1, defaultTimbre);
        timbreConfig.put(2, defaultTimbre);
        timbreConfig.put(3,  defaultTimbre);
        timbreConfig.put(4, new Timbre(null, Technical.LEGATO, Dynamic.MF));
        timbreConfig.put(5, new Timbre(Articulation.DETACHED_LEGATO, Technical.LEGATO, Dynamic.MF));
    }

    public Timbre getTimbreConfigForVoice(int voice){
        return timbreConfig.get(voice);
    }

}