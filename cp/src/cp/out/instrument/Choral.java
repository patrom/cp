package cp.out.instrument;

import java.util.ArrayList;
import java.util.List;

import cp.out.instrument.voice.Alto;
import cp.out.instrument.voice.Bass;
import cp.out.instrument.voice.Soprano;
import cp.out.instrument.voice.Tenor;

public class Choral {

	public static List<Instrument> getSATB(){
		List<Instrument> voices = new ArrayList<Instrument>();
		voices.add(new Bass());
		voices.add(new Tenor());
		voices.add(new Alto());
		voices.add(new Soprano());
		return voices;
	}
}
