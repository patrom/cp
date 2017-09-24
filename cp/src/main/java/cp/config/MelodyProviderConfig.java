package cp.config;

import cp.generator.provider.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.TreeMap;

@Component
public class MelodyProviderConfig {

    private Map<Integer, MelodyProvider> melodyProviderConfigs = new TreeMap<>();

    @Autowired
    private MelodyManualProvider melodyManualProvider;
    @Autowired
    private MelodyGeneratorProvider melodyGeneratorProvider;
    @Autowired
    private MelodyDoubleProvider melodyDoubleProvider;
    @Autowired
    private MelodyParserProvider melodyParserProvider;
    @Autowired
    private MelodyRhythmProvider melodyRhythmProvider;

    @PostConstruct
    public void init() {
        //voice!!!
        melodyProviderConfigs.put(0, melodyParserProvider);
        melodyProviderConfigs.put(1, melodyParserProvider);
        melodyProviderConfigs.put(2, melodyGeneratorProvider);
        melodyProviderConfigs.put(3, melodyGeneratorProvider);
        melodyProviderConfigs.put(4, melodyGeneratorProvider);
    }

    public MelodyProvider getMelodyProviderForVoice(int voice){
        return melodyProviderConfigs.get(voice);
    }
}