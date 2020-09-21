package cp.config;

import cp.config.map.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.TreeMap;

@Component
public class CompositionMapConfig {

    private Map<Integer, CompositionMap> compositionMap = new TreeMap<>();

    @Autowired
    private MelodyMap melodyMap;
    @Autowired
    private HarmonyMap harmonyMap;
    @Autowired
    private BassMap bassMap;
    @Autowired
    private RhythmMap rhythmMap;

    @PostConstruct
    public void init() {
        compositionMap.put(0, harmonyMap);
        compositionMap.put(1, harmonyMap);
        compositionMap.put(2, harmonyMap);
        compositionMap.put(3, harmonyMap);
        compositionMap.put(4, harmonyMap);
        compositionMap.put(5, melodyMap);
    }

    public CompositionMap getCompositionMapForVoice(int voice){
        return compositionMap.get(voice);
    }

}