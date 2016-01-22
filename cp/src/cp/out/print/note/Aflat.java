package cp.out.print.note;

import org.springframework.stereotype.Component;

@Component(value="Aflat")
public class Aflat implements NoteStep{

	@Override
	public String getStep() {
		return "A";
	}

	@Override
	public String getAlter() {
		return "-1";
	}

	@Override
	public int getKeySignature() {
		return -4;
	}
	
	@Override
	public int getInterval() {
		return 8;
	}

}

