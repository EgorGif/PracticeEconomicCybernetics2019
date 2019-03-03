package pack;
import java.awt.*;
import java.awt.geom.*;

public class MyStroke implements Stroke {

    private float amplitude;
    private float period;
    private BasicStroke bs = new BasicStroke();

    public MyStroke(float amplitude, float period) {
        this.amplitude = amplitude;
        this.period = period;
    }

    @Override
    public Shape createStrokedShape(Shape plot) {
        GeneralPath res = new GeneralPath();
        PathIterator it = new FlatteningPathIterator(plot.getPathIterator(null), 1);
        float[] points = new float[2];
        float next = 0, mvX = 0, mvY = 0, prevX = 0, prevY = 0, curX = 0, curY = 0;
        int type;

        while (!it.isDone()) {
            type = it.currentSegment(points);
            switch (type) {
                case PathIterator.SEG_MOVETO:
                    mvX = prevX = points[0];
                    mvY = prevY = points[1];
                    res.moveTo(mvX, mvY);
                    next = period;
                    break;
                case PathIterator.SEG_CLOSE:
                    points[0] = mvX;
                    points[1] = mvY;
                case PathIterator.SEG_LINETO:
                    curX = points[0];
                    curY = points[1];
                    float dx = curX - prevX;
                    float dy = curY - prevY;

                    float distance = (float) Math.sqrt(dx * dx + dy * dy) / 1.7f;
                    if (distance >= next) {
                        float r = 1.0f / distance;
                        while (distance >= next) {
                            float x = prevX + next * r * dx;
                            float y = prevY + next * r * dy;
                            res.curveTo(x + amplitude / 8, y + amplitude / 8, x - amplitude / 8, y - amplitude / 8, x + amplitude, y + amplitude);
                            next += period;
                        }
                    }
                    next -= distance;
                    prevX = curX;
                    prevY = curY;
                    if (type == PathIterator.SEG_CLOSE) {
                        res.closePath();
                    }
                    break;
            }
            it.next();
        }

        return bs.createStrokedShape(res);
    }

}