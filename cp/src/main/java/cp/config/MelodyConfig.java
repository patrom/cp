package cp.config;

import cp.objective.melody.*;
import cp.objective.melody.subset.MelodyMajorScale_7_35;
import cp.objective.melody.subset.MelodySubSet_8_25;
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
    private PartMelodyDissonance partMelodyDissonance;
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
    @Autowired
    private MelodyMajorScale_7_35 melodyMajorScale_7_35;
    @Autowired
    private MelodySubSet_8_25 melodySubSet_8_25;

    @PostConstruct
    public void init() {
        //voice!!!
        meldodyConfigs.put(0, partMelodyDissonance);
        meldodyConfigs.put(1, partMelodyDissonance);
        meldodyConfigs.put(2, melodyDefaultDissonance);
        meldodyConfigs.put(3, melodyDefaultDissonance);
        meldodyConfigs.put(4, melodyDefaultDissonance);
        meldodyConfigs.put(5, pentatonicMelodyDissonance);

//        MelodicHarmonicTriadDissonance melodicHarmonicTriadDissonance = new MelodicHarmonicTriadDissonance("2-6" );
        MelodicHarmonicSetClassDissonance melodicHarmonicSetClassDissonance = new MelodicHarmonicSetClassDissonance(6, "6-7");
//        meldodyHarmonicConfigs.put(0, melodicHarmonicSetClassDissonance);
//        meldodyHarmonicConfigs.put(1, melodicHarmonicSetClassDissonance);
//        meldodyHarmonicConfigs.put(2, melodicHarmonicSetClassDissonance);
//        meldodyHarmonicConfigs.put(3, melodicHarmonicSetClassDissonance);
//        meldodyHarmonicConfigs.put(4, melodicHarmonicSetClassDissonance);
//        meldodyHarmonicConfigs.put(3, melodySubSet_8_25);
//        meldodyHarmonicConfigs.put(4, melodyMajorScale_7_35);
//        meldodyHarmonicConfigs.put(5, melodyMajorScale_7_35);
    }

    public MelodyDissonance getMelodyDissonanceForVoice(int voice){
        return meldodyConfigs.get(voice);
    }

    public MelodyHarmonicDissonance getMelodyHarmonicDissonanceForVoice(int voice){
        return meldodyHarmonicConfigs.get(voice);
    }
}
