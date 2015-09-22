package cp.nsga.operator.mutation;

import java.util.HashMap;

import jmetal.operators.mutation.Mutation;
import jmetal.util.JMException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cp.generator.MusicProperties;

public abstract class AbstractMutation extends Mutation{
	
	protected static Logger LOGGER = LoggerFactory.getLogger(AbstractMutation.class.getName());

	@Autowired
	protected MusicProperties musicProperties;
	
	public AbstractMutation(HashMap<String, Object> parameters) {
		super(parameters);
	}

	@Override
	public abstract Object execute(Object arg0) throws JMException;

}
