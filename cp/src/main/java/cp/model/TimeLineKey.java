package cp.model;

import cp.model.note.Scale;
import cp.out.print.note.Key;

public class TimeLineKey implements Comparable<TimeLineKey>{

    private Key key;
	private int start = 0;
	private int end = 0;
	private Scale scale;
	private int voice;

	public TimeLineKey(Key key, Scale scale) {
		this.key = key;
		this.scale = scale;
	}

	public TimeLineKey(Key key, Scale scale, int start, int end) {
		this.key = key;
		this.start = start;
		this.end = end;
		this.scale = scale;
	}

    public TimeLineKey(int voice, Key key, Scale scale, int start, int end) {
	    this.voice = voice;
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

    public int getPitchClassForKey(int pitchClass) {
	    return (pitchClass + key.getInterval()) % 12;
    }

    @Override
    public int compareTo(TimeLineKey timeLineKey) {
        return Integer.compare(this.start, timeLineKey.getStart());
    }

    public int getVoice() {
        return voice;
    }
}
