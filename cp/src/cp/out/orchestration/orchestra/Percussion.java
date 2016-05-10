package cp.out.orchestration.orchestra;

import java.util.ArrayList;

import cp.out.instrument.keyboard.Celesta;
import cp.out.instrument.keyboard.Piano;
import cp.out.instrument.percussion.Glockenspiel;
import cp.out.instrument.percussion.Harp;
import cp.out.instrument.percussion.Timpani;
import cp.out.instrument.percussion.Xylophone;

public class Percussion extends Orchestra{
	
	public Percussion(){
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
	}

}
