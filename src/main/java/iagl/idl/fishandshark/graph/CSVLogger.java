package iagl.idl.fishandshark.graph;

import iagl.idl.fishandshark.mas.MAS;
import iagl.idl.fishandshark.mas.environment.Environment;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Observable;
import java.util.Observer;

/**
 * @author Jérémy Bossut, Jonathan Geoffroy
 */
public class CSVLogger implements Observer {

    protected final MAS mas;
    protected final PrintWriter writer;

    public CSVLogger(MAS mas, String filePath) throws FileNotFoundException {
        this.mas = mas;
        mas.getEnvironment().addObserver(this);
        writer = new PrintWriter(filePath);
    }
    @Override
    public void update(Observable observable, Object o) {
        writeLine();
    }

    protected void writeLine() {
        Environment environment = mas.getEnvironment();
        writer.printf("%s %s %s%n", mas.getScheduling(), environment.getFish(), environment.getSharks());
    }

    public void close() {
        writer.close();
    }
}