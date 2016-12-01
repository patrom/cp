package cp.nsga;


import cp.model.Motive;
import jmetal.core.Variable;
import jmetal.util.JMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MusicVariable extends Variable {

	private static final Logger LOGGER = LoggerFactory.getLogger(MusicVariable.class.getName());
	private final Motive motive;
	
	public MusicVariable(MusicVariable musicVariable) throws JMException {
		this.motive =  musicVariable.getMotive().clone();
	}

	public MusicVariable(Motive motive) {
		this.motive = motive;
	}
	
	public Motive getMotive() {
		return motive;
	}

	@Override
	public Variable deepCopy() {
		try {
	      return new MusicVariable(this);
	    } catch (JMException e) {
	    	LOGGER.error("MusicVariable.deepCopy.execute: JMException");
	      return null ;
	    }
	}
	
}

