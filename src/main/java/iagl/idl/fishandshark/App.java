package iagl.idl.fishandshark;

import iagl.idl.fishandshark.mas.MAS;
import iagl.idl.fishandshark.mas.environment.Environment;
import iagl.idl.fishandshark.view.FishAndSharkFrame;

import javax.swing.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws InterruptedException {
        MAS mas = MAS.getInstance();
        JFrame frame = new FishAndSharkFrame("Fish and Shark", mas);
        frame.setVisible(true);
        mas.run();
    }
}
