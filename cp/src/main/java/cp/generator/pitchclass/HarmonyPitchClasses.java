package cp.generator.pitchclass;

import cp.composition.beat.BeatGroup;
import cp.config.TextureConfig;
import cp.model.TimeLine;
import cp.model.TimeLineKey;
import cp.model.harmony.DependantHarmony;
import cp.model.melody.Tonality;
import cp.model.note.Note;
import cp.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static java.util.stream.Collectors.toList;

//@Component
public class HarmonyPitchClasses {

    private static Logger LOGGER = LoggerFactory.getLogger(HarmonyPitchClasses.class);

    @Autowired
    private TimeLine timeLine;
    @Autowired
    private TextureConfig textureConfig;

    public List<Note> updatePitchClasses(List<Note> notes, BeatGroup beatGroup) {
        LOGGER.debug("HarmonyPitchClasses");
        List<Note> melodyNotes = notes.stream().filter(n -> !n.isRest()).collect(toList());
        if (!melodyNotes.isEmpty()) {
            Note firstNote = melodyNotes.get(0);
            List<DependantHarmony> textureTypes = textureConfig.getTextureFor(firstNote.getVoice());
            if (beatGroup != null) {
                setPitchClasses(melodyNotes, beatGroup);
                if (textureTypes != null && !textureTypes.isEmpty()) {
                    for (Note note : notes) {
                        note.setDependantHarmony(RandomUtil.getRandomFromList(textureTypes));
                    }
                }
            } else {
                TimeLineKey timeLineKey = timeLine.getTimeLineKeyAtPosition(firstNote.getPosition(), firstNote.getVoice());
                int[] pitchClasses = timeLineKey.getScale().getPitchClasses();
                int i = 0;
                for (Note note : notes) {
                    note.setPitchClass((pitchClasses[i] + timeLineKey.getKey().getInterval()) % 12);
                    if (textureTypes != null && !textureTypes.isEmpty()) {
                        note.setDependantHarmony(RandomUtil.getRandomFromList(textureTypes));
                    }
                    i = (i + 1) % pitchClasses.length;
                }
            }
        }
        return notes;
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
