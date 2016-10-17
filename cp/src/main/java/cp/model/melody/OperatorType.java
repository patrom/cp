package cp.model.melody;


public class OperatorType {
	
	private int steps;
	private final Operator operator;
	private int degree;
	private double factor;
	
	public OperatorType(Operator operator) {
		this.operator = operator;
	}

	public int getSteps() {
		return steps;
	}

	public double getFactor() {
		return factor;
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

	public void setFactor(double factor) {
		this.factor = factor;
	}

	public void setFunctionalDegreeCenter(int degree) {
		this.degree = degree;
	}
}
