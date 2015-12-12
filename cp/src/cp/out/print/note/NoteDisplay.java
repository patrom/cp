package cp.out.print.note;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cp.generator.MusicProperties;

@Component
public class NoteDisplay {
	
	@Autowired
	private MusicProperties musicProperties;
	@Resource(name = "keyOfC")
	private Map<Integer, NoteStep> keyOfC;
	@Resource(name = "keyOfD")
	private Map<Integer, NoteStep> keyOfD;
	

	public NoteStep getNoteStep(int pitchClass){
		switch (musicProperties.getKeySignature()) {
		case 0:
			return keyOfC.get(pitchClass);
		case 2:
			return keyOfD.get(pitchClass);
		default:
			break;
		}
		throw new IllegalStateException("Key signature is not (yet) defined: " + musicProperties.getKeySignature());
		
	}
}
