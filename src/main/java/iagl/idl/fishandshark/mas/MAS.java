package iagl.idl.fishandshark.mas;

import iagl.idl.fishandshark.mas.agent.Agent;
import iagl.idl.fishandshark.mas.environment.Coordinate;
import iagl.idl.fishandshark.mas.environment.Environment;

import java.util.*;

/**
 * @author Jérémy Bossut, Jonathan Geoffroy
 */
public class MAS {
    private final int delay;
    private long scheduling;
    private boolean terminated;
    private Environment environment;


    public MAS(Environment environment, int delay) {
        this.environment = environment;
        this.delay = delay;
    }

    public void run() throws InterruptedException {
        while(!terminated) {
            Thread.sleep(delay);
            List<Agent> agents = environment.getAllAgents();
            for(Agent agent : agents) {
                if(!environment.isDead(agent)) {
                    agent.doIt();
                }
            }
            scheduling++;
            environment.clearDead();
            environment.notifyObservers();
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
