package cp.model;

import jmetal.core.Solution;

/**
 * Created by prombouts on 24/10/2016.
 */
@FunctionalInterface
public interface Relation {

    Solution execute(Solution solution);
}
