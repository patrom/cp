package cp.model;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;


@Component
public class TimeLine {

	private final Map<Integer, List<TimeLineKey>> keysPerVoice = new TreeMap<>();
	
	public TimeLineKey getTimeLineKeyAtPosition(int position, int voice){
		List<TimeLineKey> keys = keysPerVoice.get(voice);
		return keys.stream().filter(k -> k.getStart() <= position && position < k.getEnd()).findFirst().get();
	}

	public void addKeysForVoice(List<TimeLineKey> keys, int voice){
		this.keysPerVoice.put(voice, keys);
	}
	
}
