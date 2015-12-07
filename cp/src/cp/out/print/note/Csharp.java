package cp.out.print.note;

import org.springframework.stereotype.Component;

@Component(value="Csharp")
public class Csharp implements NoteStep{

	@Override
	public String getStep() {
		return "C";
	}

	@Override
	public String getAlter() {
		return "1";
	}
	
	@Override
	public int getKeySignature() {
		return 8;
	}
	
	@Override
	public int getInterval() {
		return 1;
	}

}
