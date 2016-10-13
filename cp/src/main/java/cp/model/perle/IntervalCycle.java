package cp.model.perle;

public enum IntervalCycle {

	P_IC1(new int[]{0,1,2,3,4,5,6,7,8,9,10,11}, 1, "P"),
	P_IC2_0(new int[]{0,2,4,6,8,10}, 2, "P"),
	P_IC2_1(new int[]{1,3,5,7,9,11}, 2, "P"),
	P_IC3_0(new int[]{0,3,6,9}, 3, "P"),
	P_IC3_1(new int[]{1,4,7,10}, 3, "P"),
	P_IC3_2(new int[]{2,5,8,11}, 3, "P"),
	P_IC4_0(new int[]{0,4,8}, 4, "P"),
	P_IC4_1(new int[]{1,5,9}, 4, "P"),
	P_IC4_2(new int[]{2,6,10}, 4, "P"),
	P_IC4_3(new int[]{3,7,11}, 4, "P"),
	P_IC5(new int[]{0,5,10,3,8,1,6,11,4,9,2,7}, 5, "P"),
	P_IC6(new int[]{0,6}, 6, "P"),
	P_IC7(new int[]{0,7,2,9,4,11,6,1,8,3,10,5}, 7, "P"),
	
	I_IC1(new int[]{0,11,10,9,8,7,6,5,4,3,2,1}, 11, "I"),
	I_IC2(new int[]{0,10,8,6,4,2}, 10, "I"),
	I_IC3(new int[]{0,9,6,3}, 9, "I"),
	I_IC4(new int[]{0,8,4}, 8, "I"),
	I_IC5(new int[]{0,7,2,9,4,11,6,1,8,3,10,5}, 5, "I"),
	I_IC6(new int[]{0,6}, 6, "I"),
	I_IC7(new int[]{0,5,10,3,8,1,6,11,4,9,2,7}, 7, "I");
	private int[] intervalCycle;
	private int interval;
	private String type;
	
	private IntervalCycle(int[] intervalCycle, int interval, String type) {
		this.intervalCycle = intervalCycle;
		this.interval = interval;
		this.type = type;
	}

	public int[] getIntervalCycle() {
		return intervalCycle;
	}

	public int getInterval() {
		return interval;
	}

	public String getType() {
		return type;
	}
	
	public IntervalCycle getInversePCycle(){
		switch (this) {
			case P_IC1:
					return I_IC1;
			case P_IC2_0:
			case P_IC2_1:
				return I_IC2;
			case P_IC3_0:
			case P_IC3_1:
			case P_IC3_2:
				return I_IC3;
			case P_IC4_0:
			case P_IC4_1:
			case P_IC4_2:
			case P_IC4_3:
				return I_IC4;
			case P_IC5:
				return I_IC5;
			case P_IC6:
				return I_IC6;
			case P_IC7:
				return I_IC7;
		}
		throw new IllegalArgumentException("No pcycle");
	}

}
