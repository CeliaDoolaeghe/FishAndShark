package iagl.idl.simulation.graph;

import iagl.idl.simulation.mas.MAS;
import iagl.idl.simulation.mas.agent.Agent;
import iagl.idl.simulation.mas.environment.Environment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * Let loggers which extends this class observes the environment.
 *
 * @author Célia Cacciatore, Jonathan Geoffroy
 */
public abstract class CSVLogger<T extends Agent> implements Observer {

    protected final MAS<T> mas;
    protected final Logger logger;

    public CSVLogger(MAS<T> mas) throws FileNotFoundException {
        this.mas = mas;
        mas.getEnvironment().addObserver(this);
        logger = LogManager.getLogger(this.getClass());
    }

    @Override
    public void update(Observable observable, Object o) {
        writeLine();
    }

    protected void getColorPopulation(StringBuilder builder) {
        Environment environment = mas.getEnvironment();
        Map<Color, Integer> agentColors = environment.getAgentGroupedByColor();
        int nb;
        for(Color color : agentColors.keySet()) {
            nb = agentColors.get(color);
            builder.append(nb).append(" ");
        }
    }

    protected abstract void writeLine();
}