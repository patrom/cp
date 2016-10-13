package cp.out.print.note;

import org.springframework.stereotype.Component;

@Component(value="E")
public class E implements Key{

	@Override
	public String getStep() {
		return "E";
	}

	@Override
	public String getAlter() {
		return "";
	}
	
	@Override
	public int getKeySignature() {
		return 4;
	}
	
	@Override
	public int getInterval() {
		return 4;
	}

}
