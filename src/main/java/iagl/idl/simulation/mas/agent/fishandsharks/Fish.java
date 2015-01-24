package iagl.idl.simulation.mas.agent.fishandsharks;

import iagl.idl.simulation.mas.environment.Coordinate;
import iagl.idl.simulation.mas.environment.Environment;

import java.awt.*;

/**
 * Agent which represents a Fish
 * <p>
 * First try to move before giving birth to another Fish if its <code>gestation</code> as bigger than the <code>GESTATION_DURATION</code>
 * <p/>
 * </p>
 *
 * @author Célia Cacciatore, Jonathan Geoffroy
 */
public class Fish extends FishAndSharkAgent {

    private static int GESTATION_DURATION = 2;

    public Fish(Environment<FishAndSharkAgent> environment) {
        super(environment);
    }

    @Override
    public void doIt() {
        super.doIt();
        this.tryToMove();
        this.tryToGiveBirth();
    }

    @Override
    public void addChild(Coordinate childCoordinate) {
        Fish fish = new Fish(environment);
        environment.addAgent(fish, childCoordinate);
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
        environment.removeAgent(this);
    }

    @Override
    public Color getColor() {
        return Color.BLUE;
    }

    public static void setGestationDuration(int gestationDuration) {
        GESTATION_DURATION = gestationDuration;
    }

	@Override
	public boolean canEat() {
		return false;
	}
}