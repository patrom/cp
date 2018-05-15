package cp.out.orchestration.orchestra;

import cp.out.instrument.keyboard.Celesta;
import cp.out.instrument.keyboard.Piano;
import cp.out.instrument.percussion.determinate.Glockenspiel;
import cp.out.instrument.percussion.determinate.Timpani;
import cp.out.instrument.percussion.determinate.Xylophone;
import cp.out.instrument.plucked.Harp;
import cp.out.play.InstrumentMapping;

import java.util.ArrayList;

public class Percussion extends Orchestra{
	
	public Percussion(){
		glockenspiel = new InstrumentMapping(new Glockenspiel(), 2, 1);
		celesta =  new InstrumentMapping(new Celesta(), 2, 1);
		xylophone =  new InstrumentMapping(new Xylophone(), 2, 1);
		piano =  new InstrumentMapping(new Piano(), 2, 1);
		harp =  new InstrumentMapping(new Harp(), 2, 1);
		timpani =  new InstrumentMapping(new Timpani(), 2, 1);
		map.put(glockenspiel, new ArrayList<>());
		map.put(celesta, new ArrayList<>());
		map.put(xylophone, new ArrayList<>());
		map.put(piano, new ArrayList<>());
		map.put(harp, new ArrayList<>());
		map.put(timpani, new ArrayList<>());
	}

}
