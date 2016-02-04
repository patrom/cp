package cp.model;

import cp.out.print.note.Key;

public class TimeLineKey {

	private Key key;
	private int start;
	private int end;

	public TimeLineKey(Key key, int start, int end) {
		super();
		this.key = key;
		this.start = start;
		this.end = end;
	}
	
	public Key getKey() {
		return key;
	}
	public void setKey(Key key) {
		this.key = key;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	
}
