package iagl.idl.fishandshark.mas.environment;

import iagl.idl.fishandshark.mas.agent.Agent;
import iagl.idl.fishandshark.mas.agent.Fish;
import iagl.idl.fishandshark.mas.agent.Shark;

import java.util.*;

public class Environment extends  Observable {
    public final static int BOARD_SIZE = 40;
    private final static int NB_SHARKS_INIT = 50;
    private final static int NB_FISHES_INIT = 250;

	private int size;
    private Agent[][] board;
    private Map<Agent, Coordinate> agents;
    private List<Agent> agentsList;
    private int fishes;
    private int sharks;
    private Set theDead;

    public Environment() {
        this.size = BOARD_SIZE;
        agents = new HashMap<Agent, Coordinate>();
        agentsList = new LinkedList<Agent>();
        theDead = new HashSet();
        board = new Agent[size][size];
        init();
    }

    private void init() {
        // Add fishes
        int nbFishes = 0;
        Coordinate coordinate;
        while(nbFishes < NB_FISHES_INIT) {
            int x = (int) (Math.random() * size);
            int y = (int) (Math.random() * size);
            if(board[y][x] == null) {
                coordinate = new Coordinate(x, y);
                addFish(coordinate);
                nbFishes++;
            }
        }

        // add sharks
        int nbSharks = 0;
        while(nbSharks < NB_SHARKS_INIT) {
            int x = (int) (Math.random() * size);
            int y = (int) (Math.random() * size);
            if(board[y][x] == null) {
                coordinate = new Coordinate(x, y);
                addShark(coordinate);
                nbSharks++;
            }
        }
    }

    public Coordinate findFreeSpace(Agent agent) {
        Coordinate coordinate = agents.get(agent);
        assert(coordinate != null);
		List<Coordinate> possibilities = neighborsCoordinates(coordinate);
		Collections.shuffle(possibilities);
		
		for(Coordinate c : possibilities) {
			if(isFree(c)) {
				return c;
			}
		}
		return null;
	}

    public Coordinate findFreeSpace() {
		Coordinate currentCoordinate;
		for(int y = 0; y < size; y++) {
			for(int x = 0; x < size; x++) {
				currentCoordinate = new Coordinate(x, y);
				if(isFree(currentCoordinate)) {
					return currentCoordinate;
				}
			}
		}
		return null;
	}

    public Coordinate getCoordinateOf(Agent agent) {
        return agents.get(agent);
    }

    private List<Coordinate> neighborsCoordinates(Coordinate coordinate) {
        List<Coordinate> possibilities = new LinkedList<Coordinate>();
        Coordinate currentCoordinate;
        for(int yCoord = coordinate.getY(), y =  Math.max(0, yCoord - 1); y <= yCoord + 1 && y < size; y++) {
            for(int xCoord = coordinate.getX(), x = Math.max(0, xCoord - 1); x <= xCoord + 1 && x < size; x++) {
                currentCoordinate = new Coordinate(x, y);
                if(currentCoordinate != coordinate) {
                    possibilities.add(currentCoordinate);
                }
            }
        }
        return  possibilities;
    }

    public List<Agent> getAllAgents() {
        List<Agent> agentsCopy = new LinkedList<Agent>();
        for(Agent a : agentsList) {
            agentsCopy.add(a);
        }
        Collections.shuffle(agentsCopy);
        return agentsCopy;
    }

    public boolean isDead(Agent agent) {
        return theDead.contains(agent);
    }

    public void clearDead() {
        theDead.clear();
    }

    public Map<Coordinate, Agent> getNeighbors(Agent agent) {
        Coordinate coordinate = agents.get(agent);

        // Shuffled List of neighbors' coordinates
        List<Coordinate> neighborsCoordinate = neighborsCoordinates(coordinate);
        Collections.shuffle(neighborsCoordinate);

        // Find the neighbors into board
        Map<Coordinate, Agent> neighbors = new HashMap<Coordinate, Agent>();
        Agent neighbor;
        for(Coordinate neighborCoordinate : neighborsCoordinate) {
            neighbor = board[neighborCoordinate.getY()][neighborCoordinate.getX()];
            if(neighbor != null) {
                neighbors.put(neighborCoordinate, neighbor);
            }
        }

        return neighbors;
    }

	private boolean isFree(Coordinate c) {
		return board[c.getY()][c.getX()] == null;
	}

	public void addFish(Coordinate fishCoordinate) {
        Fish fish = new Fish(this);
		addAgent(fish, fishCoordinate);
        fishes++;
	}

    public void addShark(Coordinate sharkCoordinate) {
		Shark shark = new Shark(this);
        addAgent(shark, sharkCoordinate);
        sharks++;
	}

    private void addAgent(Agent agent, Coordinate coordinate) {
        agents.put(agent, coordinate);
        agentsList.add(agent);
        board[coordinate.getY()][coordinate.getX()] = agent;
    }

    public void remove(Shark shark) {
        removeAgent(shark);
        sharks--;
    }

    public void remove(Fish fish) {
        removeAgent(fish);
        fishes--;
    }

    private void removeAgent(Agent agent) {
        Coordinate agentCoordinate = agents.get(agent);
        agents.remove(agent);
        agentsList.remove(agent);
        board[agentCoordinate.getY()][agentCoordinate.getX()] = null;
        theDead.add(agent);
    }

    public void move(Agent agent, Coordinate coordinate) {
        if(agents.containsKey(agent)) {
            Coordinate previousCoordinate = agents.get(agent);
            agents.put(agent, coordinate);
            board[previousCoordinate.getY()][previousCoordinate.getX()] = null;
            board[coordinate.getY()][coordinate.getX()] = agent;
        }
    }

    public int getFishes() {
        return fishes;
    }

    public int getSharks() {
        return sharks;
    }

    public Agent[][] getBoard() {
        return board;
    }

    @Override
    public void notifyObservers() {
        setChanged();
        super.notifyObservers();
    }
}