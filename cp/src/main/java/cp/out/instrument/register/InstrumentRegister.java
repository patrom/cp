package cp.out.instrument.register;

import cp.out.instrument.Articulation;

public class InstrumentRegister {

	protected int low;
	protected int high;
	protected Articulation articulation;
	protected String intensity;
	
	public InstrumentRegister() {
		super();
	}

	public InstrumentRegister(int low, int high) {
		this.low = low;
		this.high = high;
	}

	public int getHigh() {
		return high;
	}
	
	public void setHigh(int high) {
		this.high = high;
	}
	
	public int getLow() {
		return low;
	}
	
	public void setLow(int low) {
		this.low = low;
	}
}
