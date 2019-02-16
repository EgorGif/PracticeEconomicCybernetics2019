package pack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyFrame extends JComponent {

    private Timer timer;
    private String stroke;
    private String bgColor;
    private String segmentColor;
    private double angle = 0;
    private final double ROTATE = Math.PI/60;

    MyFrame(String stroke, String bgColor, String segmentColor, int delay) {
        this.stroke = stroke;
        this.bgColor = bgColor;
        this.segmentColor = segmentColor;

        timer = new Timer(delay, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Segment seg = new Segment(angle, bgColor, stroke, segmentColor);
        seg.paint(g);
        angle += ROTATE;
    }

    public static void main(String args[]) {
        JFrame frame = new JFrame("Segment");
        MyFrame MF = new MyFrame(args[0], args[1], args[2], 30);
        frame.add(MF);
        frame.setSize(700, 700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}
