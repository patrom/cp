package cp.nsga.operator.mutation.melody;

import cp.composition.voice.NoteSizeValueObject;
import cp.composition.voice.Voice;
import cp.config.MelodyProviderConfig;
import cp.config.TextureConfig;
import cp.config.TimbreConfig;
import cp.config.VoiceConfig;
import cp.generator.pitchclass.PitchClassGenerator;
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

import java.util.ArrayList;
import java.util.List;

@Component(value= "noteSizeMutation")
public class NoteSizeMutation implements MutationOperator<CpMelody> {

	private static Logger LOGGER = LoggerFactory.getLogger(NoteSizeMutation.class);

    private double probabilityNoteSize;

	@Autowired
	private VoiceConfig voiceConfig;
	@Autowired
	private TextureConfig textureConfig;
	@Autowired
	private TimbreConfig timbreConfig;

	@Autowired
	private MelodyProviderConfig melodyProviderConfig;

    @Autowired
    public NoteSizeMutation(@Value("${probabilityNoteSize}") double probabilityNoteSize) {
        this.probabilityNoteSize = probabilityNoteSize;
    }

    //all rhythm combinations and pitches
    public void doMutation(CpMelody melody) {
		if (PseudoRandom.randDouble() < probabilityNoteSize) {
			Voice voice = voiceConfig.getVoiceConfiguration(melody.getVoice());
			Timbre timbreConfigForVoice = timbreConfig.getTimbreConfigForVoice(melody.getVoice());

			List<Note> melodyNotes = new ArrayList<>();
            NoteSizeValueObject noteSizeValueObject = melody.getBeatGroup().getRandomRhythmNotesForBeatgroupType();
            if (noteSizeValueObject.getKey() != melody.getNotesSize()) {
                LOGGER.info(noteSizeValueObject.getKey() != melody.getNotesSize()?"change note size":"");
                melodyNotes = noteSizeValueObject.getNotes();
                if (!melodyNotes.isEmpty()) {
                    melodyNotes.forEach(n -> {
                        n.setVoice(melody.getVoice());
                        n.setDynamic(timbreConfigForVoice.getDynamic());
                        n.setDynamicLevel(timbreConfigForVoice.getDynamic().getLevel());
                        n.setTechnical(timbreConfigForVoice.getTechnical());
                        n.setPosition(n.getPosition() + melody.getStart());
                    });
                    if (textureConfig.hasTexture(melody.getVoice())) {
                        for (Note melodyNote : melodyNotes) {
                            if (!melodyNote.isRest()) {
                                DependantHarmony dependantHarmony = textureConfig.getTextureFor(melodyNote.getVoice(), melodyNote.getPitchClass());
                                melodyNote.setDependantHarmony(dependantHarmony);
                            }
                        }
                    }
                    PitchClassGenerator pitchClassGenerator = voiceConfig.getRandomPitchClassGenerator(melody.getVoice());
                    melodyNotes = pitchClassGenerator.updatePitchClasses(melody);
                    melody.updateNotes(melodyNotes);
                    melody.setNotesSize(noteSizeValueObject.getKey());
//                    LOGGER.info("Note size: " + melody.getNotesSize());
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
