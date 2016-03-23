package cp.out.orchestration.quality;

import java.util.ArrayList;
import java.util.List;

import cp.out.instrument.Instrument;
import cp.util.RandomUtil;

public abstract class OrchestralQuality {

	protected String quality;
	protected String color;
	protected String type;
	protected List<Instrument> instruments = new ArrayList<>();
	
	public String getQuality() {
		return quality;
	}

	public String getColor() {
		return color;
	}

	public String getType() {
		return type;
	}

	public Instrument getInstrument(String name){
		return instruments.stream().filter(i -> i.getInstrumentName().equals(name)).findFirst().get();
	}
	
	public List<Instrument> getBasicInstruments(){
		return instruments;
	}
	
	public Instrument getRandomInstrument(){
		return RandomUtil.getRandomFromList(instruments);
	}
	
}
