package cp.nsga.operator.mutation.melody;

import cp.config.CompositionMapConfig;
import cp.config.TextureConfig;
import cp.config.VoiceConfig;
import cp.config.map.CompositionMap;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prombouts on 4/06/2017.
 */
@Component(value = "textureMutation")
public class TextureMutation implements MutationOperator<MelodyBlock> {

    private static Logger LOGGER = LoggerFactory.getLogger(TextureMutation.class);

    private double probability;

    @Autowired
    private VoiceConfig voiceConfig;
    @Autowired
    private TextureConfig textureConfig;
//    @Value("${mutation.voices.one}")
//    private List<Integer> mutationVoices = new ArrayList<>();
    @Autowired
    protected CompositionMapConfig compositionMapConfig;

    @Autowired
    public TextureMutation(@Value("${probabilityTexture}") double probability) {
        this.probability = probability;
    }

    public void doMutation(double probability, MelodyBlock melodyBlock) {
        if (PseudoRandom.randDouble() < probability) {
            CompositionMap compositionMap = compositionMapConfig.getCompositionMapForVoice(melodyBlock.getVoice());
            CpMelody melody = compositionMap.getMelody(melodyBlock.getVoice());
            int voice = melody.getVoice();
            List<Note> notesNoRest = melody.getNotesNoRest();
            if (textureConfig.hasTexture(voice) && !notesNoRest.isEmpty()) {
                Note note = RandomUtil.getRandomFromList(notesNoRest);
                DependantHarmony dependantHarmony = textureConfig.getTextureFor(voice, note.getPitchClass());
                note.setDependantHarmony(dependantHarmony);
//                LOGGER.info("Texture replaced: " + melody.getVoice());
//                LOGGER.info("Texture replaced: " + dependantHarmony.getVoicingType());
            }
        }
    }

    @Override
    public MelodyBlock execute(cp.model.melody.MelodyBlock melodyBlock) {
        doMutation(probability, melodyBlock);
        return melodyBlock;
    }

}
