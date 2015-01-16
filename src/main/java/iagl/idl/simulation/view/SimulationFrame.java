package iagl.idl.simulation.view;

import iagl.idl.simulation.mas.MAS;

import javax.swing.*;
import java.awt.*;

/**
 * @author Célia Cacciatore, Jonathan Geoffroy
 */
public class SimulationFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private BoardPanel boardPanel;
    private StatusPanel statusPanel;

    public SimulationFrame(String title, MAS mas) throws HeadlessException {
        super(title);

        // Create panels
        boardPanel = new BoardPanel(mas.getEnvironment());
        boardPanel.setPreferredSize(new Dimension(600, 600));
        statusPanel = new StatusPanel(mas);
        statusPanel.setPreferredSize(new Dimension(200, 600));

        JPanel globalLayout = new JPanel();
        globalLayout.setLayout(new BoxLayout(globalLayout, BoxLayout.X_AXIS));
        // Add panels to this Frame
        globalLayout.add(boardPanel);
        globalLayout.add(statusPanel);
        this.setContentPane(globalLayout);
        this.setSize(new Dimension(800, 600));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}