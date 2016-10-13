package cp.out.orchestration.orchestra;

import cp.model.note.Note;
import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentUpdate;
import cp.util.RandomUtil;

import java.util.*;

import static java.util.stream.Collectors.toList;


public class Orchestra {

	protected final Map<Instrument, List<Note>> map = new TreeMap<>();
	protected Instrument piccolo;
	protected Instrument flute;
	protected Instrument altoFlute;
	protected Instrument oboe;
	protected Instrument corAnglais;
	protected Instrument clarinetEflat;
	protected Instrument clarinet;
	protected Instrument bassClarinet;
	protected Instrument bassoon;
	protected Instrument contrabassoon;
	
	protected Instrument horn;
	protected Instrument trumpet;
	protected Instrument trombone;
	protected Instrument bassTrombone;
	protected Instrument tuba;
	
	protected Instrument glockenspiel;
	protected Instrument celesta;
	protected Instrument xylophone;
	protected Instrument harp;
	protected Instrument piano;
	protected Instrument timpani;
	
	protected Instrument violin1;
	protected Instrument violin2;
	protected Instrument viola;
	protected Instrument cello;
	protected Instrument bass;

	public Orchestra() {
		super();
	}
	
	public Instrument getPiccolo() {
		return piccolo;
	}

	public void setPiccolo(List<Note> notes) {
		map.get(piccolo).addAll(notes);
	}
	
	public void setPiccolo(List<Note> notes, InstrumentUpdate instrumentUpdate) {
		map.get(piccolo).addAll(instrumentUpdate.updateInstrumentNotes(notes));
	}

	public Instrument getFlute() {
		return flute;
	}

	public void setFlute(List<Note> notes) {
		map.get(flute).addAll(notes);
	}
	
	public void setFlute(List<Note> notes, InstrumentUpdate instrumentUpdate) {
		map.get(flute).addAll(instrumentUpdate.updateInstrumentNotes(notes));
	}
	
	public Instrument getAltoFlute() {
		return altoFlute;
	}

	public void setAltoFlute(List<Note> notes) {
		map.get(altoFlute).addAll(notes);
	}
	
	public void setAltoFlute(List<Note> notes, InstrumentUpdate instrumentUpdate) {
		map.get(altoFlute).addAll(instrumentUpdate.updateInstrumentNotes(notes));
	}
	
	public Instrument getOboe() {
		return oboe;
	}

	public void setOboe(List<Note> notes) {
		map.get(oboe).addAll(notes);
	}
	
	public void setOboe(List<Note> notes, InstrumentUpdate instrumentUpdate) {
		map.get(oboe).addAll(instrumentUpdate.updateInstrumentNotes(notes));
	}
	
	public Instrument getCorAnglais() {
		return corAnglais;
	}

	public void setCorAnglais(List<Note> notes) {
		map.get(corAnglais).addAll(notes);
	}
	
	public void setCorAnglais(List<Note> notes, InstrumentUpdate instrumentUpdate) {
		map.get(corAnglais).addAll(instrumentUpdate.updateInstrumentNotes(notes));
	}

	public Map<Instrument, List<Note>> getOrchestra() {
		return map;
	}

	public List<Note> getNotes(Instrument instrument) {
		return map.get(instrument);
	}
	
	public Instrument getSmallClarinet() {
		return clarinetEflat;
	}

	public void setSmallClarinet(List<Note> notes) {
		map.get(clarinetEflat).addAll(notes);
	}
	
	public void setSmallClarinet(List<Note> notes, InstrumentUpdate instrumentUpdate) {
		map.get(clarinetEflat).addAll(instrumentUpdate.updateInstrumentNotes(notes));
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
	
	public Instrument getBassClarinet() {
		return bassClarinet;
	}

	public void setBassClarinet(List<Note> notes) {
		map.get(bassClarinet).addAll(notes);
	}
	
	public void setBassClarinet(List<Note> notes, InstrumentUpdate instrumentUpdate) {
		map.get(bassClarinet).addAll(instrumentUpdate.updateInstrumentNotes(notes));
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
	
	public Instrument getContraBassoon() {
		return contrabassoon;
	}

	public void setContraBassoon(List<Note> notes) {
		map.get(contrabassoon).addAll(notes);
	}
	
	public void setContraBassoon(List<Note> notes, InstrumentUpdate instrumentUpdate) {
		map.get(contrabassoon).addAll(instrumentUpdate.updateInstrumentNotes(notes));
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
	
	public Instrument getBassTrombone() {
		return bassTrombone;
	}

	public void setBassTrombone(List<Note> notes) {
		map.get(bassTrombone).addAll(notes);
	}
	
	public void setBassTrombone(List<Note> notes, InstrumentUpdate instrumentUpdate) {
		map.get(bassTrombone).addAll(instrumentUpdate.updateInstrumentNotes(notes));
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
	
	public Instrument getGlockenspiel() {
		return glockenspiel;
	}

	public void setGlockenspiel(List<Note> notes) {
		map.get(glockenspiel).addAll(notes);
	}
	
	public void setGlockenspiel(List<Note> notes, InstrumentUpdate instrumentUpdate) {
		map.get(glockenspiel).addAll(instrumentUpdate.updateInstrumentNotes(notes));
	}
	
	public Instrument getCelesta() {
		return celesta;
	}

	public void setCelesta(List<Note> notes) {
		map.get(celesta).addAll(notes);
	}
	
	public void setCelesta(List<Note> notes, InstrumentUpdate instrumentUpdate) {
		map.get(celesta).addAll(instrumentUpdate.updateInstrumentNotes(notes));
	}
	
	public Instrument getXylophone() {
		return xylophone;
	}

	public void setXylophone(List<Note> notes) {
		map.get(xylophone).addAll(notes);
	}
	
	public void setXylophone(List<Note> notes, InstrumentUpdate instrumentUpdate) {
		map.get(xylophone).addAll(instrumentUpdate.updateInstrumentNotes(notes));
	}
	
	public Instrument getPiano() {
		return piano;
	}

	public void setPiano(List<Note> notes) {
		map.get(piano).addAll(notes);
	}
	
	public void setPiano(List<Note> notes, InstrumentUpdate instrumentUpdate) {
		map.get(piano).addAll(instrumentUpdate.updateInstrumentNotes(notes));
	}
	
	public Instrument getHarp() {
		return harp;
	}

	public void setHarp(List<Note> notes) {
		map.get(harp).addAll(notes);
	}
	
	public void setHarp(List<Note> notes, InstrumentUpdate instrumentUpdate) {
		map.get(harp).addAll(instrumentUpdate.updateInstrumentNotes(notes));
	}
	
	public Instrument getTimpani() {
		return timpani;
	}

	public void setTimpani(List<Note> notes) {
		map.get(timpani).addAll(notes);
	}
	
	public void setTimpani(List<Note> notes, InstrumentUpdate instrumentUpdate) {
		map.get(timpani).addAll(instrumentUpdate.updateInstrumentNotes(notes));
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