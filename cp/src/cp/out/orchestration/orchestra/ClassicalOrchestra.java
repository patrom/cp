package cp.out.orchestration.orchestra;

import java.util.ArrayList;

import cp.out.instrument.brass.FrenchHorn;
import cp.out.instrument.brass.Trombone;
import cp.out.instrument.brass.Trumpet;
import cp.out.instrument.brass.Tuba;
import cp.out.instrument.strings.Cello;
import cp.out.instrument.strings.Doublebass;
import cp.out.instrument.strings.Viola;
import cp.out.instrument.strings.ViolinsI;
import cp.out.instrument.strings.ViolinsII;
import cp.out.instrument.woodwinds.Bassoon;
import cp.out.instrument.woodwinds.Clarinet;
import cp.out.instrument.woodwinds.Flute;
import cp.out.instrument.woodwinds.Oboe;


public class ClassicalOrchestra extends Orchestra {

	public ClassicalOrchestra() {
		flute = new Flute(0, 0);
		oboe = new Oboe(1, 1);
		clarinet = new Clarinet(2, 2);
		bassoon = new Bassoon(3, 3);
		map.put(flute, new ArrayList<>());
		map.put(oboe, new ArrayList<>());
		map.put(clarinet, new ArrayList<>());
		map.put(bassoon, new ArrayList<>());
		
		horn = new FrenchHorn(4, 4);
		trumpet = new Trumpet(5, 5);
		trombone = new Trombone(6, 6);
		tuba = new Tuba(7, 7);
		map.put(horn, new ArrayList<>());
		map.put(trumpet, new ArrayList<>());
		map.put(trombone, new ArrayList<>());
		map.put(tuba, new ArrayList<>());
		
		violin1 = new ViolinsI(8, 8);
		violin2 = new ViolinsII(9, 9);
		viola = new Viola(10, 10);
		cello = new Cello(11, 11);
		bass = new Doublebass(12, 12);
		map.put(violin1, new ArrayList<>());
		map.put(violin2, new ArrayList<>());
		map.put(viola, new ArrayList<>());
		map.put(cello, new ArrayList<>());
		map.put(bass, new ArrayList<>());
	}
	
}
