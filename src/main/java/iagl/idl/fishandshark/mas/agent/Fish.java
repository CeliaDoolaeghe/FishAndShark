package iagl.idl.fishandshark.mas.agent;

import iagl.idl.fishandshark.mas.environment.Coordinate;
import iagl.idl.fishandshark.mas.environment.Environment;

import java.awt.*;

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
	
	public void addChild(Coordinate childCoordinate) {
		environment.addFish(childCoordinate);
	}
	
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
        return Color.YELLOW;
    }

    public static void setGestationDuration(int gestationDuration) {
        GESTATION_DURATION = gestationDuration;
    }
}