package cp.config;

import cp.composition.voice.*;
import cp.generator.pitchclass.PitchClassGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by prombouts on 20/03/2017.
 */
@Component
public class VoiceConfig {

    @Autowired
    private MelodyVoice melodyVoice;
    @Autowired
    private HomophonicVoice homophonicVoice;
    @Autowired
    private BassVoice bassVoice;
    @Autowired
    private FixedVoice fixedVoice;
    @Autowired
    private DoubleTimeVoice doubleTimeVoice;
    @Autowired
    private HarmonyVoice harmonyVoice;
    @Autowired
    private RowVoice rowVoice;
    @Autowired
    private ProvidedVoice providedVoice;
    @Autowired
    private ProvidedDoubleVoice providedDoubleVoice;
    @Autowired
    private TimeVoice timeVoice;
    @Autowired
    private ProvidedRhythmVoice providedRhythmVoice;

    private Map<Integer, Voice> voiceConfiguration = new TreeMap<>();

    public Voice getVoiceConfiguration(int voice){
        Voice voiceConfig = voiceConfiguration.get(voice);
        if (voiceConfig == null){
            throw new IllegalStateException("VoiceConfig not available for voice: " + voice);
        }
        return voiceConfig;
    }

    @PostConstruct
    public void initVoiceConfig(){
        voiceConfiguration.put(0, homophonicVoice);
        voiceConfiguration.put(1, homophonicVoice);
        voiceConfiguration.put(2, homophonicVoice);
        voiceConfiguration.put(3, providedVoice);
        voiceConfiguration.put(4, providedVoice);

    }

    public void addVoiceConfiguration(int voice, Voice voiceConfig){
        voiceConfiguration.put(voice, voiceConfig);
    }

    public PitchClassGenerator getRandomPitchClassGenerator(int voice) {
        return voiceConfiguration.get(voice).getRandomPitchClassGenerator();
    }



}
