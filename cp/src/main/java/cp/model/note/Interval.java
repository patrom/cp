package cp.model.note;

import java.util.HashMap;
import java.util.Map;


public class Interval  {
	
	public static double HARMONIC_OCTAVE_VALUE = 0.3;
	public static double MELODIC_OCTAVE_VALUE = 0.3;
	
	public static final Map<Integer,Interval> intervalMap = new HashMap<Integer,Interval>() {{
	    //cope
	    put(0, new Interval(0, 0.5, 0.2, 0.0, 0)); //octaaf
	    put(1, new Interval(1, 0.9, 0.05, 0.2, 2)); //harmonic value for geometric mean!!!!
	    put(2, new Interval(2, 1.0, 0.2, 0.4 , 4));
	    put(3, new Interval(3, 0.8, 0.775, 0.6, 6)); 
	    put(4, new Interval(4, 0.7, 0.8, 0.7, 8));
	    put(5, new Interval(5, 0.5, 0.45, 0.9, 9));
	    put(6, new Interval(6, 0.1, 0.35, 0.0, 0)); 
	    put(7, new Interval(7, 0.6, 0.9, 1.0, 10)); 
	    put(8, new Interval(8, 0.4, 0.725, 0.5, 5)); 
	    put(9, new Interval(9, 0.3, 0.75, 0.8, 7)); 
	    put(10, new Interval(10, 0.2, 0.3, 0.3, 3)); 
	    put(11, new Interval(11, 0.1, 0.1, 0.1, 1)); 
//	    put(12, new Interval(12, 0.8, 0.2, 0.0 , 0)); //verminder melodisch gebruik 
//	    put(13, new Interval(13, 0.0, 0.0, 0.0 , 0)); // vermijd melodisch - harmonisch
	    //Roughness - worst interval is highest value!
	    
	}};
	
	public static final Interval UNISONO = intervalMap.get(0);
	public static final Interval KLEINE_SECONDE = intervalMap.get(1);
	public static final Interval GROTE_SECONDE = intervalMap.get(2);
	public static final Interval KLEINE_TERTS = intervalMap.get(3);
	public static final Interval GROTE_TERTS = intervalMap.get(4);
	public static final Interval KWART = intervalMap.get(5);
	public static final Interval TRITONE = intervalMap.get(6);
	public static final Interval KWINT = intervalMap.get(7);
	public static final Interval KLEINE_SIXT = intervalMap.get(8);
	public static final Interval GROTE_SIXT = intervalMap.get(9);
	public static final Interval KLEIN_SEPTIEM = intervalMap.get(10);
	public static final Interval GROOT_SEPTIEM = intervalMap.get(11);
	public static final Interval OCTAAF = intervalMap.get(12);
//	public static final Interval GROTER_DAN_OCTAAF = intervalMap.get(13);
	
	public void setInterval(int interval) {
		this.interval = interval;
	}
	public void setMelodicValue(double melodicValue) {
		this.melodicValue = melodicValue;
	}
	public void setHarmonicValue(double harmonicValue) {
		this.harmonicValue = harmonicValue;
	}
	public void setTonalValue(double tonalValue) {
		this.tonalValue = tonalValue;
	}
	public void setSeries2(int series2) {
		this.series2 = series2;
	}

	private int interval;
	private double melodicValue;
	private double harmonicValue;
	private double tonalValue;
	private int series2;
	
	public int getInterval() {
		return interval;
	}
	
	public String getIntervalName() {
		if (interval == 10) {
			return "A";
		}
		if (interval == 11) {
			return "B";
		}
		return String.valueOf(interval);
	}
	
	public double getMelodicValue() {
		return melodicValue;
	}
	
	public double getHarmonicValue() {
		return harmonicValue;
	}
	
	public double getTonalValue() {
		return tonalValue;
	}
	public int getSeries2() {
		return series2;
	}
	
	public Interval(int interval, double melodicValue, double harmonicValue, double tonalValue, int series2){
		this.interval = interval;
		this.melodicValue = melodicValue;
		this.harmonicValue = harmonicValue;
		this.tonalValue = tonalValue;
		this.series2 = series2;
	}
	
	public static Interval getEnumInterval(int difference) {
		return intervalMap.get(Math.abs(difference % 12));
	}

	public static boolean isDissonantInterval(int difference){
		Integer interval = Math.abs(difference % 12);
		switch (interval){
			case 1:
			case 2:
			case 10:
			case 11:
			case 6:
				return true;
		}
		return false;
	}
	
}
