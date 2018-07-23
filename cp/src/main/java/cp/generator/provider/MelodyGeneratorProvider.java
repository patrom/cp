package cp.generator.provider;

import cp.composition.beat.BeatGroup;
import cp.composition.voice.Voice;
import cp.config.TimbreConfig;
import cp.config.VoiceConfig;
import cp.generator.MelodyGenerator;
import cp.generator.pitchclass.PassingPitchClassesProvidedGenerator;
import cp.generator.pitchclass.PitchClassProvidedGenerator;
import cp.generator.pitchclass.RandomPitchClassesProvidedGenerator;
import cp.generator.pitchclass.RepeatingPitchClassesProvidedGenerator;
import cp.model.melody.CpMelody;
import cp.model.timbre.Timbre;
import cp.out.print.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by prombouts on 12/05/2017.
 */
@Component(value = "melodyGeneratorProvider")
public class MelodyGeneratorProvider extends AbstractProvidder implements MelodyProvider{

    @Autowired
    private VoiceConfig voiceConfiguration;
    @Autowired
    private Keys keys;
    @Autowired
    private RandomPitchClassesProvidedGenerator randomPitchClassesProvidedGenerator;
    @Autowired
    private PassingPitchClassesProvidedGenerator passingPitchClassesProvidedGenerator;
    @Autowired
    private RepeatingPitchClassesProvidedGenerator repeatingPitchClassesProvidedGenerator;
    @Autowired
    private MelodyGenerator melodyGenerator;
    @Autowired
    private TimbreConfig timbreConfig;

    private List<PitchClassProvidedGenerator> pitchClassProvidedGenerators;

    @PostConstruct
    public void init(){
        pitchClassProvidedGenerators = new ArrayList<>();
        pitchClassProvidedGenerators.add(randomPitchClassesProvidedGenerator::randomPitchClasses);
        pitchClassProvidedGenerators.add(passingPitchClassesProvidedGenerator::updatePitchClasses);
//        pitchClassProvidedGenerators.add(repeatingPitchClassesProvidedGenerator::updatePitchClasses);
    }

    public List<CpMelody> getMelodies(int voice){
        if (melodies.isEmpty()) {
            for (int i = 0; i < 2; i++) {
                melodies.add(generateMelodyConfig(voice));
            }
//            melodies.add(getRest(0, DurationConstants.EIGHT));
//            melodies.add(getNote(0, DurationConstants.EIGHT));
//            melodies.add(getRest(0, DurationConstants.QUARTER));
//            melodies.add(getRest(0, DurationConstants.THREE_EIGHTS));
//            melodies.add(getNote(0, DurationConstants.THREE_EIGHTS));
            return melodies;
        } else {
            return melodies;
        }
    }

    public CpMelody generateMelodyConfig(int voice) {
        Timbre timbreConfigForVoice = timbreConfig.getTimbreConfigForVoice(voice);
        Voice voiceConfig = voiceConfiguration.getVoiceConfiguration(voice);
        BeatGroup beatGroup = voiceConfig.getRandomBeatgroup();
        return melodyGenerator.generateMelodyConfig(voice, 0, beatGroup, voiceConfig, timbreConfigForVoice);
//        return generateMelodyConfig(voice, beatGroup);
    }

}
