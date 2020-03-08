package cp.out.orchestration.orchestra;

import cp.out.instrument.brass.*;
import cp.out.instrument.keyboard.Celesta;
import cp.out.instrument.keyboard.Piano;
import cp.out.instrument.percussion.determinate.Glockenspiel;
import cp.out.instrument.percussion.determinate.Timpani;
import cp.out.instrument.percussion.determinate.Xylophone;
import cp.out.instrument.plucked.Harp;
import cp.out.instrument.strings.*;
import cp.out.instrument.woodwinds.*;
import cp.out.play.InstrumentMapping;

import java.util.ArrayList;

public class FullOrchestra extends Orchestra {

	public FullOrchestra() {
		piccolo = new InstrumentMapping(new Piccolo(), 2, 1);
		flute = new InstrumentMapping(new Flute(), 2, 2);
		altoFlute = new InstrumentMapping(new AltoFlute(), 2, 3);
		oboe = new InstrumentMapping(new Oboe(), 2, 4);
		corAnglais = new InstrumentMapping(new CorAnglais(), 2, 5);
		clarinetEflat = new InstrumentMapping(new ClarinetEFlat(), 2, 6);
		clarinet = new InstrumentMapping(new Clarinet(), 2, 7);
		bassClarinet = new InstrumentMapping(new BassClarinet(), 2, 8);
		bassoon = new InstrumentMapping(new Bassoon(), 2, 9);
		contrabassoon = new InstrumentMapping(new ContraBassoon(), 2, 10);
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

		horn = new InstrumentMapping(new FrenchHorn(), 2, 11);
		trumpet = new InstrumentMapping(new Trumpet(), 2, 12);
		trombone = new InstrumentMapping(new Trombone(), 2, 13);
		bassTrombone = new InstrumentMapping(new BassTrombone(), 2, 14);
		tuba = new InstrumentMapping(new Tuba(), 2, 15);
		map.put(horn, new ArrayList<>());
		map.put(trumpet, new ArrayList<>());
		map.put(trombone, new ArrayList<>());
		map.put(bassTrombone, new ArrayList<>());
		map.put(tuba, new ArrayList<>());

		glockenspiel = new InstrumentMapping(new Glockenspiel(), 2, 16);
		celesta =  new InstrumentMapping(new Celesta(), 2, 17);
		xylophone =  new InstrumentMapping(new Xylophone(), 2, 18);
		piano =  new InstrumentMapping(new Piano(), 2, 19);
		harp =  new InstrumentMapping(new Harp(), 2, 20);
		timpani =  new InstrumentMapping(new Timpani(), 2, 21);
		map.put(glockenspiel, new ArrayList<>());
		map.put(celesta, new ArrayList<>());
		map.put(xylophone, new ArrayList<>());
		map.put(piano, new ArrayList<>());
		map.put(harp, new ArrayList<>());
		map.put(timpani, new ArrayList<>());

		violin1 = new InstrumentMapping(new ViolinsI(), 2, 22);
		violin2 = new InstrumentMapping(new ViolinsII(), 2, 23);
		viola = new InstrumentMapping(new Viola(), 2, 24);
		cello = new InstrumentMapping(new Cello(), 2, 25);
		bass = new InstrumentMapping(new DoubleBass(), 2, 26);
		map.put(violin1, new ArrayList<>());
		map.put(violin2, new ArrayList<>());
		map.put(viola, new ArrayList<>());
		map.put(cello, new ArrayList<>());
		map.put(bass, new ArrayList<>());
	}
	
}
