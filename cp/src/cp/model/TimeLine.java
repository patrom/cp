package cp.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import cp.out.print.note.Key;

@Component
public class TimeLine {

	private List<TimeLineKey> keys = new ArrayList<>();
	
	public Key getKeyAtPosition(int position){
		return keys.stream().filter(k -> k.getStart() <= position && position < k.getEnd()).findFirst().get().getKey();
	}

	public List<TimeLineKey> getKeys() {
		return keys;
	}

	public void setKeys(List<TimeLineKey> keys) {
		this.keys = keys;
	}
	
}
