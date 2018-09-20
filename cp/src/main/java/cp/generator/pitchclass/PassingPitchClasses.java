package cp.generator.pitchclass;

import cp.composition.beat.BeatGroup;
import cp.model.TimeLineKey;
import cp.model.melody.CpMelody;
import cp.model.melody.Tonality;
import cp.model.note.Note;
import cp.model.note.Scale;
import cp.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PassingPitchClasses{

	private static Logger LOGGER = LoggerFactory.getLogger(PassingPitchClasses.class);

	public List<Note> updatePitchClasses(CpMelody melody) {
		LOGGER.debug("PassingPitchClasses");
		List<Note> melodyNotes = melody.getNotesNoRest();
        melody.updateTimeLineKeysNotes();
        BeatGroup beatGroup = melody.getBeatGroup();
        if (beatGroup.hasMelody()) {
            updateNotesForBeatgroup(melodyNotes, beatGroup);
        } else if (!melodyNotes.isEmpty()) {
            Note firstNote = melodyNotes.get(0);
			TimeLineKey timeLineKey = firstNote.getTimeLineKey();
			int tempPC = timeLineKey.getScale().pickRandomPitchClass();
			firstNote.setPitchClass(timeLineKey.getPitchClassForKey(tempPC));

			for (int i = 1; i < melodyNotes.size(); i++) {
                Note nextNote = melodyNotes.get(i);
                timeLineKey = nextNote.getTimeLineKey();
                Scale scale = timeLineKey.getScale();
                int pitchClass;
                if (RandomUtil.toggleSelection()) {
                    pitchClass = scale.pickNextPitchFromScale(tempPC);
                } else {
                    pitchClass = scale.pickPreviousPitchFromScale(tempPC);
                }
                nextNote.setPitchClass(timeLineKey.getPitchClassForKey(pitchClass));
                tempPC = pitchClass;
            }
		}
		return melodyNotes;
	}

    private void updateNotesForBeatgroup(List<Note> melodyNotes, BeatGroup beatGroup) {
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
    }

}
