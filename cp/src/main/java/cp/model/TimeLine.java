package cp.model;

import cp.config.InstrumentConfig;
import cp.model.contour.Contour;
import cp.model.note.Scale;
import cp.out.print.note.Key;
import cp.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;


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

    public void addTimeLineKey(int voice, Key key, Scale scale, int duration) {
        if (keysPerVoice.get(voice) == null) {
            List<TimeLineKey> timeLineKeys = new ArrayList<>();
            timeLineKeys.add(new TimeLineKey(key, scale, 0, duration));
            keysPerVoice.put(voice, timeLineKeys);
        } else {
            List<TimeLineKey> timeLineKeys = keysPerVoice.get(voice);
            Collections.sort(timeLineKeys);
            TimeLineKey lastTimeLineKey = timeLineKeys.get(timeLineKeys.size() - 1);
            int start = lastTimeLineKey.getEnd();
            timeLineKeys.add( new TimeLineKey(key, scale, start, start + duration));
            keysPerVoice.put(voice, timeLineKeys);
        }
    }

	public void addKeysForVoice(int voice, TimeLineKey... key){
		this.keysPerVoice.put(voice, Arrays.asList(key));
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
		List<TimeLineKey> allTimeLineKeys = new ArrayList<>(timeLineKeys);
		List<TimeLineKey> keys = new ArrayList<>();
		int start = 0;
		int end = 0;
		while (start < compositionEnd) {
			int duration = RandomUtil.getRandomFromList(durations);
			end = end + duration;
            TimeLineKey timeLineKey = RandomUtil.getRandomFromList(allTimeLineKeys);
            keys.add(new TimeLineKey(timeLineKey.getKey(), timeLineKey.getScale(), start, end));
            allTimeLineKeys.remove(timeLineKey);
            if (allTimeLineKeys.isEmpty()) {
                allTimeLineKeys = new ArrayList<>(timeLineKeys);
            }
			start = end;
		}
		int instrumentSize = instrumentConfig.getSize();
		for (int i = 0; i < instrumentSize; i++) {
			addKeysForVoice(keys, i);
		}
	}

    public void randomKeysAndDurationsForVoice(int voice, List<TimeLineKey> timeLineKeys, List<Integer> durations) {
        List<TimeLineKey> allTimeLineKeys = new ArrayList<>(timeLineKeys);
        List<TimeLineKey> keys = new ArrayList<>();
        int start = 0;
        int end = 0;
        while (start < compositionEnd) {
            int duration = RandomUtil.getRandomFromList(durations);
            end = end + duration;
            TimeLineKey timeLineKey = RandomUtil.getRandomFromList(allTimeLineKeys);
            keys.add(new TimeLineKey(timeLineKey.getKey(), timeLineKey.getScale(), start, end));
            allTimeLineKeys.remove(timeLineKey);
            if (allTimeLineKeys.isEmpty()) {
                allTimeLineKeys = new ArrayList<>(timeLineKeys);
            }
            start = end;
        }
        addKeysForVoice(keys, voice);
    }

    public void randomKeysAndDurationsForVoices(List<Integer> durations , List<TimeLineKey>... timeLineKeys) {
        List<List<TimeLineKey>> allKeys = new ArrayList<>(Arrays.asList(timeLineKeys));
        Map<Integer, List<TimeLineKey>> allTimeLineKeys = new HashMap<>();
        int start = 0;
        int end = 0;
        while (start < compositionEnd) {
            int duration = RandomUtil.getRandomFromList(durations);
            end = end + duration;
            List<TimeLineKey> randomTimeLineKey = RandomUtil.getRandomFromList(allKeys);
            for (TimeLineKey timeLineKey : randomTimeLineKey) {
                allTimeLineKeys.computeIfAbsent(timeLineKey.getVoice(), voice -> new ArrayList<>())
                        .add(new TimeLineKey(timeLineKey.getKey(), timeLineKey.getScale(), start, end));
            }
            allKeys.remove(randomTimeLineKey);
            if (allKeys.isEmpty()) {
                allKeys = new ArrayList<>(Arrays.asList(timeLineKeys));
            }
            start = end;
        }
        for (Map.Entry<Integer, List<TimeLineKey>> entry : allTimeLineKeys.entrySet()) {
            addKeysForVoice(entry.getValue(), entry.getKey());
        }
    }

    public void randomKeysAndDurationsForVoices(List<Integer> durations , List<List<TimeLineKey>> timeLineKeys) {
        List<List<TimeLineKey>> allKeys = new ArrayList<>(timeLineKeys);
        Map<Integer, List<TimeLineKey>> allTimeLineKeys = new HashMap<>();
        int start = 0;
        int end = 0;
        while (start < compositionEnd) {
            int duration = RandomUtil.getRandomFromList(durations);
            end = end + duration;
            List<TimeLineKey> randomTimeLineKey = RandomUtil.getRandomFromList(allKeys);
            for (TimeLineKey timeLineKey : randomTimeLineKey) {
                allTimeLineKeys.computeIfAbsent(timeLineKey.getVoice(), voice -> new ArrayList<>())
                        .add(new TimeLineKey(timeLineKey.getKey(), timeLineKey.getScale(), start, end));
            }
            allKeys.remove(randomTimeLineKey);
            if (allKeys.isEmpty()) {
                allKeys = new ArrayList<>(timeLineKeys);
            }
            start = end;
        }
        for (Map.Entry<Integer, List<TimeLineKey>> entry : allTimeLineKeys.entrySet()) {
            addKeysForVoice(entry.getValue(), entry.getKey());
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

    public List<TimeLineKey> getTimelineKeys(int voice, int start, int end) {
        return this.keysPerVoice.get(voice).stream()
                .filter(timeLineKey ->
                        (timeLineKey.getStart() >= start && timeLineKey.getStart() < end) // bg end after
                                || (timeLineKey.getEnd() > start && timeLineKey.getEnd() <= end) // bg start before
                                || (start <= timeLineKey.getStart() && timeLineKey.getEnd() <= end) // bg in between
                                || (timeLineKey.getStart() < start && end < timeLineKey.getEnd())) // bg before and after
                .collect(Collectors.toList());
    }


	public void setEnd(int compositionEnd) {
		this.compositionEnd = compositionEnd;
	}

	public Map<Integer, List<TimeLineKey>> getKeysPerVoice() {
		return keysPerVoice;
	}
}
