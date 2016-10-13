package cp.out.print.note;

import org.springframework.stereotype.Component;

@Component(value="Esharp")
public class Esharp implements Key{

	@Override
	public String getStep() {
		return "E";
	}

	@Override
	public String getAlter() {
		return "1";
	}
	
	@Override
	public int getKeySignature() {
		return -1;
	}
	
	@Override
	public int getInterval() {
		return 5;
	}
}
