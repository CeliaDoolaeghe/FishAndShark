package iagl.idl.simulation.mas.agent.segregation;

import iagl.idl.simulation.mas.agent.Agent;
import iagl.idl.simulation.mas.environment.Coordinate;
import iagl.idl.simulation.mas.environment.Environment;

import java.awt.*;
import java.util.Map;

/**
 * Agent for Schelling Segregation simulation
 * This Agent computes the compatibility with his neighbor. Two Agents with a different color are incompatibles.
 * Otherwise, if a neighbor has the same color, or if there is no agent on a near square, compatibility increases.<br/>
 * If compatibility with neighbor is less than tolerance, Agent tries to move to a near square
 *
 * @author Célia Cacciatore, Jonathan Geoffroy
 */
public class SegregationAgent implements Agent {
    private static int TOLERANCE = 30;

    /**
     * Environment where this agent is
     */
    private Environment<SegregationAgent> environment;

    /**
     * the color of this agent
     */
    private Color color;

    public SegregationAgent(Environment<SegregationAgent> environment, Color color) {
        this.environment = environment;
        this.color = color;
    }

    public float getSatisfaction() {
        final Map<Coordinate, SegregationAgent> neighbors = environment.getAllNeighbors(this);
        int compatibility = 0;
        for(SegregationAgent agent : neighbors.values()) {
            if(agent != null && agent.color.equals((color))) {
                compatibility++;
            }
        }
        compatibility = compatibility * 100 / 8;
        return compatibility;
	}

	public static void setTOLERANCE(int tolerance) {
        SegregationAgent.TOLERANCE = tolerance;
    }

    @Override
    public void doIt() {
        if(needToMove()) {
            Coordinate nextMove = environment.findFreeSpace();
            if(nextMove != null) {
                environment.move(this, nextMove);
            }
        }
    }

    /**
     * Check if this agent needs to move, i.e. if the satisfaction with his neighbors is less than tolerance
     * @return true iff the satisfaction with his neighbors is less than tolerance
     */
    private boolean needToMove() {
        return getSatisfaction() < TOLERANCE;
    }

    @Override
    public Color getColor() {
        return color;
    }
}
