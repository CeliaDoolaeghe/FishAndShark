package iagl.idl.simulation.graph;

import iagl.idl.simulation.mas.MAS;
import iagl.idl.simulation.mas.agent.Agent;
import iagl.idl.simulation.mas.environment.Environment;

import java.io.FileNotFoundException;

/**
 * Specific Log4J logger which counts the number of Fish & Sharks in environment, and then log it into a file.
 *
 * @author Célia Cacciatore, Jonathan Geoffroy
 */
public class PopulationLogger<T extends Agent> extends CSVLogger<T> {
    public PopulationLogger(MAS<T> mas) throws FileNotFoundException {
        super(mas);
    }

    protected void writeLine() {
        Environment environment = mas.getEnvironment();
        StringBuilder builder = new StringBuilder();
        getColorPopulation(builder);
        logger.trace(builder.toString());
    }
}
