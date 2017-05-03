package cp.model;

import cp.model.contour.Contour;
import cp.out.play.InstrumentConfig;
import cp.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class TimeLine {

	private final Map<Integer, List<TimeLineKey>> keysPerVoice = new TreeMap<>();
	private final Map<Integer, List<Contour>> contourPerVoice = new TreeMap<>();
	@Autowired
	private InstrumentConfig instrumentConfig;

	private int compositionEnd;
	
	public TimeLineKey getTimeLineKeyAtPosition(int position, int voice){
		List<TimeLineKey> keys = keysPerVoice.get(voice);
		Optional<TimeLineKey> optional = keys.stream().filter(k -> k.getStart() <= position && position < k.getEnd()).findFirst();
		if(optional.isPresent()){
			return optional.get();
		}
		throw new IllegalArgumentException("No Key found at position; " + position + " for voice: " + voice);
	}

	public Contour getContourAtPosition(int position, int voice){
		List<Contour> contours = contourPerVoice.get(voice);
		Optional<Contour> optional = contours.stream().filter(k -> k.getPosition() <= position && position <= k.getEnd()).findFirst();
		if(optional.isPresent()){
			return optional.get();
		}
		throw new IllegalArgumentException("No Contour found at position; " + position + " for voice: " + voice);
	}

	public void addKeysForVoice(List<TimeLineKey> keys, int voice){
		this.keysPerVoice.put(voice, keys);
	}

	public void addContourForVoice(List<Contour> contours, int voice){
		this.contourPerVoice.put(voice, contours);
	}

	public void randomKeys(List<TimeLineKey> timeLineKeys, int... durations) {
		List<TimeLineKey> keys = new ArrayList<>();
		int start = 0;
		int end = 0;
		for (int duration :durations) {
			end = end + duration;
			TimeLineKey timeLineKey = RandomUtil.getRandomFromList(timeLineKeys);
			keys.add(new TimeLineKey(timeLineKey.getKey(), timeLineKey.getScale(), start, end));
			start = end;
		}
		int instrumentSize = instrumentConfig.getSize();
		for (int i = 0; i < instrumentSize; i++) {
			addKeysForVoice(keys, i);
		}
	}

	public void randomKeysAndDurations(List<TimeLineKey> timeLineKeys, List<Integer> durations) {
		List<TimeLineKey> keys = new ArrayList<>();
		int start = 0;
		int end = 0;
		while (start < compositionEnd) {
			int duration = RandomUtil.getRandomFromList(durations);
			end = end + duration;
			TimeLineKey timeLineKey = RandomUtil.getRandomFromList(timeLineKeys);
			keys.add(new TimeLineKey(timeLineKey.getKey(), timeLineKey.getScale(), start, end));
			start = end;
		}
		int instrumentSize = instrumentConfig.getSize();
		for (int i = 0; i < instrumentSize; i++) {
			addKeysForVoice(keys, i);
		}
	}

	public void repeatContourPattern(int duration, int voice, int... directions){
		List<Contour> contouren = new ArrayList<>();
		int start = 0;
		int end = duration;
		int i = 0;
		while (start < compositionEnd) {
			int direction = directions[ i % directions.length];
			contouren.add(new Contour(start ,end, direction));
			start = end;
			end = end + duration;
			i++;
		}
		addContourForVoice(contouren, voice);
	}

	public void setEnd(int compositionEnd) {
		this.compositionEnd = compositionEnd;
	}

}
