package iagl.idl.simulation.mas.environment;

import iagl.idl.simulation.mas.agent.Agent;

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
public class Environment<T extends Agent> extends Observable {
    /**
     * The 2D representation of the Sea
     * Contains all agents
     */
    private List<List<T>> board;

    /**
     * All agents into the sea
     * Map each Agent to its current coordinate.
     */
    private Map<T, Coordinate> agents;

    private List<T> agentsList;

    /**
     * Keep a reference to Agents who died
     */
    private Set<T> theDead;

    /**
     * Create an Environment by adding some fish & sharks.
     *
     * @param size the size of the sea
     */
    public Environment(int size) {
        agents = new HashMap<>();
        agentsList = new LinkedList<>();
        theDead = new HashSet<>();
        board = new ArrayList<>();
        List<T> abscissa;
        for(int y = 0; y < size; y++) {
            abscissa = new ArrayList<>();
            for(int x = 0; x < size; x++) {
                abscissa.add(null);
            }
            board.add(abscissa);
        }
    }

    /**
     * Add fish and sharks to Environment
     *
     * @param agents agents to add into this Environment at the beginning of the simulation
     */
    public void init(List<T> agents) {
        Coordinate coordinate;
        for(T agent: agents) {
            coordinate = findFreeSpace();
            addAgent(agent, coordinate);
        }

        setChanged();
    }

    /**
     * Find a free square near to <code>agent</code>
     * If there is more than one free square, return a square randomly.
     *
     * @param agent the agent which search a free space
     * @return a free Coordinate if at least one square near to <code>agent</code> is free, null otherwise
     */
    public Coordinate findFreeSpace(T agent) {
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
        int size = board.size();
        if(agentsList.size() == size * size) {
            return null;
        }

        // Find a free square by randomly checking squares
        Coordinate coordinate;
        do {
            int x = (int) (Math.random() * size);
            int y = (int) (Math.random() * size);
            coordinate = new Coordinate(x, y);
        } while(!isFree(coordinate));

        return coordinate;
    }

    private T getBoardSquare(Coordinate coordinate) {
        return board.get(coordinate.getY()).get(coordinate.getX());
    }

    /**
     * return the coordinate of <code>agent</code>
     *
     * @param agent the agent which is placed at the returned coordinate
     * @return the coordinate of <code>agent</code>
     */
    public Coordinate getCoordinateOf(T agent) {
        return agents.get(agent);
    }

    /**
     * Find all coordinates near to <code>coordinate</code>
     *
     * @param coordinate the coordinate which ask for its coordinates
     * @return a list of neighbour coordinates.
     */
    private List<Coordinate> neighborsCoordinates(Coordinate coordinate) {
        int size = board.size();
        List<Coordinate> possibilities = new LinkedList<>();
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
    public List<T> getAllAgents() {
        List<T> agentsCopy = new LinkedList<>();
        for (T a : agentsList) {
            agentsCopy.add(a);
        }
        Collections.shuffle(agentsCopy);
        return agentsCopy;
    }

    /**
     * @param agent to agent to check if is dead or alive
     * @return true if <code>agent</code> is already dead
     */
    public boolean isDead(T agent) {
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
    public Map<Coordinate, T> getNeighbors(T agent) {
        Coordinate coordinate = agents.get(agent);

        // Shuffled List of neighbors' coordinates
        List<Coordinate> neighborsCoordinate = neighborsCoordinates(coordinate);
        Collections.shuffle(neighborsCoordinate);

        // Find the neighbors into board
        Map<Coordinate, T> neighbors = new HashMap<>();
        T neighbor;
        for (Coordinate neighborCoordinate : neighborsCoordinate) {
            neighbor = getBoardSquare(neighborCoordinate);
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
        return getBoardSquare(coordinate) == null;
    }

    /**
     * Add an Agent to this Environment
     *
     * @param agent      the agent to add
     * @param coordinate the coordinate where to add the agent
     */
    public void addAgent(T agent, Coordinate coordinate) {
        agents.put(agent, coordinate);
        agentsList.add(agent);
        setBoardSquare(coordinate, agent);
        setChanged();
    }

    /**
     * Remove an agent from this environment
     *
     * @param agent the agent to remove
     */
    public void removeAgent(T agent) {
        Coordinate agentCoordinate = agents.get(agent);
        setBoardSquare(agentCoordinate, null);
        agentsList.remove(agent);
        agents.remove(agent);
        theDead.add(agent);
        setChanged();
    }

    private void setBoardSquare(Coordinate agentCoordinate, T agent) {
        try {
            board.get(agentCoordinate.getY()).set(agentCoordinate.getX(), agent);
        } catch(NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Move the <code>agent</code> to <code>coordinate</code>
     *
     * @param agent      the agent to move
     * @param coordinate the new position of <code>agent</code>
     */
    public void move(T agent, Coordinate coordinate) {
        if (agents.containsKey(agent)) {
            Coordinate previousCoordinate = agents.get(agent);
            agents.put(agent, coordinate);
            setBoardSquare(previousCoordinate, null);
            setBoardSquare(coordinate, agent);
            setChanged();
        }
    }

    public List<List<T>> getBoard() {
        return board;
    }
}