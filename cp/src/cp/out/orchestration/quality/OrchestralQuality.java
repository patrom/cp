package cp.out.orchestration.quality;

import java.util.ArrayList;
import java.util.List;

import cp.out.instrument.register.InstrumentRegister;

public abstract class OrchestralQuality {

	protected String quality;
	protected String color;
	protected String type;
	protected List<InstrumentRegister> instrumentRegisters = new ArrayList<>();
	
	public List<InstrumentRegister> getInstrumentRegisters() {
		return instrumentRegisters;
	}
	public void setInstrumentRegisters(List<InstrumentRegister> instrumentRegisters) {
		this.instrumentRegisters = instrumentRegisters;
	}
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
	
}
