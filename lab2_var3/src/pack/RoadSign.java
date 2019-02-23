package pack;

import java.awt.*;
import java.awt.font.GlyphVector;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class RoadSign implements Shape, GraphSample {
    private Area shapeArea = new Area();
    private static final int WIDTH = 1000, HEIGHT = 500;
    public String getName() { return "Paints"; }
    public int getWidth() { return WIDTH; }
    public int getHeight() { return HEIGHT; }

    public void draw(final Graphics2D ig, Component c) {

        BufferedImage bimage = new BufferedImage(WIDTH/2, HEIGHT, BufferedImage.TYPE_INT_RGB);
        final Graphics2D g = bimage.createGraphics();

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setPaint(new GradientPaint(0, 0, new Color(200, 200, 200), WIDTH/2, 0, new Color(100, 100, 100)));
        g.fillRect(0, 0, WIDTH/2, HEIGHT);


        g.setColor(new Color(200, 0, 0));
        g.setStroke(new BasicStroke(40));
        g.drawOval(50, 50, WIDTH/2 - 100, HEIGHT - 100);

        g.setColor(Color.white);
        g.fillOval(70, 70, WIDTH/2 - 140, HEIGHT - 140);

        final Font font = new Font("Serif", Font.BOLD, 17);
        final Font bigfont = font.deriveFont(AffineTransform.getScaleInstance(30.0, 30.0));
        final GlyphVector gv = bigfont.createGlyphVector(g.getFontRenderContext(),
                "-");
        final Shape shapeD = gv.getGlyphOutline(0);

        g.setStroke(new BasicStroke(5.0f));

        final Paint shadowPaint = new Color(0, 0, 0, 100);
        final AffineTransform shadowTransform = AffineTransform.getShearInstance(-1.0, 0.0);
        shadowTransform.scale(1, 0.5);

        g.translate(160, 365);
        g.setPaint(shadowPaint);
        g.translate(0, 20);
        g.fill(shadowTransform.createTransformedShape(shapeD));
        g.translate(0, -20);
        g.setPaint(new Color(200, 0, 0));
        g.fill(shapeD);

        ig.drawImage(bimage, 0,0,c);
        ig.drawString("No filters", 10 , 13);
        ig.drawImage(new ConvolveOp(new Kernel(3, 3, new float[] {
                0.0f, -0.75f, 0.0f,
                -0.75f, 4.0f, -0.75f,
                0.0f, -0.75f, 0.0f})).filter(bimage, null), WIDTH/2,0, c);
        ig.drawString("Sharpen", WIDTH - 80,13);
        ig.drawLine(WIDTH/2, 0,WIDTH/2, HEIGHT);
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
