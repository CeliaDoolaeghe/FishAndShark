package iagl.idl.simulation.graph;

import iagl.idl.simulation.mas.MAS;
import iagl.idl.simulation.mas.environment.Environment;

import java.io.FileNotFoundException;

/**
 * @author Célia Cacciatore, Jonathan Geoffroy
 */
public class FishAndSharksLogger extends CSVLogger {

    public FishAndSharksLogger(MAS mas, String filePath) throws FileNotFoundException {
        super(mas, filePath);
    }

    @Override
    protected void writeLine() {
        Environment environment = mas.getEnvironment();
//        writer.printf("%s %s%n", environment.getFish(), environment.getSharks());
    }
}
