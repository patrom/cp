package cp.out.print.note;

import org.springframework.stereotype.Component;

@Component(value="C")
public class C implements NoteStep{

	@Override
	public String getStep() {
		return "C";
	}

	@Override
	public String getAlter() {
		return "";
	}
	
	@Override
	public int getKeySignature() {
		return 0;
	}

	@Override
	public int getInterval() {
		return 0;
	}

}
