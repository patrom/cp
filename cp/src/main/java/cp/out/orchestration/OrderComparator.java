package cp.out.orchestration;

import cp.out.instrument.Instrument;

import java.util.Comparator;

public class OrderComparator implements Comparator<Instrument> {

	@Override
	public int compare(Instrument instrument1, Instrument instrument2) {
		if (instrument1.getOrder() < instrument2.getOrder()) {
			return -1;
		} else if (instrument1.getOrder() > instrument2.getOrder()){
			return 1;
		}
		return 0;
	}

}
