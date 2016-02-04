package cp.out.print.note;

import org.springframework.stereotype.Component;

@Component(value="B")
public class B implements Key{

	@Override
	public String getStep() {
		return "B";
	}

	@Override
	public String getAlter() {
		return "";
	}
	
	@Override
	public int getKeySignature() {
		return 5;
	}
	
	@Override
	public int getInterval() {
		return 11;
	}

}
