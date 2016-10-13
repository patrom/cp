package cp.out.orchestration;

import cp.model.note.Note;
import cp.model.rhythm.DurationConstants;
import cp.out.instrument.Instrument;
import cp.out.orchestration.orchestra.ClassicalOrchestra;
import cp.out.orchestration.orchestra.Orchestra;
import cp.out.orchestration.quality.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.Map.Entry;

import static cp.model.note.NoteBuilder.note;
import static java.util.stream.Collectors.toList;

@Component
public class OrchestrationGenerator {
	
	private static Logger LOGGER = LoggerFactory.getLogger(OrchestrationGenerator.class);

	@Autowired
	private Brilliant brilliant;
	@Autowired
	private Bright bright;
	@Autowired
	private Pleasant pleasant;
	@Autowired
	private Rich rich;
	@Autowired
	private Golden golden;
	@Autowired
	private Glowing glowing;
	@Autowired
	private Mellow mellow;
	@Autowired
	private Warm warm;
	@Autowired
	private VerticalRelations verticalRelations;
	
	private Orchestra orchestra = new ClassicalOrchestra();

	public List<OrchestralQuality> getOrchestralQualities() {
		List<OrchestralQuality> orchestralQualities = new ArrayList<>();
		orchestralQualities.add(brilliant);
		orchestralQualities.add(bright);
		orchestralQualities.add(pleasant);
		orchestralQualities.add(rich);
		orchestralQualities.add(golden);
		orchestralQualities.add(glowing);
		orchestralQualities.add(mellow);
		orchestralQualities.add(warm);
		return orchestralQualities;
	}
	
	public Map<Instrument, List<Note>> orchestrateChord(int[] pitches, int noteLength, List<Instrument> instruments, List<Instrument> combiningInstrument) {
		int position = 0;
		List<OrchestralQuality> orchestralQualities = getOrchestralQualities();
		for (OrchestralQuality orchestralQuality : orchestralQualities) {
			List<InstrumentNoteMapping> noteForInstrument = combine(pitches, instruments, instruments, verticalRelations::overlapping2);
			List<InstrumentNoteMapping> orchestratedChords = noteForInstrument.stream().filter(m -> m.getChordSize() == pitches.length + 1).collect(toList());
			LOGGER.info(orchestralQuality.getQuality());
			
			position = updateOrchestra(position, noteLength, orchestratedChords);

			noteForInstrument = combine(pitches, instruments, instruments, verticalRelations::enclosing);
			orchestratedChords = noteForInstrument.stream().filter(m -> m.getChordSize() == pitches.length).collect(toList());
			position = updateOrchestra(position, noteLength, orchestratedChords);

			noteForInstrument = combine(pitches, instruments, instruments, verticalRelations::crossing);
			orchestratedChords = noteForInstrument.stream().filter(m -> m.getChordSize() == pitches.length).collect(toList());
			position = updateOrchestra(position, noteLength, orchestratedChords);

			noteForInstrument = combine(pitches, instruments, instruments,verticalRelations::oneOrchestralQuality);
			orchestratedChords = noteForInstrument.stream().filter(m -> m.getChordSize() == pitches.length).collect(toList());
			position = updateOrchestra(position, noteLength, orchestratedChords);

			noteForInstrument = combine(pitches, instruments, instruments, verticalRelations::superpositionSplit2);
			orchestratedChords = noteForInstrument.stream().filter(m -> m.getChordSize() == pitches.length).collect(toList());
			position = updateOrchestra(position, noteLength, orchestratedChords);

			noteForInstrument = combine(pitches, instruments, instruments, verticalRelations::superpositionSplit1);
			orchestratedChords = noteForInstrument.stream().filter(m -> m.getChordSize() == pitches.length).collect(toList());
			position = updateOrchestra(position, noteLength, orchestratedChords);
		}
		return orchestra.getOrchestra();
	}

