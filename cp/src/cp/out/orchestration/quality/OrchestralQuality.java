package cp.out.orchestration.quality;

import java.util.ArrayList;
import java.util.List;

import cp.out.instrument.Instrument;

public abstract class OrchestralQuality {

	protected String quality;
	protected String color;
	protected String type;
	protected List<Instrument> instruments = new ArrayList<>();
	
	public String getQuality() {
		return quality;
	}
	public void setQuality(String quality) {
		this.quality = quality;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<Instrument> getInstruments() {
		return instruments;
	}
	public void setInstruments(List<Instrument> instruments) {
		this.instruments = instruments;
	}
	
}
