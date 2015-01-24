package iagl.idl.simulation.mas.agent.pacman;

import iagl.idl.simulation.mas.environment.Coordinate;
import iagl.idl.simulation.mas.environment.Environment;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * An Agent whose goal is to catch <code>Prey</code>s in the environment.<br/>
 * Compute dijkstra and try to get closer to a <code>Prey</code> by moving into the neighbor square which has the minimum value.
 */
public class Predator extends PacManAgent {

	public Predator(Environment<PacManAgent> environment) {
		super(environment);
	}

	@Override
	public boolean canStopSimulation() {
		return true;
	}

	@Override
	public void doIt() {
		List<List<Integer>> dijkstra = new ArrayList<>();
		List<PacManAgent> agents = environment.getAllAgents();
		Coordinate nextMove = null;
		int nextMoveNumber = Integer.MAX_VALUE;
		List<Coordinate> neighborsCoordinates = environment.neighborsCoordinates(environment.getCoordinateOf(this));

        // move closer to the closest eatable Agent by computing Dijkstra for each of them
		for(PacManAgent agent : agents) {
			if(agent.isEatable()) {
				initializeDijkstra(dijkstra);
				computeDijkstra(dijkstra, agent);
				for(Coordinate c : neighborsCoordinates) {
					if(dijkstra.get(c.getY()).get(c.getX()) != 0 && dijkstra.get(c.getY()).get(c.getX()) < nextMoveNumber) {
						nextMoveNumber = dijkstra.get(c.getY()).get(c.getX());
						nextMove = c;
					}
				}
			}
		}
		if(nextMove != null) {
            // Move to square
			environment.move(this, nextMove);

            // Eat all eatable neighbors
            for(PacManAgent neighbor : environment.getNeighbors(this).values()) {
                if(neighbor.isEatable()) {
                    environment.removeAgent(neighbor);
                }
            }
		}
	}

	@Override
	public Color getColor() {
		return Color.YELLOW;
	}

	@Override
	public boolean isEatable() {
		return false;
	}

	@Override
	public boolean canEat() {
		return true;
	}
}
