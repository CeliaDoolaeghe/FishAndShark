package iagl.idl.fishandshark.environment;

import iagl.idl.fishandshark.mas.agent.Fish;
import iagl.idl.fishandshark.mas.agent.Shark;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Environment {
	private int size;
	private Map<Coordinate, Fish> fishes;
	private Map<Coordinate, Shark> sharks;
	
	public Coordinate findFreeSpace(Coordinate coordinate) {
		List<Coordinate> possibilities = new LinkedList<Coordinate>();
		possibilities.add(new Coordinate(coordinate.getX() - 1, coordinate.getY() - 1));
		possibilities.add(new Coordinate(coordinate.getX() - 1, coordinate.getY()));
		possibilities.add(new Coordinate(coordinate.getX() - 1, coordinate.getY() + 1));
		possibilities.add(new Coordinate(coordinate.getX(), coordinate.getY() - 1));
		possibilities.add(new Coordinate(coordinate.getX(), coordinate.getY() + 1));
		possibilities.add(new Coordinate(coordinate.getX() + 1, coordinate.getY() - 1));
		possibilities.add(new Coordinate(coordinate.getX() + 1, coordinate.getY()));
		possibilities.add(new Coordinate(coordinate.getX() + 1, coordinate.getY() + 1));
		
		Collections.shuffle(possibilities);
		
		for(Coordinate c : possibilities) {
			if(isFree(c)) {
				return c;
			}
		}
		return null;
	}

	public Coordinate findFreeSpace() {
		Coordinate currentCoordinate;
		for(int y = 0; y < size; y++) {
			for(int x = 0; x < size; x++) {
				currentCoordinate = new Coordinate(x, y);
				if(isFree(currentCoordinate)) {
					return currentCoordinate;
				}
			}
		}
		return null;
	}
	
	private boolean isFree(Coordinate c) {
		return !fishes.containsKey(c) && !sharks.containsKey(c);
	}

	public void addFish(Coordinate fishCoordinate) {
		Fish fish = new Fish(fishCoordinate, this);
		fishes.put(fishCoordinate, fish);
		// TODO add fish into MAS
	}

	public void move(Coordinate previousCoordinate, Coordinate nextCoordinate) {
		if(fishes.containsKey(previousCoordinate)) {
			Fish fish = fishes.get(previousCoordinate);
			fishes.remove(previousCoordinate);
			fishes.put(nextCoordinate, fish);
		} else if (sharks.containsKey(previousCoordinate)) {
			Shark shark = sharks.get(previousCoordinate);
			sharks.remove(previousCoordinate);
			sharks.put(nextCoordinate, shark);
		}
	}

	public void addShark(Coordinate sharkCoordinate) {
		Shark shark = new Shark(sharkCoordinate, this);
		sharks.put(sharkCoordinate, shark);
		// TODO add shark into MAS
	}
}