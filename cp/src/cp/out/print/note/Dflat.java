package cp.out.print.note;

import org.springframework.stereotype.Component;

@Component(value="Dflat")
public class Dflat implements NoteStep{

	@Override
	public String getStep() {
		return "D";
	}

	@Override
	public String getAlter() {
		return "-1";
	}

	@Override
	public int getKeySignature() {
		return -5;
	}
	
	@Override
	public int getInterval() {
		return 1;
	}

}

