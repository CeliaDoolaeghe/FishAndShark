package iagl.idl.simulation.mas.agent.fishandsharks;

import iagl.idl.simulation.mas.agent.Agent;
import iagl.idl.simulation.mas.agent.Eatable;
import iagl.idl.simulation.mas.environment.Coordinate;
import iagl.idl.simulation.mas.environment.Environment;

import java.awt.*;

/**
 * Special Agent for Wa-Tor simulation
 * @author Célia Cacciatore, Jonathan Geoffroy
 */
public abstract class FishAndSharkAgent implements Agent, Eatable {
    /**
     * Environment where the Fish moves
     */
    protected Environment<FishAndSharkAgent> environment;

    /**
     * Gestation time
     * back to 0 as soon as the fish <code>canGiveBirth</code>
     */
    protected int gestation;

    /**
     * Age of the fish
     */
    private int age;

    public FishAndSharkAgent(Environment<FishAndSharkAgent> environment) {
        this. environment = environment;
    }

    @Override
    public void doIt() {
        age++;
    }

    /**
     * check if the fish <code>canGiveBirth</code>, and give birth by calling <code>addChild</code> if there is at least a place near to it.
     */
    protected void tryToGiveBirth() {
        if (this.canGiveBirth()) {
            Coordinate childCoordinate = environment.findFreeSpace(this);
            if (childCoordinate != null) {
                this.addChild(childCoordinate);
            }
            this.gestation = 0;
        }
        else {
            gestation++;
        }
    }

    /**
     * Add a child into environment
     * @param childCoordinate the child's coordinate
     */
    protected abstract void addChild(Coordinate childCoordinate);

    /**
     *
     * @return true iff this Fish can give birth
     */
    protected abstract boolean canGiveBirth();

    /**
     * Try to move into a neighbor square of the environment
     */
    protected void tryToMove() {
        Coordinate nextCoordinate = environment.findFreeSpace(this);
        if (nextCoordinate != null) {
            environment.move(this, nextCoordinate);
        }
    }

    /**
     * remove this fish from its environment
     */
    public abstract void removeFromEnvironment();

    public abstract Color getColor();

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}