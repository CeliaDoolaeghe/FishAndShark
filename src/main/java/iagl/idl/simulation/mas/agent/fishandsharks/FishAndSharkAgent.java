package iagl.idl.simulation.mas.agent.fishandsharks;

import iagl.idl.simulation.mas.agent.Agent;
import iagl.idl.simulation.mas.environment.Coordinate;
import iagl.idl.simulation.mas.environment.Environment;

import java.awt.*;

/**
 * Agent element of the @link MAS
 * <p>
 * The abstract method <code>doIt</code> lets this agent compute its next behavior, depending on the Environment<br/>
 * In particular, "move" and "giveBirth" behaviors are implemented.
 * </p>
 *
 * @author Célia Cacciatore, Jonathan Geoffroy
 */
public abstract class FishAndSharkAgent implements Agent {
    protected Environment<FishAndSharkAgent> environment;

    protected int gestation;
    private int age;

    public FishAndSharkAgent(Environment<FishAndSharkAgent> environment) {
        this. environment = environment;
    }


    public void doIt() {
        age++;
    }

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

    protected abstract void addChild(Coordinate childCoordinate);

    protected abstract boolean canGiveBirth();

    protected void tryToMove() {
        Coordinate nextCoordinate = environment.findFreeSpace(this);
        if (nextCoordinate != null) {
            environment.move(this, nextCoordinate);
        }
    }

    public abstract boolean isEatable();

    public abstract void removeFromEnvironment();

    public abstract Color getColor();

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}