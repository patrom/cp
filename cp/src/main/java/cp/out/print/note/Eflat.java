package cp.out.print.note;

import org.springframework.stereotype.Component;

@Component(value="Eflat")
public class Eflat implements Key{

	@Override
	public String getStep() {
		return "E";
	}

	@Override
	public String getAlter() {
		return "-1";
	}

	@Override
	public int getKeySignature() {
		return -3;
	}
	
	@Override
	public int getInterval() {
		return 3;
	}

}

