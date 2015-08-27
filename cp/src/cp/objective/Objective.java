package cp.objective;

import org.springframework.beans.factory.annotation.Autowired;

import cp.generator.MusicProperties;
import cp.model.Motive;


public abstract class Objective {
	
	@Autowired
	protected MusicProperties musicProperties;

	public abstract double evaluate(Motive motive);

}
