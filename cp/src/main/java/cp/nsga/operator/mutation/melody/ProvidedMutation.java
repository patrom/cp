package cp.nsga.operator.mutation.melody;

import cp.composition.Composition;
import cp.composition.voice.Voice;
import cp.composition.voice.VoiceConfig;
import cp.model.TimeLine;
import cp.model.harmony.DependantHarmony;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.melody.Tonality;
import cp.model.note.Note;
import cp.model.texture.TextureConfig;
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

/**
 * Created by prombouts on 13/06/2017.
 */
@Component(value = "providedMutation")
public class ProvidedMutation implements MutationOperator<MelodyBlock> {

    private static Logger LOGGER = LoggerFactory.getLogger(ProvidedMutation.class);

    private double probabilityProvided;

    @Autowired
    private VoiceConfig voiceConfig;
    @Autowired
    private TextureConfig textureConfig;
    @Autowired
    private Composition composition;
    @Autowired
    private MelodyTransformer melodyTransformer;
    @Autowired
    private TimeLine timeLine;

    @Autowired
    public ProvidedMutation(@Value("${probabilityProvided}") double probabilityProvided) {
        this.probabilityProvided = probabilityProvided;
    }

    public void doMutation(MelodyBlock melodyBlock) {
        if (PseudoRandom.randDouble() < probabilityProvided) {
            Optional<CpMelody> optionalMelody = melodyBlock.getRandomMelody(m -> m.isReplaceable());
            if (optionalMelody.isPresent()) {
                CpMelody melody = optionalMelody.get();
                Voice voiceConfiguration = voiceConfig.getVoiceConfiguration(melody.getVoice());
                List<CpMelody> provideMelodies = voiceConfiguration.getMelodyProvider().getMelodies().stream().filter(m -> m.getBeatGroupLength() == melody.getBeatGroupLength()).collect(toList());
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
                    if (providedMelody.getTonality() == Tonality.TONAL && providedMelody.getTimeLineKey() != null) {
                        providedMelody.convertToTimelineKey(timeLine);
                    }
                    melodyTransformer.transform(providedMelody);
//                    LOGGER.info("Provided melody:" + melody.getVoice());
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
