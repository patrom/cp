package cp.out.orchestration;

import static cp.model.note.NoteBuilder.note;
import static java.util.stream.Collectors.toList;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cp.model.note.Note;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.orchestration.orchestra.ClassicalOrchestra;
import cp.out.orchestration.orchestra.Orchestra;
import cp.out.orchestration.quality.Bright;
import cp.out.orchestration.quality.Brilliant;
import cp.out.orchestration.quality.Glowing;
import cp.out.orchestration.quality.Golden;
import cp.out.orchestration.quality.Mellow;
import cp.out.orchestration.quality.OrchestralQuality;
import cp.out.orchestration.quality.Pleasant;
import cp.out.orchestration.quality.Rich;
import cp.out.orchestration.quality.Warm;

@Component
public class OrchestrationGenerator {

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
			position = updateOrchestra(position, noteLength, orchestralQuality, orchestratedChords);

			noteForInstrument = combine(pitches, instruments, instruments, verticalRelations::enclosing);
			orchestratedChords = noteForInstrument.stream().filter(m -> m.getChordSize() == pitches.length).collect(toList());
			position = updateOrchestra(position, noteLength, orchestralQuality, orchestratedChords);

			noteForInstrument = combine(pitches, instruments, instruments, verticalRelations::crossing);
			orchestratedChords = noteForInstrument.stream().filter(m -> m.getChordSize() == pitches.length).collect(toList());
			position = updateOrchestra(position, noteLength, orchestralQuality, orchestratedChords);

			noteForInstrument = combine(pitches, instruments, instruments,verticalRelations::oneOrchestralQuality);
			orchestratedChords = noteForInstrument.stream().filter(m -> m.getChordSize() == pitches.length).collect(toList());
			position = updateOrchestra(position, noteLength, orchestralQuality, orchestratedChords);

			noteForInstrument = combine(pitches, instruments, instruments, verticalRelations::superpositionSplit2);
			orchestratedChords = noteForInstrument.stream().filter(m -> m.getChordSize() == pitches.length).collect(toList());
			position = updateOrchestra(position, noteLength, orchestralQuality, orchestratedChords);

