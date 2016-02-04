package cp.out.print.note;

import org.springframework.stereotype.Component;

@Component(value="D")
public class D implements Key{

	@Override
	public String getStep() {
		return "D";
	}

	@Override
	public String getAlter() {
		return "";
	}
	
	@Override
	public int getKeySignature() {
		return 2;
	}
	
	@Override
	public int getInterval() {
		return 2;
	}

}