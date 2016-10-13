package cp.model.melody;


public class OperatorType {
	
	private int steps;
	private Operator operator;
	private int degree;
	
	public OperatorType(Operator operator) {
		this.operator = operator;
	}

	public int getSteps() {
		return steps;
	}

	public Operator getOperator() {
		return operator;
	}
	
	public int getFunctionalDegreeCenter() {
		return degree;
	}
	
	public void setSteps(int steps) {
		this.steps = steps;
	}
	
	public void setFunctionalDegreeCenter(int degree) {
		this.degree = degree;
	}
}
