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
    private Map<Integer, MelodyHarmonicDissonance> meldodyHarmonicConfigs = new TreeMap<>();

    @Autowired
    private MelodyDefaultDissonance melodyDefaultDissonance;
    @Autowired
    private PentatonicMelodyDissonance pentatonicMelodyDissonance;
    @Autowired
    private ChromaticMelodyDissonance chromaticMelodyDissonance;
    @Autowired
    private ChordalMelodyDissonance chordalMelodyDissonance;
    @Autowired
    private MelodyHarmoniceTriChordalDissonance melodyHarmoniceTriChordalDissonance;
    @Autowired
    private MelodyHarmoniceTetraChordalDissonance melodyHarmoniceTetraChordalDissonance;

    @PostConstruct
    public void init() {
        //voice!!!
        meldodyConfigs.put(0, melodyDefaultDissonance);
        meldodyConfigs.put(1, melodyDefaultDissonance);
        meldodyConfigs.put(2, melodyDefaultDissonance);
        meldodyConfigs.put(3, melodyDefaultDissonance);
        meldodyConfigs.put(4, pentatonicMelodyDissonance);
        meldodyConfigs.put(5, pentatonicMelodyDissonance);

        MelodicHarmonicTriadDissonance melodicHarmonicTriadDissonance = new MelodicHarmonicTriadDissonance("3-1", "3-2","3-3","3-4","3-5" );
        meldodyHarmonicConfigs.put(0, melodicHarmonicTriadDissonance);
        meldodyHarmonicConfigs.put(1, melodicHarmonicTriadDissonance);
        meldodyHarmonicConfigs.put(2, melodicHarmonicTriadDissonance);
        meldodyHarmonicConfigs.put(3, melodyHarmoniceTriChordalDissonance);
        meldodyHarmonicConfigs.put(4, melodyHarmoniceTriChordalDissonance);
        meldodyHarmonicConfigs.put(5, melodyHarmoniceTriChordalDissonance);
    }

    public MelodyDissonance getMelodyDissonanceForVoice(int voice){
        return meldodyConfigs.get(voice);
    }

    public MelodyHarmonicDissonance getMelodyHarmonicDissonanceForVoice(int voice){
        return meldodyHarmonicConfigs.get(voice);
    }
}
