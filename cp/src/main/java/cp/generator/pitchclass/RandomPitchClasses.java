package cp.generator.pitchclass;

import cp.model.TimeLineKey;
import cp.model.melody.CpMelody;
import cp.model.note.Note;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RandomPitchClasses  {

	private static Logger LOGGER = LoggerFactory.getLogger(RandomPitchClasses.class);

	public List<Note> randomPitchClasses(CpMelody melody){
		LOGGER.debug("RandomPitchClasses");
        melody.updateTimeLineKeysNotes();
        return  melody.getNotesNoRest().stream().map(note -> {
                TimeLineKey timeLineKey = note.getTimeLineKey();
                note.setPitchClass(timeLineKey.getPitchClassForKey(timeLineKey.getScale().pickRandomPitchClass()));
                return note;
			}).collect(Collectors.toList());
	}
}
