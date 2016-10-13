package cp.out.print.note;

import org.springframework.stereotype.Component;

@Component(value="Asharp")
public class Asharp implements Key{
	
	@Override
	public String getStep() {
		return "A";
	}

	@Override
	public String getAlter() {
		return "1";
	}

	@Override
	public int getKeySignature() {
		return -2;
	}
	
	@Override
	public int getInterval() {
		return 10;
	}

}
