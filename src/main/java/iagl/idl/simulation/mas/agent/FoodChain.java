package iagl.idl.simulation.mas.agent;

/**
 * Agents in the food chain can either eat other agents either be eaten by them.
 * @author Célia Cacciatore, Jonathan Geoffroy
 */
public interface FoodChain {
    /**
     * @return true iff another Agent can eat it
     */
    public boolean isEatable();

    /**
     * @return true iff it can eat another Agent
     */
    public boolean canEat();
}
