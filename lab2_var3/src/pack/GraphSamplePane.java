package pack;
import javax.swing.*;
import java.awt.*;

public class GraphSamplePane extends JComponent {
    private GraphSample example;
    private Dimension size;

    public GraphSamplePane(final GraphSample example) {
        super();
        this.example = example;
        size = new Dimension(example.getWidth(), example.getHeight());
        setMaximumSize( size );
    }

    public void paintComponent(final Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, size.width, size.height);
        g.setColor(Color.black);
        example.draw((Graphics2D) g, this);
    }

    public Dimension getPreferredSize() { return size; }
    public Dimension getMinimumSize() { return size; }
}
