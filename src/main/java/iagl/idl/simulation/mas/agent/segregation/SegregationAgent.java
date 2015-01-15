package iagl.idl.simulation.mas.agent.segregation;

import iagl.idl.simulation.mas.agent.Agent;
import iagl.idl.simulation.mas.environment.Coordinate;
import iagl.idl.simulation.mas.environment.Environment;

import java.awt.*;
import java.util.Map;

/**
 * Agent for Schelling Segregation simulation
 * This Agent compute the compatibility with his neighbor. Two Agents with a different color are incompatibles.
 * Otherwise, if neighbor has the same color, or if there is no agent on a near square, compatibility increase.<br/>
 * If compatibility with neighbor is less than intolerance, Agent try to move to a near square
 *
 * @author Célia Cacciatore, Jonathan Geoffroy
 */
public class SegregationAgent implements Agent {
    private static int INTOLERANCE = 30;

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

    public static void setINTOLERANCE(int INTOLERANCE) {
        SegregationAgent.INTOLERANCE = INTOLERANCE;
    }

    @Override
    public void doIt() {
        if(needToMove()) {
            Coordinate nextMove = environment.findFreeSpace(this);
            if(nextMove != null) {
                environment.move(this, nextMove);
            }
        }
    }

    /**
     * Check if this agent need to move, i.e. if the compatibility with his neighbors is less than intolerance
     * @return true iff the compatibility with his neighbors is less than intolerance
     */
    private boolean needToMove() {
        int nbNeighbors = 8;
        final Map<Coordinate, SegregationAgent> neighbors = environment.getNeighbors(this);
        int compatibility = nbNeighbors - neighbors.size(); // free space is considered as compatible
        for(SegregationAgent agent : neighbors.values()) {
            if(agent.color.equals((color))) {
                compatibility++;
            }
        }
        compatibility = compatibility * 100 / 8;
        return compatibility < INTOLERANCE;
    }

    @Override
    public Color getColor() {
        return color;
    }
}
