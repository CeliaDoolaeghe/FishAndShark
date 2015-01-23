package iagl.idl.simulation;

import iagl.idl.simulation.mas.MAS;
import iagl.idl.simulation.mas.StoppableMAS;
import iagl.idl.simulation.mas.agent.PacManMAS;
import iagl.idl.simulation.mas.agent.pacman.PacManAgent;
import iagl.idl.simulation.mas.agent.pacman.Predator;
import iagl.idl.simulation.mas.agent.pacman.Prey;
import iagl.idl.simulation.mas.environment.Environment;
import iagl.idl.simulation.view.SimulationFrame;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;

public class PacManApp {

	public static void main(String[] args) throws InterruptedException {
		if (args.length < 5) {
			usage();
		}

		int size = Integer.parseInt(args[0]);
		int numberOfPreys = Integer.parseInt(args[1]);
		int numberOfPredators = Integer.parseInt(args[2]);
		int percentageOfObstacles = Integer.parseInt(args[3]);
		int delay = Integer.parseInt(args[4]);
		
		Environment<PacManAgent> environment = new Environment<>(size);
        final PacManMAS mas = new PacManMAS(environment, delay);
        mas.init(numberOfPreys, numberOfPredators, percentageOfObstacles);

        JFrame frame = new SimulationFrame("PacMan", mas);
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
        System.err.println("usage: size numberOfPreys numberOfPredators percentageOfObstacles delay");
        System.exit(-1);
    }
}
