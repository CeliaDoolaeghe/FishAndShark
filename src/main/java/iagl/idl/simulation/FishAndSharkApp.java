package iagl.idl.simulation;

import iagl.idl.simulation.graph.CSVLogger;
import iagl.idl.simulation.graph.PopulationLogger;
import iagl.idl.simulation.graph.TimeLogger;
import iagl.idl.simulation.mas.MAS;
import iagl.idl.simulation.mas.agent.fishandsharks.Fish;
import iagl.idl.simulation.mas.agent.fishandsharks.FishAndSharkAgent;
import iagl.idl.simulation.mas.agent.fishandsharks.Shark;
import iagl.idl.simulation.mas.environment.Environment;
import iagl.idl.simulation.view.SimulationFrame;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Célia Cacciatore, Jonathan Geoffroy
 */
public class FishAndSharkApp {
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

        Environment<FishAndSharkAgent> environment = new Environment<>(size);

        List<FishAndSharkAgent> agents = new LinkedList<>();
        for(int i = 0; i < numberOfFish; i ++) {
            agents.add(new Fish(environment));
        }
        for(int i = 0; i < numberOfSharks; i++) {
            agents.add(new Shark(environment));
        }
        environment.init(agents);

        final MAS<FishAndSharkAgent> mas = new MAS<>(environment, delay);

        // Configure Loggers

        new TimeLogger(mas);
        new PopulationLogger(mas);

        JFrame frame = new SimulationFrame("Fish and Shark", mas);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                mas.setTerminated(true);
            }
        });
        mas.run();
    }

    private static void usage() {
        System.err.println("usage: size numberOfFish numberOfSharks fishGestation sharksGestation sharkStarvation delay");
        System.exit(-1);
    }
}