	private int updateOrchestra(int position, int noteLength, List<InstrumentNoteMapping> orchestratedChords) {
		for (InstrumentNoteMapping instrumentNoteMapping : orchestratedChords) {
			for (Entry<Instrument, List<Note>> entry : instrumentNoteMapping.getNotesForInstrument().entrySet()) {
				addToOrchestra(position, entry.getKey(), entry.getValue());
			}
			position = position + noteLength;
		}
		return position;
	}
	
	private void addToOrchestra(int position, Instrument instrument, List<Note> notes) {
		LOGGER.info(instrument.getInstrumentName());
		notes.forEach(n -> n.setPosition(position));
		LOGGER.info(notes.toString());
		orchestra.updateInstrument(instrument, notes);
	}

	public List<InstrumentNoteMapping> overlay2Instruments(int[] pitches, List<Instrument> instruments, int split) {
		List<InstrumentNoteMapping> instrumentNoteMappings = new ArrayList<>();
		if (!instruments.isEmpty()) {
			InstrumentNoteMapping instrumentNoteMapping = new InstrumentNoteMapping();
			Collections.sort(instruments, new OrderComparator());
			Instrument instrument1 = instruments.get(0);
			for (int j = 0; j < split; j++) {
				if (instrument1.inRange(pitches[j])) {
					instrumentNoteMapping.addNoteForInstrument(pitches[j], instrument1);
				}
			}
			Collections.shuffle(instruments);
			Optional<Instrument> instrumentOptional = findInstrument(pitches[split], instruments, instrument1);
			if (instrumentOptional.isPresent()) {
				for (int j = split; j < pitches.length; j++) {
					Instrument instrument2 = instrumentOptional.get();
					if (instrument2.inRange(pitches[j])) {
						instrumentNoteMapping.addNoteForInstrument(pitches[j], instrument2);
					}
				}
			}
		}
		return instrumentNoteMappings;
	}

	private Optional<Instrument> findInstrument(int pitch, List<Instrument> instruments, Instrument instrument) {
		return instruments.stream()
				.filter(instr -> instr.inRange(pitch) && instr.getOrder() >= instrument.getOrder()).findFirst();
	}
	
	public List<InstrumentNoteMapping> orchestrateMultipleRegisterChord(int[] pitches, List<Instrument> instruments){
		if (instruments.size() < pitches.length) {
			throw new IllegalArgumentException("Not enough instruments to orchestrate chord: size =" + instruments.size());
		}
		List<InstrumentNoteMapping> instrumentNoteMappings = new ArrayList<>();
		
		Instrument firstInstrument = instruments.get(0);
		Optional<OrchestralQuality> optionalOrchestralQuality = findOrchestralQuality(pitches[0], firstInstrument);
		if (optionalOrchestralQuality.isPresent()) {
			OrchestralQuality previousOrchestralQuality = optionalOrchestralQuality.get();
			LOGGER.info(previousOrchestralQuality.getColor() + ", " + firstInstrument.getInstrumentName() + ", pitch: " + pitches[0]);
			InstrumentNoteMapping instrumentNoteMapping = new InstrumentNoteMapping();
			instrumentNoteMapping.addNoteForInstrument(pitches[0], firstInstrument);
			instrumentNoteMappings.add(instrumentNoteMapping);
			instruments.remove(firstInstrument);
			List<Instrument> copyInstruments = new ArrayList<>(instruments);
			for (int i = 0; i < pitches.length - 1; i++) {
				//search in basic
				Instrument instrument = findInstrumentInBasicQualityForPitch(pitches[i + 1], instruments, previousOrchestralQuality);
				if (instrument != null) {
					previousOrchestralQuality = optionalOrchestralQuality.get();
					instrumentNoteMapping = new InstrumentNoteMapping();
					instrumentNoteMapping.addNoteForInstrument(pitches[i + 1], instrument);
					instrumentNoteMappings.add(instrumentNoteMapping);
					instruments.remove(instrument);
				}else {
					//search in complementary
					instrument = findInstrumentInCloseQualityForPitch(pitches[i + 1], copyInstruments, previousOrchestralQuality);
					if (instrument != null) {
						previousOrchestralQuality = optionalOrchestralQuality.get();
						instrumentNoteMapping = new InstrumentNoteMapping();
						instrumentNoteMapping.addNoteForInstrument(pitches[i + 1], instrument);
						instrumentNoteMappings.add(instrumentNoteMapping);
						copyInstruments.remove(instrument);
					}
				}
			}
		}
		return instrumentNoteMappings;
	}
	
