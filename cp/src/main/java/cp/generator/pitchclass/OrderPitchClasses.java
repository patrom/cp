package cp.generator.pitchclass;

import cp.composition.beat.BeatGroup;
import cp.model.TimeLine;
import cp.model.TimeLineKey;
import cp.model.note.Note;
import cp.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class OrderPitchClasses {
    private static Logger LOGGER = LoggerFactory.getLogger(PassingPitchClasses.class);

    @Autowired
    private TimeLine timeLine;

    public List<Note> updatePitchClasses(List<Note> notes, BeatGroup beatGroup) {
        LOGGER.debug("OrderPitchClasses");
        List<Note> melodyNotes = notes.stream().filter(n -> !n.isRest()).collect(toList());
        if (!melodyNotes.isEmpty()) {
            Note firstNote = melodyNotes.get(0);
            if (beatGroup == null) {
                TimeLineKey timeLineKey = timeLine.getTimeLineKeyAtPosition(firstNote.getPosition(), firstNote.getVoice());
                int[] pitchClasses = timeLineKey.getScale().getPitchClasses();
                int i = 0;
                for (Note note : notes) {
                    note.setPitchClass((pitchClasses[i] + timeLineKey.getKey().getInterval()) % 12);
                    i = (i + 1) % pitchClasses.length;
                }
            } else {
                setPitchClasses(melodyNotes, beatGroup);
            }
        }
        return notes;
    }

    private void setPitchClasses(List<Note> notes, BeatGroup beatGroup) {
        int[] indexesMotivePitchClasses = RandomUtil.getRandomFromList(beatGroup.getIndexesMotivePitchClasses());
        TimeLineKey timeLineKey = RandomUtil.getRandomFromList(beatGroup.getTimeLineKeys());
        List<Integer> pitchClasses = timeLineKey.getScale().getPitchClasses(indexesMotivePitchClasses, 0, timeLineKey.getKey());

        int i = 0;
        for (Note note : notes) {
            note.setPitchClass(pitchClasses.get(i));
            i = (i + 1) % pitchClasses.size();
        }
    }

}

