package cp.out.print.note;

import org.springframework.stereotype.Component;

@Component(value = "Bsharp")
public class Bsharp implements Key{

	@Override
	public String getStep() {
		return "B";
	}

	@Override
	public String getAlter() {
		return "1";
	}
	
	@Override
	public int getKeySignature() {
		return 0;
	}
	
	@Override
	public int getInterval() {
		return 0;
	}
}
