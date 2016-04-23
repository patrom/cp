package cp.out.orchestration.orchestra;

import java.util.ArrayList;

import cp.out.instrument.brass.BassTrombone;
import cp.out.instrument.brass.FrenchHorn;
import cp.out.instrument.brass.Trombone;
import cp.out.instrument.brass.Trumpet;
import cp.out.instrument.brass.Tuba;
import cp.out.instrument.strings.Cello;
import cp.out.instrument.strings.Doublebass;
import cp.out.instrument.strings.Viola;
import cp.out.instrument.strings.ViolinsI;
import cp.out.instrument.strings.ViolinsII;
import cp.out.instrument.woodwinds.AltoFlute;
import cp.out.instrument.woodwinds.BassClarinet;
import cp.out.instrument.woodwinds.Bassoon;
import cp.out.instrument.woodwinds.Clarinet;
import cp.out.instrument.woodwinds.ClarinetEFlat;
import cp.out.instrument.woodwinds.ContraBassoon;
import cp.out.instrument.woodwinds.CorAnglais;
import cp.out.instrument.woodwinds.Flute;
import cp.out.instrument.woodwinds.Oboe;
import cp.out.instrument.woodwinds.Piccolo;


public class ClassicalOrchestra extends Orchestra {

	public ClassicalOrchestra() {
		piccolo = new Piccolo(0,0);
		flute = new Flute(1, 1);
		altoFlute = new AltoFlute(1, 1);
		oboe = new Oboe(2, 2);
		corAnglais = new CorAnglais(3,3);
		clarinetEflat = new ClarinetEFlat(4, 4);
		clarinet = new Clarinet(4, 4);
		bassClarinet = new BassClarinet(5,5);
		bassoon = new Bassoon(6, 6);
		contrabassoon = new ContraBassoon(6, 6);
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
		
		horn = new FrenchHorn(7, 7);
		trumpet = new Trumpet(8, 8);
		trombone = new Trombone(9, 9);
		bassTrombone = new BassTrombone(10,10);
		tuba = new Tuba(11, 11);
		map.put(horn, new ArrayList<>());
		map.put(trumpet, new ArrayList<>());
		map.put(trombone, new ArrayList<>());
		map.put(bassTrombone, new ArrayList<>());
		map.put(tuba, new ArrayList<>());
		
		violin1 = new ViolinsI(12, 12);
		violin2 = new ViolinsII();
		viola = new Viola(13, 13);
		cello = new Cello(14, 14);
		bass = new Doublebass(15, 15);
		map.put(violin1, new ArrayList<>());
		map.put(violin2, new ArrayList<>());
		map.put(viola, new ArrayList<>());
		map.put(cello, new ArrayList<>());
		map.put(bass, new ArrayList<>());
	}
	
}
