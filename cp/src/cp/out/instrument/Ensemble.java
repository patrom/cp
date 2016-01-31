package cp.out.instrument;

import java.util.ArrayList;
import java.util.List;

import cp.out.instrument.strings.Cello;
import cp.out.instrument.strings.Doublebass;
import cp.out.instrument.strings.Viola;
import cp.out.instrument.strings.Violin;
import cp.out.instrument.voice.Alto;
import cp.out.instrument.voice.Bass;
import cp.out.instrument.voice.Soprano;
import cp.out.instrument.voice.Tenor;
import cp.out.instrument.woodwinds.Bassoon;
import cp.out.instrument.woodwinds.Clarinet;
import cp.out.instrument.woodwinds.Flute;
import cp.out.instrument.woodwinds.Oboe;

//reverse numbering Kontakt!
public class Ensemble {
	
	public static List<Instrument> getStringDuo(){
		List<Instrument> stringDuo = new ArrayList<Instrument>();
		stringDuo.add(new Violin(1, 1));
		stringDuo.add(new Cello(0, 0));
		return stringDuo;
	}

	public static List<Instrument> getStringQuartet(){
		List<Instrument> stringQuartet = new ArrayList<Instrument>();
		stringQuartet.add(new Violin(3, 0));
		stringQuartet.add(new Violin(2, 0));
		stringQuartet.add(new Viola(1, 1));
		stringQuartet.add(new Cello(0, 2));
		return stringQuartet;
	}
	
	public static List<Instrument> getWindQuartet(){
		List<Instrument> instruments = new ArrayList<Instrument>();
		instruments.add(new Flute(3, 3));
		instruments.add(new Oboe(2, 1));
		instruments.add(new Clarinet(1, 2));
		instruments.add(new Bassoon(0, 0));
		return instruments;
	}
	
	public static List<Instrument> getPianoAndViolin(){
		List<Instrument> voices = new ArrayList<Instrument>();
		voices.add(new Violin(4, 1));
		voices.add(new Piano(3, 0));
		voices.add(new Piano(2, 0));
		voices.add(new Piano(1, 0));
		voices.add(new Piano(0, 0));
		return voices;
	}
	
	public static List<Instrument> getPianoAndFlute(){
		List<Instrument> voices = new ArrayList<Instrument>();
		voices.add(new Flute(4, 1));
		voices.add(new Piano(3, 0));
		voices.add(new Piano(2, 0));
		voices.add(new Piano(1, 0));
		voices.add(new Piano(0, 0));
		return voices;
	}
	
	public static List<Instrument> getPianoAnd2Flutes(){
		List<Instrument> voices = new ArrayList<Instrument>();
		voices.add(new Flute(5, 1));
		voices.add(new Flute(4, 1));
		voices.add(new Piano(3, 0));
		voices.add(new Piano(2, 0));
		voices.add(new Piano(1, 0));
		voices.add(new Piano(0, 0));
		return voices;
	}
	
	public static List<Instrument> getChoir(){
		List<Instrument> voices = new ArrayList<Instrument>();
		voices.add(new Soprano(3, 0));
		voices.add(new Alto(2, 1));
		voices.add(new Tenor(1, 2));
		voices.add(new Bass(0, 3));
		return voices;
	}
	
	public static List<Instrument> getPiano(int totalVoices){
		List<Instrument> voices = new ArrayList<Instrument>();
		for (int i = 0; i < totalVoices; i++) {
			voices.add(new Piano(i, 0));
		}
		return voices;
	}
	
	public static List<Instrument> getStrings(){
		List<Instrument> strings = new ArrayList<Instrument>();
		strings.add(new Violin(3, 0));
		strings.add(new Viola(2, 1));
		strings.add(new Cello(1, 2));
		strings.add(new Doublebass(0, 3));
		return strings;
	}
	
	public static List<Instrument> getStringsDoubleTriads(){
		List<Instrument> strings = new ArrayList<Instrument>();
		strings.add(new Violin(5, 0));
		strings.add(new Violin(4, 0));
		strings.add(new Violin(3, 0));
		strings.add(new Cello(2, 2));
		strings.add(new Cello(1, 2));
		strings.add(new Doublebass(0, 3));
		return strings;
	}
}
