package cp.out.print.note;

import org.springframework.stereotype.Component;

@Component(value="Fsharp")
public class Fsharp implements Key{

	@Override
	public String getStep() {
		return "F";
	}

	@Override
	public String getAlter() {
		return "1";
	}
	
	@Override
	public int getKeySignature() {
		return 6;
	}
	
	@Override
	public int getInterval() {
		return 6;
	}

}

