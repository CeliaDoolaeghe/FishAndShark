package iagl.idl.fishandshark.mas.environment;

import iagl.idl.fishandshark.mas.agent.Agent;
import iagl.idl.fishandshark.mas.agent.Fish;
import iagl.idl.fishandshark.mas.agent.Shark;

import java.util.*;

/**
 * Contain the entire map model:
 * <ul>
 * <li>List of Agents</li>
 * <li>number of Fish</li>
 * <li>number of Sharks</li>
 * <li>2D board which represents the sea containing Agents</li>
 * </ul><br/>
 * <p/>
 * Environment can provide some useful information to Agents such as their position or neighbors.
 */
public class Environment extends Observable {
    /**
     * The size of the board
     */
    private int size;
    /**
     * The 2D representation of the Sea
     * Contains all agents
     */
    private Agent[][] board;

    /**
     * All agents into the sea
     * Map each Agent to its current coordinate.
     */
    private Map<Agent, Coordinate> agents;

    private List<Agent> agentsList;

    /**
     * The number of fish into the sea
     */
    private int fish;

    /**
     * The number of sharks into the sea
     */
    private int sharks;

    /**
     * Keep a reference to Agents who died
     */
    private Set<Agent> theDead;

    /**
     * Create an Environment by adding some fish & sharks.
     *
     * @param size       the size of the sea
     * @param initFish   the number of fish to add to Environment at the beginning of the simulation
     * @param initSharks the number of sharks to add to Environment at the beginning of the simulation
     */
    public Environment(int size, int initFish, int initSharks) {
        this.size = size;
        agents = new HashMap<Agent, Coordinate>();
        agentsList = new LinkedList<Agent>();
        theDead = new HashSet<Agent>();
        board = new Agent[size][size];
        init(initFish, initSharks);
    }

    /**
     * Add fish and sharks to Environment
     *
     * @param initFish   the number of fish to add to Environment at the beginning of the simulation
     * @param initSharks the number of sharks to add to Environment at the beginning of the simulation
     */
    private void init(int initFish, int initSharks) {
        // Add fish
        int nbFish = 0;
        Coordinate coordinate;
        while (nbFish < initFish) {
            coordinate = findFreeSpace();
            addFish(coordinate);
            nbFish++;
        }

        // add sharks
        int nbSharks = 0;
        while (nbSharks < initSharks) {
            coordinate = findFreeSpace();
            addShark(coordinate);
            nbSharks++;
        }
    }

