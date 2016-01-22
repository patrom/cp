package cp.out.print.note;

import org.springframework.stereotype.Component;

@Component(value="Gflat")
public class Gflat implements NoteStep{

	@Override
	public String getStep() {
		return "G";
	}

	@Override
	public String getAlter() {
		return "-1";
	}

	@Override
	public int getKeySignature() {
		return -6;
	}
	
	@Override
	public int getInterval() {
		return 6;
	}

}

