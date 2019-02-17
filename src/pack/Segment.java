package pack;
import java.awt.*;
import java.awt.geom.*;

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

    public void setAngle(final double rotate){this.angle += rotate;}

    Segment(final double angle, final String bgColor, final String stroke, final String segmentColor){
        super();
        this.angle = angle;
        this.segment = new Rectangle2D.Double(x1, y1, width, height);
        this.bgColor = bgColor;
        this.stroke = stroke;
        this.segmentColor = segmentColor;
    }

    @Override
    public void paint(final Graphics g) {
        final Graphics2D graphics2D = (Graphics2D) g;
        final AffineTransform at = AffineTransform.getRotateInstance(angle, x1, y1+5);
        graphics2D.setTransform(at);
        graphics2D.setStroke(new BasicStroke(Float.valueOf(stroke)));
        graphics2D.setColor(new Color(Integer.valueOf(bgColor,16)));
        graphics2D.fillRect(this.x1, this.y1, this.width, this.height);
        graphics2D.setColor(new Color(Integer.valueOf(segmentColor,16)));
        graphics2D.draw(segment);
    }

    @Override
    public boolean contains(final Point2D arg0) {
        return shapeArea.contains(arg0);
    }

    @Override
    public boolean contains(final Rectangle2D arg0) {
        return shapeArea.contains(arg0);
    }

    @Override
    public boolean contains(final double arg0, final double arg1) {
        return shapeArea.contains(arg0, arg1);
    }

    @Override
    public boolean contains(final double arg0, final double arg1, final double arg2, final double arg3) {
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
    public PathIterator getPathIterator(final AffineTransform arg0) {
        return shapeArea.getPathIterator(arg0);
    }

    @Override
    public PathIterator getPathIterator(final AffineTransform arg0, final double arg1) {
        return shapeArea.getPathIterator(arg0, arg1);
    }

    @Override
    public boolean intersects(final Rectangle2D arg0) {
        return shapeArea.intersects(arg0);
    }

    @Override
    public boolean intersects(final double arg0, final double arg1, final double arg2, final double arg3) {
        return shapeArea.intersects(arg0, arg1, arg2, arg3);
    }

}
