package pack;
import java.awt.*;
import java.awt.geom.FlatteningPathIterator;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;

public class MyStroke implements Stroke{

    private float ampl;
    private float elLength;
    private BasicStroke bs = new BasicStroke();

    public MyStroke(float ampl, float elLength) {
        this.ampl = ampl;
        this.elLength = elLength;
    }





    @Override
    public Shape createStrokedShape(Shape plot) {
        // TODO Auto-generated method stub
        GeneralPath res = new GeneralPath();
        PathIterator it = new FlatteningPathIterator(plot.getPathIterator(null), 1);
        float[] points = new float[2];
        float next = 0, mvX = 0, mvY = 0, prevX = 0, prevY = 0, curX = 0, curY = 0;
        int phase = 0, type = 0;

        while( !it.isDone()) {
            type = it.currentSegment( points );
            switch( type ){
                case PathIterator.SEG_MOVETO:
                    System.out.println("MoveTo");
                    mvX = prevX = points[0];
                    mvY = prevY = points[1];
                    res.moveTo( mvX, mvY );
                    next = elLength ;
                    break;

                case PathIterator.SEG_CLOSE:
                    points[0] = mvX;
                    points[1] = mvY;
                case PathIterator.SEG_LINETO:
                    System.out.println("LineTo");
                    curX = points[0];
                    curY = points[1];
                    float dx = curX - prevX;
                    float dy = curY - prevY;

                    float distance = (float)Math.sqrt( dx*dx + dy*dy )/1.7f;
                    if ( distance >= next ) {
                        float r = 1.0f/distance;
                        while ( distance >= next ) {
                            float x = prevX + next * r*dx;
                            float y = prevY + next * r*dy;
                            res.curveTo(x+ampl/8, y+ampl/8, x-ampl/8, y-ampl/8,x + ampl , y + ampl);
                            next += elLength;
                            phase++;
                        }
                    }
                    next -= distance;
                    prevX = curX;
                    prevY = curY;
                    if ( type == PathIterator.SEG_CLOSE ) {
                        System.out.println("SegClose");
                        res.closePath();
                    }
                    break;
            }
            it.next();
        }

        return bs.createStrokedShape(res);
    }

}
