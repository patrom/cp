package cp.out.orchestration.orchestra;

import cp.out.instrument.woodwinds.*;
import cp.out.play.InstrumentMapping;

import java.util.ArrayList;

public class WindOrchestra extends Orchestra{

	public WindOrchestra() {
		piccolo = new InstrumentMapping(new Piccolo(), 2, 1);
		flute = new InstrumentMapping(new Flute(), 2, 1);
		altoFlute = new InstrumentMapping(new AltoFlute(), 2, 1);
		oboe = new InstrumentMapping(new Oboe(), 2, 1);
		corAnglais = new InstrumentMapping(new CorAnglais(), 2, 1);
		clarinetEflat = new InstrumentMapping(new ClarinetEFlat(), 2, 1);
		clarinet = new InstrumentMapping(new Clarinet(), 2, 1);
		bassClarinet = new InstrumentMapping(new BassClarinet(), 2, 1);
		bassoon = new InstrumentMapping(new Bassoon(), 2, 1);
		contrabassoon = new InstrumentMapping(new ContraBassoon(), 2, 1);
		map.put(piccolo, new ArrayList<>());
		map.put(flute, new ArrayList<>());
		map.put(altoFlute, new ArrayList<>());
		map.put(oboe, new ArrayList<>());
		map.put(corAnglais, new ArrayList<>());
		map.put(clarinetEflat, new ArrayList<>());
		map.put(clarinet, new ArrayList<>());
		map.put(bassClarinet, new ArrayList<>());
		map.put(bassoon, new ArrayList<>());
		map.put(contrabassoon, new ArrayList<>());
	}

}
