package cp.generator.provider;

import cp.composition.beat.BeatGroup;
import cp.composition.voice.Voice;
import cp.composition.voice.VoiceConfig;
import cp.model.melody.CpMelody;
import cp.model.note.Note;
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
        BeatGroup beatGroup = voiceConfig.getTimeConfig().getBeatGroup(0);
        List<Note> melodyNotes = voiceConfig.getRhythmNotesForBeatgroup(beatGroup);
        melodyNotes.forEach(n -> {
            n.setVoice(voice);
            n.setDynamic(voiceConfig.getDynamic());
            n.setDynamicLevel(voiceConfig.getDynamic().getLevel());
            n.setTechnical(voiceConfig.getTechnical());
        });
        melodyNotes = voiceConfiguration.getRandomPitchClassGenerator(voice).updatePitchClasses(melodyNotes);
        CpMelody melody = new CpMelody(melodyNotes, voice, 0,  beatGroup.getBeatLength());
        melody.setBeatGroup(beatGroup);
        return melody;
    }
}
