package iagl.idl.fishandshark.mas.agent;

import iagl.idl.fishandshark.environment.Coordinate;
import iagl.idl.fishandshark.environment.Environment;

public abstract class Agent {

	protected Coordinate coordinate;
	protected Environment environment;
	protected int gestation;
	
	public Agent(Coordinate coordinate, Environment environment) {
		super();
		this.coordinate = coordinate;
		this.environment = environment;
	}
	
	public abstract void doIt();
	
	protected void tryToGiveBirth() {
		if (this.canGiveBirth()) {
			Coordinate childCoordinate = environment.findFreeSpace(this.coordinate);
			if(childCoordinate == null) {
				childCoordinate = environment.findFreeSpace();
			}
			if(childCoordinate != null) {
				this.gestation = 0;
				this.addChild(childCoordinate);
			}
		}
	}
	
	protected abstract void addChild(Coordinate childCoordinate);
	
	protected abstract boolean canGiveBirth();
	
	protected void tryToMove() {
		Coordinate nextCoordinate = environment.findFreeSpace(coordinate);
		if(nextCoordinate != null) {
			environment.move(coordinate, nextCoordinate);
			coordinate = nextCoordinate;
		}
	}
}