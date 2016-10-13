package cp.out.print.note;

import org.springframework.stereotype.Component;

@Component(value="F")
public class F implements Key{

	@Override
	public String getStep() {
		return "F";
	}

	@Override
	public String getAlter() {
		return "";
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
