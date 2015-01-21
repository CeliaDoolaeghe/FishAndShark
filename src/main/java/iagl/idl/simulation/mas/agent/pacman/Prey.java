package iagl.idl.simulation.mas.agent.pacman;

import java.awt.Color;
import java.util.Map;

import iagl.idl.simulation.mas.environment.Coordinate;
import iagl.idl.simulation.mas.environment.Environment;

public class Prey extends PacManAgent {	
	
	public Prey(Environment<PacManAgent> environment) {
		super(environment);
	}

	@Override
	public void doIt() {
		// TODO Auto-generated method stub

	}

	@Override
	public Color getColor() {
		return Color.GREEN;
	}

	@Override
	public boolean canStopSimulation() {
		Map<Coordinate, PacManAgent> neighbors = environment.getNeighbors(this);
		for(PacManAgent agent: neighbors.values()) {
			if(!agent.isEatable()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isEatable() {
		return true;
	}

}
