package cp.out.print.note;

import org.springframework.stereotype.Component;

@Component(value="G")
public class G implements Key{

	@Override
	public String getStep() {
		return "G";
	}

	@Override
	public String getAlter() {
		return "";
	}

	@Override
	public int getKeySignature() {
		return 1;
	}
	
	@Override
	public int getInterval() {
		return 7;
	}

}
