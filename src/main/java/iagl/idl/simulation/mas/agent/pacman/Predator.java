package iagl.idl.simulation.mas.agent.pacman;

import iagl.idl.simulation.mas.environment.Coordinate;
import iagl.idl.simulation.mas.environment.Environment;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
		for(PacManAgent agent : agents) {
			if(agent.isEatable()) {
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
			environment.move(this, nextMove);
		}
	}

	private List<List<Integer>> computeDijkstra(List<List<Integer>> dijkstra, PacManAgent agent) {
		List<List<PacManAgent>> board = environment.getBoard();
		for(int i = 0, size = board.size(); i < size; i++) {
			List<Integer> l = new ArrayList<Integer>();
			for(int j = 0; j < size; j++) {
				l.add(0);
			}
			dijkstra.add(l);
		}
		
		computeDijkstra(dijkstra, environment.getCoordinateOf(agent), new LinkedList<Coordinate>(), 1);
		return dijkstra;
	}

	private void computeDijkstra(List<List<Integer>> dijkstra, Coordinate current, List<Coordinate> openSquares, int number) {
		if(dijkstra.get(current.getY()).get(current.getX()) != 0) {
			return;
		}

		List<Coordinate> newOpenSquares = new LinkedList<>(openSquares); 
		List<Coordinate> coordinates = environment.neighborsCoordinates(current);
		for(Coordinate coordinate : coordinates) {
			if(dijkstra.get(coordinate.getY()).get(coordinate.getX()) == 0 && environment.getAgentAt(coordinate) == null) {
				dijkstra.get(coordinate.getY()).set(coordinate.getX(), new Integer(number));
				newOpenSquares.add(coordinate);
			}
		}

		for(Coordinate c : newOpenSquares) {
			computeDijkstra(dijkstra, c, newOpenSquares, number + 1);
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

}