	protected Instrument findInstrumentInBasicQualityForPitch(int pitch, List<Instrument> instruments, OrchestralQuality orchestralQuality){
		Optional<Instrument> optionalInstrument = findBasicInstrumentWithQuality(instruments, orchestralQuality);
		Instrument previousInstrument = null;
		OrchestralQuality previousOrchestralQuality = orchestralQuality;
		while (optionalInstrument.isPresent()) {
			previousInstrument = optionalInstrument.get();
			Optional<OrchestralQuality> optionalOrchestralQuality = findOrchestralBasicQuality(pitch, previousOrchestralQuality, previousInstrument);
			if (optionalOrchestralQuality.isPresent()) {
				previousOrchestralQuality = optionalOrchestralQuality.get();
				LOGGER.info(previousOrchestralQuality.getColor() + ", " + previousInstrument.getInstrumentName() + ", pitch: " + pitch);
				return previousInstrument;
			}
			instruments.remove(previousInstrument);
			optionalInstrument = findBasicInstrumentWithQuality(instruments, orchestralQuality);
		}
		return null;
	}
	
	protected Instrument findInstrumentInCloseQualityForPitch(int pitch, List<Instrument> instruments, OrchestralQuality orchestralQuality){
		Optional<Instrument> optionalInstrument = findCloseInstrumentWithQuality(instruments, orchestralQuality);
		Instrument previousInstrument = null;
		OrchestralQuality previousOrchestralQuality = orchestralQuality;
		while (optionalInstrument.isPresent()) {
			previousInstrument = optionalInstrument.get();
			Optional<OrchestralQuality> optionalOrchestralQuality = findOrchestralCloseQuality(pitch, previousOrchestralQuality, previousInstrument);
			if (optionalOrchestralQuality.isPresent()) {
				previousOrchestralQuality = optionalOrchestralQuality.get();
				LOGGER.info(previousOrchestralQuality.getColor() + ", " + previousInstrument.getInstrumentName() + ", pitch: " + pitch);
				return previousInstrument;
			}
			instruments.remove(previousInstrument);
			optionalInstrument = findCloseInstrumentWithQuality(instruments, orchestralQuality);
		}
		return null;
	}
	
	

	private Optional<OrchestralQuality> findOrchestralQuality(int pitch, Instrument instrument) {
		List<OrchestralQuality> orchestralQualities = getOrchestralQualities();
		return orchestralQualities.stream().filter(quality -> quality.hasBasicInstrument(instrument)
				&& quality.getBasicInstrument(instrument.getInstrumentName()).inRange(pitch)).findAny();
	}
	
	protected Optional<Instrument> findBasicInstrumentWithQuality(List<Instrument> instruments, OrchestralQuality orchestralQuality){
		List<OrchestralQuality> orchestralQualities = getOrchestralQualities();
		Optional<Instrument> optionalInstrument = orchestralQualities.stream().filter(quality -> 
				quality.getQuality().equals(orchestralQuality.getQuality()))
				.flatMap(instr -> instr.getBasicInstruments().stream())
				.filter(instr -> instruments.contains(instr)).findAny();
		return optionalInstrument;
	}
	
