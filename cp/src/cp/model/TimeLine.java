package cp.model;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Component;


@Component
public class TimeLine {

	private Map<Integer, List<TimeLineKey>> keysPerVoice = new TreeMap<>();
	
//	private List<TimeLineKey> keys = new ArrayList<>();
	
	public TimeLineKey getTimeLineKeyAtPosition(int position, int voice){
		List<TimeLineKey> keys = keysPerVoice.get(voice);
		return keys.stream().filter(k -> k.getStart() <= position && position < k.getEnd()).findFirst().get();
	}

//	public List<TimeLineKey> getKeys() {
//		return keys;
//	}
//
//	public void setKeys(List<TimeLineKey> keys) {
//		this.keys = keys;
//	}
	
	public void addKeysForVoice(List<TimeLineKey> keys, int voice){
		this.keysPerVoice.put(voice, keys);
	}
	
}