    /**
     * Find a free square near to <code>agent</code>
     * If there is more than one free square, return a square randomly.
     *
     * @param agent the agent which search a free space
     * @return a free Coordinate if at least one square near to <code>agent</code> is free, null otherwise
     */
    public Coordinate findFreeSpace(Agent agent) {
        Coordinate coordinate = agents.get(agent);
        assert (coordinate != null);
        List<Coordinate> possibilities = neighborsCoordinates(coordinate);
        Collections.shuffle(possibilities);

        for (Coordinate c : possibilities) {
            if (isFree(c)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Find a free space into entire sea
     *
     * @return a free square if the sea isn't full, or null otherwise
     */
    public Coordinate findFreeSpace() {
        // If there is an agent for each square: return null
        if(fish + sharks == size * size) {
            return null;
        }
        // Find a free square by randomly checking squares
        Coordinate coordinate = null;
        while(coordinate == null) {
            int x = (int) (Math.random() * size);
            int y = (int) (Math.random() * size);
            if (board[y][x] == null) {
                coordinate = new Coordinate(x, y);
            }
        }
        return coordinate;
    }

    /**
     * return the coordinate of <code>agent</code>
     *
     * @param agent the agent which is placed at the returned coordinate
     * @return the coordinate of <code>agent</code>
     */
    public Coordinate getCoordinateOf(Agent agent) {
        return agents.get(agent);
    }

    /**
     * Find all coordinates near to <code>coordinate</code>
     *
     * @param coordinate the coordinate which ask for its coordinates
     * @return a list of neighbour coordinates.
     */
    private List<Coordinate> neighborsCoordinates(Coordinate coordinate) {
        List<Coordinate> possibilities = new LinkedList<Coordinate>();
        Coordinate currentCoordinate;
        for (int yCoord = coordinate.getY(), y = Math.max(0, yCoord - 1); y <= yCoord + 1 && y < size; y++) {
            for (int xCoord = coordinate.getX(), x = Math.max(0, xCoord - 1); x <= xCoord + 1 && x < size; x++) {
                currentCoordinate = new Coordinate(x, y);
                if (currentCoordinate != coordinate) {
                    possibilities.add(currentCoordinate);
                }
            }
        }
        return possibilities;
    }

    /**
     * return a cloned list of agents
     * agents themself are not copied.
     *
     * @return a new list containing all agents of this Environment
     */
    public List<Agent> getAllAgents() {
        List<Agent> agentsCopy = new LinkedList<Agent>();
        for (Agent a : agentsList) {
            agentsCopy.add(a);
        }
        Collections.shuffle(agentsCopy);
        return agentsCopy;
    }

    /**
     * @param agent to agent to check if is dead or alive
     * @return true if <code>agent</code> is already dead
     */
    public boolean isDead(Agent agent) {
        return theDead.contains(agent);
    }

    /**
     * Clear the list of dead agents
     * useful to free memory
     * should be called after each simulation turn
     */
    public void clearDead() {
        theDead.clear();
    }

    /**
     * find each coordinate near to <code>agent</code> which contains an Agent
     *
     * @param agent the agent who ask for its neighbor
     * @return a Map of Coordinate -> Agent of <code>agent</code>'s neighbor
     */
    public Map<Coordinate, Agent> getNeighbors(Agent agent) {
        Coordinate coordinate = agents.get(agent);

        // Shuffled List of neighbors' coordinates
        List<Coordinate> neighborsCoordinate = neighborsCoordinates(coordinate);
        Collections.shuffle(neighborsCoordinate);

        // Find the neighbors into board
        Map<Coordinate, Agent> neighbors = new HashMap<Coordinate, Agent>();
        Agent neighbor;
        for (Coordinate neighborCoordinate : neighborsCoordinate) {
            neighbor = board[neighborCoordinate.getY()][neighborCoordinate.getX()];
            if (neighbor != null) {
                neighbors.put(neighborCoordinate, neighbor);
            }
        }

        return neighbors;
    }

    /**
     * @param coordinate the coordinate to check
     * @return true iff there is no Agent into <code>coordinate</code>
     */
    private boolean isFree(Coordinate coordinate) {
        return board[coordinate.getY()][coordinate.getX()] == null;
    }

    /**
     * Add a fish to this Environment
     *
     * @param fishCoordinate the coordinate of the fish to add
     */
    public void addFish(Coordinate fishCoordinate) {
        Fish fish = new Fish(this);
        addAgent(fish, fishCoordinate);
        this.fish++;
    }

    /**
     * Add a shark to this Environment
     *
     * @param sharkCoordinate the coordinate of the shark to add
     */
    public void addShark(Coordinate sharkCoordinate) {
        Shark shark = new Shark(this);
        addAgent(shark, sharkCoordinate);
        sharks++;
    }

    /**
     * Add an Agent to this Environment
     *
     * @param agent      the agent to add
     * @param coordinate the coordinate where to add the agent
     */
    private void addAgent(Agent agent, Coordinate coordinate) {
        agents.put(agent, coordinate);
        agentsList.add(agent);
        board[coordinate.getY()][coordinate.getX()] = agent;
    }

    /**
     * Remove a shark from this environment
     *
     * @param shark the shark to remove
     */
    public void remove(Shark shark) {
        removeAgent(shark);
        sharks--;
    }

    /**
     * Remove a fish from this environment
     *
     * @param fish the fish to remove
     */
    public void remove(Fish fish) {
        removeAgent(fish);
        this.fish--;
    }

    /**
     * Remove an agent from this environment
     *
     * @param agent the agent to remove
     */
    private void removeAgent(Agent agent) {
        Coordinate agentCoordinate = agents.get(agent);
        agents.remove(agent);
        agentsList.remove(agent);
        board[agentCoordinate.getY()][agentCoordinate.getX()] = null;
        theDead.add(agent);
    }

    /**
     * Move the <code>agent</code> to <code>coordinate</code>
     *
     * @param agent      the agent to move
     * @param coordinate the new position of <code>agent</code>
     */
    public void move(Agent agent, Coordinate coordinate) {
        if (agents.containsKey(agent)) {
            Coordinate previousCoordinate = agents.get(agent);
            agents.put(agent, coordinate);
            board[previousCoordinate.getY()][previousCoordinate.getX()] = null;
            board[coordinate.getY()][coordinate.getX()] = agent;
        }
    }

    public int getFish() {
        return fish;
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

    public boolean somebodyWon() {
        return fish == size * size || sharks == size * size || fish + sharks == 0;
    }
}