package iagl.idl.simulation.mas.agent.pacman;

import iagl.idl.simulation.mas.environment.Environment;

import java.awt.*;

/**
 * @author Célia Cacciatore, Jonathan Geoffroy
 */
public class Obstacle extends PacManAgent {

    public Obstacle(Environment<PacManAgent> environment) {
        super(environment);
    }

    @Override
    public boolean isEatable() {
        return false;
    }

    @Override
    public boolean canStopSimulation() {
        return true;
    }

    @Override
    public void doIt() {}

    @Override
    public Color getColor() {
        return Color.BLACK;
    }
}
