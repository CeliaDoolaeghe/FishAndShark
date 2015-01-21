package iagl.idl.simulation.mas;

import iagl.idl.simulation.mas.agent.StoppableAgent;
import iagl.idl.simulation.mas.environment.Environment;

import java.util.List;

public class StoppableMAS<T extends StoppableAgent> extends MAS<T> {

	public StoppableMAS(Environment<T> environment, int delay) {
		super(environment, delay);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void runOnce() {
		super.runOnce();
		List<T> agents = environment.getAllAgents();
		boolean canStop = true;
		int i = 0;
		int size = agents.size();
		T agent;
		while (i < size && canStop) {
			agent = agents.get(i);
			if(!agent.canStopSimulation()) {
				canStop = false;
			}
		}
		terminated = canStop;
	}
}
