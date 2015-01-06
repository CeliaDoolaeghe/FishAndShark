package iagl.idl.fishandshark.mas.agent;

import iagl.idl.fishandshark.environment.Coordinate;
import iagl.idl.fishandshark.environment.Environment;

public class Fish extends Agent {

	private final static int GESTATION_DURATION = 2;
	
	public Fish(Coordinate coordinate, Environment environment) {
		super(coordinate, environment);
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
}