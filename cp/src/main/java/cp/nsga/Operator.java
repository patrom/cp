package cp.nsga;

/**
 * Created by prombouts on 19/05/2017.
 */
import java.io.Serializable;

public interface Operator<Source, Result> extends Serializable {

    Result execute(Source source) ;
}
