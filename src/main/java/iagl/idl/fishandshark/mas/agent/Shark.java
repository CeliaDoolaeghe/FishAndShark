package iagl.idl.fishandshark.mas.agent;

import iagl.idl.fishandshark.mas.environment.Coordinate;
import iagl.idl.fishandshark.mas.environment.Environment;

import java.awt.*;
import java.util.Map;

/**
 * Agent which represents a Shark
 * <p/>
 * Try to eat an eatable neighbor Agent, i.e. remove the Agent from Environment
 * then try to give birth  if its <code>gestation</code> as bigger than the <code>GESTATION_DURATION</code>,
 * before moving if it's possible.<br/>
 * If a Shark doesn't eat any eatable Agent for <code>STARVATION_DURATION</code>, it starves by removing itself from the Environment.
 * <p/>
 *
 * @author Célia Cacciatore, Jonathan Geoffroy
 */
public class Shark extends Agent {

    /**
     * The maximum starvation duration before dieing
     */
    private static int STARVATION_DURATION = 3;

    /**
     * The maximum gestation duration before giving birth to another Shark
     */
    private static int GESTATION_DURATION = 8;

    /**
     * Current starvation<br/>
     */
    private int starvation;

    public Shark(Environment environment) {
        super(environment);
    }

    public static int getGestationDuration() {
        return GESTATION_DURATION;
    }

    @Override
    public void doIt() {
        if (starvation >= STARVATION_DURATION) {
            environment.remove(this);
        } else {
            tryToEat();
            tryToGiveBirth();
            tryToMove();
        }
    }

    /**
     * Try to eat a neighbour if it's possible<br/>
     * The neighbour is chosen randomly. If this Shark can eat, its starvation down to 0, or increases otherwise.
     */
    private void tryToEat() {
        Map<Coordinate, Agent> neighbors = environment.getNeighbors(this);
        Agent neighbor;
        for (Coordinate neighborCoordinate : neighbors.keySet()) {
            neighbor = neighbors.get(neighborCoordinate);
            if (neighbor.isEatable()) {
                eat(neighbor);
                starvation = 0;
                return;
            }
        }
        starvation++;
    }

    /**
     * Eat an agent by removing it from the environment
     *
     * @param agent the eaten agent
     */
    private void eat(Agent agent) {
        agent.removeFromEnvironment();
    }

    @Override
    public void addChild(Coordinate childCoordinate) {
        environment.addShark(childCoordinate);
    }

    @Override
    public boolean canGiveBirth() {
        return gestation == GESTATION_DURATION;
    }

    @Override
    public boolean isEatable() {
        return false;
    }

    @Override
    public void removeFromEnvironment() {
        environment.remove(this);
    }

    @Override
    public Color getColor() {
        return Color.RED;
    }

    public static void setGestationDuration(int gestationDuration) {
        Shark.GESTATION_DURATION = gestationDuration;
    }

    public static void setStarvationDuration(int starvationDuration) {
        Shark.STARVATION_DURATION = starvationDuration;
    }

}