package cp.generator.provider;

import cp.composition.beat.BeatGroup;
import cp.composition.voice.NoteSizeValueObject;
import cp.composition.voice.Voice;
import cp.composition.voice.VoiceConfig;
import cp.generator.pitchclass.PassingPitchClassesProvidedGenerator;
import cp.generator.pitchclass.PitchClassProvidedGenerator;
import cp.generator.pitchclass.RandomPitchClassesProvidedGenerator;
import cp.generator.pitchclass.RepeatingPitchClassesProvidedGenerator;
import cp.model.TimeLineKey;
import cp.model.melody.CpMelody;
import cp.model.melody.Tonality;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.out.print.Keys;
import cp.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prombouts on 12/05/2017.
 */
@Component(value = "melodyGeneratorProvider")
public class MelodyGeneratorProvider implements MelodyProvider{

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

    private List<PitchClassProvidedGenerator> pitchClassProvidedGenerators;

    private List<CpMelody> melodies = new ArrayList<>();

    public List<CpMelody> getMelodies(){
        if (melodies.isEmpty()) {
            pitchClassProvidedGenerators = new ArrayList<>();
            pitchClassProvidedGenerators.add(randomPitchClassesProvidedGenerator::randomPitchClasses);
            pitchClassProvidedGenerators.add(passingPitchClassesProvidedGenerator::updatePitchClasses);
            pitchClassProvidedGenerators.add(repeatingPitchClassesProvidedGenerator::updatePitchClasses);
            int voice = 2;

            for (int i = 0; i < 3; i++) {
                melodies.add(generateMelodyConfig(voice));
            }
            return melodies;
        } else {
            return melodies;
        }
    }

    protected CpMelody generateMelodyConfig(int voice) {
        Voice voiceConfig = voiceConfiguration.getVoiceConfiguration(voice);
        BeatGroup beatGroup = voiceConfig.getTimeConfig().getRandomBeatgroup();
        NoteSizeValueObject valueObject = voiceConfig.getRandomRhythmNotesForBeatgroupType(beatGroup);
        List<Note> melodyNotes = valueObject.getRhythmCombination().getNotes(beatGroup.getBeatLength());
        melodyNotes.forEach(n -> {
            n.setVoice(voice);
            n.setDynamic(voiceConfig.getDynamic());
            n.setDynamicLevel(voiceConfig.getDynamic().getLevel());
            n.setTechnical(voiceConfig.getTechnical());
        });
        TimeLineKey timeLineKey = new TimeLineKey(keys.C, Scale.MAJOR_SCALE);//same config as composition scale/key?
        PitchClassProvidedGenerator pitchClassProvidedGenerator = RandomUtil.getRandomFromList(pitchClassProvidedGenerators);
        melodyNotes = pitchClassProvidedGenerator.updatePitchClasses(melodyNotes, timeLineKey);
        CpMelody melody = new CpMelody(melodyNotes, voice, 0,  beatGroup.getBeatLength());
        melody.setBeatGroup(beatGroup);
        melody.setTimeLineKey(timeLineKey);
        melody.setTonality(Tonality.TONAL);
        melody.setNotesSize(valueObject.getKey());
        return melody;
    }
}