			noteForInstrument = combine(pitches, instruments, instruments, verticalRelations::superpositionSplit1);
			orchestratedChords = noteForInstrument.stream().filter(m -> m.getChordSize() == pitches.length).collect(toList());
			position = updateOrchestra(position, noteLength, orchestralQuality, orchestratedChords);
		}
		return orchestra.getOrchestra();
	}

	private int updateOrchestra(int position, int noteLength, OrchestralQuality orchestralQuality,
			List<InstrumentNoteMapping> orchestratedChords) {
		for (InstrumentNoteMapping instrumentNoteMapping : orchestratedChords) {
			System.out.println("-----------------");
			System.out.println(orchestralQuality.getQuality());
			for (Entry<Instrument, List<Note>> entry : instrumentNoteMapping.getNotesForInstrument().entrySet()) {
				addToOrchestra(position, entry);
			}
			position = position + noteLength;
		}
		return position;
	}
	
	private void addToOrchestra(int position, Entry<Instrument, List<Note>> entry) {
		System.out.println(entry.getKey().getInstrumentName());
		List<Note> notes = entry.getValue();
		notes.forEach(n -> n.setPosition(position));
		System.out.println(notes);
		orchestra.updateInstrument(entry.getKey(), notes);
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

	private Optional<Instrument> findInstrument(int pitchClass, List<Instrument> instruments, Instrument instrument) {
		return instruments.stream()
				.filter(instr -> instr.inRange(pitchClass) && instr.getOrder() >= instrument.getOrder()).findFirst();
	}

	public List<Note> orchestrate1Instrument(int[] pitchClasses, Instrument instrument) {
		List<Note> notes = new ArrayList<>();
		for (int i = 0; i < pitchClasses.length; i++) {
			if (instrument.inRange(pitchClasses[i])) {
				notes.add(note().pitch(pitchClasses[i]).len(12).build());
			}
		}
		return notes;
	}

	public List<InstrumentNoteMapping> overlayAllInstruments(int[] pitches, List<Instrument> instruments) {
		List<InstrumentNoteMapping> instrumentNoteMappings = new ArrayList<>();
		int size = instruments.size();
		for (int i = 0; i < size; i++) {
			Instrument topInstrument = instruments.get(i);
			List<Instrument> subList = instruments.subList(i + 1, size);
			int sizeSubList = subList.size();
			for (int s = 0; s < sizeSubList; s++) {
				Instrument bottomInstrument = subList.get(s);
				instrumentNoteMappings.add(superpositionSplit2(pitches, topInstrument, bottomInstrument));
			}
		}
		return instrumentNoteMappings;
	}

	public InstrumentNoteMapping superpositionSplit1(int[] pitches, Instrument topInstrument, Instrument bottomInstrument) {
		return splitSuperposition(pitches, topInstrument, bottomInstrument, 1);
	}
	
	public InstrumentNoteMapping superpositionSplit2(int[] pitches, Instrument topInstrument, Instrument bottomInstrument) {
		return splitSuperposition(pitches, topInstrument, bottomInstrument, 2);
	}
	
	public InstrumentNoteMapping superpositionSplit3(int[] pitches, Instrument topInstrument, Instrument bottomInstrument) {
		return splitSuperposition(pitches, topInstrument, bottomInstrument, 3);
	}

	private InstrumentNoteMapping splitSuperposition(int[] pitches, Instrument topInstrument,
			Instrument bottomInstrument, int split) {
		InstrumentNoteMapping instrumentNoteMapping = new InstrumentNoteMapping();
		for (int h = split; h < pitches.length; h++) {
			if (bottomInstrument.inRange(pitches[h])) {
				instrumentNoteMapping.addNoteForInstrument(pitches[h], bottomInstrument);
			}
		}
		for (int j = 0; j < split; j++) {
			if (topInstrument.inRange(pitches[j])) {
				instrumentNoteMapping.addNoteForInstrument(pitches[j], topInstrument);
			}
		}
		return instrumentNoteMapping;
	}
	
	public List<InstrumentNoteMapping> overlappingAllInstruments(int[] pitches, List<Instrument> instruments) {
		List<InstrumentNoteMapping> instrumentNoteMappings = new ArrayList<>();
		int size = instruments.size();
		for (int i = 0; i < size; i++) {
			Instrument topInstrument = instruments.get(i);
			List<Instrument> subList = instruments.subList(i + 1, size);
			int sizeSubList = subList.size();
			for (int s = 0; s < sizeSubList; s++) {
				Instrument bottomInstrument = subList.get(s);
				instrumentNoteMappings.add(overlapping2(pitches, topInstrument, bottomInstrument));
			}
		}
		return instrumentNoteMappings;
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
//				System.out.println("top: " +topInstrument.getInstrumentName() + " with: " + bottomInstrument.getInstrumentName());
				instrumentNoteMappings.add(verticalRelation.combine(pitches, topInstrument, bottomInstrument));
			}
		}
		return instrumentNoteMappings;
	}
	
	public InstrumentNoteMapping overlapping2(int[] pitches, Instrument topInstrument,
			Instrument bottomInstrument) {
		return overlapping(pitches, topInstrument, bottomInstrument, 2);
	}
	
	private InstrumentNoteMapping overlapping(int[] pitches, Instrument topInstrument,
			Instrument bottomInstrument, int split) {
		InstrumentNoteMapping instrumentNoteMapping = new InstrumentNoteMapping();
		for (int h = split - 1; h < pitches.length; h++) {
			if (bottomInstrument.inRange(pitches[h])) {
				instrumentNoteMapping.addNoteForInstrument(pitches[h], bottomInstrument);
			}
		}
		for (int j = 0; j < split; j++) {
			if (topInstrument.inRange(pitches[j])) {
				instrumentNoteMapping.addNoteForInstrument(pitches[j], topInstrument);
			}
		}
		return instrumentNoteMapping;
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

	public List<InstrumentNoteMapping> enclosureAllInstruments(int[] pitches, List<Instrument> instruments) {
		List<InstrumentNoteMapping> instrumentNoteMappings = new ArrayList<>();
		int size = instruments.size();
		for (int i = 0; i < size; i++) {
			Instrument topInstrument = instruments.get(i);
			List<Instrument> subList = instruments.subList(i + 1, size);
			int sizeSubList = subList.size();
			for (int s = 0; s < sizeSubList; s++) {
				Instrument bottomInstrument = subList.get(s);
				
				instrumentNoteMappings.add(enclosing(pitches, topInstrument, bottomInstrument));
			}
		}
		return instrumentNoteMappings;
	}

	public InstrumentNoteMapping enclosing(int[] pitches, Instrument topInstrument, Instrument bottomInstrument) {
		InstrumentNoteMapping instrumentNoteMapping = new InstrumentNoteMapping();
		// above
		if (topInstrument.inRange(pitches[0])) {
			instrumentNoteMapping.addNoteForInstrument(pitches[0], topInstrument);
		}
		for (int h = 1; h < pitches.length - 1; h++) {
			if (bottomInstrument.inRange(pitches[h])) {
				instrumentNoteMapping.addNoteForInstrument(pitches[h], bottomInstrument);
			}
		}
		// below
		if (topInstrument.inRange(pitches[pitches.length - 1])) {
			instrumentNoteMapping.addNoteForInstrument(pitches[pitches.length - 1], topInstrument);
		}
		return instrumentNoteMapping;
	}

	public List<InstrumentNoteMapping> crossingAllInstruments(int[] pitches, List<Instrument> instruments) {
		List<InstrumentNoteMapping> instrumentNoteMappings = new ArrayList<>();
		int size = instruments.size() - 1;
		for (int i = 0; i < size; i++) {
			Instrument topInstrument = instruments.get(i);
			Instrument bottomInstrument = instruments.get(i + 1);
			instrumentNoteMappings.add(crossing(pitches, topInstrument, bottomInstrument));
		}
		return instrumentNoteMappings;
	}
	
	public InstrumentNoteMapping crossing(int[] pitches, Instrument topInstrument, Instrument bottomInstrument) {
		InstrumentNoteMapping instrumentNoteMapping = new InstrumentNoteMapping();
		// above
		if (topInstrument.inRange(pitches[0])) {
			instrumentNoteMapping.addNoteForInstrument(pitches[0], topInstrument);
		}
		if (bottomInstrument.inRange(pitches[1])) {
			instrumentNoteMapping.addNoteForInstrument(pitches[1], bottomInstrument);
		}
		// crossing
		if (topInstrument.inRange(pitches[2])) {
			instrumentNoteMapping.addNoteForInstrument(pitches[2], topInstrument);
		}
		for (int h = 3; h < pitches.length; h++) {
			if (bottomInstrument.inRange(pitches[h])) {
				instrumentNoteMapping.addNoteForInstrument(pitches[h], bottomInstrument);
			}
		}
		return instrumentNoteMapping;
	}

}
