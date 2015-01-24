package iagl.idl.simulation.mas.agent.pacman;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import iagl.idl.simulation.mas.environment.Coordinate;
import iagl.idl.simulation.mas.environment.Environment;

/**
 * An Eatable Agent try to survive against <code>Predator</code>s
 * Compute dijkstra and try to escape <code>Predator</code>s by moving into the neighbor's square which have the maximum value
 */
public class Prey extends PacManAgent {

	public Prey(Environment<PacManAgent> environment) {
		super(environment);
	}

	@Override
	public void doIt() {
        List<List<Integer>> dijkstra = new ArrayList<>();
        List<PacManAgent> agents = environment.getAllAgents();
        Coordinate nextMove = null;
        int nextMoveNumber = Integer.MIN_VALUE;
        List<Coordinate> neighborsCoordinates = environment.neighborsCoordinates(environment.getCoordinateOf(this));
        for(PacManAgent agent : agents) {
            if(agent.canEat()) {
            	initializeDijkstra(dijkstra);
                computeDijkstra(dijkstra, agent);
                for(Coordinate c : neighborsCoordinates) {
                    if(dijkstra.get(c.getY()).get(c.getX()) != 0 && dijkstra.get(c.getY()).get(c.getX()) > nextMoveNumber) {
                        nextMoveNumber = dijkstra.get(c.getY()).get(c.getX());
                        nextMove = c;
                    }
                }
            }
        }
        if(nextMove != null) {
            environment.move(this, nextMove);
        }
        System.out.println(nextMoveNumber);
	}

	@Override
	public Color getColor() {
		return Color.GREEN;
	}

	@Override
	public boolean canStopSimulation() {
		return false;
	}

	@Override
	public boolean isEatable() {
		return true;
	}

	@Override
	public boolean canEat() {
		return false;
	}

}
