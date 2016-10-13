package cp.model;

import cp.model.note.Scale;
import cp.out.print.note.Key;

public class TimeLineKey {

	private Key key;
	private int start;
	private int end;
	private Scale scale;

	public TimeLineKey(Key key, Scale scale, int start, int end) {
		this.key = key;
		this.start = start;
		this.end = end;
		this.scale = scale;
	}
	
	public Key getKey() {
		return key;
	}
	
	public int getStart() {
		return start;
	}
	
	public int getEnd() {
		return end;
	}
	
	public Scale getScale() {
		return scale;
	}
	
}
