package iagl.idl.fishandshark.mas.agent;

import iagl.idl.fishandshark.environment.Coordinate;
import iagl.idl.fishandshark.environment.Environment;

public class Shark extends Agent {

	private final static int STARVATION_DURATION = 3;
	private final static int GESTATION_DURATION = 10;
	
	private int starvation;
	
	public Shark(Coordinate coordinate, Environment environment) {
		super(coordinate, environment);
	}

	@Override
	public void doIt() {
		
	}
	
	public void addChild(Coordinate childCoordinate) {
		environment.addShark(childCoordinate);
	}
	
	public boolean canGiveBirth() {
		return gestation == GESTATION_DURATION;
	}
}