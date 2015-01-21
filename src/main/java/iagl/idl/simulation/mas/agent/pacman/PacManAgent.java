package iagl.idl.simulation.mas.agent.pacman;

import iagl.idl.simulation.mas.agent.StoppableAgent;
import iagl.idl.simulation.mas.environment.Environment;

public abstract class PacManAgent implements StoppableAgent {
	protected Environment<PacManAgent> environment;
	
	public PacManAgent(Environment<PacManAgent> environment) {
		super();
		this.environment = environment;
	}

	public abstract boolean isEatable();
}
