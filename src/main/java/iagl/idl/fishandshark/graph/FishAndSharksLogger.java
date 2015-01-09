package iagl.idl.fishandshark.graph;

import iagl.idl.fishandshark.mas.MAS;
import iagl.idl.fishandshark.mas.environment.Environment;

import java.io.FileNotFoundException;

/**
 * @author Jérémy Bossut, Jonathan Geoffroy
 */
public class FishAndSharksLogger extends CSVLogger {

    public FishAndSharksLogger(MAS mas, String filePath) throws FileNotFoundException {
        super(mas, filePath);
    }

    @Override
    protected void writeLine() {
        Environment environment = mas.getEnvironment();
        writer.printf("%s %s%n", environment.getFish(), environment.getSharks());
    }
}
