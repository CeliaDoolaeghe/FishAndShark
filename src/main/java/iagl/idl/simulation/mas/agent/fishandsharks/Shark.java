package iagl.idl.simulation.mas.agent.fishandsharks;

import iagl.idl.simulation.mas.environment.Coordinate;
import iagl.idl.simulation.mas.environment.Environment;

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
public class Shark extends FishAndSharkAgent {

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

    public Shark(Environment<FishAndSharkAgent> environment) {
        super(environment);
    }

    @Override
    public void doIt() {
        super.doIt();
        if (starvation >= STARVATION_DURATION) {
            environment.removeAgent(this);
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
        Map<Coordinate, FishAndSharkAgent> neighbors = environment.getNeighbors(this);
        FishAndSharkAgent neighbor;
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
     * @param fishAndSharkAgent the eaten agent
     */
    private void eat(FishAndSharkAgent fishAndSharkAgent) {
        fishAndSharkAgent.removeFromEnvironment();
    }

    @Override
    public void addChild(Coordinate childCoordinate) {
        Shark shark = new Shark(environment);
        environment.addAgent(shark, childCoordinate);
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
        environment.removeAgent(this);
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