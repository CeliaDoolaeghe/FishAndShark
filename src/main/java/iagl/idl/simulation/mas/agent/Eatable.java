package iagl.idl.simulation.mas.agent;

/**
 * @author Célia Cacciatore, Jonathan Geoffroy
 */
public interface Eatable {
    /**
     *
     * @return true iff another Fish can eat it
     */
    public boolean isEatable();
}
