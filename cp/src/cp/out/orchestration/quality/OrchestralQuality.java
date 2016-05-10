package cp.out.orchestration.quality;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;

import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.orchestration.OrderComparator;
import cp.util.RandomUtil;

public abstract class OrchestralQuality {

	protected String quality;
	protected String color;
	protected String type;
	protected List<Instrument> instruments = new ArrayList<>();
	protected List<Instrument> complementaryInstruments = new ArrayList<>();
	
	public String getQuality() {
		return quality;
	}

	public String getColor() {
		return color;
	}

	public String getType() {
		return type;
	}

	public Instrument getBasicInstrument(String name){
		return instruments.stream().filter(i -> i.getInstrumentName().equals(name)).findFirst().get();
	}
	
	public List<Instrument> getBasicInstruments(){
		return instruments;
	}
	
	public List<Instrument> getBasicInstrumentsByGroup(InstrumentGroup instrumentGroup){
		return instruments.stream().filter(i -> i.getInstrumentGroup() == instrumentGroup).sorted(new OrderComparator()).collect(toList());
	}
	
	public Instrument getComplementaryInstrument(String name){
		return complementaryInstruments.stream().filter(i -> i.getInstrumentName().equals(name)).findFirst().get();
	}
	
	public List<Instrument> getComplementarInstruments(){
		return complementaryInstruments;
	}
	
	public List<Instrument> getComplementarInstrumentsByGroup(InstrumentGroup instrumentGroup){
		return complementaryInstruments.stream().filter(i -> i.getInstrumentGroup() == instrumentGroup).sorted(new OrderComparator()).collect(toList());
	}
	
	public Instrument getRandomBasicInstrument(){
		return RandomUtil.getRandomFromList(instruments);
	}
	
	public boolean hasBasicInstrument(Instrument instrument){
		return instruments.contains(instrument);
	}
	
}
