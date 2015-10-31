package cp.model.melody;


public class OperatorType {
	
	private int steps;
	private Operator operator;
	
	public OperatorType(int steps, Operator operator) {
		this.steps = steps;
		this.operator = operator;
	}

	public int getSteps() {
		return steps;
	}

	public Operator getOperator() {
		return operator;
	}
}
