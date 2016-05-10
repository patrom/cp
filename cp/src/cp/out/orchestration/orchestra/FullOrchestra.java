package cp.out.orchestration.orchestra;

import java.util.ArrayList;

import org.jfree.chart.renderer.xy.XYLine3DRenderer;

import cp.out.instrument.brass.BassTrombone;
import cp.out.instrument.brass.FrenchHorn;
import cp.out.instrument.brass.Trombone;
import cp.out.instrument.brass.Trumpet;
import cp.out.instrument.brass.Tuba;
import cp.out.instrument.keyboard.Celesta;
import cp.out.instrument.keyboard.Piano;
import cp.out.instrument.percussion.Glockenspiel;
import cp.out.instrument.percussion.Harp;
import cp.out.instrument.percussion.Timpani;
import cp.out.instrument.percussion.Xylophone;
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

public class FullOrchestra extends Orchestra {

	public FullOrchestra() {
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
		
		horn = new FrenchHorn(10, 10);
		trumpet = new Trumpet(11, 11);
		trombone = new Trombone(12, 12);
		bassTrombone = new BassTrombone(13,13);
		tuba = new Tuba(14, 14);
		map.put(horn, new ArrayList<>());
		map.put(trumpet, new ArrayList<>());
		map.put(trombone, new ArrayList<>());
		map.put(bassTrombone, new ArrayList<>());
		map.put(tuba, new ArrayList<>());
		
		glockenspiel = new Glockenspiel(15, 15);
		celesta = new Celesta(16, 16);
		xylophone = new Xylophone(17, 17);
		piano = new Piano(18,18);
		harp = new Harp(19, 19);
		timpani = new Timpani(20, 20);
		map.put(glockenspiel, new ArrayList<>());
		map.put(celesta, new ArrayList<>());
		map.put(xylophone, new ArrayList<>());
		map.put(piano, new ArrayList<>());
		map.put(harp, new ArrayList<>());
		map.put(timpani, new ArrayList<>());
		
		violin1 = new ViolinsI(21, 21);
		violin2 = new ViolinsII();
		viola = new Viola(22, 22);
		cello = new Cello(23, 23);
		bass = new Doublebass(24, 24);
		map.put(violin1, new ArrayList<>());
		map.put(violin2, new ArrayList<>());
		map.put(viola, new ArrayList<>());
		map.put(cello, new ArrayList<>());
		map.put(bass, new ArrayList<>());
	}
	
}
