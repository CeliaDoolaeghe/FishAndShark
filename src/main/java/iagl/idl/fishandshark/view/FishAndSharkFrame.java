package iagl.idl.fishandshark.view;

import iagl.idl.fishandshark.mas.MAS;

import javax.swing.*;
import java.awt.*;

/**
 * @author Jérémy Bossut, Jonathan Geoffroy
 */
public class FishAndSharkFrame extends JFrame {
    private BoardPanel boardPanel;
    private StatusPanel statusPanel;

    public FishAndSharkFrame(String title, MAS mas) throws HeadlessException {
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
