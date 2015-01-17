package iagl.idl.simulation.mas;

import iagl.idl.simulation.mas.agent.Agent;
import iagl.idl.simulation.mas.environment.Environment;

import java.util.List;

/**
 * Multi-Agent System Engine
 * Run the simulation on an Environment
 *
 * @author Célia Cacciatore, Jonathan Geoffroy
 */
public class MAS<T extends Agent> {
    /**
     * The delay between two simulation turns (in ms)
     */
    private final int delay;

    /**
     * The number of turns already computed.
     */
    private long scheduling;

    private boolean terminated;

    /**
     * The Environment of this Simulation
     */
    private Environment<T> environment;


    public MAS(Environment<T> environment, int delay) {
        this.environment = environment;
        this.delay = delay;
    }

    /**
     * Run the simulation while the simulation is not <code>terminated</code>
     * wait <code>delay</code> ms between two turns
     *
     * @throws InterruptedException
     */
    public void run() throws InterruptedException {
        terminated = false;
        Thread masRunner = new Thread(new MASRunner());
        masRunner.start();
    }

    public long getScheduling() {
        return scheduling;
    }

    public void setTerminated(boolean terminated) {
        this.terminated = terminated;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public boolean isTerminated() {
        return terminated;
    }

    class MASRunner implements Runnable {

        @Override
        public void run() {
            try {
                while (!terminated) {
                    Thread.sleep(delay);
                    List<T> agents = environment.getAllAgents();
                    for (T agent : agents) {
                        if (!environment.isDead(agent)) {
                            agent.doIt();
                        }
                    }
                    scheduling++;
                    environment.clearDead();
                    environment.notifyObservers();
                }
            } catch(InterruptedException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }
}
