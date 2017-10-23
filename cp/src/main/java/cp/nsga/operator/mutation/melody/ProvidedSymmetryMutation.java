package cp.nsga.operator.mutation.melody;

import cp.composition.Composition;
import cp.composition.voice.Voice;
import cp.config.MelodyProviderConfig;
import cp.config.TextureConfig;
import cp.config.TimbreConfig;
import cp.config.VoiceConfig;
import cp.generator.provider.MelodyProvider;
import cp.model.TimeLine;
import cp.model.harmony.DependantHarmony;
import cp.model.melody.CpMelody;
import cp.model.note.Note;
import cp.model.timbre.Timbre;
import cp.nsga.operator.mutation.MutationOperator;
import cp.util.RandomUtil;
import jmetal.util.PseudoRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component(value = "providedSymmetryMutation")
public class ProvidedSymmetryMutation implements MutationOperator<CpMelody> {

    private static Logger LOGGER = LoggerFactory.getLogger(ProvidedSymmetryMutation.class);

    private double probabilitySymmetryProvided;

    @Autowired
    private VoiceConfig voiceConfig;
    @Autowired
    private TextureConfig textureConfig;
    @Autowired
    private Composition composition;
    @Autowired
    private TimeLine timeLine;
    @Autowired
    private TimbreConfig timbreConfig;
    @Autowired
    private MelodyProviderConfig melodyProviderConfig;

    @Autowired
    public ProvidedSymmetryMutation(@Value("${probabilitySymmetryProvided}") double probabilitySymmetryProvided) {
        this.probabilitySymmetryProvided = probabilitySymmetryProvided;
    }

    public void doMutation(CpMelody melody) {
        if (PseudoRandom.randDouble() < probabilitySymmetryProvided) {
            int voice = melody.getVoice();
            Voice voiceConfiguration = voiceConfig.getVoiceConfiguration(voice);
            MelodyProvider melodyProviderForVoice = melodyProviderConfig.getMelodyProviderForVoice(voice);
            List<CpMelody> provideMelodies = melodyProviderForVoice.getMelodies(voice).stream().filter(m -> m.getBeatGroupLength() == melody.getBeatGroupLength()).collect(toList());
            if (!provideMelodies.isEmpty()) {
                CpMelody providedMelody = RandomUtil.getRandomFromList(provideMelodies).clone(voice);
                Timbre timbreConfigForVoice = timbreConfig.getTimbreConfigForVoice(voice);

                List<Note> melodyNotes = providedMelody.getNotes();
                melodyNotes.forEach(n -> {
                    n.setVoice(voice);
                    n.setDynamic(timbreConfigForVoice.getDynamic());
                    n.setDynamicLevel(timbreConfigForVoice.getDynamic().getLevel());
                    n.setTechnical(timbreConfigForVoice.getTechnical() != null?timbreConfigForVoice.getTechnical():n.getTechnical());
                    n.setPosition(n.getPosition() + melody.getStart());
                });
                if (textureConfig.hasTexture(voice)) {
                    List<DependantHarmony> textureTypes = textureConfig.getTextureFor(voice);
                    for (Note melodyNote : melodyNotes) {
                        if (!melodyNote.isRest()) {
                            DependantHarmony dependantHarmony = RandomUtil.getRandomFromList(textureTypes);
                            melodyNote.setDependantHarmony(dependantHarmony);
                        }
                    }
                }
                //after new positions are set!!
//                    if (providedMelody.getTonality() == Tonality.TONAL && providedMelody.getTimeLineKey() != null) {
//                        providedMelody.convertToTimelineKey(timeLine);
//                    }
                providedMelody.symmetricalInverse(composition.axisHigh,composition.axisLow);
//                    melodyTransformer.transform(providedMelody);
//                    LOGGER.info("Provided symmetry melody:" + melody.getVoice());
                melody.updateNotes(providedMelody.getNotes());
            }
        }
    }

    @Override
    public CpMelody execute(CpMelody melody) {
        doMutation(melody);
        return melody;
    }
}

