import java.awt.*;

public class Point extends OnePointFigure {
    private static final long serialVersionUID = 573154677467364041L;

    Point(Color color) {
        super(color);
    }

    Point(Color color, int x, int y) {
        super(color, x, y);
    }

    void draw(Graphics g) {
        g.setColor(color);
        g.drawOval(x1 - GAP, y1 - GAP, 2 * GAP, 2 * GAP);
        g.fillOval(x1 - GAP, y1 - GAP, 2 * GAP, 2 * GAP);
    }

    Figure copy() {
        Point newPoint = new Point(color, x1, y1);
        newPoint.popup = popup;
        newPoint.move(MOVE_DX, MOVE_DY);
        return newPoint;
    }
}
