package iagl.idl.simulation;

import iagl.idl.simulation.graph.SatisfactionLogger;
import iagl.idl.simulation.mas.MAS;
import iagl.idl.simulation.mas.agent.segregation.SegregationAgent;
import iagl.idl.simulation.mas.environment.Environment;
import iagl.idl.simulation.view.SimulationFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

/**
 * Run the Segregation Simulation
 * @author Célia Cacciatore, Jonathan Geoffroy
 */
public class SegregationApp {
    public static void main(String[] args) throws InterruptedException, FileNotFoundException {
        if (args.length < 5) {
            usage();
        }

        int size = Integer.parseInt(args[0]);
        int numberOfGreen = Integer.parseInt(args[1]);
        int numberOfRed = Integer.parseInt(args[2]);
        int tolerance = Integer.parseInt(args[3]);
        int delay = Integer.parseInt(args[4]);

        SegregationAgent.setTOLERANCE(tolerance);
        Environment<SegregationAgent> environment = new Environment<>(size);
        List<SegregationAgent> agents = new LinkedList<>();
        for (int i = 0; i < numberOfGreen; i++) {
            agents.add(new SegregationAgent(environment, Color.GREEN));
        }
        for (int i = 0; i < numberOfRed; i++) {
            agents.add(new SegregationAgent(environment, Color.RED));
        }
        environment.init(agents);

        final MAS<SegregationAgent> mas = new MAS<>(environment, delay);

        // Initialize Logger
        new SatisfactionLogger(mas);

        JFrame frame = new SimulationFrame("Segregation", mas);
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
        System.err.println("usage: size numberOfGreen numberOfRed intolerance delay");
        System.exit(-1);
    }
}
