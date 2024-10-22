import java.awt.*;
import javax.swing.*;
import java.io.*;

abstract class Figure implements Serializable {
    private static final long serialVersionUID = 5115481395465861190L;
    protected static int MOVE_DX = 20;
    protected static int MOVE_DY = 10;

    protected Polygon region;
    protected Color color;
    transient protected Popup popup;

    Figure(Color color) {
        this.color = color;
        region = null;
        popup = null;
    }

    void f() {
    }

    // hook function
    void setFill() {
    }

    void setColor(Color color) {
        this.color = color;
    }

    Color getColor() {
        return color;
    }

    void setPopup(Popup popup) {
        this.popup = popup;
    }

    void popup(JPanel view, int x, int y) {
        // delegation
        popup.popup(view, x, y);
    }

    abstract void setXY2(int x, int y);

    abstract void draw(Graphics g);

    abstract void makeRegion();

    abstract void move(int dx, int dy);

    abstract Figure copy();

    boolean contains(int x, int y) {
        if (region == null) return false;
        return region.contains(x, y);
    }

    void move(Graphics g, int dx, int dy) {
        draw(g);
        move(dx, dy);
        draw(g);
    }

    void drawing(Graphics g, int newX, int newY) {
        draw(g);
        setXY2(newX, newY);
        draw(g);
    }

    // hook function
    int getX1() {
        return -1;
    }

    int getY1() {
        return -1;
    }

    int getX2() {
        return -1;
    }

    int getY2() {
        return -1;
    }

    public String toString() {
        String s = "" + getClass().getName() + "[" + getX1() + "," + getY1();
        if (getX2() < 0) {
            s = s + "]";
            return s;
        }
        s = s + "," + getX2() + "," + getY2() + "]";
        return s;
    }
}
