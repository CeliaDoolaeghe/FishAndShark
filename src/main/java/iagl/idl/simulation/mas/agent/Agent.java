package iagl.idl.simulation.mas.agent;

import java.awt.Color;

/**
 * Agent element of the @link MAS
 * <p>
 * The abstract method <code>doIt</code> lets this agent compute its next behavior, depending on the Environment<br/>
 * In particular, "move" and "giveBirth" behaviors are implemented.
 * </p>
 *
 * @author Célia Cacciatore, Jonathan Geoffroy
 */
public interface Agent {
    /**
     * let the Agent do its job
     */
    public abstract void doIt();

    /**
     * @return the colors of this Agent
     */
    public abstract Color getColor();
}
