package iagl.idl.simulation.mas.agent;

/**
 * An agent which is able to inform MAS that it have finished to play into environment.
 */
public interface StoppableAgent extends Agent {
    /**
     * Check if this agent has finished
     * @return true iff this agent has finished
     */
	public boolean canStopSimulation();
}
