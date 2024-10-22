import java.awt.*;
import java.util.*;

public class Kite extends Box {
    private static final long serialVersionUID = 7568510222332939104L;
    Circle circle;
    Line line1;
    Line line2;
    Line line3;
    Line line4;
    ArrayList<Figure> children = new ArrayList<Figure>();

    Kite(Color color) {
        super(color);
        children.add(circle = new Circle(color));
        children.add(line1 = new Line(color));
        children.add(line2 = new Line(color));
        children.add(line3 = new Line(color));
        children.add(line4 = new Line(color));
    }

    Kite(Color color, int x, int y) {
        super(color, x, y);
        children.add(circle = new Circle(color, x, y));
        children.add(line1 = new Line(color, x, y));
        children.add(line2 = new Line(color, x, y));
        children.add(line3 = new Line(color, x, y));
        children.add(line4 = new Line(color, x, y));
    }

    Kite(Color color, int x1, int y1, int x2, int y2) {
        super(color, x1, y1, x2, y2);
        int w = Math.abs(x2 - x1);
        int h = Math.abs(y2 - y1);
        int x = Math.min(x1, x2);
        int y = Math.min(y1, y2);

        children.add(circle = new Circle(color, x + w / 4, y + h / 4, x + 3 * w / 4, y + 3 * h / 4));
        children.add(line1 = new Line(color, x1, y1, x2, y2));
        children.add(line2 = new Line(color, x1 + w / 2, y1, x1 + w / 2, y2));
        children.add(line3 = new Line(color, x2, y1, x1, y2));
        children.add(line4 = new Line(color, x1, y1 + h / 2, x2, y1 + h / 2));
    }

    void setXY2(int newX, int newY) {
        super.setXY2(newX, newY);
        int w = Math.abs(x2 - x1);
        int h = Math.abs(y2 - y1);
        int x = Math.min(x1, x2);
        int y = Math.min(y1, y2);

        circle.setXY12(x + w / 4, y + h / 4, x + 3 * w / 4, y + 3 * h / 4);
        line1.setXY12(x1, y1, x2, y2);
        line2.setXY12(x + w / 2, y, x + w / 2, y + h);
        line3.setXY12(x2, y1, x1, y2);
        line4.setXY12(x, y + h / 2, x + w, y + h / 2);
    }

    void setFill() {
        circle.setFill();
    }

    void setColor(Color color) {
        super.setColor(color);
        for (Figure ptr : children) {
            ptr.setColor(color);
        }
    }

    void draw(Graphics g) {
        super.draw(g);
        for (Figure ptr : children) {
            ptr.draw(g);
        }
    }

    void move(int dx, int dy) {
        super.move(dx, dy);
        for (Figure ptr : children) {
            ptr.move(dx, dy);
        }
    }

    Figure copy() {
        Kite newKite = new Kite(color, x1, y1, x2, y2);
        newKite.popup = popup;
        newKite.circle.setFillFlag(circle.getFillFlag());
        newKite.move(MOVE_DX, MOVE_DY);
        return newKite;
    }
}
