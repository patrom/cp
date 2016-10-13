package cp.out.orchestration;

import cp.out.instrument.Instrument;
import org.springframework.stereotype.Component;

@Component
public class VerticalRelations {

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
	
	public InstrumentNoteMapping oneOrchestralQuality(int[] pitches, Instrument instrument, Instrument empty) {
		InstrumentNoteMapping instrumentNoteMapping = new InstrumentNoteMapping();
		for (int h = 0; h < pitches.length; h++) {
			if (instrument.inRange(pitches[h])) {
				instrumentNoteMapping.addNoteForInstrument(pitches[h], instrument);
			}
		}
		return instrumentNoteMapping;
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

}
