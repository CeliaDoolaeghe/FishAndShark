package iagl.idl.simulation.mas.agent.pacman;

import iagl.idl.simulation.mas.agent.FoodChain;
import iagl.idl.simulation.mas.agent.StoppableAgent;
import iagl.idl.simulation.mas.environment.Coordinate;
import iagl.idl.simulation.mas.environment.Environment;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Specific Agent for PacMan simulation.
 * @author Célia Cacciatore, Jonathan Geoffroy
 */
public abstract class PacManAgent implements StoppableAgent, FoodChain {

    /**
     * Environment where this Agent moves
     */
	protected Environment<PacManAgent> environment;

	public PacManAgent(Environment<PacManAgent> environment) {
		super();
		this.environment = environment;
	}

	/**
	 * Initializes an empty dijkstra grid.
	 * @param dijkstra the grid to initialize
	 */
	protected void initializeDijkstra(List<List<Integer>> dijkstra) {
		dijkstra.clear();
		List<List<PacManAgent>> board = environment.getBoard();
        for(int i = 0, size = board.size(); i < size; i++) {
            List<Integer> l = new ArrayList<Integer>();
            for(int j = 0; j < size; j++) {
                l.add(0);
            }
            dijkstra.add(l);
        }
	}

    /**
     * Compute dijkstra minimum path to eat the provided <code>agent</code>
     * @param dijkstra container of the dijkstra computing
     * @param agent the agent to eat
     * @return the computed result
     */
    protected List<List<Integer>> computeDijkstra(List<List<Integer>> dijkstra, PacManAgent agent) {
        LinkedList<Coordinate> openSquares = new LinkedList<>();
        openSquares.add(environment.getCoordinateOf(agent));
        computeDijkstra(dijkstra, openSquares);

        return dijkstra;
    }

    /**
     * Implementation of dijkstra's minimum path computing
     * @param dijkstra the result
     * @param openSquares the squares to check
     */
    private void computeDijkstra(List<List<Integer>> dijkstra, LinkedList<Coordinate> openSquares) {
        int number;
        while(!openSquares.isEmpty()) {
            Coordinate currentCoordinate = openSquares.pop();
            number = dijkstra.get(currentCoordinate.getY()).get(currentCoordinate.getX());
            List<Coordinate> coordinates = environment.neighborsCoordinates(currentCoordinate);
            for(Coordinate coordinate : coordinates) {
                if(dijkstra.get(coordinate.getY()).get(coordinate.getX()) == 0 && environment.getAgentAt(coordinate) == null) {
                    dijkstra.get(coordinate.getY()).set(coordinate.getX(), number + 1);
                    openSquares.add(coordinate);
                }
            }
        }
    }
}
