package iagl.idl.simulation.graph;

import iagl.idl.simulation.mas.MAS;
import iagl.idl.simulation.mas.agent.Agent;

import java.io.FileNotFoundException;


/**
 * Specific Log4J logger which counts the number of Fish & Sharks for each time in environment, and then log it into a file.
 * @author Célia Cacciatore, Jonathan Geoffroy
 */
public class TimeLogger<T extends Agent> extends CSVLogger<T> {
     public TimeLogger(MAS<T> mas) throws FileNotFoundException {
        super(mas);
    }

    protected void writeLine() {
        long chronons = mas.getScheduling();
        StringBuilder builder = new StringBuilder(chronons + " ");
        getColorPopulation(builder);
        logger.trace(builder.toString());
    }
}
