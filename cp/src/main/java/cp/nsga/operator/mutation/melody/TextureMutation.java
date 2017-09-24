package cp.nsga.operator.mutation.melody;

import cp.config.TextureConfig;
import cp.config.VoiceConfig;
import cp.model.harmony.DependantHarmony;
import cp.model.melody.CpMelody;
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

/**
 * Created by prombouts on 4/06/2017.
 */
@Component(value = "textureMutation")
public class TextureMutation implements MutationOperator<CpMelody> {

    private static Logger LOGGER = LoggerFactory.getLogger(TextureMutation.class);

    private double probability;

    @Autowired
    private VoiceConfig voiceConfig;
    @Autowired
    private TextureConfig textureConfig;

    @Autowired
    public TextureMutation(@Value("${probabilityTexture}") double probability) {
        this.probability = probability;
    }

    public void doMutation(double probability, CpMelody melody)  {
        if (PseudoRandom.randDouble() < probability) {
            int voice = melody.getVoice();
            List<Note> notesNoRest = melody.getNotesNoRest();
            if (textureConfig.hasTexture(voice) && !notesNoRest.isEmpty()) {
                Note note = RandomUtil.getRandomFromList(notesNoRest);
                List<DependantHarmony> textureTypes = textureConfig.getTextureFor(voice);
                DependantHarmony dependantHarmony = RandomUtil.getRandomFromList(textureTypes);
                note.setDependantHarmony(dependantHarmony);
//				    LOGGER.info("Texture replaced: " + melody.getVoice());
            }
        }
    }

    @Override
    public CpMelody execute(CpMelody melodyBlock) {
        doMutation(probability, melodyBlock);
        return melodyBlock;
    }
}
