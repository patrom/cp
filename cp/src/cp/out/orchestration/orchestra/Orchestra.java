package cp.out.orchestration.orchestra;

import static cp.model.note.NoteBuilder.note;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.TreeMap;

import cp.model.note.Note;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentUpdate;
import cp.util.RandomUtil;


public class Orchestra {

	protected Map<Instrument, List<Note>> map = new TreeMap<>();
	protected Instrument flute;
	protected Instrument oboe;
	protected Instrument clarinet;
	protected Instrument bassoon;
	
	protected Instrument horn;
	protected Instrument trumpet;
	protected Instrument trombone;
	protected Instrument tuba;
	
	protected Instrument violin1;
	protected Instrument violin2;
	protected Instrument viola;
	protected Instrument cello;
	protected Instrument bass;

	public Orchestra() {
		super();
	}

	public Instrument getFlute() {
		return flute;
	}

	public Instrument getOboe() {
		return oboe;
	}

	public void setFlute(List<Note> notes) {
		map.get(flute).addAll(notes);
	}
	
	public void setFlute(List<Note> notes, InstrumentUpdate instrumentUpdate) {
		map.get(flute).addAll(instrumentUpdate.updateInstrumentNotes(notes));
	}

	public void setOboe(List<Note> notes) {
		map.get(oboe).addAll(notes);
	}
	
	public void setOboe(List<Note> notes, InstrumentUpdate instrumentUpdate) {
		map.get(oboe).addAll(instrumentUpdate.updateInstrumentNotes(notes));
	}

	public Map<Instrument, List<Note>> getOrchestra() {
		return map;
	}

	public List<Note> getNotes(Instrument instrument) {
		return map.get(instrument);
	}

	public Instrument getClarinet() {
		return clarinet;
	}

	public void setClarinet(List<Note> notes) {
		map.get(clarinet).addAll(notes);
	}
	
	public void setClarinet(List<Note> notes, InstrumentUpdate instrumentUpdate) {
		map.get(clarinet).addAll(instrumentUpdate.updateInstrumentNotes(notes));
	}

	public Instrument getBassoon() {
		return bassoon;
	}

	public void setBassoon(List<Note> notes) {
		map.get(bassoon).addAll(notes);
	}
	
	public void setBassoon(List<Note> notes, InstrumentUpdate instrumentUpdate) {
		map.get(bassoon).addAll(instrumentUpdate.updateInstrumentNotes(notes));
	}

	public Instrument getHorn() {
		return horn;
	}

	public void setHorn(List<Note> notes) {
		map.get(horn).addAll(notes);
	}
	
	public void setHorn(List<Note> notes, InstrumentUpdate instrumentUpdate) {
		map.get(horn).addAll(instrumentUpdate.updateInstrumentNotes(notes));
	}

	public Instrument getTrumpet() {
		return trumpet;
	}

	public void setTrumpet(List<Note> notes) {
		map.get(trumpet).addAll(notes);
	}
	
	public void setTrumpet(List<Note> notes, InstrumentUpdate instrumentUpdate) {
		map.get(trumpet).addAll(instrumentUpdate.updateInstrumentNotes(notes));
	}
	
	public Instrument getTrombone() {
		return trombone;
	}

	public void setTrombone(List<Note> notes) {
		map.get(trombone).addAll(notes);
	}
	
	public void setTrombone(List<Note> notes, InstrumentUpdate instrumentUpdate) {
		map.get(trombone).addAll(instrumentUpdate.updateInstrumentNotes(notes));
	}
	
	public Instrument getTuba() {
		return tuba;
	}

	public void setTuba(List<Note> notes) {
		map.get(tuba).addAll(notes);
	}
	
	public void setTuba(List<Note> notes, InstrumentUpdate instrumentUpdate) {
		map.get(tuba).addAll(instrumentUpdate.updateInstrumentNotes(notes));
	}

	public Instrument getViolin1() {
		return violin1;
	}

	public void setViolin1(List<Note> notes) {
		map.get(violin1).addAll(notes);
	}
	
	public void setViolin1(List<Note> notes, InstrumentUpdate instrumentUpdate) {
		map.get(violin1).addAll(instrumentUpdate.updateInstrumentNotes(notes));
	}

	public Instrument getViolin2() {
		return violin2;
	}

