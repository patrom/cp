package cp.out.orchestration.orchestra;

import java.util.ArrayList;

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

public class WindOrchestra extends Orchestra{

	public WindOrchestra() {
		piccolo = new Piccolo(0,0);
		flute = new Flute(1, 1);
		altoFlute = new AltoFlute(2, 2);
		oboe = new Oboe(3, 3);
		corAnglais = new CorAnglais(4,4);
		clarinetEflat = new ClarinetEFlat(5, 5);
		clarinet = new Clarinet(6, 6);
		bassClarinet = new BassClarinet(7,7);
		bassoon = new Bassoon(8, 8);
		contrabassoon = new ContraBassoon(9, 9);
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
