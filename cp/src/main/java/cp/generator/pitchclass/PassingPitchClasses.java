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
public class PassingPitchClasses{

	private static Logger LOGGER = LoggerFactory.getLogger(PassingPitchClasses.class);
	
	@Autowired
	private TimeLine timeLine;

	public List<Note> updatePitchClasses(List<Note> notes, BeatGroup beatGroup) {
		LOGGER.debug("PassingPitchClasses");
		List<Note> melodyNotes = notes.stream().filter(n -> !n.isRest()).collect(toList());
		if (!melodyNotes.isEmpty()) {
			Note firstNote = melodyNotes.get(0);
			TimeLineKey timeLineKey = timeLine.getTimeLineKeyAtPosition(firstNote.getPosition(), firstNote.getVoice());
			int tempPC = timeLineKey.getScale().pickRandomPitchClass();

			firstNote.setPitchClass((tempPC + timeLineKey.getKey().getInterval()) % 12);
			for (int i = 1; i < melodyNotes.size(); i++) {
                Note nextNote = melodyNotes.get(i);
                timeLineKey = timeLine.getTimeLineKeyAtPosition(nextNote.getPosition(), nextNote.getVoice());
                int pitchClass;
                if (RandomUtil.toggleSelection()) {
                    pitchClass = timeLineKey.getScale().pickNextPitchFromScale(tempPC);
                } else {
                    pitchClass = timeLineKey.getScale().pickPreviousPitchFromScale(tempPC);
                }
                tempPC = pitchClass;
                nextNote.setPitchClass((pitchClass + timeLineKey.getKey().getInterval()) % 12);
            }
		}
		return notes;
	}

}
