package pack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyFrame extends JComponent {

    private Segment segment;
    private double angle;
    private static final double ROTATE = Math.PI/60;

    MyFrame(final String stroke, final String bgColor, final String segmentColor, final int delay) {
        super();
        segment = new Segment(angle,bgColor,stroke,segmentColor);
        final Timer timer = new Timer(delay, new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                angle+=ROTATE;
                repaint();
            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        segment.setAngle(ROTATE);
        segment.paint(g);
    }

    public static void main(final String args[]) {
        final JFrame frame = new JFrame("Segment");
        final MyFrame MF = new MyFrame(args[0], args[1], args[2], 30);
        frame.add(MF);
        frame.setSize(700, 700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}
