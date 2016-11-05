package cp.out.orchestration.orchestra;

import cp.out.instrument.brass.*;
import cp.out.instrument.strings.*;
import cp.out.instrument.woodwinds.*;
import cp.out.play.InstrumentMapping;

import java.util.ArrayList;


public class ClassicalOrchestra extends Orchestra {

	public ClassicalOrchestra() {
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
		
		horn = new InstrumentMapping(new FrenchHorn(), 2, 1);
		trumpet = new InstrumentMapping(new Trumpet(), 2, 1);
		trombone = new InstrumentMapping(new Trombone(), 2, 1);
		bassTrombone = new InstrumentMapping(new BassTrombone(), 2, 1);
		tuba = new InstrumentMapping(new Tuba(), 2, 1);
		map.put(horn, new ArrayList<>());
		map.put(trumpet, new ArrayList<>());
		map.put(trombone, new ArrayList<>());
		map.put(bassTrombone, new ArrayList<>());
		map.put(tuba, new ArrayList<>());
		
		violin1 = new InstrumentMapping(new ViolinsI(), 2, 1);
		violin2 = new InstrumentMapping(new ViolinsII(), 2, 1);
		viola = new InstrumentMapping(new Viola(), 2, 1);
		cello = new InstrumentMapping(new Cello(), 2, 1);
		bass = new InstrumentMapping(new Doublebass(), 2, 1);
		map.put(violin1, new ArrayList<>());
		map.put(violin2, new ArrayList<>());
		map.put(viola, new ArrayList<>());
		map.put(cello, new ArrayList<>());
		map.put(bass, new ArrayList<>());
	}
	
}
