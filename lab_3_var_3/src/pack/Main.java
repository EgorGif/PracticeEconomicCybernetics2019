package pack;
import java.awt.*;
import java.applet.*;

public class Main extends Applet {

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        this.setBackground(Color.white);
        this.setSize(new Dimension(400,500));
        g2.setPaint(Color.black);
        g2.setStroke(new MyStroke(10,15));
        g2.draw(new Strofoid(90,this.getSize().width / 2,this.getSize().height / 2));
    }
}