	public void setViolin2(List<Note> notes) {
		map.get(violin2).addAll(notes);
	}
	
	public void setViolin2(List<Note> notes, InstrumentUpdate instrumentUpdate) {
		map.get(violin2).addAll(instrumentUpdate.updateInstrumentNotes(notes));
	}

	public Instrument getViola() {
		return viola;
	}

	public void setViola(List<Note> notes) {
		map.get(viola).addAll(notes);
	}
	
	public void setViola(List<Note> notes, InstrumentUpdate instrumentUpdate) {
		map.get(viola).addAll(instrumentUpdate.updateInstrumentNotes(notes));
	}

	public Instrument getCello() {
		return cello;
	}

	public void setCello(List<Note> notes) {
		map.get(cello).addAll(notes);
	}
	
	public void setCello(List<Note> notes, InstrumentUpdate instrumentUpdate) {
		map.get(cello).addAll(instrumentUpdate.updateInstrumentNotes(notes));
	}

	public Instrument getBass() {
		return bass;
	}

	public void setBass(List<Note> notes) {
		map.get(bass).addAll(notes);
	}
	
	public void setBass(List<Note> notes, InstrumentUpdate instrumentUpdate) {
		map.get(bass).addAll(instrumentUpdate.updateInstrumentNotes(notes));
	}

	public List<Note> duplicate(Instrument instrumentToDuplicate, int octave) {
		return getNotes(instrumentToDuplicate).stream()
				.map(n -> n.clone())
				.map(n -> {
						if (!n.isRest()) {
							n.transposePitch(octave);
						}
						return n;
					})
				.collect(toList());
	}
	
//	public List<Note> duplicate(Instrument instrumentToDuplicate, Instrument instrument, int octave) {
//		List<Note> duplicateNotes = map.get(instrumentToDuplicate).stream()
//				.map(n -> n.clone())
//				.collect(toList());
//		duplicateNotes.forEach(n ->{
//			if (!n.isRest()) {
//				n.transposePitch(octave);
//			}
//		});
//		instrument.updateMelodyInRange(duplicateNotes);
//		return duplicateNotes;
//	}
	
	public List<Note> duplicate(Instrument instrumentToDuplicate) {
		return getNotes(instrumentToDuplicate).stream()
				.map(n -> n.clone())
				.collect(toList());
	}
	
	public void setInstrument(Instrument instrument, Instrument instrumentUpdate,  InstrumentUpdate instrumentMethod) {
			List<Note> duplicatedNotes = duplicate(instrument, 0);
			map.get(instrumentUpdate).addAll(instrumentMethod.updateInstrumentNotes(duplicatedNotes));
	}
	
	public void updateInstrument(Instrument instrument, List<Note> notes) {
		Optional<Instrument> optionalInstrument = map.keySet().stream().filter(i -> i.getInstrumentName().equals(instrument.getInstrumentName())).findFirst();
		if (optionalInstrument.isPresent()) {
			map.compute(optionalInstrument.get(), (k, v) -> {
				if (v == null) {
					List<Note> list = new ArrayList<>();
					list.addAll(notes);
					return list;
				} else {
					v.addAll(notes);
					return v;
				}
			});
		}else{
//			throw new IllegalStateException("Doesn't contain the instrument:" + instrument.getInstrumentName());
		}
	}
	
//	public void updateEmptyWithRest(int position, int length){
//		for (Entry<Instrument, List<Note>> entry: map.entrySet()) {
//			Optional<Note> noteFound = entry.getValue().stream().filter(n -> n.getPosition() == position).findAny();
//			if (!noteFound.isPresent()) {
//				Note rest = note().rest().pc(position).len(length).build();
//				map.compute(entry.getKey(), (k, v) -> {
//					if (v == null) {
//						List<Note> list = new ArrayList<>();
//						list.addAll(Collections.singletonList(rest));
//						return list;
//					} else {
//						v.addAll(Collections.singletonList(rest));
//						return v;
//					}
//				});	
//			}
//		}
//	}
	
	public Instrument getRandomEmptyInstrument(){
		List<Instrument> instrumentsToUpdate = map.entrySet().stream()
				.filter(entry -> entry.getValue().isEmpty())
				.map(e -> e.getKey())
				.collect(toList());
		return RandomUtil.getRandomFromList(instrumentsToUpdate);
	}
	
}