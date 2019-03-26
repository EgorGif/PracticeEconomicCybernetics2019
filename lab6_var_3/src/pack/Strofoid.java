package pack;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.Serializable;


public class Strofoid implements Shape, Serializable, Cloneable, Transferable
{

    private static final long serialVersionUID = 1L;

    private int param = 100;
    private double delta = 0.001;
    private double startAngle = - Math.PI / 2 + delta;
    private double endAngle = Math.PI / 2 - delta;
    private int centerX = 0;
    private int centerY = 0;
    private int width;
    private int height;

    public Strofoid(int centerX, int centerY, int width, int height)
    {
        //this.param = param;
        this.centerX = centerX;
        this.centerY = centerY;
        this.width = width;
        this.height = height;
    }

    @Override
    public Rectangle getBounds()
    {
        return new Rectangle(centerX - 20, centerY - 40, centerX + 20, centerY + 40);
    }

    @Override
    public Rectangle2D getBounds2D()
    {
        return new Rectangle2D.Float(centerX - width, centerY - height, width, height);
    }

    @Override
    public boolean contains(double x, double y)
    {
        return false;
    }

    @Override
    public boolean contains(Point2D p)
    {
        return false;
    }

    @Override
    public boolean intersects(double x, double y, double w, double h)
    {
        return getBounds().intersects(x, y, w, h);
    }

    @Override
    public boolean intersects(Rectangle2D r)
    {
        return getBounds().intersects(r);
    }

    @Override
    public boolean contains(double x, double y, double w, double h)
    {
        return contains(x, y) && contains(x + w, y) && contains(x, y + h) && contains(x + w, y + h);
    }

    @Override
    public boolean contains(Rectangle2D r)
    {
        return contains(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }

    @Override
    public PathIterator getPathIterator(AffineTransform at)
    {
        return new PlotIterator(at);
    }

    @Override
    public PathIterator getPathIterator(AffineTransform at, double flatness)
    {
        return new PlotIterator(at, flatness);
    }


    class PlotIterator implements PathIterator
    {
        AffineTransform transform;
        double flatness;
        double angle = startAngle;
        double step = 10;
        boolean done = false;

        public PlotIterator(AffineTransform transform, double flatness)
        {
            this.transform = transform;
            this.flatness = flatness;
        }

        public PlotIterator(AffineTransform transform)
        {
            this.transform = transform;
            this.flatness = 0;
        }

        @Override
        public int getWindingRule() {
            return WIND_NON_ZERO;
        }

        @Override
        public boolean isDone() {
            return done;
        }

        @Override
        public void next()
        {
            if(done)
                return;
            if(flatness == 0)
                step = 0.05;
            else
                step = flatness;
            angle += step;
            if(angle >= endAngle)
                done = true;
        }

        @Override
        public int currentSegment(float[] coords)
        {
            coords[0] = (float)( (param * (Math.pow(Math.tan(Math.PI  + angle), 2) - 1)) / (Math.pow(Math.tan(Math.PI  + angle ), 2) + 1) ) + centerX;
            coords[1] = (float)( (param * Math.tan(Math.PI  + angle) * (Math.pow(Math.tan(Math.PI + angle ), 2) - 1)) / (Math.pow(Math.tan(Math.PI  + angle ), 2) + 1) )+ centerY;

            if (angle >= endAngle)
                done = true;
            if (angle == startAngle)
                return SEG_MOVETO;
            else return SEG_LINETO;
        }

        @Override
        public int currentSegment(double[] coords)
        {

            coords[0] = ( (param * (Math.pow(Math.tan(Math.PI  + angle), 2) - 1)) / (Math.pow(Math.tan(Math.PI  + angle ), 2) + 1) ) + centerX;
            coords[1] = ( (param * Math.tan(Math.PI  + angle) * (Math.pow(Math.tan(Math.PI + angle ), 2) - 1)) / (Math.pow(Math.tan(Math.PI  + angle ), 2) + 1) )+ centerY;

            if (angle >= endAngle) done = true;
            if (angle == startAngle) return SEG_MOVETO;
            else return SEG_LINETO;
        }


       /* private void checkForNext(double x, double y)
        {
            if (index == 2 && curAngle >= Math.PI)
            {
                done = true;
                return;
            }
            if ((index == 1 && curAngle >= Math.PI / 2) || (index == 0 && !getBounds().contains(x, y)))
            {
                index++;
                if (index == 1)
                    curAngle = 0;
                else if (index == 2)
                {
                    curAngle = Math.PI / 4 * 3 + angleStep;
                    x0 = leftX;
                    y0 = leftY;
                    noTransform = true;
                }
                start = true;

            }
        }*/
    }





    public Object clone()
    {
        try
        {
            Strofoid s = (Strofoid) super.clone(); // make a copy of all
            // fields
            return s;
        } catch (CloneNotSupportedException e)
        { // This should never happen
            return this;
        }
    }

    public static DataFlavor decDataFlavor = new DataFlavor(Strofoid.class, "DecartsLeaf");

    // This is a list of the flavors we know how to work with
    public static DataFlavor[] supportedFlavors = { decDataFlavor, DataFlavor.stringFlavor };

    public DataFlavor[] getTransferDataFlavors()
    {
        return (DataFlavor[]) supportedFlavors.clone();
    }

    public boolean isDataFlavorSupported(DataFlavor flavor)
    {
        return (flavor.equals(decDataFlavor) || flavor.equals(DataFlavor.stringFlavor));
    }

    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException
    {
        if (flavor.equals(decDataFlavor))
        {
            return this;
        } else if (flavor.equals(DataFlavor.stringFlavor))
        {
            return toString();
        } else
            throw new UnsupportedFlavorException(flavor);
    }

    @Override
    public String toString()
    {
        return centerX + " " + centerY + " " + width + " " + height ;
    }

    public static Strofoid getFromString(String s)
    {
        String[] arr = s.split(" ");
        return new Strofoid(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), Integer.parseInt(arr[2]), Integer
                .parseInt(arr[3]));
    }

    public void translate(double x, double y)
    {
        centerX += x;
        centerY += y;
    }


}



