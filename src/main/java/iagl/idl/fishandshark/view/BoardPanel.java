package iagl.idl.fishandshark.view;

import iagl.idl.fishandshark.mas.MAS;
import iagl.idl.fishandshark.mas.agent.Agent;
import iagl.idl.fishandshark.mas.environment.Environment;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * @author Jérémy Bossut, Jonathan Geoffroy
 */
public class BoardPanel extends JPanel implements Observer {
    private Environment environment;
    public BoardPanel(Environment environment) {
        super();
        this.environment = environment;
        environment.addObserver(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Agent[][] board = environment.getBoard();
        int squareHeight = getHeight() / board.length;
        int squareWidth =  getWidth() / board[0].length;

        for(int y = 0; y < board.length; y++) {
            for(int x = 0; x < board[y].length; x++) {
                if(board[y][x] == null) {
                    g.setColor(Color.BLUE);
                }
                else {
                    g.setColor(board[y][x].getColor());
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
