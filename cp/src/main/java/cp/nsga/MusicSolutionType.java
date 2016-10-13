package cp.nsga;

import cp.model.Motive;
import jmetal.core.Problem;
import jmetal.core.SolutionType;
import jmetal.core.Variable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MusicSolutionType extends SolutionType {

	private Motive motive;
	
	@Autowired
	public MusicSolutionType(Problem problem) {
		super(problem);
		problem.setSolutionType(this);
	}
	
	public void setMotive(Motive motive) {
		this.motive = motive;
	}

	@Override
	public Variable[] createVariables() throws ClassNotFoundException {
		Variable[] variables = new Variable[problem_.getNumberOfVariables()];
		variables[0] = new MusicVariable(motive);
		return variables ;
	}	

}
