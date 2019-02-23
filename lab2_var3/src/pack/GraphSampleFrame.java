package pack;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class GraphSampleFrame extends JFrame {

    static final String classname = "RoadSign";

    public GraphSampleFrame(final GraphSample[] examples) {
        super("GraphSampleFrame");

        Container cpane = getContentPane();
        cpane.setLayout(new BorderLayout());
        final JTabbedPane tpane = new JTabbedPane();
        cpane.add(tpane, BorderLayout.CENTER);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        for (int i = 0; i < examples.length; i++) {
            GraphSample e = examples[i];
            tpane.addTab(e.getName(), new GraphSamplePane(e));
        }
    }

    public static void main(String[] args) {
        GraphSample[] examples = new GraphSample[1];

        try {
            Class exampleClass = Class.forName("pack."+classname);
            examples[0] = (GraphSample) exampleClass.newInstance();
        }
        catch (ClassNotFoundException e) {
            System.err.println("Couldn't find example: "  + classname);
            System.exit(1);
        }
        catch (ClassCastException e) {
            System.err.println("Class " + classname +
                    " is not a GraphSample");
            System.exit(1);
        }
        catch (Exception e) {
            System.err.println("Couldn't instantiate example: " +
                    classname);
            System.exit(1);
        }

        GraphSampleFrame f = new GraphSampleFrame(examples);
        f.pack();
        f.setVisible(true);
    }
}