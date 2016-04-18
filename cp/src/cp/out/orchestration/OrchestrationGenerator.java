package cp.out.orchestration;

import static cp.model.note.NoteBuilder.note;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cp.model.note.Note;
import cp.out.instrument.Instrument;
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

	public List<InstrumentNoteMapping> overlay2AllInstruments(int[] pitches, List<Instrument> instruments, int split) {
		List<InstrumentNoteMapping> instrumentNoteMappings = new ArrayList<>();
		Collections.sort(instruments, new OrderComparator());
		int size = instruments.size();
		for (int i = 0; i < size; i++) {
			List<Instrument> subList = instruments.subList(i + 1, size);
			Collections.sort(subList, new OrderComparator());
			int sizeSubList = subList.size();
			for (int s = 0; s < sizeSubList; s++) {
				InstrumentNoteMapping instrumentNoteMapping = new InstrumentNoteMapping();
				for (int h = split; h < pitches.length; h++) {
					Instrument instrument2 = subList.get(s);
					if (instrument2.inRange(pitches[h])) {
						instrumentNoteMapping.addNoteForInstrument(pitches[h], instrument2);
					}
				}
				Instrument instrument1 = instruments.get(i);
				for (int j = 0; j < split; j++) {
					if (instrument1.inRange(pitches[j])) {
						instrumentNoteMapping.addNoteForInstrument(pitches[j], instrument1);
					}
				}
				instrumentNoteMappings.add(instrumentNoteMapping);
			}
		}
		return instrumentNoteMappings;
	}
	
	public List<InstrumentNoteMapping> enclosureAllInstruments(int[] pitches, List<Instrument> instruments) {
		List<InstrumentNoteMapping> instrumentNoteMappings = new ArrayList<>();
		Collections.sort(instruments, new OrderComparator());
		int size = instruments.size();
		for (int i = 0; i < size; i++) {
			List<Instrument> subList = instruments.subList(i + 1, size);
			Collections.sort(subList, new OrderComparator());
			int sizeSubList = subList.size();
			for (int s = 0; s < sizeSubList; s++) {
				InstrumentNoteMapping instrumentNoteMapping = new InstrumentNoteMapping();
				//above
				Instrument instrumentEnclosing = instruments.get(i);
				if (instrumentEnclosing.inRange(pitches[0])) {
					instrumentNoteMapping.addNoteForInstrument(pitches[0], instrumentEnclosing);
				}
				for (int h = 1; h < pitches.length - 1; h++) {
					Instrument instrument2 = subList.get(s);
					if (instrument2.inRange(pitches[h])) {
						instrumentNoteMapping.addNoteForInstrument(pitches[h], instrument2);
					}
				}
				//below
				if (instrumentEnclosing.inRange(pitches[pitches.length - 1])) {
					instrumentNoteMapping.addNoteForInstrument(pitches[pitches.length - 1], instrumentEnclosing);
				}
				instrumentNoteMappings.add(instrumentNoteMapping);
			}
		}
		return instrumentNoteMappings;
	}
	
	public List<InstrumentNoteMapping> crossingAllInstruments(int[] pitches, List<Instrument> instruments) {
		List<InstrumentNoteMapping> instrumentNoteMappings = new ArrayList<>();
		Collections.sort(instruments, new OrderComparator());
		int size = instruments.size() - 1;
		for (int i = 0; i < size; i++) {
			InstrumentNoteMapping instrumentNoteMapping = new InstrumentNoteMapping();
			//above
			Instrument instrumentCrossing = instruments.get(i);
			if (instrumentCrossing.inRange(pitches[0])) {
				instrumentNoteMapping.addNoteForInstrument(pitches[0], instrumentCrossing);
			}
			Instrument instrument = instruments.get(i + 1);
			if (instrument.inRange(pitches[1])) {
				instrumentNoteMapping.addNoteForInstrument(pitches[1], instrument);
			}
			//crossing
			if (instrumentCrossing.inRange(pitches[2])) {
				instrumentNoteMapping.addNoteForInstrument(pitches[2], instrumentCrossing);
			}
			for (int h = 3; h < pitches.length; h++) {
				if (instrument.inRange(pitches[h])) {
					instrumentNoteMapping.addNoteForInstrument(pitches[h], instrument);
				}
			}
			instrumentNoteMappings.add(instrumentNoteMapping);
		}
		return instrumentNoteMappings;
	}
	
}
