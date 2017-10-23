package cp.config;

import cp.model.note.Dynamic;
import cp.model.timbre.Timbre;
import cp.out.instrument.Articulation;
import cp.out.instrument.Technical;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.TreeMap;

@Component
public class TimbreConfig {

    private Map<Integer, Timbre> timbreConfig = new TreeMap<>();

    @Autowired
    private Timbre timbre;

    @PostConstruct
    public void init() {
        Timbre defaultTimbre = new Timbre(null, Technical.LEGATO, Dynamic.MF);
//        defaultTimbre.setDynamics(Stream.of(Dynamic.P, Dynamic.MF, Dynamic.F).collect(toList()););

        //voice!!!
        timbreConfig.put(0, new Timbre(Articulation.DETACHED_LEGATO, Technical.LEGATO, Dynamic.PP));
        timbreConfig.put(1, defaultTimbre);
        timbreConfig.put(2, new Timbre(Articulation.DETACHED_LEGATO, Technical.PORTATO, Dynamic.F));
        timbreConfig.put(3, new Timbre(Articulation.DETACHED_LEGATO, Technical.PIZZ, Dynamic.MF));
        timbreConfig.put(4, new Timbre(Articulation.DETACHED_LEGATO, Technical.LEGATO, Dynamic.MF));
    }

    public Timbre getTimbreConfigForVoice(int voice){
        return timbreConfig.get(voice);
    }

}