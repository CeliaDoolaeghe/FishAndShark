package iagl.idl.fishandshark.graph;

import iagl.idl.fishandshark.mas.MAS;
import iagl.idl.fishandshark.mas.environment.Environment;

import java.io.FileNotFoundException;

/**
 * @author Jérémy Bossut, Jonathan Geoffroy
 */
public class FunctionLogger extends CSVLogger {

    public FunctionLogger(MAS mas, String filePath) throws FileNotFoundException {
        super(mas, filePath);
    }

    @Override
    protected void writeLine() {
        Environment environment = mas.getEnvironment();
        writer.printf("%s %s %s%n", mas.getScheduling(), environment.getFish(), environment.getSharks());
    }
}
