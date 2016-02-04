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
	private Map<Integer, Key> keyOfC;
	@Resource(name = "keyOfG")
	private Map<Integer, Key> keyOfG;
	@Resource(name = "keyOfD")
	private Map<Integer, Key> keyOfD;
	@Resource(name = "keyOfA")
	private Map<Integer, Key> keyOfA;
	@Resource(name = "keyOfE")
	private Map<Integer, Key> keyOfE;
	@Resource(name = "keyOfB")
	private Map<Integer, Key> keyOfB;
	@Resource(name = "keyOfFsharp")
	private Map<Integer, Key> keyOfFsharp;
	@Resource(name = "keyOfF")
	private Map<Integer, Key> keyOfF;
	@Resource(name = "keyOfBflat")
	private Map<Integer, Key> keyOfBflat;
	@Resource(name = "keyOfEflat")
	private Map<Integer, Key> keyOfEflat;
	@Resource(name = "keyOfAflat")
	private Map<Integer, Key> keyOfAflat;
	@Resource(name = "keyOfDflat")
	private Map<Integer, Key> keyOfDflat;
	@Resource(name = "keyOfGflat")
	private Map<Integer, Key> keyOfGflat;
	

	public Key getNoteStep(int pitchClass){
		switch (musicProperties.getKeySignature()) {
		case 0:
			return keyOfC.get(pitchClass);
		case 1:
			return keyOfG.get(pitchClass);
		case 2:
			return keyOfD.get(pitchClass);
		case 3:
			return keyOfA.get(pitchClass);
		case 4:
			return keyOfE.get(pitchClass);
		case 5:
			return keyOfB.get(pitchClass);
		case 6:
			return keyOfFsharp.get(pitchClass);
		case -1:
			return keyOfF.get(pitchClass);
		case -2:
			return keyOfBflat.get(pitchClass);
		case -3:
			return keyOfEflat.get(pitchClass);
		case -4:
			return keyOfAflat.get(pitchClass);
		case -5:
			return keyOfDflat.get(pitchClass);
		case -6:
			return keyOfGflat.get(pitchClass);
		default:
			break;
		}
		throw new IllegalStateException("Key signature is not (yet) defined: " + musicProperties.getKeySignature());
		
	}
}
