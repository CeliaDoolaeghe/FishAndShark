package iagl.idl.simulation.mas.agent;

import iagl.idl.simulation.mas.StoppableMAS;
import iagl.idl.simulation.mas.agent.pacman.Obstacle;
import iagl.idl.simulation.mas.agent.pacman.PacManAgent;
import iagl.idl.simulation.mas.agent.pacman.Predator;
import iagl.idl.simulation.mas.agent.pacman.Prey;
import iagl.idl.simulation.mas.environment.Coordinate;
import iagl.idl.simulation.mas.environment.Environment;

import java.util.LinkedList;
import java.util.List;

/**
 * A specific Multi-Agents System which initializes PacMan Agents
 * see init method
 *
 */
public class PacManMAS extends StoppableMAS<PacManAgent> {
    public PacManMAS(Environment<PacManAgent> environment, int delay) {
        super(environment, delay);
    }

    /**
     * Init the environment by adding Preys, Predators and Obstacles
     * @param nbPreys the number of preys to add into environment
     * @param nbPredators the number of predators to add into environment
     * @param percentageObstacles the number of obstacles to add into environment
     */
    public void init(int nbPreys, int nbPredators, int percentageObstacles) {
        List<PacManAgent> agents = new LinkedList<>();
        for (int i = 0; i < nbPreys; i++) {
            agents.add(new Prey(environment));
        }
        for (int i = 0; i < nbPredators; i++) {
            agents.add(new Predator(environment));
        }

        int nbObstacles = (int) Math.pow(environment.getBoard().size(), 2) * percentageObstacles / 100;
        Obstacle obstacle;
        Coordinate obstacleCoordinate;

        for(int i = 0; i < nbObstacles; i++) {
            obstacle = new Obstacle(environment);
            obstacleCoordinate = environment.findFreeSpace();
            environment.addAgent(obstacle, obstacleCoordinate);
        }
        environment.init(agents);
    }
}
