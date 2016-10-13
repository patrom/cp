package cp.out.print.note;

import org.springframework.stereotype.Component;

@Component(value="Gsharp")
public class Gsharp implements Key{

	@Override
	public String getStep() {
		return "G";
	}

	@Override
	public String getAlter() {
		return "1";
	}
	
	@Override
	public int getKeySignature() {
		return 7;
	}

	@Override
	public int getInterval() {
		return 8;
	}
}

