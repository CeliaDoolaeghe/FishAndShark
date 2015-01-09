package iagl.idl.fishandshark.mas;

import iagl.idl.fishandshark.mas.agent.Agent;
import iagl.idl.fishandshark.mas.environment.Environment;

import java.util.List;

/**
 * Multi-Agent System Engine
 * Run the simulation on an Environment
 *
 * @author Célia Cacciatore, Jonathan Geoffroy
 */
public class MAS {
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
    private Environment environment;


    public MAS(Environment environment, int delay) {
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
        while (!terminated) {
            Thread.sleep(delay);
            List<Agent> agents = environment.getAllAgents();
            for (Agent agent : agents) {
                if (!environment.isDead(agent)) {
                    agent.doIt();
                }
            }
            scheduling++;
            environment.clearDead();
            environment.notifyObservers();
            if(environment.somebodyWon()) {
                setTerminated(true);
            }
        }
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


}
