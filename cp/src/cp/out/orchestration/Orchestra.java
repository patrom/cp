package cp.out.orchestration;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import cp.model.note.Note;
import cp.out.instrument.Instrument;
import cp.out.instrument.brass.FrenchHorn;
import cp.out.instrument.brass.Trumpet;
import cp.out.instrument.strings.Cello;
import cp.out.instrument.strings.Doublebass;
import cp.out.instrument.strings.Viola;
import cp.out.instrument.strings.ViolinsI;
import cp.out.instrument.strings.ViolinsII;
import cp.out.instrument.woodwinds.Bassoon;
import cp.out.instrument.woodwinds.Clarinet;
import cp.out.instrument.woodwinds.Flute;
import cp.out.instrument.woodwinds.Oboe;
@Component
public class Orchestra {

	protected Map<Instrument, List<Note>> map = new TreeMap<>();
	protected Instrument flute = new Flute(0, 1);
	protected Instrument oboe = new Oboe(1, 2);
	protected Instrument clarinet = new Clarinet(2, 3);
	protected Instrument bassoon = new Bassoon(3, 4);
	protected Instrument horn = new FrenchHorn(4, 5);
	protected Instrument trumpet = new Trumpet(5, 6);
	protected Instrument violin1 = new ViolinsI(6, 7);
	protected Instrument violin2 = new ViolinsII(7, 8);
	protected Instrument viola = new Viola(8, 9);
	protected Instrument cello = new Cello(9, 10);
	protected Instrument bass = new Doublebass(10, 11);

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

}