package cp.objective.rhythm;

import cp.composition.voice.Voice;
import cp.config.VoiceConfig;
import cp.model.Motive;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.objective.Objective;
import cp.objective.meter.InnerMetricWeight;
import cp.objective.meter.InnerMetricWeightFunctions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RhythmObjective extends Objective{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RhythmObjective.class.getName());
	
	@Autowired
	private InnerMetricWeightFunctions innerMetricWeightFunctions;
	@Autowired
	private VoiceConfig voiceConfig;

	@Override
	public double evaluate(Motive motive) {

		List<MelodyBlock> melodies = motive.getMelodyBlocks();
        return melodies.stream()
                .mapToDouble(melody -> getProfileAverage(melody))
                .average()
                .getAsDouble();
	}
	
	public double getProfileAverage(MelodyBlock melody){
		Voice voice = voiceConfig.getVoiceConfiguration(melody.getVoice());
		List<Note> filteredNotes = filterRhythmWeigths(melody.getMelodyBlockNotes(), voice.getTimeConfig().getMinimumRhythmFilterLevel());
		LOGGER.debug(filteredNotes.toString());
		if (filteredNotes.isEmpty()) {
			return 0;
		}
		InnerMetricWeight innerMetricWeight = innerMetricWeightFunctions.getInnerMetricWeight(filteredNotes , voice.getTimeConfig().getMinimumLength(), voice.getTimeConfig().getDistance());
		LOGGER.debug("InnerMetricMap: " + innerMetricWeight.getInnerMetricWeightMap().toString());
		return innerMetricWeight.getInnerMetricWeightAverage();
	}
	
	public List<Note> filterRhythmWeigths(List<Note> notes, double minimumWeight){
		return notes.stream().filter(note -> note.getPositionWeight() >= minimumWeight).collect(Collectors.toList());
	}
	
}
