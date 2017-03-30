package cp.out.orchestration.quality;

import cp.out.instrument.Instrument;
import cp.out.instrument.InstrumentGroup;
import cp.out.orchestration.OrderComparator;
import cp.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public abstract class OrchestralQuality {

	protected String quality;
	protected String color;
	protected String type;
	protected List<Instrument> instruments = new ArrayList<>();
	protected List<Instrument> complementaryInstruments = new ArrayList<>();
	protected final List<OrchestralQuality> closeQualities = new ArrayList<>();
	
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
		Optional<Instrument> instrument = instruments.stream().filter(i -> i.getInstrumentName().equals(name)).findFirst();
		if(instrument.isPresent()){
			return instrument.get();
		}
		throw new IllegalArgumentException("No Basic instrument (" + quality + ") found for: " + name);
	}
	
	public List<Instrument> getBasicInstruments(){
		return instruments;
	}
	
	public List<Instrument> getBasicInstrumentsByGroup(InstrumentGroup instrumentGroup){
		return instruments.stream().filter(i -> i.getInstrumentGroup() == instrumentGroup).sorted(new OrderComparator()).collect(toList());
	}
	
	public List<Instrument> getBasicInstrumentsByGroup(List<InstrumentGroup> instrumentGroup){
		return instruments.stream().filter(i -> instrumentGroup.contains(i.getInstrumentGroup())).sorted(new OrderComparator()).collect(toList());
	}
	
	public Instrument getComplementaryInstrument(String name){
		Optional<Instrument> instrument = complementaryInstruments.stream().filter(i -> i.getInstrumentName().equals(name)).findFirst();
		if(instrument.isPresent()){
			return instrument.get();
		}
		throw new IllegalArgumentException("No complementary instrument (" + quality + ") found for: " + name);
	}
	
	public List<Instrument> getComplementarInstruments(){
		return complementaryInstruments;
	}
	
	public List<Instrument> getComplementaryInstrumentsByGroup(InstrumentGroup instrumentGroup){
		return complementaryInstruments.stream().filter(i -> i.getInstrumentGroup() == instrumentGroup).sorted(new OrderComparator()).collect(toList());
	}
	
	public Instrument getRandomBasicInstrument(){
		return RandomUtil.getRandomFromList(instruments);
	}
	
	public boolean hasBasicInstrument(Instrument instrument){
		return instruments.contains(instrument);
	}

	public List<OrchestralQuality> getCloseQualities() {
		return closeQualities;
	}

	public List<Instrument> findInstrumentsInRange(int low, int high){
		List<Instrument> instrumentsFound = new ArrayList<>();
		for (Instrument instrument : instruments) {
			if(instrument.getLowest() >= low && instrument.getHighest() >= high){
				instrumentsFound.add(instrument);
			}

		}
		return instrumentsFound;
	}
	
}
