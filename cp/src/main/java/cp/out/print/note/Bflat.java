package cp.out.print.note;

import org.springframework.stereotype.Component;

@Component(value="Bflat")
public class Bflat implements Key{

	@Override
	public String getStep() {
		return "B";
	}

	@Override
	public String getAlter() {
		return "-1";
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

