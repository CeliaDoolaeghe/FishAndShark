package iagl.idl.fishandshark;

import iagl.idl.fishandshark.graph.CSVLogger;
import iagl.idl.fishandshark.graph.FishAndSharksLogger;
import iagl.idl.fishandshark.graph.FunctionLogger;
import iagl.idl.fishandshark.mas.MAS;
import iagl.idl.fishandshark.mas.agent.Fish;
import iagl.idl.fishandshark.mas.agent.Shark;
import iagl.idl.fishandshark.mas.environment.Environment;
import iagl.idl.fishandshark.view.FishAndSharkFrame;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;

/**
 * @author Célia Cacciatore, Jonathan Geoffroy
 */
public class App {
    public static void main(String[] args) throws InterruptedException, FileNotFoundException {
        if (args.length < 7) {
            usage();
        }
        int size = Integer.parseInt(args[0]);
        int numberOfFish = Integer.parseInt(args[1]);
        int numberOfSharks = Integer.parseInt(args[2]);
        int fishGestation = Integer.parseInt(args[3]);
        int sharksGestation = Integer.parseInt(args[4]);
        int sharkStarvation = Integer.parseInt(args[5]);
        int delay = Integer.parseInt(args[6]);

        Fish.setGestationDuration(fishGestation);
        Shark.setGestationDuration(sharksGestation);
        Shark.setStarvationDuration(sharkStarvation);

        Environment environment = new Environment(size, numberOfFish, numberOfSharks);
        final MAS mas = new MAS(environment, delay);
        final CSVLogger functionLogger = new FunctionLogger(mas, "target/simulationTime.csv");
        final CSVLogger fishAndSharksLogger = new FishAndSharksLogger(mas, "target/fishAndSharks.csv");
        JFrame frame = new FishAndSharkFrame("Fish and Shark", mas);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                mas.setTerminated(true);
                functionLogger.close();
                fishAndSharksLogger.close();
            }
        });
        mas.run();
    }

    private static void usage() {
        System.err.println("usage: size numberOfFish numberOfSharks fishGestation sharksGestation sharkStarvation delay");
        System.exit(-1);
    }
}
