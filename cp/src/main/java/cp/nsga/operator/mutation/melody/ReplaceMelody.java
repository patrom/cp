package cp.nsga.operator.mutation.melody;

import cp.composition.beat.BeatGroup;
import cp.composition.voice.Voice;
import cp.config.MelodyProviderConfig;
import cp.config.TextureConfig;
import cp.config.VoiceConfig;
import cp.generator.pitchclass.PitchClassGenerator;
import cp.generator.provider.MelodyProvider;
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

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component(value="replaceMelody")
public class ReplaceMelody implements MutationOperator<CpMelody> {

	private static Logger LOGGER = LoggerFactory.getLogger(ReplaceMelody.class);

    private double probabilityReplaceMelody;

	@Autowired
	private VoiceConfig voiceConfig;
	@Autowired
	private TextureConfig textureConfig;
	@Autowired
	private MelodyProviderConfig melodyProviderConfig;

	@Autowired
    public ReplaceMelody(@Value("${probabilityReplaceMelody}") double probabilityReplaceMelody) {
        this.probabilityReplaceMelody = probabilityReplaceMelody;
    }

    //all rhythm combinations and pitches
    public void doMutation(CpMelody melody) {
		if (PseudoRandom.randDouble() < probabilityReplaceMelody) {
			Voice voice = voiceConfig.getVoiceConfiguration(melody.getVoice());

			BeatGroup beatGroup = voice.getTimeConfig().getRandomBeatgroup();
			if(melody.getBeatGroup().getBeatLength() == beatGroup.getBeatLength()){
				List<Note> melodyNotes = new ArrayList<>();
				if (voice.isMelodiesProvided()) {
					MelodyProvider melodyProviderForVoice = melodyProviderConfig.getMelodyProviderForVoice(melody.getVoice());
					List<CpMelody> provideMelodies = melodyProviderForVoice.getMelodies(melody.getVoice()).stream()
							.filter(m -> m.getBeatGroupLength() == melody.getBeatGroupLength() && m.getMutationType() == melody.getMutationType())
							.collect(toList());
					if (!provideMelodies.isEmpty()) {
						CpMelody providedMelody = RandomUtil.getRandomFromList(provideMelodies).clone(melody.getVoice());
						melodyNotes = providedMelody.getNotes();
					}
				} else {
					melodyNotes = voice.getRhythmNotesForBeatgroupType(beatGroup, melody.getNotesSize());
				}
//                    LOGGER.info("Melody replaced: " + melody.getVoice() + ", " + beatGroup.getBeatLength());

				if (!melodyNotes.isEmpty()) {
					melodyNotes.forEach(n -> {
                        n.setVoice(melody.getVoice());
                        n.setDynamic(voice.getDynamic());
                        n.setDynamicLevel(voice.getDynamic().getLevel());
                        n.setTechnical(voice.getTechnical());
                        n.setPosition(n.getPosition() + melody.getStart());
                    });
					if (textureConfig.hasTexture(melody.getVoice())) {
                        List<DependantHarmony> textureTypes = textureConfig.getTextureFor(melody.getVoice());
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
	public CpMelody execute(CpMelody melody) {
		doMutation(melody);
		return melody;
	}
}
