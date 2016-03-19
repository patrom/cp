package cp.out.orchestration.orchestra;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import cp.model.note.Note;
import cp.out.instrument.Instrument;
@Component
public class Orchestra {

	protected Map<Instrument, List<Note>> map = new TreeMap<>();
	protected Instrument flute;
	protected Instrument oboe;
	protected Instrument clarinet;
	protected Instrument bassoon;
	protected Instrument horn;
	protected Instrument trumpet;
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

	public void setOboe(List<Note> notes) {
		map.get(oboe).addAll(notes);
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

	public Instrument getBassoon() {
		return bassoon;
	}

	public void setBassoon(List<Note> notes) {
		map.get(bassoon).addAll(notes);
	}

	public Instrument getHorn() {
		return horn;
	}

	public void setHorn(List<Note> notes) {
		map.get(horn).addAll(notes);
	}

	public Instrument getTrumpet() {
		return trumpet;
	}

	public void setTrumpet(List<Note> notes) {
		map.get(trumpet).addAll(notes);
	}

	public Instrument getViolin1() {
		return violin1;
	}

	public void setViolin1(List<Note> notes) {
		map.get(violin1).addAll(notes);
	}

	public Instrument getViolin2() {
		return violin2;
	}

	public void setViolin2(List<Note> notes) {
		map.get(violin2).addAll(notes);
	}

	public Instrument getViola() {
		return viola;
	}

	public void setViola(List<Note> notes) {
		map.get(viola).addAll(notes);
	}

	public Instrument getCello() {
		return cello;
	}

	public void setCello(List<Note> notes) {
		map.get(cello).addAll(notes);
	}

	public Instrument getBass() {
		return bass;
	}

	public void setBass(List<Note> notes) {
		map.get(bass).addAll(notes);
	}

}