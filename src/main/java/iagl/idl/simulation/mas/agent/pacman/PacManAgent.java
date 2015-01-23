package iagl.idl.simulation.mas.agent.pacman;

import iagl.idl.simulation.mas.agent.StoppableAgent;
import iagl.idl.simulation.mas.environment.Coordinate;
import iagl.idl.simulation.mas.environment.Environment;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class PacManAgent implements StoppableAgent {
	protected Environment<PacManAgent> environment;
	
	public PacManAgent(Environment<PacManAgent> environment) {
		super();
		this.environment = environment;
	}

	public abstract boolean isEatable();

    protected List<List<Integer>> computeDijkstra(List<List<Integer>> dijkstra, PacManAgent agent) {
        List<List<PacManAgent>> board = environment.getBoard();
        for(int i = 0, size = board.size(); i < size; i++) {
            List<Integer> l = new ArrayList<Integer>();
            for(int j = 0; j < size; j++) {
                l.add(0);
            }
            dijkstra.add(l);
        }

        LinkedList<Coordinate> openSquares = new LinkedList<>();
        openSquares.add(environment.getCoordinateOf(agent));
        computeDijkstra(dijkstra, openSquares);
        return dijkstra;
    }

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
