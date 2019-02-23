package pack;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class GraphSampleFrame extends JFrame {

    private static final String CLASSNAME = "RoadSign";

    public GraphSampleFrame(final GraphSample[] examples) {
        super("GraphSampleFrame");
        final Container cpane = getContentPane();
        cpane.setLayout(new BorderLayout());
        final JTabbedPane tpane = new JTabbedPane();
        cpane.add(tpane, BorderLayout.CENTER);
        for (int i = 0; i < examples.length; i++) {
            final GraphSample e = examples[i];
            tpane.addTab(e.getName(), new GraphSamplePane(e));
        }
    }

    public static void main(final String[] args) {
        GraphSample[] examples = new GraphSample[1];
        try {
            final Class exampleClass = Class.forName("pack."+CLASSNAME);
            examples[0] = (GraphSample) exampleClass.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        final GraphSampleFrame f = new GraphSampleFrame(examples);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);
    }
}
