package cp.out.print.note;

import org.springframework.stereotype.Component;

@Component(value="A")
public class A implements Key{

	@Override
	public String getStep() {
		return "A";
	}

	@Override
	public String getAlter() {
		return "";
	}

	@Override
	public int getKeySignature() {
		return 3;
	}
	
	@Override
	public int getInterval() {
		return 9;
	}

}
