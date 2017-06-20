package cp.generator.provider;

import cp.composition.beat.BeatGroup;
import cp.composition.voice.NoteSizeValueObject;
import cp.composition.voice.Voice;
import cp.composition.voice.VoiceConfig;
import cp.model.TimeLineKey;
import cp.model.melody.CpMelody;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.out.print.Keys;
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

    public List<CpMelody> getMelodies(){
        int voice = 0;
        ArrayList<CpMelody> melodies = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            melodies.add(generateMelodyConfig(voice));
        }
        return melodies;
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
//        melodyNotes = voiceConfiguration.getRandomPitchClassGenerator(voice).updatePitchClasses(melodyNotes);
        CpMelody melody = new CpMelody(melodyNotes, voice, 0,  beatGroup.getBeatLength());
        melody.setBeatGroup(beatGroup);
        melody.setTimeLineKey(new TimeLineKey(keys.C, Scale.MAJOR_SCALE));
        melody.setNotesSize(valueObject.getKey());
        return melody;
    }
}
