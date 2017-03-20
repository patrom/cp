package cp.objective;

import cp.generator.MusicProperties;
import cp.model.Motive;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class Objective {
	
	@Autowired
	protected MusicProperties musicProperties;


	public abstract double evaluate(Motive motive);

}
