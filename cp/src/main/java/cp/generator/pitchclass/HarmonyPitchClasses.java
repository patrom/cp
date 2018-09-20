package cp.generator.pitchclass;

import cp.composition.beat.BeatGroup;
import cp.config.TextureConfig;
import cp.model.TimeLineKey;
import cp.model.harmony.DependantHarmony;
import cp.model.melody.CpMelody;
import cp.model.melody.Tonality;
import cp.model.note.Note;
import cp.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

//@Component
public class HarmonyPitchClasses {

    private static Logger LOGGER = LoggerFactory.getLogger(HarmonyPitchClasses.class);

    @Autowired
    private TextureConfig textureConfig;

    public List<Note> updatePitchClasses(CpMelody melody) {
        LOGGER.debug("HarmonyPitchClasses");
        List<Note> melodyNotes = melody.getNotesNoRest();
        melody.updateTimeLineKeysNotes();
        if (!melodyNotes.isEmpty()) {
            Note firstNote = melodyNotes.get(0);
            List<DependantHarmony> textureTypes = textureConfig.getTextureFor(firstNote.getVoice());
            BeatGroup beatGroup = melody.getBeatGroup();
            if (beatGroup.hasMelody()) {
                setPitchClasses(melodyNotes, beatGroup);
                if (textureTypes != null && !textureTypes.isEmpty()) {
                    for (Note note : melodyNotes) {
                        note.setDependantHarmony(RandomUtil.getRandomFromList(textureTypes));
                    }
                }
            } else {
                TimeLineKey timeLineKey = firstNote.getTimeLineKey();
                int[] pitchClasses = timeLineKey.getScale().getPitchClasses();
                int i = 0;
                for (Note note : melodyNotes) {
                    timeLineKey = note.getTimeLineKey();
                    note.setPitchClass(timeLineKey.getPitchClassForKey(pitchClasses[i]));
                    if (textureTypes != null && !textureTypes.isEmpty()) {
                        note.setDependantHarmony(RandomUtil.getRandomFromList(textureTypes));
                    }
                    i = (i + 1) % pitchClasses.length;
                }
            }
        }
        return melodyNotes;
    }

    private void setPitchClasses(List<Note> notes, BeatGroup beatGroup) {
        if (beatGroup.getTonality() == Tonality.TONAL) {
            int[] indexesMotivePitchClasses = RandomUtil.getRandomFromList(beatGroup.getIndexesMotivePitchClasses());
            TimeLineKey timeLineKey = RandomUtil.getRandomFromList(beatGroup.getTimeLineKeys());
            List<Integer> pitchClasses = timeLineKey.getScale().getPitchClasses(indexesMotivePitchClasses, 0, timeLineKey.getKey());
            int i = 0;
            for (Note note : notes) {
                note.setPitchClass(pitchClasses.get(i));
                i = (i + 1) % pitchClasses.size();
            }
        } else {
            int[] motivePitchClasses = RandomUtil.getRandomFromList(beatGroup.getMotivePitchClasses()).getPitchClasses();
            int i = 0;
            for (Note note : notes) {
                note.setPitchClass(motivePitchClasses[i] % 12);
                i = (i + 1) % motivePitchClasses.length;
            }
        }
    }

}
