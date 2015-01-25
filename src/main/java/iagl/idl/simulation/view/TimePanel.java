package iagl.idl.simulation.view;

import iagl.idl.simulation.mas.MAS;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 * Display a "start/pause" button which can pause the simulation,
 * and a "stop" button which can stop the simulation and exit the software.
 *
 *  @author Célia Cacciatore, Jonathan Geoffroy
 */
public class TimePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private final MAS mas;

    public TimePanel(MAS mas) {
        super();
        this.mas = mas;

        final JButton startPause = new JButton("Pause");
        startPause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(TimePanel.this.mas.isTerminated()) {
                    try {
                        TimePanel.this.mas.run();
                        startPause.setText("Pause");
                    } catch (InterruptedException e) {
                        System.exit(-1);
                    }
                }
                else {
                    TimePanel.this.mas.setTerminated(true);
                    startPause.setText("Start");
                }
            }
        });

        JButton stop = new JButton("Stop");
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                TimePanel.this.mas.setTerminated(true);
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(TimePanel.this);
                topFrame.dispatchEvent(new WindowEvent(topFrame, WindowEvent.WINDOW_CLOSING));
            }
        });

        this.add(startPause);
        this.add(stop);
    }
}
