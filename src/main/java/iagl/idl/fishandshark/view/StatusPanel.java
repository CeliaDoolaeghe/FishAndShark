package iagl.idl.fishandshark.view;

import iagl.idl.fishandshark.mas.MAS;
import iagl.idl.fishandshark.mas.environment.Environment;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Display MAS status.
 *
 * @author Célia Cacciatore, Jonathan Geoffroy
 */
public class StatusPanel extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	private MAS mas;
    private JLabel chrononsLabel;
    private JLabel fishLabel;
    private JLabel sharksLabel;

    public StatusPanel(MAS mas) {
        super();
        this.mas = mas;

        // Subscribe to Environment Observation
        Environment environment = mas.getEnvironment();
        environment.addObserver(this);

        // Create Labels
        chrononsLabel = new JLabel(String.valueOf(mas.getScheduling()));
        fishLabel = new JLabel(String.valueOf(environment.getFish()));
        sharksLabel = new JLabel(String.valueOf(environment.getSharks()));

        // Add labels to Panel
        GridLayout layout = new GridLayout(0, 2);
        setLayout(layout);
        add(new JLabel("Chronons:"));
        add(chrononsLabel);
        JLabel textFishLabel = new JLabel("Fish:");
        textFishLabel.setForeground(Color.BLUE);
        add(textFishLabel);
        add(fishLabel);
        JLabel textSharksLabel = new JLabel("Sharks:");
        textSharksLabel.setForeground(Color.RED);
        add(textSharksLabel);
        textFishLabel.setForeground(Color.BLUE);
        add(sharksLabel);
    }

    @Override
    public void update(Observable observable, Object o) {
        Environment environment = mas.getEnvironment();
        chrononsLabel.setText(String.valueOf(mas.getScheduling()));
        fishLabel.setText(String.valueOf(environment.getFish()));
        sharksLabel.setText(String.valueOf(environment.getSharks()));
    }
}
