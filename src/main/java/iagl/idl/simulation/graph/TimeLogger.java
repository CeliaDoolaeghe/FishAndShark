package iagl.idl.simulation.graph;

import iagl.idl.simulation.mas.MAS;
import iagl.idl.simulation.mas.agent.Agent;

import java.io.FileNotFoundException;


/**
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
