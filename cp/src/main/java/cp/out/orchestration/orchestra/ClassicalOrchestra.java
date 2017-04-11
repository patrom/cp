package cp.out.orchestration.orchestra;

import cp.out.instrument.brass.*;
import cp.out.instrument.strings.*;
import cp.out.instrument.woodwinds.*;
import cp.out.play.InstrumentMapping;

import java.util.ArrayList;


public class ClassicalOrchestra extends Orchestra {

	public ClassicalOrchestra() {
		piccolo = new InstrumentMapping(new Piccolo(), 2, 0);
		flute = new InstrumentMapping(new Flute(), 1, 1);
		altoFlute = new InstrumentMapping(new AltoFlute(), 2, 2);
		oboe = new InstrumentMapping(new Oboe(), 2, 3);
		corAnglais = new InstrumentMapping(new CorAnglais(), 2, 4);
		clarinetEflat = new InstrumentMapping(new ClarinetEFlat(), 2, 5);
		clarinet = new InstrumentMapping(new Clarinet(), 3, 6);
		bassClarinet = new InstrumentMapping(new BassClarinet(), 2, 7);
		bassoon = new InstrumentMapping(new Bassoon(), 4, 8);
		contrabassoon = new InstrumentMapping(new ContraBassoon(), 2, 9);
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
		
		horn = new InstrumentMapping(new FrenchHorn(), 5, 10);
		trumpet = new InstrumentMapping(new Trumpet(), 6, 11);
		trombone = new InstrumentMapping(new Trombone(), 7, 12);
		bassTrombone = new InstrumentMapping(new BassTrombone(), 2, 13);
		tuba = new InstrumentMapping(new Tuba(), 2, 14);
		map.put(horn, new ArrayList<>());
		map.put(trumpet, new ArrayList<>());
		map.put(trombone, new ArrayList<>());
		map.put(bassTrombone, new ArrayList<>());
		map.put(tuba, new ArrayList<>());
		
		violin1 = new InstrumentMapping(new ViolinsI(), 8, 15);
		violin2 = new InstrumentMapping(new ViolinsII(), 8, 16);
		viola = new InstrumentMapping(new Viola(), 9, 17);
		cello = new InstrumentMapping(new Cello(), 10, 18);
		bass = new InstrumentMapping(new DoubleBass(), 11, 19);
		map.put(violin1, new ArrayList<>());
		map.put(violin2, new ArrayList<>());
		map.put(viola, new ArrayList<>());
		map.put(cello, new ArrayList<>());
		map.put(bass, new ArrayList<>());
	}
	
}
