package iagl.idl.simulation.view;

import iagl.idl.simulation.mas.MAS;
import iagl.idl.simulation.mas.agent.Agent;
import iagl.idl.simulation.mas.environment.Environment;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Display MAS status.
 *
 * @author Célia Cacciatore, Jonathan Geoffroy
 */
public class StatusPanel extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	private MAS mas;
    private JLabel chrononsLabel;
    private Map<Color, JLabel> counterLabels;

    public StatusPanel(MAS mas) {
        super();
        this.mas = mas;

        // Subscribe to Environment Observation
        Environment<Agent> environment = mas.getEnvironment();
        environment.addObserver(this);

        // Create Labels
        chrononsLabel = new JLabel(String.valueOf(mas.getScheduling()));

        // Compute colors
        Set<Color> colors = new HashSet<>();
        for(Agent agent : environment.getAllAgents()) {
            colors.add(agent.getColor());
        }

        // Create a label for each color
        Map<Color, JLabel> colorLabels = new HashMap<>();
        counterLabels = new HashMap<>();
        for(Color color : colors) {
            colorLabels.put(color, new JLabel());
            counterLabels.put(color, new JLabel());
        }

        // Add labels to Panel
        GridLayout layout = new GridLayout(0, 2);
        setLayout(layout);
        JLabel colorLabel;
        for(Color color : colors) {
            colorLabel = colorLabels.get(color);
            colorLabel.setOpaque(true);
            colorLabel.setBackground(color);
            add(colorLabel);
            add(counterLabels.get(color));
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        Environment<Agent> environment = mas.getEnvironment();
        chrononsLabel.setText(String.valueOf(mas.getScheduling()));

        // Compute the number of Agents for each color
        Color currentColor;
        int nbAgentsForColor;
        Map<Color, Integer> counter = new HashMap<>();
        for(Agent agent : environment.getAllAgents()) {
            currentColor = agent.getColor();
            if(counter.containsKey(currentColor)) {
                nbAgentsForColor = counter.get(currentColor) + 1;
            }
            else {
                nbAgentsForColor = 1;
            }
            counter.put(currentColor, nbAgentsForColor);
        }

        // Update each JLabel
        for(Color color: counter.keySet()) {
            counterLabels.get(color).setText(String.valueOf(counter.get(color)));
        }
    }
}
