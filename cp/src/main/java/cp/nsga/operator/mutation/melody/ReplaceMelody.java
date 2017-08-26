package cp.nsga.operator.mutation.melody;

import cp.composition.beat.BeatGroup;
import cp.composition.voice.Voice;
import cp.config.TextureConfig;
import cp.config.VoiceConfig;
import cp.generator.pitchclass.PitchClassGenerator;
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

@Component(value="replaceMelody")
public class ReplaceMelody implements MutationOperator<MelodyBlock> {

	private static Logger LOGGER = LoggerFactory.getLogger(ReplaceMelody.class);

    private double probabilityReplaceMelody;

	@Autowired
	private VoiceConfig voiceConfig;
	@Autowired
	private TextureConfig textureConfig;

	@Autowired
    public ReplaceMelody(@Value("${probabilityReplaceMelody}") double probabilityReplaceMelody) {
        this.probabilityReplaceMelody = probabilityReplaceMelody;
    }

    //all rhythm combinations and pitches
    public void doMutation(MelodyBlock melodyBlock) {
		if (PseudoRandom.randDouble() < probabilityReplaceMelody) {
			Optional<CpMelody> optionalMelody = melodyBlock.getRandomMelody(m -> m.isReplaceable());
			if (optionalMelody.isPresent()) {
				CpMelody melody = optionalMelody.get();
				Voice voice = voiceConfig.getVoiceConfiguration(melodyBlock.getVoice());
				BeatGroup beatGroup = voice.getTimeConfig().getRandomBeatgroup();
                if(melody.getBeatGroup().getBeatLength() == beatGroup.getBeatLength()){
//                    LOGGER.info("Melody replaced: " + melody.getVoice() + ", " + beatGroup.getBeatLength());
					List<Note> melodyNotes = voice.getRhythmNotesForBeatgroupType(beatGroup, melody.getNotesSize());
					melodyNotes.forEach(n -> {
						n.setVoice(melody.getVoice());
						n.setDynamic(voice.getDynamic());
						n.setDynamicLevel(voice.getDynamic().getLevel());
						n.setTechnical(voice.getTechnical());
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
					PitchClassGenerator pitchClassGenerator = voiceConfig.getRandomPitchClassGenerator(melody.getVoice());
					melodyNotes = pitchClassGenerator.updatePitchClasses(melodyNotes);
					melody.updateNotes(melodyNotes);
					melody.setBeatGroup(beatGroup);
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
