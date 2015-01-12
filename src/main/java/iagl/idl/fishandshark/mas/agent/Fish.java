package iagl.idl.fishandshark.mas.agent;

import iagl.idl.fishandshark.mas.environment.Coordinate;
import iagl.idl.fishandshark.mas.environment.Environment;

import java.awt.*;

/**
 * Agent which represents a Fish
 * <p>
 * A Fish first try to move before giving birth to another Fish if its <code>gestation</code> as bigger than the <code>GESTATION_DURATION</code>
 * <p/>
 * </p>
 *
 * @author Célia Cacciatore, Jonathan Geoffroy
 */
public class Fish extends Agent {

    private static int GESTATION_DURATION = 2;

    public Fish(Environment environment) {
        super(environment);
    }

    @Override
    public void doIt() {
        this.tryToMove();
        this.tryToGiveBirth();
    }

    @Override
    public void addChild(Coordinate childCoordinate) {
        environment.addFish(childCoordinate);
    }

    @Override
    public boolean canGiveBirth() {
        return gestation == GESTATION_DURATION;
    }

    @Override
    public boolean isEatable() {
        return true;
    }

    @Override
    public void removeFromEnvironment() {
        environment.remove(this);
    }

    @Override
    public Color getColor() {
        return Color.BLUE;
    }

    public static void setGestationDuration(int gestationDuration) {
        GESTATION_DURATION = gestationDuration;
    }
}