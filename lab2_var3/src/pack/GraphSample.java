package pack;
import java.awt.*;

interface GraphSample {
    String getName();
    int getWidth();
    int getHeight();
    void draw(Graphics2D g, Component c);
}