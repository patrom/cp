package cp.objective.rhythm;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cp.generator.MusicProperties;
import cp.model.Motive;
import cp.model.melody.CpMelody;
import cp.model.melody.MelodyBlock;
import cp.model.note.Note;
import cp.objective.Objective;
import cp.objective.meter.InnerMetricWeight;
import cp.objective.meter.InnerMetricWeightFunctions;

@Component
public class RhythmObjective extends Objective{
	
	private static Logger LOGGER = LoggerFactory.getLogger(RhythmObjective.class.getName());
	
	@Autowired
	private InnerMetricWeightFunctions innerMetricWeightFunctions;

	@Override
	public double evaluate(Motive motive) {
		List<MelodyBlock> melodies = motive.getMelodyBlocks();
		double profileAverage = melodies.stream()
				.mapToDouble(melody -> getProfileAverage(melody, melody.getTimeConfig().getMinimumRhythmFilterLevel(), musicProperties.getMinimumLength()))
				.average()
				.getAsDouble();
		return profileAverage;
	}
	
	public double getProfileAverage(MelodyBlock melody, double minimumFilterLevel, int minimumRhythmicValue){
		List<Note> filteredNotes = filterRhythmWeigths(melody.getMelodyBlockNotes(), minimumFilterLevel);
		LOGGER.debug(filteredNotes.toString());
		if (filteredNotes.isEmpty()) {
			return 0;
		}
		InnerMetricWeight innerMetricWeight = innerMetricWeightFunctions.getInnerMetricWeight(filteredNotes , minimumRhythmicValue, melody.getTimeConfig().getDistance());
		LOGGER.debug("InnerMetricMap: " + innerMetricWeight.getInnerMetricWeightMap().toString());
		return innerMetricWeight.getInnerMetricWeightAverage();
	}
	
	public List<Note> filterRhythmWeigths(List<Note> notes, double minimumWeight){
		return notes.stream().filter(note -> note.getPositionWeight() >= minimumWeight).collect(Collectors.toList());
	}
	
}
