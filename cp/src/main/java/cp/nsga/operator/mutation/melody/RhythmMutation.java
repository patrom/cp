package cp.nsga.operator.mutation.melody;

import cp.config.TextureConfig;
import cp.config.TimbreConfig;
import cp.config.VoiceConfig;
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

/**
 * Created by prombouts on 6/05/2017.
 */
@Component(value = "rhythmMutation")
public class RhythmMutation implements MutationOperator<CpMelody> {

    private static Logger LOGGER = LoggerFactory.getLogger(RhythmMutation.class);

    private double probability;

    @Autowired
    private VoiceConfig voiceConfig;
    @Autowired
    private TextureConfig textureConfig;
    @Autowired
    private TimbreConfig timbreConfig;

    @Autowired
    public RhythmMutation(@Value("${probabilityRhythm}") double probability) {
        this.probability = probability;
    }

    public void doMutation(double probability, CpMelody melody)  {
        if (PseudoRandom.randDouble() < probability) {
            int v = melody.getVoice();
            Timbre timbreConfigForVoice = timbreConfig.getTimbreConfigForVoice(v);
            List<Note> rhythmNotes = melody.getBeatGroup().getRhythmNotesForBeatgroupType(melody.getNotesSize());
            if (!rhythmNotes.isEmpty()) {
                rhythmNotes.forEach(n -> {
                    n.setVoice(melody.getVoice());
                    n.setDynamic(timbreConfigForVoice.getDynamic());
                    n.setDynamicLevel(timbreConfigForVoice.getDynamic().getLevel());
                    n.setTechnical(timbreConfigForVoice.getTechnical());
                });
                if (textureConfig.hasTexture(v)) {
                    for (Note melodyNote : rhythmNotes) {
                        if (!melodyNote.isRest()) {
                            DependantHarmony dependantHarmony = textureConfig.getTextureFor(v, melodyNote.getPitchClass());
                            melodyNote.setDependantHarmony(dependantHarmony);
                        }
                    }
                }
                melody.updateRhythmNotes(rhythmNotes);
//			LOGGER.info("RhythmMutation: " + melody.getVoice());
            }
        }
    }

    @Override
    public CpMelody execute(CpMelody melody) {
        doMutation(probability, melody);
        return melody;
    }
}
