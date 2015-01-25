package iagl.idl.simulation.view;

import iagl.idl.simulation.mas.agent.Agent;
import iagl.idl.simulation.mas.environment.Environment;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Graphical view of an Environment
 *
 * @author Célia Cacciatore, Jonathan Geoffroy
 */
public class BoardPanel extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	private Environment environment;

    public BoardPanel(Environment environment) {
        super();
        this.environment = environment;
        environment.addObserver(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        List<List<Agent>> board = environment.getBoard();

        int height = board.size();
        int width = board.get(0).size();

        int squareHeight = getHeight() / height;
        int squareWidth = getWidth() / width;

        // Display environment by drawing an oval of the right color, depending on the agent inside the square
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (board.get(y).get(x) != null) {
                    g.setColor(board.get(y).get(x).getColor());
                } else {
                	g.setColor(Color.LIGHT_GRAY);
                }
                g.fillOval(x * squareWidth, y * squareHeight, squareWidth, squareHeight);
            }
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        this.repaint();
    }
}
