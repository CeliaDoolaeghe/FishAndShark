package iagl.idl.simulation.view;

import iagl.idl.simulation.mas.MAS;

import javax.swing.*;
import java.awt.*;

/**
 * @author Célia Cacciatore, Jonathan Geoffroy
 */
public class SimulationFrame extends JFrame {

	private static final long serialVersionUID = 1L;

    public SimulationFrame(String title, MAS mas) throws HeadlessException {
        super(title);

        // Create panels
        BoardPanel boardPanel = new BoardPanel(mas.getEnvironment());
        boardPanel.setPreferredSize(new Dimension(600, 600));
        StatusPanel statusPanel = new StatusPanel(mas);
        JPanel timePanel = new TimePanel(mas);

        JPanel globalLayout = new JPanel();
        globalLayout.setLayout(new BorderLayout());

        // Add panels to this Frame
        globalLayout.add(boardPanel, BorderLayout.CENTER);
        globalLayout.add(statusPanel, BorderLayout.EAST);
        globalLayout.add(timePanel, BorderLayout.SOUTH);

        this.setContentPane(globalLayout);
        this.setSize(new Dimension(800, 600));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
