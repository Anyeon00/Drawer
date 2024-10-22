import java.awt.*;

public class Circle extends TwoPointFigure {
    private static final long serialVersionUID = -8390635094601811752L;
    private boolean fillFlag;

    Circle(Color color) {
        super(color);
        fillFlag = false;
    }

    Circle(Color color, int x, int y) {
        super(color, x, y);
        fillFlag = false;
    }

    Circle(Color color, int x1, int y1, int x2, int y2) {
        super(color, x1, y1, x2, y2);
        fillFlag = false;
    }

    boolean getFillFlag() {
        return fillFlag;
    }

    void setFillFlag(boolean flag) {
        fillFlag = flag;
    }

    void setFill() {
        fillFlag = !fillFlag;
    }

    void draw(Graphics g) {
        int minX = Math.min(x1, x2);
        int minY = Math.min(y1, y2);
        int width = Math.abs(x2 - x1);
        int height = Math.abs(y2 - y1);

        g.setColor(color);
        g.drawOval(minX, minY, width, height);

        if (fillFlag == true) {
            g.fillOval(minX, minY, width, height);
        }
    }

    Figure copy() {
        Circle newCircle = new Circle(color, x1, y1, x2, y2);
        newCircle.popup = popup;
        newCircle.fillFlag = fillFlag;
        newCircle.move(MOVE_DX, MOVE_DY);
        return newCircle;
    }
}
