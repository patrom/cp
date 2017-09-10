package cp.nsga.operator.mutation.melody;

import cp.composition.Composition;
import cp.composition.voice.Voice;
import cp.config.MelodyProviderConfig;
import cp.config.TextureConfig;
import cp.config.VoiceConfig;
import cp.generator.provider.MelodyProvider;
import cp.model.TimeLine;
import cp.model.harmony.DependantHarmony;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.nsga.operator.mutation.MutationOperator;
import cp.util.RandomUtil;
import jmetal.util.PseudoRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Component(value = "providedSymmetryMutation")
public class ProvidedSymmetryMutation implements MutationOperator<MelodyBlock> {

    private static Logger LOGGER = LoggerFactory.getLogger(ProvidedSymmetryMutation.class);

    private double probabilitySymmetryProvided;

    @Autowired
    private VoiceConfig voiceConfig;
    @Autowired
    private TextureConfig textureConfig;
    @Autowired
    private Composition composition;
//    @Autowired
//    private MelodyTransformer melodyTransformer;
    @Autowired
    private TimeLine timeLine;
    @Autowired
    private MelodyProviderConfig melodyProviderConfig;

    @Autowired
    public ProvidedSymmetryMutation(@Value("${probabilitySymmetryProvided}") double probabilitySymmetryProvided) {
        this.probabilitySymmetryProvided = probabilitySymmetryProvided;
    }

    public void doMutation(MelodyBlock melodyBlock) {
        if (PseudoRandom.randDouble() < probabilitySymmetryProvided) {
            Optional<CpMelody> optionalMelody = melodyBlock.getRandomMelody(m -> m.isReplaceable());
            if (optionalMelody.isPresent()) {
                CpMelody melody = optionalMelody.get();
                Voice voiceConfiguration = voiceConfig.getVoiceConfiguration(melody.getVoice());
                MelodyProvider melodyProviderForVoice = melodyProviderConfig.getMelodyProviderForVoice(melody.getVoice());
                List<CpMelody> provideMelodies = melodyProviderForVoice.getMelodies().stream().filter(m -> m.getBeatGroupLength() == melody.getBeatGroupLength()).collect(toList());
                if (!provideMelodies.isEmpty()) {
                    final Voice voice = voiceConfig.getVoiceConfiguration(melody.getVoice());

                    CpMelody providedMelody = RandomUtil.getRandomFromList(provideMelodies).clone(melody.getVoice());

                    List<Note> melodyNotes = providedMelody.getNotes();
                    melodyNotes.forEach(n -> {
                        n.setVoice(melody.getVoice());
                        n.setDynamic(voice.getDynamic());
                        n.setDynamicLevel(voice.getDynamic().getLevel());
                        n.setTechnical(voice.getTechnical() != null?voice.getTechnical():n.getTechnical());
                        n.setPosition(n.getPosition() + melody.getStart());
                    });
                    if (textureConfig.hasTexture(melodyBlock.getVoice())) {
                        List<DependantHarmony> textureTypes = textureConfig.getTextureFor(melodyBlock.getVoice());
                        for (Note melodyNote : melodyNotes) {
                            if (!melodyNote.isRest()) {
                                DependantHarmony dependantHarmony = RandomUtil.getRandomFromList(textureTypes);
                                melodyNote.setDependantHarmony(dependantHarmony);
                            }
                        }
                    }
//                    providedMelody.setVoice(melody.getVoice());
                    providedMelody.setStart(melody.getStart());
                    providedMelody.setEnd(melody.getEnd());
                    melodyBlock.replaceMelody(melody, providedMelody);

                    //after new positions are set!!
//                    if (providedMelody.getTonality() == Tonality.TONAL && providedMelody.getTimeLineKey() != null) {
//                        providedMelody.convertToTimelineKey(timeLine);
//                    }
                    providedMelody.symmetricalInverse(composition.axisHigh,composition.axisLow);
//                    melodyTransformer.transform(providedMelody);
//                    LOGGER.info("Provided symmetry melody:" + melody.getVoice());
                }
            }
        }
    }

    @Override
    public MelodyBlock execute(MelodyBlock melodyBlock) {
        doMutation(melodyBlock);
        return melodyBlock;
    }
}

