package iagl.idl.fishandshark.mas.agent;

import iagl.idl.fishandshark.mas.environment.Coordinate;
import iagl.idl.fishandshark.mas.environment.Environment;

import java.awt.*;

public abstract class Agent {
	protected Environment environment;
	protected int gestation;
	
	public Agent(Environment environment) {
		super();
		this.environment = environment;
	}
	
	public abstract void doIt();
	
	protected void tryToGiveBirth() {
		if (this.canGiveBirth()) {
			Coordinate childCoordinate = environment.findFreeSpace(this);
			if(childCoordinate == null) {
				childCoordinate = environment.findFreeSpace();
			}
			if(childCoordinate != null) {
				this.gestation = 0;
				this.addChild(childCoordinate);
			}
		}
        else {
            gestation++;
        }
	}
	
	protected abstract void addChild(Coordinate childCoordinate);
	
	protected abstract boolean canGiveBirth();
	
	protected void tryToMove() {
		Coordinate nextCoordinate = environment.findFreeSpace(this);
		if(nextCoordinate != null) {
			environment.move(this, nextCoordinate);
		}
	}

    public abstract boolean isEatable();

    public abstract void removeFromEnvironment();

    public abstract Color getColor();
}