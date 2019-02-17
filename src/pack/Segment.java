package pack;
import java.awt.*;
import java.awt.geom.*;
import java.awt.geom.AffineTransform;

public class Segment extends Canvas implements Shape {

    private Area shapeArea = new Area();
    private Shape segment;
    private String bgColor;
    private String stroke;
    private String segmentColor;
    private int x1 = 300;
    private int width = 200;
    private int height = 10;
    private int y1 = 250;
    private double angle;

    Segment(double angle, String bgColor, String stroke, String segmentColor){
        this.angle = angle;
        this.segment = new Rectangle2D.Double(x1, y1, width, height);
        this.bgColor = bgColor;
        this.stroke = stroke;
        this.segmentColor = segmentColor;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        AffineTransform at = AffineTransform.getRotateInstance(angle, x1, y1+5);
        graphics2D.setTransform(at);
        graphics2D.setStroke(new BasicStroke(Float.valueOf(stroke)));
        graphics2D.setColor(new Color(Integer.valueOf(bgColor,16)));
        graphics2D.fillRect(this.x1, this.y1, this.width, this.height);
        graphics2D.setColor(new Color(Integer.valueOf(segmentColor,16)));
        graphics2D.draw(segment);
    }

    @Override
    public boolean contains(Point2D arg0) {
        return shapeArea.contains(arg0);
    }

    @Override
    public boolean contains(Rectangle2D arg0) {
        return shapeArea.contains(arg0);
    }

    @Override
    public boolean contains(double arg0, double arg1) {
        return shapeArea.contains(arg0, arg1);
    }

    @Override
    public boolean contains(double arg0, double arg1, double arg2, double arg3) {
        return shapeArea.contains(arg0, arg1, arg2, arg3);
    }

    @Override
    public Rectangle getBounds() {
        return shapeArea.getBounds();
    }

    @Override
    public Rectangle2D getBounds2D() {
        return shapeArea.getBounds2D();
    }

    @Override
    public PathIterator getPathIterator(AffineTransform arg0) {
        return shapeArea.getPathIterator(arg0);
    }

    @Override
    public PathIterator getPathIterator(AffineTransform arg0, double arg1) {
        return shapeArea.getPathIterator(arg0, arg1);
    }

    @Override
    public boolean intersects(Rectangle2D arg0) {
        return shapeArea.intersects(arg0);
    }

    @Override
    public boolean intersects(double arg0, double arg1, double arg2, double arg3) {
        return shapeArea.intersects(arg0, arg1, arg2, arg3);
    }

}
