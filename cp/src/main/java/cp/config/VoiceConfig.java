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
    private HomophonicUnevenVoice homophonicUnevenVoice;
    @Autowired
    private BassVoice bassVoice;
    @Autowired
    private DoubleTimeVoice doubleTimeVoice;
    @Autowired
    private HarmonyVoice harmonyVoice;
    @Autowired
    private RowVoice rowVoice;
    @Autowired
    private ProvidedDoubleVoice providedDoubleVoice;

    @Autowired
    private BalanceVoice1 balanceVoice1;
    @Autowired
    private BalanceVoice2 balanceVoice2;
    @Autowired
    private BalanceVoice3 balanceVoice3;
    @Autowired
    private BalanceVoice4 balanceVoice4;
    @Autowired
    private AccompVoice1 accompVoice1;
    @Autowired
    private AccompVoice2 accompVoice2;
    @Autowired
    private AccompVoice3 accompVoice3;

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
        voiceConfiguration.put(2, homophonicUnevenVoice);
        voiceConfiguration.put(3, homophonicVoice);
        voiceConfiguration.put(4, homophonicVoice);
        voiceConfiguration.put(5, melodyVoice);

    }

    public void addVoiceConfiguration(int voice, Voice voiceConfig){
        voiceConfiguration.put(voice, voiceConfig);
    }

    public PitchClassGenerator getRandomPitchClassGenerator(int voice) {
        return voiceConfiguration.get(voice).getRandomPitchClassGenerator();
    }



}
