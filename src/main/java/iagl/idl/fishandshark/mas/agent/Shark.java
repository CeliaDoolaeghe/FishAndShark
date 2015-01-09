package iagl.idl.fishandshark.mas.agent;

import iagl.idl.fishandshark.mas.environment.Coordinate;
import iagl.idl.fishandshark.mas.environment.Environment;

import java.awt.*;
import java.util.Map;

public class Shark extends Agent {

	private final static int STARVATION_DURATION = 3;
	private final static int GESTATION_DURATION = 8;
	
	private int starvation;
	
	public Shark(Environment environment) {
		super(environment);
	}

	@Override
	public void doIt() {
		if(starvation >= STARVATION_DURATION) {
            environment.remove(this);
        }
        else {
            tryToEat();
            tryToGiveBirth();
            tryToMove();
        }
	}

    private void tryToEat() {
        Map<Coordinate, Agent> neighbors = environment.getNeighbors(this);
        Agent neighbor;
        for(Coordinate neighborCoordinate : neighbors.keySet()) {
            neighbor = neighbors.get(neighborCoordinate);
            if(neighbor.isEatable()) {
                eat(neighbor);
                starvation = 0;
                return;
            }
        }
        starvation++;
    }

    private void eat(Agent agent) {
        agent.removeFromEnvironment();
    }

    public void addChild(Coordinate childCoordinate) {
		environment.addShark(childCoordinate);
	}
	
	public boolean canGiveBirth() {
		return gestation == GESTATION_DURATION;
	}

    @Override
    public boolean isEatable() {
        return false;
    }

    @Override
    public void removeFromEnvironment() {
        environment.remove(this);
    }

    @Override
    public Color getColor() {
        return Color.RED;
    }
}