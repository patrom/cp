package cp.generator.pitchclass;

import cp.composition.beat.BeatGroup;
import cp.model.TimeLine;
import cp.model.TimeLineKey;
import cp.model.melody.Tonality;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class PassingPitchClasses{

	private static Logger LOGGER = LoggerFactory.getLogger(PassingPitchClasses.class);
	
	@Autowired
	private TimeLine timeLine;

	public List<Note> updatePitchClasses(List<Note> notes, BeatGroup beatGroup) {
		LOGGER.debug("PassingPitchClasses");
		List<Note> melodyNotes = notes.stream().filter(n -> !n.isRest()).collect(toList());
        if (beatGroup != null) {
            if (beatGroup.getTonality() == Tonality.TONAL) {
                Scale scale = RandomUtil.getRandomFromList(beatGroup.getMotivePitchClasses());
                int tempPC = scale.pickRandomPitchClass();
                TimeLineKey timeLineKey = RandomUtil.getRandomFromList(beatGroup.getTimeLineKeys());

                Note firstNote = melodyNotes.get(0);
                firstNote.setPitchClass((tempPC + timeLineKey.getKey().getInterval()) % 12);
                for (int i = 1; i < melodyNotes.size(); i++) {
                    Note nextNote = melodyNotes.get(i);
                    int pitchClass;
                    if (RandomUtil.toggleSelection()) {
                        pitchClass = scale.pickNextPitchFromScale(tempPC);
                    } else {
                        pitchClass = scale.pickPreviousPitchFromScale(tempPC);
                    }
                    tempPC = pitchClass;
                    nextNote.setPitchClass((pitchClass + timeLineKey.getKey().getInterval()) % 12);
//                    LOGGER.info("passing");
                }
            } else {
                Scale scale = RandomUtil.getRandomFromList(beatGroup.getMotivePitchClasses());
                int tempPC = scale.pickRandomPitchClass();
                Note firstNote = melodyNotes.get(0);
                firstNote.setPitchClass((tempPC));
                for (int i = 1; i < melodyNotes.size(); i++) {
                    Note nextNote = melodyNotes.get(i);
                    int pitchClass;
                    if (RandomUtil.toggleSelection()) {
                        pitchClass = scale.pickNextPitchFromScale(tempPC);
                    } else {
                        pitchClass = scale.pickPreviousPitchFromScale(tempPC);
                    }
                    tempPC = pitchClass;
                    nextNote.setPitchClass((pitchClass));
                }
            }
        } else if (!melodyNotes.isEmpty()) {
            Note firstNote = melodyNotes.get(0);
			TimeLineKey timeLineKey = timeLine.getTimeLineKeyAtPosition(firstNote.getPosition(), firstNote.getVoice());
			int tempPC = timeLineKey.getScale().pickRandomPitchClass();
            Scale scale = timeLineKey.getScale();

			firstNote.setPitchClass((tempPC + timeLineKey.getKey().getInterval()) % 12);
			for (int i = 1; i < melodyNotes.size(); i++) {
                Note nextNote = melodyNotes.get(i);
                timeLineKey = timeLine.getTimeLineKeyAtPosition(nextNote.getPosition(), nextNote.getVoice());
                int pitchClass;
                if (RandomUtil.toggleSelection()) {
                    pitchClass = scale.pickNextPitchFromScale(tempPC);
                } else {
                    pitchClass = scale.pickPreviousPitchFromScale(tempPC);
                }
                tempPC = pitchClass;
                nextNote.setPitchClass((pitchClass + timeLineKey.getKey().getInterval()) % 12);
            }
		}
		return notes;
	}

}
