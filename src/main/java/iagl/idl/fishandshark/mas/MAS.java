package iagl.idl.fishandshark.mas;

import iagl.idl.fishandshark.mas.agent.Agent;
import iagl.idl.fishandshark.mas.environment.Coordinate;
import iagl.idl.fishandshark.mas.environment.Environment;

import java.util.*;

/**
 * @author Jérémy Bossut, Jonathan Geoffroy
 */
public class MAS {
    private final static MAS INSTANCE = new MAS();
    private long scheduling;
    private boolean terminated;
    private Environment environment;


    private MAS() {
        environment = new Environment();
    }

    public static MAS getInstance() {
        return INSTANCE;
    }

    public void run() throws InterruptedException {
        while(!terminated) {
            Thread.sleep(200);
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
