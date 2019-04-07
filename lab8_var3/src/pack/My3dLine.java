package pack;
import java.awt.*;
public class My3dLine extends Canvas{
    private int width = 2;
    private Color color = new Color(0, 0, 0);

    private int X_START_COORDINATE = 10;
    private int X_END_COORDINATE = 400;

    private int Y_COORDINATE = 100;

    public My3dLine() {}

    public My3dLine(int width) {
        this.width = width;
    }

    @Override
    public void paint(Graphics g) {

        g.setColor(color);

        g.draw3DRect(X_START_COORDINATE, Y_COORDINATE, width,1, true);
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setX_START_COORDINATE(int x_START_COORDINATE) {
        X_START_COORDINATE = x_START_COORDINATE;
    }

    public void setX_END_COORDINATE(int x_END_COORDINATE) {
        X_END_COORDINATE = x_END_COORDINATE;
    }

    public void setY_COORDINATE(int y_COORDINATE) {
        Y_COORDINATE = y_COORDINATE;
    }
}