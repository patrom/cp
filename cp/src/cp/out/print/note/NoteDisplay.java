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
	

	public NoteStep getNoteStep(int pitchClass){
		switch (musicProperties.getKeySignature()) {
		case 0:
			return keyOfC.get(pitchClass);
		default:
			break;
		}
		throw new IllegalStateException("Key signature is not (yet) defined: " + musicProperties.getKeySignature());
		
	}
}