	protected Optional<Instrument> findCloseInstrumentWithQuality(List<Instrument> instruments, OrchestralQuality orchestralQuality){
		List<OrchestralQuality> orchestralQualities = getOrchestralQualities();
			Optional<Instrument> optionalInstrument = orchestralQualities.stream().filter(quality -> 
			orchestralQuality.getCloseQualities().contains(quality))
			.flatMap(instr -> instr.getBasicInstruments().stream())
			.filter(instr -> instruments.contains(instr)).findAny();
		return optionalInstrument;
	}

	protected Optional<OrchestralQuality> findOrchestralBasicQuality(int pitch , OrchestralQuality orchestralQuality,
			Instrument instrument) {
		List<OrchestralQuality> orchestralQualities = getOrchestralQualities();
		Optional<OrchestralQuality> optionalOrchestralQuality = orchestralQualities.stream().filter(quality -> 
				quality.getQuality().equals(orchestralQuality.getQuality())//same quality
				&& quality.hasBasicInstrument(instrument)
				&& quality.getBasicInstrument(instrument.getInstrumentName()).inRange(pitch)).findAny();
		return optionalOrchestralQuality;
	}
	
	protected Optional<OrchestralQuality> findOrchestralCloseQuality(int pitch , OrchestralQuality orchestralQuality,
			Instrument instrument) {
		List<OrchestralQuality> orchestralQualities = getOrchestralQualities();
		Optional<OrchestralQuality> optionalOrchestralQuality = orchestralQualities.stream().filter(quality -> 
			orchestralQuality.getCloseQualities().contains(quality)//complementary quality
			&& quality.hasBasicInstrument(instrument)
			&& quality.getBasicInstrument(instrument.getInstrumentName()).inRange(pitch)).findAny();
		return optionalOrchestralQuality;
	}

	public List<Note> orchestrate1Instrument(int[] pitchClasses, Instrument instrument) {
		List<Note> notes = new ArrayList<>();
		for (int i = 0; i < pitchClasses.length; i++) {
			if (instrument.inRange(pitchClasses[i])) {
				notes.add(note().pitch(pitchClasses[i]).len(DurationConstants.QUARTER).build());
			}
		}
		return notes;
	}
	
	public List<InstrumentNoteMapping> combine(int[] pitches, List<Instrument> firstInstruments, List<Instrument> secondInstruments, VerticalRelation verticalRelation) {
		List<InstrumentNoteMapping> instrumentNoteMappings = new ArrayList<>();
		int size = firstInstruments.size();
		for (int i = 0; i < size; i++) {
			Instrument topInstrument = firstInstruments.get(i);
			List<Instrument> subList = secondInstruments.stream().filter(instr -> !instr.getInstrumentName().equals(topInstrument.getInstrumentName())
					&& instr.getOrder() >= topInstrument.getOrder()).collect(toList());
			int sizeSubList = subList.size();
			for (int s = 0; s < sizeSubList; s++) {
				Instrument bottomInstrument = subList.get(s);
				instrumentNoteMappings.add(verticalRelation.combine(pitches, topInstrument, bottomInstrument));
			}
		}
		return instrumentNoteMappings;
	}
	
	public InstrumentNoteMapping oneOrchestralQuality(int[] pitches, Instrument instrument) {
		InstrumentNoteMapping instrumentNoteMapping = new InstrumentNoteMapping();
		for (int h = 0; h < pitches.length; h++) {
			if (instrument.inRange(pitches[h])) {
				instrumentNoteMapping.addNoteForInstrument(pitches[h], instrument);
			}
		}
		return instrumentNoteMapping;
	}
	
	public List<InstrumentNoteMapping> oneQualityAllInstruments(int[] pitches, List<Instrument> instruments) {
		List<InstrumentNoteMapping> instrumentNoteMappings = new ArrayList<>();
		int size = instruments.size() - 1;
		for (int i = 0; i < size; i++) {
			Instrument instrument = instruments.get(i);
			instrumentNoteMappings.add(oneOrchestralQuality(pitches, instrument));
		}
		return instrumentNoteMappings;
	}
	
}
