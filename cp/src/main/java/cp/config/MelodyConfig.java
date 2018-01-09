package cp.config;

import cp.objective.melody.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.TreeMap;

@Component
public class MelodyConfig {

    private Map<Integer, MelodyDissonance> meldodyConfigs = new TreeMap<>();

    @Autowired
    private MelodyDefaultDissonance melodyDefaultDissonance;
    @Autowired
    private PentatonicMelodyDissonance pentatonicMelodyDissonance;
    @Autowired
    private ChromaticeMelodyDissonance chromaticeMelodyDissonance;
    @Autowired
    private ChordalMelodyDissonance chordalMelodyDissonance;

    @PostConstruct
    public void init() {
        //voice!!!
        meldodyConfigs.put(0, melodyDefaultDissonance);
        meldodyConfigs.put(1, melodyDefaultDissonance);
        meldodyConfigs.put(2, chordalMelodyDissonance);
        meldodyConfigs.put(3, melodyDefaultDissonance);
        meldodyConfigs.put(4, melodyDefaultDissonance);
        meldodyConfigs.put(5, melodyDefaultDissonance);
    }

    public MelodyDissonance getMelodyDissonanceForVoice(int voice){
        return meldodyConfigs.get(voice);
    }
}
